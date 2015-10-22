package mp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameBoard 
{
	/**
	 * Stores the Players playing this Game
	 */
	private ArrayList<Player> players;
	
	/**
	 * Stores the Cells in this GameBoard
	 */
	private ArrayList<GameCell> cells;
	
	/**
	 * Input Reader
	 */
	private Scanner sc;
	
	/**
	 * The dimension of the square GameBoard
	 */
	private int width;
	
	/**
	 * The input Game File
	 */
	private File game;
	
	/**
	 * Constructor
	 * @param filename
	 * @param numberPlayers
	 */
	public GameBoard(String filename, int numberPlayers) 
	{
		//Add the Players to this Game
		players = new ArrayList<Player>();
		
		for(int i = 0; i< numberPlayers; i++)
		{
			players.add(new Player(i));
		}
		
		//Set the initial Width
		width = 0;
		
		//Initialize the GameCells List
		cells = new ArrayList<GameCell>();
		
		//Read in the GameFile
		readBoard(filename);
	}
	
	/**
	 * Check is the Game is finished or not
	 */
	public boolean isFull() 
	{
		for(GameCell cell: cells) 
		{
			if(!cell.isOccupied())
				return false;
		}
		
		return true;
	}
	
	/**
	 * Reads and Parses the GameBoard inputfile
	 * @param filename
	 */
	private void readBoard(String filename) 
	{
		// Open the File
		game = new File(filename);
		
		try 
		{
			sc = new Scanner(game);
			int counter = 0;
			
			while(sc.hasNextLine()) 
			{
				//Read a Line and Split it based on tabs
				String val = sc.nextLine();
				System.out.println("Val: " + val);
				String[] vals = val.split("\t");
				
				//Set the width of this board
				width = vals.length;
				
				//
				for(int i =0; i < vals.length; i++) 
				{
					cells.add(new GameCell(counter%vals.length, counter/vals.length, Integer.parseInt(vals[i]) , 0, false));
					counter++;
				}
			}
			
			//Close the input reader
			sc.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File not found");
		}
		
	}

	/**
	 * Allocates a Cell to a Player if it's unoccupied
	 * @param player
	 * @param cell
	 */
	public void cellCapture(Player player, GameCell cell) 
	{
		//Check if already occupied
		if(cell.isOccupied())
		{
			return;
		}
		
		//Assign the Cell to that Player
		player.captureCell(cell);
		cell.setOccupied(player.playerColor());
	}
	
	/**
	 * Getter method for the Cells in this GameBoard
	 * @return
	 */
	public ArrayList<GameCell> cells() 
	{
		return cells;
	}
	
	/**
	 * Prints the Board
	 * @param width
	 */
	public void printBoard(int width) 
	{
		try
		{
			PrintWriter writer = new PrintWriter("status.txt", "UTF-8");
			for(int i = 0; i < cells.size(); i++)
			{
				if(i%width ==0)
					writer.println();
				if(cells.get(i).isOccupied())
				{
					if(cells.get(i).getColor() == 0)
						writer.print("B");
					else
						writer.print("G");
				}
				else
					writer.print("X");
				writer.print("\t");
			}
			writer.close();
			
		}
		catch(UnsupportedEncodingException | FileNotFoundException e) 
		{
			System.out.println("File not found");
		}
	}
	
	/**
	 * Driver Method to Test the above Code
	 */
	public static void main(String[] args) 
	{
		GameBoard g = new GameBoard("src/mp2/game_boards/Westerplatte.txt", 2);
		System.out.println(g.cells.size());
		System.out.println("Value: " + g.cells().get(0).getValue() + " Occupied?: " + g.cells().get(0).isOccupied());
		Player blue = g.players.get(0);
		Player green = g.players.get(1);
		g.cellCapture(blue, g.cells().get(0));
		g.cellCapture(blue, g.cells().get(11));
		g.cellCapture(blue, g.cells().get(23));
		g.cellCapture(green, g.cells().get(5));
		g.cellCapture(green, g.cells().get(15));
		g.cellCapture(green, g.cells().get(25));
		g.cellCapture(green, g.cells().get(11));
		g.printBoard(6);
		System.out.println("Player 1: " + g.players.get(0).playerScore());
		System.out.println("Player 2: " + g.players.get(1).playerScore());
	}

}
