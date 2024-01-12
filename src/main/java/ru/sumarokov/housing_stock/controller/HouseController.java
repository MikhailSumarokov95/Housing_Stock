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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sumarokov.housing_stock.dto.HouseDto;
import ru.sumarokov.housing_stock.entity.House;
import ru.sumarokov.housing_stock.entity.User;
import ru.sumarokov.housing_stock.service.HouseService;
import ru.sumarokov.housing_stock.service.UserService;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping("/house")
@Tag(name = "house", description = "The house API")
public class HouseController {

    private final HouseService houseService;
    private final UserService userService;

    @Autowired
    public HouseController(HouseService houseService,
                           UserService userService) {
        this.houseService = houseService;
        this.userService = userService;
    }

    @Operation(summary = "Get house", tags = "house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "House received"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = "/{houseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HouseDto> getHouse(@PathVariable Long houseId) {
        HouseDto houseDto = HouseDto.toDto(houseService.getHouse(houseId));
        return ResponseEntity.ok().body(houseDto);
    }

    @Operation(summary = "Create house", tags = "house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "House created"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HouseDto> createHouse(Principal principal,
                                                @RequestBody @Valid HouseDto houseDto) {
        User user = userService.getUser(principal);
        House house = houseService.createHouse(houseDto.toEntity(), user.getId());
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(String.format("/task/%d", house.getId()))
                .toUriString());
        return ResponseEntity.created(uri).body(HouseDto.toDto(house));
    }

    @Operation(summary = "Update house", tags = "house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "House updated"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HouseDto> updateHouse(Principal principal,
                                                @RequestBody @Valid HouseDto houseDto) {
        User user = userService.getUser(principal);
        House house = houseService.updateHouse(houseDto.toEntity(), user.getId());
        return ResponseEntity.accepted().body(HouseDto.toDto(house));
    }

    @Operation(summary = "Delete house", tags = "house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "House deleted"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @DeleteMapping("/{houseId}")
    public ResponseEntity<Void> deleteHouse(Principal principal,
                                            @PathVariable Long houseId) {
        User user = userService.getUser(principal);
        houseService.deleteHouse(houseId, user.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add tenant to house", tags = "house")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Tenant added"),
            @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @PutMapping("/add-tenant-to-house")
    public ResponseEntity<Void> addTenantToHouse(Principal principal,
                                                 @RequestParam(required = false, value = "tenant-id") Long tenantId,
                                                 @RequestParam(required = false, value = "house-id") Long houseId) {
        User user = userService.getUser(principal);
        houseService.addTenantToHouse(houseId, tenantId, user.getId());
        return ResponseEntity.noContent().build();
    }
}
