package model.moves;

import model.coord.Entry;
import model.coord.Position;
import model.set.Board;
import model.set.Piece;
import model.set.Player;

import java.util.ArrayList;
import java.util.List;

public class SpecialMove {

    Position destination;
    Entry<Position, Piece> prey;
    Entry<Entry<Position, Position>, Piece> companion;
    List<Position> free;
    int id;

    public SpecialMove(
            Position destination,
            Entry<Position, Piece> prey,
            Entry<Entry<Position, Position>, Piece> companion,
            List<Position> free,
            int id
    ) {
        this.destination = destination;
        this.prey = prey;
        this.companion = companion;
        this.free = free;
        this.id = id;
    }

    public SpecialMove(
            Position destination,
            Entry<Position, Piece> prey,
            Entry<Entry<Position, Position>, Piece> companion,
            List<Position> free
    ) {
        this.destination = destination;
        this.prey = prey;
        this.companion = companion;
        this.free = free;
        this.id = -1;
    }

    public Entry<Integer, ArrayList<Entry<Position, Position>>> getArrangements(Position start, Player.Orientation orientation, Board board) {
        Entry<Integer, ArrayList<Entry<Position, Position>>> arrangements =
                new Entry<Integer, ArrayList<Entry<Position, Position>>>(
                        id, new ArrayList<Entry<Position, Position>>()
                );

        int sign;
        if (orientation == Player.Orientation.UP) {
            sign = 1;
        } else {
            sign = -1;
        }

        Position actualDestination = new Position(
                start.getX() + sign * destination.getX(),
                start.getY() + sign * destination.getY()
        );

        Piece self = board.getPiece(start);
        if (self == null || self.getPieceType() == null) {
            return arrangements;
        }

        Entry<Position, Piece> actualPrey = null;
        if (prey != null) {
            actualPrey = new Entry<Position, Piece>(
                    new Position(
                            start.getX() + sign * prey.getKey().getX(),
                            start.getY() + sign * prey.getKey().getY()
                    ),
                    prey.getValue()
            );

            Piece preyPiece = board.getPiece(actualPrey.getKey());
            if (
                    preyPiece == null ||
                            preyPiece.getPieceType() == null ||
                            self.getPlayer().equals(preyPiece.getPlayer()) ||
                            !preyPiece.equals(actualPrey.getValue())
                    ) {
                return arrangements;
            }
        }

        Entry<Entry<Position, Position>, Piece> actualCompanion = null;
        if (companion != null) {
            actualCompanion = new Entry<Entry<Position, Position>, Piece>(
                    new Entry<Position, Position>(
                            new Position(
                                    start.getX() + sign * companion.getKey().getKey().getX(),
                                    start.getY() + sign * companion.getKey().getKey().getY()
                            ),
                            new Position(
                                    start.getX() + sign * companion.getKey().getValue().getX(),
                                    start.getY() + sign * companion.getKey().getValue().getY()
                            )
                    ),
                    companion.getValue()
            );

            Piece companionPiece = board.getPiece(actualCompanion.getKey().getKey());
            if (
                    companionPiece == null ||
                            companionPiece.getPieceType() == null ||
                            !self.getPlayer().equals(companionPiece.getPlayer()) ||
                            !companionPiece.equals(actualCompanion.getValue())
                    ) {
                return arrangements;
            }
        }

        List<Position> actualFree = new ArrayList<Position>();
        for (Position position : free) {
            Position actualPosition = new Position(
                    start.getX() + sign * position.getX(),
                    start.getY() + sign * position.getY()
            );

            Piece noPiece = board.getPiece(actualPosition);
            if (noPiece == null || noPiece.getPieceType() != null) {
                return arrangements;
            }

            actualFree.add(actualPosition);
        }

        if (actualPrey != null) {
            arrangements.getValue().add(new Entry<Position, Position>(actualPrey.getKey(), null));
        } else {
            arrangements.getValue().add(new Entry<Position, Position>(null, null));
        }

        arrangements.getValue().add(new Entry<Position, Position>(start, actualDestination));

        if (actualCompanion != null) {
            arrangements.getValue().add(new Entry<Position, Position>(
                    actualCompanion.getKey().getKey(), actualCompanion.getKey().getValue())
            );
        }

        return arrangements;
    }

}
