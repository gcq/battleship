package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import utils.Point;
import utils.Enums.Direction;
import utils.Enums.HitType;
import core.Board;
import core.Player;
import core.Ship;
import core.exceptions.InvalidShipPlacementException;
import server.Packet.PacketType;


public class Server {
	static int port = 4321;
	
	Player player;
	
	public Server() {
	}
	
	public static void main(String[] args) {
		System.out.println("Server running...");
		
		Player player = createPlayer();
		System.out.println(player);
		
		// We instantiate the ServerSocket, giving the port we are going to use.
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			
			// We are always running the server, so we create the endless iteration
			while (true) {
				// We listen for a connection until somebody gets connected.
				Socket clientSocket = serverSocket.accept();

				// We run the runnable class we created as a Thread, so as to
				// listen multiple clients at same time.
				(new Thread(new ServerRunnable(clientSocket, player)))
						.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Player createPlayer () {
		Board board = new Board(10, 10);
		Player player = new Player(board);
		
		Direction direction = Direction.HORIZONTAL;
		int ships = 5;
		Random rand = new Random();
		int x;
		int y;
		
		int i = 0;
		while (i < ships) {
			x = rand.nextInt(9);
			y = rand.nextInt(9);
			 
			direction = direction == Direction.VERTICAL ? Direction.HORIZONTAL : Direction.VERTICAL;
			int length = i+1;
			
			if (i == 0)
				length = 2;
			if (i == 1 || i == 2)
				length = 3;
			
			try {
				player.addShip(new Ship(x, y, length, direction));
				i++;
				
			} catch (InvalidShipPlacementException e) {
				e.printStackTrace();
			}
		}
		
		return player;
		
	}
	
}

class ServerRunnable implements Runnable {
	
	Socket clientSocket;
	Player player;
	
	public ServerRunnable(Socket clientSocket, Player player) {
		this.player = player;
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		try (
			// We instantiate the PrintWriter variable to send a message to the
			// client.
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	
			// We instantiate the BufferedReader variable to read the
			// client's message.
			BufferedReader in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));) {
			
			String inputLine;

			// In this while iteration, we get the String message, print it for
			// screen and we transform it following our format protocol
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Received message: " + inputLine); // Rebem la gridX i gridY
				
				Packet packet = Packet.fromString(inputLine);
				
				if (packet.type == PacketType.MOVEMENT) {
					Point hitPoint = Protocol.parseMove(packet);
					packet = Protocol.createMoveResult(hitPoint, player.hit(hitPoint.getX(), hitPoint.getY())); // decidir que es aquesta jugada; aigua, tocado, hundido
				}
				
				else if (packet.type == PacketType.GET_LAST_HIT_SHIP) {
					packet = Protocol.createGetLastShipResponse(player.getLastHitShip());
					

					if (player.isDead()) {
						packet.setWinGame(true);
					}
				}
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//We send the modified data to the client with the PrintWriter variable.
				out.println(packet);
				System.out.println("Message sent: " + packet);
				
				Random rand = new Random();
				Packet randomMove = Protocol.createMove(rand.nextInt(9), rand.nextInt(9));
				out.println(randomMove);
				System.out.println("Message sent: " + randomMove);
				
				System.out.println(player);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getHitType (int x, int y) {
		HitType hitType = player.hit(x, y);
		
		if (hitType == HitType.HIT)
			return "hit";
		else if (hitType == HitType.SUNK)
			return "sunk";	
		return "water";
	}
}
