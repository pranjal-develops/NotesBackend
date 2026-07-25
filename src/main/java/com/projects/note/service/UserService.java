package com.projects.note.service;

import com.projects.note.dto.AuthResponseDTO;
import com.projects.note.entity.User;
import com.projects.note.exception.UserNotFoundException;
import com.projects.note.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public AuthResponseDTO getUserById(Long id) {
        log.debug("Fetching user by id: {}", id);
        User user = userRepo.findById(id).orElseThrow(() -> {
            //log
            log.warn("User not found for user id: {}", id);
            return new UserNotFoundException("User not found for id: " + id);
        });
        log.debug("Found the user id={}", user.getId());
        return new AuthResponseDTO(user.getId(), user.getUsername(), user.getPfp());
    }
}
