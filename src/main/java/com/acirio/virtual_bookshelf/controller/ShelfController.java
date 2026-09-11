package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.dto.*;
import com.acirio.virtual_bookshelf.model.UserModel;
import com.acirio.virtual_bookshelf.model.enums.StatusBookEnum;
import com.acirio.virtual_bookshelf.service.ShelfService;
// Removendo o import do Swagger, o do Spring já está no import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me/shelf")
@Tag(name = "Estante (Shelf)", description = "Endpoints para gerenciamento da estante pessoal do usuário")
public class ShelfController {

    @Autowired
    private ShelfService shelfService;

    @Operation(summary = "Adicionar livro", description = "Adiciona um livro existente à estante do usuário.")
    @PostMapping("/")
    public ResponseEntity<ShelfResponseDto> addBook(@RequestBody ShelfAddRequestDto shelfAddRequestDto, @AuthenticationPrincipal UserModel userModel) {
        ShelfResponseDto shelfResponseDto = shelfService.addBook(shelfAddRequestDto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(shelfResponseDto);
    }

    @Operation(summary = "Listar estante", description = "Retorna os livros na estante do usuário, com suporte a paginação e filtros (ex: status).")
    @GetMapping("/")
    public ResponseEntity<Page<ShelfSummaryDto>> getBooks(@ParameterObject BookFilter bookFilter, StatusBookEnum status, @ParameterObject Pageable pageable, @AuthenticationPrincipal UserModel userModel) {
        Page<ShelfSummaryDto> pageResult = shelfService.getBooks(bookFilter, pageable, status, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    @Operation(summary = "Detalhes do item", description = "Retorna os detalhes completos de um livro específico na estante do usuário.")
    @GetMapping("/{id}")
    public ResponseEntity<ShelfResponseDto> getShelfItem(@PathVariable Long id, @AuthenticationPrincipal UserModel userModel) {
        ShelfResponseDto shelfResponseDto = shelfService.getShelfItemById(id, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(shelfResponseDto);
    }

    @Operation(summary = "Atualizar status de leitura", description = "Altera o status de leitura de um livro (ex: Lendo, Concluído).")
    @PutMapping("/{id}")
    public ResponseEntity<ShelfResponseDto> updateShelfItem(@PathVariable Long id, @RequestBody ShelfUpdateReadingStatusDto status, @AuthenticationPrincipal UserModel userModel) {
        ShelfResponseDto shelfResponseDto = shelfService.updateReadingStatus(id, status, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(shelfResponseDto);
    }

    @Operation(summary = "Atualizar progresso", description = "Atualiza a página atual que o usuário está lendo.")
    @PutMapping("/{id}/progress")
    public ResponseEntity<ShelfResponseDto> updateProgress(@PathVariable Long id, @RequestBody ShelfUpdateProgressDto progress, @AuthenticationPrincipal UserModel userModel) {
        ShelfResponseDto shelfResponseDto = shelfService.updateProgressStatus(id, progress, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(shelfResponseDto);
    }


    @Operation(summary = "Avaliar livro", description = "Atribui uma nota (1 a 5) a um livro da estante.")
    @PutMapping("/{id}/rating")
    public ResponseEntity<ShelfResponseDto> updateRating(@PathVariable Long id, @RequestBody ShelfUpdateNoteDto note, @AuthenticationPrincipal UserModel userModel) {
        ShelfResponseDto shelfResponseDto = shelfService.updateNote(id, note, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(shelfResponseDto);
    }

    @Operation(summary = "Remover livro", description = "Remove um livro da estante do usuário.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelfItem(@PathVariable Long id, @AuthenticationPrincipal UserModel userModel) {
        shelfService.deleteShelfItem(id, userModel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
