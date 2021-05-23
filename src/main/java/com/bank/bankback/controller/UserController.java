package com.bank.bankback.controller;

import com.bank.bankback.dto.CreateUserBodyDto;
import com.bank.bankback.dto.UpdateUserBodyDto;
import com.bank.bankback.dto.UserDto;
import com.bank.bankback.model.User;
import com.bank.bankback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Collection<UserDto>> listUsers() {
        var users = userService.getAll();
        var dtos = users.stream().map(user -> map(user)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        try {
            var user = userService.get(id);
            return ResponseEntity.status(HttpStatus.OK).body(map(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("The person with document %s does not exists", id));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("The person with document %s does not exists", id));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@RequestBody CreateUserBodyDto userDto) {
        try {
            var user = map(userDto);
            user = userService.save(user);
            return new ResponseEntity<UserDto>(map(user), HttpStatus.CREATED);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(String.format("The user with document %s has already been created", userDto.getDocument()));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable("id") long id, @RequestBody UpdateUserBodyDto userDto) {
        try {
            var updatedUser = userService.replace(id, map(userDto));
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user does not exist");
        }
    }

    private UserDto map(User user) {
        return new UserDto(
                user.getId(),
                user.getDocument(),
                user.getName(),
                user.getUsername(),
                user.isActive());
    }

    private User map(CreateUserBodyDto dto) {
        return new User(
                dto.getDocument(),
                dto.getName(),
                dto.getUsername(),
                dto.isActive());

    }

    private User map(UpdateUserBodyDto dto) {
        return new User(
                dto.getId(),
                dto.getDocument(),
                dto.getName(),
                dto.getUsername(),
                dto.isActive());
    }

}
