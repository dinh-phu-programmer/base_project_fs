package com.samsung.project.service.user;

import com.samsung.project.model.User;
import com.samsung.project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void auth(String username, String password) {

        if (username == null || password == null) {
            throw new BadCredentialsException("Invalid user or password!");
        }

        User user = userRepo.findUserByUsername(username);
        if (user != null) {
            boolean isMatch = passwordEncoder.matches(password, user.getPassword());
            if (!isMatch)
                throw new BadCredentialsException("Bad Credentials");
        } else {
            throw new BadCredentialsException("Bad Credentials!");
        }
    }
}
