package com.bank.antifraud.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditDTO implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private String entityType;
    @NotNull
    private String operationType;
    @NotNull
    private String createdBy;
    private String modifiedBy;
    @NotNull
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;
    private String newEntityJson;
    @NotNull
    private String entityJson;
}
