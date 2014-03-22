package move;

import board.Board;
import board.Position;
import player.Player;

import java.util.Vector;

public interface Move {

    public Vector<Position> getDestinations(Position start, Player.Orientation orientation, Board board);

}
