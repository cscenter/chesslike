package model.moves;

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

    public List<Position> getDestinations(Position start, Player.Orientation orientation, Board board) {
        List<Position> destinations = new ArrayList<Position>();

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
                piece != null && (
                        (piece.getPieceType() == null &&
                                capture == false) ||
                                (piece.getPieceType() != null &&
                                        !piece.getPlayer().equals(board.getPiece(start).getPlayer())
                                        && capture == true)
                )
                ) {
            destinations.add(new Position(xDest, yDest));
        }

        return destinations;
    }

    public boolean isCapture() {
        return capture;
    }

    public Move makeCapture() {
        return new Jump(destination, true);
    }

}
