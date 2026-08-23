package com.projects.note.controller;

import com.projects.note.dto.AuthRequestDTO;
import com.projects.note.dto.AuthResponseDTO;
import com.projects.note.dto.LoginResponseDTO;
import com.projects.note.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<AuthResponseDTO> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public AuthResponseDTO getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDTO register(@RequestBody AuthRequestDTO request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody AuthRequestDTO request) {
        String token = userService.verify(request);
        AuthResponseDTO user = userService.getUserByUsername(request.getUsername());
        return new LoginResponseDTO(token, user);
    }
}
