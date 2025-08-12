package com.todoapp.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ToDoView {
    public final JFrame frame;
    public final JTextField inputField;
    public final JButton addButton;
    public final JPanel tasksContainer;
    public final JScrollPane scrollPane;
    public final JLabel statusLabel;
    public final JTextField searchField;
    public final JMenuItem saveMenu;
    public final JMenuItem exportMenu;
    public final JMenuItem clearCompletedMenu;

    public ToDoView() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        frame = new JFrame("Modern ToDo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(680, 850);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(UIConstants.COLOR_BACKGROUND);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(UIConstants.COLOR_BACKGROUND);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inputField = new JTextField("What needs to be done?");
        inputField.setFont(UIConstants.FONT_INPUT);
        inputField.setBackground(UIConstants.COLOR_PANEL);
        inputField.setForeground(UIConstants.COLOR_TEXT_MUTED);
        inputField.setCaretColor(UIConstants.COLOR_TEXT);
        inputField.setBorder(UIConstants.BORDER_COMPONENT);
        inputField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (inputField.getText().equals("What needs to be done?")) {
                    inputField.setText("");
                    inputField.setForeground(UIConstants.COLOR_TEXT);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (inputField.getText().isEmpty()) {
                    inputField.setForeground(UIConstants.COLOR_TEXT_MUTED);
                    inputField.setText("What needs to be done?");
                }
            }
        });

        // --- MODIFIED Add Button (Text only) ---
        addButton = new JButton("Add");
        addButton.setFont(UIConstants.FONT_BOLD);
        addButton.setForeground(Color.WHITE);
        addButton.setToolTipText("Add Task");
        addButton.setBackground(UIConstants.COLOR_ACCENT);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        UIConstants.setButtonHoverEffect(addButton, UIConstants.COLOR_ACCENT, UIConstants.COLOR_ACCENT_LIGHT);

        JPanel inputArea = new JPanel(new BorderLayout(10, 10));
        inputArea.setOpaque(false);
        inputArea.add(inputField, BorderLayout.CENTER);
        inputArea.add(addButton, BorderLayout.EAST);

        searchField = new JTextField();
        searchField.setToolTipText("Search tasks (type to filter)");
        searchField.setFont(UIConstants.FONT_GENERAL);
        searchField.setBackground(UIConstants.COLOR_PANEL);
        searchField.setForeground(UIConstants.COLOR_TEXT);
        searchField.setCaretColor(UIConstants.COLOR_TEXT);
        searchField.setBorder(UIConstants.BORDER_COMPONENT);




        topPanel.add(inputArea, BorderLayout.CENTER);
        topPanel.add(searchField, BorderLayout.SOUTH);

        tasksContainer = new JPanel();
        tasksContainer.setLayout(new BoxLayout(tasksContainer, BoxLayout.Y_AXIS));
        tasksContainer.setBackground(UIConstants.COLOR_BACKGROUND);
        tasksContainer.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        scrollPane = new JScrollPane(tasksContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = UIConstants.COLOR_SCROLLBAR;
                this.trackColor = UIConstants.COLOR_BACKGROUND;
            }
            @Override
            protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
            @Override
            protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
        });

        statusLabel = new JLabel("0 / 0 completed");
        statusLabel.setFont(UIConstants.FONT_GENERAL);
        statusLabel.setForeground(UIConstants.COLOR_TEXT_MUTED);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(UIConstants.COLOR_PANEL);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.COLOR_BORDER));
        JMenu fileMenu = new JMenu("File");
        fileMenu.setFont(UIConstants.FONT_GENERAL);
        fileMenu.setForeground(UIConstants.COLOR_TEXT);
        saveMenu = createMenuItem("Save");
        exportMenu = createMenuItem("Export as CSV");
        clearCompletedMenu = createMenuItem("Clear Completed Tasks");
        fileMenu.add(saveMenu);
        fileMenu.add(exportMenu);
        fileMenu.addSeparator();
        fileMenu.add(clearCompletedMenu);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);
    }

    private JMenuItem createMenuItem(String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(UIConstants.FONT_GENERAL);
        menuItem.setBackground(UIConstants.COLOR_PANEL);
        menuItem.setForeground(UIConstants.COLOR_TEXT);
        return menuItem;
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}