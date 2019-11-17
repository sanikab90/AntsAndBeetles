
public class Beetle extends Creature {
	private int starveTurns;
	private int beetleRow;
	private int beetleCol;
	private boolean isAlive;
	private boolean hasMoved;
	private boolean hasBred;

	//default constructor
	public Beetle() {
		starveTurns = 0;
		beetleRow = 0;
		beetleCol = 0;
		isAlive = false;
		hasMoved = false;
		hasBred = false;
	}
	
	//other constructor 4got the name
	public Beetle(int s, int xPos, int yPos, boolean alive, boolean move, boolean bred) {
		starveTurns = s;
		beetleRow = xPos;
		beetleCol = yPos;
		isAlive = alive;
		hasMoved = move;
		hasBred = bred;
	}
	
	//accessors
	public int getStarveTurns() {
		return starveTurns;
	}
	
	public int getXPos() {
		return beetleRow;
	}
	
	public int getYPos() {
		
		return beetleCol;
	}
	
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	public boolean getHasBred() {
		return hasBred;
	}
	
	//mutators
	public void setStarveTurns(int turns) {
		this.starveTurns = turns;
	}
	
	public void setbeetleRow(int xPos) {
		this.beetleRow = xPos;
	}
	
	public void setbeetleCol(int yPos) {
		this.beetleCol = yPos;
	}
	
	public void setHasMoved(boolean move) {
		this.hasMoved = move;
	}
	
	public void setHasBred(boolean bred) {
		this.hasBred = bred;
	}
	
	//methods
	public void move(Creature[][] arr) {
		int nearestAnt[] = new int[4];
		int antRow[] = new int[4];
		int antCol[] = new int[4];
		boolean isAnt[] = new boolean[4];
		boolean isBeetle[] = new boolean[4];
		
		int shortestDistance;
		int tempRow = this.beetleRow-1;
		int tempCol = this.beetleCol;
		
			//while wall isn't hit look north
			while(tempRow >= 0) {
				//look north and if it has an ant break
				if(arr[tempRow][tempCol] instanceof Ant) {
					isAnt[0] = true; //set isAnt to true
					//get the row and col for the ant position
					antRow[0] = tempRow;
					antCol[0] = tempCol;
					break;
				}
				else if(this.beetleRow >= 0 && arr[this.beetleRow-1][this.beetleCol] instanceof Beetle) {
					//check if this.beetle is right under it then set isBeetle to true
					isBeetle[0] = true;
					nearestAnt[0]++;
					break;
				}
				else {
					//otherwise keep incrementing everything
					tempRow--;
					nearestAnt[0]++;
				}
			}
			
			//reset temp counters
			tempRow = this.beetleRow;
			tempCol = this.beetleCol+1;
			
			//while wall isn't hit look east
			while(tempCol < 10) {
				if(arr[tempRow][tempCol] instanceof Ant) {
					isAnt[1] = true;
					//get the row and col for the ant position
					antRow[1] = tempRow;
					antCol[1] = tempCol;
					break;
				}
				else if(this.beetleCol < 10 && arr[this.beetleRow][this.beetleCol+1] instanceof Beetle) {
					//set isBeetle to true if there is a beetle right next to this.beetle
					isBeetle[1] = true;
					nearestAnt[1]++;
					break;
				}
				else {
					//otherwise increment counters
					tempCol++;
					nearestAnt[1]++;
				}
			}
			
			//reset temp counters
			tempRow = this.beetleRow+1;
			tempCol = this.beetleCol;
			
			//while wall isn't hit look south
			while(tempRow < 10) {
				if(arr[tempRow][tempCol] instanceof Ant) {
					isAnt[2] = true;
					
					//get the row and col for the ant position
					antRow[2] = tempRow;
					antCol[2] = tempCol;
					break;
				}
				else if(this.beetleRow+1 < 10 && arr[this.beetleRow+1][this.beetleCol] instanceof Beetle) {
					//set isBeetle to true
					isBeetle[2] = true;
					nearestAnt[2]++;
					break;
				}
				else {
					tempRow++;
					nearestAnt[2]++;
				}
			}
			
			//reset temp counters
			tempRow = this.beetleRow;
			tempCol = this.beetleCol-1;
			
			//while wall isn't hit look west
			while(tempCol >= 0) {
				if(arr[tempRow][tempCol] instanceof Ant) {
					isAnt[3] = true;
					
					//get the row and col for ant
					antRow[3] = tempRow;
					antCol[3] = tempCol;
					break;
				}
				else if(this.beetleCol-1 >= 0 && arr[this.beetleRow][this.beetleCol-1 ] instanceof Beetle) {
					//set is Beetle to true
					isBeetle[3] = true;
					nearestAnt[3]++;
					break;
				}
				else {
					tempCol--;
					nearestAnt[3]++;
				}
			}
			
			//if there are NO ANTS orthagonal to the beetle
			if(!isAnt[0] && !isAnt[1] && !isAnt[2] && !isAnt[3]) {
				//move to the farthest edge
				
				int largestDist;
				largestDist = -999;
				for(int i = 0; i < nearestAnt.length; i++) {
					if(nearestAnt[i] > largestDist && !(isBeetle[i]))
						largestDist = nearestAnt[i];
				}
				
				//move  if largestDist == nearestant and is beetle is true
				if(largestDist == nearestAnt[0] && !(isBeetle[0])) {
					//move north
					moveInDirection(arr, 'n', isBeetle);
				}
				else if(largestDist == nearestAnt[1] && !(isBeetle[1])) {
					//move east
					moveInDirection(arr, 'e', isBeetle);
				}
				else if(largestDist == nearestAnt[2] && !(isBeetle[2])) {
					//move south
					moveInDirection(arr, 's', isBeetle);
				}
				else if(largestDist == nearestAnt[3] && !(isBeetle[3])) {
					//move west
					moveInDirection(arr, 'w', isBeetle);
				}
			}
			
			//get the shortest distance from beetle to ant 
			shortestDistance = 999;
			for(int i = 0; i < nearestAnt.length; i++) {
				if(nearestAnt[i] < shortestDistance && !(isBeetle[i]) && isAnt[i])
					shortestDistance = nearestAnt[i];
			}
			
			//check for multiple shortestDistance
			int numShortDist = 0;
			for(int i = 0; i < nearestAnt.length; i++) {
				if(shortestDistance == nearestAnt[i] && !(isBeetle[i]) && isAnt[i]) {
					numShortDist++;
				}
			}
			
			//if theres only 1 ant orthagonal to beetle
			if(numShortDist == 1) {
				if(shortestDistance == nearestAnt[0] && !isBeetle[0] && isAnt[0]) { //if ant is only north move north
					moveInDirection(arr, 'n', isBeetle);
				}
				else if(shortestDistance == nearestAnt[1] && !isBeetle[1] && isAnt[1]) { //if ant is only east move east
					moveInDirection(arr, 'e', isBeetle);
				}
				else if(shortestDistance == nearestAnt[2] && !isBeetle[2] && isAnt[2]) { //if ant is only south move south
					moveInDirection(arr, 's', isBeetle);
				}
				else if(shortestDistance == nearestAnt[3] && !isBeetle[3] && isAnt[3]) { //if ant is only west move west
					moveInDirection(arr, 'w', isBeetle);
				}
			}
			
			//if there is more than 1 ants around beetle
			if(numShortDist >= 2) {
				int antCount[] = new int[4];
				//calculate how many adjacent neighbors ant has
				if(isAnt[0] && !isBeetle[0])
					antCount[0] = antNeighbors(arr, 0, antRow, antCol);
				if(isAnt[1] && !isBeetle[1])
					antCount[1] = antNeighbors(arr, 1, antRow, antCol);
				if(isAnt[2] && !isBeetle[2])
					antCount[2] = antNeighbors(arr, 2, antRow, antCol);
				if(isAnt[3] && !isBeetle[3])
					antCount[3] = antNeighbors(arr, 3, antRow, antCol);
				
				//find the largest number of ants
				int mostAnt = -999;
				for(int i = 0; i < antCount.length; i++) {
					if(antCount[i] > mostAnt && nearestAnt[i] == shortestDistance)
						mostAnt = antCount[i];
				}
				
				//move beetle
				if(mostAnt == antCount[0] && !(isBeetle[0])) {
					//move north
					moveInDirection(arr, 'n', isBeetle);
				}
				else if(mostAnt == antCount[1] && !(isBeetle[1])) {
					//move east
					moveInDirection(arr, 'e', isBeetle);
					
				}
				else if(mostAnt == antCount[2] && !(isBeetle[2])) {
					//move south
					moveInDirection(arr, 's', isBeetle);
				}
				else if(mostAnt == antCount[3] && !(isBeetle[3])) {
					//move west
					moveInDirection(arr, 'w', isBeetle);
				}
			}
			
			
	}
	
	public int antNeighbors(Creature[][] arr, int i, int row[], int col[]) {
		int antCount = 0;
		//look north and if theres an ant increment count
		if((row[i]-1 >= 0) && arr[row[i]-1][col[i]] instanceof Ant)
			antCount++;
		
		//look north east adn if thers an ant increment count
		if((row[i]-1 >= 0) && (col[i]+1 < 10) && arr[row[i]-1][col[i]+1] instanceof Ant)
			antCount++;
		
		//look east and if theres an ant increment count
		if((col[i]+1 < 10) && (arr[row[i]][col[i]+1] instanceof Ant))
			antCount++;
		
		//look southeast and if theres an ant increment count
		if((row[i]+1 < 10) && (col[i]+1 < 10) && (arr[row[i]+1][col[i]+1] instanceof Ant))
			antCount++;
		
		//look south and if theres an ant increment count
		if((row[i]+1 < 10) && (arr[row[i]+1][col[i]]instanceof Ant))
			antCount++;
		
		//look southwest and if theres an ant increment count
		if((row[i]+1 < 10) && (col[i]-1 >= 10) && (arr[row[i]+1][col[i]-1] instanceof Ant))
			antCount++;
		
		//look west and if theres an ant incrment count
		if((col[i]-1 >= 0) && (arr[row[i]][col[i]-1] instanceof Ant))
			antCount++;
		
		//look northwest and if theres an ant increment count
		if((row[i]-1 >= 0) && (col[i]-1 >= 10) && (arr[row[i]-1][col[i]-1] instanceof Ant))
			antCount++;
		
		return antCount;
	}
	
	public void moveInDirection(Creature[][] arr, char direction, boolean isBeetle[]) {
		if(direction == 'n') {
			//check if u can move
			if((this.beetleRow-1 >= 0) && !isBeetle[0]) {
				//set initial position to null
				arr[this.beetleRow][this.beetleCol] = null;
				
				//check if theres an ant above
				if(arr[this.beetleRow-1][this.beetleCol] instanceof Ant) {
					arr[this.beetleRow-1][this.beetleCol] = null;
					this.starveTurns = 0;
				} else {this.starveTurns++;}
				
				//move north
				arr[this.beetleRow-1][this.beetleCol] = this;
				
				//change row
				this.beetleRow = this.beetleRow-1;
			}
		}
		
		else if(direction == 'e') {
			//check if u can move
			if((this.beetleCol+1 < 10) && !isBeetle[1]) {
				//set initial position to null
				arr[this.beetleRow][this.beetleCol] = null;
				
				//check if theres an ant east
				if(arr[this.beetleRow][this.beetleCol+1] instanceof Ant) {
					arr[this.beetleRow][this.beetleCol+1] = null;
					this.starveTurns = 0;
				} else {this.starveTurns++;}
				
				//move east
				arr[this.beetleRow][this.beetleCol+1] = this;
				
				//change col
				this.beetleCol = this.beetleCol+1;
			}
		}
		
		else if(direction == 's') {
			//check if u can move
			if((this.beetleRow+1 < 10) && !isBeetle[2]) {
				//set initial position to null
				arr[this.beetleRow][this.beetleCol] = null;
				
				//check if theres an ant below
				if(arr[this.beetleRow+1][this.beetleCol] instanceof Ant) {
					arr[this.beetleRow+1][this.beetleCol] = null;
					this.starveTurns = 0;
				} else {this.starveTurns++;}
				
				//move south
				arr[this.beetleRow+1][this.beetleCol] = this;
				
				//change row
				this.beetleRow = this.beetleRow+1;
			}
		}
		
		else if(direction == 'w') {
			//check if u can move
			if((this.beetleCol-1 >= 0) && !isBeetle[3]) {
				//set initial position to null
				arr[this.beetleRow][this.beetleCol] = null;
				
				//check if theres an ant west
				if(arr[this.beetleRow][this.beetleCol-1] instanceof Ant) {
					arr[this.beetleRow][this.beetleCol-1] = null;
					this.starveTurns = 0;
				} else {this.starveTurns++;}
				
				//move west
				arr[this.beetleRow][this.beetleCol-1] = this;
				
				//change col
				this.beetleCol = this.beetleCol-1;
			}
		}
	}
	
	public void breed(Creature[][] arr, int breedTurn) {
		//this.breedTurns++;
		if(breedTurn % 8 == 0 && this.isAlive && breedTurn != 0) {
			//look north and see if u can breed
			if(this.beetleRow-1 >= 0 && arr[this.beetleRow-1][this.beetleCol] == null)
				arr[this.beetleRow-1][this.beetleCol] = new Beetle(0, this.beetleRow-1, this.beetleCol, true, false, true);
			
			//look east
			else if(this.beetleCol+1 < 10 && arr[this.beetleRow][this.beetleCol+1] == null)
				arr[this.beetleRow][this.beetleCol+1] = new Beetle(0, this.beetleRow, this.beetleCol+1, true, false, true);
			
			//look south
			else if(this.beetleRow+1 < 10 && arr[this.beetleRow+1][this.beetleCol] == null)
				arr[this.beetleRow+1][this.beetleCol] = new Beetle(0, this.beetleRow+1, this.beetleCol, true, false, true);
			
			//look west
			else if(this.beetleCol-1 >= 0 && arr[this.beetleRow][this.beetleCol-1] == null)
				arr[this.beetleRow][this.beetleCol-1] = new Beetle(0, this.beetleRow, this.beetleCol-1, true, false, true);
		}
	}
	
	public void starve(Creature[][] arr) {
		this.starveTurns++;
		if(this.starveTurns % 5 == 0) {
			arr[beetleRow][beetleCol] = null;
			this.isAlive = false;
		}
		
	}
}
