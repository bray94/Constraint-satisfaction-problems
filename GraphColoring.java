//GraphColoring.java

package src.mp2;

import java.util.ArrayList;
import java.util.Random;
import java.awt.geom.Line2D;
import java.lang.Math;

public class GraphColoring{
	ArrayList<Points> points;
	ArrayList<Line2D> lines;


	public void generatePoints(int n){
		Random num = new Random();
		points = new ArrayList<Points>();

		while(n > 0){
			Points point = new Points();
			double x = num.nextDouble();
			double y = num.nextDouble();

			point.setX(x);			

			point.setY(y);

			points.add(point);

			n--;
		}

		for(Points curr : points){
			curr.addStillAvailable(points);
		}
	}

	public void connectPoints(){

		lines = new ArrayList<Line2D>();

		Random num = new Random();

		boolean stillAvailable = true;
		double lastNearest = 0.0;
		double tempNearest;
		Line2D line;
		int whileLoop = 0;
		int firstForloop = 0;

		while(stillAvailable){

			// if it assigns a line to a point it will flip this to true and continue the while loop until it comes back
			stillAvailable = false;

			// Iterates through points in list
			for(Points currentPoint : points){


				// If the point has no more possible neighbor options it goes to the next point
				if(currentPoint.stillAvailable.size() == 0) continue;

				Points other = currentPoint.stillAvailable.get(0);
				Points otherPoint;

				//System.out.println("Current point: " + currentPoint);


				// Goes through the remaining neighbors
				for(int i = 0 ; i < currentPoint.stillAvailable.size() ; i++){

					double nearest = Math.sqrt(2);

					// Finds the nearest remaining neighbor
					for(int currPoint = 0; currPoint < currentPoint.stillAvailable.size() ; currPoint++){
						otherPoint = currentPoint.stillAvailable.get(currPoint);

						// If the other point is a neighbor then it's already been checked and it's skipped
						if(currentPoint.getNeighbors().contains(otherPoint)) continue;

						tempNearest = Math.sqrt(((currentPoint.getX() - otherPoint.getX()) * (currentPoint.getX() - otherPoint.getX())) 
							+ ((currentPoint.getY() - otherPoint.getY()) * (currentPoint.getY() - otherPoint.getY())));

						if(tempNearest < nearest) {
							other = otherPoint;
							nearest = tempNearest;
						}
					}


					// Removes the point from each other's still available, 
					//since it is currently checking it for plausability
					if(currentPoint.stillAvailable.indexOf(other) != -1) {
						currentPoint.stillAvailable.remove(currentPoint.stillAvailable.indexOf(other));
						other.stillAvailable.remove(other.stillAvailable.indexOf(currentPoint));

					}

					// Creates a line between the two points
					line = new Line2D.Double(currentPoint.getX() , currentPoint.getY() , other.getX() , other.getY());

					// Sets it to not intersect at first
					boolean intersects = false;

					// If there are any other lines, iterate though them and check if any cross this line,
					// if so set intersect to true and break out of list
					if(lines.size() != 0){
						for(Line2D otherLine : lines){
							boolean pointOnLine = ((otherLine.getX1() == other.getX() || otherLine.getX2() == other.getX()) 
								&& (otherLine.getY1() == other.getY() || otherLine.getY2() == other.getY()));

							if(line.intersectsLine(otherLine) && !pointOnLine){
								intersects = true;
								break;
							}
						}
					}

					// If it didn't find a line that intersected, add line to line list
					// in graph and add it to each other's neighbor list
					if(!intersects){
						lines.add(line);
						other.addNeighbor(currentPoint);
						currentPoint.addNeighbor(other);

						// Sets still available to true since solutions are still being found
						stillAvailable = true;

						break;

					}
					
				}
				
			}


		}
		System.out.println("Entered while loop " + whileLoop + " times and eneterd first for loop " + firstForloop + "times");
	}

	public boolean colorMapWithoutFowardChecking(int index){
		// Checks if we made it to an assignment for all, if so returns true
		if(index >= point.size()){
			return true;
		}

		// iterates through remaining colors for point
		for(int colorIndex = 0 ; colorIndex < points.get(index).possible.size() ; colorIndex++ ){

			// Gets color of remaining possible colors
			int currentColor = points.get(index).possible.get(colorIndex);
			points.get(index).setColor(currentColor);

			boolean notPossible = false;

			// Check constraints, so if any neighbor has the matching color
			for(Points neighbor : points.get(index).getNeighbors()){
				if(neighbor.getColor() == currentColor) {
					notPossible = true;
					break;
				}
			}

			// returns true if it made it to the end, false if the assignment didn't work
			boolean flag = colorMapWithoutFowardChecking(index + 1);

			if(!flag) continue;
			else return true;
		}

	}

	public void colorMapWithFowardChecking(){
		// Checks if we made it to an assignment for all, if so returns true
		if(index >= point.size()){
			return true;
		}

		// Checks if any points don't have any more remaining colors
		for(Point point : points){

			if(point.possible.size() == 0) return false;
		}

		// iterates through remaining colors for point
		for(int colorIndex = 0 ; colorIndex < points.get(index).possible.size() ; colorIndex++ ){

			// Gets color of remaining possible colors
			int currentColor = points.get(index).possible.get(colorIndex);
			points.get(index).setColor(currentColor);

			// Check constraints, so if any neighbor has the matching color
			for(Points neighbor : points.get(index).getNeighbors()){
				if(neighbor.getColor() == currentColor) {
					notPossible = true;
					break;
				}
			}

			// Removes the current color for any remaining values of neighbors (forward checking)
			for(Points neighbor : points.get(index).getNeighbors()){
				if(neighbor.possible.indexOf(currentColor) == -1) continue;
				else{
					neighbor.possible.remove(neighbor.possible.indexOf(currentColor));
				}

				
			}

			// returns true if it made it to the end, false if the assignment didn't work
			boolean flag = colorMapWithoutFowardChecking(index + 1);

			if(!flag) continue;
			else return true;
		}

		// If it makes it through all of them then no assignment worked, returns false;
		return false;


	}



	public static void main(String[] args){
		int numberOfPoints = 10;

		GraphColoring graph = new GraphColoring();
		graph.generatePoints(numberOfPoints);

		for(Points point : graph.points){
			System.out.println("Point:   x is " + point.getX() + "    and y is " + point.getY());
		}

		graph.connectPoints();

		for(Line2D line : graph.lines){
			System.out.println("Line: " + line.getP1() + " to " + line.getP2());
		}

		for(Points main : graph.points){
			for(Points neighbor : main.getNeighbors()){
				System.out.println(neighbor + " is neighbor of " + main);
			}
		}

		//boolean assignmnent = graph.colorMapWithoutFowardChecking(0);


		//boolean assignmnent = graph.colorMapWithFowardChecking(0);

	}
}