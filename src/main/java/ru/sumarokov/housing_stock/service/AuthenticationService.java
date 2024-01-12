package ru.sumarokov.housing_stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sumarokov.housing_stock.config.JwtService;
import ru.sumarokov.housing_stock.dto.TokenDto;
import ru.sumarokov.housing_stock.entity.User;
import ru.sumarokov.housing_stock.exception.EntityNotFoundException;
import ru.sumarokov.housing_stock.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public TokenDto register(User user) {
        if (userRepository.existsByName(user.getName())) {
            throw new IllegalArgumentException("User is already registered");
        }
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
        String jwtToken = jwtService.generateToken(user);
        return new TokenDto(jwtToken);
    }

    public TokenDto authenticate(User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
        );
        User userAuth = userRepository.findByName(user.getName())
                .orElseThrow(() -> new EntityNotFoundException(User.class, user.getName()));
        String jwtToken = jwtService.generateToken(userAuth);
        return new TokenDto(jwtToken);
    }
}
