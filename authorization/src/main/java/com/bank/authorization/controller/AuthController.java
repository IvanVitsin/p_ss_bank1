package com.bank.authorization.controller;

import com.bank.authorization.dto.AuthDto;
import com.bank.authorization.entity.Auth;
import com.bank.authorization.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллеры для обработки запросов
 * @author Zhukova Ekaterina
 * @version 1.0
 * @since 15.02.2023
 */

@RestController
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/add")
    public ResponseEntity<AuthDto> createAuth(@RequestBody Auth auth) {
        AuthDto savedAuth = authService.createAuth(auth);
        return new ResponseEntity<>(savedAuth, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthDto> getUserById(@PathVariable("id") Long id) {
        AuthDto authDto = authService.getUserById(id);
        return new ResponseEntity<>(authDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthDto>> getAllRoles() {
        List<AuthDto> roles = authService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthDto> updateAuth(@PathVariable("id") Long id, @RequestBody AuthDto authDto) {
        authDto.setProfile_id(id);
        AuthDto updatedAuth = authService.updateAuth(authDto);
        return new ResponseEntity<>(updatedAuth, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAuth(@PathVariable("id") Long id) {
        authService.deleteAuth(id);
        return new ResponseEntity<>("Auth deleted!", HttpStatus.OK);
    }
}
