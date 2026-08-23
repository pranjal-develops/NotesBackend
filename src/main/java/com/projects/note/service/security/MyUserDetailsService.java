package com.projects.note.service.security;

import com.projects.note.entity.User;
import com.projects.note.entity.UserPrinciples;
import com.projects.note.exception.UserNotFoundException;
import com.projects.note.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username).orElseThrow(() -> {
            log.warn("Username {} not found", username);
            return new UserNotFoundException("Username " + username + " not found");
        });
        return new UserPrinciples(user);
    }
}
