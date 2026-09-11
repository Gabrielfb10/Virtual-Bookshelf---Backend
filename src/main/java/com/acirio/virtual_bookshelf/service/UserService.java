package com.acirio.virtual_bookshelf.service;

import com.acirio.virtual_bookshelf.dto.*;
import com.acirio.virtual_bookshelf.exception.ConflictException;
import com.acirio.virtual_bookshelf.repository.UserRepository;
import com.acirio.virtual_bookshelf.exception.ResourceNotFoundException;
import com.acirio.virtual_bookshelf.mapper.UserMapper;
import com.acirio.virtual_bookshelf.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto getLogedUser(UserModel userLoged) {
        return userMapper.toResponse(userLoged);
    }

    public UserResponseDto updateLogedUser(UserRequestDto userRequestDto, UserModel userLoged) {
        UserModel user = userRepository.findById(userLoged.getId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        if(userRepository.existsByNickname(userRequestDto.getNickname()) && !userRequestDto.getNickname().equals(user.getNickname()) ) {
            throw new ConflictException("Já existe um usuário com esse nickname.");
        }

        if(userRepository.existsByEmail(userRequestDto.getEmail()) && !userRequestDto.getEmail().equals(user.getEmail())) {
            throw new ConflictException("Já existe um usuário com esse email.");
        }

        user.setNickname(userRequestDto.getNickname());
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());

        UserModel userSaved = userRepository.save(user);
        return userMapper.toResponse(userSaved);
    }

    public void changePasswordLogedUser(ChangePasswordDto changePasswordDto, UserModel logedUser) {
        UserModel user = userRepository.findById(logedUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        
        if(!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), logedUser.getPassword())) {
            throw new BadCredentialsException("Senha errada.");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteLogedUser(Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        userRepository.delete(user);
        return;
    }

    public Page<UserAdminResponseDto> getAllUsers(UserFilterDto filter, Pageable pageable) {
        UserModel probe = userMapper.toEntity(filter);
        org.springframework.data.domain.Example<UserModel> example = org.springframework.data.domain.Example.of(probe, org.springframework.data.domain.ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnorePaths("level", "experience", "pagesRead")
                .withIgnoreNullValues());

        org.springframework.data.domain.Page<UserModel> usersPage = userRepository.findAll(example, pageable);
        return usersPage.map(userMapper::toAdminResponse);
    }

    public UserAdminResponseDto getUserById(Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        return userMapper.toAdminResponse(user);
    }

    public UserAdminResponseDto updateUser(UserAdminRequestDto userAdminRequestDto, Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        if(userRepository.existsByNickname(userAdminRequestDto.getNickname()) && !userAdminRequestDto.getNickname().equals(user.getNickname()) ) {
            throw new ConflictException("Já existe um usuário com esse nickname.");
        }

        if(userRepository.existsByEmail(userAdminRequestDto.getEmail()) && !userAdminRequestDto.getEmail().equals(user.getEmail())) {
            throw new ConflictException("Já existe um usuário com esse email.");
        }

        user.setNickname(userAdminRequestDto.getNickname());
        user.setName(userAdminRequestDto.getName());
        user.setEmail(userAdminRequestDto.getEmail());
        user.setRole(userAdminRequestDto.getRole());

        UserModel userSaved = userRepository.save(user);
        return userMapper.toAdminResponse(userSaved);
    }

    public void changePasswordUser(Long id, AdminChangePasswordDto adminChangePasswordDto) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        
        user.setPassword(passwordEncoder.encode(adminChangePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        userRepository.delete(user);
        return;
    }
}
