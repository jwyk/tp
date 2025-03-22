/**
 * The {@code Manager} package contains the core game management and command execution
 * logic. It follows the Command design pattern to handle various user actions and game state
 * changes.
 *
 * <p>This package is responsible for managing game flow, handling user commands, and coordinating
 * between the game model and the user interface.
 *
 * <h2>Classes in this package:</h2>
 *
 * <ul>
 *   <li>{@link Javatro.Javatro.Manager.Command} - Interface defining executable game commands.
 *   <li>{@link Javatro.Javatro.Manager.DiscardCardsCommand} - Command for discarding selected cards.
 *   <li>{@link Javatro.Javatro.Manager.ExitGameCommand} - Command for terminating the game.
 *   <li>{@link Javatro.Javatro.Manager.JavatroManager} - Central controller managing game state and user
 *       input.
 *   <li>{@link Javatro.Javatro.Manager.LoadGameScreenCommand} - Command for starting the game and loading
 *       the game screen.
 *   <li>{@link Javatro.Javatro.Manager.LoadOptionsScreenCommand} - Command for displaying the options menu.
 *   <li>{@link Javatro.Javatro.Manager.LoadStartScreenCommand} - Command for displaying the main menu.
 *   <li>{@link Javatro.Javatro.Manager.MakeSelectionCommand} - Command for selecting cards to play or
 *       discard.
 *   <li>{@link Javatro.Javatro.Manager.PlayCardsCommand} - Command for playing selected cards.
 *   <li>{@link Javatro.Javatro.Manager.ResumeGameCommand} - Command for returning to the main game screen.
 * </ul>
 */
package Javatro.Manager;
