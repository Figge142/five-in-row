package fiveInRow;

import fiveInRow.Game.Mark;

public class Move
{
	public int x, y;
	public Mark mark;

	public Move(int x, int y, Mark mark)
	{
		this.x = x;
		this.y = y;
		this.mark = mark;
	}
	
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
	    if (other == this) {
	    	return true;
	    }
	    if (!(other instanceof Move)) {
	    	return false;
	    }
	    
		Move otherMove = (Move) other;
		if (this.x == otherMove.x && this.y == otherMove.y && this.mark == otherMove.mark) {
			return true;
		}
		
		return false;
	}
	
	public String toString()
	{
		String markString;
		if (mark == Mark.CIRCLE)
		{
			markString = "O";
		}
		else if (mark == Mark.CROSS)
		{
			markString = "X";
		}
		else
		{
			markString = "?";
		}
		
		return ("Move: Place " + markString + " at (x, y) = (" + x + ", " + y + ").");
	}
}
