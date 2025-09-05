package com.insurance.manage.auth.service;

import com.insurance.manage.auth.dto.AuthResponse;
import com.insurance.manage.auth.dto.LoginRequest;
import com.insurance.manage.auth.dto.RegisterRequest;
import com.insurance.manage.auth.model.User;
import com.insurance.manage.auth.repository.UserRepository;
import com.insurance.manage.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String register(RegisterRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return "User registration successfully.";
    }
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if(user == null || !encoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid username or password.");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        return  new AuthResponse(token);
    }
}
