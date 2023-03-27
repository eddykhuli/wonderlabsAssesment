package com.example.wonderlabassessment;

import com.example.wonderlabassessment.domain.Account;
import com.example.wonderlabassessment.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class WonderlabassessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(WonderlabassessmentApplication.class, args);

//		System.out.println(accountService.generateAccountNumber());
//		openaccount();
	}

	public static void openaccount(){
		Scanner scan = new Scanner(System.in);
		Account account = new Account();

		System.out.println("What would you like to do? select action below :\n O - Open Account \n W - Withdraw \n D - Deposit T - Transfer  \n C - Check balances");
		String action = scan.next();

		if(action.equalsIgnoreCase("O")){

//			System.out.println("Provide your name and surname");
//			String names = scan.next();
//			account.setAccountHolderName(names);

			System.out.println("Select Account Type : \n 1. Current Account \n 2. Savings Account");
			String accountType = scan.next();

			if(accountType.equalsIgnoreCase("1")){
				account.setAccountNumber(generateAccountNumber());
				account.setAccountType("Current Account");

				System.out.println("Would you like an overdraft on your account");
				String overdraft = scan.next();

				if(overdraft.equalsIgnoreCase("Yes")){
					account.setOverdraftAmount(50000);
					account.setOverDraft("Yes");
				}else{
					account.setOverDraft("No");
				}


			}else if(accountType.equalsIgnoreCase("2")){
				System.out.println("A minimum of 1000 is required to open Savings Account? \n Press 1 to Continue and 2 to Cancel");
				account.setAccountNumber(generateAccountNumber());
				account.setAccountType("Savings Account");
				System.out.println("Make a deposit should be above R1000");
				double amount = scan.nextDouble();

				if(amount < 1000){
					System.out.println("Amount cannot be less than R1000, Make a deposit");
					amount = scan.nextDouble();
				}
				account.setBalance(amount);
			}

		}else if(action.equalsIgnoreCase("W")){

		}else if(action.equalsIgnoreCase("D")){

		}else if(action.equalsIgnoreCase("C")){

		}

		System.out.println(account.toString());
	}

	public static String generateAccountNumber(){
		int min = 0;
        int max = 9;
        StringBuilder accoutnNo = new StringBuilder("");
//        String accoutnNo = "";
        int random_int =0;
        for(int x = 0; x < 10; x++){
            random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
//            accoutnNo =""+random_int;
			accoutnNo.append(random_int);
        }
        // Printing the generated random numbers
        System.out.println(accoutnNo);
        return accoutnNo.toString();
	}

	public static void withDrawMoney(String accountNo, double amount){

	}

}
