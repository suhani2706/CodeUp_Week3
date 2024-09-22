/***
 * The below code contains the functions used to run the game of 2048.
 * Enter w,a,s or d or move up, left, down or right respectively.
 * Owner : Suhani Mathur
 * Created on : 17 Sep 2024
 */

package com.Gamepedia;


import java.util.*;



public class Logic2048 {
	public static final String RESET1 = "\u001B[0m";

	// Black text color
	public static final String BLACK_TEXT = "\u001B[30m";
	public static final String WHITE_TEXT = "\u001B[37m";

    private int[][] previousBoard;
    private int[][] board;
    private int score;
    private int moveCount;
    private static final int SIZE = 4;

    public Logic2048() {
        board = new int[SIZE][SIZE];
        previousBoard = new int[SIZE][SIZE]; // Used for undo functionality
        score = 0;
        moveCount = 0;
        placeRandomTile();
        placeRandomTile();
    }

    public int getScore() {
        return score;
    }

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

    public void placeRandomTile() {
        List<int[]> emptyPositions = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    emptyPositions.add(new int[]{i, j});
                }
            }
        }

        if (!emptyPositions.isEmpty()) {
            int[] pos = emptyPositions.get(new Random().nextInt(emptyPositions.size()));
            board[pos[0]][pos[1]] = Math.random() < 0.9 ? 2 : 4; // Randomly place 2 or 4
        }
    }

    public void saveState() {
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, previousBoard[i], 0, SIZE);
        }
    }

    public void undo() {
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(previousBoard[i], 0, board[i], 0, SIZE);
        }
        System.out.println("Undo successful!");
    }

    public boolean moveUp() {
        return move(0, -1);
    }

    public boolean moveDown() {
        return move(0, 1);
    }

    public boolean moveLeft() {
        return move(-1, 0);
    }

    public boolean moveRight() {
        return move(1, 0);
    }

    private boolean move(int dx, int dy) {
        saveState(); // Save current state before move
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

    private boolean moveTile(int x, int y, int dx, int dy) {
        boolean moved = false;
        while (x + dx >= 0 && x + dx < SIZE && y + dy >= 0 && y + dy < SIZE && board[x + dx][y + dy] == 0) {
            board[x + dx][y + dy] = board[x][y];
            board[x][y] = 0;
            x += dx;
            y += dy;
            moved = true;
        }

        if (x + dx >= 0 && x + dx < SIZE && y + dy >= 0 && y + dy < SIZE && board[x + dx][y + dy] == board[x][y]) {
            board[x + dx][y + dy] *= 2;
            score += board[x + dx][y + dy];
            board[x][y] = 0;
            moved = true;
        }

        return moved;
    }

    public boolean checkGameOver() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) return false; // Empty space
                if (i < SIZE - 1 && board[i][j] == board[i + 1][j]) return false; // Same tile vertically
                if (j < SIZE - 1 && board[i][j] == board[i][j + 1]) return false; // Same tile horizontally
            }
        }
        return true;
    }

    public String getBoardState() {
        StringBuilder state = new StringBuilder();
        for (int[] row : board) {
            for (int tile : row) {
                state.append(tile).append(" ");
            }
            state.append("\n");
        }
        return state.toString();
    }

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

    // Utility function to map tile value to a color
    private String getColor(int value) {
        if (value == 0) return Game2048.COLORS[0];
        int index = (int) (Math.log(value) / Math.log(2));
        return index < Game2048.COLORS.length ? Game2048.COLORS[index] : Game2048.COLORS[Game2048.COLORS.length - 1];
    }
}

        