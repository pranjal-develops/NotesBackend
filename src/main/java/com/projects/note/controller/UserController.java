package com.projects.note.controller;

import com.projects.note.dto.AuthResponseDTO;
import com.projects.note.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public AuthResponseDTO getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
