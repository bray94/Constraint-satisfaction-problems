package src.mp2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Spots
{
	// _________________________ Spot Number __________________________________

	/**
	 * The number of the spot in puzzle between 1 and puzzle size
	 */
	private int spotNumber;

	/**
	 * Get the spot number
	 */
	public int getSpotNumber()
	{
		return spotNumber;
	}

	/**
	 * Set the spot number
	 */
	public void setSpotNumber(int num)
	{
		spotNumber = num;
	}

	// ___________________________________ Letter LIST __________________________________

	/**
	 * Stores the list of letters available each spot
	 */
	private ArrayList<Char> letterList;
	
	/**
	 * Constructor
	 */
	public Spot()
	{
		//Initialize the Letter List and the Categories List
		letterList = new ArrayList<Char>();
		categories = new ArrayList<String>();
	}
	
	/**
	 * Simply adds a letter to the list of letters
	 * @param word
	 */
	public void addLetter(char letter)
	{
		if(!letterList.contains(letter)) letterList.add(letter);
	}
	
	/**
	 * Returns the list of letters in this position
	 */
	public ArrayList<Char> getLetterList()
	{
		return letterList;
	}

	public int getNumLetters()
	{
		return lettersList.size();
	}
	

	// ___________________________________ POSITION CATEGORIES ______________________________
	
	/**
	 * Stores the categories attached to this position
	 */
	private ArrayList<Category> categories;
	
	/**
	 * Returns the categories for this position
	 */
	public ArrayList<Category> getCategories()
	{
		return categories;
	}
	
	/**
	 * Adds a Category to this Position
	 */
	public void addCategory(Category newCategory)
	{
		categories.add(newcategory);
	}




}