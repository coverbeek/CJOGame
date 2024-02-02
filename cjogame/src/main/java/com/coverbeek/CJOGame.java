package com.coverbeek;

//package com.edu4java.samplegame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CJOGame extends JPanel {
    int x = 0;
    int y = 0;
    boolean reverseX = false;
    boolean reverseY = false;

    private void moveBall() {
        if (!reverseX && x >= 270) reverseX = true;
        else if (reverseX && x <= 0) reverseX = false;

        if (!reverseY && y >= 370) reverseY = true;
        else if (reverseY && y <= 0) reverseY = false;
        
        if (!reverseX) x++;
        else x--;

        if (!reverseY) y++;
        else y--;

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        Color curColor = Color.green;
        g2d.setColor(curColor);
        g2d.fillOval(x, y, 30, 30);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Sample Frame");
        CJOGame game = new CJOGame();
        frame.add(game);
        frame.setSize(310, 450);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            game.moveBall();
            game.repaint();
            Thread.sleep(5);
        }
    }
}
