package com.bank.profile.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;
/**
 * Класс RegistrationDTO — это объект передачи данных (DTO),
 * представляющий регистрацию пользователя.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Информация о регистрации")
public class RegistrationDTO {
    @Schema(description = "Страна")
    @Size(max = 166)
    private String country;
    @NotNull
    @Schema(description = "Регион")
    @NotBlank
    @Size(min = 1, max = 160)
    private String region;
    @NotNull
    @Schema(description = "Город")
    @NotBlank
    @Size(min = 1, max = 160)
    private String city;
    @NotNull
    @Schema(description = "Район")
    @NotBlank
    @Size(min = 1, max = 160)
    private String district;
    @NotNull
    @Schema(description = "Населенный пункт")
    @NotBlank
    @Size(min = 1, max = 230)
    private String locality;
    @NotNull
    @Schema(description = "Улица")
    @NotBlank
    @Size(min = 1, max = 230)
    private String street;
    @NotNull
    @Schema(description = "Номер дома")
    @NotBlank
    @Size(min = 1, max = 20)
    private String houseNumber;
    @NotNull
    @Schema(description = "Корпус")
    @NotBlank
    @Size(min = 1, max = 20)
    private String houseBlock;
    @NotNull
    @Schema(description = "Номер квартиры")
    @NotBlank
    @Size(min = 1, max = 40)
    private String flatNumber;
    @Schema(description = "Индекс")
    @Max(999999999)
    private Long index;
}
