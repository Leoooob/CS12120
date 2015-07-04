import java.util.Random;
import java.util.Scanner;

public class PlayingBoard {
	private static int nLevel;
	private static int Level = 1;
	public static char[][] myGrid = new char[12][12];	//type char so that each cell can have a char value

	public static char[][] getGrid(){
		return myGrid;
	}

	public static int getnLevel() { 
		return nLevel;
	}

	public static int getLevel() { 
		return Level;
	}

	public static void setLevel(int l) {
		Level = l;
	}

	public static void startGame() {	//Starts the game by taking and then generating a number of infected tiles
		Scanner input = new Scanner (System.in);	//Instantiates the Scanner class
		System.out.println("How many infected tiles would you like to start the game with? (Maximum of 9)");
		if(input.hasNextInt()) {
			nLevel = input.nextInt();
			if(nLevel > 9) {
				System.out.println("You can't have that many infected tiles, you'd never survive!\n");
				startGame();
			}
			else if (nLevel <= 0) {
				System.out.println("You need to have some infected tiles, and don't try any negative numbers!\n");
				startGame();
			}
			else if (nLevel <= 9 && nLevel > 0) {
				System.out.println("We will now create your gameboard with " + nLevel + " infected tiles, good luck!\n");
				switch(Level) {
					case 1:
						System.out.println("You need 10 turns to get to the next level.\n");
						break;
					case 2:
						System.out.println("You need 15 turns to get to the next level.\n");
						break;
					case 3:
						System.out.println("You need 20 turns to get to the next level.n");
						break;
					case 4:
						System.out.println("You need 20 turns to get to the next level.\n");
						break;
				}
				GamePlay Game = new GamePlay();
				Game.setInfection(0);
				generateBoard(nLevel);
			}
		}
		else {
			System.out.println("I'm sorry, you didn't enter an integer number. Please try again.");
			startGame();
		}
	}

	public static void resetBoard() {	//Resets the entire board to the default value of 'x'
		int i, j;
		for(i = 0; i < 12; i++) {
			for(j = 0; j < 12; j++) {
				myGrid[i][j] = 'x';
			}
		}
	}

	public static void generateBoard(int nLevel) {	//Creates the unique tiles on the board
		Random rand = new Random();					//eg. Player and Infected tiles
		int i, j;
		int infTile = 0;
		//System.out.println(nLevel + " before");

		if(Level > 1) {
			switch(Level) {
				case 2:
					nLevel++;
					//System.out.println("Case 1: " + nLevel + " after");
					break;
				case 3:
					nLevel += 2;
					//System.out.println("Case 2: " + nLevel + " after");
					break;
				case 4:
					nLevel += 3;
					//System.out.println("Case 3: " + nLevel + " after");
					break;
			}
		}
		for(i = 0; i < 12; i++) {
			for(j = 0; j < 12; j++) {
				if (i == 6 && j == 6) {
					myGrid[i][j] = 'P';
					//System.out.println("I made a player tile");
				}
				else if(myGrid[i][j] == 'I') {
					//System.out.println("I found infected tile");
					myGrid[i][j] = 'x';
					break;
				}
				else if(rand.nextInt(9) == 0 && infTile < nLevel) {	//Checks to make sure too many tiles aren't created while
					myGrid[i][j] = 'I';							//making sure that the rate at which tiles appear is low
					//System.out.println("I made an infected tile");
					infTile++;	//Increments the current number of infected tiles
					//System.out.println("I counted an infected tile (" + infTile +") at " + i + "," + j);
				}
				else {
					myGrid[i][j] = 'x';
				}
			}
		}
		//System.out.println("Print me mofo!");
		printBoard();
	}

	public static void printBoard() {	//Prints the current generation of the gameboard
		int i, j;
		GamePlay Game = new GamePlay();
		System.out.println( "Lives: " + Game.getLives() + "\nLevel: " + Level + "\nNumber of infected tiles: " + nLevel);
		for(i = 0; i < 12; i++) {	//The above printed line shows your lives, level and number of infected tiles at turn 0
			for(j = 0; j < 12; j++) {
				if(j == 0) {
					System.out.print( "| " );	//Creates a border on the left and right side of the grid
				}
				System.out.print( myGrid[i][j] + " " ); //Also gives a space after each cell value
				if(j == 11) {
					System.out.print( "|\n" );//Ends the line after each row of cells
					if(i == 11) {
						System.out.print("\n\n");	//Two line breaks to have a clear break between the grid and 
						GamePlay.levelCheck();	//and other text
					}
				}
			}
		}
	}

}