package src.mp2;

import java.util.ArrayList;


public class PuzzleSolver{

	/** 
	* Method to call recursive algorithm for word based assignment and sets up necessary variables and print
	*/
	public void runWordBasedAssignment(){
		char[] tempSolution = new char[this.length];

		for(int i = 0; i < this.length ; i++){
			tempSolution[i] = ' ';
		}

		this.wordBasedAssignment(tempSolution, 0 , categoryList);

		for(int i = 0 ; i < solution.size() ; i++){
			System.out.println(solution.get(i));
		}

	}

	/** 
	* Runs recursive back-tracing algorithm
	*/

	public void wordBasedAssignment(char[] tempSolution, int index, ArrayList<Category> sortedCategoryList){

		
		// If solution doesn't contain any blank spaces, it is printed and returned up a level
		if(!(new String(tempSolution).contains(" "))){
			//this.solution.add(new String(tempSolution));
			System.out.println(new String(tempSolution));
			return;
		}

		// Gets array of positions for current category
		ArrayList<Integer> positions = sortedCategoryList.get(index).getPositions();

		// Iterate through words in current category
		for(String temp_word : sortedCategoryList.get(index).getWordList()){
			System.out.println(new String(tempSolution));

			// Check if word meets constraints
			if(checkConstraints(tempSolution, positions, temp_word)){

				// Finds the places for the current category and enters the current temp_word into that spot
				for(int i = 0; i < 3 ; i ++ ){
					tempSolution[positions.get(i) - 1] = temp_word.charAt(i);
				}

				wordBasedAssignment(tempSolution, index + 1 , sortedCategoryList); // Recursive search to next category with current solution
			}

			else{
				continue; // Skips current word since it doesn't work
			} 

		}
	}

	/** 
	* Method checks whether the current word fits in to the solution. It will check the positions it goes in to and
	* if the space is blank or has the matching letter
	*/
	public static boolean checkConstraints(char[] tempSolution, ArrayList<Integer> positions, String word){


		if((word.charAt(0) == tempSolution[positions.get(0)] || tempSolution[positions.get(0)] == ' ')
			&& (word.charAt(0) == tempSolution[positions.get(0)] || tempSolution[positions.get(0)] == ' ') 
				&& (word.charAt(0) == tempSolution[positions.get(0)] || tempSolution[positions.get(0)] == ' ')) return true;
		else return false;
	}
	
}