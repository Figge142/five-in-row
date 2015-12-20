package fiveInRow;

import fiveInRow.Game.Mark;

public interface Player {
	
	public void setMark(Mark mark);
	
	public void setGame(Game game);
	
	public void resetPlayer();
	
	public Move getMove();
}
