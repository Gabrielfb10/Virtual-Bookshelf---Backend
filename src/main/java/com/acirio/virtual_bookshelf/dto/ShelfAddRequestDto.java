package com.acirio.virtual_bookshelf.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelfAddRequestDto {
    @NotNull
    @Schema(description = "ID do livro a ser adicionado na estante", example = "5")
    private Long bookId;
}
