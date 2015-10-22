package mp2;

import java.util.ArrayList;

public class Player 
{
	/**
	 * The Cells Captured by this Player
	 */
	private ArrayList<GameCell> cells;
	
	/**
	 * The total score of this player
	 */
	private int totalValue;
	
	/**
	 * The number of this Player
	 */
	private int order;
	
	/**
	 * Constructor
	 * @param playerNumber
	 */
	public Player(int playerNumber) 
	{
		totalValue = 0;
		order = playerNumber;
		cells = new ArrayList<GameCell>();
	}

	/**
	 * Lets this Player capture the Cell
	 * @param cell
	 */
	public void captureCell(GameCell cell) 
	{
		cells.add(cell);
		totalValue+= cell.getValue();
		System.out.println("Score: "+ totalValue);
	}

	/**
	 * Lets this Cell get stolen from this player
	 */
	public void cellStolen(GameCell cell) 
	{
		cells.remove(cell);
		totalValue-= cell.getValue();
		System.out.println("Score: "+ totalValue);
	}

	/**
	 * Gets the Color of this player
	 */
	public int playerColor() 
	{
		return order;
	}
	
	/**
	 * Gets the Score of this Player
	 */
	public int playerScore() 
	{
		return totalValue;
	}
}
