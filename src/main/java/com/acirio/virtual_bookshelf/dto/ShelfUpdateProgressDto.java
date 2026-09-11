package com.acirio.virtual_bookshelf.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShelfUpdateProgressDto {
    @NotNull
    @Schema(description = "Página atual em que o usuário está na leitura", example = "45")
    private int currentPage;
}
