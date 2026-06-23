package program.gui.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import javax.swing.border.AbstractBorder;

// Class untuk membuat input component agar ada bordernya dan cekungan ujung

public class RoundedBorderInput extends AbstractBorder
{
    private int radius;
    private int thickness;
    private Color color;

    public RoundedBorderInput(int radius, int thickness, Color color)
    {
        this.radius = radius;
        this.thickness = thickness;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness)); // atur ketebalan garis
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }
}