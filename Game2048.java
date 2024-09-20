/***
 * The below code is the game of 2048. 2048 is a single-player sliding tile puzzle video game.
 * Enter w, a, s, or d to move up, left, down, or right, respectively.
 * Owner : Suhani Mathur
 * Created on : 17 Sep 2024
 */

package com.Gamepedia;

import java.util.Scanner;

public class Game2048 {

	public static void main(String[] args) {
        Logic2048 logic = new Logic2048();
        logic.placeRandomTile();
        logic.placeRandomTile();
        boolean gameOver = false;

        Scanner scanner = new Scanner(System.in);

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
            }

            if (moved) {
            	logic.placeRandomTile();
            }

            if (logic.checkGameOver()) {
                gameOver = true;
                System.out.println("Game Over! Final Score: " + logic.getScore());
                System.out.println("\f");
            }
        }
        scanner.close();
    }
	
	// Make sure that the user enter a valid move
    public static char getValidMove(Scanner scanner) {
        char move = ' ';
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter move (w/a/s/d): ");
            String input = scanner.nextLine();

            if (input.length() == 1 && "wasd".contains(input.toLowerCase())) {
                move = input.toLowerCase().charAt(0);
                validInput = true;
            } else {
                System.out.println("Invalid input! Please enter w/a/s/d.");
            }
        }

        return move;
    }
}
