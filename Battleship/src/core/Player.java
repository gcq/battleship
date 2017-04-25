package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
	
	Board board;
	List<Ship> ships;
	Map<Integer, Ship> shipIds;
	
	public Player(Board board) {
		this.board = board;
		
		for (int y = 0; y < board.h; y++)
			for (int x = 0; x < board.w; x++)
				board.set(x, y, 0);
		
		ships = new ArrayList<>();
		shipIds = new HashMap<>();
	}
	
	public void addShip(Ship ship) {
		ships.add(ship);
		shipIds.put(getShipId(ship), ship);
	}
	
	public int getShipId(Ship ship) {
		return ships.indexOf(ship) + 1;
	}
	
	public void resetBoard() {
		for (Ship s : ships) {
			int x = s.getX();
			int y = s.getY();
			
			for (int i = 0; i < s.getLength(); i++) {
				board.set(x, y, getShipId(s));
				
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
	}
	
	public boolean hit(int x, int y) {
		int shipid = board.get(x, y);
		boolean isHit = shipid > 0;
		
		if (isHit) {
			board.set(x, y, -1);
			shipIds.get(shipid).hit();
		}
		
		return isHit;
	}
	
	public boolean isDead() {
		for (Ship s : ships) {
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
