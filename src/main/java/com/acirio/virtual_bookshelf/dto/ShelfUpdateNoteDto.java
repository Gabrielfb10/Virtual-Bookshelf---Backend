package com.acirio.virtual_bookshelf.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShelfUpdateNoteDto {
    @NotNull
    @Schema(description = "Nota dada pelo usuário ao livro, de 1 a 5", example = "4.5")
    private float note;
}
