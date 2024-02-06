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
    public Hole[][] holeArray;

    public Board(){

        holeArray = new Hole[7][7];
        for (int x=0; x < 7; x++){
            for (int y = 0; y < 7; y++){
                Hole oneHole = new Hole();
                holeArray[x][y] = oneHole;

                // the four holes at the corners are not valid
                if ((x < 2 && y < 2) || (x < 2 && y > 4) || (x > 4 && y < 2) || (x > 4 && y > 4)){
                    oneHole.setIsValid(false);
                }

                // center hole is empty
                if (x == 3 && y == 3 ){
                    oneHole.setHasPeg(false);
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
                oneHole = holeArray[x][y];
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
        Hole oneHole = holeArray[x][y];
        isValid = oneHole.getIsValid();
        return isValid;
    }

    public boolean isHoleFilled(int x, int y) {
        boolean filled = false;
        if (isHoleValid(x,y)) {
            Hole oneHole = holeArray[x][y];
            filled = oneHole.getHasPeg(); 
        }
        return filled;
    }

    public boolean isMoveValid(Move oneMove) {
        boolean isValid = false;
        int gapX = -1;
        int gapY = -1;
        
        // Don't bother if start and end aren't valid and filled and empty respectively.
        if (isHoleFilled(oneMove.startingX, oneMove.startingY) && isHoleValid(oneMove.endingX, oneMove.endingY) 
            && !isHoleFilled(oneMove.endingX, oneMove.endingY)) {
            // start and end must be separated by a hole
            if ( (Math.abs(oneMove.startingX - oneMove.endingX) == 2) && (Math.abs(oneMove.startingY - oneMove.endingY) == 2) ) {
                // hole separating must be valid and filled
                if (oneMove.startingX > oneMove.endingX) gapX = oneMove.startingX + 1;
                else gapX = oneMove.endingX + 1;
                if (oneMove.startingY > oneMove.endingY) gapY = oneMove.startingY + 1;
                else gapY = oneMove.endingY + 1;

                // Is the gap hole filled? (will be false if somehow invalid)
                if (isHoleFilled(gapX, gapY)) {
                    isValid = true;
                }                
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
 }
