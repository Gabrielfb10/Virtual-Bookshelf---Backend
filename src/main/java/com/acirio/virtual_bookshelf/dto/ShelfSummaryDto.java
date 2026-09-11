package com.acirio.virtual_bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShelfSummaryDto {
    @Schema(description = "Caminho da imagem de capa", example = "/covers/imagem.jpg")
    private String cover;
    
    @Schema(description = "Nome do livro", example = "O Senhor dos Anéis")
    private String name;
    
    @Schema(description = "Autor do livro", example = "J.R.R. Tolkien")
    private String author;
    
    @Schema(description = "Página atual da leitura", example = "45")
    private int currentPage;
    
    @Schema(description = "Porcentagem da leitura", example = "15")
    private int percentageRead;
}
