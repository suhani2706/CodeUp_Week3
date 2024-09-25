/***
 * The below code contains the functions used to run the game of 2048.
 * Enter w,a,s or d or move up, left, down or right respectively.
 * Owner : Suhani Mathur
 * Created on : 17 Sep 2024
 */

package com.Gamepedia;

public class Logic2048 {
	//Static variables
    public static final String RESET1 = "\u001B[0m";
    public static final String BLACK_TEXT = "\u001B[30m";
    public static final String WHITE_TEXT = "\u001B[37m";

    private int[][] previousBoard;
    private int[][] board;
    private int score;
    private int moveCount;
    private static final int SIZE = 4;
    
    //constructor
    public Logic2048() {
        board = new int[SIZE][SIZE];
        previousBoard = new int[SIZE][SIZE]; 
        score = 0;
        moveCount = 0;
        placeRandomTile();
    }

    //Saving score
    public int getScore() {
        return score;
    }

    //Printing the colored board
    public void printColoredBoard() {
        System.out.println("--------------------------");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int value = board[i][j];
                String color = getColor(value);
                if (value == 0) {
                    System.out.print(color + WHITE_TEXT + ".\t" + RESET1);
                } else {
                    System.out.print(color + BLACK_TEXT + value + "\t" + RESET1);
                }
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    //Placing the numbers in random tile
    public void placeRandomTile() {
        int[][] emptyPositions = new int[SIZE * SIZE][2];
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    emptyPositions[count][0] = i;
                    emptyPositions[count][1] = j;
                    count++;
                }
            }
        }

        if (count > 0) {
            int[] pos = emptyPositions[(int)(Math.random() * count)];
            board[pos[0]][pos[1]] = Math.random() < 0.9 ? 2 : 4;
        }
    }

    //Saving every state if user wants to undo
    public void saveState() {
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, previousBoard[i], 0, SIZE);
        }
    }

    
    //If user wants to undo
    public void undo() {
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(previousBoard[i], 0, board[i], 0, SIZE);
        }
        System.out.println("Undo successful!");
    }

    //Moving up
    public boolean moveUp() {
        return move(-1, 0);
    }

  //Moving down
    public boolean moveDown() {
        return move(1, 0);
    }

  //Moving left
    public boolean moveLeft() {
        return move(0, -1);
    }

  //Moving right
    public boolean moveRight() {
        return move(0, 1);
    }

  //Moving the number blocks
    private boolean move(int dx, int dy) {
        saveState();
        boolean moved = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int x = dx > 0 ? SIZE - 1 - i : i;
                int y = dy > 0 ? SIZE - 1 - j : j;
                if (board[x][y] != 0) {
                    moved |= moveTile(x, y, dx, dy);
                }
            }
        }

        if (moved) {
            moveCount++;
            placeRandomTile();
        }

        return moved;
    }

    //Moving the tile to input direction
    private boolean moveTile(int x, int y, int dx, int dy) {
        int newX = x;
        int newY = y;
        boolean moved = false;

        // Move the tile in the given direction until it reaches a boundary or another tile
        while (isValid(newX + dx, newY + dy) && board[newX + dx][newY + dy] == 0) {
            newX += dx;
            newY += dy;
            moved = true;
        }

        // If moved, update the board by shifting the tile
        if (moved) {
            board[newX][newY] = board[x][y];
            board[x][y] = 0;
        }

        // If the next tile has the same value, merge them
        if (isValid(newX + dx, newY + dy) && board[newX + dx][newY + dy] == board[newX][newY]) {
            board[newX + dx][newY + dy] *= 2;
            score += board[newX + dx][newY + dy];
            board[newX][newY] = 0;
            moved = true;
        }

        return moved;
    }

    // Helper method to check if the next move is within bounds
    private boolean isValid(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }


    //Checking if the game ends
    public boolean checkGameOver() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) return false;
                if (i < SIZE - 1 && board[i][j] == board[i + 1][j]) return false;
                if (j < SIZE - 1 && board[i][j] == board[i][j + 1]) return false;
            }
        }
        return true;
    }

    //Returns a string representation of the current board state.
    public String getBoardState() {
        String state = "";
        for (int i = 0; i < SIZE; i++) { 
            for (int j = 0; j < SIZE; j++) {
                state += board[i][j] + " ";
            }
            state += "\n"; 
        }
        return state;
    }
    
    //Returns the highest tile value on the board
    public int getHighTile() {
        int maxTile = 0;
        for (int[] row : board) {
            for (int tile : row) {
                if (tile > maxTile) {
                    maxTile = tile;
                }
            }
        }
        return maxTile;
    }

    public int getMoveCount() {
        return moveCount;
    }

    private String getColor(int value) {
        if (value == 0) return Game2048.COLORS[0]; 

        switch (value) {
            case 2: return Game2048.COLORS[1];
            case 4: return Game2048.COLORS[2];
            case 8: return Game2048.COLORS[3];
            case 16: return Game2048.COLORS[4];
            case 32: return Game2048.COLORS[5];
            case 64: return Game2048.COLORS[6];
            case 128: return Game2048.COLORS[7];
            case 256: return Game2048.COLORS[8];
            case 512: return Game2048.COLORS[9];
            case 1024: return Game2048.COLORS[10];
            case 2048: return Game2048.COLORS[11];
            default: return Game2048.COLORS[Game2048.COLORS.length - 1]; // Default for larger values
        }
    }
}




        