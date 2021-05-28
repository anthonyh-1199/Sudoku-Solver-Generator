package sudokugame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Anthony
 */
public class SudokuGame {
    
    public static void printBoard(int[][] gameboard) {
        for (int i = 0; i < gameboard.length; i++){
            String s = "";
            
            for (int j = 0; j < gameboard.length; j++) {
                s += gameboard[j][i] + " ";
            }
            
            System.out.println(s + "\n");
        }
    }
    
    //Returns true if every square in the board is 0
    public static boolean checkBoardEmpty(int[][] gameboard) {
        for (int x = 0; x < gameboard.length; x++) {
            for (int y = 0; y < gameboard.length; y++) {
                //If there exists a non-empty square, return false
                if (gameboard[x][y] != 0) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    //Returns true if the board is complete and correct
    public static boolean checkBoardSolved(int[][] gameboard) {
        for (int x = 0; x < gameboard.length; x++) {
            for (int y = 0; y < gameboard.length; y++) {
                //If there exists an empty square, return false
                if (gameboard[x][y] == 0) {
                    return false;
                }
                
                //If there exists an illegal move, return false
                if (!checkMoveValid(x, y, gameboard, gameboard[x][y])) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    //Generates a random, filled-in Sudoku board
    public static int[][] generateBoard() {
        int[][] gameboard = new int[9][9];
        
        fillSquare(0, 0, gameboard);
        
        return gameboard;
    }
    
    //Generates a filled-in Sudoku board with a pre-made base
    public static int[][] generateBoard(int[][] gameboard) {
        fillSquare(0, 0, gameboard);
        
        return gameboard;
    }
    
    public static int[][] generatePuzzle(int[][] gameboard){
        //Initialize variables for square coordinates, boards, and attempts
        int randomX;
        int randomY;
        int[][] puzzleboard = new int[9][9];
        int[][] cloneboard = new int[9][9];
        int attempts = 5;
        
        //Set puzzleboard to be a copy of the gameboard
        for (int i = 0; i < gameboard.length; i++){
            puzzleboard[i] = gameboard[i].clone();
        }
        
        //Initialize 2D list of square coordinates, then randomize it
        //We will pick the squares from this list instead of using Random
        //to avoid wasting time on repeats
        ArrayList<int[]> randomSquares = new ArrayList<>();
        int n = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                int[] k = {i, j};
                randomSquares.add(k);
            }
        }
        Collections.shuffle(randomSquares);
        
        while (true){
            //Get a random square
            randomX = randomSquares.get(n)[0];
            randomY = randomSquares.get(n++)[1];
            
            //Set it to 0
            puzzleboard[randomX][randomY] = 0;
            
            //Clone puzzleboard so generateBoard doesn't overwrite it
            for (int i = 0; i < puzzleboard.length; i++){
                cloneboard[i] = puzzleboard[i].clone();
            }
            
            //If the puzzle has a solution that != gameboard, undo 
            if (!isEqual(generateBoard(cloneboard), gameboard)){
                puzzleboard[randomX][randomY] = gameboard[randomX][randomY];
                
                //Perform arbitrary # of attempts before returning puzzle
                //More attempts -> harder puzzle
                if (attempts-- == 0){
                    return puzzleboard;
                }
            }
        }
    }
    
    //Returns true if two 2D arrays are equal
    private static boolean isEqual(int[][] boardA, int[][] boardB){
        for (int i = 0; i < boardA.length; i++) {
            if (!Arrays.equals(boardA[i], boardB[i])) {
                return false;
            }
        }
        return true;
    }
    
    //Returns true if move is legal
    public static boolean checkMoveValid(int x, int y, int[][] gameboard, int value) {
        // Check value in all the other squares in that square's block
        for (int i = x / 3 * 3; i < ((x / 3 + 1) * 3); i++){
            for (int j = y / 3 * 3; j < ((y / 3 + 1) * 3); j++){
                if (gameboard[i][j] == value && i != x && j != y){
                    return false;
                }
            }
        }

        // Check value in all the other squares in that square's row
        for (int i = 0; i < 9; i++) {
            if (gameboard[i][y] == value && i != x) {
                return false;
            }
        }

        // Check value in all the other squares in that square's row
        for (int j = 0; j < 9; j++) {
            if (gameboard[x][j] == value && j != y) {
                return false;
            }
        }
        
        return true;
    }
    
    //Recursive function to fill squares
    public static int[][] fillSquare(int x, int y, int[][] gameboard) {
        //Initialize variables
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        int nextx;
        int nexty;
        int originalValue = gameboard[x][y];
        
        //Randomize array of values
        Collections.shuffle(values);
        
        //Don't write over pre-filled squares
        if (originalValue != 0){
            //Check if the current square is a valid move
            if (checkMoveValid(x, y, gameboard, originalValue)) {
                //Check if board is complete
                if (checkBoardSolved(gameboard)){
                    return gameboard;
                }

                //Move to next square, first horizontally, then vertically
                if (x == 8) {
                    nextx = 0;
                    nexty = y + 1;
                } else {
                    nextx = x + 1;
                    nexty = y;
                }

                //Recursive call, returns the solved puzzle
                if (checkBoardSolved(fillSquare(nextx, nexty, gameboard.clone()))) {
                    return gameboard;
                } 
            }
        }
        
        else if (originalValue == 0) {
            //Attempt to fill in the square. If none of the values are valid, 
            //then there's a mistake higher up the chain
            for(int value : values) {
                if (checkMoveValid(x, y, gameboard, value)) {
                    //Add square to gameboard
                    gameboard[x][y] = value;

                    //Check if board is complete
                    if (checkBoardSolved(gameboard)) {
                        return gameboard;
                    }

                    //Move to next square, first horizontally, then vertically
                    if (x == 8) {
                        nextx = 0;
                        nexty = y + 1;
                    } else {
                        nextx = x + 1;
                        nexty = y;
                    }

                    //Recursive call, returns the solved puzzle
                    if (checkBoardSolved(fillSquare(nextx, nexty, gameboard.clone()))) {
                        return gameboard;
                    } 

                }

            }
            
        }
        
        //If there were no legal moves for that square, set it to its original value
        gameboard[x][y] = originalValue;

        return gameboard;
    }

    public static void main(String[] args) {
        int[][] gameboard = generateBoard();
        int[][] puzzleboard = generatePuzzle(gameboard); 
        
        System.out.println("Puzzle: \n");
        
        printBoard(puzzleboard);
        
        System.out.println("Solution: \n");
        
        printBoard(gameboard);
        
    }
    
}