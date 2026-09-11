package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.dto.BookRequestDto;
import com.acirio.virtual_bookshelf.dto.BookResponseDto;
import com.acirio.virtual_bookshelf.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {


    @Autowired
    private BookService bookService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponseDto> registerBook(@Valid @RequestPart("data") BookRequestDto bookRequestDto, @RequestPart("cover") MultipartFile cover) {
        BookResponseDto bookResponseDto = bookService.registerBook(bookRequestDto, cover);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponseDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        List<BookResponseDto> booksResponse = bookService.getBooks();
        return ResponseEntity.status(HttpStatus.OK).body(booksResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        BookResponseDto bookResponse = bookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updatebook(@PathVariable Long id, @Valid @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto bookResponse = bookService.updateBook(id, bookRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body("Livro deletado com sucesso.");
    }
}
