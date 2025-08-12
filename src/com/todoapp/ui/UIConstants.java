package com.todoapp.ui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * A centralized place for all UI design constants like colors and fonts.
 * This creates a consistent and easily modifiable design system.
 */
public final class UIConstants {

    // Primary Color Palette
    public static final Color COLOR_BACKGROUND = new Color(0x2B2D30); // Very dark grey, almost black
    public static final Color COLOR_PANEL = new Color(0x3C3F41);      // Dark grey for panels
    public static final Color COLOR_ACCENT = new Color(0x007ACC);      // A vibrant, professional blue
    public static final Color COLOR_ACCENT_LIGHT = new Color(0x3399FF); // Lighter blue for hover effects

    // Text and Secondary Colors
    public static final Color COLOR_TEXT = new Color(0xBBBBBB);          // Light grey for readable text
    public static final Color COLOR_TEXT_MUTED = new Color(0x8C8C8C);  // Muted grey for placeholders/status

    // Action Colors for Hovers
    public static final Color COLOR_DANGER = new Color(0xE53935);      // A soft red for delete actions
    public static final Color COLOR_SUCCESS = new Color(0x28a745);     // A professional green for edit actions

    // Component-Specific Colors
    public static final Color COLOR_BORDER = new Color(0x555555);
    public static final Color COLOR_SCROLLBAR = new Color(0x4A4D50);

    // Fonts
    // Uses Segoe UI (common on Windows) with fallbacks for other systems.
    public static final Font FONT_GENERAL = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 16);

    // Borders (for consistent spacing)
    public static final Border BORDER_COMPONENT = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 1, 1, COLOR_BORDER),
            BorderFactory.createEmptyBorder(8, 12, 8, 12) // Inner padding
    );

    // Utility method for creating a hover effect on buttons
    public static void setButtonHoverEffect(javax.swing.JButton button, Color normal, Color hover) {
        button.setBackground(normal);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normal);
            }
        });
    }
}