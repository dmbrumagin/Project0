package dev.brumagin.api;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.Customer;
import dev.brumagin.entity.TransactionType;
import dev.brumagin.service.*;
import dev.brumagin.utility.LinkedList;
import dev.brumagin.utility.LogLevel;
import dev.brumagin.utility.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingApplication {

    static CustomerService customerService = new CustomerServiceImpl();
    static BankAccountService bankAccountService = new BankAccountServiceImpl();
    static TransactionService transactionService = new TransactionServiceImpl();
    static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to Negative Root Banking Inc.\n" +
                "Please access an account so we can assist you further.");
        try {
            welcomeControlFlow();
        } catch (Throwable t) {
            Logger.log(t.getMessage(), LogLevel.ERROR);
        }

    }

    static void welcomeControlFlow() {
        System.out.println("Which option would you like to choose?");
        System.out.println("1. Register a new account/user.");
        System.out.println("2. Login to an existing account.");
        System.out.println("3. Exit.");
        System.out.println("Please enter 1, 2, or 3:");

        boolean validInput;
        do {
            validInput = true;
            int inputOption = 0;
            try {
                inputOption = userInput.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a number.");
            }
            userInput.nextLine();

            switch (inputOption) {
                case 1:
                    createAccountControlFlow(false);
                    break;
                case 2:
                    login(false);
                    break;
                case 3:
                    System.out.println("Thank you for using Negative Root Banking Inc. We hope to see you again soon!");
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1, 2, or 3: ");
                    validInput = false;
                    break;
            }
        } while (!validInput);
    }

    static Customer login(boolean customerToReturn) {
        Customer customer = null;
        System.out.println("Please enter the username associated with an account: ");
        String username = userInput.nextLine();
        if (!customerService.login(username))
            dissuadeBruteForce(username);
        else {
            int logonTries = 0;

            do {
                System.out.println("Please enter the password for the account with username: " + username + ":");
                String password = userInput.next();


                customer = customerService.login(username, password);

                if (customer != null)
                    break;
                else {
                    logonTries++;
                    System.out.println("You entered an incorrect password. Please check your records and try again.\n" +
                            "Passwords are case-sensitive. You have " + (3 - logonTries) + " attempts remaining.");

                }

            } while (logonTries < 3);
            if (customer == null) {
                System.out.println("You have used up all your logon attempts.\n");
                welcomeControlFlow();
                return null;
                //TODO lockoutCustomer(customer);
            }
            if (!customerToReturn) {
                accountSelectControlFlow(customer, true);
            }
        }
        return customer;
    }

    static void dissuadeBruteForce(String username) {
        int logonTries = 0;
        do {
            System.out.println("Please enter the password for the account with username: " + username + ":");
            String password = userInput.nextLine();
            logonTries++;
            System.out.println("You entered an incorrect password. Please check your records and try again.\n" +
                    "Passwords are case-sensitive. You have " + (3 - logonTries) + " attempts remaining.");

        } while (logonTries < 3);
        System.out.println("You have used up all your logon attempts.\n");
        welcomeControlFlow();
    }

    static void accountSelectControlFlow(Customer customer, boolean loggedIn) {
        boolean validInput;
        long inputOption;
        LinkedList<BankAccount> listOfAccounts = bankAccountService.getAllAccounts(customer.getCustomerID());

        do {
            System.out.println("Please enter what Account Number you would like to access\n" +
                    "Please enter '0' to create a new account.");
            for (BankAccount b : listOfAccounts) {
                bankAccountService.printBalance(b);
            }
            validInput = true;
            try {
                inputOption = userInput.nextLong();
                userInput.nextLine();
                if (inputOption == 0) {
                    System.out.println("debug");
                    createAccountControlFlow(customer, loggedIn);
                } else if (bankAccountService.isAccount(inputOption)) {
                    accountOptionsControlFlow(bankAccountService.getAccount(inputOption), customer);
                } else {
                    System.out.println("You did not enter an account number. Please try again.");
                    validInput = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a number.");
                validInput = false;
            }
        } while (!validInput);

    }

    static void makeDeposit(BankAccount bankAccount, Customer customer) {
        boolean valid;
        double deposit = 0;
        do {
            System.out.println("You selected to make a deposit.");
            System.out.println("How much would you like to deposit?");

            valid=true;
            try {
                deposit = userInput.nextDouble();
                userInput.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a number.");
                valid=false;
            }
        }while(!valid);
        bankAccountService.deposit(bankAccount, deposit);
        transactionService.createTransaction(bankAccount.getAccountNumber(), bankAccount.getAccountNumber(), TransactionType.DEPOSIT, deposit, System.currentTimeMillis());
        bankAccountService.printBalance(bankAccount);
        accountOptionsControlFlow(bankAccount, customer);
    }

    static void makeWithdrawal(BankAccount bankAccount, Customer customer) {
        double withdrawal = 0;
        boolean valid;
        do {
            valid=true;
            System.out.println("You selected to make a withdrawal.");
            System.out.println("How much would you like to withdraw?");

            try {
                withdrawal = userInput.nextDouble();
                userInput.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a number.");
                valid=false;
            }
        }while (!valid);
        bankAccountService.withdraw(bankAccount, withdrawal);
        transactionService.createTransaction(bankAccount.getAccountNumber(), bankAccount.getAccountNumber(), TransactionType.WITHDRAWAL, withdrawal, System.currentTimeMillis());
        bankAccountService.printBalance(bankAccount);
        accountOptionsControlFlow(bankAccount, customer);
    }

    static void transferToAccount(BankAccount origin, Customer customer) {
        boolean valid;
        double transfer = 0;
        long accountDestination = 0;

        do {
            valid = true;
            System.out.println("You selected to make a transfer.");
            System.out.println("How much would you like to transfer?");


            try {
                transfer = userInput.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a proper argument.");
                valid = false;
            }
        } while (!valid);

        do {
            valid = true;
            System.out.println("What is the account number you would like to transfer to?");
            try {
                accountDestination = userInput.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a proper argument.");
                valid = false;
            }
        } while (!valid);

        if (bankAccountService.getAccount(accountDestination) != null) {
            valid = bankAccountService.transferFunds(origin, bankAccountService.getAccount(accountDestination), transfer);
            transactionService.createTransaction(origin.getAccountNumber(), accountDestination, TransactionType.TRANSFER, transfer, System.currentTimeMillis());
            if(!valid)
                System.out.println("You tried to transfer too many funds. The transfer was not completed.");
            bankAccountService.printBalance(origin);
        }

        else {
            System.out.println("You entered an invalid account number. Transfer was not completed.");
            accountOptionsControlFlow(origin, customer);
        }
        accountOptionsControlFlow(origin, customer);
    }

    static void accountOptionsControlFlow(BankAccount bankAccount, Customer customer) {
        int inputOption = 0;
        boolean validInput;
        do {

            System.out.println("Please enter what you would like to do with the account #:" + bankAccount.getAccountNumber());
            System.out.println("1.Deposit to account");
            System.out.println("2.Withdraw from account");
            System.out.println("3.Make a transfer to another account");
            System.out.println("4.Show transaction history");
            System.out.println("5.Go back to account overview.");
            System.out.println("6.Log Out.");
            System.out.println("7.Exit Application");

            try {
                inputOption = userInput.nextInt();
                userInput.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a number.");
            }

            validInput = true;
            switch (inputOption) {
                case 1:
                    makeDeposit(bankAccount, customer);
                    break;
                case 2:
                    makeWithdrawal(bankAccount, customer);
                    break;
                case 3:
                    transferToAccount(bankAccount, customer);
                    break;
                case 4:
                    transactionService.getAllTransactions(bankAccount.getAccountNumber());
                    accountOptionsControlFlow(bankAccount, customer);
                    break;
                case 5:
                    accountSelectControlFlow(customer, true);
                    break;
                case 6:
                    logout();
                    break;
                case 7:
                    System.out.println("Thank you for using Negative Root Banking Inc. We hope to see you again soon!");
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        } while (!validInput);
    }

    static void logout() {
        System.out.println("You have logged out of your account.\n");
        welcomeControlFlow();
    }

    static void createAccountControlFlow(Customer customer, boolean loggedIn) {
        boolean validInput;
        int inputOption = 0;
        Customer nullCustomer = new Customer("", "");
        do {
        System.out.println("You selected to register a new account.");
        System.out.println("Would you like to open this account with someone else? ");
        System.out.println("Please enter 1 for 'Yes' and 2 for 'No'.");

        try {
            inputOption = userInput.nextInt();
            userInput.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("You did not enter a number.");
        }


            validInput = true;
            switch (inputOption) {
                case 1:
                    jointAccountUpdateControlFlow(customer, loggedIn);
                    break;
                case 2:
                    accountTypeControlFlow(customer, nullCustomer, loggedIn);
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        } while (!validInput);
    }

    static void jointAccountUpdateControlFlow(Customer customer, boolean loggedIn) {
        boolean validInput;
        do {
            int inputOption = 0;
            System.out.println("Is the person you are opening an account with currently a customer? ");
            System.out.println("Please enter 1 for 'Yes' and 2 for 'No'.");

            try {
                inputOption = userInput.nextInt();
                userInput.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a number.");
            }

            validInput = true;
            switch (inputOption) {
                case 1:
                    System.out.println("Please enter the login credentials for the Joint Account holder.");
                    Customer joint = login(true);
                    accountTypeControlFlow(customer, joint, loggedIn);
                    break;
                case 2:
                    jointAccountControlFlow(customer, loggedIn);
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        } while (!validInput);
    }

    static void createAccountControlFlow(boolean loggedIn) {

        boolean validInput;
        Customer nullCustomer = new Customer("", "");
        do {
            validInput = true;
            System.out.println("You selected to register a new account.");
            System.out.println("Please enter a your first name: ");
            String firstName = userInput.nextLine();
            System.out.println("Please enter a your last name: ");
            String lastName = userInput.nextLine();
            Customer customer = customerService.createCustomer(firstName, lastName);
            usernameAndPasswordControlFlow(customer);
            if (customer == null) {
                validInput = false;
                continue;
            }

            boolean nestedValid;
            do {
                System.out.println("Would you like to open this account with someone else? ");
                System.out.println("Please enter 1 for 'Yes' and 2 for 'No'");
                nestedValid = true;
                int inputOption = 0;
                try {
                    inputOption = userInput.nextInt();
                    userInput.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("You did not enter a proper argument");
                }

                switch (inputOption) {
                    case 1:
                        jointAccountControlFlow(customer, loggedIn);
                        break;
                    case 2:
                        accountTypeControlFlow(customer, nullCustomer, loggedIn);
                        break;
                    default:
                        System.out.println("You did not enter a valid option.\n" +
                                "Please enter 1 or 2: ");
                        nestedValid = false;
                        break;
                }
            } while (!nestedValid);

        } while (!validInput);
    }

    static void usernameAndPasswordControlFlow(Customer customer) {

        boolean validInput;
        do {
            System.out.println("Please enter a valid username for the account holder.");
            System.out.println("A valid username is at least 8 characters, no more than 20 characters, and contains no spaces.");
            String username = userInput.nextLine();
            System.out.println("Please enter a valid password for the account holder.");
            System.out.println("A valid password is at least 10 characters, no more than 20 characters and contains all of the following:\n" +
                    "--An uppercase letter --A number --A special character");
            String password = userInput.nextLine();
            validInput = customerService.registerNewAccount(customer, username, password);

        } while (!validInput);
    }


    static void jointAccountControlFlow(Customer mainHolder, boolean loggedIn) {
        boolean validInput;
        do {
            validInput = true;
            System.out.println("You selected to register a joint account holder.");
            System.out.println("Please enter their first name: ");
            String firstName = userInput.nextLine();
            System.out.println("Please enter their last name: ");
            String lastName = userInput.nextLine();
            Customer jointHolder = customerService.createCustomer(firstName, lastName);

            if (jointHolder == null) {
                validInput = false;
                continue;
            }

            usernameAndPasswordControlFlow(jointHolder);
            accountTypeControlFlow(mainHolder, jointHolder, loggedIn);

        } while (!validInput);

    }

    static void accountTypeControlFlow(Customer mainHolder, Customer jointHolder, boolean loggedIn) {

        boolean validInput;
        int inputOption;

        do {
            System.out.println("Would you like to open a Checking or Savings Account? ");
            System.out.println("Please enter 1 for 'Checking' and 2 for 'Savings'");

            try {
                inputOption = userInput.nextInt();
                userInput.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("You did not enter a proper argument.");
                break;
            }

            validInput = true;
            switch (inputOption) {
                case 1:
                    bankAccountService.createAccount(mainHolder.getCustomerID(), jointHolder.getCustomerID(), 'c');
                    System.out.println("You successfully created your account.\n");
                    break;
                case 2:
                    bankAccountService.createAccount(mainHolder.getCustomerID(), jointHolder.getCustomerID(), 's');
                    System.out.println("You successfully created your account.\n");
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    validInput = false;
                    break;
            }
        } while (!validInput);
        if (!loggedIn)
            welcomeControlFlow();
        else {
            accountSelectControlFlow(mainHolder, true);
        }
    }

}
