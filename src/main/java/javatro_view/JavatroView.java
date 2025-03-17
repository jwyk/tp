package javatro_view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Main View Class
public class JavatroView {

    private static Screen currentScreen; // Current UI that is displayed to user
    private PropertyChangeSupport support = new PropertyChangeSupport(this); // Observable

    private static final GameScreen gameScreen = new GameScreen();
    private static final OptionScreen optionScreen = new OptionScreen();
    private static final StartScreen startScreen = new StartScreen();
    private static final SelectCardsToPlayScreen selectCardsToPlayScreen =
            new SelectCardsToPlayScreen();
    private static final SelectCardsToDiscardScreen selectCardsToDiscardScreen =
            new SelectCardsToDiscardScreen();

    public static SelectCardsToDiscardScreen getSelectCardsToDiscardScreen() {
        return selectCardsToDiscardScreen;
    }

    public static SelectCardsToPlayScreen getSelectCardsToPlayScreen() {
        return selectCardsToPlayScreen;
    }

    // Register an observer (Controller)
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    // Method to load the set the currentScreen (e.g. start game, options)
    public void setCurrentScreen(Screen s) {
        currentScreen = s;
        currentScreen.displayScreen();
        getInput(); // Get user input
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static GameScreen getGameScreen() {
        return gameScreen;
    }

    public static OptionScreen getOptionScreen() {
        return optionScreen;
    }

    public static StartScreen getStartScreen() {
        return startScreen;
    }

    public static void clearConsole() {
        String FLUSH = "\033[H\033[2J";
        System.out.print(FLUSH);
        System.out.flush();
    }

    public static List<Integer> getCardInput(int maxCardsAvailable, int maxCardsToSelect) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> userInput = null; // List to store the numbers entered by the user

        while (true) {
            System.out.println(
                    "Enter numbers (comma-separated, e.g., 1,2,3) from 1 to "
                            + maxCardsAvailable
                            + " (Allowed to select only "
                            + maxCardsToSelect
                            + "): ");

            String input = scanner.nextLine().trim(); // Read the entire line

            // Split the input string by commas
            String[] inputArray = input.split(",");
            boolean valid = true;

            // Validate each number in the input
            userInput =
                    Arrays.stream(inputArray) // Convert to stream to process each part
                            .map(String::trim) // Trim spaces around numbers
                            .map(
                                    numStr -> {
                                        try {
                                            return Integer.parseInt(numStr)
                                                    - 1; // Try to parse each number
                                        } catch (NumberFormatException e) {
                                            return null; // Return null for invalid number
                                        }
                                    })
                            .filter(num -> num != null) // Remove nulls caused by invalid
                            // number format
                            .collect(Collectors.toList()); // Collect the valid numbers into a list

            // Check if the userInput list is valid
            if (userInput.isEmpty()
                    || userInput.stream().anyMatch(num -> num < 0 || num > maxCardsAvailable - 1)) {
                System.out.println(
                        "Invalid Input! Enter numbers (comma-separated, e.g., 1,2,3) from 1 to "
                                + maxCardsAvailable
                                + " (Allowed to select only "
                                + maxCardsToSelect
                                + "): ");
            } else {
                // If the input is valid, exit the loop
                System.out.println("You selected the numbers: " + userInput);
                break;
            }
        }
        return userInput;
    }

    public void getInput() {
        Scanner scanner = new Scanner(System.in);
        int userInput = -1;
        int maxRange =
                getCurrentScreen().getOptionsSize(); // Change this value to set a different range
        while (true) {
            currentScreen.displayOptions();
            System.out.print("Enter a number (1 to " + maxRange + "): ");
            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt();
                if (userInput >= 1 && userInput <= maxRange) {
                    break; // Valid input, exit loop
                } else {
                    System.out.println(
                            "Invalid input! Please enter a number between 1 and " + maxRange + ".");
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
        // When user input is received, trigger all the observers
        support.firePropertyChange("userInput", null, userInput);
        clearConsole();
    }
}
