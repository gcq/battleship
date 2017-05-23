package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Enums.Direction;
import utils.Enums.HitType;
import utils.Point;
import core.exceptions.InvalidShipPlacementException;

public class Player {
	
	String name;
	Board board;
	Map<Integer, Ship> ships;
	
	Ship lastHitShip;
	
	public Player () {
		
	}
	
	public Player(Board board) {
		this.board = board;
		
		for (int y = 0; y < this.board.h; y++)
			for (int x = 0; x < this.board.w; x++)
				this.board.set(x, y, 0);
		
		ships = new HashMap<>();
	}
	
	public List<Ship> getShips () {
		List<Ship> ships = new ArrayList<Ship>();
		for (Ship ship : this.ships.values()) {
			ships.add(ship);
		}
		return ships;
	}
	
	public void addShip(Ship ship) throws InvalidShipPlacementException {
		if (!checkShipPlacement(ship))
			throw new InvalidShipPlacementException();
		
		ship.setId(getNextShipId());
		ships.put(ship.getId(), ship);
		placeShip(ship);
	}
	
	public int getNextShipId() {
		return ships.size() + 1;
	}
	
	public Ship getLastHitShip() {
		return lastHitShip;
	}
	
	public boolean checkShipPlacement(Ship s) {
		int startAxis = -100;
		
		if (s.getDirection() == Direction.HORIZONTAL)
			startAxis = s.getX();
		else if (s.getDirection() == Direction.VERTICAL)
			startAxis = s.getY();
		
		if (startAxis + s.getLength() > 10)
			return false;
		
		for (Point p : s.getSegmentsPositions())
			if (board.get(p.getX(), p.getY()) != 0)
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
			lastHitShip = ships.get(shipid);
			
			board.set(x, y, -1);
			
			if (lastHitShip.hit())
				return HitType.SUNK;
			
			return HitType.HIT;
		}
		
		return HitType.WATER;
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
