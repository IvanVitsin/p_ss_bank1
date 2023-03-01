package com.bank.profile.controller;

import com.bank.profile.dto.ActualRegistrationDTO;
import com.bank.profile.service.ActualRegistrationService;
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
@RequestMapping("/actual")
@Tag(name = "Фактическая регистрация ", description = "Данные о фактической регистрации пользователей")
public class ActualRegisController {
    private final ActualRegistrationService actualRegistrationService;

    @Operation(summary = "Получить все фактические  регистрации",
            description = "Этот API возвращает список всех фактических регистраций")
    @ApiResponse(responseCode = "200", description = "Успешно получены все фактические  регистрации")
    @GetMapping()
    public ResponseEntity<List<ActualRegistrationDTO>> getActualRegistrationAll() {
        return new ResponseEntity<>(actualRegistrationService
                .getALLActualRegistration(),
                HttpStatus.OK);
    }

    @Operation(summary = "Добавить новую фактическую  регистрацию",
            description = "Этот API позволяет добавить новую фактическую регистрацию")
    @ApiResponse(responseCode = "200", description = "Успешно добавлена новая фактическая регистрация")
    @PostMapping()
    public ResponseEntity<ActualRegistrationDTO> addActualRegistration(@RequestBody
                                                                       @Valid ActualRegistrationDTO actualRegistrationDTO) {
        return new ResponseEntity<>(actualRegistrationService
                .saveActualRegistration(actualRegistrationDTO),
                HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующую фактическую регистрацию",
            description = "Этот API позволяет обновить существующую фактическую регистрацию")
    @ApiResponse(responseCode = "200", description = "Фактическая регистрация успешно обновлена")
    @PutMapping("/{id}")
    public ResponseEntity<ActualRegistrationDTO> updateActualRegistration(@RequestBody
                                                                          @Valid ActualRegistrationDTO actualRegistrationDTO,
                                                                          @PathVariable("id")
                                                                          @Parameter(description = "Идентификатор фактической регистрации, ID")
                                                                          Long id) {
        return new ResponseEntity<>(actualRegistrationService.
                updateActualRegistration(actualRegistrationDTO, id),
                HttpStatus.OK);
    }

    @Operation(summary = "Удалить фактическую регистрацию по ID",
            description = "Этот API позволяет удалить  существующую фактическую  регистрацию по ID")
    @ApiResponse(responseCode = "200", description = "Фактическую регистрация успешно удалена")
    @DeleteMapping(value = "/{id}",produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> deleteActualRegistration(@PathVariable("id")
                                                           @Parameter(description = "Идентификатор фактической регистрации, ID")
                                                           Long id) {
        actualRegistrationService.deleteActualRegistration(id);
        return new ResponseEntity<>("Данные успешно удалены",
                HttpStatus.OK);
    }
}
