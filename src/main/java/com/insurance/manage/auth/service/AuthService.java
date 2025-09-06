package com.insurance.manage.auth.service;

import com.insurance.manage.auth.dto.AuthResponse;
import com.insurance.manage.auth.dto.LoginRequest;
import com.insurance.manage.auth.dto.RegisterRequest;
import com.insurance.manage.auth.model.User;
import com.insurance.manage.auth.repository.UserRepository;
import com.insurance.manage.auth.security.JwtUtil;
import com.insurance.manage.auth.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request){
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .enabled(true)
                .build();
        userRepository.save(user);
        return "User registration successfully.";
    }
    
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token);
    }
}
