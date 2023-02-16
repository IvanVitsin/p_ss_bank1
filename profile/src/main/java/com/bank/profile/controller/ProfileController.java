package com.bank.profile.controller;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.service.ProfileService;
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

@Api
@RestController
@AllArgsConstructor
@RequestMapping("/profile")
@Tag(name = "Профиль ", description = "Данные о профиле пользователей")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "Получить регистрацию по ID профиля",
            description = "Этот API возвращает  регистрацию данного профиля")
    @ApiResponse(responseCode = "200", description = "Успешно получена его регистрация")
    @GetMapping("/registration/{id}")
    public ResponseEntity<RegistrationDTO> getInfoRegistrationToProfile(@PathVariable("id")
                                                                        @Parameter(description = "Идентификатор профеля ID")
                                                                        Long id) {
        return new ResponseEntity<>(profileService
                .getPassportRegistration(id),
                HttpStatus.OK);
    }

    @Operation(summary = "Получение данных о пасспорте профиля",
            description = "Этот API возвращает данные о паспорте профиля")
    @ApiResponse(responseCode = "200", description = "Успешно получены данные паспорта профиля")
    @GetMapping("/passport/{id}")
    public ResponseEntity<PassportDTO> getPassportInfoToProfile(@PathVariable("id")
                                                                @Parameter(description = "Идентификатор профеля ID")
                                                                Long id) {
        return new ResponseEntity<>(profileService
                .getProfilePassport(id),
                HttpStatus.OK);
    }

    @Operation(summary = "Создание профиля с актуальной регистрацией)",
            description = "Этот API позваляет добавить новый профиль с уже заполненными паспортными данными и актуальной регистрацией ")
    @ApiResponse(responseCode = "200", description = "Успешно добавлен профиль")
    @PostMapping("/{id1}/{id2}")
    public ResponseEntity<ProfileDTO> addProfile(@RequestBody
                                                 @Valid ProfileDTO profileDTO,
                                                 @PathVariable("id1")
                                                 @Parameter(description = "Идентификатор пасспорта ID")
                                                 Long idPassport,
                                                 @PathVariable("id2")
                                                 @Parameter(description = "Идентификатор актуальной регистрации ID")
                                                 Long idRegis) {
        return new ResponseEntity<>(profileService
                .saveProfile(profileDTO, idPassport, idRegis),
                HttpStatus.OK);
    }

    @Operation(summary = "Создание профиля БЕЗ актуальной регистрацией)",
            description = "Этот API позваляет добавить новый профиль с уже заполненными паспортными данными ")
    @ApiResponse(responseCode = "200", description = "Успешно добавлен профиль")
    @PostMapping("/{id1}")
    public ResponseEntity<ProfileDTO> addProfileNoActualRegistration(@RequestBody
                                                                     @Valid ProfileDTO profileDTO,
                                                                     @PathVariable("id1")
                                                                     @Parameter(description = "Идентификатор пасспорта ID")
                                                                     Long idPassport) {
        return new ResponseEntity<>(profileService
                .saveProfileNoActualRegistration(profileDTO, idPassport),
                HttpStatus.OK);
    }

    @Operation(summary = "Обновить существующий профиль",
            description = "Этот API позволяет обновить существующий профиль")
    @ApiResponse(responseCode = "200", description = "Профиль успешно обновлен")
    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody
                                                    @Valid ProfileDTO profileDTO,
                                                    @PathVariable("id")
                                                    @Parameter(description = "Идентификатор профиля ID")
                                                    Long id) {
        profileService.update(profileDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить профиль по ID",
            description = "Этот API позволяет удалить  существующий профиль по ID")
    @ApiResponse(responseCode = "200", description = "Профиль успешно удален")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable("id")
                                                @Parameter(description = "Идентификатор профиля ID")
                                                Long id) {
        profileService.delete(id);
        return new ResponseEntity<>("Данные успешно удалены",
                HttpStatus.OK);
    }
}
