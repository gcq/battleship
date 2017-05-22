package server;

import java.util.Arrays;

public class Packet {
	public static enum PacketType {
		MOVEMENT,
		MOVEMENT_RESULT,
		GET_LAST_HIT_SHIP,
		GET_LAST_HIT_SHIP_RESULT
	}
	
	PacketType type;
	String contents;
	boolean winGame;
	
	public Packet() {
	}
	
	public Packet(PacketType type, String contents) {
		this.type = type;
		this.contents = contents;
	}
	
	public static Packet fromString(String p) {
		String[] arr = p.split("\\|");
		System.out.println("arr: " + Arrays.toString(arr));
		Packet packet = new Packet(PacketType.valueOf(arr[0]), arr[1]);
		packet.setWinGame(Boolean.valueOf(arr[2]));
		return packet;
	}

	public boolean isWinGame() {
		return winGame;
	}

	public void setWinGame(boolean winGame) {
		this.winGame = winGame;
	}

	public PacketType getType() {
		return type;
	}

	public void setType(PacketType type) {
		this.type = type;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return type + "|" + contents + "|" + winGame;
	}
}
