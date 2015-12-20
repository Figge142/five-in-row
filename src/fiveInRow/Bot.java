package fiveInRow;

import java.util.Random;

import fiveInRow.Game.Mark;

public class Bot implements Player {
	private Game game;
	private Mark playedMark;
	
	@Override
	public void setMark(Mark mark) {
		playedMark = mark;
	}

	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void resetPlayer() {
		game = null;
		playedMark = null;
	}

	@Override
	public Move getMove() {
		int w = game.getWidth();
		int h = game.getHeight();
		
		Random rand = new Random();
		
		return new Move(rand.nextInt(w), rand.nextInt(h), playedMark);
	}

}
