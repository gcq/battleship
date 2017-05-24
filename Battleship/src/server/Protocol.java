package server;

import core.Ship;
import server.Packet.PacketType;
import utils.Point;
import utils.Enums.HitType;


public class Protocol {
	public static Packet createMove(int x, int y) {
		return new Packet(PacketType.MOVEMENT, x + "," + y);
	}
	
	public static Point parseMove(Packet p) {
		String[] arr = p.getContents().split(",");
		
		int x = Integer.parseInt(arr[0]);
		int y = Integer.parseInt(arr[1]);
		
		return new Point(x, y);
	}

	public static Packet createMoveResult(Point p, HitType hitType) {
		return new Packet(PacketType.MOVEMENT_RESULT, p.getX() + "," + p.getY() + "," + hitType.toString());
	}
	
	public static MoveResult parseMoveResult(Packet p) {
		String[] arr = p.getContents().split(",");
		
		int x = Integer.parseInt(arr[0]);
		int y = Integer.parseInt(arr[1]);
		
		HitType hitType = HitType.valueOf(arr[2]);
		
		return new MoveResult(hitType, new Point(x, y));
	}
	
	public static Packet createGetLastHitShip() {
		return new Packet(PacketType.GET_LAST_HIT_SHIP, "AIXONOFARES");
	}
	
	public static void parseGetLastHitShip(Packet p) {
		return;
	}
	
	public static Packet createGetLastShipResponse(Ship s) {
		return new Packet(PacketType.GET_LAST_HIT_SHIP_RESULT, s.toEncodedString());
	}
	
	public static Ship parseGetLastShipResponse(Packet p) {
		return Ship.fromEncodedString(p.getContents() + "," + p.isWinGame());
	}
}
