package com.bank.transfer.dto;


import com.bank.transfer.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PhoneTransferDto {

    @Phone(message = "Некорректно введенный номер телефона")
    private Long phoneNumber;

    @NotNull(message = "Не указана сумма перевода")
    @Min(value = 100, message = "Сумма перевода не может быть меньше 100")
    @Max(value = 1000000, message = "Сумма перевода не может быть больше 1000000")
    private Double amount;

    private String purpose;

    @NotNull(message = "Не указан технический идентификатор банковского счета")
    private Long accountDetailsId;

}
