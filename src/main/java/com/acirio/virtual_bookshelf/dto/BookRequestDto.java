package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.CategoryBookEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequestDto {
    @NotBlank
    @Schema(description = "Nome do livro", example = "O Senhor dos Anéis")
    private String name;

    @NotBlank
    @Schema(description = "Autor do livro", example = "J.R.R. Tolkien")
    private String author;

    @NotNull
    @Schema(description = "Categoria do livro", example = "FICTION")
    private CategoryBookEnum category;

    @NotBlank
    @Schema(description = "Gênero do livro", example = "Fantasia")
    private String genre;

    @NotBlank
    @Schema(description = "Sinopse ou descrição do livro", example = "Uma aventura épica...")
    private String description;

    @NotNull
    @Schema(description = "Número total de páginas do livro", example = "1200")
    private Integer numberOfPages;
}
