package core;

public class Game {
	
	Player player1, player2;
	
	int nTurn;
	
	public Game(int w, int h) {
		player1 = new Player(new Board(w, h));
		player2 = new Player(new Board(w, h));
		
		nTurn = 0;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public int getNTurn() {
		return nTurn;
	}
	
	public int advanceTurn() {
		nTurn += 1;
		return nTurn;
	}
	
	public int getTurn() {
		return getNTurn() % 2;
	}
	
	public Player getPlayerForTurn() {
		return (getTurn() == 0) ? getPlayer1() : getPlayer2();
	}
	
	public Player getEnemyForTurn() {
		return (getTurn() == 0) ? getPlayer2() : getPlayer1();
	}

}
