package com.todoapp.ui;

import com.todoapp.model.Task;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class TaskPanel extends JPanel {
    private final Task task;
    private final JCheckBox checkBox;
    private final JLabel label;
    private final JButton deleteButton;
    private final JButton editButton;
    private final JTextField editField;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    private float alpha = 1f;
    private Consumer<Task> onEdit;

    public TaskPanel(Task task) {
        this.task = task;
        setLayout(new BorderLayout(15, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 12));

        checkBox = new JCheckBox();
        checkBox.setOpaque(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- View/Edit Card Panel ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        // View Mode: JLabel
        label = new JLabel();
        label.setFont(UIConstants.FONT_GENERAL);
        label.setForeground(UIConstants.COLOR_TEXT);
        updateLabelText();
        cardPanel.add(label, "view");

        // Edit Mode: JTextField
        editField = new JTextField();
        editField.setFont(UIConstants.FONT_GENERAL);
        editField.setBackground(UIConstants.COLOR_PANEL);
        editField.setForeground(UIConstants.COLOR_TEXT);
        editField.setCaretColor(UIConstants.COLOR_ACCENT);
        editField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.COLOR_ACCENT),
                BorderFactory.createEmptyBorder(2, 0, 2, 0)
        ));
        cardPanel.add(editField, "edit");

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setOpaque(false);

        editButton = createActionButton("Edit", UIConstants.COLOR_TEXT_MUTED, UIConstants.COLOR_SUCCESS);
        deleteButton = createActionButton("Delete", UIConstants.COLOR_TEXT_MUTED, UIConstants.COLOR_DANGER);

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(checkBox, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height + 10));

        setupActionListeners();
    }

    private JButton createActionButton(String text, Color normalColor, Color hoverColor) {
        JButton button = new JButton(text);

        button.setForeground(normalColor);
        button.setFont(UIConstants.FONT_GENERAL.deriveFont(13f));
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect for foreground text
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(normalColor);
            }
        });

        return button;
    }

    private void setupActionListeners() {
        checkBox.addActionListener(e -> {
            task.setDone(checkBox.isSelected());
            updateLabelText();
            if (onEdit != null) onEdit.accept(task);
        });

        editButton.addActionListener(e -> switchToEditMode());

        editField.addActionListener(e -> commitEdit()); // On Enter press

        editField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                commitEdit();
            }
        });

        editField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private void switchToEditMode() {
        editField.setText(task.getText());
        cardLayout.show(cardPanel, "edit");
        editField.requestFocusInWindow();
        editField.selectAll();
    }

    private void commitEdit() {
        if (editField.isVisible()) {
            String newText = editField.getText().trim();
            if (!newText.isEmpty()) {
                task.setText(newText);
                updateLabelText();
                if (onEdit != null) onEdit.accept(task);
            }
            switchToViewMode();
        }
    }

    private void cancelEdit() {
        switchToViewMode();
    }

    private void switchToViewMode() {
        cardLayout.show(cardPanel, "view");
    }

    private void updateLabelText() {
        String safeHtml = escapeHtml(task.getText());
        if (task.isDone()) {
            label.setText("<html><body style='color:" + toHex(UIConstants.COLOR_TEXT_MUTED) + "'><strike>" + safeHtml + "</strike></body></html>");
        } else {
            label.setText("<html><body style='color:" + toHex(UIConstants.COLOR_TEXT) + "'>" + safeHtml + "</body></html>");
        }
    }

    private String toHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }
    private String escapeHtml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>");
    }

    // Getters and Setters
    public void setOnEdit(Consumer<Task> onEdit) { this.onEdit = onEdit; }
    public JButton getDeleteButton() { return deleteButton; }
    public JCheckBox getCheckBox() { return checkBox; }
    public Task getTask() { return task; }
    public void setAlpha(float a) { this.alpha = Math.max(0f, Math.min(1f, a)); repaint(); }
    public float getAlphaFloat() { return alpha; }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(UIConstants.COLOR_PANEL);
        g2.fillRoundRect(0, 0, getWidth(), getHeight() - 5, 20, 20);
        super.paintComponent(g2);
        g2.dispose();
    }
}