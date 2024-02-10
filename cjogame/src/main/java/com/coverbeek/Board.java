package com.coverbeek;

class Hole
{
     private boolean hasPeg;
     private boolean isValid;

     public Hole(boolean peg, boolean valid){
         hasPeg = peg;
         isValid = valid;
     }

     public Hole(){
        hasPeg = true;
        isValid = true;
    }

    public boolean getIsValid(){
        return isValid;
    }

    public void setIsValid(boolean valid){
        isValid = valid;
    }

    public boolean getHasPeg(){
        return hasPeg;
    }

    public void setHasPeg(boolean peg){
        hasPeg = peg;
    }
    
 }

 class Board
 {
    public Hole[][] m_holeArray;
    boolean m_isSolved;
    int m_pegCount;

    public Board(){
        m_isSolved = false;
        m_pegCount = 0;
        m_holeArray = new Hole[7][7];
        for (int x=0; x < 7; x++){
            for (int y = 0; y < 7; y++){
                Hole oneHole = new Hole();
                m_holeArray[x][y] = oneHole;
                m_pegCount++;

                // the four holes at the corners are not valid
                if ((x < 2 && y < 2) || (x < 2 && y > 4) || (x > 4 && y < 2) || (x > 4 && y > 4)){
                    oneHole.setIsValid(false);
                    m_pegCount--;
                }

                // center hole is empty
                if (x == 3 && y == 3 ){
                    oneHole.setHasPeg(false);
                    m_pegCount--;
                }
            }
        }
    }

    public void outputBoard(){
        Hole oneHole; 
        String oneLine = "";
        String oneChar = "";
        for( int y = 0; y<7; y++){
            for (int x=0; x<7; x++){
                oneHole = m_holeArray[x][y];
                if (oneHole.getIsValid()){
                    if (oneHole.getHasPeg()){
                        oneChar = "X";
                    }
                    else {
                        oneChar = "O";
                    }
                }                
                else{
                    oneChar = " ";
                }

                oneLine += oneChar + " ";
            }
            System.out.println(oneLine);
            oneLine = "";
        }        
    }

    public boolean isHoleValid(int x, int y) {
        boolean isValid = false;

        if (!(x < 0 || y < 0 || x >= 7 || y >= 7)) {
            Hole oneHole = m_holeArray[x][y];
            isValid = oneHole.getIsValid();
        }
        return isValid;
    }

    public boolean isHoleFilled(int x, int y) {
        boolean filled = false;
        if (isHoleValid(x,y)) {
            Hole oneHole = m_holeArray[x][y];
            filled = oneHole.getHasPeg(); 
        }
        return filled;
    }

    public boolean isMoveValid(Move oneMove) {
        boolean isValid = false;
        int gapX = oneMove.startingX;
        int gapY = oneMove.startingY;

        // Don't bother if start and end aren't valid and filled and empty respectively.
        if (isHoleFilled(oneMove.startingX, oneMove.startingY) && isHoleValid(oneMove.endingX, oneMove.endingY) 
            && !isHoleFilled(oneMove.endingX, oneMove.endingY)) {
            // start and end must be separated by a hole
            if ( (Math.abs(oneMove.startingX - oneMove.endingX) == 2) &&  (oneMove.startingY == oneMove.endingY) ) {
                // hole separating must be valid and filled
                if (oneMove.startingX > oneMove.endingX) gapX = oneMove.endingX + 1;
                else if (oneMove.startingX < oneMove.endingX) gapX = oneMove.startingX + 1;

                if (oneMove.startingY > oneMove.endingY) gapY = oneMove.endingY + 1;
                else if (oneMove.startingY < oneMove.endingY) gapY = oneMove.startingY + 1;

                // Is the gap hole filled? (will be false if somehow invalid)
                if (isHoleFilled(gapX, gapY)) {
                    isValid = true;
                }                
            }
            else if ( (Math.abs(oneMove.startingY - oneMove.endingY) == 2) &&  (oneMove.startingX == oneMove.endingX) ) {

            }
        }
        return isValid;
    }

    public Move getValidMove() {
        Move aMove = new Move();

        // we're just going to iterate over the board looking for the first valid move.
        for( int y = 0; y<7; y++){
            for (int x=0; x<7; x++){

            
                aMove.startingX = x;
                aMove.startingY = y;
                aMove.endingX = x;
                aMove.endingY = y;

                // try negative X
                aMove.endingX = aMove.startingX - 2;
                if (isMoveValid(aMove)) return aMove;
                // try positive X
                aMove.endingX = aMove.startingX + 2;
                if (isMoveValid(aMove)) return aMove;
                aMove.endingX = aMove.startingX;

                // try negative Y
                aMove.endingY = aMove.startingY - 2;
                if (isMoveValid(aMove)) return aMove;
                // try positive Y
                aMove.endingY = aMove.startingY + 2;
                if (isMoveValid(aMove)) return aMove;
            }
        }

        // No Valid Moves!! return empty Move;
        return new Move();
    }

    public boolean applyMove(Move aMove) {
        boolean moveWorked = false;

        if (isMoveValid(aMove)) {
            int x = -1;
            int y = -1;

            if (aMove.endingX < aMove.startingX) {
                y = aMove.startingY;
                x = (aMove.startingX - 1);
            } 
            else if (aMove.endingX > aMove.startingX) {
                y = aMove.startingY;
                x = (aMove.startingX + 1);
            }
            else {
                x = aMove.startingX;
                if (aMove.endingY < aMove.startingY) y = (aMove.startingY - 1);
                else y = (aMove.startingY + 1);
            }

            // remove the peg from starting position
            m_holeArray[aMove.startingX][aMove.startingY].setHasPeg(false);
            
            // remove the jumped peg
            m_holeArray[x][y].setHasPeg(false);
            m_pegCount--;

            // put the peg in the ending position
            m_holeArray[aMove.endingX][aMove.endingY].setHasPeg(true);

            // one peg remaining in center hole is solved...
            if (m_pegCount == 1 && (aMove.endingX == 3 && aMove.endingY == 3)) {
                m_isSolved = true;
            }
            moveWorked = true;
            outputBoard();
        }

        return moveWorked;
    }

    public void undoMove(Move aMove) {
        int x = -1;
        int y = -1;

        if (aMove.endingX < aMove.startingX) {
            y = aMove.startingY;
            x = (aMove.startingX - 1);
        } 
        else if (aMove.endingX > aMove.startingX) {
            y = aMove.startingY;
            x = (aMove.startingX + 1);
        }
        else {
            x = aMove.startingX;
            if (aMove.endingY < aMove.startingY) y = (aMove.startingY - 1);
            else y = (aMove.startingY + 1);
        }

        // remove the peg from starting position
        m_holeArray[aMove.startingX][aMove.startingY].setHasPeg(true);
        
        // remove the jumped peg
        m_holeArray[x][y].setHasPeg(true);
        m_pegCount++;

        // put the peg in the ending position
        m_holeArray[aMove.endingX][aMove.endingY].setHasPeg(false);
    }

    public boolean isInSolutionState() {
        return m_isSolved;
    }
 }
