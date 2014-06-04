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

public class RandomAI implements AI {
	
	final private Random randomChoice;
	
	public RandomAI(Integer ownSeed) {
		randomChoice = new Random(ownSeed);
	}
	
	public RandomAI() {
		randomChoice = new Random();
	}
	
	public Route getRoute(Game game) {
		ArrayList<Route> destinations = game.allDestinations();
		int alternativeNumber = destinations.size();
		int choice = randomChoice.nextInt(alternativeNumber);
		
		Route answer = new Route(destinations.get(choice).getStartPosition(), destinations.get(choice).getDestPosition());
		
		return answer;
	}

    public boolean isAI() {
        return true;
    }
}
