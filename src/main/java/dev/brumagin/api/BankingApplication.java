package dev.brumagin.api;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.CheckingBankAccount;
import dev.brumagin.entity.Customer;
import dev.brumagin.entity.SavingsBankAccount;
import dev.brumagin.service.BankAccountService;
import dev.brumagin.service.BankAccountServiceImpl;
import dev.brumagin.service.CustomerService;
import dev.brumagin.service.CustomerServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingApplication {

    static CustomerService customerService = new CustomerServiceImpl();
    static BankAccountService bankAccountService = new BankAccountServiceImpl();
    static Scanner userInput = new Scanner(System.in);

    public static void main (String[] args){

        System.out.println("Welcome to Negative Root Banking Inc.\n" +
                "Please access an account so we can assist you further.");
        welcomeControlFlow();


        /*
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
    static void welcomeControlFlow(){
        System.out.println("Which option would you like to choose?");
        System.out.println("1. Register a new account/user.");
        System.out.println("2. Login to an existing account.\n");
        System.out.println("Please enter 1 or 2:");

        boolean validInput;
        do {
            validInput = true;
            int inputOption=0;
            input:
            try {
                inputOption = userInput.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("You did not enter a proper argument.");
                break input;
            }
            userInput.nextLine();

            switch (inputOption) {
                case 1:
                    registerAccountControlFlow();
                    break;
                case 2:
                    System.out.println("Please enter the username associated with the account: ");
                    String username = userInput.nextLine();
                 if(!customerService.login(username))
                   dissuadeBruteForce();
                 else {

                    System.out.println("Please enter the password for the account with username: "+ username + ":");
                    String password = userInput.nextLine();

                Customer customer = customerService.login(username,password);
                     System.out.println(customer);
                 }


                 break;
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
        Customer nullCustomer = new Customer("","");
        do {
            validInput = true;
            System.out.println("You selected to register a new account.");


            System.out.println("Please enter a your first name: ");
            String firstName = userInput.nextLine();
            System.out.println("Please enter a your last name: ");
            String lastName = userInput.nextLine();
            Customer customer = customerService.createCustomer(firstName,lastName);
            usernameAndPasswordControlFlow(customer);
            if(customer== null) {
                validInput = false;
                continue;
            }
            System.out.println("Would you like to open this account with someone else? ");
            System.out.println("Please enter 1 for 'Yes' and 2 for 'No'");
            int inputOption=0;
            input:
            try {
                inputOption = userInput.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("You did not enter a proper argument");
                break input;
            }
            userInput.nextLine();

            do {
                validInput=true;
                switch (inputOption) {
                    case 1:
                        jointAccountControlFlow(customer);
                        break;
                    case 2:
                        accountTypeControlFlow(customer,nullCustomer);
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

    static void usernameAndPasswordControlFlow(Customer customer){

        boolean validInput;
        do {
            validInput=true;
            System.out.println("Please enter a valid username for the account holder.");
            System.out.println("A valid username is at least 8 characters, no more than 20 characters, and contains no spaces.");
            String username = userInput.nextLine();
            System.out.println("Please enter a valid password for the account holder.");
            System.out.println("A valid password is at least 10 characters, no more than 20 characters and contains all of the following:\n" +
                    "An uppercase letter\t A number\t A special character");
            String password = userInput.nextLine();
            validInput =customerService.registerNewAccount(customer,username, password);

        }while (!validInput);
    }

    static void dissuadeBruteForce(){

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
            usernameAndPasswordControlFlow(jointHolder);

            accountTypeControlFlow(mainHolder,jointHolder);

        }while(!validInput);

    }

    static void accountTypeControlFlow(Customer mainHolder, Customer jointHolder){
        boolean validInput;
        System.out.println("Would you like to open a Checking or Savings Account? ");
        System.out.println("Please enter 1 for 'Checking' and 2 for 'Savings'");

        int inputOption=0;
        input:
        try {
            inputOption = userInput.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("You did not enter a proper argument.");
            break input;
        }

        userInput.nextLine();
        do {
            validInput=true;
            switch (inputOption) {
                case 1:
                    bankAccountService.createAccount(mainHolder.getCustomerID(),jointHolder.getCustomerID(),'c');
                    System.out.println("You successfully created your account.\n");
                    break;
                case 2:
                    bankAccountService.createAccount(mainHolder.getCustomerID(),jointHolder.getCustomerID(),'s');
                    System.out.println("You successfully created your account.\n");
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        }while (!validInput);
        welcomeControlFlow();
    }

}
