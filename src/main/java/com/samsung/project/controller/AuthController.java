package com.samsung.project.controller;

import com.samsung.project.model.User;
import com.samsung.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/user/add")
    public String helloUser() {
        throw new BadCredentialsException("AAAA");
//        return "Hello World!";
    }
}

