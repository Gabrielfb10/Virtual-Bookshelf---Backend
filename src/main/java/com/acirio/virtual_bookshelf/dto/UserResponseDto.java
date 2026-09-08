package com.acirio.virtual_bookshelf.dto;
import com.acirio.virtual_bookshelf.model.enums.UserRoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    
    private String username;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

}
