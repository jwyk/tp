# Javatro User Guide

**Javatro** is a command line card game based on **Balatro**, designed to be played via a Command Line Interface (CLI). It offers various deck options, scoring mechanisms, and strategic gameplay. This guide will walk you through the features, usage, and functionalities of the application.

- [What is Javatro?](#what-is-javatro)
- [Quick start](#quick-start)
- [Features](#features)
  - [Starting a game](#starting-a-game)
  - [Selecting a deck](#selecting-a-deck)
  - [Choosing a blind](#choosing-a-blind)
  - [Playing cards](#playing-cards)
  - [Discarding cards](#discarding-cards)
  - [Viewing Poker Hands](#viewing-poker-hands)
  - [Viewing the deck](#viewing-the-deck)
  - [Saving progress](#saving-progress)
  - [Loading progress](#loading-progress)
  - [Exiting the game](#exiting-the-game)
  - [Undo & Redo](#undo--redo)
  - [Scoring system](#scoring-system)
- [FAQ](#faq)
- [Known issues](#known-issues)
- [Command summary](#command-summary)


### What is Javatro?

#### Introduction
**Javatro** is a strategic, card-based game inspired by Poker but with a twist. The objective of the game is to score the highest points by creating valid poker hands from the cards dealt. It adds a layer of complexity with various deck types and special mechanics, making it a unique experience.

---

#### Objective
The objective of **Balatro** is to accumulate points by forming valid Poker hands. Players must strategically choose which cards to play, discard, or hold onto to achieve the highest-scoring hand.

---

### Core Mechanics

#### 1. Deck Types
In **Balatro**, players can choose from various decks, each with unique rules or bonuses. The standard decks include:

- **Red Deck**: Grants the player one additional discard per round.
- **Blue Deck**: Grants the player one additional hand per round.
- **Checkered Deck**: Starts with 26 Hearts and 26 Spades, offering more opportunities to form suited hands.
- **Abandoned Deck**: Removes all face cards (King, Queen, Jack) from the deck, making it harder to form high-ranking hands.

---

#### 2. Blinds
Before the start of a game round, players select a **Blind** which determines the minimum score they need to beat to proceed. The available blinds are:

- **Small Blind**: A low score requirement (e.g., 300 points).
- **Large Blind**: A moderate score requirement (e.g., 450 points).
- **Boss Blind**: A high score requirement (e.g., 600 points).

Blinds influence the difficulty of the round and the rewards earned upon completion.

---

#### 3. Rounds and Hands
The game progresses through several **Rounds**, with each round having a limited number of **Hands**. The player must achieve the required score within the allowed hands to progress.

- **Ante**: This represents the round number, starting from 1 and increasing with each successful completion.
- **Hands**: The number of attempts available to reach the required score. This is usually limited and can be affected by deck type (e.g., Blue Deck offers an extra hand).

---

#### 4. Jokers’ Effects
The game may have **Joker Slots** that provide special abilities or bonuses when utilized. By default, these slots are empty but may be activated based on game progression or deck type.

---

#### 5. Cards and Poker Hands
Players are dealt cards at the start of each round. The objective is to arrange these cards into valid **Poker Hands**. Common Poker Hands include:

- **High Card**: The highest card in the hand when no other combination is made.
- **Pair**: Two cards of the same rank.
- **Two Pair**: Two separate pairs.
- **Three of a Kind**: Three cards of the same rank.
- **Straight**: Five consecutive cards of varying suits.
- **Flush**: Five cards of the same suit.
- **Full House**: A combination of a Three of a Kind and a Pair.
- **Four of a Kind**: Four cards of the same rank.
- **Straight Flush**: Five consecutive cards of the same suit.
- **Royal Flush**: Ace, King, Queen, Jack, Ten of the same suit.

Each Poker Hand is worth a specific amount of points. Higher-ranking hands yield more points.

---

### Gameplay Flow

1. **Start Game**  
   Choose a deck from the available options.

2. **Select Blind**  
   Choose a blind (Small, Large, or Boss) to determine the target score for the round.

3. **Play a Hand**  (Keep playing until you hit the required target score or until you have no more hands left)
   - View your dealt cards.
   - Choose to **Play Cards**, **Discard Cards**, or **View Poker Hands** to evaluate your current hand.  
   - Cards played are assessed, and points are awarded based on the hand formed.

4. **Save or Resume**  
   The game allows saving progress and resuming from previous saves.

5. **Achieve Score & Progress**  
   Continue playing hands until the required score is met. Progress to the next round if successful.  

6. **End Game**  
   The game ends if you fail to meet the required score within the allowed hands, or if you choose to exit.

---

### Winning the Game
The game continues until you fail to reach the required score for a particular blind level. Aim to form the highest-scoring poker hands and choose the best deck for your strategy.

---

### Tips for New Players
1. **Understand Deck Mechanics**: Different decks have varying effects. Choose one that suits your playstyle.  
2. **Plan Your Hands**: Don’t rush through rounds. Consider which cards to play or discard carefully.  
3. **Keep Track of Score Requirements**: The blind you choose affects your gameplay strategy.  
4. **Utilize Jokers When Available**: Jokers may offer special abilities; use them wisely.  

---



## Quick Start
1. Ensure you have Java 17 or above installed on your system.
2. Clone the repository from your desired source.
3. Navigate to the project root directory.
4. Run the jar using:
```
java -jar Javatro.jar
```

---

## Features

### Start Screen
![Start Screen](start_screen.png)
This is the initial screen of the game where you are presented with the following options:
- `1. Start Game`
- `2. Help Menu`
- `3. Exit Game`


- ### Deck Selection Screen
![Deck Selection](deck_selection.png)
After selecting `1. Start Game`, you will be prompted to select a deck. Available options:
- `1. Red Deck`: +1 Discards per Round
- `2. Blue Deck`: +1 Hands per Round
- `3. Checkered Deck`: Start with 26 Hearts, 26 Spades
- `4. Abandoned Deck`: Start with no Face Cards (K, Q, J)
---

### Blind Selection Screen
![Blind Selection](blind_selection.png)
After selecting a deck, you will proceed to select a Blind level:
- `1. Accept Blind`
- `2. Reject Blind`
---

### Game Screen
![Game Screen](game_screen.png)
The main game interface where you can view your cards, current score, and game status. The menu options are:
- `1. Play Cards`
- `2. Discard Cards`
- `3. View Poker Hands`
- `4. View Deck`
- `5. Main Menu`
- `6. Exit Game`
---


## FAQs

### How do I navigate through the application?
Navigation throughout the application is done via **numbered options**. For example, the main menu may present:
```
1. New Game
2. Load Game
3. Exit
```
To start a new game, enter `1` and press Enter.

Similarly, in-game options are presented as:
```
1. Select Cards
2. Sort by Suit
3. Sort by Rank
4. Check Poker Hand
5. Save Game
6. Resume Game
7. Exit
```

Selecting an option is done by entering the corresponding number.
---

#### Command Usage Examples
```
> 1
Selected card: [3 of Hearts]

> 2
Cards sorted by suit.

> 4
Poker Hand: Full House

> 5
Game saved successfully.

> 6
Game loaded successfully.
```

---

## Glossary
- **Card**: A single playing card.
- **Deck**: A collection of cards.
- **Poker Hand**: A specific combination of cards as defined in poker rules.

## Acknowledgements
- Original game concept from **Balatro**.

