package com.bank.bankback.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "USERR")
public class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GENERATOR")
    @SequenceGenerator(name = "USER_GENERATOR", sequenceName = "USER_SEQ", allocationSize = 1)
    private long id;

    @Column(unique = true)
    private long document;

    private String name;
    private String username;
    private String password;
    private boolean active;

    public UserDao(long document, String name, String username, String password, boolean active){

        this.document = document;
        this.name = name;
        this.username = username;
        this.password = password;
        this.active = active;

    }

}
