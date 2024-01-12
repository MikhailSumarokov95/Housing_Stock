package ru.sumarokov.housing_stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.sumarokov.housing_stock.config.JwtService;
import ru.sumarokov.housing_stock.dto.TokenDto;
import ru.sumarokov.housing_stock.entity.User;
import ru.sumarokov.housing_stock.exception.EntityNotFoundException;
import ru.sumarokov.housing_stock.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager,
                                 UserService userService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public TokenDto register(User user) {
        if (userRepository.existsByName(user.getName())) {
            throw new IllegalArgumentException("User is already registered");
        }
        User newUser = userService.createdUser(user);
        String jwtToken = jwtService.generateToken(newUser);
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
