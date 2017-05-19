package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {

	public static void main(String[] args) throws IOException {
		
//		String serverIpString = args[0];	//ip address of the server
//
//		int serverPort = Integer.valueOf(args[1]);	//port of the server
		//both needed in unicast tcp app
		
		String serverIpString = "localhost";
		int serverPort = 4321;
		
		try (
			Socket echoSocket = new Socket(serverIpString, serverPort); //Socket that we use to connect with the server
			
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true); //Output Stream that we get from the socket to send data to the server
			
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); //Input Stream that we get from the socket to receive data from the server
				
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); //Input Stream that comes from  the console
		) {
			String userInput;
			System.out.println("Write your move: ");
			while ((userInput = stdIn.readLine()) != null) { //We read from the console while user sends STOP signal
				out.println(userInput); //We send the user input to the server through the output stream we get from the socket
				System.out.println("Received message: " + in.readLine()); //We receive the message returned from the server through Input stream we get from the socket
				if (userInput.equals("STOP")) {
					return;
				}
			}
		}
	}
	
}
