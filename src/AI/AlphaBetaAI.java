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

public class AlphaBetaAI implements AI {
	
	final private int depth;
	final private Random choice; 
	final private int modulus;
	
	public AlphaBetaAI(int ownDepth, int ownSeed, int ownModulus) {
		depth = ownDepth;
		choice = new Random(ownSeed);
		modulus = ownModulus;
	}
	
	public AlphaBetaAI(int ownDepth, int ownSeed) {
		depth = ownDepth;
		choice = new Random(ownSeed);
		modulus = 5;
	}
	
	public AlphaBetaAI(int ownDepth) {
		depth = ownDepth;
		choice = new Random();
		modulus = 5;
	}
	
	public Route getRoute(Game game) {
		Entry<Integer, Route> weightRoute = alphaBeta(depth, game, 500);
		return weightRoute.getValue();
	}
	
	public Entry<Integer, Route> alphaBeta(int ownDepth, Game ownGame, int target) {
		
		ArrayList<Route> destinations = ownGame.allDestinations();
		
		Entry<Integer, Route> answer = new Entry(-500, destinations.get(0));
		
		int i = 0;
		
		while ((i < destinations.size()) && (answer.getKey() <= target)) {
			try {
				Game sonGame = ownGame.clone();
			
				Position start = destinations.get(i).getStartPosition();
				Position dest = destinations.get(i).getDestPosition();
				
				sonGame.move(start, dest);
				
				int ownEstimation = -sonGame.estimation();
				
				if (ownEstimation > 500) {
					answer = new Entry(ownEstimation, new Route(start, dest)); 
				} else {
					if (ownDepth == 1) {	
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
						Entry<Integer, Route> candidate = alphaBeta(ownDepth - 1, sonGame, -answer.getKey());
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
					++i;
				}
			} catch(Exception e) {
				System.out.print(e.toString());
			}
		}
		
		return answer;
	}

    public boolean isAI() {
        return true;
    }
}