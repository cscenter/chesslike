package model.moves;

import model.coord.Entry;
import model.coord.Position;
import model.set.Board;
import model.set.Piece;
import model.set.Player;

import java.util.ArrayList;
import java.util.List;

public class Jump implements Move {

    private Position destination;
    private boolean capture;

    public Jump(Position destination, boolean capture) {
        this.destination = destination;
        this.capture = capture;
    }

    public List<Entry<Integer, Position>> getDestinations(Position start, Player.Orientation orientation, Board board) {
        List<Entry<Integer, Position>> destinations = new ArrayList<Entry<Integer, Position>>();

        int xDest, yDest;

        if (orientation == Player.Orientation.UP) {
            xDest = start.getX() + destination.getX();
            yDest = start.getY() + destination.getY();
        } else {
            xDest = start.getX() - destination.getX();
            yDest = start.getY() - destination.getY();
        }

        Piece piece = board.getPiece(xDest, yDest);
        if (
                (piece != null) && (
                        (piece.getPieceType() == null && capture == false) ||
                        (piece.getPieceType() != null && capture == true)
                )
        ) {
            destinations.add(new Entry<Integer, Position>(getId(), new Position(xDest, yDest)));
        }

        return destinations;
    }

    public boolean isCapture() {
        return capture;
    }

    public Move makeCapture() {
        return new Jump(destination, true);
    }

    public int getId() {
        return -1;
    }

}
