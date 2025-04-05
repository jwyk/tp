# Kwa Jian Quan - Project Portfolio Page

## Overview

**Javatro** is a roguelike deck-building game played via the command line, drawing inspiration from Balatro. It blends poker strategy with innovative deck mechanics, delivering a streamlined yet deeply engaging experience. With multiple deck types, escalating blind levels, and dynamic scoring systems, the game offers rich replayability and strategic depth.


As the **Test Lead**, I took charge of managing the scoring algorithm

## Summary of Contributions

### **Code Contributed**
[Link to tP Code Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=markneoneo&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

### **Features and Enhancements Implemented**:
1. **Joker System**
    - **Features**: Implemented the _Joker_ mechanic, where special effects get applied to cards or hands to enhance your overall score.
    - **Depth**:
        - Implemented an abstract `Joker` class and related sub-classes to implement the Joker mechanic.
        - Implemented the `HeldJokers` class to hold `Jokers` that the user has in the game.
        - Designed the system of giving each player a `Joker` card everytime a Boss Blind is won.
        - Created the `JokerFactory` class using the _Factory_ design pattern to distribute a random `Joker` card.
    - **Considerations**:
        - In normal Balatro, there are many types of Jokers that affect more than just affect your score - such as increasing discards or changing hand sizes. This was considered, but discarded due to time constraints for v2.0.
        - For Jokers that affect score, there are 3 main types: **+Chips**, **+Mult** and **xMult** Jokers. For Javatro, we decided to focus on the first 2 types of Jokers, as **xMult** would increase the complexity of testing for score.
        - There are 2 types of trigger effects, `ONCARDPLAY` and `AFTERHANDPLAY`. Other ways of triggering the Jokers such as "on discard" were considered but later disregarded as they were too complex to test.


2. **Scoring Algorithm**
   - **Features**: Developed the `Score` class that manages the scoring algorithm of a played hand every round, including factors such as boss-specific rules and Joker effects.
   - **Depth**:
     - Integrated the **Joker** and **Ante** system to work with the Scoring Algorithm 

3. **Deck and Cards in Hand** 
   - **Features**: Implemented `HoldingHand` class that manages the cards a user holds during a round, and the `Deck` holding the cards in a round.


4. **Different Deck Types**
   - **Features**: Implemented different deck types of **Red**, **Blue**, **Checkered** and **Abandoned** to enhance the gameplay experience for unique runs.
   - **Depth**:
     

### **Contributions to the UG**:

### **Contributions to the DG**:
  - Documented design and implementation of the `Score` component, including UML diagrams and sequence diagrams for respective methods.

### **Team-Based Tasks**:
- Reviewed and tested core gameplay features to ensure consistency and correctness.
- Collaborated with team members to integrate the `Score` component with the overall game architecture, ensuring smooth interactions with other components like `Round`, `
- Assisted in integrating the `Score` component with the overall game flow.
- Participated in team meetings to discuss design decisions, code reviews, and project planning.


### **Review/Mentoring Contributions**:
- Reviewed pull requests related to gameplay mechanics and provided feedback on improving code quality and readability.
- Reported bugs and suggested enhancements to the game mechanics, contributing to the overall quality of the project.