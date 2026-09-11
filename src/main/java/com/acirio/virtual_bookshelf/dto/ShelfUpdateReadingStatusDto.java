package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.StatusBookEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShelfUpdateReadingStatusDto {
    @NotNull
    @Schema(description = "Status de leitura desejado", example = "READING")
    private StatusBookEnum status;
}
