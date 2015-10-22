package mp2;

public class GameCell 
{

	/*
	 * xLocation/yLocation - position in grid
	 * 					   - x is horizontal across the top
	 * 					   - y is vertical top to bottom
	 * value - value of the GameCell
	 * color - So if the color is 0 -> player 0
	 * 		 - Color is 1 -> player 1
	 * occupied - whether or not the cell is occupied in the first place
	 */
	
	/**
	 * The co-ordinates of this cell
	 */
	private int xLocation,yLocation;
	
	/**
	 * The value of this Cell
	 */
	private int value;
	
	/**
	 * The Color of this Cell
	 * Essentially the player who owns this Cell
	 */
	private int color;
	
	/**
	 * Store whether this Cell has been occupied or not
	 */
	private boolean occupied;
	
	/**
	 * Constructor
	 */
	public GameCell() 
	{
		new GameCell(0,0,0,0,false);
	}
	
	/**
	 * Initialize the data for this Cell
	 */
	public GameCell(int x, int y, int val, int cellColor, boolean occ) 
	{
		xLocation = x;
		yLocation = y;
		value = val;
		color = cellColor;
		occupied = occ;
	}

	/**
	 * Getter Method for the Occupation Status
	 */
	public boolean isOccupied() 
	{
		return occupied;
	}
	
	/**
	 * A Player captures this cell
	 */
	public void setOccupied(int playerColor) 
	{
		occupied = true;
		setColor(playerColor);
	}
	
	/**
	 * Get the value of this Cell
	 */
	public int getValue() 
	{
		return value;
	}
	
	/**
	 * Set the Color of this Cell
	 */
	private void setColor(int playerColor) 
	{
		color = playerColor;
	}
	
	/**
	 * Get the color of this cell
	 */
	public int getColor() 
	{
		return color;
	}
	
	/**
	 * Get the X Co-ordinate
	 */
	public int getX() 
	{
		return xLocation;
	}
	
	/**
	 * Get the Y Co-ordinate
	 */
	public int getY() 
	{
		return yLocation;
	}
	
}
