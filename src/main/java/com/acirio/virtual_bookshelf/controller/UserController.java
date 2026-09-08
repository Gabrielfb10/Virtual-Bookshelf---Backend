package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.model.UserModel;
import com.acirio.virtual_bookshelf.dto.UserResponseDto;
import com.acirio.virtual_bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal UserModel user) {
        UserResponseDto userData = userService.getLogedUser(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(userData);
    }
}
