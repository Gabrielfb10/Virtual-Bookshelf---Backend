package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.CategoryBookEnum;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class BookFilter {
    @Schema(description = "Filtro por nome do livro", example = "Senhor")
    private String name;
    @Schema(description = "Filtro por autor do livro", example = "Tolkien")
    private String author;
    @Schema(description = "Filtro por categoria do livro", example = "FICTION")
    private CategoryBookEnum category;
    @Schema(description = "Filtro por gênero do livro", example = "Fantasia")
    private String genre;
}
