package com.example.wonderlabassessment.domain;

import com.example.wonderlabassessment.domain.AuditEntity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Created by eddyT on 2023/03/13.
 */
@Entity
@Data
public class AccountTransaction extends BaseEntity {
    private String transactionType;
    private double amount;
    private String accountNumber;
}
