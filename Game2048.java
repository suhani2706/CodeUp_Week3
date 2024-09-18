/***
 * The below code is the game of 2048. 2048 is a single-player sliding tile puzzle video game.
 * Enter w,a,s or d or move up, left, down or right respectively.
 * Owner : Suhani Mathur
 * Created on : 17 Sep 2024
 */



package com.Gamepedia;
import java.util.Scanner;

public class Game2048 {

	 public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        Logic2048 game = new Logic2048();
	        boolean gameOver = false;

	        game.placeRandomTile();
	        game.placeRandomTile();

	        // Main game loop
	        while (!gameOver) {
	            game.printBoard();
	            System.out.println("Score: " + game.getScore());
	            System.out.print("Enter move (w/a/s/d): ");
	            char move = scanner.next().charAt(0);

	            // Perform move
	            switch (move) {
	                case 'w':
	                    game.moveUp();
	                    break;
	                case 's':
	                    game.moveDown();
	                    break;
	                case 'a':
	                    game.moveLeft();
	                    break;
	                case 'd':
	                    game.moveRight();
	                    break;
	                default:
	                    System.out.println("Invalid move! Use w/a/s/d keys.");
	                    continue;
	            }
	            game.placeRandomTile();

	            if (game.checkGameOver()) {
	                gameOver = true;
	                System.out.println("Game Over!");
	                System.out.println("Final Score: " + game.getScore());
	            }
	        }
	        scanner.close();
	    }
}