package dev.brumagin.api;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.CheckingBankAccount;
import dev.brumagin.entity.Customer;
import dev.brumagin.entity.SavingsBankAccount;
import dev.brumagin.service.BankAccountService;
import dev.brumagin.service.BankAccountServiceImpl;
import dev.brumagin.service.CustomerService;
import dev.brumagin.service.CustomerServiceImpl;

import java.util.Scanner;

public class BankingApplication {

    static CustomerService customerService = new CustomerServiceImpl();
    static BankAccountService bankAccountService = new BankAccountServiceImpl();
    static Scanner userInput = new Scanner(System.in);

    public static void main (String[] args){

        welcomeControlFlow();


        /*register a new user account with the system (must be secured with a password)
        login with my existing credentials
        create at least one account
        deposit funds into an account (use doubles, not ints)
        withdraw funds from an account (no overdrafting!)
        view the balance of my account(s) (all balance displays must be in proper currency format)
        Suggested Bonus User Stories
        As a user I can:

        view the transaction history for an account
        create multiple accounts per user (checking, savings, etc.)
        share a joint account with another user
        transfer money between accounts*/


    }
    static void dissuadeBruteForce(){

    }
    static void welcomeControlFlow(){
        System.out.println("Welcome to Not A Real Bank Banking inc.\n" +
                "Please access an account so we can assist you further.");
        System.out.println("Which option would you like to choose?");
        System.out.println("1. Register a new account.");
        System.out.println("2. Login to an existing account.\n");
        System.out.println("Please enter 1 or 2:");

        boolean validInput;
        do {
            validInput = true;
            int inputOption = userInput.nextInt();
            userInput.nextLine();

            switch (inputOption) {
                case 1:
                    registerAccountControlFlow();
                    break;
                case 2:
                    System.out.println("Please enter the username associated with the account: ");
                    String username = userInput.nextLine();
                    break;
                // if(!customerService.login(username))
                //    dissuadeBruteForce(userInput);
                // else {

                //    System.out.println("Please enter the password for the account with username: "+ username + ":");
                //    String password = userInput.nextLine();

                //Customer customer = customerService.login(username,password);

                // }


                // break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        }while(!validInput);
    }





    static void registerAccountControlFlow(){
        boolean validInput;

        do {
            validInput = true;
            System.out.println("You selected to register a new account.");
            System.out.println("Please enter a your first name: ");
            String firstName = userInput.nextLine();
            System.out.println("Please enter a your last name: ");
            String lastName = userInput.nextLine();
            Customer customer = customerService.createCustomer(firstName,lastName);
            if(customer== null) {
                validInput = false;
                continue;
            }
            System.out.println("Would you like to open this account with someone else? ");
            System.out.println("Please enter 1 for 'Yes' and 2 for 'No'");
            int inputOption = userInput.nextInt();
            userInput.nextLine();

            do {
                validInput=true;
                switch (inputOption) {
                    case 1:
                        jointAccountControlFlow(customer);
                        break;
                    case 2:
                        accountTypeControlFlow(customer,null);
                        break;
                    default:
                        System.out.println("You did not enter a valid option.\n" +
                                "Please enter 1 or 2: ");
                        validInput = false;
                        break;
                }
            }while (!validInput);


        }while(!validInput);

    }
    static void jointAccountControlFlow(Customer mainHolder){
        boolean validInput;


        do {
            validInput = true;
            System.out.println("You selected to register a joint account holder.");
            System.out.println("Please enter their first name: ");
            String firstName = userInput.nextLine();
            System.out.println("Please enter their last name: ");
            String lastName = userInput.nextLine();
            Customer jointHolder = customerService.createCustomer(firstName,lastName);
            if(jointHolder== null) {
                validInput = false;
                continue;
            }

            accountTypeControlFlow(mainHolder,jointHolder);

        }while(!validInput);

    }

    static void accountTypeControlFlow(Customer mainHolder, Customer jointHolder){
        boolean validInput;
        System.out.println("Would you like to open a Checking or Savings Account? ");
        System.out.println("Please enter 1 for 'Checking' and 2 for 'Savings'");
        int inputOption = userInput.nextInt();
        userInput.nextLine();
        do {
            validInput=true;
            switch (inputOption) {
                case 1:
                    CheckingBankAccount checkingBankAccount = (CheckingBankAccount) bankAccountService.createAccount(mainHolder,jointHolder);
                    break;
                case 2:
                    SavingsBankAccount savingsBankAccount = (SavingsBankAccount) bankAccountService.createAccount(mainHolder,jointHolder);
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        }while (!validInput);

    }

}
