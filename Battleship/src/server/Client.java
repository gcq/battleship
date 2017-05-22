package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import core.Ship;
import utils.Constants;
import utils.Enums.HitType;


public class Client {
	private Socket echoSocket;
	private PrintWriter out;
	private BufferedReader in;
	private BufferedReader stdIn;
	
	private boolean conected; 
	
	public void open() {
		
		try {
			echoSocket = new Socket(Constants.serverIp, Constants.serverPort); //Socket that we use to connect with the server
			out = new PrintWriter(echoSocket.getOutputStream(), true); //Output Stream that we get from the socket to send data to the server
			
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); //Input Stream that we get from the socket to receive data from the server
				
			stdIn = new BufferedReader(new InputStreamReader(System.in)); //Input Stream that comes from  the console
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			echoSocket.close();
			out.close();
			in.close();
			stdIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HitType sendMove(int x, int y) {
		out.println(Protocol.createMove(x, y)); //We send the user input to the server through the output stream we get from the socket
		try {
			return Protocol.parseMoveResult(Packet.fromString(in.readLine())); //We receive the message returned from the server through Input stream we get from the socket
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public Ship sendGetLastHitShip() {
		out.println(Protocol.createGetLastHitShip()); //We send the user input to the server through the output stream we get from the socket
		try {
			return Protocol.parseGetLastShipResponse(Packet.fromString(in.readLine())); //We receive the message returned from the server through Input stream we get from the socket
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public boolean isConected() {
		return conected;
	}

	public void setConected(boolean conected) {
		this.conected = conected;
	}
}
