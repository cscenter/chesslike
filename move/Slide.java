package move;

import board.Board;
import board.Position;
import player.Player;

import java.util.Vector;

public class Slide implements Move {

    private Position direction;

    public Slide(Position direction) {
        this.direction = direction;
    }

    public Vector<Position> getDestinations(Position start, Player.Orientation orientation, Board board) {
        Vector<Position> destinations = new Vector<Position>();

        int xDest = start.getX();
        int yDest = start.getY();

        int xShift, yShift;
        if (orientation == Player.Orientation.UP) {
            xShift = direction.getX();
            yShift = direction.getY();
        } else {
            xShift = - direction.getX();
            yShift = - direction.getY();
        }

        while (true) {
            xDest += xShift;
            yDest += yShift;

            int pieceId = board.getPieceId(xDest, yDest);
            if (pieceId == 0) {
                destinations.add(new Position(xDest, yDest));
            } else if (pieceId == -2) {
                destinations = new Vector<Position>();
                destinations.add(new Position(xDest, yDest));
                break;
            } else {
                break;
            }
        }

        return destinations;
    }

}
