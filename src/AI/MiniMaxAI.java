package AI;

import model.Game;
import IO.RulesParser;
import IO.TextViewer;

import model.coord.Route;
import model.coord.Entry;
import model.coord.Position;
import model.moves.Move;
import model.moves.SpecialMove;
import model.set.Board;
import model.set.Piece;
import model.set.PieceType;
import model.set.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class MiniMaxAI implements AI {
	
	final private int depth;
	final private Random choice; 
	
	public MiniMaxAI(int ownDepth, int ownSeed) {
		depth = ownDepth;
		choice = new Random(ownSeed);
	}
	
	public MiniMaxAI(int ownDepth) {
		depth = ownDepth;
		choice = new Random();
	}
	
	public Route getRoute(Game game) {
		Entry<Integer, Route> weightRoute = miniMax(depth, game);
		return weightRoute.getValue();
	}
	
	public Entry<Integer, Route> miniMax(int ownDepth, Game ownGame) {
			
		Entry<Integer, Route> answer = new Entry(-2000, new Route(new Position(0, 0), new Position(0, 0)));
		
		ArrayList<Route> destinations = ownGame.allDestinations();
		
		for (int i = 0; i < destinations.size(); ++i) {
			try {
				Game sonGame = ownGame.clone();
			
				Position start = destinations.get(i).getStartPosition();
				Position dest = destinations.get(i).getDestPosition();
				
				sonGame.move(start, dest);
				int modulus = 2;
				if (ownDepth == 1) {
					int ownEstimation = -sonGame.estimation();
					if (answer.getKey() < ownEstimation) {
						answer = new Entry(ownEstimation, new Route(start, dest));
					} else {
						if (answer.getKey() == ownEstimation) {
							if (choice.nextInt(modulus) == 0) {
								answer = new Entry(ownEstimation, new Route(start, dest));
							}
						}
					}
				} else {
					Entry<Integer, Route> candidate = miniMax(ownDepth - 1, sonGame);
					if (answer.getKey() < -candidate.getKey()) {
						answer = new Entry(-candidate.getKey(), new Route(start, dest));
					} else {
						if (answer.getKey() == -candidate.getKey()) {
							if (choice.nextInt(modulus) == 0) {
								answer = new Entry(-candidate.getKey(), new Route(start, dest));
							}
						}
					}
				}
			} catch(Exception e) {
				System.out.print(e.toString());
			}
		}
		
		return answer;
	}
}