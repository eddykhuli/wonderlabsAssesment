package com.example.wonderlabassessment.controller;

import com.example.wonderlabassessment.domain.Account;
import com.example.wonderlabassessment.domain.Wrapper.AccountWrapper;
import com.example.wonderlabassessment.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by eddyT on 2023/03/20.
 */
@Controller
public class AccountController {
//    @Autowired
    private static AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * This method renders the registration form page
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String showForm(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);

        List<String> accountTypes = Arrays.asList("Savings Account", "Current Account");
        model.addAttribute("accountTypes", accountTypes);

        return "register_form";
    }

    /**
     * This method does the endpoint call
     * @param account
     * @param model
     * @return
     */
    @PostMapping("/register")
    public String submitForm(@ModelAttribute("account") Account account,Model model) {
        if((account.getAccountType().equalsIgnoreCase("Savings Account") &&  account.getTransactionAmount() < 1000)){
            model.addAttribute("errorMessage", "Deposit Amount not less than 1000 required");
            model.addAttribute("accountType",account.getAccountType());
            model.addAttribute("account",new Account());
            List<String> accountTypes = Arrays.asList("Savings Account", "Current Account");
            model.addAttribute("accountTypes", accountTypes);
            return "register_form";
        }else {
                account = accountService.openaccount(account);
                model.addAttribute("accountType",account.getAccountType());
                model.addAttribute("page","register");
                return "register_success";
        }

    }

    @GetMapping("/login")
    public String loginForm(Model model){
//        String accountNo = "";
//        String password = "";
        Account account = new Account();
        model.addAttribute("account",account);
//        model.addAttribute("passsword",password);
        return "login_page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("accountNumber") String accountNumber, @ModelAttribute("password") String password, Model model){
        Account account = accountService.login(accountNumber,password);

        if(account == null){
            model.addAttribute("errorMessage","Login failed, check credentials and try again");
            return "login_page";
        }else{
            model.addAttribute("successMessage","Login successful");
            model.addAttribute("account",account);
            model.addAttribute("page","login");
            return "register_success";
        }

    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam("accountNumber") String accountNumber,@ModelAttribute("depositAmount") double transactionAmount,Model model){

            Account account = accountService.deposit(accountNumber,transactionAmount);
            model.addAttribute("account",account);
            model.addAttribute("amount",transactionAmount);

        return "register_success";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("accountNumber") String accountNumber,@ModelAttribute("depositAmount") double transactionAmount,Model model){

        Account account = accountService.withdraw(accountNumber,transactionAmount);
        model.addAttribute("account",account);
        model.addAttribute("transactionAmount",transactionAmount);

        return "register_success";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam("senderAccountNumber") String senderAccountNumber,@ModelAttribute("receiverAccountNumber") String receiverAccountNumber,@ModelAttribute("amount") double amount,Model model){

        accountService.transferMoney(senderAccountNumber,receiverAccountNumber,amount);
        model.addAttribute("transactionAmount",amount);
        model.addAttribute("receiverAccountNumber",receiverAccountNumber);
        return "register_success";
    }




}
