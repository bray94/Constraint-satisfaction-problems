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



	private int color;

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