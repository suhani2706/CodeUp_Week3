/***
 * The below code contains the functions used to run the game of 2048.
 * Enter w,a,s or d or move up, left, down or right respectively.
 * Owner : Suhani Mathur
 * Created on : 17 Sep 2024
 */



package com.Gamepedia;

import java.util.Random;

public class Logic2048 {
    private int[][] board;
    private int score;

    public Logic2048() {
        board = new int[4][4];
        score = 0; 
    }

    // Function to get the current score
    public int getScore() {
        return score;
    }

    // Function to print the board
    public void printBoard() {
        System.out.println("-----------------");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    System.out.print(".\t");
                } else {
                    System.out.print(board[i][j] + "\t");
                }
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }

    // Function to place a random tile (2 or 4)
    public void placeRandomTile() {
        Random rand = new Random();
        int row, col;
        do {
            row = rand.nextInt(4);
            col = rand.nextInt(4);
        } while (board[row][col] != 0);
        board[row][col] = (rand.nextInt(2) + 1) * 2; // Places 2 or 4
    }

    // Function to move tiles up
    public void moveUp() {
        for (int j = 0; j < 4; j++) {
            for (int i = 1; i < 4; i++) {
                if (board[i][j] != 0) {
                    int row = i;
                    while (row > 0 && board[row - 1][j] == 0) {
                        board[row - 1][j] = board[row][j];
                        board[row][j] = 0;
                        row--;
                    }
                    if (row > 0 && board[row - 1][j] == board[row][j]) {
                        board[row - 1][j] *= 2;
                        score += board[row - 1][j];  // Update score when tiles merge
                        board[row][j] = 0;
                    }
                }
            }
        }
    }

    // Function to move tiles down
    public void moveDown() {
        for (int j = 0; j < 4; j++) {
            for (int i = 2; i >= 0; i--) {
                if (board[i][j] != 0) {
                    int row = i;
                    while (row < 3 && board[row + 1][j] == 0) {
                        board[row + 1][j] = board[row][j];
                        board[row][j] = 0;
                        row++;
                    }
                    if (row < 3 && board[row + 1][j] == board[row][j]) {
                        board[row + 1][j] *= 2;
                        score += board[row + 1][j];  // Update score when tiles merge
                        board[row][j] = 0;
                    }
                }
            }
        }
    }

    // Function to move tiles left
    public void moveLeft() {
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (board[i][j] != 0) {
                    int col = j;
                    while (col > 0 && board[i][col - 1] == 0) {
                        board[i][col - 1] = board[i][col];
                        board[i][col] = 0;
                        col--;
                    }
                    if (col > 0 && board[i][col - 1] == board[i][col]) {
                        board[i][col - 1] *= 2;
                        score += board[i][col - 1];  // Update score when tiles merge
                        board[i][col] = 0;
                    }
                }
            }
        }
    }

    // Function to move tiles right
    public void moveRight() {
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j >= 0; j--) {
                if (board[i][j] != 0) {
                    int col = j;
                    while (col < 3 && board[i][col + 1] == 0) {
                        board[i][col + 1] = board[i][col];
                        board[i][col] = 0;
                        col++;
                    }
                    if (col < 3 && board[i][col + 1] == board[i][col]) {
                        board[i][col + 1] *= 2;
                        score += board[i][col + 1];  // Update score when tiles merge
                        board[i][col] = 0;
                    }
                }
            }
        }
    }

    // Function to check if the game is over
    public boolean checkGameOver() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    return false; // There are still empty spaces
                }
                if (i < 3 && board[i][j] == board[i + 1][j]) {
                    return false; // Possible vertical merge
                }
                if (j < 3 && board[i][j] == board[i][j + 1]) {
                    return false; // Possible horizontal merge
                }
            }
        }
        return true; // No more moves
    }
}
        