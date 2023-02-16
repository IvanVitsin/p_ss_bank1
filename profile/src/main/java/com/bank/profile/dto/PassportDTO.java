package com.bank.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Класс PassportDTO — это объект передачи данных (DTO),
 * представляющий паспорт пользователя.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация о паспорте")
public class PassportDTO {
    @Schema(description = "Серия")
    @Max(99999999)
    private Integer series;
    @Schema(description = "Номер")
    @Max(99999999)
    private Long number;
    @Schema(description = "Фамилия")
    @Size(max = 50)
    private String lastName;
    @Schema(description = "Имя")
    @Size(max = 50)
    private String firstName;
    @NotNull
    @Schema(description = "Отчество")
    @NotBlank
    @Size(min = 1, max = 30)
    private String middleName;
    @Schema(description = "Пол")
    @Size(max = 3)
    private String gender;
    @Schema(description = "Дата рождения")
    private LocalDate birthDate;
    @Schema(description = "Место рождения")
    @Size(max = 50)
    private String birthPlace;
    @Schema(description = "Кем выдан")
    @Size(max = 50)
    private String issuedBy;
    @Schema(description = "Дата выдачи")
    private LocalDate dateOfIssue;
    @Schema(description = "Код подразделения")
    @Max(99999999)
    private Integer divisionCode;
    @NotNull
    @Schema(description = "Срок действия")
    private LocalDate expirationDate;
}
