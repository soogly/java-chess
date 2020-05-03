package gui.components;

import javax.swing.*;
import java.awt.*;


public class Cell extends JComponent
{
    private static final long serialVersionUID = 1L;

    public Color color;
    public Color normalColor;
    public int x_;
    public int y_;
    public int side;

    public Cell(Color color, int x, int y, int side) {
        this.color = color;
        this.normalColor = color;
        this.x_ = x;
        this.y_ = y;
        this.side = side;
        setOpaque(false);
    }
    public void paintComponent(Graphics g) {
        // прорисовка фигуры
        g.setColor(color);
        g.fillRect(0, 0, side, side);
    }
}
