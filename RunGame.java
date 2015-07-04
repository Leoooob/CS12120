import java.util.Scanner;

public class RunGame {
	public static void main(String[] args) {
		PlayingBoard Board = new PlayingBoard();
		PlayingBoard.startGame();   //Class reference used because the method is static 
		Scanner input = new Scanner (System.in);
		System.out.println("\nYou are at level " + Board.getLevel());
		System.out.println("\nPress enter to make your next move");
		String line = null;	//Sets 'line' to null, then checks the input for this value to take another move

		//System.out.println("getLevel: " + Test.getLevel());
		do{
			//System.out.println("getMoveNo: " + Test.getMoveNo());
			if(line == input.nextLine()) {
				//System.out.println("We have no new move!");
			}
			else {
				//System.out.println("getMoveNo: " + Test.getMoveNo());
				GamePlay.findMoveableCell();
			}
		} while(Board.getLevel() < 5);	//Keeps this loop going until you finish the game or die trying
	}
}