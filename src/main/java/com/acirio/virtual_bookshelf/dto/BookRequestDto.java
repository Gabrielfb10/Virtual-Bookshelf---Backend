package com.acirio.virtual_bookshelf.dto;

import com.acirio.virtual_bookshelf.model.enums.CategoryBookEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String author;

    @NotNull
    private CategoryBookEnum category;

    @NotBlank
    private String genre;

    @NotNull
    private Long numberOfPages;
}
