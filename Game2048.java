package com.Gamepedia;

import java.util.*;

public class Game2048 {

	public static final String RESET = "\033[0m";   // Reset color
	public static final String[] COLORS = {
	        "\u001B[48;5;130m",  // Background color for 0 (White)
	        "\u001B[48;5;230m",  // 2 - Light grey
	        "\u001B[48;5;187m",  // 4 - Pale yellow
	        "\u001B[48;5;220m",  // 8 - Orange
	        "\u001B[48;5;214m",  // 16 - Deep orange
	        "\u001B[48;5;208m",  // 32 - Red-orange
	        "\u001B[48;5;202m",  // 64 - Bright red
	        "\u001B[48;5;94m",   // 128 - Dark red
	        "\u001B[48;5;136m",  // 256 - Dark yellow
	        "\u001B[48;5;142m",  // 512 - Lime green
	        "\u001B[48;5;82m",   // 1024 - Green
	        "\u001B[48;5;46m",   // 2048 - Bright green
	        "\u001B[48;5;21m",   // Above 2048 - Blue
	    };
	 public static void exitGame(int score) {
	        System.out.println("Thank you for playing! Your final score is: " + score);
	        System.exit(0); 
	    }

	//Main function
    public static void main(String[] args) {
        boolean playAgain;
        Scanner scanner = new Scanner(System.in);

        // Ask the user for their name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        System.out.println("Welcome to 2048, " + playerName + "!");
        
        do {
            playAgain = false;
            Logic2048 logic = new Logic2048();
            logic.placeRandomTile();
            logic.placeRandomTile();
            boolean gameOver = false;
            String lastState = logic.getBoardState(); 

            while (!gameOver) {
                logic.printColoredBoard();  // Changed to print with color
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
                    case 'e': 
                        exitGame(logic.getScore());
                        break;
                    default:
                        System.out.println("Invalid move! Please enter w/a/s/d/u/e.");
                        continue; 
                }

                if (moved) {
                    logic.placeRandomTile();
                    lastState = logic.getBoardState(); 
                }

                if (logic.checkGameOver()) {
                    gameOver = true;
                    System.out.println("Game Over, " + playerName + "! Final Score: " + logic.getScore());
                    System.out.println("Highest Tile: " + logic.getHighTile());
                    System.out.println("Total Moves: " + logic.getMoveCount());
                }
            }

            System.out.print("Do you want to play again, " + playerName + "? (y/n): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                playAgain = true;
            } else {
                System.out.println("Thanks for playing, " + playerName + "! Exiting...");
            }
        } while (playAgain);
    }

    // Make sure that the user enters a valid move
    public static char getValidMove(Scanner scanner) {
        char move = ' ';
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter move (w/a/s/d) or 'u' for undo or 'e' for exit: ");
            String input = scanner.nextLine();

            if (input.length() == 1 && "wasdue".contains(input.toLowerCase())) {
                move = input.toLowerCase().charAt(0);
                validInput = true;
            } else {
                System.out.println("Invalid input! Please enter w/a/s/d or 'u'.");
            }
        }

        return move;
    }
    
   
}
