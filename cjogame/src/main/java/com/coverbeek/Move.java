package com.coverbeek;

public class Move {
    public int startingX;
    public int startingY;

    public int endingX;
    public int endingY;

    public Move() {
        startingX = -1;
        startingY = -1;
        endingX = -1;
        endingY = -1;
    }

    public String ToString() {
        return ("\tStarting [" + startingX + "][" + startingY + "]\n\tEnding  ["+ endingX + "][" + endingY + "]\n");
    }
}
