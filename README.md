# Smart Task Manager

## Overview

The Smart Task Manager is a command-line interface (CLI) application developed in Java to help users manage their daily tasks efficiently. It provides core functionalities for task creation, viewing, updating, and deletion, along with a "smart" feature for overdue task notifications.

## Features

*   **Add Task:** Create new tasks with a description, priority (HIGH, MEDIUM, LOW), and a due date.
*   **View Tasks:** Display all active tasks, sorted for better readability.
*   **Update Task Status:** Change the status of an existing task (PENDING, IN_PROGRESS, COMPLETED).
*   **Delete Task:** Remove tasks that are no longer needed.
*   **Overdue Task Notifications:** A background service periodically checks for and notifies the user about tasks that have passed their due date and are not yet completed.

## Technologies Used

This project demonstrates the application of several key Java concepts:

*   **Object-Oriented Programming (OOP):** Structured with classes like `Task`, `TaskManager`, `Priority`, and `Status` to ensure modularity and reusability.
*   **Collections Framework & Generics:** Utilizes `List<Task>` and `CopyOnWriteArrayList` for efficient and thread-safe management of task collections, leveraging generics for type safety.
*   **Exception Handling:** Implements custom exceptions (e.g., `TaskNotFoundException`) to gracefully manage errors and provide meaningful feedback to the user.
*   **Concurrency & Multithreading:** Features a `ScheduledExecutorService` to run a background thread for real-time overdue task notifications without blocking the main application flow.
*   **Lambda Expressions & Functional Programming:** Employs lambda expressions and the Stream API for concise and efficient data processing, such as sorting and filtering tasks.

## How to Run

To compile and run this application:

1.  **Navigate to the `src` directory:**
    ```bash
    cd D:\Work\Smart Task Manager\src
    ```
2.  **Compile the Java files:**
    ```bash
    javac com/smarttaskmanager/main/*.java com/smarttaskmanager/model/*.java com/smarttaskmanager/service/*.java com/smarttaskmanager/exception/*.java
    ```
3.  **Run the application:**
    ```bash
    java com.smarttaskmanager.Main
    ```

Follow the on-screen menu to interact with the Smart Task Manager.