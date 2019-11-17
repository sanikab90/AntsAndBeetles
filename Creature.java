
abstract class Creature {
	//methods
	abstract void move(Creature[][] arr);
	abstract void breed(Creature[][] arr, int breedTurn);
	abstract int getXPos();
	abstract int getYPos();
}