package core;

import utils.Enums.Direction;


public class Ship {

	int x, y, id;
	int length;
	
	Direction direction;
	
	int hits;
	
	public Ship(int x, int y, int length, Direction direction) {
		this(x, y, length, direction, -100);
	}

	public Ship(int x, int y, int length, Direction direction, int id) {
		super();
		this.x = x;
		this.y = y;
		this.length = length;
		this.direction = direction;
		this.id = id;
		
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
	
	public boolean hit() {
		return ++hits >= getLength();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/* FI GETTERS & SETTERS */
	
	public boolean isSunk() {
		return hits >= length;
	}
	
}
