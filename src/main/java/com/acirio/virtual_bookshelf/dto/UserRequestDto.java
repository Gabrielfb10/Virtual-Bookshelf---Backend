package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto {

    private String username;

    private String name;

    @Email
    private String email;

    private UserRoleEnum role;
}
