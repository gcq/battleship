package core;

import java.util.Scanner;

import core.exceptions.InvalidShipPlacementException;
import utils.Enums.Direction;

public class Test {

	public static void main(String[] args) {
		Player p = new Player(new Board(10, 10));

		try {
			p.addShip(new Ship(0, 0, 3, Direction.VERTICAL));
		} catch (InvalidShipPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			p.addShip(new Ship(1, 1, 3, Direction.HORIZONTAL));
		} catch (InvalidShipPlacementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scanner s  = new Scanner(System.in);
		
		while (!p.isDead()) {
			System.out.println(p);
			
			System.out.print("x,y = ");
			String[] xy = s.nextLine().split(",");
			
			System.out.println("hit: " + p.hit(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
		}
		
		System.out.println("Player dead");
		
		s.close();
	}

}
