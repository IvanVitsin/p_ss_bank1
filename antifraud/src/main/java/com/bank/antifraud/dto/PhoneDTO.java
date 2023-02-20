package com.bank.antifraud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PhoneDTO {
    @Schema(description = "Первичный ключ")
    @NotNull
    private Long id;
    @NotNull
    @Schema(description = "Идентификатор перевода по номеру телефона")
    private Long phoneTransferId;
    @NotNull
    @Schema(description = "заблокирован/не заблокирован")
    private boolean isBlocked;
    @NotNull
    @Schema(description = "подозрительный/не подозрительный")
    private boolean isSuspicious;

    @Schema(description = "причина блокировки")
    private String blockedReason;

    @Schema(description = "причина подозрений")
    private String suspiciousReason;
}
