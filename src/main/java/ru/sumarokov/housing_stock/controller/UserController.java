package ru.sumarokov.housing_stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sumarokov.housing_stock.dto.UserDto;
import ru.sumarokov.housing_stock.entity.User;
import ru.sumarokov.housing_stock.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@Tag(name = "user", description = "The user API")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "House received"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userDto = UserDto.toDto(userService.getUser(userId));
        return ResponseEntity.ok().body(userDto);
    }

    @Operation(summary = "Update user", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User updated"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(Principal principal,
                                              @RequestBody @Valid UserDto userDto) {
        User user = userService.updateUser(userDto.toEntity(), principal);
        return ResponseEntity.accepted().body(UserDto.toDto(user));
    }

    @Operation(summary = "Delete user", tags = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteHouse(Principal principal,
                                            @PathVariable Long userId) {
        userService.deleteUser(userId, principal);
        return ResponseEntity.noContent().build();
    }
}
