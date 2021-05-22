package com.bank.bankback.repository;

import com.bank.bankback.dao.UserDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDao, Long> {
    UserDao findByDocument(long id);
}
