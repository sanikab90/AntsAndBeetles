import java.util.Scanner;

import java.io.*;
public class Main {
	public static void main(String[] args) throws Exception {
		//make 2D array
		Creature[][] grid = new Creature[10][10];
		int numTurns;
		char antSymbol, beetleSymbol;
		
		//string for file names
		String inputFile;
		
		//get file
		Scanner read = new Scanner(System.in);
		
		//read input file
		inputFile = read.nextLine();
		
		//enter what symbol u want for ant
		antSymbol = read.next().charAt(0);
		
		//enter what symbol u want for beetle
		beetleSymbol = read.next().charAt(0);
		
		//get number of turns
		numTurns = read.nextInt();
		
		//pass to fill grid method
		grid = fillGrid(inputFile, antSymbol, beetleSymbol);
		
		//close scanner
		read.close();
	
		int count = 1;
		int breedTurn = 1;
		while(numTurns > 0) {
			//Beetle move
			BeetleMove(grid);
			
			//ant move
			AntMove(grid);
			
			//beetle starve
			BeetleStarve(grid);
			
			//ant breed
			AntBreed(grid, breedTurn);
			
			//beetle breed
			BeetleBreed(grid, breedTurn);
			
			//print
			print(count, grid, antSymbol, beetleSymbol);
			
			//increment counters
			numTurns--;
			count++;
			breedTurn++;
		}
	}
	
	public static void BeetleMove(Creature[][] grid) {
		for(int col = 0; col < 10; col++) {
			for(int row = 0; row < 10; row++) {
				if(grid[row][col] instanceof Beetle) {
					Beetle obj = (Beetle) grid[row][col];
					if(!obj.getHasMoved()) { //if the object has already moved
						obj.move(grid);
						obj.setHasMoved(true); //set flag
					}
				}
			}
		}
		//loop through array again to put all objects back to false
		for(int col = 0; col < 10; col++) {
			for(int row = 0; row < 10; row++) {
				if(grid[row][col] instanceof Beetle) {
					Beetle obj = (Beetle) grid[row][col];
					obj.setHasMoved(false); //reset flag
				}
			}
		}
	}
	
	public static void AntMove(Creature[][] grid) {
		for(int col = 0; col < 10; col++) {
			for(int row = 0; row < 10; row++) {
				if(grid[row][col] instanceof Ant) { //if the object has already moved
					Ant obj = (Ant) grid[row][col];
					if(!obj.getHasMoved()) {
						obj.move(grid);
						obj.setHasMoved(true); //set flag
					}
				}
			}
		}
		
		//loop through array again to put all objects back to false
		for(int col = 0; col < 10; col++) {
			for(int row = 0; row < 10; row++) {
				if(grid[row][col] instanceof Ant) {
					Ant obj = (Ant) grid[row][col];
					obj.setHasMoved(false); //reset flag
				}
			}
		}
	}
	
	public static void BeetleStarve(Creature[][] grid) {
		for(int col = 0; col < 10; col++) {
			for(int row = 0; row < 10; row++) {
				if(grid[row][col] instanceof Beetle) {
					Beetle obj = (Beetle) grid[row][col];
					obj.starve(grid);
				}
			}
		}
	}
	
	public static void AntBreed(Creature[][] grid, int breedTurn) {
		for(int col = 0; col < 10; col++) {
			for(int row = 0; row < 10; row++) {
				if(grid[row][col] instanceof Ant) {
					Ant obj = (Ant) grid[row][col];
					if(!obj.getHasBred()) {  //if object has already bred
						obj.breed(grid, breedTurn);
						obj.setHasBred(true); //set flag
					}
				}
			}
		}
		
		for(int col = 0; col < 10; col++) {
			for(int row = 0; row < 10; row++) {
				if(grid[row][col] instanceof Ant) {
					Ant objAnt = (Ant) grid[row][col];
					objAnt.setHasBred(false); //reset flag
				}
			}
		}
	}
	
	public static void BeetleBreed(Creature[][] grid, int breedTurn) {
		for(int col = 0; col < 10; col++) {
			for(int row = 0; row < 10; row++) {
				if(grid[row][col] instanceof Beetle) {
					Beetle obj = (Beetle) grid[row][col];
					if(!obj.getHasBred()) { //if object has already bred
						obj.breed(grid, breedTurn);
						obj.setHasBred(true); //set flag
					}
				}
			}
		}
		
		for(int col = 0; col < 10; col++) {
			for(int row = 0; row < 10; row++) {
				if(grid[row][col] instanceof Beetle) {
					Beetle obj = (Beetle) grid[row][col];
					obj.setHasBred(false);  //reset flag
				}
			}
		}
	}
	
	public static Creature[][] fillGrid(String inputFile, char ant, char beetle) throws Exception {
		Creature[][] grid = new Creature[10][10];
		File input = new File(inputFile);
		Scanner scan = new Scanner(input);
		String line = null;

		//get one line of 10 characters in a string and loop through that
		for(int row = 0; row < 10; row++) {
			//get one line and loop through character by character
			line = scan.nextLine();
			
			for(int col = 0; col < 10; col++) {
				//if char is space initalize to null
				if(line.charAt(col) == ' ')
					grid[row][col] = null;
				
				//if char is a initalize ant obj with x and y values
				else if(line.charAt(col) == 'a') {
					grid[row][col] = new Ant(row, col, false, false);
				}
				
				//if char is B initialize beetle obj with x and y values
				else if(line.charAt(col) == 'B') {
					grid[row][col] = new Beetle(0, row, col, true, false, false);
				}
			}
		}
		
		//close scanner
		scan.close();
		return grid;
	}
	
	
	public static void print(int numTurns, Creature[][] grid, char ant, char beetle) {
		
		System.out.println("TURN " + numTurns);
		for(int i = 0; i < 10; i++) {
			//System.out.print("\n");
			for(int j = 0; j < 10; j++) {
				//if beetle is found print B
				if(grid[i][j] instanceof Beetle)
					System.out.print(beetle);
				
				//if ant is found print a
				else if(grid[i][j] instanceof Ant)
					System.out.print(ant);
				
				//if null is found print a space
				else {
					System.out.print(' ');
				}
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}