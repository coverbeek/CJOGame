package com.coverbeek;

import java.util.ArrayList;

/**
 * Class to encapsulate Solving the HiQ peg jump game
 * Will enumerate moves until a solution state has been found.
 * Solution state can be played back.
 */
public class Solver {
    private Board m_board; 
    private boolean m_solutionReady;
    private ArrayList<Move> m_solutionMoves;

    // Basic constructor that takes in a board;
    public Solver(Board startingBoard) {
        m_board = startingBoard;
        m_solutionReady = false;
        m_solutionMoves = new ArrayList<Move>();
    }

    public boolean isSolved() {
        return m_solutionReady;
    }
    
    public void findSolution() {
        boolean notDone = true;
        Move aMove = m_board.getValidMove();
        Move nextMove;
        while(notDone) {

            // Manipulate the gameboard to find a solution            
            if (!m_board.isMoveValid(aMove)) {
                // No valid moves!! If the game is not solved, must back up...
                if (!m_board.isInSolutionState()) {
                    // get the next Valid move if there is one.
                    nextMove = m_board.getValidMove();
                    // have to back out the last move and try another...
                    aMove = m_solutionMoves.getLast();
                    // back it out on the board
                    m_board.undoMove(aMove);
                    // remove from solution list
                    m_solutionMoves.removeLast();
                    System.out.println("Removing Move: " + aMove.ToString());
                    if (m_board.isMoveValid(nextMove)) {
                        aMove = nextMove;
                    }
                    else {
                        aMove = m_board.getValidMove();
                    }

                }
                else {
                    // if the game is solved, then we're done
                    notDone = false;
                }
            } 
            else {
                // This move is valid, stash it and update the board!
                System.out.println("Making Move: " + aMove.ToString());
                m_solutionMoves.add(aMove);
                m_board.applyMove(aMove);
                aMove = m_board.getValidMove();
            } 
        }

        // temporarily output the board for fun...
        m_board.outputBoard();
        return;
    }

}
