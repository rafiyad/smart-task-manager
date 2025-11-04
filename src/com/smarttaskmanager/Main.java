package com.smarttaskmanager;

import com.smarttaskmanager.model.Priority;
import com.smarttaskmanager.model.Status;
import com.smarttaskmanager.model.Task;
import com.smarttaskmanager.service.TaskManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final TaskManager taskManager = new TaskManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Background service to check for overdue tasks
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            List<Task> overdueTasks = taskManager.getOverdueTasks();
            if (!overdueTasks.isEmpty()) {
                System.out.println("\n[NOTIFICATION] The following tasks are overdue:");
                overdueTasks.forEach(System.out::println);
            }
        }, 0, 1, TimeUnit.MINUTES);

        // Main application loop
        while (true) {
            printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addTask();
                        break;
                    case 2:
                        viewTasks();
                        break;
                    case 3:
                        updateTaskStatus();
                        break;
                    case 4:
                        deleteTask();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scheduler.shutdown();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Smart Task Manager ---");
        System.out.println("1. Add Task");
        System.out.println("2. View Tasks");
        System.out.println("3. Update Task Status");
        System.out.println("4. Delete Task");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addTask() {
        try {
            System.out.print("Enter task description: ");
            String description = scanner.nextLine();

            System.out.print("Enter priority (HIGH, MEDIUM, LOW): ");
            Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Enter due date (YYYY-MM-DD): ");
            LocalDate dueDate = LocalDate.parse(scanner.nextLine());

            taskManager.addTask(description, priority, dueDate);
            System.out.println("Task added successfully.");
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Invalid input. Please check priority or date format.");
        }
    }

    private static void viewTasks() {
        System.out.println("\n--- All Tasks ---");
        // Using a lambda expression for sorting by priority
        taskManager.getAllTasks(Comparator.comparing(Task::getPriority))
                .forEach(System.out::println);
    }

    private static void updateTaskStatus() {
        try {
            System.out.print("Enter task ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter new status (PENDING, IN_PROGRESS, COMPLETED): ");
            Status status = Status.valueOf(scanner.nextLine().toUpperCase());

            if (taskManager.updateTaskStatus(id, status)) {
                System.out.println("Task status updated successfully.");
            } else {
                System.out.println("Task not found.");
            }
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter a valid ID and status.");
            scanner.nextLine(); // Clear invalid input
        }
    }

    private static void deleteTask() {
        try {
            System.out.print("Enter task ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (taskManager.deleteTask(id)) {
                System.out.println("Task deleted successfully.");
            } else {
                System.out.println("Task not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid ID.");
            scanner.nextLine(); // Clear invalid input
        }
    }
}
