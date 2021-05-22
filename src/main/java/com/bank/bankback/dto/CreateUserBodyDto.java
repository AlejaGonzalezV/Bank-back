package com.bank.bankback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserBodyDto {

    private long document;
    private String name;
    private String userName;
    private String password;
    private boolean active;

    public CreateUserBodyDto(long document, String name, String userName, String password, boolean active){

        this.document = document;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.active = active;

    }

}
