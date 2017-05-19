package core;

import java.awt.Point;

import core.exceptions.InvalidPointsForShipException;
import utils.Enums.Direction;


public class Ship {

	int x, y, id;
	int length;
	
	Direction direction;
	
	int hits;
	
	public Ship (int id, int length, Direction direction) {
		this.id = id;
		this.length = length;
		this.direction = direction;
	}
	
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
	
	public static Ship getShipFromPoints(Point a, Point b) throws InvalidPointsForShipException {
		if (a.getX() == b.getX()) {
			//VERTICAL
			
			int minY = (int) Math.min(a.getY(), b.getY());
			int maxY = (int) Math.max(a.getY(), b.getY());
			
			return new Ship((int) a.getX(), minY, maxY - minY + 1, Direction.VERTICAL);
		
		} else if (a.getY() == b.getY()) {
			//HORIZONTAL
			
			int minX = (int) Math.min(a.getX(), b.getX());
			int maxX = (int) Math.max(a.getX(), b.getX());
			
			return new Ship(minX, (int) a.getY(), maxX - minX + 1, Direction.HORIZONTAL);
		
		} else {
			//INVALID
			throw new InvalidPointsForShipException("Points are not in a line");
		}
		
	}
	
	@Override
	public String toString() {
		return "Ship [id=" + id + ", x=" + x + ", y=" + y + ", length="
				+ length + ", direction=" + direction + ", hits=" + hits + "]";
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
