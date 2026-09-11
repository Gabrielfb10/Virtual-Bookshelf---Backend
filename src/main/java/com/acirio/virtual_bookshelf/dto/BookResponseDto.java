package com.acirio.virtual_bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseDto {
    @Schema(description = "ID único do livro", example = "5")
    private Long id;

    @Schema(description = "Nome do livro", example = "O Senhor dos Anéis")
    private String name;

    @Schema(description = "Autor do livro", example = "J.R.R. Tolkien")
    private String author;

    @Schema(description = "Categoria do livro", example = "FICTION")
    private String category;

    @Schema(description = "Gênero do livro", example = "Fantasia")
    private String genre;

    @Schema(description = "Número total de páginas do livro", example = "1200")
    private Long numberOfPages;

    @Schema(description = "Caminho da imagem de capa do livro", example = "/covers/imagem.jpg")
    private String cover;
}
