package com.bank.profile.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;
/**
 * Класс ProfileDTO — это объект передачи данных (DTO),
 * представляющий профиль пользователя.
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
@Schema(description = "Информация о профиле")
public class ProfileDTO {

    @Schema(description = "Hомер телефона, без +7")
    @Max(100000000)
    private Long phoneNumber;
    @NotNull
    @Schema(description = "Email")
    @NotBlank
    @Size(min = 1, max = 40)
    private String email;
    @NotNull
    @Schema(description = "Имя на пластиковой карте")
    @NotBlank
    @Size(min = 1, max = 40)
    private String nameOnCard;
    @NotNull
    @Schema(description = "ИНН")
    @Min(7)
    @Max(999999999)
    private Long inn;
    @NotNull
    @Schema(description = "Снилс")
    @Min(5)
    @Max(99999999)
    private Long snils;
}
