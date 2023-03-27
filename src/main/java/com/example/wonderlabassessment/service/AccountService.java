package com.example.wonderlabassessment.service;

import com.example.wonderlabassessment.domain.Account;
import com.example.wonderlabassessment.domain.AccountTransaction;
import com.example.wonderlabassessment.domain.IConstants.IConstants;
import com.example.wonderlabassessment.repository.AccountRepository;
import com.example.wonderlabassessment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by eddyT on 2023/03/13.
 */
@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public Account openaccount(Account account){

        account.setAccountNumber(generateAccountNumber());
        if(account.getAccountType().equalsIgnoreCase(IConstants.ACCOUNT_TYPE_SAVINGS)){
            account.setBalance(account.getTransactionAmount());
        }else{
            account.setOverdraftBalance(account.getOverdraftAmount());
            account.setOverDraft(IConstants.OVERDRAFT_CONCERN);
        }
            return accountRepository.save(account);
    }

    public static String generateAccountNumber(){
        int min = 0;
        int max = 9;
        StringBuilder accoutnNo = new StringBuilder("");

        int random_int =0;
        for(int x = 0; x < 10; x++){
            random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
            accoutnNo.append(random_int);
        }

        return accoutnNo.toString();
    }

    public Account login(String accountNo, String password) {
        Account account = accountRepository.findAccountByAccountNumberAndPassword(accountNo,password);
        return account;
    }

    public Account withdraw(String accountNo, double amount){
        Account account = findAccount(accountNo);
        double balance =0.0;

        if(account.getAccountType().equalsIgnoreCase(IConstants.ACCOUNT_TYPE_SAVINGS)){
            if(balance !=0.0){
               balance = account.getBalance()- amount;
                account.setBalance(balance);
            }
        }else{
            if(account.getOverDraft().equalsIgnoreCase(IConstants.OVERDRAFT_CONCERN)){
                double overdraftLimit = -account.getOverdraftAmount();
                if( account.getBalance() != overdraftLimit){
                    balance = account.getBalance() - amount;
                    account.setBalance(balance);
                }
            }else{
                if(account.getBalance() != 0.0){
                    balance = account.getBalance() - amount;
                    account.setBalance(balance);
                }
            }

//            if(account.getBalance() <1.00){
//                if(account.getOverDraft().equalsIgnoreCase(IConstants.OVERDRAFT_CONCERN)){
//                     bal = account.getOverdraftBalance() - amount;
//                    account.setOverdraftBalance(bal);
//                }
//            }else{
//                if(amount > account.getBalance()){
//                    double diff = account.getBalance() - amount;
//                    double odBal = account.getOverdraftBalance() + diff;
//                    account.setOverdraftBalance(odBal);
//                }
//                bal = account.getBalance() - amount;
//                account.setBalance(bal);
//            }
        }


        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setAccountNumber(accountNo);
        accountTransaction.setAmount(amount);
        accountTransaction.setTransactionType(IConstants.TRANSACTION_TYPE_WITHDRWAL);

        accountTransaction = transactionRepository.save(accountTransaction);
        account.getTransactionHistory().add(accountTransaction);
        account =  accountRepository.save(account);

        return account;
    }

    public Account deposit(String accountNo, double amount){
        Account account = findAccount(accountNo);
        double balance = account.getBalance()+ amount;

        if(account.getOverDraft().equalsIgnoreCase(IConstants.OVERDRAFT_CONCERN) && (account.getOverdraftBalance() < account.getOverdraftAmount()) ){
            double overdraftBalance = account.getOverdraftBalance() + amount;
            account.setOverdraftBalance(overdraftBalance);
            if(overdraftBalance > account.getOverdraftAmount()){
                double difference = overdraftBalance - account.getOverdraftAmount();
                account.setBalance(difference);
                account.setOverdraftBalance(account.getOverdraftBalance() - difference);
            }
        }else{
            account.setBalance(balance);
        }

        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setAccountNumber(accountNo);
        accountTransaction.setAmount(amount);
        accountTransaction.setTransactionType(IConstants.TRANSACTION_TYPE_DEPOSIT);

        accountTransaction = transactionRepository.save(accountTransaction);
        account.getTransactionHistory().add(accountTransaction);
       account =  accountRepository.save(account);

       return account;

    }


    public void transferMoney(String tranfererAccountNo,String receiverAccountNo, double amount){
        Account receiverAccount = findAccount(receiverAccountNo);
        Account trafererAccount = findAccount(tranfererAccountNo);

        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        trafererAccount.setBalance(trafererAccount.getBalance() - amount);

        //saving transferer transactions
        AccountTransaction senderAccountTransaction = new AccountTransaction();
        senderAccountTransaction.setAmount(amount);
        senderAccountTransaction.setAccountNumber(receiverAccountNo);
        senderAccountTransaction.setTransactionType(IConstants.TRANSACTION_TYPE_TRANFER);

        senderAccountTransaction = transactionRepository.save(senderAccountTransaction);
        trafererAccount.getTransactionHistory().add(senderAccountTransaction);
        accountRepository.save(trafererAccount);

        //saving receiver transactions
        AccountTransaction receiverAccountTransaction = new AccountTransaction();
        receiverAccountTransaction.setAmount(amount);
        receiverAccountTransaction.setAccountNumber(tranfererAccountNo);
        receiverAccountTransaction.setTransactionType(IConstants.TRANSACTION_TYPE_TRANFER);

        senderAccountTransaction = transactionRepository.save(senderAccountTransaction);
        trafererAccount.getTransactionHistory().add(senderAccountTransaction);
        accountRepository.save(receiverAccount);
    }

    public Account findAccount(String accontNo){
        return accountRepository.findAccountByAccountNumber(accontNo);
    }


}
