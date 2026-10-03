package com.acirio.virtual_bookshelf.service;

import com.acirio.virtual_bookshelf.dto.BookAiRecommendationResponseDto;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

import java.util.List;

public interface AiRecommendationService {

    @SystemMessage("Você é um vendedor de uma livraria com conhecimento amplo em literatura. Você deve recomendar 3 livros baseados no histórico de leitura forcenido.")
    List<BookAiRecommendationResponseDto> recommendBooks(@UserMessage String userHistory);
}
