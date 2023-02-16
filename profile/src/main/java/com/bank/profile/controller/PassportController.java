package com.bank.profile.controller;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.service.PassportService;
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
@RequestMapping("/passport")
@Tag(name = "Пасспорт", description = "Данные о паспортах пользователей")
public class PassportController {
    private PassportService passportService;

    @Operation(summary = "Получить все паспорта",
            description = "Этот API возвращает список всех паспортов")
    @ApiResponse(responseCode = "200", description = "Успешно получены все паспорта")
    @GetMapping()
    public ResponseEntity<List<PassportDTO>> getPassportAll() {
        return new ResponseEntity<>(passportService
                .getALLPassport(),
                HttpStatus.OK);
    }

    @Operation(summary = "Добавить новый паспорт",
            description = "Этот API позволяет добавить новый паспорт с уже до этого заполненной регистрацией")
    @ApiResponse(responseCode = "200", description = "Успешно добавлен новый паспорт")
    @PostMapping("/{id}")
    public ResponseEntity<PassportDTO> addPassport(@RequestBody
                                                   @Valid PassportDTO passportDTO,
                                                   @PathVariable("id")
                                                   @Parameter(description = "Идентификатор регистрации ID")
                                                   Long id) {
        return new ResponseEntity<>(passportService
                .savePassport(passportDTO, id),
                HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующий паспорт",
            description = "Этот API позволяет обновить существующий паспорт по ID")
    @ApiResponse(responseCode = "200", description = "Паспорт успешно обновлен")
    @PutMapping("/{id}")
    public ResponseEntity<PassportDTO> updatePassport(@RequestBody
                                                      @Valid PassportDTO passportDTO,
                                                      @PathVariable("id")
                                                      @Parameter(description = "Идентификатор паспорта ID")
                                                      Long id) {
        return new ResponseEntity<>(passportService
                .update(passportDTO, id),
                HttpStatus.OK);

    }

    @Operation(summary = "Удалить паспорт по ID",
            description = "Этот API позволяет удалить  существующий паспорт  по ID")
    @ApiResponse(responseCode = "200", description = "Паспорт успешно удален")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePassport(@PathVariable("id")
                                                 @Parameter(description = "Идентификатор паспорта ID")
                                                 Long id) {
        passportService.delete(id);
        return new ResponseEntity<>("Данные успешно удалены",
                HttpStatus.OK);
    }
}
