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