package com.acirio.virtual_bookshelf.service;

import com.acirio.virtual_bookshelf.repository.UserRepository;
import com.nimbusds.jose.util.Resource;
import com.acirio.virtual_bookshelf.dto.UserResponseDto;
import com.acirio.virtual_bookshelf.exception.ResourceNotFoundException;
import com.acirio.virtual_bookshelf.mapper.UserMapper;
import com.acirio.virtual_bookshelf.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private UserMapper userMapper;

    public UserResponseDto getLogedUser(Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        return userMapper.toResponse(user);
    }
}
