package model.coord;

import model.coord.Position;

public class Route {

	private Position startPosition;
	private Position destPosition;
	
	public Route (Position start, Position dest) {
		startPosition = start;
		destPosition = dest;
	}
	
	public Position getStartPosition() {
		return startPosition;
	}
	
	public Position getDestPosition() {
		return destPosition;
	}
}
		