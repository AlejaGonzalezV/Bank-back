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
    private Long id;

    @Column(unique = true)
    private Long document;

    private String name;
    private String username;
    private boolean active;

    public UserDao(Long document, String name, String username, boolean active){

        this.document = document;
        this.name = name;
        this.username = username;
        this.active = active;

    }

}
