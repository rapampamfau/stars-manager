package controller;

import service.FileManager;
import service.Logger;
import java.util.Scanner;
public class Controller {
    private static Controller instance;
    private static FileManager fileManager;
    private Scanner scan = new Scanner(System.in);
    private String input;
    private int option;
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    public void runApp() {
        fileManager = new FileManager();
        do {
            showInstructions();
            try {
                option = Integer.parseInt(scan.next());
            } catch (NumberFormatException e) {
                Logger.INSTANCE.log("Please enter a number.");
            }
        } while (option <= 0 || option >= 4);
            switch (option) {
                case 1:
                    createNewStar();
                    break;
                case 2: // deleteStar(catalogName); TODO
                    break;
                case 3: // displayAllStars(); TODO
                    break;
            }
    }
    private void showInstructions() {
        System.out.println(
          """
          Welcome to stars manager, what do you want do do?
          1. Add new star.
          2. Delete star.
          3. Display all stars.
          """
        );
    }

    private void createNewStar() {
        String name = askForStarName();
    }

    private String askForStarName() {
        String letters;
        String numbers;
        do {
            System.out.println("Enter 3 letters for name: ");
            letters = scan.next();

        } while (letters.length() != 3 || !letters.matches("[a-zA-Z]+"));

        do {
            System.out.println("Enter 4 following numbers for name: ");
            numbers = scan.next();
        } while (numbers.length() != 4 || !numbers.matches("\\d+"));

        return letters + numbers;
    }
}
