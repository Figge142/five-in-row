package fiveInRow;

import java.util.Scanner;

import fiveInRow.Game.Mark;

public class Human implements Player {
	Game game;
	Mark playedMark;
	
	@Override
	public void setMark(Mark mark)
	{
		playedMark = mark;
	}

	@Override
	public void setGame(Game game)
	{
		this.game = game;
	}

	@Override
	public void resetPlayer()
	{
		game = null;
		playedMark = null;
	}
	
	@Override
	public Move getMove() {
		assert(game != null);
		assert(playedMark != null);
		
		game.printBoard();
		System.out.print("Enter move (x, y): ");
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		int x = sc.nextInt();
		int y = sc.nextInt();
		
		//sc.close();
		
		return new Move(x, y, playedMark);
	}

}
