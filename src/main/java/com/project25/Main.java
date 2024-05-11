package com.project25;

import com.project25.Exceptions.PasswordNotEqualException;
import com.project25.Exceptions.UsernameTakenException;
import com.project25.Exceptions.ValidationException;
import com.project25.Models.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean correctInputFlag = false;

            System.out.println("""
                    Do you want to 1 or 2?
                    1 : Register
                    2 : Login""");
        while (!correctInputFlag) {
            int choice = input.nextInt();
            if (choice == 1) {
                correctInputFlag = true;
                register(input);
            } else if (choice == 2) {
                correctInputFlag = true;
                login(input);
            } else {
                System.out.println("Invalid choice, try again: ");
            }
        }
    }

    private static void login( Scanner input) {
        input.nextLine();
        System.out.println("Welcome to the login page!");
        boolean loginSuccessful = false;
        UserService userService = new UserService();
        while (!loginSuccessful) {
            try {
                System.out.print("Username: ");
                String username = input.nextLine();

                System.out.print("Password: ");
                String password = input.nextLine();

                if (username.isEmpty() || password.isEmpty()) {
                    System.out.println("Username or password cannot be empty. Please try again.");
                    continue;
                }


                userService.login(username, password);
                System.out.println("Login successful!");
                loginSuccessful = true;

            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        input.close();
    }

    private static void register(Scanner input) {
        input.nextLine();
        boolean isValid = false;
        boolean emailValidated = false;
        boolean usernameValidated = false;

        String email;
        String username;
        String password;
        String password2;

        UserService userService = new UserService();
        System.out.println("Welcome to the sign-up page");


        while (!isValid) {
            try {
                if (!emailValidated) {
                    System.out.println("Please enter your email (alphanumeric characters and .@-):");
                    System.out.print("Email: ");
                    email = input.nextLine();
                    userService.emailInput(email);
                    emailValidated = true;
                }


                if (!usernameValidated) {
                    System.out.println("Enter your desired username (alphanumeric characters and underscores):");
                    System.out.print("Username: ");
                    username = input.nextLine();
                    userService.usernameInput(username);
                    usernameValidated = true;
                }

                //no if statement because when password is true and matches method is over
                System.out.println("Create your password (must contain at least one uppercase letter, lowercase letter, number, and special character):");
                System.out.print("Password: ");
                password = input.nextLine();
                userService.passwordInput(password);
                System.out.print("Re-enter the password: ");
                password2 = input.nextLine();
                if (!password.equals(password2)) {
                    throw new PasswordNotEqualException("Passwords do not match, try again.");
                }


                userService.register();
                System.out.println("Registration Successful");
                isValid = true;

            } catch (ValidationException | UsernameTakenException | PasswordNotEqualException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        input.close();
    }
}

