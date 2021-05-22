package com.bank.bankback.service;

import com.bank.bankback.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(long id) throws Exception;

    User save(User user);

    User replace(long idDocument, User user) throws Exception;

    void delete(long id) throws Exception;
}
