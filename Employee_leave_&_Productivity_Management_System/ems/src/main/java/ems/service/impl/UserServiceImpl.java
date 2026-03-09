package ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ems.entity.User;
import ems.repository.UserRepository;
import ems.service.UserService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
private PasswordEncoder encoder;

    // REGISTER
    @Override
    public User registerUser(User user) {

        //agar role nahi diya to default employee maan lo
        if(user.getRole() == null|| user.getRole().isEmpty()) {
            user.setRole("EMPLOYEE"); // default
        }
       
        // Check if email exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        // Encrypt password
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // LOGIN
    @Override
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                new RuntimeException("Invalid Email!")
            );

        // Password Match
        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid Password!");
        }

        return user; // Login Success
    }
}