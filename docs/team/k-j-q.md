# Kwa Jian Quan - Project Portfolio Page

## Overview

**Javatro** is a command-line roguelike deck-building game inspired by **Balatro**. It combines strategic poker-based gameplay with unique deck mechanics, offering players a minimalist yet engaging experience. The game features various deck types, blind levels, and scoring systems, providing replayability and depth.

---

## Summary of Contributions

### Code Contributions
- [View my contributions on the tP Code Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=k-j-q&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

### Technical Contributions

#### Key Classes Developed
1. **`Round` Class**
   - Implemented the primary game round management system using object-oriented principles
   - Designed the class to follow Single Responsibility Principle by delegating specific functionality to helper classes
   - Created methods for tracking game state, evaluating win conditions, and applying boss-specific modifiers
   - Implemented proper exception handling with custom error messages for invalid game actions

2. **`RoundState` Class**
   - Developed a dedicated state management class to encapsulate the mutable aspects of a game round
   - Implemented methods for tracking scores, remaining plays/discards, and player resources
   - Created a clean interface for state modifications with validation

3. **`RoundActions` Class**
   - Designed a utility class to handle card play and discard logic with robust validation
   - Implemented the ActionResult pattern to return multiple pieces of information about game actions
   - Created validation methods that enforce game rules for card plays and discards

4. **`RoundConfig` Class**
   - Developed a configuration class to store game round parameters and settings
   - Implemented boss-type specific configurations that modify gameplay rules
   - Created constants and configuration methods with appropriate access modifiers

5. **`RoundObservable` Class**
   - Implemented the Observer pattern to notify UI components of state changes
   - Created methods for registering listeners and broadcasting state updates

#### Comprehensive Testing
- **`RoundTest` Class**
   - Created extensive unit tests covering all aspects of the Round component
   - Implemented tests for normal gameplay, edge cases, and error conditions
   - Developed helper methods to reduce code duplication in tests
   - Achieved high test coverage for the Round component

- **`RoundActionsTest` Class**
   - Developed unit tests for the RoundActions class to ensure correct card play and discard logic
   - Implemented tests for all possible action outcomes, including valid and invalid scenarios
  
### Enhancements Implemented

#### Game Round Management
- **Round Structure and Flow**:
  - Developed the complete round lifecycle management system including initialization, play, discard, and completion
  - Implemented the `Round` class to manage the state and flow of a single game round, including configurations, scoring, and boss-specific rules
  - Created the `RoundState` and `RoundConfig` classes to properly separate concerns following SOLID principles

#### Boss Mechanics
- **Boss-Specific Gameplay Rules**:
  - Implemented unique gameplay modifications for different boss types:
    - `THE_NEEDLE`: Limited plays to 1 per round
    - `THE_WATER`: Removed ability to discard cards
    - `THE_PSYCHIC`: Required exactly 5 cards in every hand
  - Added boss-specific scoring restrictions in the `Score` class

#### Card Actions System
- **Card Play and Discard Logic**:
  - Enhanced the `RoundActions` class with a stateless design for better testability
  - Implemented comprehensive validation for all card actions
  - Created a flexible `ActionResult` pattern to return multiple values from actions

#### Observer Pattern Implementation
- **UI Update Mechanism**:
  - Implemented the `RoundObservable` class using the Observer pattern
  - Created a clean interface for UI components to register as listeners
  - Enabled automatic UI updates when round state changes

### Documentation Contributions

#### Developer Guide (DG)
- **Round Component Documentation**:
  - Created comprehensive class diagrams showing the relationships between Round classes
  - Developed detailed sequence diagrams for key methods:
    - `playCards`: Showing the flow from user input to score calculation
    - `discardCards`: Illustrating the card replacement mechanism
    - `isWon` and `isRoundOver`: Documenting win condition evaluation
  - Added technical documentation explaining the Observer pattern implementation
  - Wrote detailed explanations of the Round component's responsibilities and design decisions

#### User Guide (UG)
- **Player Instructions**:
  - Added clear descriptions of possible user inputs for playing and discarding cards
  - Documented round mechanics and scoring rules for player reference
  - Created examples showing valid card selections and their outcomes

### Team-Based Contributions

#### Code Integration and Architecture
- **Component Integration**:
  - Designed the Round component to integrate cleanly with other system components
  - Implemented proper interfaces for the Deck, Player, and Score components
  - Collaborated with team members to ensure consistent coding style and architecture

#### Code Review and Refinement
- **Pull Request Reviews**:
  - Reviewed pull requests related to gameplay mechanics
  - Provided detailed feedback on code quality and suggested improvements
  - Identified potential bugs and edge cases in gameplay implementations

#### Testing Coordination
- **Test Framework Development**:
  - Established patterns for comprehensive unit testing
  - Created helper methods used across multiple test classes
  - Implemented robust test cases covering normal operation and edge cases

| Element         | Class, %   | Method, %    | Line, %       | Branch, %    |
|-----------------|------------|--------------|---------------|--------------|
| round           | 100% (7/7) | 79% (62/78)  | 85% (152/177) | 66% (44/66)  |
| └─ Round        | 100% (2/2) | 66% (24/36)  | 79% (57/72)   | 73% (19/26)  |
| └─ RoundActions | 100% (2/2) | 100% (9/9)   | 95% (41/43)   | 68% (22/32)  |
| └─ RoundConfig  | 100% (1/1) | 100% (12/12) | 100% (21/21)  | 50% (1/2)    |
| └─ RoundObservable | 100% (1/1) | 66% (2/3)    | 90% (9/10)    | 100% (0/0)   |
| └─ RoundState   | 100% (1/1) | 83% (15/18)  | 77% (24/31)   | 33% (2/6)    |

### Additional Contributions

#### Feature Enhancements
- **Ante and Blind System**:
  - Implemented the foundational Ante object for round initialization
  - Created a flexible blind system with different difficulty levels
  - Added boss blinds with special gameplay modifications

#### Bug Fixes
- Fixed duplicate handling issues in user input (#136)
- Resolved uncaught index error when drawing from an empty deck (#128)
- Corrected UI to reflect max hands accurately (#124)
- Addressed bug in `HoldingHand Play()` method (#62)

#### Code Quality Improvements
- Implemented assertions throughout the Round component for robust error detection
- Refactored the Round class to improve separation of concerns
- Enhanced exception handling with descriptive error messages
- Improved code readability and maintainability by following OOP principles
- Ensured consistent naming conventions and code style across the Round component
- Conducted code reviews to maintain high standards of code quality

#### Documentation Updates
- Updated Developer Guide with detailed explanations of the Round component
- Contributed to the User Guide with clear instructions for gameplay
- Kept documentation synchronized with code changes throughout development
- Created diagrams and flowcharts to illustrate complex interactions within the Round component