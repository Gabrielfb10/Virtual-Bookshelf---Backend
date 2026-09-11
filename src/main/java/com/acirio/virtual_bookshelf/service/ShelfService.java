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
import com.acirio.virtual_bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.acirio.virtual_bookshelf.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShelfService {
    @Autowired
    UserBookRepository userBookRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBookMapper userBookMapper;
    @Autowired
    private BookMapper bookMapper;

    public ShelfResponseDto addBook(ShelfAddRequestDto shelfAddRequestDto, UserModel userModel) {
        BookModel bookModel = bookRepository.findById(shelfAddRequestDto.getBookId()).orElseThrow(() -> new ResourceNotFoundException("O livro não foi encontrado."));
        if(userBookRepository.existsByBookAndUser(bookModel, userModel)) {
            throw new ConflictException("Este livro já está na sua estante.");
        }

        UserBookModel userBookModel = userBookMapper.toEntity(bookModel, userModel, LocalDateTime.now());
        UserBookModel saved = userBookRepository.save(userBookModel);

        return userBookMapper.toResponse(saved);
    }

    public Page<ShelfResponseDto> getBooks(BookFilter filter, Pageable pageable, StatusBookEnum status, UserModel userModel) {
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
        return resultPage.map(userBookMapper::toResponse);
    }

    public ShelfResponseDto getShelfItemById(Long id, UserModel userModel) {
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));

        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new UnauthorizedException("Esse livro não está na sua estante.");
        }

        return userBookMapper.toResponse(userBookModel);
    }

    public ShelfResponseDto updateReadingStatus(Long id, ShelfUpdateReadingStatusDto status, UserModel userModel){
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));


        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new UnauthorizedException("Esse livro não está na sua estante.");
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

        // Se marcou como completo e nao tinha lido todas as paginas, ganha XP pelo resto
        if (status.getStatus() == StatusBookEnum.COMPLETED && userBookModel.getCurrentPage() < userBookModel.getBook().getNumberOfPages()) {

            // Calula o delta de paginas lidas
            int pagesRead = userBookModel.getBook().getNumberOfPages() - userBookModel.getCurrentPage();

            // Atualiza a pagina atual para a ultima
            userBookModel.setCurrentPage(userBookModel.getBook().getNumberOfPages());

            // Atualiza a porcentagem de progresso para 100
            userBookModel.setPercentageRead(100);

            addExperienceAndSave(userModel, pagesRead);
        }

        UserBookModel saved = userBookRepository.save(userBookModel);
        return userBookMapper.toResponse(saved);
    }

    public ShelfResponseDto updateProgressStatus(Long id, ShelfUpdateProgressDto currentPage, UserModel userModel){
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));


        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new UnauthorizedException("Esse livro não está na sua estante.");
        }

        int oldPage = userBookModel.getCurrentPage();
        int newPage = currentPage.getCurrentPage();

        //  Checa se o input corresponde com uma quantidade de paginas que o livro realmente tem
        if(newPage > userBookModel.getBook().getNumberOfPages()){
            newPage = userBookModel.getBook().getNumberOfPages();
        }

        // Calcular quantas pagina foram lidas
        int pagesRead = newPage - oldPage;

        userBookModel.setCurrentPage(newPage);

        // Calculando a porcentagem (usando double para não dar divisão inteira zero)
        int percentage = (int) (((double) newPage / userBookModel.getBook().getNumberOfPages()) * 100);
        userBookModel.setPercentageRead(percentage);

        // Se o novo progresso corresponder ao fim do livro, automaticamente ele é colocado como compeltado
        if(newPage == userBookModel.getBook().getNumberOfPages()){
            userBookModel.setStatus(StatusBookEnum.COMPLETED);
        }

        // Caso o progresso de pagina seja feito em um livro que estava como "Quero ler", seta ele como "Lendo"
        if(newPage>0 && userBookModel.getStatus() == StatusBookEnum.WANT_TO_READ) {
            userBookModel.setStatus(StatusBookEnum.READING);
        }

        // Contrabiliza o progresso do usuario
        if (pagesRead > 0) {
            addExperienceAndSave(userModel, pagesRead);
        }

        UserBookModel saved = userBookRepository.save(userBookModel);
        return userBookMapper.toResponse(saved);
    }

    public ShelfResponseDto updateNote(Long id, ShelfUpdateNoteDto note, UserModel userModel){
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));


        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new UnauthorizedException("Esse livro não está na sua estante.");
        }

        // Caso a pessoa tente avaliar um livro que ainda quer ler, atualia o status para lendo (pelo menos lendo tem que estar para avaliar)
        if(userBookModel.getStatus() == StatusBookEnum.WANT_TO_READ) {
            userBookModel.setStatus(StatusBookEnum.READING);
        }

        userBookModel.setNote(note.getNote());
        UserBookModel saved = userBookRepository.save(userBookModel);
        return userBookMapper.toResponse(saved);
    }

    public void deleteShelfItem(Long id, UserModel userModel) {
        UserBookModel userBookModel = userBookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Esse registro não existe na sua estante."));

        if(!userBookModel.getUser().getId().equals(userModel.getId())) {
            throw new UnauthorizedException("Esse livro não está na sua estante.");
        }

        userBookRepository.delete(userBookModel);
    }

    private void addExperienceAndSave(UserModel user, int pagesRead) {
        if (pagesRead <= 0) return;

        user.setPagesRead(user.getPagesRead() + pagesRead);
        int xp = user.getExperience() + pagesRead;
        int level = user.getLevel();

        while (true) {
            int requiredXp = 10 + (level - 1) * 2;
            if (xp >= requiredXp) {
                xp -= requiredXp;
                level++;
            } else {
                break;
            }
        }

        user.setExperience(xp);
        user.setLevel(level);
        userRepository.save(user);
    }
}
