package src.main.java.com.bank.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "history", schema = "history")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long transfer_audit_id;
    private Long profile_audit_id;
    private Long account_audit_id;
    private Long anti_fraud_audit_id;
    private Long public_bank_info_audit_id;
    private Long authorization_audit_id;

}