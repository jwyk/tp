/**
 * Contains classes that define the structure and actions of a game round in Javatro.
 * <p>
 * The {@code javatro.core.round} package provides the core components for managing the state and 
 * actions within a game round. These classes handle everything from game configuration and round state 
 * to the game actions and observer notifications related to the round.
 * </p>
 * <p>
 * The package includes:
 * <ul>
 *     <li>{@link javatro.core.round.Round} - Represents a round in the Javatro game, managing the round's flow and logic.</li>
 *     <li>{@link javatro.core.round.RoundActions} - Implements the game actions available in a round, without direct dependencies on the Round class.</li>
 *     <li>{@link javatro.core.round.RoundObservable} - Handles observer notifications for round state changes, also without direct Round dependencies.</li>
 *     <li>{@link javatro.core.round.RoundConfig} - Stores configuration details for a game round, including rules and parameters.</li>
 *     <li>{@link javatro.core.round.RoundState} - Encapsulates the state of a round, including score, play limits, and player resources. This state is used by the {@link Round} class.</li>
 * </ul>
 * </p>
 */
package javatro.core.round;
