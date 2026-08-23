package com.projects.note.service;

import com.projects.note.dto.AuthRequestDTO;
import com.projects.note.dto.AuthResponseDTO;
import com.projects.note.entity.User;
import com.projects.note.enums.Role;
import com.projects.note.exception.UserNotFoundException;
import com.projects.note.repository.UserRepo;
import com.projects.note.service.security.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AuthResponseDTO getUserById(Long id) {
        log.debug("Fetching user by id: {}", id);
        User user = userRepo.findById(id).orElseThrow(() -> {
            //log
            log.warn("User not found for user id: {}", id);
            return new UserNotFoundException("User not found for id: " + id);
        });
        log.debug("Found the user id={}", user.getId());
        return convertToResponse(user);
    }

    public List<AuthResponseDTO> getAllUsers() {
        log.debug("Fetching all users");
        return userRepo.findAll().stream().map(this::convertToResponse).toList();
    }

    public AuthResponseDTO createUser(AuthRequestDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setPfp(request.getPfp());
        user.setRole(Role.USER);
        log.info("Received pfp length: {}", request.getPfp().length());
        log.info("Received pfp head: {}", request.getPfp().substring(0, Math.min(30, request.getPfp().length())));

        return convertToResponse(userRepo.save(user));
    }

//    public AuthResponseDTO updateUser(AuthRequestDTO request, Long id) {
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(encoder.encode(request.getPassword()));
//        user.setPfp(request.getPfp());
//        user.setRole(Role.USER);
//        return convertToResponse(userRepo.save(user));
//    }

    private AuthResponseDTO convertToResponse(User user) {
        return new AuthResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getPfp()
        );
    }

    public String verify(AuthRequestDTO request) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return auth.isAuthenticated() ? jwtService.generateToken(request.getUsername()) : "Failed";
    }

    public AuthResponseDTO getUserByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        return convertToResponse(user);
    }

}
