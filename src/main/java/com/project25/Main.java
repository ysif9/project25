package com.project25;

import com.project25.Models.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        register();

    }

    private static void register() {
        Scanner input = new Scanner(System.in);
        boolean isValid = false;
        boolean passMatch = false;
        UserService userService = new UserService();
        System.out.println("Welcome to the sign-up page");


        while (!isValid) {
            try {
                System.out.println("Please enter your email (alphanumeric characters and .@-):");
                System.out.print("Email: ");
                String email = input.nextLine();
                userService.emailInput(email);


                String username;
                System.out.println("Now enter your desired username (alphanumeric characters and underscores):");
                System.out.print("Username: ");
                username = input.nextLine();
                userService.usernameInput(username);


                System.out.println("Now create your password (must contain at least one uppercase letter, lowercase letter, number, and special character):");


                System.out.print("Password: ");
                String password = input.nextLine();
                userService.passwordInput(password);


                System.out.print("Re-enter the password: ");
                String password2 = input.nextLine();

                if (!password.equals(password2)) {
                        System.out.println("Passwords don't match. Please try again.");
                    } else {
                        passMatch = true;
                        isValid = true;
                        System.out.println("Registration successful!");
                    }
                } catch (Exception e) {
                e.printStackTrace();
            }
        }
        input.close();
    }

}

