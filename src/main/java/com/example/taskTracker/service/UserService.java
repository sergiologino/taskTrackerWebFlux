package com.example.taskTracker.service;

import com.example.taskTracker.model.RoleType;
import com.example.taskTracker.model.User;
import com.example.taskTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Flux<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> createUser(User user) {


        //  роль по умолчанию
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(RoleType.ROLE_USER));
        }


        return userRepository.save(user);
    }

    public Mono<User> updateUser(String id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setEmail(user.getEmail());
                    return userRepository.save(existingUser);
                });
    }

    public Mono<Void> deleteUser(String id) {
        return userRepository.deleteById(id);
    }
}

