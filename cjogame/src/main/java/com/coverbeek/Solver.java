package com.coverbeek;

/**
 * Class to encapsulate Solving the HiQ peg jump game
 * Will enumerate moves until a solution state has been found.
 * Solution state can be played back.
 */
public class Solver {
    private Board m_board; 
    private boolean m_solutionReady;

    // Basic constructor that takes in a board;
    public Solver(Board startingBoard) {
        m_board = startingBoard;
        m_solutionReady = false;
    }

    public boolean isSolved() {
        return m_solutionReady;
    }
    
    public void findSolution() {
        // Manipulate the gameboard to find a solution
        Move aMove = m_board.getValidMove();
        if (!m_board.isMoveValid(aMove)) {
            // No valid moves!!
        } 
        else {
            // This move is valid, stash it and update the board!
        }
        return;
    }

}
