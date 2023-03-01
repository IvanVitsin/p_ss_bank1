package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.service.RegistrationService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@RestController
@AllArgsConstructor
@RequestMapping("/registration")
@Tag(name = "Регистрация ", description = "Данные о регистрации пользователей")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Operation(summary = "Получить все регистрации",
            description = "Этот API возвращает список всех регистраций")
    @ApiResponse(responseCode = "200", description = "Успешно получены все регистрации")
    @GetMapping()
    public ResponseEntity<List<RegistrationDTO>> getRegistrationAll() {
        return new ResponseEntity<>(registrationService
                .getALLRegistration(),
                HttpStatus.OK);
    }

    @Operation(summary = "Добавить новую регистрацию",
            description = "Этот API позволяет добавить новую регистрацию")
    @ApiResponse(responseCode = "200", description = "Успешно добавлена новая регистрация")
    @PostMapping()
    public ResponseEntity<RegistrationDTO> addRegistration(@RequestBody
                                                           @Valid
                                                           RegistrationDTO registrationDTO) {
        return new ResponseEntity<>(registrationService
                .saveRegistration(registrationDTO),
                HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующую регистрацию",
            description = "Этот API позволяет обновить существующую регистрацию")
    @ApiResponse(responseCode = "200", description = "Регистрация успешно обновлена")
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationDTO> updateRegistration(@RequestBody @Valid RegistrationDTO registrationDTO,
                                                              @PathVariable("id")
                                                              @Parameter(description = "Идентификатор регистрации ID")
                                                              Long id) {
        return new ResponseEntity<>(registrationService
                .update(registrationDTO, id),
                HttpStatus.OK);

    }

    @Operation(summary = "Удалить регистрацию по ID",
            description = "Этот API позволяет удалить  существующую регистрацию по ID")
    @ApiResponse(responseCode = "200", description = "Регистрация успешно удалена")
    @DeleteMapping(value = "/{id}",produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> deleteRegistration(@PathVariable("id")
                                                     @Parameter(description = "Идентификатор регистрации ID")
                                                     Long id) {
        registrationService.delete(id);
        return new ResponseEntity<>("Данные успешно удалены",
                HttpStatus.OK);
    }
}
