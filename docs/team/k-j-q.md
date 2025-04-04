# Kwa Jian Quan - Project Portfolio Page

## Overview

**Javatro** is a command-line roguelike deck-building game inspired by **Balatro**. It combines strategic poker-based gameplay with unique deck mechanics, offering players a minimalist yet engaging experience. The game features various deck types, blind levels, and scoring systems, providing replayability and depth.

### Summary of Contributions

- **Code Contributed**: [View my contributions on the tP Code Dashboard](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=k-j-q&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

- **Enhancements Implemented**:
  - Developed the `Round` class to manage the state and flow of a single game round, including configurations, scoring, and boss-specific rules.
  - Implemented the `RoundState` and `RoundConfig` classes to encapsulate round-specific state and configuration details.
  - Added boss-specific gameplay mechanics in the `BossType` and `Score` classes, such as restricting scoring for specific card types.
  - Enhanced the `RoundActions` class to handle card play and discard logic with validation.

- **Contributions to the UG**:
  - Added descriptions of the possible user inputs.

- **Contributions to the DG**:
  - Documented the design and implementation of the `Round` component, including UML diagrams and sequence diagrams.
  - Explained the observer pattern used in `RoundObservable` for notifying UI components of state changes.

- **Team-Based Tasks**:
  - Reviewed and tested core gameplay features to ensure consistency and correctness.
  - Collaborated with team members to integrate the `Round` component with the overall game architecture, ensuring smooth interactions with other components like `Deck`, `Player`, and `Boss`.
  - Assisted in integrating the `Round` component with the overall game flow.
  - Participated in team meetings to discuss design decisions, code reviews, and project planning.


- **Review/Mentoring Contributions**:
  - Reviewed pull requests related to gameplay mechanics and provided feedback on improving code quality and readability.
  - Reported bugs and suggested enhancements to the game mechanics, contributing to the overall quality of the project.