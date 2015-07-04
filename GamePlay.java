import java.util.Random;

public class GamePlay {
	private static int Lives = 1;
	private static int MoveNo = 0;
	private static boolean canMove = true;
	private static boolean WalkSmart = false;
	private static int Infection;

	public void setInfection(int I) {
		Infection = I;
	}
	
	public static void findMoveableCell() {	// finds all cells with a value that moves or replicates
		int i, j;
		char[][] myGrid = PlayingBoard.getGrid();
		for(i = 0; i < 12; i++) {
			for(j = 0; j < 12; j++) {
				if(myGrid[i][j] == 'P') {
					cellMovement('P', i, j);
					canMove = true;
				}
				else if(myGrid[i][j] == 'I') {
					cellMovement('I', i, j);
					canMove = true;
				}
			}
		}
	PlayingBoard.printBoard();
	}

	public static void cellMovement(char t, int i, int j) {	//Moving or replicating the cell.
		Random rand = new Random();							//Also calls methods that check if player is dying
		int dir = rand.nextInt(4);
		//canMove = true;

		//System.out.println(t);
		int Level = PlayingBoard.getLevel();
		char[][] myGrid = PlayingBoard.getGrid();
		if(t == 'x') {	//Protects against accidentily parsing through a cell that is 'default' and therefore cannot move
		}
		else if(t == 'I') {
			double chance = Math.random();
			//System.out.println(chance);
			if((chance < 0.35 && Level < 4)||(chance < 0.4 && Level == 4)) {
				//System.out.println(dir);
				switch(dir) {
					case 0:
						//System.out.println("case 0");
						if(i != 0 && myGrid[i-1][j] == 'x' && canMove){
							canMove = false;
							myGrid[i-1][j] = t;
							}
						break;
					case 1:
						//System.out.println("case 1");
						if(i != 11 && myGrid[i+1][j] == 'x' && canMove){
							canMove = false;
							myGrid[i+1][j] = t;
							}
						break;
					case 2:
						//System.out.println("case 2");
						if(j != 0 && myGrid[i][j-1] == 'x' && canMove){
							canMove = false;
							myGrid[i][j-1] = t;
							}
						break;
					case 3:
						//System.out.println("case 3");
						if(j != 11 && myGrid[i][j+1] == 'x' && canMove){
							canMove = false;
							myGrid[i][j+1] = t;
							}
						break;
				}
			}
		}
		else if(t == 'P'){
			//System.out.println("Direction: " + dir);
			if(dir == 0 && canMove) {
				if(i != 0) {
					if(myGrid[i-1][j] == 'x' || myGrid[i-1][j] == 'I'){
						if(myGrid[i-1][j] == 'I') {
							if(WalkSmart && !checkAdjacentCells(i, j) && canMove) {
								//System.out.println("I'm surrounded by I");
								cellMovement(t, i, j);
								canMove = true;
								return;
							}
							else if(canMove) {
								Infection++;
								System.out.println("You have stepped on " + Infection + " infected tiles!");
								canMove = false;
								myGrid[i][j] = 'x';
								myGrid[i-1][j] = t;
								if(Infection > 5) {
									youDead();
									return;
								}
							}
						}
						else if(canMove) {
							canMove = false;
							myGrid[i][j] = 'x';
							myGrid[i-1][j] = t;
						}
					}
					else if(myGrid[i-1][j] == 'D') {
						if(WalkSmart && !checkAdjacentDisease(i,j) && canMove) {
							//System.out.println("I'm surrounded by D");
							cellMovement(t, i, j);
							canMove = true;
							return;
						}
						else {
							if(Lives == 1) {
								System.out.println("You have stepped on a diseased tile and died! Better luck next time.");
								Scores.AddScore();
								System.exit(0);
							}
							else {
								Lives--;
								System.out.println("You contracted the disease and died, the leve will now restart. You now have " + Lives + " lives left, good luck.");
								MoveNo = 0;
								Infection = 0;
								PlayingBoard.resetBoard();
								PlayingBoard.generateBoard(PlayingBoard.getnLevel());
							}
						}
					}
					return;
				}
				else if(canMove) {
					//System.out.println("I'm surrounded by border");
					cellMovement(t, i, j);
					return;
				}
			}
			else if(dir == 1 && canMove) {
				if(i != 11) {
					if(myGrid[i+1][j] == 'x' || myGrid[i+1][j] == 'I'){
						if(myGrid[i+1][j] == 'I') {
							if(WalkSmart && !checkAdjacentCells(i, j) && canMove) {
								//System.out.println("I'm surrounded by I");
								cellMovement(t, i, j);
								canMove = true;
								return;
							}
							else if(canMove) {
								Infection++;
								System.out.println("You have stepped on " + Infection + " infected tiles!");
								canMove = false;
								myGrid[i][j] = 'x';
								myGrid[i+1][j] = t;
								if(Infection > 5) {
									youDead();
									return;
								}
							}
						}
						else if(canMove) {
							canMove = false;
							myGrid[i][j] = 'x';
							myGrid[i+1][j] = t;
						}
					}
					else if(myGrid[i+1][j] == 'D') {
						if(WalkSmart && !checkAdjacentDisease(i,j) && canMove) {
							//System.out.println("I'm surrounded by D");
							cellMovement(t, i, j);
							canMove = true;
							return;
						}
						else {
							if(Lives == 1) {
								System.out.println("You have stepped on a diseased tile and died! Better luck next time.");
								Scores.AddScore();
								System.exit(0);
							}
							else {
								Lives--;
								System.out.println("You contracted the disease and died, the leve will now restart. You now have " + Lives + " lives left, good luck.");
								MoveNo = 0;
								Infection = 0;
								PlayingBoard.resetBoard();
								PlayingBoard.generateBoard(PlayingBoard.getnLevel());
							}
						}
					}
					return;
				}
				else if(canMove) {
					//System.out.println("I'm surrounded by border");
					cellMovement(t, i, j);
					canMove = true;
					return;
					}
			}
			else if(dir == 2 && canMove) {
				if(j != 0) {
					if(myGrid[i][j-1] == 'x' || myGrid[i][j-1] == 'I'){
						if(myGrid[i][j-1] == 'I') {
							if(WalkSmart && !checkAdjacentCells(i, j) && canMove) {
								//System.out.println("I'm surrounded by I");
								cellMovement(t, i, j);
								canMove = true;
								return;
							}
							else if(canMove) {
								Infection++;
								System.out.println("You have stepped on " + Infection + " infected tiles!");
								canMove = false;
								myGrid[i][j] = 'x';
								myGrid[i][j-1] = t;
								if(Infection > 5) {
									youDead();
									return;
								}
							}
						}
						else if(canMove) {
							canMove = false;
							myGrid[i][j] = 'x';
							myGrid[i][j-1] = t;
						}
					}
					else if(myGrid[i][j-1] == 'D') {
						if(WalkSmart && !checkAdjacentDisease(i,j) && canMove) {
							//System.out.println("I'm surrounded by D");
							cellMovement(t, i, j);
							canMove = true;
							return;
						}
						else {
							if(Lives == 1) {
								System.out.println("You have stepped on a diseased tile and died! Better luck next time.");
								Scores.AddScore();
								System.exit(0);
							}
							else {
								Lives--;
								System.out.println("You contracted the disease and died, the leve will now restart. You now have " + Lives + " lives left, good luck.");
								MoveNo = 0;
								Infection = 0;
								PlayingBoard.resetBoard();
								PlayingBoard.generateBoard(PlayingBoard.getnLevel());
							}
						}
					}
					return;
				}
				else if(canMove) {
					//System.out.println("I'm surrounded by border");
					cellMovement(t, i, j);
					canMove = true;
					return;
				}
			}
			else  if(canMove) { // final available direction
				if(j != 11) {
					if(myGrid[i][j+1] == 'x' || myGrid[i][j+1] == 'I'){
						if(myGrid[i][j+1] == 'I') {
							if(WalkSmart && !checkAdjacentCells(i, j) && canMove) {
								//System.out.println("I'm surrounded by I");
								cellMovement(t, i, j);
								canMove = true;
								return;
							}
							else if(canMove) {
								Infection++;
								System.out.println("You have stepped on " + Infection + " infected tiles!");
								canMove = false;
								myGrid[i][j] = 'x';
								myGrid[i][j+1] = t;
								if(Infection > 5) {
									youDead();
									return;
								}
							}
						}
						else if(canMove) {
							canMove = false;
							myGrid[i][j] = 'x';
							myGrid[i][j+1] = t;
						}
					}
					else if(myGrid[i][j+1] == 'D') {
						if(WalkSmart && !checkAdjacentDisease(i,j) && canMove) {
							//System.out.println("I'm surrounded by D");
							cellMovement(t, i, j);
							canMove = true;
							return;
						}
						else {
							if(Lives == 1) {
								System.out.println("You have stepped on a diseased tile and died! Better luck next time.");
								Scores.AddScore();
								System.exit(0);
							}
							else {
								Lives--;
								System.out.println("You contracted the disease and died, the leve will now restart. You now have " + Lives + " lives left, good luck.");
								MoveNo = 0;
								Infection = 0;
								PlayingBoard.resetBoard();
								PlayingBoard.generateBoard(PlayingBoard.getnLevel());
							}
						}
					}
					return;
				}
				else if(canMove) {
					//System.out.println("I'm surrounded by border");
					cellMovement(t, i, j);
					canMove = true;
					return;
				}
			}
		}
		checkInfectedCell();
	}

	public static boolean checkAdjacentCells(int i,int j) {	//Checks to see if neighbouring cells are infected or diseased
		char[][] myGrid = PlayingBoard.getGrid();
		boolean leftInfected = ((i != 0) && (myGrid[i-1][j]=='I'||myGrid[i-1][j] == 'D'));
		boolean rightInfected = ((i != 11) && (myGrid[i+1][j]=='I'||myGrid[i+1][j] == 'D'));
		boolean topInfected = ((j != 0) && (myGrid[i][j-1]=='I'||myGrid[i][j-1] == 'D'));
		boolean bottomInfected = ((j != 11) && (myGrid[i][j+1]=='I'||myGrid[i][j+1] == 'D'));
		if(leftInfected && rightInfected && topInfected && bottomInfected) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean checkAdjacentDisease(int i,int j) {	//Checks if neighbouring cells are all diseased
		char[][] myGrid = PlayingBoard.getGrid();
		boolean leftInfected = ((i != 0) && myGrid[i-1][j] == 'D');
		boolean rightInfected = ((i != 11) && myGrid[i+1][j] == 'D');
		boolean topInfected = ((j != 0) && myGrid[i][j-1] == 'D');
		boolean bottomInfected = ((j != 11) && myGrid[i][j+1] == 'D');
		if(leftInfected && rightInfected && topInfected && bottomInfected) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void youDead() {	//Once you have a certain amount (5 tiles) of infection
		double chance = Math.random();//You have a 40% chance of death on each subsequent tile
		if(chance < 0.4) {
			if(Lives == 1) {
				System.out.println("You have stepped on too many infected tiles and have succumbed to the disease! Better luck next time.");
				Scores.AddScore();
				System.exit(0);
			}
			else {
				Lives--;
				System.out.println("You succombed to the infection, the level will now restart. You now have " + Lives + " lives left, good luck.");
				MoveNo = 0;	//Resets the number of turns and your infection level upon resetting the level
				Infection = 0;
				PlayingBoard.resetBoard();
				PlayingBoard.generateBoard(PlayingBoard.getnLevel());
			}
		}
	}

	public static void checkInfectedCell() {	//Creates the diseased cells and if a player is surrounded
		int i, j;								//by infected/diseased cells, decrements a life.
		char[][] myGrid = PlayingBoard.getGrid();
		for(i = 0; i < 12; i++) {
			for(j = 0; j < 12; j++) {
				if(myGrid[i][j] == 'I'){
					if(checkAdjacentCells(i, j)) {
						myGrid[i][j] = 'D';
					}
				}
				if(myGrid[i][j] == 'P') {
					if(checkAdjacentCells(i, j)) {
						System.out.println("You contracted the disease! Better luck next time.");
						if(Lives == 1) {
							Scores.AddScore();
							System.exit(0);
						}
						else {
							Lives--;
							System.out.println("You died, the level will now reset. You now have " + Lives + " lives left, good luck.");
							MoveNo = 0;
							Infection = 0;
							PlayingBoard.resetBoard();
							PlayingBoard.generateBoard(PlayingBoard.getnLevel());
						}
					}
				}
			}
		}
	}

	public static void levelCheck() {	//ends the turn and checks to see if you have completed the level
		MoveNo++;
		//Moved = false;
		int Level = PlayingBoard.getLevel();
		System.out.println("You are now at " + MoveNo + " turns");
		if(Level == 4) {
			if(MoveNo == 20) {
				System.out.println("Congratulations, you've beaten the game!\n");
				Scores.AddScore();
				System.exit(0);
			}
		}
		else if(Level == 3) {
			if(MoveNo == 20) {
				MoveNo = 0;
				Infection = 0;
				Lives = 3;
				System.out.println("\nCongratulations, you're now at level 4. You now have 2 extra lives where the board will restart.");
				PlayingBoard.setLevel(4);
				PlayingBoard.resetBoard();
				PlayingBoard.generateBoard(PlayingBoard.getnLevel());
			}
		}
		else if(Level == 2) {
			if(MoveNo == 15) {
				MoveNo = 0;
				Infection = 0;
				System.out.println("\nCongratulations, you're now at level 3. You now need 20 moves to win!\n");
				WalkSmart = true;
				PlayingBoard.setLevel(3);
				PlayingBoard.resetBoard();
				PlayingBoard.generateBoard(PlayingBoard.getnLevel());
			}
		}
		else {
			if(MoveNo == 10){
				MoveNo = 0;
				Infection = 0;
				System.out.println("\nCongratulations, you're now at level 2. You now need 15 moves to win!\n");
				PlayingBoard.setLevel(2);
				PlayingBoard.resetBoard();
				PlayingBoard.generateBoard(PlayingBoard.getnLevel());
			}
		}
	}

	public static int getPoints() {
		
		int Score = (MoveNo * nLevel);
		return Score;
	}
}