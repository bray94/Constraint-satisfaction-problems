package mp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This file stores the word list for every category
 *
 */
public class Category 
{
	// _________________________ CATEGORY NAME __________________________________
	
	/**
	 * The name of this Category
	 */
	private String categoryName;
	
	/**
	 * Returns the value of the Category
	 */
	public String getCategoryName()
	{
		return categoryName;
	}
	
	/**
	 * Sets the name of this category
	 */
	public void setCategoryName(String name)
	{
		categoryName = name;
	}
	
	// ___________________________________ WORD LIST __________________________________
	
	/**
	 * Stores the list of words available for this category
	 */
	private ArrayList<String> wordList;
	
	/**
	 * Constructor
	 */
	public Category()
	{
		//Initialize the Word List and the Positions List
		wordList = new ArrayList<String>();
		positions = new ArrayList<Integer>();
	}
	
	/**
	 * Simply adds a word to the list of words
	 * @param word
	 */
	public void addWord(String word)
	{
		wordList.add(word);
	}
	
	/**
	 * Returns the list of words in this category
	 */
	public ArrayList<String> getWordList()
	{
		return wordList;
	}
	
	/**
	 * Reads in Words from a File based on the Category Name
	 */
	public void makeWordList()
	{
		//Contains list of words for this file
		File myFile =  new File("src/mp2/wordlist/" + categoryName + ".txt");
		
		try
		{
			Scanner inputReader = new Scanner(myFile);
			
			while(inputReader.hasNextLine())
			{
				//Get the next word
				String curr_word = inputReader.nextLine().trim();
				
				addWord(curr_word);
			}
		}
		
		catch(FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
	}
	

	// ___________________________________ CATEGORY POSITIONS ______________________________
	
	/**
	 * Stores the positions where this category must form words
	 */
	private ArrayList<Integer> positions;
	
	/**
	 * Returns the positions for this category
	 */
	public ArrayList<Integer> getPositions()
	{
		return positions;
	}
	
	/**
	 * Adds a Position to this Category
	 */
	public void addPosition(int newPosition)
	{
		positions.add(newPosition);
	}
	
}
