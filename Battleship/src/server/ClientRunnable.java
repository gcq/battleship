package server;

import java.io.IOException;

import ui.interfaces.ServerMoveListener;
import ui.interfaces.ServerMovePublisher;

public class ClientRunnable implements Runnable, ServerMovePublisher{

	Client client;
	ServerMoveListener serverMoveListener = new ServerMoveListener() {
		
		@Override
		public void onServerMoveListener(String move) {
			System.out.println("Listener per defecte. Crida setServerMoveListener. (" + move + ")");
		}
	};
	
	public ClientRunnable(Client client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		String inputLine;
		try {
			while (true) {
				inputLine = client.getIn().readLine();
				serverMoveListener.onServerMoveListener(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setServerMoveListener(ServerMoveListener l) {
		serverMoveListener = l;
	}
		
	
}
