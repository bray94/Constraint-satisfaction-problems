package mp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Puzzle 
{
	/**
	 * Stores the categories possible for this maze
	 */
	private ArrayList<Category> categoryList;
	
	/**
	 * Stores the number of letters in this maze
	 */
	private int length;
	
	/**
	 * Stores the list of Solutions possible for this Maze
	 */
	private ArrayList<String> solution;
	
	/**
	 * Constructor
	 */
	public Puzzle(String filename)
	{
		//Initialize the arraylists
		categoryList = new ArrayList<Category>();
		solution = new ArrayList<String>();
		
		//Make the Actual Maze
		makePuzzle(filename);
	}
	
	/**
	 * Makes the Actual Representation of the Puzzle
	 * @param filename The name of the file containing the maze
	 */
	public void makePuzzle(String filename)
	{
		//Create a new file
		File puzzleFile = new File(filename);
		
		try
		{
			//The actual input reader
			Scanner inputReader = new Scanner(puzzleFile);
			
			//Assign the value of the length
			length = inputReader.nextInt();
			
			inputReader.nextLine();
			
			while (inputReader.hasNextLine())
			{
				//Get the Current Line
				String curr_line = inputReader.nextLine();
				
				//Make a new category to add
				Category newCategory = new Category();
				
				String [] temp = curr_line.split(":");
								
				//Set the name of this category
				newCategory.setCategoryName(temp[0]);
				
				//Remove the spaces
				temp[1] = temp[1].replaceAll("\\s", "");
				
				String [] temp2 = temp[1].split(",");
				
				//Add Each Position
				for(int i = 0; i < temp2.length; i++)
				{
					newCategory.addPosition(Integer.parseInt(temp2[i]));
				}
				
				//Read in Words from the File
				newCategory.makeWordList();
				
				//Add this category to this maze's list
				categoryList.add(newCategory);
			}
			
			inputReader.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		
		
		
	}
	
	/**
	 * Tests the Basic Puzzle Parameters
	 */
	public void testPuzzleParameters()
	{
		System.out.println(length);
		
		for (Category temp_category : categoryList)
		{
			System.out.print(temp_category.getCategoryName() + ": ");
			
			ArrayList<Integer> positions_list = temp_category.getPositions();
			
			for (int x: positions_list)
			{
				System.out.print(x + ",");
			}
			
			if(temp_category.getCategoryName().equals("emotion"))
			{
				ArrayList<String> wordList = temp_category.getWordList();
				
				for(String word: wordList)
				{
					System.out.println(word+ " ,");
				}
			}
			
			System.out.println();
		}
		
	}
	
	/**
	 * Driver Method to test the above Code
	 * @param args
	 */
	public static void main(String [] args)
	{
		Puzzle myPuzzle = new Puzzle("src/mp2/puzzle1.txt");
		myPuzzle.testPuzzleParameters();
	}
	
	
}
