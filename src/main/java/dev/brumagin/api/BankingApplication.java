package dev.brumagin.api;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.Customer;
import dev.brumagin.entity.TransactionType;
import dev.brumagin.service.*;
import dev.brumagin.utility.LinkedList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingApplication {

    static CustomerService customerService = new CustomerServiceImpl();
    static BankAccountService bankAccountService = new BankAccountServiceImpl();
    static TransactionService transactionService = new TransactionServiceImpl();
    static Scanner userInput = new Scanner(System.in);

    public static void main (String[] args){

        System.out.println("Welcome to Negative Root Banking Inc.\n" +
                "Please access an account so we can assist you further.");
        welcomeControlFlow();

       /*TODO view the transaction history for an account*/

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
                System.out.println("You did not enter a number.");
                break input;
            }
            userInput.nextLine();

            switch (inputOption) {
                case 1:
                    createAccountControlFlow(false);
                    break;
                case 2:
                    login(false, false);
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        }while(!validInput);
    }
    static Customer login(boolean customerToReturn, boolean loggedIn){
        Customer customer = null;
        System.out.println("Please enter the username associated with an account: ");
        String username = userInput.nextLine();
        if(!customerService.login(username))
            dissuadeBruteForce(username);
        else {
            int logonTries = 0;

            do {
                System.out.println("Please enter the password for the account with username: " + username + ":");
                String password = userInput.next();


                customer = customerService.login(username, password);

                if(customer!=null)
                    break;
                else{
                    System.out.println("You entered an incorrect password. Please check your records and try again.\n" +
                            "Passwords are case-sensitive. You have " + (3-logonTries) + " attempts remaining.");
                    logonTries++;
                }

            }while (logonTries < 3);
            if(customer == null){
                System.out.println("You have used up all your logon attempts.");
                logout();
                //TODO lockoutCustomer(customer);
            }
            if(!customerToReturn ){
                accountSelectControlFlow(customer,true);
            }
        }
        return customer;
    }

    static void dissuadeBruteForce(String username) {
        int logonTries = 0;
        do {
            System.out.println("Please enter the password for the account with username: " + username + ":");
            String password = userInput.next();
            System.out.println("You entered an incorrect password. Please check your records and try again.\n" +
                    "Passwords are case-sensitive. You have " + (3 - logonTries) + " attempts remaining.");
            logonTries++;
        } while (logonTries < 3);
        System.out.println("You have used up all your logon attempts.");
        logout();
    }

    static void accountSelectControlFlow(Customer customer, boolean loggedIn){
        boolean validInput;

        System.out.println("Please enter what Account Number you would like to access\n" +
                "Please enter '0' to create a new account.");
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
                System.out.println("You did not enter a number.");
                break input;
            }
            if(inputOption==0){
                createAccountControlFlow(customer,loggedIn);
            }
            else if (bankAccountService.isAccount(inputOption)) {
                accountOptionsControlFlow(bankAccountService.getAccount(inputOption),customer);
            } else {
                System.out.println("You did not enter an account number. Please try again.");
                validInput = false;
            }

        }while (!validInput);

    }
    static void makeDeposit(BankAccount bankAccount,Customer customer){
        System.out.println("You selected to make a deposit.");
        System.out.println("How much would you like to deposit?");
        double deposit=0;
        input:
        try {
            deposit = userInput.nextDouble();
            userInput.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("You did not enter a number.");
            break input;
        }
        bankAccountService.deposit(bankAccount,deposit);
        transactionService.createTransaction(bankAccount.getAccountNumber(),bankAccount.getAccountNumber(), TransactionType.DEPOSIT,deposit,System.currentTimeMillis());
        bankAccountService.printBalance(bankAccount);
        accountOptionsControlFlow(bankAccount,customer);
    }
    static void makeWithdrawal(BankAccount bankAccount,Customer customer){
        System.out.println("You selected to make a withdrawal.");
        System.out.println("How much would you like to withdraw?");
        double withdrawal=0;
        input:
        try {
            withdrawal = userInput.nextDouble();
            userInput.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("You did not enter a number.");
            break input;
        }
        bankAccountService.withdraw(bankAccount,withdrawal);
        transactionService.createTransaction(bankAccount.getAccountNumber(),bankAccount.getAccountNumber(), TransactionType.WITHDRAWAL,withdrawal,System.currentTimeMillis());
        bankAccountService.printBalance(bankAccount);
        accountOptionsControlFlow(bankAccount,customer);
    }
    static void transferToAccount(BankAccount origin, Customer customer){
        System.out.println("You selected to make a transfer.");
        System.out.println("How much would you like to transfer?");
        double transfer=0;
        long accountDestination=0;
        input:
        try {
            transfer = userInput.nextDouble();
        }
        catch (InputMismatchException e){
            System.out.println("You did not enter a proper argument.");
            break input;
        }
        System.out.println("What is the account number you would like to transfer to?");
        input2:
        try {
            accountDestination = userInput.nextLong();
        }
        catch (InputMismatchException e){
            System.out.println("You did not enter a proper argument.");
            break input2;
        }
        if(bankAccountService.getAccount( accountDestination)!= null){
        bankAccountService.transferFunds(origin, bankAccountService.getAccount(accountDestination),transfer);
        transactionService.createTransaction(origin.getAccountNumber(),accountDestination, TransactionType.TRANSFER,transfer,System.currentTimeMillis());
        bankAccountService.printBalance(origin);
        }
        else{
            System.out.println("You entered an invalid account number. Transfer was not completed.");
            accountOptionsControlFlow(origin,customer);
        }
        accountOptionsControlFlow(origin,customer);
    }

    static void accountOptionsControlFlow(BankAccount bankAccount,Customer customer){

        System.out.println("Please enter what you would like to do with the account #:"+bankAccount.getAccountNumber());
        System.out.println("1.Deposit to account");
        System.out.println("2.Withdraw from account");
        System.out.println("3.Make a transfer to another account");
        System.out.println("4.Show transaction history");
        System.out.println("5.Go back to account overview.");
        System.out.println("6.Log Out.");
        System.out.println("7.Exit Application");

        int inputOption=0;
        input:
        try {
            inputOption = userInput.nextInt();
            userInput.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("You did not enter a number.");
            break input;
        }
        boolean validInput;
        do {
            validInput=true;
            switch (inputOption) {
                case 1:
                    makeDeposit(bankAccount,customer);
                    break;
                case 2:
                    makeWithdrawal(bankAccount,customer);
                    break;
                case 3:
                    transferToAccount(bankAccount,customer);
                    break;
                case 4:
                    transactionService.getAllTransactions(bankAccount.getAccountNumber());
                    accountOptionsControlFlow(bankAccount,customer);
                    break;
                case 5:
                    accountSelectControlFlow(customer,true);
                    break;
                case 6:
                    logout();
                    break;
                case 7:
                    System.out.println("Thank you for using Negative Root Banking. We hope to see you again soon!");
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        }while (!validInput);


    }
    static void logout(){
        System.out.println("You have logged out of your account.\n");
        welcomeControlFlow();
    }
    static void createAccountControlFlow(Customer customer, boolean loggedIn) {
        boolean validInput;
        Customer nullCustomer = new Customer("", "");
        System.out.println("You selected to register a new account.");
        System.out.println("Would you like to open this account with someone else? ");
        System.out.println("Please enter 1 for 'Yes' and 2 for 'No'.");
        int inputOption = 0;
        input:
        try {
            inputOption = userInput.nextInt();
            userInput.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("You did not enter a number.");
            break input;
        }

        do {
            validInput = true;
            switch (inputOption) {
                case 1:
                    jointAccountUpdateControlFlow(customer,loggedIn);
                    break;
                case 2:
                    accountTypeControlFlow(customer, nullCustomer,loggedIn);
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        } while (!validInput);
    }

    static void jointAccountUpdateControlFlow(Customer customer,boolean loggedIn){
        System.out.println("Is the person you are opening an account with currently a customer? ");
        System.out.println("Please enter 1 for 'Yes' and 2 for 'No'.");
        int inputOption=0;
        input:
        try {
            inputOption = userInput.nextInt();
            userInput.nextLine();
        }
        catch (InputMismatchException e){
            System.out.println("You did not enter a number.");
            break input;
        }

        boolean validInput;
        do {
            validInput=true;
            switch (inputOption) {
                case 1:
                    System.out.println("Please enter the login credentials for the Joint Account holder.");
                    Customer joint = login(true,loggedIn);
                    accountTypeControlFlow(customer,joint,loggedIn);
                    break;
                case 2:
                    jointAccountControlFlow(customer,loggedIn);
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        }while (!validInput);
    }

    static void createAccountControlFlow(boolean loggedIn){
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
                        jointAccountControlFlow(customer,loggedIn);
                        break;
                    case 2:
                        accountTypeControlFlow(customer,nullCustomer,loggedIn);
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
            else{
                //TODO split into username and password validation
                System.out.println("You did not enter a valid username or password. Please follow the prompts.");
            }

        }while (!validInput);
    }



    static void jointAccountControlFlow(Customer mainHolder,boolean loggedIn){
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

            accountTypeControlFlow(mainHolder,jointHolder,loggedIn);


        }while(!validInput);

    }

    static void accountTypeControlFlow(Customer mainHolder, Customer jointHolder, boolean loggedIn){
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
        if(!loggedIn)
            welcomeControlFlow();
        else{
            accountSelectControlFlow(mainHolder,true);
        }
    }

}
