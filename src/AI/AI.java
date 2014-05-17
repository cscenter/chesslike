package model;

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

public class AI {
	
	public Entry<Integer, Entry<Position, Position> > minimax(int depth, Game ownGame) {
			
		Entry<Integer, Entry<Position, Position> > answer = new Entry(-2000, new Entry(new Position(0, 0), new Position(0, 0)));
		
		ArrayList<Entry<Position, Position> > destinations = ownGame.allDestinations();
		
		for (int i = 0; i < destinations.size(); ++i) {
			try {
				Game sonGame = ownGame.clone();
			
				Position start = destinations.get(i).getKey();
				Position dest = destinations.get(i).getValue();
				sonGame.move(start, dest);
			
				if (depth == 1) {
					int ownEstimation = -sonGame.estimation();
					if (answer.getKey() < ownEstimation) {
						answer = new Entry(ownEstimation, new Entry(start, dest));
					} else {
						if (answer.getKey() == ownEstimation) {
							Random ownChoice = new Random();
							int modulus = 10;
							if (ownChoice.nextInt(modulus) == 0) {
								answer = new Entry(ownEstimation, new Entry(start, dest));
							}
						}
					}
				} else {
					Entry<Integer, Entry<Position, Position> > candidate = minimax(depth - 1, sonGame);
					if (answer.getKey() < candidate.getKey()) {
						answer = new Entry(-candidate.getKey(), candidate.getValue());
					} else {
						if (answer.getKey() == candidate.getKey()) {
							Random ownChoice = new Random();
							int modulus = 10;
							if (ownChoice.nextInt(modulus) == 0) {
								answer = new Entry(-candidate.getKey(), candidate.getValue());
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