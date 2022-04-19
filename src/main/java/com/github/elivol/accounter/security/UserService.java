package com.github.elivol.accounter.security;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private static final String USER_NOT_FOUND = "User whit id %d not found";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(String.format(USER_NOT_FOUND, id))
        );
    }

    public void create(User user) {
        userRepository.save(user);
    }

}
