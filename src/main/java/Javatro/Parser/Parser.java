package Javatro.Parser;

import Javatro.UI.UI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import static Javatro.UI.UI.getCurrentScreen;

public class Parser {

    /** Property change support for notifying observers of user input changes. */
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Registers an observer (JavatroManager) to listen for user input changes.
     *
     * @param pcl the property change listener to register
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }


    /**
     * Prompts the user to select card numbers and returns a list of selected card indices.
     *
     * @param maxCardsAvailable the maximum number of available cards
     * @param maxCardsToSelect the maximum number of cards a user can select
     * @return a list of selected card indices
     */
    public static List<Integer> getCardInput(int maxCardsAvailable, int maxCardsToSelect) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> userInput;

        while (true) {
            System.out.println(
                    "Enter numbers (comma-separated, e.g., 1,2,3) from 1 to "
                            + maxCardsAvailable
                            + " (Allowed to select only "
                            + maxCardsToSelect
                            + "):");

            String input = scanner.nextLine().trim();
            String[] inputArray = input.split(",");

            userInput =
                    Arrays.stream(inputArray)
                            .map(String::trim)
                            .map(
                                    numStr -> {
                                        try {
                                            return Integer.parseInt(numStr) - 1;
                                        } catch (NumberFormatException e) {
                                            return null;
                                        }
                                    })
                            .filter(num -> num != null && num >= 0 && num < maxCardsAvailable)
                            .collect(Collectors.toList());

            if (!userInput.isEmpty()) {
                System.out.println("You selected the numbers: " + userInput);
                break;
            } else {
                System.out.println("Invalid Input! Please enter valid numbers.");
            }
        }
        return userInput;
    }

    /** Handles user input for navigating the current screen and notifies observers. */
    public void getInput() {
        Scanner scanner = new Scanner(System.in);
        int userInput;
        int maxRange = getCurrentScreen().getOptionsSize();

        while (true) {
            getCurrentScreen().displayOptions();
            System.out.print("Enter a number (1 to " + maxRange + "): ");

            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt();
                if (userInput >= 1 && userInput <= maxRange) {
                    break;
                } else {
                    System.out.println(
                            "Invalid input! Please enter a number between 1 and " + maxRange + ".");
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }
        }

        // Update listeners (JavatroManager)  on the value of user input being updated
        support.firePropertyChange("userInput", null, userInput);
        UI.clearConsole();
    }
}
