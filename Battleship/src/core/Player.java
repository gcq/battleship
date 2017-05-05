package core;

import java.util.HashMap;
import java.util.Map;

import utils.Enums.HitType;
import core.exceptions.InvalidShipPlacementException;

public class Player {
	
	Board board;
	Map<Integer, Ship> ships;
	
	public Player(Board board) {
		this.board = board;
		
		for (int y = 0; y < board.h; y++)
			for (int x = 0; x < board.w; x++)
				board.set(x, y, 0);
		
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
		int x = s.getX();
		int y = s.getY();
		
		for (int i = 0; i < s.getLength(); i++)
			if (board.get(x, y) != 0)
				return false;
		
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
	
	@Override
	public String toString() {
		return board.toString();
	}
}
