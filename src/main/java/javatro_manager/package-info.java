/**
 * The {@code javatro_manager} package contains the core game management and command execution
 * logic. It follows the Command design pattern to handle various user actions and game state changes.
 *
 * <p>This package is responsible for managing game flow, handling user commands, and coordinating
 * between the game model and the user interface.</p>
 *
 * <h2>Classes in this package:</h2>
 * <ul>
 *   <li>{@link javatro_manager.Command} - Interface defining executable game commands.</li>
 *   <li>{@link javatro_manager.DiscardCardsCommand} - Command for discarding selected cards.</li>
 *   <li>{@link javatro_manager.ExitGameCommand} - Command for terminating the game.</li>
 *   <li>{@link javatro_manager.JavatroManager} - Central controller managing game state and user input.</li>
 *   <li>{@link javatro_manager.LoadGameScreenCommand} - Command for starting the game and loading the game screen.</li>
 *   <li>{@link javatro_manager.LoadOptionsScreenCommand} - Command for displaying the options menu.</li>
 *   <li>{@link javatro_manager.LoadStartScreenCommand} - Command for displaying the main menu.</li>
 *   <li>{@link javatro_manager.MakeSelectionCommand} - Command for selecting cards to play or discard.</li>
 *   <li>{@link javatro_manager.PlayCardsCommand} - Command for playing selected cards.</li>
 *   <li>{@link javatro_manager.ResumeGameCommand} - Command for returning to the main game screen.</li>
 * </ul>
 */
package javatro_manager;