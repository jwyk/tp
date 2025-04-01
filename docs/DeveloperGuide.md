# Developer Guide – UI Implementation  

## 1. Overview  
The **UI module** manages screen transitions, user input, and formatted content rendering (e.g., bordered menus, card art). Designed using the **Singleton pattern**, it ensures a single point of control for display operations. This guide details the architecture, key components, and enhancements like dynamic screen rendering and transition logic.  

---

## 2. Architectural Design  

### 2.1. High-Level Architecture  
The UI follows a **Model-View-Controller (MVC)**-inspired design:  
- **Model**: `JavatroCore` (game state).  
- **View**: `Screen` subclasses (e.g., `GameScreen`, `StartScreen`).  
- **Controller**: `UI` class (manages transitions, input parsing).  

#### Key Design Patterns:  
1. **Singleton Pattern**: Ensures a single `UI` instance for centralized screen management.  
2. **Observer Pattern**: `GameScreen` listens to `JavatroCore` for state changes (e.g., score updates).  
3. **State Pattern**: Screens encapsulate state-specific behavior (e.g., `HelpScreen` vs. `GameScreen`).  

### 2.2. Rationale for Key Decisions  
- **ANSI/Unicode Formatting**: Enables rich terminal UIs without external libraries.  
- **Abstract `Screen` Class**: Promotes code reuse (e.g., `displayOptions()` for command menus).  
- **Centralized `UI` Class**: Simplifies debugging and ensures consistent transitions.  

---

## 3. Component-Level Design  

### 3.1. The `UI` Class  
**Responsibilities**:  
- **Screen Management**: Stores static references to all screens and handles transitions via `setCurrentScreen()`.  
- **Formatted Output**: Methods like `printBorderedContent()` and `centerText()` standardize layouts.  
- **Utility Functions**: `clearScreen()`, `getCardArtLines()` (renders ASCII card art).  

**Key Code Snippet**:  
```java  
public void setCurrentScreen(Screen screen) {  
    previousScreen = currentScreen;  
    currentScreen = screen;  
    currentScreen.displayScreen(); // Triggers screen-specific rendering  
    PARSER.getOptionInput(); // Handle user input  
}  
```  

### 3.2. The `Screen` Class Hierarchy  
- **Abstract `Screen` Class**:  
  - Defines `displayScreen()` (abstract) and `displayOptions()` (common command menu).  
  - Manages `commandMap` (list of user-selectable options).  

- **Concrete Screens**:  
  - **`GameScreen`**: Displays real-time game stats, card art, and listens for state changes.  
  - **`HelpScreen`**: Shows static help content.  

**Example: `GameScreen` Property Listener**  
```java  
public void propertyChange(PropertyChangeEvent evt) {  
    switch (evt.getPropertyName()) {  
        case "currentScore":  
            roundScore = (Long) evt.getNewValue();  
            break;  
        // ... other cases  
    }  
    displayScreen(); // Re-render UI on state change  
}  
```  

---

## 4. UML Diagrams  

### 4.1. Class Diagram  
```mermaid  
classDiagram  
    class UI {  
        -currentScreen: Screen  
        -previousScreen: Screen  
        +setCurrentScreen(Screen)  
        +printBorderedContent(...)  
        +centerText(...)  
    }  

    class Screen {  
        -commandMap: List~Option~  
        +displayScreen()*  
        +displayOptions()  
    }  

    class GameScreen {  
        -blindScore: int  
        -roundScore: long  
        +propertyChange(...)  
    }  

    UI --> Screen : manages  
    Screen <|-- GameScreen : extends  
    GameScreen ..|> PropertyChangeListener : implements  
```  
*Simplified to highlight core relationships. The `UI` manages `Screen` instances, and `GameScreen` extends `Screen` while observing game state changes.*  

### 4.2. Sequence Diagram – Dynamic GameScreen Rendering  
```mermaid  
sequenceDiagram  
    participant JavatroCore  
    participant UI  
    participant GameScreen  

    JavatroCore->>GameScreen: firePropertyChange("currentScore", 200)  
    GameScreen->>GameScreen: Update roundScore  
    GameScreen->>GameScreen: Re-render UI  
    GameScreen->>UI: Call displayScreen()  
    UI->>UI: Clear terminal  
    UI->>GameScreen: Render card art/stats  
```  
*Illustrates how `GameScreen` updates its display when the game state changes.*  

---

## 5. Key Enhancements  

### 5.1. Enhancement: Screen Transition Mechanism  
**Implementation**:  
- **Transition Messages**: ANSI-formatted logs (e.g., `Transitioning to: GameScreen`).  
- **Back Navigation**: `previousScreen` allows reverting to prior states (e.g., exiting `HelpScreen`).  

**Sequence Diagram**:  
```mermaid  
sequenceDiagram  
    actor User  
    participant UI  
    participant StartScreen  
    participant GameScreen  

    User->>UI: Enters "play" command  
    UI->>UI: setCurrentScreen(GameScreen)  
    UI->>StartScreen: Save as previousScreen  
    UI->>GameScreen: displayScreen()  
    GameScreen-->>UI: Rendered content  
```  

**Alternatives Considered**:  
- **Stack-Based History**: Rejected due to limited navigation depth requirements.  
- **Immediate Input Handling**: Chose deferred parsing via `Parser` to decouple input from rendering.  

### 5.2. Enhancement: Dynamic Card Rendering  
- **Card Art Generation**: `CardRenderer` converts `Card` objects to ASCII art with ANSI colour formatting.  
- **Grid Layout**: Renders 8 cards in two rows, spaced with ANSI backgrounds.  

**Code Snippet**:  
```java  
public static List<String> getCardArtLines(List<Card> hand) {  
    List<String> lines = new ArrayList<>();  
    String[][] renderedCards = hand.stream().map(CardRenderer::renderCard).toArray(String[][]::new);  
    // Combine lines horizontally  
    return lines;  
}  
```

### **5.3. Challenge: Emoji and Unicode Compatibility**  

#### **Initial Attempt**  
Emojis and Unicode symbols (e.g., 🃏, ♥️, ♥) were initially incorporated into the UI to enhance visual appeal. For example:  
```java  
// Early prototype (discarded)  
private static String getSuitSymbol(Card.Suit suit) {  
    return switch (suit) {  
        case HEARTS -> "♥️";  
        case DIAMONDS -> "♦️";  
        case CLUBS -> "♣️";  
        case SPADES -> "♠️";  
    };  
}  
```  

#### **Challenges**  
1. **Inconsistent Spacing**:  
   - Emojis/Unicode symbols vary in display width (e.g., `♥️` vs. `♠️`), causing misalignment in card art.  
   - Example: A heart symbol might be very slightly larger than a regular spacing, breaking grid layouts.  

2. **Terminal Compatibility**:  
   - Terminals like **Windows CMD / Windows Powershell** require `chcp 65001` to display Unicode, which is error-prone for users.  
   - Colored emojis are unsupported in many terminals, leading to monochrome or placeholder symbols (e.g., `�`).  

3. **ANSI Overlap**:  
   - Combining ANSI color codes with Unicode symbols introduced unpredictable formatting (e.g., background colors bleeding into adjacent text).  

#### **Final Approach**  
To ensure cross-terminal compatibility and consistent spacing, **letters** (`H`, `D`, `C`, `S`) replaced symbols:  
```java  
// Current implementation (CardRenderer.java)  
private static String getSuitSymbol(Card.Suit suit) {  
    return switch (suit) {  
        case HEARTS -> "H";  
        case DIAMONDS -> "D";  
        case CLUBS -> "C";  
        case SPADES -> "S";  
    };  
}  
```  
- **Advantages**:  
  - Fixed-width characters ensure alignment in card art.  
  - Works universally across terminals without configuration.  
  - ANSI colors apply cleanly to letters (e.g., `H` in red for hearts).  

---

### **UML Diagrams**  

#### **CardRenderer Class Diagram**  
```mermaid  
classDiagram  
    class CardRenderer {  
        +renderCard(Card): String[]  
        -getSuitSymbol(Suit): String  
        -getColour(Suit): String  
    }  
```  
*The `CardRenderer` class maps suits to letters (`H`, `D`, `C`, `S`) and colors using ANSI codes.*  

#### **Card Rendering Sequence Diagram**  
```mermaid  
sequenceDiagram  
    participant GameScreen  
    participant CardRenderer  
    participant Card  

    GameScreen->>CardRenderer: renderCard(card)  
    CardRenderer->>Card: getSuit()  
    CardRenderer->>CardRenderer: getSuitSymbol() → "H"  
    CardRenderer->>CardRenderer: getColour() → ANSI code  
    CardRenderer-->>GameScreen: [" H ", ...]  
    GameScreen->>UI: Display formatted card art  
```

### **Key Takeaways**  
- **Prioritize Functionality Over Aesthetics**: Letters ensured reliability, while emojis introduced terminal-specific bugs.  
- **Test Across Environments**: The decision to avoid Unicode was driven by rigorous testing in CMD, PowerShell, and Unix terminals.  
- **Code Hygiene**: Unused symbols (e.g., `♠` in `UI`) should be removed in future refactoring.  

This approach balances visual clarity with technical robustness, ensuring the UI works seamlessly for all users.

---

## 6. Future Considerations  

1. **GUI Framework Integration**: Migrate to JavaFX for animations and mouse support.  
2. **Theming System**: Allow users to customize colors via config files.  
3. **Accessibility Mode**: Add high-contrast themes and screen-reader support.  

---

## 7. Conclusion  
The UI module’s design prioritizes modularity, extensibility, and terminal compatibility. By combining design patterns like Singleton and Observer with ANSI formatting, it delivers a responsive and visually consistent experience. Future developers can extend it by adding new `Screen` subclasses or integrating modern GUI frameworks.


## **The Ante and Blind class system:**

### **1. Introduction**

The **Ante and Blind System** in the game determines the minimum required score to beat for each round and regulates the increasing stakes as the game progresses. The **Ante** sets a base score to beat, while the **Blind** system introduces different blind levels (SMALL, LARGE, BOSS) each with a unique multiplier score effect to ensure competitive play. This section provides an in-depth look into its implementation, design choices, and a UML sequence diagram illustrating interactions.

### **2. Implementation**

#### **2.1 Class Overview**

The Ante class manages the ante value and blind progression. There are a total of 8 antes where each ante consists of 3 blinds (SMALL, LARGE, BOSS). The ante class maintains the current ante level the gameplay is at , determines the betting stakes, and updates the blind stage as the game progresses. 

#### **2.2 Attributes**

_private static final int MAX_ANTE_COUNT_ = 8; – Defines the maximum ante level progression.
_private static int anteCount;_ – Stores the current ante level.
_private Blind blind;_ – Represents the current blind stage.
_private final int[] anteScore;_ – An array storing predefined ante values for each level.

#### **2.3 Enum: Blind Levels**

The **Blind** enum represents different blind levels, each with a unique multiplier affecting the ante base score. Additionally, the **SMALL** and **LARGE** blinds are optional, giving the players a choice to accept or reject, meanwhile the **BOSS** blind is compulsory for the players to proceed to the next round.

**Code Example** :

public enum Blind {
SMALL_BLIND(1.0, "SMALL BLIND"),
LARGE_BLIND(1.5, "LARGE BLIND"),
BOSS_BLIND(2.0, "BOSS BLIND");

private final double multiplier;
private final String name;

Blind(double multiplier, String name) {
this.multiplier = multiplier;
this.name = name;
}
public double getMultiplier() {
return multiplier;
}
public String getName() {
return name;
}
}

#### **2.4 Methods**
**_public Ante()_:** 
Initializes ante at level 1 with Small Blind.

**public int getRoundScore()** :
Returns the ante score multiplied by the unique blind multiplier.

**_public void setAnteCount(int anteCount)_:**
Updates the ante count within valid bounds.

**_public void resetAnte()_:**
Resets ante to level 1 and Small Blind.

**_public void nextRound()_:**
Progresses to the next blind level or increases ante level when Boss Blind is reached.

**### 3. Design Considerations**

## ** 3.1 Alternatives Considered**

**1. Fixed Ante and Blind System**:
   Pros: Simple to implement and requires minimal logic.
   Cons: Lacks adaptability, making the game less engaging over time.

**2. Dynamic Blind Adjustment**:
   Pros: Adjusts difficulty dynamically, providing a more balanced progression.
   Cons: Requires additional tracking and logic to manage changes effectively.

   ## **3.2 Chosen Approach**
   To balance simplicity and dynamic progression, the enum-based structured Blind System. It cycles through Small Blind → Large Blind → Boss Blind, ensuring controlled progression while increasing the ante at the end of each cycle. This approach keeps gameplay engaging without overcomplicating the logic. Moreover the array-based ante progression simplifies lookup and ensures consistent bet increases. This structure prevents unfair jumps in bet sizes, ensuring a fairer experience for the players.

## **Class Diagram:** 

![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)