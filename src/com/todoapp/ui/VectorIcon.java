package com.todoapp.ui;

import javax.swing.Icon;
import java.awt.Component;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A resolution-independent, colorable Icon that renders from an SVG path.
 * This is a lightweight and dependency-free way to use vector graphics in Swing.
 */
public class VectorIcon implements Icon {
    private final Path2D path;
    private final int width;
    private final int height;
    private Color color;

    public VectorIcon(String svgPathData, int width, int height, Color color) {
        this.path = parsePath(svgPathData);
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate the scale required to fit the path into the icon dimensions
        double scaleX = (double) width / path.getBounds2D().getWidth();
        double scaleY = (double) height / path.getBounds2D().getHeight();

        // Apply transformation: move to icon's position and scale
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.scale(scaleX, scaleY);

        g2d.setColor(color);
        g2d.fill(at.createTransformedShape(path));
        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    // A simple parser for SVG path data. Handles M, L, H, V, C, Z commands (and their lowercase relatives).
    private static Path2D.Double parsePath(String pathData) {
        Path2D.Double path = new Path2D.Double();
        Pattern pattern = Pattern.compile("([MLHVCSQTAZmlhvcsqtaz])([^MLHVCSQTAZmlhvcsqtaz]*)");
        Matcher matcher = pattern.matcher(pathData);
        double currentX = 0;
        double currentY = 0;
        double ctrlX = 0;
        double ctrlY = 0;

        while (matcher.find()) {
            char command = matcher.group(1).charAt(0);
            String[] params = matcher.group(2).trim().split("[\\s,]+");

            try {
                switch (command) {
                    case 'M': currentX = Double.parseDouble(params[0]); currentY = Double.parseDouble(params[1]); path.moveTo(currentX, currentY); break;
                    case 'm': currentX += Double.parseDouble(params[0]); currentY += Double.parseDouble(params[1]); path.moveTo(currentX, currentY); break;
                    case 'L': currentX = Double.parseDouble(params[0]); currentY = Double.parseDouble(params[1]); path.lineTo(currentX, currentY); break;
                    case 'l': currentX += Double.parseDouble(params[0]); currentY += Double.parseDouble(params[1]); path.lineTo(currentX, currentY); break;
                    case 'H': currentX = Double.parseDouble(params[0]); path.lineTo(currentX, currentY); break;
                    case 'h': currentX += Double.parseDouble(params[0]); path.lineTo(currentX, currentY); break;
                    case 'V': currentY = Double.parseDouble(params[0]); path.lineTo(currentX, currentY); break;
                    case 'v': currentY += Double.parseDouble(params[0]); path.lineTo(currentX, currentY); break;
                    case 'C':
                        ctrlX = Double.parseDouble(params[2]); ctrlY = Double.parseDouble(params[3]);
                        currentX = Double.parseDouble(params[4]); currentY = Double.parseDouble(params[5]);
                        path.curveTo(Double.parseDouble(params[0]), Double.parseDouble(params[1]), ctrlX, ctrlY, currentX, currentY);
                        break;
                    case 'c':
                        double x1 = currentX + Double.parseDouble(params[0]); double y1 = currentY + Double.parseDouble(params[1]);
                        ctrlX = currentX + Double.parseDouble(params[2]); ctrlY = currentY + Double.parseDouble(params[3]);
                        currentX += Double.parseDouble(params[4]); currentY += Double.parseDouble(params[5]);
                        path.curveTo(x1, y1, ctrlX, ctrlY, currentX, currentY);
                        break;
                    case 'Z': case 'z': path.closePath(); break;
                }
            } catch (Exception e) {
                // Ignore parsing errors for simplicity
            }
        }
        return path;
    }
}