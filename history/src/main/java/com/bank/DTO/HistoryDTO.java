package com.bank.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    private Long id;
    private Long transfer_audit_id;
    private Long profile_audit_id;
    private Long account_audit_id;
    private Long anti_fraud_audit_id;
    private Long public_bank_info_audit_id;
    private Long authorization_audit_id;
}
