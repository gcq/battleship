package core;

import java.util.Scanner;

import utils.Enums.Direction;

public class Test {

	public static void main(String[] args) {
		Player p = new Player(new Board(10, 10));

		p.addShip(new Ship(0, 0, 1, Direction.VERTICAL));
		p.addShip(new Ship(2, 1, 1, Direction.HORIZONTAL));
		
		p.resetBoard();
		
		Scanner s  = new Scanner(System.in);
		
		while (!p.isDead()) {
			System.out.println(p);
			
			String[] xy = s.nextLine().split(",");
			
			System.out.println("hit: " + p.hit(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
		}
		
		System.out.println("Player dead");
		
		s.close();
	}

}
