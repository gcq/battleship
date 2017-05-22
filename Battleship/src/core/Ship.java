package core;

import java.util.ArrayList;
import java.util.List;

import core.exceptions.InvalidPointsForShipException;
import utils.Enums.Direction;
import utils.Point;


public class Ship {

	int x, y, id;
	int length;
	
	boolean lastShipStanding;
	
	Direction direction;
	
	int hits;
	
	public static Ship getShipFromPoints(Point a, Point b) throws InvalidPointsForShipException {
		if (a.getX() == b.getX()) {
			//VERTICAL
			
			int minY = Math.min(a.getY(), b.getY());
			int maxY = Math.max(a.getY(), b.getY());
			
			return new Ship(a.getX(), minY, maxY - minY + 1, Direction.VERTICAL);
		
		} else if (a.getY() == b.getY()) {
			//HORIZONTAL
			
			int minX = Math.min(a.getX(), b.getX());
			int maxX = Math.max(a.getX(), b.getX());
			
			return new Ship(minX, a.getY(), maxX - minX + 1, Direction.HORIZONTAL);
		
		} else {
			//INVALID
			throw new InvalidPointsForShipException("Points are not in a line");
		}
		
	}
	
	public static Ship fromEncodedString(String s) {
		String[] arr = s.split(",");
		System.out.println("arr from encodedString: " + arr[5]);
		Ship ship = new Ship(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), Direction.valueOf(arr[4]), Integer.parseInt(arr[0]));
		if (Boolean.valueOf(arr[5])) // == true
			ship.setLastShipStanding(true);
		return ship;
	}
	
	public Ship (int length, Direction direction) {
		this(-100, -100, length, direction);
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
	
	
	
	public boolean isLastShipStanding() {
		return lastShipStanding;
	}

	public void setLastShipStanding(boolean lastShipStanding) {
		this.lastShipStanding = lastShipStanding;
	}

	@Override
	public String toString() {
		return "Ship [id=" + id + ", x=" + x + ", y=" + y + ", length="
				+ length + ", direction=" + direction + ", hits=" + hits + "]";
	}
	
	public String toEncodedString() {
		return id + "," + x + "," + y + "," + length + "," + direction;
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
	
	public List<Point> getSegmentsPositions() {
		List<Point> points = new ArrayList<>();
		
		if (direction == Direction.HORIZONTAL)
			for (int _x = x; _x < x + length; _x++)
				points.add(new Point(_x, y));
		
		else if (direction == Direction.VERTICAL)	
			for (int _y = y; _y < y + length; _y++)
				points.add(new Point(x, _y));
		
		return points;
	}
	
}
