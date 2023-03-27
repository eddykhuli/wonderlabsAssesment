package com.example.wonderlabassessment.repository;

import com.example.wonderlabassessment.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by eddyT on 2023/03/13.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findAccountByAccountNumber(String accountNo);

    Account findAccountByAccountNumberAndPassword(String accountNo,String password);
}
