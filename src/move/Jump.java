package move;

import board.Board;
import board.Position;
import player.Player;

import java.util.Vector;

public class Jump implements Move {

    private Position destination;

    public Jump(Position destination) {
        this.destination = destination;
    }

    public Vector<Position> getDestinations(Position start, Player.Orientation orientation, Board board) {
        Vector<Position> destinations = new Vector<Position>();

        int xDest, yDest;

        if (orientation == Player.Orientation.UP) {
            xDest = start.getX() + destination.getX();
            yDest = start.getY() + destination.getY();
        } else {
            xDest = start.getX() - destination.getX();
            yDest = start.getY() - destination.getY();
        }

        int pieceId = board.getPieceId(xDest, yDest);
        if (pieceId == 0 || pieceId == -2) {
            destinations.add(new Position(xDest, yDest));
        }

        return destinations;
    }

}
