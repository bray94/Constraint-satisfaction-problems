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
	 * Stores the spots for this maze
	 */
	private ArrayList<Spot> spotList;
	
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

	// ___________________________________ Constructs Puzzle __________________________________
	
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
	

	// ___________________________________ Functions for Word Based Assignment __________________________________

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

		ArrayList<Category> sortedCategoryList = sortCategoryList();


		wordBasedAssignment(tempSolution, 0 , sortedCategoryList);

		System.out.println("Solution Size: " + solution.size());
		
		//Print the Solution
		for(int i = 0 ; i < solution.size() ; i++)
		{
			System.out.println(solution.get(i));
		}

	}

	
	/** 
	* Runs recursive back-tracing algorithm for word based assignment
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
		/*	
		*	System.out.println(new String(tempSolution) + "       Current Word: " 
		*		+ temp_word + "     Current positions: " + positions.get(0) + ", "
		*			+ positions.get(1) + ", "+ positions.get(2));
		*/ 

			// Check if word meets constraints
			if(checkConstraints(tempSolution, positions, temp_word))
			{
				char[] newTempSolution = new char[tempSolution.length];

				for(int i = 0; i < tempSolution.length ; i++)
				{
					newTempSolution[i] = tempSolution[i];
				}

				// Finds the places for the current category and enters the current temp_word into that spot
				for(int i = 0; i < 3 ; i ++ )
				{
					newTempSolution[positions.get(i) - 1] = temp_word.charAt(i);
				}

				wordBasedAssignment(newTempSolution, ((index + 1) % sortedCategoryList.size()) , sortedCategoryList); // Recursive search to next category with current solution
			}

			else
			{
				continue; // Skips current word since it doesn't work
			} 

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
	* Sorts categories based on amount of words 
	* using the ones with the least amount(the most constraining)
	* first
	*/
	public ArrayList<Category> sortCategoryList()
	{
		ArrayList<Category> newList = new ArrayList<Category>();

		int minimum;

		int numberOfCategories = categoryList.size();

		for(int i = 0; i < numberOfCategories ; i++)
		{
			minimum = getCategoryWithLowestWords();
			newList.add(categoryList.get(minimum));
			categoryList.remove(minimum);
		}
		
		return newList;
	}

	/** 
	* Method checks whether the current word fits in to the solution. 
	* It will check the positions it goes in to and
	* if the space is blank or has the matching letter
	*/
	public static boolean checkConstraintsWordBasedAssignment(char[] tempSolution, ArrayList<Integer> positions, String word)
	{
		if((word.charAt(0) == tempSolution[positions.get(0) - 1] || tempSolution[positions.get(0) -1] == ' ')
			&& (word.charAt(1) == tempSolution[positions.get(1) - 1] || tempSolution[positions.get(1) -1] == ' ') 
				&& (word.charAt(2) == tempSolution[positions.get(2) - 1] || tempSolution[positions.get(2) -1] == ' ')) 
			return true;
		else 
			return false;
	}


	// ___________________________________ Functions for Letter Based Assignment __________________________________

	/** 
	* Method to call recursive algorithm for 
	* letter based assignment and sets up necessary variables and print
	* the solution
	*/
	public void runLetterBasedAssignment()
	{

		getSpotsFromCategories();

		char[] tempSolution = new char[this.length];

		for(int i = 0; i < this.length ; i++)
		{
			tempSolution[i] = ' ';
		}

		ArrayList<Spot> sortedSpotList = sortSpotList();


		letterBasedAssignment(tempSolution, 0 , sortedSpotList);

		System.out.println("Solution Size: " + solution.size());
		
		//Print the Solution
		for(int i = 0 ; i < solution.size() ; i++)
		{
			System.out.println(solution.get(i));
		}

	}


	/** 
	* Runs recursive back-tracing algorithm for letter based search
	*/
	public void letterBasedAssignment(char[] tempSolution, int index, ArrayList<Integer> sortedSpotList)
	{	
		// If solution doesn't contain any blank spaces, it is printed and returned up a level
		if(!(new String(tempSolution).contains(" ")))
		{
			solution.add(new String(tempSolution));
			return;
		}

		// Gets array of categories for current position
		ArrayList<Category> categories = sortedSpotList.get(index).getCategories();

		// Iterate through letters in current position
		for(char temp_letter : sortedListList.get(index).getLetterList())
		{
			// Check if word meets constraints
			if(checkConstraints(tempSolution, categories, temp_letter))
			{
				char[] newTempSolution = new char[tempSolution.length];

				// Creates new solution to pass recursively
				for(int i = 0; i < tempSolution.length ; i++)
				{
					newTempSolution[i] = tempSolution[i];
				}

				newTempSolution[index] = temp_letter;

				letterBasedAssignment(newTempSolution, ((index + 1) % sortedLetterList.size()) , sortedLetterList); // Recursive search to next category with current solution
			}

			else
			{
				continue; // Skips current letter since it doesn't work
			} 
		}
	}

	/**
	 * Finds the spot with the least number of letters left
	 * Returns the index of this spot in the spotList
	 */
	public int getSpotWithLowestLetters()
	{
		int min = 0;
		int counter = 0;
		
		for (Spot curr_spot : spotList)
		{
			if(curr_spot.getNumLetters() < spotList.get(min).getNumLetters())
				min = counter;
			counter++;
		}
		
		return min;
	}

	/** 
	* Sorts spots in solution based on amount of letters
	* using the ones with the least amount(the most constraining)
	* first
	*/
	public ArrayList<Spot> sortSpotList()
	{
		ArrayList<Spot> sortedSpotList = new ArrayList<Spot>();

		int minimum;

		int numberOfSpots = spotList.size();

		for(int i = 0; i < numberOfSpots ; i++)
		{
			minimum = getSpotWithLowestLetters();
			sortedSpotList.add(spotList.get(minimum));
			spotList.remove(minimum);
		}

		return sortedSpotList;
	}

	/** 
	* Method checks whether the current letter fits in to the solution. 
	* It will check the categories to see if it fits in and
	* if the space is blank or has the matching letter
	*/
	public static boolean checkConstraintsLetterBasedAssignment(char[] tempSolution, ArrayList<Category> categories, char letter)
	{
		// NEEDS IMPLIMENTED
	}

	/** 
	* Sets the spotList from Categories
	*/
	public void getSpotsFromCategories()
	{
		Spot spot;
		ArrayList<Integer> positions;

		// Go through positions to initilize spots
		for(int i = 1 ; i < length + 1 ; i++){

			// Make new spot with number
			spot = new Spot();
			spot.setSpotNumber(i);

			// Look through categories and check whether
			// it has a letter that matches the position
			for(Category curr_category : categoryList){

				// Iterate through positions
				positions = curr_category.getPositions();
				for(int j = 0; j < 3 ; j++){

					// If position matches
					if(positions.get(j) == i){

						// Add that letter to this position
						for(String word : curr_category.getWordList()){
							spot.addLetter(word.chaAt(j));
						}
					}

					// If the position is greater than our spot, break
					else if(positions.get(j) > i) break;
				}
			}

			// Add spot to list
			spotList.add(spot);
		}

	}	

// ___________________________________ Main Function __________________________________

	/**
	 * Driver Method to test the above Code
	 * @param args
	 */
	public static void main(String [] args)
	{
		Puzzle myPuzzle = new Puzzle("src/mp2/puzzle4.txt");
		//myPuzzle.testPuzzleParameters();
		myPuzzle.runWordBasedAssignment();
	}
	
	
}