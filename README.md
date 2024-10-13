Here's an updated **README.md** file that explicitly mentions **unit testing**, **integration testing**, and **system testing**, and clearly states that this is a **backend project**.

---

# Ocean Cleanup Simulation: Save the Saardines

This project is part of the **Software Engineering Lab** and focuses on simulating efforts to clean up ocean garbage using fleets of ships managed by various corporations. The goal is to model the interaction between ships, garbage, and ocean conditions to simulate a cleanup process in a simplified, yet structured manner.

**Note:** This is a **backend project**, focused on designing and implementing simulation logic, algorithms, and efficient backend functionality. No frontend or user interface is part of this project.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Key Features](#key-features)
3. [System Design](#system-design)
4. [Ship Types](#ship-types)
5. [Garbage Types](#garbage-types)
6. [Map Configuration](#map-configuration)
7. [Events and Tasks](#events-and-tasks)
8. [Technical Details](#technical-details)
9. [Testing](#testing)
    - [Unit Testing](#unit-testing)
    - [Integration Testing](#integration-testing)
    - [System Testing](#system-testing)
10. [Tools and Technologies](#tools-and-technologies)
11. [Installation Guide](#installation-guide)
12. [Contributing](#contributing)
13. [License](#license)

---

## Project Overview

The **Ocean Cleanup Simulation** models a simplified system where different corporations use fleets of ships to clean garbage from the ocean. The simulation occurs on a hexagonal tile-based map representing the ocean and aims to capture the dynamics of real-world efforts to remove plastic, oil, and chemicals from ocean ecosystems.

Corporations, represented in the simulation, manage different ship types with varying capacities and behaviors. They collaborate to clean the ocean and respond to events, such as oil spills or pirate attacks, while optimizing their operations.

This is a **backend project** focused on simulation logic, data structures, and algorithms, with no graphical or user interface components. 

---

## Key Features

- **Hexagonal Tile-Based Map**: The map is divided into hexagonal tiles, each representing an ocean area with different properties such as currents, harbors, and garbage.
- **Multiple Ship Types**: Ships can scout for garbage, collect it, and coordinate with other ships for efficient operations.
- **Various Garbage Types**: The simulation includes different types of ocean garbage such as plastic, oil, and chemicals, each requiring specific cleanup strategies.
- **Dynamic Events**: Events like storms, oil spills, and pirate attacks add complexity and force corporations to adjust their strategies.
- **Task and Reward System**: Ships are assigned tasks like collecting garbage or exploring the map, and completing tasks results in rewards that enhance ship capabilities.

---

## System Design

The project is designed with flexibility in mind, making it adaptable to specification changes after the initial design phase. The design includes:

- **UML Diagrams**: Class diagrams, state diagrams, and sequence diagrams to illustrate the system structure and flow.
- **Modular Components**: Each class and function is structured to ensure minimal changes required when adapting to new requirements.
- **Simulation Behavior**: Ships operate based on their type and are influenced by tasks, events, and environmental factors like ocean currents.

### Design Phases:

1. **Design and Planning**: Initial phase with UML diagrams.
2. **Implementation**: Simulator logic and algorithms are developed and tested.
3. **Testing and Debugging**: Comprehensive unit, integration, and system tests ensure the simulator's correctness and robustness.

---

## Ship Types

Ships are the core actors in this simulation. Each ship belongs to a specific corporation and performs various tasks depending on its type:

- **Scouting Ships**: Locate garbage on the map and provide information to other ships. They have high speed but no capacity to collect garbage.
- **Coordinating Ships**: Direct the operations of other ships, optimizing their paths and tasks.
- **Collecting Ships**: Collect garbage from the ocean. These ships have the capacity to store garbage and transport it to designated harbors.

Each ship has properties like speed, fuel capacity, acceleration, and a visibility range. The interaction between ships and garbage is based on these properties and the tasks assigned.

---

## Garbage Types

The simulation models three main types of garbage found in the ocean:

1. **Plastic**: Common and found in large quantities. Requires cooperation between ships to collect effectively.
2. **Oil**: Primarily caused by spills. Oil can drift with ocean currents, spreading over large areas and complicating cleanup efforts.
3. **Chemicals**: Found near shorelines and shallow regions. Chemicals must be collected before they spread into the deep ocean, where they can disperse and be lost.

Garbage is represented by piles on specific tiles, and collecting ships must reach these tiles to clean the ocean.

---

## Map Configuration

The simulation operates on a **hexagonal grid map**, where each tile represents a specific ocean area with properties such as:

- **Land and Shore Tiles**: Ships cannot traverse land tiles, but shore tiles can host harbors and refuel ships.
- **Shallow Ocean and Deep Ocean Tiles**: Garbage and ships can move across these tiles. Deep ocean tiles may have ocean currents that influence the movement of both.
- **Harbors**: Ships can refuel and unload garbage at harbors.

**Ocean Currents**: These currents can drift both garbage and ships across tiles, creating additional challenges for ship navigation and garbage collection.

---

## Events and Tasks

The simulation introduces dynamic elements with **events** and **tasks**:

### Events:
- **Storms**: Temporarily impact an area of the ocean, sweeping garbage to adjacent tiles.
- **Oil Spills**: Introduce new garbage to the ocean in the form of oil, which spreads over several tiles.
- **Pirate Attacks**: Remove ships from the simulation.

### Tasks:
Ships are assigned tasks such as:
- **Collect Garbage**: Directs a ship to collect garbage from a specific tile.
- **Explore Map**: Sends a ship to explore a part of the ocean.
- **Cooperate**: Encourages ships to share information about garbage with other corporations.

Completing tasks rewards corporations with enhancements like **Telescope** (increases visibility range), **Container** (increases garbage capacity), and **Tracker** (attaches to garbage to track its location).

---

## Technical Details

The simulation is implemented using **Java** and emphasizes efficient algorithms, such as the **shortest path algorithm** for ship movement across the map. Key technical components include:

- **Command-Line Interface (CLI)**: Allows users to interact with the simulator via command-line inputs.
- **Build System**: Managed through **Gradle**, ensuring smooth project builds and testing.
- **Testing Framework**: Includes unit tests, integration tests, and system tests to validate all functionality.

### Shortest Path Algorithm:
Ships calculate the shortest path to reach their destinations while avoiding obstacles like restricted areas or land tiles.

### Code Quality:
The project adheres to strict code quality standards, ensuring that the implementation is both efficient and maintainable. It includes automatic code analysis using tools like `detekt` to detect any potential issues.

---

## Testing

This project uses a comprehensive testing approach to ensure that all simulation components work as expected. The testing is split into three key levels:

### Unit Testing
- Tests individual functions, classes, and methods in isolation.
- Focuses on validating small units of code, such as the movement logic of ships or the collection of garbage.

### Integration Testing
- Ensures that multiple components work together as expected.
- Tests the interactions between ships, garbage, and map configurations. For example, testing how ships interact with garbage across multiple tiles.

### System Testing
- Validates the entire simulator to ensure it meets the project requirements.
- Covers end-to-end functionality, simulating real-world conditions like oil spills, ship cooperation, and garbage collection.

These tests are automated and run regularly during development to ensure continuous functionality.

---

## Tools and Technologies

- **Languages**: Java, Kotlin (for auxiliary scripts)
- **Version Control**: Git
- **Build System**: Gradle
- **Testing**: JUnit (for unit, integration, and system tests)
- **Code Analysis**: Detekt (for code quality checks)

---

## Installation Guide

### Prerequisites:

- Java 17 or later
- Gradle (automatically handled by the included Gradle wrapper)
- Git for version control

### Steps to Run:

1. **Clone the Repository**:
    ```bash
    git clone <repository-url>
    cd ocean-cleanup-simulation
    ```

2. **Build the Project**:
    ```bash
    ./gradlew build
    ```

3. **Run the Simulation**:
    ```bash
    ./gradlew run
    ```

4. **Run Tests**:
    ```bash
    ./gradlew test
    ```


## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

This README provides detailed documentation for your **Ocean Cleanup Simulation** backend project. Let me know if you'd like any further changes!
