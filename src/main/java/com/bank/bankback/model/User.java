package com.bank.bankback.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private long id;
    private long document;
    private String name;
    private String username;
    private String password;
    private boolean active;

    public User(long id, long document, String name, String username, String password, boolean active){

        this.id = id;
        this.document = document;
        this.name = name;
        this.username = username;
        this.password = password;
        this.active = active;

    }

    public User(long document, String name, String username, String password, boolean active){

        this.document = document;
        this.name = name;
        this.username = username;
        this.password = password;
        this.active = active;

    }

}
