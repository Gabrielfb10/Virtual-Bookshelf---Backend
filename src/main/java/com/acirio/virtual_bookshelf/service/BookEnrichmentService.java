package com.acirio.virtual_bookshelf.service;

import com.acirio.virtual_bookshelf.model.BookModel;
import com.acirio.virtual_bookshelf.repository.BookRepository;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookEnrichmentService {

    @Autowired
    private ChatModel chatModel;
    @Autowired
    private BookRepository bookRepository;

    private String generateDescription(String bookName, String author, String genre) {

        //Prepara o prompt padrão que será enviado
        String prompt = String.format(
                "Escreva uma sinopse que atrai um leitor que não conhece a obra do livro chamado '%s'," +
                "escrito por '%s', do gênero '%s'." +
                "Retorne apenas o texto da sinopse, sem aspas, introduções, explicações ou avaliações com no máximo 400 caracteres.", bookName, author, genre
        );

        //Monta a request
        ChatRequest chatRequest = ChatRequest.builder()
                .messages(SystemMessage.from("Você é um especialista em literatura."), UserMessage.from(prompt))
                .temperature(0.4)
                .build();

        //Aciona o modelo LLM
        ChatResponse response = chatModel.chat(chatRequest);

        return response.aiMessage().text();
    }

    @Async
    @Transactional
    public void aplicateDescriptionAsyn(Long bookId, String bookName, String author, String genre) {
        try {

            String newDescription = generateDescription(bookName, author, genre);

            BookModel book = bookRepository.findById(bookId).orElse(null);
            if (book == null) {
                return;
            }

            book.setDescription(newDescription);
            bookRepository.save(book);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

