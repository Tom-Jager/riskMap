
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;

public class GameBoard extends JFrame{
	// declares a global empty 2D array for my 8x8 gameboard
	private static String[][] gameBoardArr = new String[8][8];
	private static String[] slashTiles = new String[8];
	private static final long serialVersionUID = 1L;

	//checks for allied victory
	 static boolean alCheckVic(String[][] board) {
		if(board[7][7].contains("A")){
			System.out.println("Allies win");
			return true;
		}
		else{
			return false;
		}
			
	}
	
	//checks for axis victory
	 static boolean axCheckVic(String[][] board) {
		if(board[0][0].contains("A")){
			System.out.println("Axis win");
			return true;
		}
		else{
			return false;
		}
			
	}

	// declares the array to be 8x8 and sets it.
	static void initGameBoard(){
		for (int y=0; y<8; y++){
			for(int x=0; x<8; x++){
				gameBoardArr[y][x] = "###,";
			}
		}
		int x =0;
		for(int y=7;y>=0;y--){
			gameBoardArr[y][x] = "#/#,";
			slashTiles[x] = String.valueOf(x)+String.valueOf(y);
			x++;
		}
		gameBoardArr[0][0] = "AOO,";
		gameBoardArr[7][7] = "XOO,";
	}

	static void setGameBoard(String[][] argBoard){
		gameBoardArr = argBoard;
	}

	static String[][] getGameBoard(){
		return gameBoardArr;
	}
	
	//places allied troops on 5 set locations
	static void setAllies(String[][] board){
		board[7][5] = "A:20";
		board[4][2] = "A:10";
		board[3][3] = "A:10";
		board[2][4] = "A:10";
		board[1][5] = "A:10";
	}
	// places axis troops on 5 set locations
	static void setAxis(String[][] board){
		board[0][2] = "X:10";	
		board[6][6] = "X:10";
		board[5][7] = "X:10";
		board[6][5] = "X:10";
		board[5][3] = "X:5";
	}
	
	//prints the board for a basic early visual
	static void printBoard(String[][] board){
		String line = "";
		for(int y=7;y>=0;y--){
			for(int x = 0;x<8;x++){
				line = line + board[y][x];
			}
			System.out.println(line);
			line = "";
		}
	}
	
	//moves the troops to a new position and performs an action according to whats on destination
	static void moveTroops(String[][] board, int yF, int xF, int yT, int xT, int quant, String team ){
		String en;
		//determines on what team is moving
		if(team=="A"){
			en = "X";
		}
		else{
			en = "A";
		}
		//gets the number of troops on original tile
		String orig = board[yF][xF];
		orig = orig.replace(team + ":","");
		int oQuant = Integer.parseInt(orig);
		//checks your not trying to move too many troops
		if(quant>oQuant){
			System.out.println("There's not enough troops on that tile");
			return;
		}
		//checks if you're trying to put your own troops on your obj tile
		if(board[yT][xT].contains(team)){
			if(board[yT][xT].contains("O")){
				System.out.println("You can't do that");
			}
			// if the destination tile has your own team already on it, it combines troops
			else{
				String dest = board[yT][xT];
				dest = dest.replace(team+":","");
				int nQuant = Integer.parseInt(dest);
				board[yT][xT]=team + ":" + String.valueOf(nQuant+quant);
			}
		}
		// if the destination tile has an enemy troop on it, it launches an attack
		else if(board[yT][xT].contains(en+":")){
				String dest = board[yT][xT];
				dest = dest.replace(en + ":" ,"");
				int nQuant = Integer.parseInt(dest);
				// calls the attack function to determine the result and returns the number of attacking troops remaining
				int[] win = attack(quant,nQuant);
				//determines who won based on the number of attacking troops remaining and if the attacking team wins
				//they replace the other teams troops with their own remaining ones
				if(win[0]>1){
					board[yT][xT] = team + ":"+ String.valueOf(win[0]);
				}
				// if the defending team won, then their troops on their tile are reduced by the number that died
				//the attacking team stays on their tile and lose all but 1 of their attacking troops
				else{
					board[yT][xT] = en + ":"+ String.valueOf(win[1]);
					board[yF][xF] = team + ":" + String.valueOf(oQuant - quant + 1);
					return;
				}
		}
		//if destination tile is empty, it moves its moving troops.
		else{
			board[yT][xT] = team + ":"+ String.valueOf(quant);
		}
		//checks if there should be any remaining on the previous tile, if not signifies that its empty. Also determines whether a slash should be on previous tile.
		if(oQuant-quant==0){
			String checkSlash = String.valueOf(xF)+String.valueOf(yF);
			if(Arrays.asList(slashTiles).contains(checkSlash)){
				board[yF][xF] = "#/#";
			}
			else {
				board[yF][xF] = "###,";
			}
		}
		//places remaining troops on original tile
		else{
			board[yF][xF] = team + ":" + String.valueOf(oQuant-quant);
			}
		}
	
	//attack algorithm which generates the results of an attack as an array
	private static int[] attack(int attPop, int defPop) {
		Random rand = new Random();
		//ensures attacks will stop once one side has been defeated
		while(attPop > 1 && defPop > 0){
			// the next 5 lines set attNum and defNum as a maximum of 3 dice can be rolled at once
			int attNum = attPop;
			
			if(attNum>=3){attNum=3;}
			else{attNum=2;}
			
			int defNum = defPop;
			if(defNum>=3){defNum=3;}
			
			//Declares an array for holding dice rolls for each side
			int[] attArr = new int[3];
			int[] defArr = new int[3];
			for(int i=0;i<3;i++){
				attArr[i]=0;
				defArr[i]=0;
			}
			
			//Rolls the dice for as many troops as are available
			for(int i=0;i<attNum;i++){
				attArr[i] = rand.nextInt(6) + 1;
			}
			for(int i=0;i<defNum;i++){
				defArr[i] = rand.nextInt(6) + 1;
			}
			//sorts them in descending order
			attArr = sort(attArr);
			defArr = sort(defArr);
			
			//decreases the side that loses. Also ensures that the correct dice a hit against each other
			if(attNum>defNum){
				for(int i=0;i<defNum;i++){
					if(attArr[i]>defArr[i]){
					defPop = defPop - 1;
					}
				else{
					attPop = attPop-1;
					}
				}
			}
			else{
				for(int i=0;i<attNum;i++){
					if(attArr[i]>defArr[i]){
					defPop = defPop - 1;
					}
				else{
					attPop = attPop-1;
					}
				}
			}
		}
		//returns how many defenders and attackers are remaining
		int[] report = new int[2];
		report[0] = attPop;
		report[1] = defPop;
		return report;
	}

	
	//sorts my dice arrays into descending order
	private static int[] sort(int[] arr){
		int b = 1;
		int val1;
		int val2;
		for(int a =0;a<2;a++){
			val1 = arr[a];
			val2 = arr[b];
			if(val2>val1){
				arr[a] = val2;
				arr[b] = val1;
			}
			b = 2;
		}
		val1 = arr[0];
		val2 = arr[1];
		if(val2>val1){
			arr[0] = val2;
			arr[1] = val1;
		}
		return arr;
	}

}


