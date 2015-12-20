package fiveInRow;

public class Main {

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		Player player1 = new Human();
		Player player2 = new Bot();
		
		Game game = new Game(10, 10);
		game.setPlayers(player1, player2);
		game.play();
	}
}