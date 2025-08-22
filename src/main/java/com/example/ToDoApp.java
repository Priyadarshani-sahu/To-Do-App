package com.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class ToDoApp {
    private static final String FILE_NAME = "tasks.json";
    private static List<Task> tasks = new ArrayList<>();
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        loadTasks();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- To-Do List Menu ---");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter task title: ");
                    String title = sc.nextLine();
                    tasks.add(new Task(title));
                    saveTasks();
                    break;
                case 2:
                    System.out.println("Your Tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    break;
                case 3:
                    System.out.print("Enter task number to complete: ");
                    int index;
                    try {
                        index = Integer.parseInt(sc.nextLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input!");
                        break;
                    }
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markCompleted();
                        saveTasks();
                        System.out.println("Task marked completed!");
                    } else {
                        System.out.println("Invalid task number!");
                    }
                    break;
                case 4:
                    System.out.print("Enter task number to delete: ");
                    int delIndex;
                    try {
                        delIndex = Integer.parseInt(sc.nextLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input!");
                        break;
                    }
                    if (delIndex >= 0 && delIndex < tasks.size()) {
                        tasks.remove(delIndex);
                        saveTasks();
                        System.out.println("Task deleted!");
                    } else {
                        System.out.println("Invalid task number!");
                    }
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void saveTasks() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadTasks() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            Type taskListType = new TypeToken<List<Task>>() {}.getType();
            tasks = gson.fromJson(reader, taskListType);
            if (tasks == null) tasks = new ArrayList<>();
        } catch (IOException e) {
            tasks = new ArrayList<>();
        }
    }
}
