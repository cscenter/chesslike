package model.moves;

import model.coord.Position;
import model.set.Board;
import model.set.Player;

import java.util.List;

public interface Move {

    public List<Position> getDestinations(Position start, Player.Orientation orientation, Board board);
    public boolean isCapture();
    public Move makeCapture();

}
