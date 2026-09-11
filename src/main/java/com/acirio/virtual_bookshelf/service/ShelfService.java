package com.acirio.virtual_bookshelf.service;

import com.acirio.virtual_bookshelf.dto.*;
import com.acirio.virtual_bookshelf.exception.ConflictException;
import com.acirio.virtual_bookshelf.exception.ResourceNotFoundException;
import com.acirio.virtual_bookshelf.mapper.BookMapper;
import com.acirio.virtual_bookshelf.mapper.UserBookMapper;
import com.acirio.virtual_bookshelf.model.BookModel;
import com.acirio.virtual_bookshelf.model.UserBookModel;
import com.acirio.virtual_bookshelf.model.UserModel;
import com.acirio.virtual_bookshelf.model.enums.StatusBookEnum;
import com.acirio.virtual_bookshelf.repository.BookRepository;
import com.acirio.virtual_bookshelf.repository.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShelfService {
    @Autowired
    UserBookRepository userBookRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserBookMapper userBookMapper;
    @Autowired
    private BookMapper bookMapper;

//    POST	/users/me/shelf	Adicionar livro à estante

    public ShelfResponseDto addBook(ShelfAddRequestDto shelfAddRequestDto, UserModel userModel) {
        BookModel bookModel = bookRepository.findById(shelfAddRequestDto.getBookId()).orElseThrow(() -> new ResourceNotFoundException("O livro não foi encontrado."));
        if(userBookRepository.existsByBookAndUser(bookModel, userModel)) {
            throw new ConflictException("Este livro já está na sua estante.");
        }

        UserBookModel userBookModel = userBookMapper.toEntity(bookModel, userModel, LocalDateTime.now());
        UserBookModel saved = userBookRepository.save(userBookModel);

        return userBookMapper.toResponse(saved);
    }
//    GET	/users/me/shelf	Listar estante (filtro por status)

    public Page<ShelfSummaryDto> getBooks(BookFilter filter, Pageable pageable, StatusBookEnum status, UserModel userModel) {
        BookModel book = bookMapper.toEntity(filter);
        UserBookModel probe = new UserBookModel();
        probe.setBook(book);
        probe.setUser(userModel);
        probe.setStatus(status);

        Example<UserBookModel> example = Example.of(probe, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues());

        Page<UserBookModel> resultPage = userBookRepository.findAll(example, pageable);
        return resultPage.map(userBookMapper::toSummary);
    }
//    GET	/users/me/shelf/{shelfItemId}	Detalhes de um item
    public ShelfResponseDto getShelfItemById(Long id, UserModel userModel) {
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));

        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new BadCredentialsException("Esse livro não está na sua estante.");
        }

        return userBookMapper.toResponse(userBookModel);
    }
//    PUT	/users/me/shelf/{shelfItemId}	Atualizar status de leitura
    public ShelfResponseDto updateReadingStatus(Long id, ShelfUpdateReadingStatusDto status, UserModel userModel){
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));


        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new BadCredentialsException("Esse livro não está na sua estante.");
        }

        userBookModel.setStatus(status.getStatus());

        // Em caso de releitura, mantem a primeira data que comecou a leitura
        if (status.getStatus() == StatusBookEnum.READING && userBookModel.getReadingStartDate()==null) {
            userBookModel.setReadingStartDate(LocalDateTime.now());
        }

        // Em caso de releitura, mantem a primeira data que completou a leitura
        if (status.getStatus() == StatusBookEnum.COMPLETED && userBookModel.getReadingEndDate()==null) {
            userBookModel.setReadingEndDate(LocalDateTime.now());
        }

        UserBookModel saved = userBookRepository.save(userBookModel);
        return userBookMapper.toResponse(saved);
    }
//    PUT	/users/me/shelf/{shelfItemId}/progress	Atualizar progresso (página)
    public ShelfResponseDto updateProgressStatus(Long id, ShelfUpdateProgressDto currentPage, UserModel userModel){
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));


        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new BadCredentialsException("Esse livro não está na sua estante.");
        }

        userBookModel.setCurrentPage(currentPage.getCurrentPage());
        
        // Calculando a porcentagem (usando double para não dar divisão inteira zero)
        int percentage = (int) (((double) currentPage.getCurrentPage() / userBookModel.getBook().getNumberOfPages()) * 100);
        userBookModel.setPercentageRead(percentage);

        UserBookModel saved = userBookRepository.save(userBookModel);
        return userBookMapper.toResponse(saved);
    }
//    PUT	/users/me/shelf/{shelfItemId}/rating	Avaliar livro (nota 1-5)
    public ShelfResponseDto updateNote(Long id, ShelfUpdateNoteDto note, UserModel userModel){
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));


        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new BadCredentialsException("Esse livro não está na sua estante.");
        }

        userBookModel.setNote(note.getNote());
        UserBookModel saved = userBookRepository.save(userBookModel);
        return userBookMapper.toResponse(saved);
    }
//    DELETE	/users/me/shelf/{shelfItemId}	Remover livro da estante
    public void deleteShelfItem(Long id, UserModel userModel) {
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));

        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new BadCredentialsException("Esse livro não está na sua estante.");
        }

        userBookRepository.delete(userBookModel);
    }
}
