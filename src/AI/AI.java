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

public interface AI {

	public Route getRoute(Game game);
	
}