package com.example.wonderlabassessment.domain;

import com.example.wonderlabassessment.domain.AuditEntity.BaseEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by eddyT on 2023/03/13.
 */
@Entity
@Data
public class Account extends BaseEntity
{
    private String accountType;
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String overDraft;
    private double transactionAmount;
    private double overdraftAmount;
    private double overdraftBalance;
    private String password;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AccountTransaction> transactionHistory;
}
