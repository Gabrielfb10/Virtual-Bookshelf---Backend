package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.dto.UserLoginRequestDto;
import com.acirio.virtual_bookshelf.dto.UserRegisterRequestDto;
import com.acirio.virtual_bookshelf.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Registrar novo usuário", description = "Cria uma nova conta de usuário com as credenciais informadas.")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {
        authService.register(userRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
    }

    @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna um token JWT para acesso aos endpoints protegidos.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
        String token = authService.login(userLoginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }


}
