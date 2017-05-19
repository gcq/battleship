package core;

import java.util.HashMap;
import java.util.Map;

import utils.Enums.Direction;
import utils.Enums.HitType;
import core.exceptions.InvalidShipPlacementException;

public class Player {
	
	String name;
	Board board;
	Map<Integer, Ship> ships;
	
	public Player () {
		
	}
	
	public Player(Board board) {
		this.board = board;
		
		for (int y = 0; y < this.board.h; y++)
			for (int x = 0; x < this.board.w; x++)
				this.board.set(x, y, 0);
		
		ships = new HashMap<>();
	}
	
	public void addShip(Ship ship) throws InvalidShipPlacementException {
		ship.setId(getNextShipId());
		ships.put(ship.getId(), ship);
		placeShip(ship);
	}
	
	public int getNextShipId() {
		return ships.size() + 1;
	}
	
	public boolean checkShipPlacement(Ship s) {
		if (s.getDirection() == Direction.HORIZONTAL) {
			if (s.getX() + s.getLength() > 10)
				return false;
			
			for (int x = s.getX(); x < s.getX() + s.getLength(); x++) {
				if (board.get(x, s.getY()) != 0)
					return false;
			}
		
		} else if (s.getDirection() == Direction.VERTICAL) {
			if (s.getY() + s.getLength() > 10)
				return false;
			
			for (int y = s.getY(); y < s.getY() + s.getLength(); y++) {
				if (board.get(s.getX(), y) != 0)
					return false;
			}
		}
		
		return true;
	}
	
	private void placeShip(Ship s) throws InvalidShipPlacementException {
		
		if (!checkShipPlacement(s))
			throw new InvalidShipPlacementException();
		
		int x = s.getX();
		int y = s.getY();
		
		for (int i = 0; i < s.getLength(); i++) {
			board.set(x, y, s.getId());
			
			switch (s.getDirection()) {
				case HORIZONTAL:
					x++;
					break;

				case VERTICAL:
					y++;
					break;
			}
		}
	}
	
	public void resetBoard() throws InvalidShipPlacementException {
		for (Ship s : ships.values()) {
			placeShip(s);
		}
	}
	
	public HitType hit(int x, int y) {
		int shipid = board.get(x, y);
		
		boolean isHit = shipid > 0;
		
		if (isHit) {
			board.set(x, y, -1);
			
			if (ships.get(shipid).hit())
				return HitType.SUNK;
		}
		
		return (isHit) ? HitType.HIT : HitType.WATER;
	}
	
	public boolean isDead() {
		for (Ship s : ships.values()) {
			if (!s.isSunk())
				return false;
		}
		
		return true;
	}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return board.toString();
	}
}
