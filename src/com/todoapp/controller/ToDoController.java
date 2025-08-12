package com.todoapp.controller;

import com.todoapp.model.Task;
import com.todoapp.model.ToDoModel;
import com.todoapp.ui.TaskPanel;
import com.todoapp.ui.ToDoView;
import com.todoapp.ui.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ToDoController {
    private final ToDoModel model;
    private final ToDoView view;

    public ToDoController(ToDoModel model, ToDoView view) {
        this.model = model;
        this.view = view;
        init();
    }

    private void init() {
        refreshView();

        view.addButton.addActionListener(e -> addFromInput());
        view.inputField.addActionListener(e -> addFromInput());
        view.saveMenu.addActionListener(e -> { model.save(); showMessageDialog("Tasks saved successfully."); });
        view.exportMenu.addActionListener(e -> exportCSV());
        view.clearCompletedMenu.addActionListener(e -> { model.clearCompleted(); refreshView(); });

        view.searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { refreshView(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { refreshView(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { refreshView(); }
        });
    }

    private void addFromInput() {
        String text = view.inputField.getText().trim();
        if (text.isEmpty() || text.equals("What needs to be done?")) return;
        Task t = new Task(text);
        model.addTask(t);
        view.inputField.setText("");
        view.inputField.requestFocus(); // Keep focus for adding multiple tasks
        addTaskPanel(t, true);
        updateStatus();
    }

    private void addTaskPanel(Task task, boolean animate) {
        TaskPanel tp = new TaskPanel(task);
        tp.setOnEdit(t -> {
            model.updateTask(t);
            updateStatus();
        });

        tp.getDeleteButton().addActionListener(e -> animateDelete(tp));

        view.tasksContainer.add(tp, 0);
        view.tasksContainer.revalidate();
        view.tasksContainer.repaint();

        if (animate) {
            tp.setAlpha(0f);
            Timer timer = new Timer(25, null); // Smoother animation timing
            timer.addActionListener(new ActionListener() {
                float a = 0f;
                public void actionPerformed(ActionEvent ev) {
                    a += 0.08f; // Slower, smoother fade-in
                    if (a >= 1f) {
                        a = 1f;
                        timer.stop();
                    }
                    tp.setAlpha(a);
                }
            });
            timer.start();
        } else {
            tp.setAlpha(1f);
        }
    }

    private void animateDelete(TaskPanel tp) {
        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            float a = tp.getAlphaFloat();
            public void actionPerformed(ActionEvent e) {
                a -= 0.10f; // Slightly faster fade-out
                if (a <= 0f) {
                    ((Timer) e.getSource()).stop();
                    view.tasksContainer.remove(tp);
                    model.removeTask(tp.getTask());
                    view.tasksContainer.revalidate();
                    view.tasksContainer.repaint();
                    updateStatus();
                } else {
                    tp.setAlpha(a);
                }
            }
        });
        timer.start();
    }

    private void refreshView() {
        view.tasksContainer.removeAll();
        String query = view.searchField.getText().trim().toLowerCase();
        List<Task> tasks = model.getTasks();
        for (Task t : tasks) {
            if (query.isEmpty() || t.getText().toLowerCase().contains(query)) {
                addTaskPanel(t, false);
            }
        }
        view.tasksContainer.revalidate();
        view.tasksContainer.repaint();
        updateStatus();
    }

    private void updateStatus() {
        int total = model.getTasks().size();
        long done = model.getTasks().stream().filter(Task::isDone).count();
        view.statusLabel.setText(done + " of " + total + " tasks completed");
    }

    private void showMessageDialog(String message) {
        UIManager.put("OptionPane.background", UIConstants.COLOR_PANEL);
        UIManager.put("Panel.background", UIConstants.COLOR_PANEL);
        UIManager.put("OptionPane.messageFont", UIConstants.FONT_GENERAL);
        UIManager.put("OptionPane.messageForeground", UIConstants.COLOR_TEXT);
        UIManager.put("Button.background", UIConstants.COLOR_ACCENT);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", UIConstants.FONT_BOLD);
        JOptionPane.showMessageDialog(view.frame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportCSV() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(view.frame) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".csv")) {
                f = new File(f.getParentFile(), f.getName() + ".csv");
            }
            try (PrintWriter pw = new PrintWriter(new FileWriter(f))) {
                pw.println("status,task");
                for (Task t : model.getTasks()) {
                    pw.printf("%s,\"%s\"%n", t.isDone() ? "DONE" : "TODO", t.getText().replace("\"", "\"\""));
                }
                showMessageDialog("Exported to " + f.getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
                showMessageDialog("Export failed: " + ex.getMessage());
            }
        }
    }
}