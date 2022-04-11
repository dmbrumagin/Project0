package dev.brumagin.api;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.Customer;
import dev.brumagin.service.BankAccountService;
import dev.brumagin.service.BankAccountServiceImpl;
import dev.brumagin.service.CustomerService;
import dev.brumagin.service.CustomerServiceImpl;
import dev.brumagin.utility.LinkedList;

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
                    login();
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        }while(!validInput);
    }
    static Customer login(){
        Customer customer = null;
        System.out.println("Please enter the username associated with an account: ");
        String username = userInput.nextLine();
        if(!customerService.login(username))
            dissuadeBruteForce();
        else {

            System.out.println("Please enter the password for the account with username: "+ username + ":");
            String password = userInput.next();

            customer = customerService.login(username,password);
            accountValidControlFlow(customer);

        }
        return customer;
    }

    static void accountValidControlFlow(Customer customer){
        boolean validInput;

        System.out.println("Please enter what Account Number you would like to access" +
                "Please enter 0 to create a new account");
        LinkedList<BankAccount> listOfAccounts = bankAccountService.getAllAccounts(customer.getCustomerID());

        do {
            validInput = true;
            for (BankAccount b : listOfAccounts) {
                bankAccountService.printBalance(b);
            }
            long inputOption = 0;
            input:
            try {
                inputOption = userInput.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a proper argument.");
                break input;
            }
            if(inputOption==0){
                registerAccountControlFlow(customer);
            }

            else if (bankAccountService.isAccount(inputOption)) {
                accountOptionsControlFlow(bankAccountService.getAccount(inputOption));
            } else {
                validInput = false;
            }

        }while (!validInput);

    }

    static void accountOptionsControlFlow(BankAccount bankAccount){

        System.out.println("Please enter what you would like to do with the account #:"+bankAccount.getAccountNumber());
        System.out.println("1.Withdraw from account");
        System.out.println("2.Deposit to account");
        System.out.println("3.Make a transfer to another account");
        System.out.println("4.Change Joint Account Status");
        System.out.println("5.Show transaction history");
        boolean validInput = true;
        int inputOption=0;
        input:
        try {
            inputOption = userInput.nextInt();
            userInput.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("You did not enter a proper argument.");
            break input;
        }
        do {
            validInput=true;
            switch (inputOption) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:

                    //TODO
                    jointAccountWithExistingCustomerUpdate(bankAccount);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        }while (!validInput);


    }
    static void registerAccountControlFlow(Customer customer){
        boolean validInput;
        Customer nullCustomer = new Customer("","");
        do {
            validInput = true;
            System.out.println("You selected to register a new account.");


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
                        jointAccountWithExistingCustomerNew(customer);
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

    static void jointAccountWithExistingCustomerNew(Customer customer){
        System.out.println("Please enter the login credentials for the Joint Account holder");
        Customer joint = login();
        accountTypeControlFlow(customer,joint);
    }
    static void jointAccountWithExistingCustomerUpdate(BankAccount bankAccount){
        System.out.println("Please enter the login credentials for the Joint Account holder");
        Customer joint = login();
        bankAccount.setJointAccountHolder(joint.getCustomerID());
        bankAccountService.updateAccount(bankAccount);
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
           // validInput = !customerService.login(username);
            System.out.println("Please enter a valid password for the account holder.");
            System.out.println("A valid password is at least 10 characters, no more than 20 characters and contains all of the following:\n" +
                    "--An uppercase letter --A number --A special character");
            String password = userInput.nextLine();
            System.out.println(validInput);
            if(validInput)
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
