package com.todoapp.model;

import java.io.Serializable;

public class Task implements Serializable {
    private String text;
    private boolean done;

    public Task(String text) {
        this(text, false);
    }

    public Task(String text, boolean done) {
        this.text = text;
        this.done = done;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }

    // persist line format: 1|task text  or 0|task text
    public String serialize() {
        return (done ? "1|" : "0|") + text.replace("\n", "\\n");
    }

    public static Task deserialize(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        int idx = line.indexOf('|');
        if (idx < 0) return new Task(line, false);
        boolean d = line.charAt(0) == '1';
        String t = line.substring(idx + 1).replace("\\n", "\n");
        return new Task(t, d);
    }
}
