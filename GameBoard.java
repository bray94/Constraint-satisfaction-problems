package mp2Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameBoard {

	private ArrayList<Player> players;
	private ArrayList<GameCell> cells;
	private Scanner sc;
	File game;
	
	public GameBoard(String filename, int numberPlayers) {
		players = new ArrayList<Player>();
		for(int i = 0; i< numberPlayers; i++){
			players.add(new Player(i));
		}

		cells = new ArrayList<GameCell>();
		readBoard(filename);
	}
	
	public boolean isFull() {
		for(GameCell cell: cells) {
			if(!cell.isOccupied())
				return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("resource")
	private void readBoard(String filename) {
		game = new File(filename);
		try {
			sc = new Scanner(game);
			int counter = 0;
			
			while(sc.hasNextLine()) {
				String val = sc.nextLine();
				System.out.println("Val: " + val);
				String[] vals = val.split("\t");
				for(int i =0; i < vals.length; i++) {
					cells.add(new GameCell(counter%vals.length, counter/vals.length,Integer.parseInt(vals[i]) , 0, false));
					counter++;
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found");
		}
		
	}
	
	public void cellCapture(Player player, GameCell cell) {
		if(cell.isOccupied()){
			players.get(cell.getColor()).cellStolen(cell);
		}
		player.captureCell(cell);
		cell.setColor(player.playerColor());
		cell.setOccupied();
	}
	
	public ArrayList<GameCell> cells() {
		return cells;
	}
	
	public static void main(String[] args) {
		GameBoard g = new GameBoard("Narvik.txt", 2);
		System.out.println(g.cells.size());
		System.out.println("Value: " + g.cells().get(0).getValue() + " Occupied?: " + g.cells().get(0).isOccupied());
	}
}
