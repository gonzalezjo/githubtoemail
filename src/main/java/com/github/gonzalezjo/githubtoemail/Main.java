package com.github.gonzalezjo.githubtoemail;


import java.io.IOException;
import java.util.Scanner;

import static com.github.gonzalezjo.githubtoemail.grabber.EmailGrabber.printEmailsOfUser;

public class Main {
    private static final boolean DEBUG = false;

    public static void main(String[] args) throws IOException {
        System.out.println("Enter a GitHub name here:");

        if (DEBUG) {
            printEmailsOfUser("GitHub");
        } else {
            printEmailsOfUser(new Scanner(System.in).nextLine());
        }
    }
}

