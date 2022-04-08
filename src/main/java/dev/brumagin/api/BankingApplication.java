package dev.brumagin.api;

import java.util.Scanner;

public class BankingApplication {

    public static void main (String[] args){

        Scanner userInput = new Scanner(System.in);
        int inputOption = 0;

        System.out.println("Welcome to Not A Real Bank Banking inc.\n" +
                "Please access an account so we can assist you further.");
        System.out.println("Which option would you like to choose?");
        System.out.println("1. Register a new account.");
        System.out.println("2. Login to an existing account.\n");
        System.out.println("Please enter 1 or 2:");

        boolean wrongInput;

        do {
            wrongInput = false;
            inputOption = userInput.nextInt();

            switch (inputOption) {
                case 1:
                    break;
                case 2:
                    break;
                default:
                    System.out.println("You did not enter a valid option.\n" +
                            "Please enter 1 or 2: ");
                    wrongInput = true;
                    break;
            }
        }while(wrongInput);




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
}
