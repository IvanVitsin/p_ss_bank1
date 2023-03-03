package com.bank.antifraud.entity;

import com.bank.antifraud.service.audit.AuditAbstract;
import com.bank.antifraud.service.audit.AuditingAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

/*Сущность связанная с таблицей в бд */

@Entity
@Table(name = "suspicious_phone_transfer", schema = "anti_fraud")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingAccount.class)
@Schema(description = "информация о подозрительных переводах по номеру телефона")

public class PhoneTransferEntity extends AuditAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_transfer_id", unique = true)
    private Long phoneTransferId;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "is_suspicious")
    private boolean isSuspicious;

    @Column(name = "blocked_reason", nullable = false)
    private String blockedReason;

    @Column(name = "suspicious_reason")
    private String suspiciousReason;
}
