package src.mp2;

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
	 * Finds the category with the least number of words left
	 * Returns the index of this category in the categoryList
	 */
	public int getCategoryWithLowestWords()
	{
		int min = 0;
		int counter = 0;
		
		for (Category curr_category : categoryList)
		{
			if(curr_category.getNumWords() < categoryList.get(min).getNumWords())
				min = counter;
			counter++;
		}
		
		return min;
	}

	/** 
	* Method to call recursive algorithm for 
	* word based assignment and sets up necessary variables and print
	* the solution
	*/
	public void runWordBasedAssignment()
	{
		char[] tempSolution = new char[this.length];

		for(int i = 0; i < this.length ; i++)
		{
			tempSolution[i] = ' ';
		}

		wordBasedAssignment(tempSolution, 0 , categoryList);

		System.out.println("Solution Size: " + solution.size());
		
		//Print the Solution
		for(int i = 0 ; i < solution.size() ; i++)
		{
			System.out.println(solution.get(i));
		}

	}
	
	/** 
	* Runs recursive back-tracing algorithm
	*/
	public void wordBasedAssignment(char[] tempSolution, int index, ArrayList<Category> sortedCategoryList)
	{	
		// If solution doesn't contain any blank spaces, it is printed and returned up a level
		if(!(new String(tempSolution).contains(" ")))
		{
			solution.add(new String(tempSolution));
			return;
		}

		// Gets array of positions for current category
		ArrayList<Integer> positions = sortedCategoryList.get(index).getPositions();

		// Iterate through words in current category
		for(String temp_word : sortedCategoryList.get(index).getWordList())
		{
			//System.out.println(new String(tempSolution));

			// Check if word meets constraints
			if(checkConstraints(tempSolution, positions, temp_word))
			{

				// Finds the places for the current category and enters the current temp_word into that spot
				for(int i = 0; i < 3 ; i ++ )
				{
					tempSolution[positions.get(i) - 1] = temp_word.charAt(i);
				}

				wordBasedAssignment(tempSolution, ((index + 1)%categoryList.size()) , sortedCategoryList); // Recursive search to next category with current solution
			}

			else
			{
				continue; // Skips current word since it doesn't work
			} 

		}
	}

	/** 
	* Method checks whether the current word fits in to the solution. 
	* It will check the positions it goes in to and
	* if the space is blank or has the matching letter
	*/
	public static boolean checkConstraints(char[] tempSolution, ArrayList<Integer> positions, String word)
	{
		if((word.charAt(0) == tempSolution[positions.get(0) - 1] || tempSolution[positions.get(0) -1] == ' ')
			&& (word.charAt(1) == tempSolution[positions.get(1) - 1] || tempSolution[positions.get(1) -1] == ' ') 
				&& (word.charAt(2) == tempSolution[positions.get(2) - 1] || tempSolution[positions.get(2) -1] == ' ')) 
			return true;
		else 
			return false;
	}

	
	/**
	 * Driver Method to test the above Code
	 * @param args
	 */
	public static void main(String [] args)
	{
		Puzzle myPuzzle = new Puzzle("src/mp2/puzzle1.txt");
		//myPuzzle.testPuzzleParameters();
		myPuzzle.runWordBasedAssignment();
	}
	
	
}
