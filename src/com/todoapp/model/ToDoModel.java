package com.todoapp.model;

import java.io.*;
import java.util.*;

public class ToDoModel {
    private final List<Task> tasks = new ArrayList<>();
    private final File saveFile;

    public ToDoModel() {
        saveFile = new File(System.getProperty("user.home"), ".todo_tasks.txt");
        load();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void addTask(Task t) {
        tasks.add(0, t); // newest on top
        save();
    }

    public void removeTask(Task t) {
        tasks.remove(t);
        save();
    }

    public void updateTask(Task t) {
        save();
    }

    public void clearCompleted() {
        tasks.removeIf(Task::isDone);
        save();
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile))) {
            for (Task t : tasks) bw.write(t.serialize() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        tasks.clear();
        if (!saveFile.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(saveFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Task t = Task.deserialize(line);
                if (t != null) tasks.add(t);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
