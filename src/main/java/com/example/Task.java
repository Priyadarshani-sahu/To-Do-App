package com.example;

public class Task {
    private String title;   // Correct
    private boolean completed;

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        this.completed = true;
    }

    @Override
    public String toString() {
        return (completed ? "[@]" : "[]") + title;
    }
}
