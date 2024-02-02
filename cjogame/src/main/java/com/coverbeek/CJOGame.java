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
    Board ourGameBoard;

    public CJOGame() {
        ourGameBoard = new Board();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        drawEmptyHole(g2d, x, y);
        drawFullHole(g2d, x, y);
        drawWholeBoard(g2d);
    }

    private void drawWholeBoard(Graphics2D g2d) {
        // Iterate over the whole board drawing the holes either full or empty based on the
        // state of the board...

        Hole oneHole;
        int sizeOfHoleOffset = 3;
        int sizeOfSpace = 20;
        int xLoc = 0;
        int yLoc = 0;

        for (int iterY = 0; iterY  < 7; iterY++ ) {
            for (int iterX = 0; iterX  < 7; iterX++ ) {
                oneHole = this.ourGameBoard.holeArray[iterX][iterY];
                if (oneHole.getIsValid()){
                    // calculate offset based on location
                    xLoc = (iterX * sizeOfSpace) + sizeOfHoleOffset;
                    yLoc = (iterY * sizeOfSpace) + sizeOfHoleOffset;
                    if (oneHole.getHasPeg()){
                        // There is a peg, draw a filled hole
                        drawFullHole(g2d, xLoc, yLoc);                        
                    }
                    else {
                        // There is no peg, draw am unfilled hole
                        drawEmptyHole(g2d, xLoc, yLoc);
                    }
                }    
                else {
                    // No Hole here... NoOp
                }
            }
        }

    }

    private void drawEmptyHole(Graphics g2d, int x, int y) {
        // draw an X
        Color curColor = Color.BLACK;
        g2d.setColor(curColor);
        g2d.drawOval(x, y, 15,15);
    }

    
    private void drawFullHole(Graphics g2d, int x, int y) {
        // draw an X
        Color curColor = Color.BLACK;
        g2d.setColor(curColor);
        g2d.fillOval(x, y, 15,15);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Sample Frame");
        CJOGame game = new CJOGame();
        frame.add(game);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boolean gameNotDone = true;

        while (gameNotDone) {
            game.repaint();
            Thread.sleep(500);
        }
    }
}
