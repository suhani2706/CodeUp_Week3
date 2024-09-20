/***
 * The below code contains the functions used to run the game of 2048.
 * Enter w,a,s or d or move up, left, down or right respectively.
 * Owner : Suhani Mathur
 * Created on : 17 Sep 2024
 */

package com.Gamepedia;

import java.util.*;

public class Logic2048 {
    private int[][] board;
    private int[][] previousBoard; 
    private int score;
    private int size; 
    private int nextTile;
    private int moveLimit;
    private int moveCount; 

    
    {
        size = 4; 
        board = new int[size][size];
        previousBoard = new int[size][size]; 
        score = 0;
        moveLimit = 30; 
        moveCount = 0; 
        placeRandomTile();
        placeRandomTile();
        previewNextTile(); 
    }

    public int getScore() {
        return score;
    }

    public void printBoard() {
        System.out.println("--------------------------");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    System.out.print(".\t");
                } else {
                    System.out.print(board[i][j] + "\t");
                }
            }
            System.out.println();
        }
        System.out.println("--------------------------");
       
    }

    // Place a random tile using the nextTile preview
    public void placeRandomTile() {
        Random rand = new Random();
        int row, col;
        do {
            row = rand.nextInt(size);
            col = rand.nextInt(size);
        } while (board[row][col] != 0);
        board[row][col] = nextTile;
        previewNextTile(); // Set next tile
    }

    // Preview the next tile to be placed
    public void previewNextTile() {
        Random rand = new Random();
        nextTile = (rand.nextInt(2) + 1) * 2; 
    }

    // Copy the current board to the previous board for undo functionality
    private void saveState() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                previousBoard[i][j] = board[i][j];
            }
        }
    }

    // Undo the last move by restoring the previous board state
    public void undo() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = previousBoard[i][j];
            }
        }
        System.out.println("Undo successful!");
    }

    // This function moves the element up when user commands.
    // Return type : boolean
    public boolean moveUp() {
        saveState(); 
        boolean moved = false; 

        for (int j = 0; j < size; j++) {
            for (int i = 1; i < size; i++) {
                if (board[i][j] != 0) {
                    int row = i;
                    
                    while (row > 0 && board[row - 1][j] == 0) {
                        board[row - 1][j] = board[row][j];
                        board[row][j] = 0;
                        row--;
                        moved = true; 
                    }
                    if (row > 0 && board[row - 1][j] == board[row][j]) {
                        board[row - 1][j] *= 2;
                        score += board[row - 1][j];
                        board[row][j] = 0;
                        moved = true; 
                    }
                }
            }
        }

        if (moved) {
            moveCount++; 
            placeRandomTile(); 
        }

        return moved;
    }

    // This function moves the element down when user commands.
    // Return type : boolean
    public boolean moveDown() {
        saveState();
        boolean moved = false;
        for (int j = 0; j < size; j++) {
            for (int i = size - 2; i >= 0; i--) {
                if (board[i][j] != 0) {
                    int row = i;
                    while (row < size - 1 && board[row + 1][j] == 0) {
                        board[row + 1][j] = board[row][j];
                        board[row][j] = 0;
                        row++;
                        moved = true;
                    }
                    if (row < size - 1 && board[row + 1][j] == board[row][j]) {
                        board[row + 1][j] *= 2;
                        score += board[row + 1][j];
                        board[row][j] = 0;
                        moved = true;
                    }
                }
            }
        }
        if (moved) {
            moveCount++;
            placeRandomTile();
        }
        return moved;
    }
    // This function moves the element left when user commands.
    // Return type : boolean
    public boolean moveLeft() {
        saveState();
        boolean moved = false;
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (board[i][j] != 0) {
                    int col = j;
                    while (col > 0 && board[i][col - 1] == 0) {
                        board[i][col - 1] = board[i][col];
                        board[i][col] = 0;
                        col--;
                        moved = true;
                    }
                    if (col > 0 && board[i][col - 1] == board[i][col]) {
                        board[i][col - 1] *= 2;
                        score += board[i][col - 1];
                        board[i][col] = 0;
                        moved = true;
                    }
                }
            }
        }
        if (moved) {
            moveCount++;
            placeRandomTile();
        }
        return moved;
    }
    // This function moves the element right when user commands.
    // Return type : boolean
    public boolean moveRight() {
        saveState();
        boolean moved = false;
        for (int i = 0; i < size; i++) {
            for (int j = size - 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int col = j;
                    while (col < size - 1 && board[i][col + 1] == 0) {
                        board[i][col + 1] = board[i][col];
                        board[i][col] = 0;
                        col++;
                        moved = true;
                    }
                    if (col < size - 1 && board[i][col + 1] == board[i][col]) {
                        board[i][col + 1] *= 2;
                        score += board[i][col + 1];
                        board[i][col] = 0;
                        moved = true;
                    }
                }
            }
        }
        if (moved) {
            moveCount++;
            placeRandomTile();
        }
        return moved;
    }
    // This function checks if the game is over.
    // Return type : boolean
    public boolean checkGameOver() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    return false; 
                }
                if (i < size - 1 && board[i][j] == board[i + 1][j]) {
                    return false; 
                }
                if (j < size - 1 && board[i][j] == board[i][j + 1]) {
                    return false;                
               }
            }
        }
        return true;
    } 

    // This function checks if the moving limit is reached by the user. If yes, then it ends the game.
    // Return type : boolean
    public boolean checkMoveLimit() {
        if (moveCount >= moveLimit) {
            System.out.println("Move limit reached!");
            return true;
        }
        return false;
    }


}


        