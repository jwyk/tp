package javatro_core;

import java.util.Scanner;
import javatro_view.GameScreen;
import javatro_view.JavatroView;
import javatro_view.Screen;
import javatro_view.StartScreen;

//Main Controller (Manager) Class
public class JavatroManager {

  private JavatroView jv;

  public JavatroManager(JavatroView jv) {
    this.jv = jv;
  }

  public void changeScreen(Screen destinationScreen) {
    //Changes the screen to display and displays it
    jv.setCurrentScreen(destinationScreen);
    jv.displayCurrentScreen();
  }

  private int getInput() {
        Scanner scanner = new Scanner(System.in);
        int maxRange = jv.getCurrentScreen().getOptionsSize(); // Change this value to set a different range
        int userInput = -1;

        while (true) {
          System.out.print("Enter a number (1 to " + maxRange + "): ");
          if (scanner.hasNextInt()) {
            userInput = scanner.nextInt();
            if (userInput >= 1 && userInput <= maxRange) {
              break; // Valid input, exit loop
            } else {
              System.out.println("Invalid input! Please enter a number between 1 and " + maxRange + ".");
            }
          } else {
            System.out.println("Invalid input! Please enter a number.");
            scanner.next(); // Clear invalid input
          }
        }
        return userInput;
  }


  //Starts a new game, is called at the beginning
  public void startGame() {
    changeScreen(new StartScreen());
    //Get user input
    int chosen = getInput();
    //Based on the chosen, display the screen
    if(chosen == 1) changeScreen(new GameScreen());
  }

}
