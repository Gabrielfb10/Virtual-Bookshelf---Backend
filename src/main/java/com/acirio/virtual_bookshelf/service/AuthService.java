package com.acirio.virtual_bookshelf.service;

import com.acirio.virtual_bookshelf.config.TokenProvider;
import com.acirio.virtual_bookshelf.dto.UserLoginRequestDto;
import com.acirio.virtual_bookshelf.dto.UserRegisterRequestDto;
import com.acirio.virtual_bookshelf.exception.ConflictException;
import com.acirio.virtual_bookshelf.exception.UnauthorizedException;
import com.acirio.virtual_bookshelf.mapper.UserMapper;
import com.acirio.virtual_bookshelf.model.UserModel;
import com.acirio.virtual_bookshelf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenProvider tokenProvider;

    public UserModel register(UserRegisterRequestDto userRegisterRequestDto) {
        UserModel userEmail = userRepository.findByEmail(userRegisterRequestDto.getEmail()).orElse(null);
        UserModel userUsername = userRepository.findByUsername(userRegisterRequestDto.getUsername()).orElse(null);

        if(userEmail != null) {
            throw new ConflictException("Já existe um usuário com esse email.");
        }

        if(userUsername != null) {
            throw new ConflictException("Já existe um usuário com esse username.");
        }

        UserModel userRegister = userMapper.toEntity(userRegisterRequestDto);

        // Criptografa a senha
        userRegister.setPassword(passwordEncoder.encode(userRegister.getPassword()));

        UserModel userSaved = userRepository.save(userRegister);
        return userSaved;

    }

    public String login(UserLoginRequestDto userLoginRequestDto) {
        try {
            //Procurar um usuario com o email, se achar, compara a senha desse usuario com a senha passada na requisição, se estiver cetp, autentica o usuario
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword()));
            return tokenProvider.generateToken(authentication);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Credenciais inválidas.");
        }
    }
}
