package core;

public class Board {
	
	int[][] board;
	int w, h;
	
	public Board(int w, int h) {
		this.w = w;
		this.h = h;
		
		board = new int[h][w];
	}
	
	public int get(int x, int y) {
		return board[y][x];
	}
	
	public void set(int x, int y, int i) {
		board[y][x] = i;
	}
	
	public int getH() {
		return h;
	}
	
	public int getW() {
		return w;
	}
	
	@Override
	public String toString() {
		String s = "  ";
		
		for (int x = 0; x < w; x++)
			s += " " + x;
		
		s += "\n  ";
		
		for (int x = 0; x < w; x++)
			s += "--";
		
		s += "\n";
		
		for (int y = 0; y < h; y++) {
			
			s += y + "|";
			
			for (int x = 0; x < w; x++) {
				int i = get(x, y);
				s += ((i >= 0) ? " " : "") + ((i >= 0) ? i : " x");
			}
			
			s += "\n";
		}
		
		return s;
	}
}
