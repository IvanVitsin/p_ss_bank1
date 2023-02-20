package com.bank.antifraud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "информация о подозрительном переводе по аккаунту")
public class AccountDTO {

    @Schema(description = "Первичный ключ")
    @NotNull
    private Long id;
    @NotNull
    @Schema(description = "Идентификатор перевода по аккаунту")
    private Long accountTransferId;
    @NotNull
    @Schema(description = "Заблокирован/не заблокирован")
    private boolean isBlocked;
    @NotNull
    @Schema(description = "Подозрительный/не подозрительный")
    private boolean isSuspicious;

    @Schema(description = "Причина блокировки")
    private String blockedReason;

    @Schema(description = "Причина подозрений")
    private String suspiciousReason;

}
