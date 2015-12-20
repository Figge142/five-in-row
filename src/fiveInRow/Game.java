package fiveInRow;

import java.util.ArrayList;

public class Game {
	private Player player1;
	private Player player2;
	private int currentPlayer = 1;
	
	private Mark[][] board;
	private int w;
	private int h;
	
	private ArrayList<Move> moveList;

	public enum Mark {
		CIRCLE,
		CROSS,
		EMPTY;
		
		public static Mark oppositeMark(Mark mark) {
			if (mark == Mark.CIRCLE) {
				return Mark.CROSS;
			}
			else if (mark == Mark.CROSS) {
				return Mark.CIRCLE;
			}
			
			return Mark.EMPTY;
		}
	}
	
	public enum GameState {
		CIRCLE_WINNER,
		CROSS_WINNER,
		DRAW,
		ONGOING
	}
	
	public Game(int w, int h) {
		board = new Mark[h][w];
		this.w = w;
		this.h = h;
		moveList = new ArrayList<Move>();
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				board[y][x] = Mark.EMPTY;
			}
		}
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return w;
	}
	
	public void setPlayers(Player player1, Player player2) {
		if (player1 != null) {
			player1.setGame(this);
			player1.setMark(Mark.CIRCLE);
			this.player1 = player1;
		}
		
		if (player2 != null) {
			player2.setGame(this);
			player2.setMark(Mark.CROSS);
			this.player2 = player2;
		}
	}

	public void play(){
		while (getCurrentGameState() == GameState.ONGOING) {
			if (currentPlayer == 1) {
				Move move = player1.getMove();
				while (!(move.mark == Mark.CIRCLE) || !makeMove(move)) {
					move = player1.getMove();
				}
				currentPlayer = 2;
			}
			else {
				Move move = player2.getMove();
				while (!(move.mark == Mark.CROSS) || !makeMove(move)) {
					move = player2.getMove();
				}
				currentPlayer = 1;
			}
			
			printBoard();
		}
		
		printWinner();
	}
	
	public boolean makeMove(Move move) {		
		if (move == null || 
			move.y < 0 || move.y >= h || move.x < 0 || move.x >= w || 
			board[move.y][move.x] != Mark.EMPTY) {
			return false;
		}

		moveList.add(move);
		board[move.y][move.x] = move.mark;
		return true;
	}
	
	public boolean undoMove(Move move) {
		if (move == null || board[move.y][move.x] != move.mark) {
			return false;
		}

		moveList.remove(moveList.size() - 1);
		board[move.y][move.x] = Mark.EMPTY;
		return true;
	}
	
	public Mark getMark(int x, int y) {
		if (x < 0 || x >= w || y < 0 || y >= h) {
			return null;
		}
		
		return board[y][x];
	}
	
	public GameState getCurrentGameState() {
		//Checks the games current state
		Mark lastMark = Mark.EMPTY;
		Mark currentMark = Mark.EMPTY;
		int consecutive = 0;
		
		boolean hasEmptySpace = false;
		
		//Left - right
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				lastMark = currentMark;
				currentMark = board[y][x];
				
				if (currentMark == lastMark) {
					consecutive++;
				}
				else {
					consecutive = 1;
				}
				
				if (consecutive == 5 && currentMark == Mark.CROSS) {
					return GameState.CROSS_WINNER;
				}
				else if (consecutive == 5 && currentMark == Mark.CIRCLE) {
					return GameState.CIRCLE_WINNER;
				}
				
				if (currentMark == Mark.EMPTY) {
					hasEmptySpace = true;
				}
			}
			consecutive = 0;
		}
		
		//Up - down
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				lastMark = currentMark;
				currentMark = board[y][x];
				
				if (currentMark == lastMark) {
					consecutive++;
				}
				else {
					consecutive = 1;
				}
				
				if (consecutive == 5 && currentMark == Mark.CROSS) {
					return GameState.CROSS_WINNER;
				}
				else if (consecutive == 5 && currentMark == Mark.CIRCLE) {
					return GameState.CIRCLE_WINNER;
				}
			}
			consecutive = 0;
		}
		
		//Top left - Bottom down
		for (int y = -w + 1; y < h; y++) {
			for (int x = 0; x < w; x++) {
				lastMark = currentMark;
				if (y + x < 0 || y + x >= h) {
					currentMark = Mark.EMPTY;
					continue;
				}
				else {
					currentMark = board[y + x][x];
				}
				
				if (currentMark == lastMark) {
					consecutive++;
				}
				else {
					consecutive = 1;
				}
				
				if (consecutive == 5 && currentMark == Mark.CROSS) {
					return GameState.CROSS_WINNER;
				}
				else if (consecutive == 5 && currentMark == Mark.CIRCLE) {
					return GameState.CIRCLE_WINNER;
				}
			}
			consecutive = 0;
		}
		
		//Bottom left - Top right
		for (int y = 0; y < h + w - 1; y++) {
			for (int x = 0; x < w; x++) {
				lastMark = currentMark;
				if (y - x < 0 || y - x >= h) {
					currentMark = Mark.EMPTY;
					continue;
				}
				else {
					currentMark = board[y - x][x];
				}
				
				if (currentMark == lastMark) {
					consecutive++;
				}
				else {
					consecutive = 1;
				}
				
				if (consecutive == 5 && currentMark == Mark.CROSS) {
					return GameState.CROSS_WINNER;
				}
				else if (consecutive == 5 && currentMark == Mark.CIRCLE) {
					return GameState.CIRCLE_WINNER;
				}
			}
			consecutive = 0;
		}

		if (!hasEmptySpace) {
			return GameState.DRAW;
		}
		
		return GameState.ONGOING;
	}
	
	public Move getLastMove() {
		int moveListSize = moveList.size();
		if (moveListSize == 0) {
			return null;
		}
		return moveList.get(moveListSize - 1);
	}
	
	public Move getNextToLastMove() {
		int moveListSize = moveList.size();
		if (moveListSize < 2) {
			return null;
		}
		return moveList.get(moveListSize - 2);
	}
	
	public void printBoard() {
		System.out.println();
		System.out.print("y\\x");
		for (int x = 0; x < w; x++) {
			System.out.format("%3d", x);
		}
		System.out.println();
		
		for (int y = 0; y < h; y++) {
			System.out.format("%4d", y);
			for (int x = 0; x < w; x++) {
				switch(board[y][x]) {
					case EMPTY:
						System.out.print(" . ");
						break;
					case CROSS:
						System.out.print(" X ");
						break;
					case CIRCLE:
						System.out.print(" O ");
						break;
					default:
						break;
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private void printWinner() {
		printBoard();
		
		String winner;
		GameState state = getCurrentGameState();
		if (state == GameState.CIRCLE_WINNER) {
			winner = "CIRCLE";
		}
		else if (state == GameState.CROSS_WINNER) {
			winner = "CROSS";
		}
		else if (state == GameState.DRAW) {
			winner = "DRAW";
		}
		else {
			winner = "ERROR";
		}

		System.out.println("===================================");
		System.out.println("             GAME OVER             ");
		System.out.println("         WINNER IS " + winner + "!");
		System.out.println("===================================");
	}
	
	public void printMoveListAsCode()
	{
		int moveListSize = moveList.size();
		for (int i = 0; i < moveListSize; i++) {
			Move m = moveList.get(i);
			int x = m.x;
			int y = m.y;
			String mark = m.mark == Mark.CIRCLE ? "CIRCLE" : "CROSS";
			System.out.format("game.makeMove(new Move(%d, %d, Mark.%s));\n", x, y, mark);
		}
	}
}

