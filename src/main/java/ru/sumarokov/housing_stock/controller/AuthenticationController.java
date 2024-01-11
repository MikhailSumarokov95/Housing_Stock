package ru.sumarokov.housing_stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sumarokov.housing_stock.dto.TokenDto;
import ru.sumarokov.housing_stock.dto.UserAuthenticateInfoDto;
import ru.sumarokov.housing_stock.dto.UserRegistrationInfoDto;
import ru.sumarokov.housing_stock.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth", description = "The auth API")
public class AuthenticationController {

    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @Operation(summary = "Register", tags = "auth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered"),
    })
    @PostMapping("/register")
    public ResponseEntity<TokenDto> register(@RequestBody UserRegistrationInfoDto userRegistrationInfoDto) {
        return ResponseEntity.ok(service.register(userRegistrationInfoDto.toEntity()));
    }

    @Operation(summary = "Authenticate", tags = "auth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authenticate(@RequestBody UserAuthenticateInfoDto userRegistrationInfoDto) {
        return ResponseEntity.ok(service.authenticate(userRegistrationInfoDto.toEntity()));
    }
}
