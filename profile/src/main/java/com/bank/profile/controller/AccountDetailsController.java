package com.bank.profile.controller;

import com.bank.profile.dto.AccountDetailsDTO;
import com.bank.profile.service.AccountDetailsService;
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
@RequestMapping("/details")
@Tag(name = "Детали аккаунта", description = "Данные о количестве счетов у пользователя(профиль)")
public class AccountDetailsController {
    private final AccountDetailsService accountDetailsService;

    @Operation(summary = "Получить все детали аккаунта",
            description = "Этот API возвращает список всех деталей аккаунта")
    @ApiResponse(responseCode = "200", description = "Успешно получены все детали аккаунта")
    @GetMapping()
    public ResponseEntity<List<AccountDetailsDTO>> getAccountDetailsAll() {
        return new ResponseEntity<>(accountDetailsService
                .getALLAccountDetails(),
                HttpStatus.OK);
    }

    @Operation(summary = "Добавить новую деталь аккаунта",
            description = "Этот API позволяет добавить новую деталь аккаунта")
    @ApiResponse(responseCode = "200", description = "Успешно добавлена новая деталь аккаунта(счет)")
    @PostMapping("{id}")
    public ResponseEntity<AccountDetailsDTO> addAccountDetails(@RequestBody
                                                               @Valid AccountDetailsDTO accountDetailsDTO,
                                                               @PathVariable("id")
                                                               @Parameter(description = "Идентификатор профиля ID")
                                                               Long id) {
        return new ResponseEntity<>(accountDetailsService
                .saveAccountDetails(accountDetailsDTO, id),
                HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующую деталь аккаунта",
            description = "Этот API позволяет обновить существующую деталь аккаунта")
    @ApiResponse(responseCode = "200", description = "Деталь аккаунта успешно обновлена")
    @PutMapping("/{id}")
    public ResponseEntity<AccountDetailsDTO> updateAccountDetails(@RequestBody
                                                                  @Valid AccountDetailsDTO accountDetailsDTO,
                                                                  @PathVariable("id")
                                                                  @Parameter(description = "Идентификатор детали аккаунта ID")
                                                                  Long id) {
        return new ResponseEntity<>(accountDetailsService.
                updateAccountDetails(accountDetailsDTO, id),
                HttpStatus.OK);

    }

    @Operation(summary = "Удалить деталь аккаунта по ID",
            description = "Этот API позволяет удалить  существующую деталь аккаунт по ID")
    @ApiResponse(responseCode = "200", description = "Деталь аккаунта успешно удалена")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountDetails(@PathVariable("id")
                                                     @Parameter(description = "Идентификатор детали аккаунта ID")
                                                     Long id) {
        accountDetailsService.deleteAccountDetails(id);
        return ResponseEntity.ok().build();
    }
}
