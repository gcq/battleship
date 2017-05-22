package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ui.interfaces.ServerMoveListener;
import utils.Constants;


public class Client {
	private Socket echoSocket;
	private PrintWriter out;
	private BufferedReader in;
	private BufferedReader stdIn;
	ClientRunnable clientRunnable;
	
	private boolean conected; 
	
	public Client() {
		clientRunnable = new ClientRunnable(this);
	}
	
	public void open() {
		
		try {
			echoSocket = new Socket(Constants.serverIp, Constants.serverPort); //Socket that we use to connect with the server
			out = new PrintWriter(echoSocket.getOutputStream(), true); //Output Stream that we get from the socket to send data to the server
			
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); //Input Stream that we get from the socket to receive data from the server
				
			stdIn = new BufferedReader(new InputStreamReader(System.in)); //Input Stream that comes from  the console
			
			(new Thread(clientRunnable))
			.start();
			
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
	
	public String sendMove(int x, int y) {
		String move = String.valueOf(x) + "," + String.valueOf(y);
		out.println(move); //We send the user input to the server through the output stream we get from the socket
//		try {
//			return in.readLine(); //We receive the message returned from the server through Input stream we get from the socket
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
		return move;
	}

	public boolean isConected() {
		return conected;
	}

	public void setConected(boolean conected) {
		this.conected = conected;
	}

	public PrintWriter getOut() {
		return out;
	}

	public BufferedReader getIn() {
		return in;
	}
	
	public void setServerMoveListener (ServerMoveListener l) {
		clientRunnable.setServerMoveListener(l);
	}

}
