package com.example.wonderlabassessment.domain.Wrapper;

import com.example.wonderlabassessment.domain.AccountTransaction;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by zandilemt on 2023/03/22.
 */
@Data
public class AccountWrapper {
    private String accountType;
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String overDraft;
    private double overdraftAmount;
    private double overdraftBalance;
    private List<AccountTransaction> transactionHistory;
}
