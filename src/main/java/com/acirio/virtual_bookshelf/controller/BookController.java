package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.dto.BookRequestDto;
import com.acirio.virtual_bookshelf.dto.BookResponseDto;
import com.acirio.virtual_bookshelf.service.BookService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/books")
@Tag(name = "Livros", description = "Endpoints para gerenciamento do catálogo de livros")
public class BookController {


    @Autowired
    private BookService bookService;

    @Operation(summary = "Cadastrar livro", description = "Adiciona um novo livro ao catálogo global da plataforma.")
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponseDto> registerBook(@Valid @RequestPart("data") BookRequestDto bookRequestDto, @RequestPart("cover") MultipartFile cover) {
        BookResponseDto bookResponseDto = bookService.registerBook(bookRequestDto, cover);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseDto);
    }

    @Operation(summary = "Listar livros", description = "Lista os livros do catálogo global com suporte a paginação e filtros.")
    @GetMapping("/")
    public ResponseEntity<Page<BookResponseDto>> getBooks(@ParameterObject com.acirio.virtual_bookshelf.dto.BookFilter filter, @ParameterObject Pageable pageable) {
        Page<BookResponseDto> booksResponse = bookService.getBooks(filter, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(booksResponse);
    }

    @Operation(summary = "Detalhes do livro", description = "Retorna os detalhes de um livro específico do catálogo global.")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        BookResponseDto bookResponse = bookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @Operation(summary = "Atualizar livro", description = "Edita os dados de um livro do catálogo.")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updatebook(@PathVariable Long id, @Valid @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto bookResponse = bookService.updateBook(id, bookRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @Operation(summary = "Deletar livro", description = "Remove um livro do catálogo global.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
