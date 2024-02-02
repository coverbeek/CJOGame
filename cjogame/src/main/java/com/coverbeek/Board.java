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
 }
