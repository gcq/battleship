package core;

import utils.Enums.Direction;

//enum Direction {
//	VERTICAL,
//	HORIZONTAL
//}

public class Ship {

	int x, y;
	int length;
	
	Direction direction;
	
	int hits;
	
	public Ship(int x, int y, int length, Direction direction) {
		super();
		this.x = x;
		this.y = y;
		this.length = length;
		this.direction = direction;
		
		hits = 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public int getHits() {
		return hits;
	}
	
	public void hit() {
		hits++;
	}
	
	public boolean isSunk() {
		return hits >= length;
	}
	
}
