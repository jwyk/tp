```mermaid
sequenceDiagram
    participant #58;User
    participant #58;JavatroCore
    participant #58;CommandMap
    participant #58;Options
    participant #58;UI
    participant #58;Manager
    activate #58;User
    activate #58;UI
    #58;User ->> #58;UI: Issue Command
    activate #58;Manager
    #58;UI ->> #58;Manager: Forward Command
    activate #58;JavatroCore
    #58;Manager -->> #58;JavatroCore: Process Command
    #58;JavatroCore ->> #58;CommandMap: Check Command Type
    activate #58;CommandMap
    #58;CommandMap ->> #58;Options: Execute Option (e.g., Sort, Discard, Select)
    deactivate #58;CommandMap
    activate #58;Options
    #58;Options -->> #58;JavatroCore: Return Result
    deactivate #58;Options
    #58;JavatroCore ->> #58;Manager: Update State
    deactivate #58;JavatroCore
    #58;Manager --> #58;UI: Render Updated State
    deactivate #58;Manager
    deactivate #58;UI
    deactivate #58;User
```