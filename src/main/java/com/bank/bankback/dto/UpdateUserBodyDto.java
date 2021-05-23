package com.bank.bankback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserBodyDto extends CreateUserBodyDto{

    private long id;

    public UpdateUserBodyDto(long id, long document, String name, String username, boolean isActive){
        super(document,name,username,isActive);
        this.id = id;
    }

}
