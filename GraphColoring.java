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
	}

	public void connectPoints(){

		lines = new ArrayList<Line2D>();

		Random num = new Random();

		boolean stillAvailable = true;
		double lastNearest = 0.0;
		double tempNearest;
		Points other = points.get(0);
		Line2D line;
		int whileLoop = 0;
		int firstForloop = 0;

		while(stillAvailable){
			// Gets new point from list
			whileLoop++;

			stillAvailable = false;

			for(Points currentPoint : points){
				firstForloop++;

				//System.out.println("Current point: " + currentPoint);

				ArrayList<Points> skipList = new ArrayList<Points>();

				for(int i = 0 ; i < points.size() ; i++){

					double nearest = Math.sqrt(2);


					for(Points otherPoint : points){
						if(currentPoint.getX() == otherPoint.getX() && currentPoint.getY() == otherPoint.getY()) continue;
						if(skipList.contains(otherPoint)) continue;
						if(currentPoint.getNeighbors().contains(otherPoint)) {
							System.out.println(currentPoint + " already connected to " + other);
							continue;
						}

						tempNearest = Math.sqrt(((currentPoint.getX() - otherPoint.getX()) * (currentPoint.getX() - otherPoint.getX())) 
							+ ((currentPoint.getY() - otherPoint.getY()) * (currentPoint.getY() - otherPoint.getY())));

						if(tempNearest < nearest && tempNearest > lastNearest) {
							other = otherPoint;
							nearest = tempNearest;
						}
					}

					skipList.add(other);

					//System.out.println("Nearest: " + nearest);

					lastNearest = nearest;

					line = new Line2D.Double(currentPoint.getX() , currentPoint.getY() , other.getX() , other.getY());

					boolean intersects = false;

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

					if(!intersects){
						lines.add(line);
						other.addNeighbor(currentPoint);
						currentPoint.addNeighbor(other);

						stillAvailable = true;

						break;

					}
				}

				lastNearest = 0.0;



			}


		}
		System.out.println("Entered while loop " + whileLoop + " times and eneterd first for loop " + firstForloop + "times");
	}

	public void colorMapWithoutFowardChecking(){


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

		//graph.colorMapWithoutFowardChecking();

	}
}