package com.bank.bankback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private Long document;
    private String name;
    private String username;
    private boolean isActive;

    public UserDto(Long id, Long document, String name, String username, boolean isActive){

        this.id = id;
        this.document = document;
        this.name = name;
        this.username = username;
        this.isActive = isActive;

    }

}
