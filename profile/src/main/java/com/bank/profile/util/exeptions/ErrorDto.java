package com.bank.profile.util.exeptions;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * ErrorDto — это объект передачи данных, который используется для хранения информации об ошибках.
 * <p>
 * Этот класс использует аннотации ломбока @Data, @Builder, @NoArgsConstructor и @AllArgsConstructor.
 * для автоматического создания геттеров, сеттеров, построителей и конструкторов для класса.
 * Поля:
 * error - строка, представляющая сообщение об ошибке
 * errorDescription — строка с более подробным описанием ошибки.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
}

