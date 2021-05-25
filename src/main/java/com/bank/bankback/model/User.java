package com.bank.bankback.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private long id;
    private Long document;
    private String name;
    private String username;
    private boolean active;

    public User(long id, Long document, String name, String username, boolean active){

        this.id = id;
        this.document = document;
        this.name = name;
        this.username = username;
        this.active = active;

    }

    public User(Long document, String name, String username, boolean active){

        this.document = document;
        this.name = name;
        this.username = username;
        this.active = active;

    }

}
