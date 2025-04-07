# Saravanan Swetha - Project Portfolio Page

## Overview

**Javatro** is a command-line roguelike deck-building game inspired by **Balatro**, blending poker-style strategy with distinctive deck mechanics. It delivers a streamlined yet captivating experience through **diverse deck types**, escalating **blind levels**, and dynamic **scoring systems**, ensuring both depth and replayability.

---

### Summary of Contributions

### Code Contributions
- [View my contributions on the tP Code Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=swethacool&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code&tabOpen=true&tabType=authorship&tabAuthor=swethacool&tabRepo=AY2425S2-CS2113-W13-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Technical Contributions

#### Key Classes Developed

1. **`Ante` Class:** 
   - Designed and implemented the core logic for the ante system and blind progression mechanics using object-oriented principles.
   - Used enums to define and encapsulate Small Blind, Large Blind, and Boss Blind, each with custom multipliers and display names.
   - Designed the class to follow Single Responsibility Principle, focusing solely on progression logic and round multipliers.
   - Applied defensive programming and clear method contracts to ensure state transitions remained consistent.
   
   **Developed methods to:**
   - Increment the ante and transition between blinds.
   - Retrieve current ante/blind values for display and scoring.
   - Reset ante/blind state for new games or testing.

2. **`Blind` Class** 
   - Defined a strongly-typed enum to represent the three blind levels used in each round.
   - The blinds progress and rotate between Small, Large, and Boss blind for every ante. 
   - Handled blind progression logic, determining the next blind based on current state and user choice.
   - Associated each blind with:
     1. A specific multiplier for score calculation.
     2. A user-friendly label for use in the UI and help screens.
   - Provided a next() method to control valid blind progression during gameplay.

3. **`AcceptBlindOption` Class**
   - Implemented the logic that handles player acceptance of the current blind and progresses the game accordingly.
   - Integrated ante and blind progression into this class to ensure smooth transitions between rounds.
   - Triggered appropriate game state updates, including score calculation and blind level advancement.
   - Ensured validation of game conditions before accepting the blind to prevent illegal state transitions.

4. **`RejectBlindOption` Class**
   - Designed this class to represent the player’s decision to reject the current blind.
   - Coordinated with game state classes to update the round without progressing the blind level.
   - Added safeguard checks to avoid abuse of the reject mechanic and maintain game balance.
   - Ensured consistent UI and console feedback when a blind is rejected.

5. **UI Box & Function Usage for `AcceptBlindOption` and `RejectBlindOption` classes**
   - Created the UI box for blind acceptance/rejection display, reusing functions to maintain code efficiency and prevent duplication. 
   - Leveraged existing UI handling logic elsewhere to keep the code clean and consistent across different game scenarios.
   
6**`Help Menu` Class**
   #### Implemented In-Game Help Menu
   - Developed a modular command-line interface that responds to user input for different help topics
   
   1. **Game Introduction Section:** 
      - Introduced Javatro as a roguelike deck-building game inspired by Balatro.
      - Explained the goal of the game: building a deck and progressing through challenges using poker hands.
      - Motivated players with an engaging statement: "Think strategically, manage your deck wisely, and see how far you can go!".
      - Crafted a friendly, simple tone to welcome players and ease them into the game mechanics.

   2. **How to Play Section:** 
      - Wrote a beginner-friendly walkthrough explaining key game mechanics, such as drawing cards, playing hands, and advancing blinds.
      - Broke down complex gameplay sequences into digestible steps for new players.
      - Organized the steps into a structured, easy-to-read format for players to follow throughout the game.
      
   3. **Tips & Tricks Section**
      - Curated strategic advice for maximizing scores, managing discards/plays, and timing blind acceptance.
      - Included advanced tactics and considerations for boss blinds and deck composition.
      - Suggested saving powerful hands for crucial rounds and using special cards wisely.
      - Included a motivational message: "Good luck and have fun!"
      
   4. **Rules Section**
      - Compiled a clear set of core rules governing poker hand evaluations, round progression, and resource management.
      - Described the game-ending condition and the progressively challenging rounds.
      - Emphasized strategic hand selection and deck management.

#### Comprehensive Testing
- **`AnteTest` Class**
  - **Initial Setup Verification**: Ensured correct initialization with starting values for ante count, blind level, and round score.
  - **Blind Multiplier Accuracy**: Validated that the multipliers for different blind levels (SMALL_BLIND, LARGE_BLIND, BOSS_BLIND) were correctly applied to the score calculations.
  - **Round Progression & Reset Logic**: Confirmed that the blind levels and ante count progress correctly across rounds and reset appropriately after reaching maximum values.
  - **Randomized Testing & Edge Cases**: Tested various random ante counts and blind selections to ensure accurate score calculations and proper handling of dynamic game scenarios, including verifying the ante count cap.

#### Enhancements Implemented

##### Blind Acceptance and Rejection Mechanics
- Added logic to allow players to accept or reject the current blind, providing a more strategic layer to gameplay and to mimic the real gameplay of Balatro.

##### Enum-Based Blind System
- Simplified blind progression through a structured enum system.
- Ensures controlled and predictable blind levels without complex logic.

##### Ante Progression 
- Prevents unfair jumps in bet sizes, ensuring a balanced game experience.

##### Randomized Blind and Score Testing
- Implemented comprehensive testing to handle random blind selections and score calculations.
- Validated edge cases for the maximum ante count, blind cycling limits, and the impact of various blind levels on the score.
- Ensured the gameplay logic behaves as expected across multiple randomized scenarios, enhancing robustness.

Overall: The dynamic system allows for engaging gameplay while avoiding excessive complexity in tracking and logic.

### Documentation Contributions

#### Developer Guide (DG)
1. **Ante and Blind System:**
   - Explained about the ante progression and blind levels (SMALL, LARGE, BOSS).
   - Explained about the regulation of the game’s score to beat and increasing stakes as the game advances.
   - Explained about the defined methods for the Ante class
   - Explained about the implementation of AcceptBlindOption and RejectBlindOption classes to manage player decisions regarding the blind, integrated with game state classes for smooth progression.
   - Highlighted how the ante logic and player choices are encapsulated and structured using OOP principles.
   
2. Created a detailed sequence diagram to illustrate the flow of blind options and ante progression.

3. Created comprehensive class diagrams to illustrate the structure and relationships between:
  - Ante class, which manages ante levels, blind stages, and score calculation.
  - Blind enum, which defines multipliers and names for SMALL, LARGE, and BOSS blinds.
  - AcceptBlindOption and RejectBlindOption, which implement the Option interface to handle blind decisions.

4. Elaborated on the design considerations to illustrate how the dynamic approach maintains player engagement while minimizing overhead in logic and state tracking.

5. Added JavaDocs to the relevant classes to ensure better understandability and readability of what each method does.

#### User Guide (UG)
- **Help Menu**:
  - Designed Help Menu layout with 4 clear sections
  - Wrote concise descriptions for: Game Introduction, Rules, How To Play, Tips & Tricks.
  - Included visuals for user guidance.
  - Focused on clarity and ease for new players

### Team-Based Contributions

#### System Design & Integration
- **Component Integration**:
  - Designed the Ante and Blind System to integrate cleanly with core components.
  - Implemented clear interfaces between the Ante and Blind system to ensure smooth integration and consistent data flow.
  - Collaborated with teammates to ensure consistent coding style and seamless architecture
  - Reused UI code to prevent duplication, better readability, and also to follow the coding standards. 

#### Code Review and Refinement
- **Pull Request Reviews**:
  - Reviewed Pull Requests for gameplay features
  - Provided constructive feedback, suggested code improvements, and caught edge cases
  - Helped to identify potential bugs.
  - Ensured consistent architecture and clean integration between different team members.

#### Testing Coordination
- Set up reusable test helpers and patterns.
- Wrote thorough unit tests covering core and edge behaviors.
- Strengthened code reliability through collaborative test design.

### Additional Contributions

#### Feature Enhancements 
- **nextRound() Enhancement:**
- Improved progression by advancing the blind level or increasing ante when the Boss Blind is reached.
- Ensures smoother transitions and maintains game balance through controlled progression.
- Calling from the Round class to getRoundScore() which returns the round score based on the ante and its corresponding blind multiplier.
- Setting the conditions for the round to progress to next level and the winning terms. 

#### Code Quality Improvements
- Implemented assertions throughout the Ante and Blind component to ensure strong error detection.
- Refactored Ante and Blind class to improve separation of concerns.
- Ensure effective exception handling with descriptive and informative error messages.
- Improved code readability and maintainability by following OOP principles.
- Ensured consistent naming conventions and code style across the Ante and Blind component.
- Conducted code reviews to maintain high standards of code quality
- Established clear interfaces between components to maintain modularity and facilitate team collaboration.
- Contributed to consistent architectural design, aligning with team conventions and ensuring maintainable codebase structure.
- Integrated the Ante and Blind system seamlessly with core modules like Deck, Player, and Score, ensuring smooth game flow.
- Ensured the display of the Help Menu aligned with the UI code already done by another team member to ensure consistency. 

#### Documentation Updates
- Updated Developer Guide with detailed explanations of the Ante and Blind component
- Had a fair share of contributions to the User Guide by elaborating on the Help Menu curated just for the players. 
- Created class and sequence diagrams to illustrate complex interactions within the Ante and Blind component

### **Contributions Beyond the Project Team**
- Reported **10 bugs** in other teams’ tp.
- Gave structured and constructive feedback on other teams' DG and TP code work.


   
