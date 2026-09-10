package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.dto.*;
import com.acirio.virtual_bookshelf.model.UserModel;
import com.acirio.virtual_bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //=========================
    //====ROTAS DO USUARIOS====
    //=========================

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getLogedUser(@AuthenticationPrincipal UserModel userModel) {
        UserResponseDto userData = userService.getLogedUser(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userData);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateLogedUser(@Valid @RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal UserModel userModel) {
        UserResponseDto userResponseDto = userService.updateLogedUser(userRequestDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @PutMapping("/me/change-password")
    public ResponseEntity<String> changePasswordLogedUser(@RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserModel userModel) {
        userService.changePasswordLogedUser(changePasswordDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso.");
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteLogedUser(@AuthenticationPrincipal UserModel userModel) {
        userService.deleteLogedUser(userModel.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso.");
    }


    //==========================
    //======ROTAS DO ADMIN======
    //==========================

    @GetMapping("/")
    public ResponseEntity<List<UserAdminResponseDto>> getAllUsers() {
        List<UserAdminResponseDto> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAdminResponseDto> getUserById(@PathVariable Long id) {
        UserAdminResponseDto userResponse = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAdminResponseDto> updateUser(@Valid @RequestBody UserAdminRequestDto userAdminRequestDto, @PathVariable Long id) {
        UserAdminResponseDto userResponse = userService.updateUser(userAdminRequestDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePasswordLogedUser(@PathVariable Long id, @RequestBody AdminChangePasswordDto adminChangePasswordDto) {
        userService.changePasswordUser(id, adminChangePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body("Senha alterada com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLogedUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso.");
    }
}
