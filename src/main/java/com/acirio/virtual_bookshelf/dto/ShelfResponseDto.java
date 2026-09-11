package com.acirio.virtual_bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelfResponseDto {
    @Schema(description = "ID único do registro na estante", example = "1")
    private Long id;

    @Schema(description = "ID do usuário dono da estante", example = "1")
    private Long user;

    @Schema(description = "ID do livro na estante", example = "5")
    private Long book;

    @Schema(description = "Status de leitura do livro na estante", example = "READING")
    private String status;

    @Schema(description = "Página atual em que o usuário parou a leitura", example = "45")
    private int currentPage;

    @Schema(description = "Porcentagem concluída da leitura", example = "15")
    private int percentageRead;

    @Schema(description = "Nota dada pelo usuário ao livro", example = "4.5")
    private float note;

    @Schema(description = "Data em que o livro foi adicionado à estante", example = "2026-09-10T15:30:00")
    private LocalDateTime addedDate;

    @Schema(description = "Data em que a leitura foi iniciada", example = "2026-09-11T10:00:00")
    private LocalDateTime readingStartDate;

    @Schema(description = "Data em que a leitura foi concluída", example = "2026-09-20T18:00:00")
    private LocalDateTime readingEndDate;
}
