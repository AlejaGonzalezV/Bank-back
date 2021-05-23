package com.bank.bankback.service;

import com.bank.bankback.dao.UserDao;
import com.bank.bankback.model.User;
import com.bank.bankback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImp implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository){

        this.userRepository = userRepository;

    }

    @Override
    public List<User> getAll(){
        var userDao = userRepository.findAll();
        return StreamSupport
                .stream(userDao.spliterator(), false)
                .map(dao -> this.map(dao)).collect(Collectors.toList());
    }

    @Override
    public User get(long id) throws Exception {
        var animalDao = userRepository.findByDocument(id);

        if (animalDao == null) {
            throw new Exception("El usuario no existe");
        }

        return map(animalDao);
    }

    @Override
    public User save(User user) throws Exception {
        var dao = map(user);
        UserDao saved;
        if(userRepository.findByDocument(dao.getDocument()) == null){
            saved = userRepository.save(dao);

        }else{
            throw new Exception("El usuario ya está registrado");
        }
        return map(saved);
    }

    @Override
    public User replace(long idDocument, User user) throws Exception {

        var oldUser = userRepository.findByDocument(idDocument);

        if (oldUser == null) {
            throw new Exception("El usuario no existe");
        }

        var newDao = map(user);
        newDao.setId(oldUser.getId());

        userRepository.save(newDao);

        return user;
    }

    @Override
    public void delete(long id) throws Exception {
        var dao = userRepository.findByDocument(id);

        if (dao == null) {
            throw new Exception("El usuario no existe");
        }

        userRepository.delete(dao);
    }


    private User map(UserDao dao) {
        return new User(
                dao.getId(),
                dao.getDocument(),
                dao.getName(),
                dao.getUsername(),
                dao.isActive()
        );
    }

    private UserDao map(User user) {
        return new UserDao(
                user.getDocument(),
                user.getName(),
                user.getUsername(),
                user.isActive());
    }

}
