package com.bank.bankback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private long id;
    private long document;
    private String name;
    private String userName;
    private String password;
    private boolean isActive;

    public UserDto(long id, long document, String name, String userName, String password, boolean isActive){

        this.id = id;
        this.document = document;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;

    }

}
