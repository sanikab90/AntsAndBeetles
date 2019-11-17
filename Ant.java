

public class Ant extends Creature {
	private int antRow; // use a creature array
	private int antCol;
	private boolean hasMoved;
	private boolean hasBred;

	// default constructor
	public Ant() {
		antRow = 0;
		antCol = 0;
		hasMoved = false;
		hasBred = false;
	}

	// overloaded constructor
	public Ant(int xPos, int yPos, boolean move, boolean bred) {
		this.antRow = xPos;
		this.antCol = yPos;
		this.hasMoved = move;
		this.hasBred = bred;
	}

	// accessors
	public int getXPos() {
		return antRow;
	}
	
	public int getYPos() {
		return antCol;
	}
	
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	public boolean getHasBred() {
		return hasBred;
	}

	// mutators

	public void setantRow(int xPos) {
		this.antRow = xPos;
	}
	
	public void setantCol(int yPos) {

		this.antCol = yPos;
	}
	
	public void setHasMoved(boolean move) {
		this.hasMoved = move;
	}
	
	public void setHasBred(boolean bred) {
		this.hasBred = bred;
	}

	// methods
	public void move(Creature[][] arr) {
		boolean isBeetle[] = new boolean [4];
		int distanceToBeetle[] = new int [4];
		int smallestDistance, numOfSmallestDistances, largestDistance, numOpenPaths=0;
		int numLargestDistance=0;
		
		
		int row = this.antRow;
		int col = this.antCol;
		
		
		//reset tempRow and tempCol
		int tempRow = row-1;
		int tempCol = col;
		
		//look north
		while(tempRow >= 0) {
			//look north and if it has a beetle break
			if(arr[tempRow][tempCol] instanceof Beetle) {
				isBeetle[0] = true;
				break;
			}
			else {
				//increment beetle counter
				tempRow--;
				distanceToBeetle[0]++;
			}
		}
		
		//reset tempRow and tempCol
		tempRow = row;
		tempCol = col+1;
		
		//look east
		while(tempCol < 10) {
			//look east and if it has a beetle check if theres anything west and move west
			if(arr[tempRow][tempCol] instanceof Beetle) {
				isBeetle[1] = true;
				break;
			}
			else {
				//Increment beetle counter
				tempCol++;
				distanceToBeetle[1]++;
			}
		}
		
		tempRow = row+1;
		tempCol = col;
		
		//look south
		while(tempRow < 10) {
			//if u look down and it has instanceof a beetle check if theres anything up and move up
			if(arr[tempRow][tempCol] instanceof Beetle) {
				isBeetle[2] = true;
				break;
			}
			else {
				//otherwise increment down beetle counter and keep checking for a beetle
				tempRow++;
				distanceToBeetle[2]++;
			}
		}
		
		//reset tempRow and tempCol
		tempRow = row;
		tempCol = col-1;
		
		//look west
		while(tempCol >= 0) {
			//look west and if it has a beetle check if theres anything east and move east 
			if(arr[tempRow][tempCol] instanceof Beetle) {
				isBeetle[3] = true;
				break;
			}
			else {
				//increment left beetle counter
				tempCol--;
				distanceToBeetle[3]++;
			}
		}
		
		//if number of nearest beetles == 0
		if(!isBeetle[0] && !isBeetle[1] && !isBeetle[2] && !isBeetle[3]) {
			//dont' move
			return;
		}
		
		//set intitial smallest value to distanceToBeetle[0]
		smallestDistance = 999;
		for(int i = 0; i < distanceToBeetle.length; i++) {
			if((distanceToBeetle[i] < smallestDistance) && (isBeetle[i]))
				smallestDistance = distanceToBeetle[i];
		}
		
		//loop again to find how many occurrences of smallest number there is
		numOfSmallestDistances = 0;
		for(int i = 0; i < distanceToBeetle.length; i++) {
			if((smallestDistance == distanceToBeetle[i]) && (isBeetle[i])) {
				numOfSmallestDistances++;
			}
		}
		
		//if number of nearest beetles == 1
		if(numOfSmallestDistances == 1) {
			//attempt any movement in opposite direction
			if(smallestDistance == distanceToBeetle[0] && (isBeetle[0]))
				attemptMove(arr, 's');
			
			else if(smallestDistance == distanceToBeetle[1] && (isBeetle[1]))
				attemptMove(arr, 'w');
			
			else if(smallestDistance == distanceToBeetle[2] && (isBeetle[2]))
				attemptMove(arr, 'n');
			
			else if(smallestDistance == distanceToBeetle[3] && (isBeetle[3]))
				attemptMove(arr, 'e');
		}
		
		//if number of beetles > 1
		else if(numOfSmallestDistances > 1) {
			//check in all 4 orthogonal directions
			for(int i = 0; i < 4; i++) {
				if(!isBeetle[i])
					numOpenPaths++;
			}
			
			//if num of open paths == 1
			if(numOpenPaths == 1) {
				//attempt any movement in that direction
				//attempt to move north
				if(isBeetle[0]) {
					attemptMove(arr, 's');
				}
				
				//attempt to move east
				else if(isBeetle[1]) {
					attemptMove(arr, 'w');
				}
				
				//attempt to move south
				else if(isBeetle[2]) {
					attemptMove(arr, 'n');
				}
				
				//attempt to move west
				else if(isBeetle[3]) {
					attemptMove(arr, 'e');
				}
			}
			
			//if num of open paths > 1
			if(numOpenPaths > 1) {
				//use NESW to choose path and attempt movement
				//attempt to move north
				if(!isBeetle[0]) {
					attemptMove(arr, 's');
				}
				
				//attempt to move east
				else if(!isBeetle[1]) {
					attemptMove(arr, 'w');
				}
				
				//attempt to move south
				else if(!isBeetle[2]) {
					attemptMove(arr, 'n');
				}
				
				//attempt to move west
				else if(!isBeetle[3]) {
					attemptMove(arr, 'e');
				}
			}
			
			//if num open paths == 0
			if(numOpenPaths == 0) {
				//attempt ant movement toward beetle farthest away
				
				//find the largest distance from beetle
				largestDistance = -999;
				for(int i = 0; i < distanceToBeetle.length; i++) {
					if((distanceToBeetle[i] > largestDistance) && (isBeetle[i]))
						largestDistance = distanceToBeetle[i];
				}
				
				//find number of largest distace awya beetles
				for(int i = 0; i < distanceToBeetle.length; i++) {
					if(distanceToBeetle[i] == largestDistance)
						numLargestDistance++;
				}
				
				//if num of farthest beetles > 1 use NESW
				if(numLargestDistance > 1) {
					
					if(distanceToBeetle[0] == numLargestDistance && (isBeetle[0])) {
						attemptMove(arr, 's');
					}
					
					else if(distanceToBeetle[1] == numLargestDistance && (isBeetle[1])) {
						attemptMove(arr, 'w');
					}
					
					else if(distanceToBeetle[2] == numLargestDistance && (isBeetle[2])) {
						attemptMove(arr, 'n');
					}
					
					else if(distanceToBeetle[3] == numLargestDistance && (isBeetle[3])) {
						attemptMove(arr, 'e');
					}
				}
			}
		}
	}

	
	public void attemptMove(Creature[][] arr, char direction) {
		if(direction == 'n') {
			//check if u can move
			if((this.antRow-1 >= 0) && arr[this.antRow-1][this.antCol] == null) {
			
				//set initial position to null
				arr[this.antRow][this.antCol] = null;
				//move north
				arr[this.antRow-1][this.antCol] = this;
				
				//change row
				this.antRow = this.antRow-1;
			}
		}
		
		else if(direction == 'e') {
			//check if u can move
			if((this.antCol+1 < 10) && arr[this.antRow][this.antCol+1] == null) {
				//set initial position to null
				arr[this.antRow][this.antCol] = null;
				
				//move east
				arr[this.antRow][this.antCol+1] = this;
				
				//change col
				this.antCol = this.antCol+1;
			}
		}
		
		else if(direction == 's') {
			//check if u can move
			if((this.antRow+1 < 10) && arr[this.antRow+1][this.antCol] == null) {
				//set initial position to null
				arr[this.antRow][this.antCol] = null;
				
				//move south
				arr[this.antRow+1][this.antCol] = this;
				
				//change row
				this.antRow = this.antRow+1;
			}
		}
		
		else if(direction == 'w') {
			//check if u can move
			if((this.antCol-1 >= 0) && arr[this.antRow][this.antCol-1] == null) {
				//set initial position to null
				arr[this.antRow][this.antCol] = null;
				
				//move west
				arr[this.antRow][this.antCol-1] = this;
				
				//change row
				this.antCol = this.antCol-1;
			}
		}
	}
	
	public void breed(Creature[][] arr, int breedTurn) {
		//if 3 turns hasnt passed don't breed
		if((breedTurn % 3 == 0) && (breedTurn != 0)){
			//look around north and clockwise
			
			//if you check north and if its null and in bounds
			if((this.antRow-1 >= 0) && (arr[this.antRow-1][this.antCol] == null))
				//put new ant character on row-1
				arr[this.antRow-1][this.antCol] = new Ant((this.antRow-1), this.antCol, false, true);
			
			//if north is full look east
			else if((this.antCol+1 < 10) && (arr[this.antRow][this.antCol+1] == null)) {
				//put new ant character on col+1
				arr[this.antRow][this.antCol+1] = new Ant(this.antRow, (this.antCol+1), false, true);
			}
			
			//if north and east is full look south
			else if((this.antRow+1 < 10) && (arr[this.antRow+1][this.antCol] == null)) {
				//put new ant character on col+1
				arr[this.antRow+1][this.antCol] = new Ant((this.antRow+1), this.antCol, false, true);
			}
			
			//if north east and south is full look west
			else if((this.antCol-1 >= 0) && (arr[this.antRow][this.antCol-1] == null)) {
				//put new ant character on row-1
				arr[this.antRow][this.antCol-1] = new Ant(this.antRow, this.antCol-1, false, true);
			}
			//if all orthagonal directions are full no breeding and exit the function
				
		} else {return;}
	}
}
