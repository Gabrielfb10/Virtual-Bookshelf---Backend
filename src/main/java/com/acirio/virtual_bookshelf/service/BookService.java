package com.acirio.virtual_bookshelf.service;

import com.acirio.virtual_bookshelf.dto.BookRequestDto;
import com.acirio.virtual_bookshelf.dto.BookResponseDto;
import com.acirio.virtual_bookshelf.exception.ConflictException;
import com.acirio.virtual_bookshelf.exception.ResourceNotFoundException;
import com.acirio.virtual_bookshelf.mapper.BookMapper;
import com.acirio.virtual_bookshelf.model.BookModel;
import com.acirio.virtual_bookshelf.repository.BookRepository;
import com.acirio.virtual_bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private UserRepository userRepository;

    public BookResponseDto registerBook(BookRequestDto bookRequestDto, MultipartFile cover) {
        if(bookRepository.existsByName(bookRequestDto.getName())) {
            throw new ConflictException("Já existe um livro cadastrado com esse nome.");
        }

        String coverFileName = UUID.randomUUID() + "_" + cover.getOriginalFilename();

        try {
            Path uploadDir = Paths.get("uploads/covers");
            Files.createDirectories(uploadDir);
            Path filePath = uploadDir.resolve(coverFileName);
            Files.copy(cover.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new RuntimeException("Erro ao salvar a imagem de capa.",e);
        }

        BookModel book = bookMapper.toEntity(bookRequestDto);
        book.setCover("/covers/" + coverFileName);

        BookModel bookSaved = bookRepository.save(book);

        return bookMapper.toResponse(bookSaved);
    }

    public org.springframework.data.domain.Page<BookResponseDto> getBooks(com.acirio.virtual_bookshelf.dto.BookFilter filter, org.springframework.data.domain.Pageable pageable) {
        BookModel probe = bookMapper.toEntity(filter);
        org.springframework.data.domain.Example<BookModel> example = org.springframework.data.domain.Example.of(probe, org.springframework.data.domain.ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues());

        org.springframework.data.domain.Page<BookModel> booksPage = bookRepository.findAll(example, pageable);
        return booksPage.map(bookMapper::toResponse);
    }

    public BookResponseDto getBookById(Long id) {
        BookModel book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O livro não existe."));

        return bookMapper.toResponse(book);
    }

    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {
        BookModel book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O livro não existe."));

        book.setName(bookRequestDto.getName());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setCategory(bookRequestDto.getCategory());
        book.setGenre(bookRequestDto.getGenre());
        book.setNumberOfPages(bookRequestDto.getNumberOfPages());

        BookModel bookSaved = bookRepository.save(book);

        return bookMapper.toResponse(bookSaved);
    }

    public void deleteBook(Long id) {
        BookModel book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O livro não existe."));

        // Apaga tambem a imagem do banco
        if (book.getCover() != null) {
            try {
                Path filePath = Paths.get("uploads" + book.getCover());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // Log do erro, mas nao impede a exclusao do registro
            }
        }

        bookRepository.delete(book);
    }
}
