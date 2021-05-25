package com.bank.bankback.unit.api.user;

import au.com.dius.pact.provider.junitsupport.State;
import com.bank.bankback.controller.UserController;
import com.bank.bankback.dao.UserDao;
import com.bank.bankback.model.User;
import com.bank.bankback.repository.UserRepository;
import com.bank.bankback.service.UserService;
import com.bank.bankback.service.UserServiceImp;
import lombok.SneakyThrows;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserServiceImp userService;

    @Test
    public void listUsersOneElement(){
        UserDao userdao = new UserDao();
        userdao.setId(80L);
        userdao.setName("Jasper");
        userdao.setUsername("Jas123");
        userdao.setActive(true);
        userdao.setDocument(65656L);

        List<UserDao> users = new ArrayList<UserDao>();
        users.add(userdao);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        assertThat(userService.getAll(), hasSize(1));
        assertThat(userService.getAll(), not(IsEmptyCollection.empty()));

    }

    @Test
    public void listUsersNoElements(){
        List<UserDao> users = new ArrayList<UserDao>();

        Mockito.when(userRepository.findAll()).thenReturn(users);
        assertThat(userService.getAll(), hasSize(0));
        assertThat(userService.getAll(), IsEmptyCollection.empty());
    }

    @Test
    @SneakyThrows
    public void deleteUserSuccessful(){
        UserDao userdao = new UserDao();
        userdao.setId(80L);
        userdao.setName("Jasper");
        userdao.setUsername("Jas123");
        userdao.setActive(true);
        userdao.setDocument(65656L);

        Mockito.when(userRepository.findByDocument(userdao.getDocument())).thenReturn(userdao);
        assertTrue(userService.delete(userdao.getDocument()));

    }

    @Test
    public void deleteUserWrong(){
        Long iddocument = 1007554028L;

        Mockito.when(userRepository.findByDocument(iddocument)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.delete(iddocument);
        });
        String expectedMessage = "El usuario no existe";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @SneakyThrows
    public void getUserSuccessful(){
        UserDao userdao = new UserDao();
        userdao.setId(80L);
        userdao.setName("Jasper");
        userdao.setUsername("Jas123");
        userdao.setActive(true);
        userdao.setDocument(65656L);

        Mockito.when(userRepository.findByDocument(userdao.getDocument())).thenReturn(userdao);

        assertThat(userService.get(userdao.getDocument()), hasProperty("name", is("Jasper")));
        assertThat(userService.get(userdao.getDocument()), hasProperty("id", is(80L)));
        assertThat(userService.get(userdao.getDocument()), hasProperty("document", is(65656L)));
        assertThat(userService.get(userdao.getDocument()), hasProperty("username", is("Jas123")));
        assertThat(userService.get(userdao.getDocument()), hasProperty("active", is(true)));
    }

    @Test
    public void getUserWrong(){
        Long iddocument = 1007554028L;

        Mockito.when(userRepository.findByDocument(iddocument)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.get(iddocument);
        });
        String expectedMessage = "El usuario no existe";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @SneakyThrows
    public void saveUserSuccessful(){
        User user = new User();
        user.setName("Jasper");
        user.setUsername("Jas123");
        user.setActive(true);
        user.setDocument(65656L);

        UserDao userDao = new UserDao();
        userDao.setId(80L);
        userDao.setName("Jasper");
        userDao.setUsername("Jas123");
        userDao.setActive(true);
        userDao.setDocument(65656L);

        Mockito.when(userRepository.findByDocument(user.getDocument())).thenReturn(null);
        Mockito.when(userRepository.save(Mockito.any(UserDao.class))).thenReturn(userDao);

        assertThat(userService.save(user), hasProperty("name", is("Jasper")));
        assertThat(userService.save(user), hasProperty("document", is(65656L)));
        assertThat(userService.save(user), hasProperty("username", is("Jas123")));
        assertThat(userService.save(user), hasProperty("active", is(true)));

    }

    @Test
    public void saveUserWrong(){
        User user = new User();
        user.setName("Jasper");
        user.setUsername("Jas123");
        user.setActive(true);
        user.setDocument(65656L);

        UserDao userDao = new UserDao();

        Mockito.when(userRepository.findByDocument(user.getDocument())).thenReturn(userDao);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.save(user);
        });
        String expectedMessage = "El usuario ya está registrado";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

//    @Test
//    @SneakyThrows
//    public void saveUserWrongId(){
//        User user = new User();
//        user.setName("Jasper");
//        user.setUsername("Jas123");
//        user.setActive(true);
//
//        UserDao userDao = new UserDao();
//        userDao.setName("Jasper");
//        userDao.setId(80L);
//        userDao.setUsername("Jas123");
//        userDao.setActive(true);
//
//        Mockito.when(userRepository.findByDocument(user.getDocument())).thenReturn(null);
//        Mockito.when(userRepository.save(Mockito.any(UserDao.class))).thenReturn(userDao);
//
//        assertThat(userService.save(user), hasProperty("name", is("Jasper")));
//        assertThat(userService.save(user), hasProperty("document", is(65656L)));
//        assertThat(userService.save(user), hasProperty("username", is("Jas123")));
//        assertThat(userService.save(user), hasProperty("active", is(true)));
//
//    }

    @Test
    @SneakyThrows
    public void replaceUserSuccessful(){
        User user = new User();
        user.setName("Jay");
        user.setUsername("Jay123");
        user.setActive(false);
        user.setDocument(65656L);

        UserDao userDao = new UserDao();
        userDao.setId(80L);
        userDao.setName("Jasper");
        userDao.setUsername("Jas123");
        userDao.setActive(true);
        userDao.setDocument(65656L);

        UserDao newDao = new UserDao();
        newDao.setId(80L);
        newDao.setName("Jay");
        newDao.setUsername("Jay123");
        newDao.setActive(false);
        newDao.setDocument(65656L);

        Mockito.when(userRepository.findByDocument(user.getDocument())).thenReturn(userDao);
        Mockito.when(userRepository.save(Mockito.any(UserDao.class))).thenReturn(newDao);

        assertThat(userService.replace(user.getDocument(), user), hasProperty("name", is("Jay")));
        assertThat(userService.replace(user.getDocument(), user), hasProperty("document", is(65656L)));
        assertThat(userService.replace(user.getDocument(), user), hasProperty("username", is("Jay123")));
        assertThat(userService.replace(user.getDocument(), user), hasProperty("active", is(false)));

    }

    @Test
    public void replaceUserWrong(){
        User user = new User();
        user.setName("Jasper");
        user.setUsername("Jas123");
        user.setActive(true);
        user.setDocument(65656L);

        Mockito.when(userRepository.findByDocument(user.getDocument())).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.replace(user.getDocument(), user);
        });
        String expectedMessage = "El usuario no existe";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @SneakyThrows
    public void replaceUserNullName(){
        User user = new User();
        user.setUsername("Jay123");
        user.setActive(false);
        user.setDocument(65656L);

        UserDao userDao = new UserDao();
        userDao.setId(80L);
        userDao.setUsername("Jas123");
        userDao.setActive(true);
        userDao.setDocument(65656L);

        UserDao newDao = new UserDao();
        newDao.setId(80L);
        newDao.setName("Jay");
        newDao.setUsername("Jay123");
        newDao.setActive(false);
        newDao.setDocument(65656L);

        Mockito.when(userRepository.findByDocument(user.getDocument())).thenReturn(userDao);
        Mockito.when(userRepository.save(Mockito.any(UserDao.class))).thenReturn(newDao);

        assertNull(userService.replace(user.getDocument(), user).getName());
        //assertThat(userService.replace(user.getDocument(), user), hasProperty("name", is("Jay")));
        assertThat(userService.replace(user.getDocument(), user), hasProperty("document", is(65656L)));
        assertThat(userService.replace(user.getDocument(), user), hasProperty("username", is("Jay123")));
        assertThat(userService.replace(user.getDocument(), user), hasProperty("active", is(false)));

    }

}
