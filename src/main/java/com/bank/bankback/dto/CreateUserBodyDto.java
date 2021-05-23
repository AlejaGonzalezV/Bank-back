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
    private String username;
    private boolean active;

    public CreateUserBodyDto(long document, String name, String username, boolean active){

        this.document = document;
        this.name = name;
        this.username = username;
        this.active = active;

    }

}
