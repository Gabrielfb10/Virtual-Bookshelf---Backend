package com.acirio.virtual_bookshelf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAiRecommendationResponseDto {

    private String name;

    private String author;

    private String genre;

    private String reason;
}
