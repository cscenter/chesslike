package model.moves;

import model.coord.Position;
import model.set.Board;
import model.set.Piece;
import model.set.Player;

import java.util.ArrayList;
import java.util.List;

public class Slide implements Move {

    private Position direction;
    private boolean capture;

    public Slide(Position direction, boolean capture) {
        this.direction = direction;
        this.capture = capture;
    }

    public List<Position> getDestinations(Position start, Player.Orientation orientation, Board board) {
        List< Position> destinations = new ArrayList<Position>();

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

        if (capture == false) {
            while (true) {
                xDest += xShift;
                yDest += yShift;

                Piece piece = board.getPiece(xDest, yDest);
                if (piece == null || piece.getPieceType() != null) {
                    break;
                } else {
                    destinations.add(new Position(xDest, yDest));
                }
            }
        } else {
            while (true) {
                xDest += xShift;
                yDest += yShift;

                Piece piece = board.getPiece(xDest, yDest);
                if (piece == null) {
                    break;
                } else if (piece.getPieceType() != null) {
                    if (!board.getPiece(start).getPlayer().equals(piece.getPlayer())) {
                        destinations.add(new Position(xDest, yDest));
                    }
                    break;
                } else {
                    continue;
                }
            }
        }

        return destinations;
    }

    public boolean isCapture() {
        return capture;
    }

    public Move makeCapture() {
        return new Slide(direction, true);
    }

}
