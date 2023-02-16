package com.bank.profile.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Max;

/**
 * Класс AccountDetailsDTO — это объект передачи данных (DTO),
 * представляющий детали учетной записи пользователя.
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
@Schema(description = "Информация о деталях учетной записи")
public class AccountDetailsDTO {
    @Schema(description = "Технический идентификатор счета")
    @Max(999999999)
    private Long accountId;
}
