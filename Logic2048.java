/***
 * The below code contains the functions used to run the game of 2048.
 * Enter w,a,s or d or move up, left, down or right respectively.
 * Owner : Suhani Mathur
 * Created on : 17 Sep 2024
 */

package com.Gamepedia;


public class Logic2048 {
	private int[][] previousBoard;
    private int[][] board;
    private int score;
    private int size;
    private int nextTile;
    private int moveLimit;
    private int moveCount;

    public Logic2048() {
        size = 4; 
        board = new int[size][size];
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

    public void placeRandomTile() {
        int row, col;
        int emptySpaces = 0;

        // Count empty spaces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    emptySpaces++;
                }
            }
        }

        if (emptySpaces == 0) return; // No empty space available

        int position = (int)(Math.random() * emptySpaces); // Random position
        int count = 0;

        // Find the position for the new tile
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    if (count == position) {
                        board[i][j] = nextTile;
                        previewNextTile();
                        return;
                    }
                    count++;
                }
            }
        }
    }

    public void previewNextTile() {
        nextTile = (Math.random() < 0.5) ? 2 : 4; 
    }

    private void saveState() {
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                previousBoard[i][j] = board[i][j]; 
            }
        }
    }

    public void undo() {
    	for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = previousBoard[i][j]; 
            }
        }
        System.out.println("Undo successful!");
    }

    public boolean moveUp() {
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

    public boolean moveDown() {
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

    public boolean moveLeft() {
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

    public boolean moveRight() {
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

    public String getBoardState() {
        String state = ""; 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                state += board[i][j];  
                if (j < size - 1) {
                    state += ",";  
                }
            }
            if (i < size - 1) {
                state += ";";  
            }
        }        
        return state;  
    }
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

    public boolean checkMoveLimit() {
        return moveCount >= moveLimit; 
    }
    public int getHighTile() {
        int highestTile = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] > highestTile) {
                    highestTile = board[i][j]; 
                }
            }
        }
        return highestTile; 
    }
    public int getMoveCount() {
        return moveCount; 
    }
}

        