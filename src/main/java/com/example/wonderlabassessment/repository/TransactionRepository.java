package com.example.wonderlabassessment.repository;

import com.example.wonderlabassessment.domain.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by eddyT on 2023/03/13.
 */
@Repository
public interface TransactionRepository extends JpaRepository<AccountTransaction, Long> {
}
