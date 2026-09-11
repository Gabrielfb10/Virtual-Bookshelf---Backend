package com.acirio.virtual_bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseDto {
    private Long id;

    private String name;

    private String author;

    private String category;

    private String genre;

    private Long numberOfPages;

    private String cover;
}
