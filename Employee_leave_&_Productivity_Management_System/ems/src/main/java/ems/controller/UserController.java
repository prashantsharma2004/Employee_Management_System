package ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ems.dto.AuthResponse;
import ems.dto.LoginRequest;
import ems.dto.RefreshRequest;
import ems.entity.RefreshToken;
import ems.entity.User;
import ems.repository.UserRepository;
import ems.security.JwtUtil;
import ems.service.RefreshTokenService;
import ems.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
public String test() {
    return "OK";
}

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Email already registered!");
        }

        // Safety check
    if (user.getRole() == null) {
        user.setRole("EMPLOYEE");
    }

        User savedUser = userService.registerUser(user);

        return ResponseEntity.ok(savedUser);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        User user =
                userRepository.findByEmail(request.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException("Invalid Email or Password")
                        );

        // IMPORTANT FIX → Old token delete first
        refreshTokenService.deleteByUser(user);

        String accessToken =
                jwtUtil.generateToken(user.getEmail());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                new AuthResponse(
                        accessToken,
                        refreshToken.getToken(),
                        user.getRole()
                )
        );
    }

    // ================= REFRESH =================
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestBody RefreshRequest request) {

        RefreshToken token =
                refreshTokenService.verifyToken(
                        request.getRefreshToken()
                );

        User user = token.getUser();

        String accessToken =
                jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(
                new AuthResponse(
                        accessToken,
                        user.getRole(),
                        request.getRefreshToken()
                )
        );
    }

    // ================= LOGOUT =================
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        refreshTokenService.deleteByUser(user);

        return ResponseEntity.ok("Logout Success");
    }
}