/***
 * The below code is the game of 2048. 2048 is a single-player sliding tile puzzle video game.
 * Enter w, a, s, or d to move up, left, down, or right, respectively.
 * Owner : Suhani Mathur
 * Created on : 17 Sep 2024
 */

package com.Gamepedia;

import java.util.*;

public class Game2048 {

    public static void main(String[] args) {
        boolean playAgain;

        do {
            playAgain = false;
            Logic2048 logic = new Logic2048();
            logic.placeRandomTile();
            logic.placeRandomTile();
            boolean gameOver = false;
            Scanner scanner = new Scanner(System.in);
            String lastState = logic.getBoardState(); 

            while (!gameOver) {
                logic.printBoard();
                System.out.println("Score: " + logic.getScore());

                char move = getValidMove(scanner);
                boolean moved = false;

                switch (move) {
                    case 'w':
                        moved = logic.moveUp();
                        break;
                    case 's':
                        moved = logic.moveDown();
                        break;
                    case 'a':
                        moved = logic.moveLeft();
                        break;
                    case 'd':
                        moved = logic.moveRight();
                        break;
                    case 'u':
                        logic.undo(); 
                        moved = false;
                        break;
                }

                if (moved) {
                    logic.placeRandomTile();
                    lastState = logic.getBoardState(); 
                }

                if (logic.checkGameOver()) {
                    gameOver = true;
                    System.out.println("Game Over! Final Score: " + logic.getScore());
                    System.out.println("Highest Tile: " + logic.getHighTile());
                    System.out.println("Total Moves: " + logic.getMoveCount());
                }
            }

            System.out.print("Do you want to play again? (y/n): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                playAgain = true;
            }
        } while (playAgain);
    }

    // Make sure that the user enters a valid move
    public static char getValidMove(Scanner scanner) {
        char move = ' ';
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter move (w/a/s/d) or 'u' for undo: ");
            String input = scanner.nextLine();

            if (input.length() == 1 && "wasdu".contains(input.toLowerCase())) {
                move = input.toLowerCase().charAt(0);
                validInput = true;
            } else {
                System.out.println("Invalid input! Please enter w/a/s/d or 'u'.");
            }
        }

        return move;
    }
}
