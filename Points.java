//Points.java

package src.mp2;

import java.util.ArrayList;

public class Points{
	private ArrayList<Points> neighbors;



	public void addNeighbor(Points neighbor){
		neighbors.add(neighbor);
	}

	public ArrayList<Points> getNeighbors(){
		return neighbors;
	}

	public ArrayList<Points> stillAvailable;

	public void addStillAvailable(ArrayList<Points> points){
		stillAvailable = new ArrayList<Points>();

		for(Points point : points){
			if(this == point) continue;
			else stillAvailable.add(point);
		}
	}




	private int color;
	ArrayList<Integer> possible;

	public void setColor(int i){
		color = i;
	}

	public int getColor(){
		return color;
	}



	private double x;

	private double y;

	public void setX(double x){
		neighbors = new ArrayList<Points>();

		possible = new ArrayList<Integer>();
		for(int i = 1; i < 5 ; i++){
			possible.add(i);
		}

		color = 0;

		this.x = x;;
	}

	public double getX(){
		return x;
	}

	public void setY(double y){
		this.y = y;
	}

	public double getY(){
		return y;
	}
}