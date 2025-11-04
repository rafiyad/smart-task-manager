package com.smarttaskmanager.service;

import com.smarttaskmanager.exception.TaskNotFoundException;
import com.smarttaskmanager.model.Priority;
import com.smarttaskmanager.model.Status;
import com.smarttaskmanager.model.Task;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class TaskManager {
    private final List<Task> tasks = new CopyOnWriteArrayList<>();
    private int nextId = 1;

    // Add a new task
    public synchronized void addTask(String description, Priority priority, LocalDate dueDate) {
        tasks.add(new Task(nextId++, description, priority, dueDate));
    }

    // Get all tasks, sorted by a given comparator
    public List<Task> getAllTasks(Comparator<Task> comparator) {
        return tasks.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    // Get all tasks with default sorting (by ID)
    public List<Task> getAllTasks() {
        return getAllTasks(Comparator.comparing(Task::getId));
    }

    // Find a task by its ID
    public Task getTaskById(int id) throws TaskNotFoundException {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found."));
    }

    // Update a task's status
    public boolean updateTaskStatus(int id, Status status) {
        try {
            Task task = getTaskById(id);
            task.setStatus(status);
            return true;
        } catch (TaskNotFoundException e) {
            return false;
        }
    }

    // Remove a task by its ID
    public boolean deleteTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    // Get tasks that are overdue
    public List<Task> getOverdueTasks() {
        LocalDate today = LocalDate.now();
        return tasks.stream()
                .filter(task -> task.getDueDate().isBefore(today) && task.getStatus() != Status.COMPLETED)
                .collect(Collectors.toList());
    }
}
