# Mark Neo Qi Hao - Project Portfolio Page

---

## **Overview**

**Javatro** is a terminal-based card game inspired by *Balatro*. 
The game features a comprehensive evaluation of poker hands, strategic deck-building, and an interactive, 
ANSI art–enhanced interface. Leveraging multiple design patterns—such as Singleton (for UI management), 
Factory (for custom exceptions), Template (for screen hierarchy), and Observer (for dynamic content updates)
—the application seamlessly integrates core game logic with advanced screen management and robust resource handling.

---

## **Code Contribution**

### **Core Game Logic**
- Developed components for evaluating poker hands (e.g., *HandResult*) and managing scoring using level-based adjustments.
- Implemented custom exception handling via *JavatroException* with detailed, color-coded messages.
- Managed game resources with classes such as *PlanetCard* (handling upgrades and persistent game state) and *PokerHand* 
  (immutable record capturing play metrics).

### **User Interface & Screen Management**
- Created the main UI controller (singleton *UI* class) to centralize screen state, input parsing, and rendering with ANSI styling.
- Developed multiple screen classes (*CardSelectScreen*, *DeckSelectScreen*, *DeckViewScreen*, *DiscardCardScreen*, etc.) 
  to support a fluid, interactive experience.

### **Architectural Enhancements**
- Applied design patterns—Singleton, Factory, Template, and Observer—to ensure maintainability, testability, and scalability.
- Hosted complete code on the [tP Code Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=markneoneo&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other).

---

## **Enhancements Implemented**

### UI Module Architecture & Screen Management
- **Features & Depth:**
    - **Centralized UI Controller:** Implemented a singleton *UI* class to manage dynamic screen states, input parsing, 
      and ANSI-based output (including bordered text and dynamic card art).
    - **Abstract & Concrete Screens:** Developed an abstract *Screen* class with concrete subclasses 
      (like *GameScreen*, *DeckViewScreen*, and *CardSelectScreen*) to facilitate smooth gameplay transitions.
- **Challenges:**
    - Standardized output using custom abstract printing functions.
    - Addressed alignment issues with Unicode symbols to ensure legible, letter-based card designs.

### Enhanced Game Logic and Dynamic Card Rendering
- **Game Logic Improvements:**
    - Engineered a robust poker hand evaluation system supporting special hands 
      (e.g., Flush Five, Full House, Five of a Kind) along with level-based scaling.
    - Developed nuanced exception handling with *JavatroException*, providing detailed error messages.
    - Linked poker hand evaluations to dynamic bonus adjustments through the *PlanetCard* class.
- **Dynamic Card Rendering:**
    - Created *CardRenderer* to convert card objects into fixed-width ASCII art with ANSI colors.
    - Incorporated the Observer pattern to trigger dynamic updates (for example, updating scores in real time).
- **Challenges:**
    - Replaced emojis/Unicode due to inconsistent terminal support and spacing.
    - Devised a grid system for aligning eight cards in two rows without overflow.

### Terminal Compatibility & UI Enrichment
- **Features & Depth:**
    - Ensured compatibility across CMD, PowerShell, and Unix terminals by replacing colored emojis with ANSI-styled letters (e.g., red `H` for hearts).
    - Implemented fallback rendering strategies for unsupported terminals.
- **Additional Enhancements:**
    - **Evaluation of Poker Hands:** Reinforced the hand evaluation algorithm to support a variety of cases using configurable scoring factors.
    - **Exception Handling:** Centralized error management with JavatroException, which standardizes feedback with color-coded outputs.
    - **Dynamic Planet Card Management:** Integrated upgrade mechanisms that synchronize with persistent storage, ensuring accurate game progression tracking.
    - **Rich UI Experience:** Leveraged extensive ANSI escape codes and detailed ASCII art, facilitating smooth screen transitions and robust input parsing.

---

## Contributions to Documentation

### User Guide (UG)
- **Clear Organization and Structure:**  
  The guide is structured with a detailed Table of Contents, which enhances navigation. 
  Each section—from Introduction and Quick Start, through in-depth feature descriptions, to Known Issues and FAQs—is clearly delineated. 
  This structured approach reflects best practices in technical writing and ensures that both beginners and advanced users can quickly locate relevant information.


- **Attention to Formatting and Standardization:**  
  The use of Markdown formatting (headings, subheadings, horizontal rules) provides a clean and professional appearance. 
  Standardized section headings and consistent styling (e.g., bold for key terms and italics for emphasis) help create a uniform, 
  accessible look throughout the guide. The consistent use of code snippets for command-line instructions adds clarity and precision.


- **Comprehensive Content Coverage:**  
  Every aspect of the game is explained—from the installation process and basic navigation to detailed explanations of game mechanics such as deck selection, blinds, and scoring. 
  The inclusion of tables and lists where appropriate makes the technical details easy to digest.


- **Effective Use of Visual Aids:**  
  Images and screenshots (e.g., for the Main Menu, Deck Selection, Game Interface, and various in-game scenarios) are strategically placed. 
  These visuals not only break up text-heavy sections but also provide real-world examples of the UI, helping players set correct expectations about the gameplay experience. They play an essential role in bridging the gap between written instructions and actual game play.


- **Detailed Explanations and Step-by-Step Guidance:**  
  Each feature section not only outlines what the feature does but also explains how the feature fits into the overall game flow. 
  For instance, the descriptions for selecting runs, choosing decks, playing cards, and discarding cards are supplemented with both instructions and example commands. This not only educates users on how to navigate the game but also reduces potential errors.


- **User-Focused Insights and Warnings:**  
  The guide includes practical tips (e.g., reminders about terminal size and full-screen mode) and troubleshooting advice in the Known Issues section. 
  This attention to real-world usage scenarios shows a deep understanding of the end-user experience and highlights proactive measures to enhance playability.


- **Consistency with Game Theme and Branding:**  
  The guide maintains consistency with the game’s branding—references to the game’s name, integrated audio components, 
  and even credits for music and inspiration give it a cohesive identity. 
  This consistency strengthens the user’s connection to the game and reinforces a polished product image.


- **Attention to Detail:**  
  Even minor aspects, such as the inclusion of a Glossary to define game-specific terminology, 
  reflect an attention to detail that benefits both newcomers and seasoned players. 
  The guide’s careful treatment of technical details and its comprehensive FAQ section further underscore the thoroughness of the documentation.

### Developer Guide (DG)
- **Documentation Enhancements:**
    - Detailed the implementation of the UI (singleton pattern, screen transitions, ANSI challenges).
    - Discussed design trade-offs related to emoji/Unicode compatibility across different terminals.


- **Visual Aids:**
    - Provided comprehensive UML diagrams for core components such as *UI*, *Screen*, and *CardRenderer*.
    - Updated diagrams to reflect improvements in game logic and user input processing.

---

## Contributions to Team-Based Tasks

- **Collaboration & Coordination:**
    - Set up the GitHub team organization and repository, maintaining the issue tracker and labels.
    - Ensured overall project codebase quality and standard (Naming conventions, Package management, JavaDocs etc.)
    - Frequently reviews and comments on pull requests, providing constructive feedback.
    - Conducted terminal compatibility tests across Windows, macOS, and Unix systems.


- **Mentoring & Code Review:**
    - Actively mentored teammates on ANSI best practices and terminal debugging techniques.
    - Provided leadership in organizing design review sessions and conducted thorough code reviews via pull requests.
    - Shared technical insights during collaborative sessions and guided new developers in understanding the codebase structure.


- **Community Engagement:**
    - Performed extensive testing on other teams' project applications to help hunt for bugs.
    - Offered structured, constructive feedback on other teams' project documentation (UG and DG).

---

## Class Inventory

### 1. Core Game Logic (Package: javatro.core)

#### **HandResult**
- **Purpose:** Evaluates a list of 1 to 5 cards and determines the highest-ranking poker hand.
- **Key Features:**
    - Validates input and differentiates edge cases (such as all-identical cards via “Flush Five”).
    - Uses maps to count card ranks and suits; employs helper methods to detect straights, flushes, and royal flushes.
    - Applies a priority-based evaluation order to ensure the strongest valid hand is returned.
- **Complexity:** Balances multiple conditional checks and data-structure manipulation to cover a wide range of poker hand types.

#### **JavatroException**
- **Purpose:** Provides custom, color-coded error handling for the game.
- **Key Features:**
    - Extends Java’s Exception with constructors that include ANSI formatting.
    - Offers a suite of static factory methods to generate exceptions for various error conditions (e.g., invalid hand sizes, menu errors, and resource issues).
- **Complexity:** Although conceptually simple, its comprehensive set of tailored methods ensures consistency in error reporting throughout the project.

#### **PlanetCard**
- **Purpose:** Represents upgradeable game elements (planet cards) linked to specific poker hand types and governs bonus increments.
- **Key Features:**
    - Uses static registries (via `EnumMap`) to manage enhancement levels and enforce a single instance per hand type.
    - Integrates with persistent storage to load and update levels.
    - Provides both base and level-scaled chip/multiplier increments.
- **Complexity:** Combines state management, global progression tracking, and I/O integration to support consistent game advancement.

#### **PokerHand (Record)**
- **Purpose:** Encapsulates the evaluated result of a poker hand along with its progression state.
- **Key Features:**
    - Computes level-adjusted chips and multipliers by combining base values (from the embedded `HandType` enum) with current levels (stored in `PlanetCard`).
    - Maintains play count statistics and supports play count incrementation via an external tracker (`JavatroCore`).
    - The nested `HandType` enum configures all supported hand types with base scoring metrics.
- **Complexity:** Merges simple immutable record design with dynamic external state, ensuring both conciseness and flexibility in scoring.

---

### 2. Display & User Interface (Package: javatro.display)

#### **UI**
- **Purpose:** Serves as the central controller for all terminal-based UI operations.
- **Key Features:**
    - Implements a Singleton pattern to manage screen transitions, ANSI formatting, and user input parsing.
    - Defines a comprehensive set of ANSI escape codes and formatting utilities (centering text, padding, and clearing the screen).
    - Loads and renders external ASCII art and handles fallback cases.
- **Complexity:** Integrates real-time formatting and input management with robust error-checking (using assertions) and supports dynamic layout adjustments.

---

### 3. Screen Management (Package: javatro.display.screens)

#### **Screen (Abstract Base Class)**
- **Purpose:** Acts as a template for all display screens, ensuring a consistent contract for rendering content and handling command options.
- **Complexity:** Provides a common framework for diverse screen types, centralizing command maps and display routines.

#### **CardSelectScreen & DiscardCardScreen**
- **Purpose:**
    - **CardSelectScreen**: Provides an abstract foundation for screens that involve card selection, 
      including sorting (by rank or suit) and display of ASCII-rendered cards.
    - **DiscardCardScreen**: Specializes the card selection process to enforce a maximum limit for discards.
- **Key Features:**
    - Retrieves the current player hand from game state, applies reordering, and displays card art with dynamically generated indices.
- **Complexity:** Merges game state handling with advanced visual output, ensuring that interactive card selections are both logical and aesthetically presented.

#### **DeckSelectScreen & DeckViewScreen**
- **Purpose:**
    - **DeckSelectScreen**: Allows players to select from distinct deck configurations, each with unique game modifiers.
    - **DeckViewScreen**: Displays a tabular, ANSI-styled summary of the deck’s composition by suit and rank.
- **Key Features:**
    - Uses detailed string manipulation and matrix-building (for counts of cards) to create a formatted table.
    - Offers clear visual cues via ANSI colors and dynamic table components.
- **Complexity:** Involves careful coordination of data aggregation and formatted output to deliver an intuitive, visually coherent deck summary.

#### **Additional Screens**
- **Examples:** GameScreen, HelpScreen, PlayCardScreen, and others.
- **Purpose:** Manage specific phases of the game (active play, instruction, endgame scenarios) by providing custom layouts and navigation options.
- **Complexity:** Varies based on dynamic game state and interaction requirements, 
  yet all adhere to the overarching design patterns established in the base screens.

---

### 4. Testing

#### **Testing Classes & Methodologies**
- **Unit Testing:**
    - Comprehensive JUnit tests have been written for core classes such as `HandResult`, `JavatroException`, and `PlanetCard` 
      to ensure correct behavior under edge scenarios (e.g., invalid inputs, duplicate cards).
    - Tests validate scoring adjustments, error handling, and special-hand detection across various poker hand configurations.
- **Integration Testing:**
    - Simulated game sessions test interactions between UI components and core logic, verifying proper screen transitions, 
      accurate user input parsing, and seamless ANSI rendering.
- **UI Testing:**
    - Extensive manual test runs have validated the visual appearance of ANSI art, dynamic table generation in `DeckViewScreen`, 
      and cross-platform terminal compatibility.
- **Complexity:**
    - The testing suite is integrated into the development workflow, ensuring that both isolated units and the combined 
      system function reliably under real-world conditions.
