package com.acirio.virtual_bookshelf.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordDto {
    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

}