package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.dto.*;
import com.acirio.virtual_bookshelf.model.UserModel;
import com.acirio.virtual_bookshelf.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários e perfil")
public class UserController {

    @Autowired
    private UserService userService;

    //=========================
    //====ROTAS DO USUARIOS====
    //=========================

    @Operation(summary = "Obter perfil do usuário autenticado", description = "Retorna os dados do perfil do usuário logado com base no token JWT.")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getLogedUser(@AuthenticationPrincipal UserModel userModel) {
        UserResponseDto userData = userService.getLogedUser(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userData);
    }

    @Operation(summary = "Atualizar perfil do usuário autenticado", description = "Atualiza os dados de perfil (nickname, nome e e-mail) do usuário logado.")
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateLogedUser(@Valid @RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal UserModel userModel) {
        UserResponseDto userResponseDto = userService.updateLogedUser(userRequestDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @Operation(summary = "Alterar senha do usuário autenticado", description = "Permite que o usuário logado altere sua própria senha informando a senha atual e a nova senha.")
    @PutMapping("/me/change-password")
    public ResponseEntity<String> changePasswordLogedUser(@RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserModel userModel) {
        userService.changePasswordLogedUser(changePasswordDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso.");
    }

    @Operation(summary = "Deletar a própria conta", description = "Exclui permanentemente a conta do usuário autenticado.")
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteLogedUser(@AuthenticationPrincipal UserModel userModel) {
        userService.deleteLogedUser(userModel.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    //==========================
    //======ROTAS DO ADMIN======
    //==========================

    @Operation(summary = "Listar todos os usuários", description = "Retorna a lista completa de usuários cadastrados com suporte a filtros e paginação. Requer permissão de administrador.")
    @GetMapping("/")
    public ResponseEntity<org.springframework.data.domain.Page<UserAdminResponseDto>> getAllUsers(@org.springdoc.core.annotations.ParameterObject UserFilterDto filter, @org.springdoc.core.annotations.ParameterObject org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Page<UserAdminResponseDto> users = userService.getAllUsers(filter, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico pelo seu ID. Requer permissão de administrador.")
    @GetMapping("/{id}")
    public ResponseEntity<UserAdminResponseDto> getUserById(@PathVariable Long id) {
        UserAdminResponseDto userResponse = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Operation(summary = "Atualizar usuário por ID", description = "Atualiza os dados de um usuário específico, incluindo a role. Requer permissão de administrador.")
    @PutMapping("/{id}")
    public ResponseEntity<UserAdminResponseDto> updateUser(@Valid @RequestBody UserAdminRequestDto userAdminRequestDto, @PathVariable Long id) {
        UserAdminResponseDto userResponse = userService.updateUser(userAdminRequestDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Operation(summary = "Alterar senha de um usuário por ID", description = "Permite que o administrador altere a senha de qualquer usuário pelo ID.")
    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePasswordLogedUser(@PathVariable Long id, @RequestBody AdminChangePasswordDto adminChangePasswordDto) {
        userService.changePasswordUser(id, adminChangePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso.");
    }

    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema. Requer permissão de administrador.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
