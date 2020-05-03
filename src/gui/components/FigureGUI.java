package gui.components;

import javax.imageio.ImageIO;
import java.io.File;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class FigureGUI extends JComponent
{
    private static final long serialVersionUID = 1L;

    public String symbol;
    public int x_;
    public int y_;
    public int side;
    Image figureIcon;

    public FigureGUI(String symbol, int x, int y, int side){
        this.symbol = symbol;
        this.x_ = x;
        this.y_ = y;
        this.side = side;
        setOpaque(false);

        try {
            this.figureIcon = ImageIO.read(new File("src/gui/components/icons/"+ symbol + ".png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void paintComponent(Graphics g) {
        // прорисовка фигуры

        g.fillRect(0, 0, side, side);
        g.setColor(Color.orange);
//        g.drawString(symbol, side/2, side/2);
        g.drawImage(figureIcon, 0,0,null);
    }
}
