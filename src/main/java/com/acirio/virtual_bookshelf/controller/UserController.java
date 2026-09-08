package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.model.UserModel;
import com.acirio.virtual_bookshelf.dto.UserResponseDto;
import com.acirio.virtual_bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getLogedUser(@AuthenticationPrincipal UserModel userModel) {
        UserResponseDto userData = userService.getLogedUser(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userData);
    }

    //PUT/users/me;
    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateLogedUser(@Valid @RequestBody UserResponseDto userResponseDto, @AuthenticationPrincipal UserModel userModel) {
        UserResponseDto userResponseDto = userService.updateLogedUser()
    }
    
    //DELETE/users/me (pode deletar apenas a si mesmo)
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteLogedUser(@AuthenticationPrincipal UserModel userModel) {
        userService.deleteLogedUsr(userModel.getId());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso.");
    }
}
