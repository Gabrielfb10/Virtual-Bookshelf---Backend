package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.dto.UserLoginRequestDto;
import com.acirio.virtual_bookshelf.dto.UserRegisterRequestDto;
import com.acirio.virtual_bookshelf.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {
        authService.register(userRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        String token = authService.login(userLoginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
