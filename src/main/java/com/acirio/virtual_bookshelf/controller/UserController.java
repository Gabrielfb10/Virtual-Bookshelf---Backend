package com.acirio.virtual_bookshelf.controller;

import com.acirio.virtual_bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
}
