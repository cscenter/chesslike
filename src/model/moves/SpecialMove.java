package model.moves;

import model.coord.Entry;
import model.coord.Position;
import model.set.Board;
import model.set.Piece;
import model.set.Player;

import java.util.ArrayList;
import java.util.List;

public class SpecialMove {

    public static class Arrangement {
        private Position start;
        private Position finish;

        public Arrangement(Position start, Position finish) {
            this.start = start;
            this.finish = finish;
        }

        public Position getStart() {
            return start;
        }

        public Position getFinish() {
            return finish;
        }

        public void setStart() {
            this.start = start;
        }

        public void setFinish() {
            this.finish = finish;
        }
    }

    private Integer idNeeded;
    private Position destination;
    private Entry<Position, Piece> prey;
    private Entry<Arrangement, Piece> companion;
    private List<Position> free;
    private int id;

    public SpecialMove(
            Integer idNeeded,
            Position destination,
            Entry<Position, Piece> prey,
            Entry<Arrangement, Piece> companion,
            List<Position> free,
            int id
    ) {
        this.idNeeded = idNeeded;
        this.destination = destination;
        this.prey = prey;
        this.companion = companion;
        this.free = free;
        this.id = id;
    }

    public SpecialMove(
            Integer idNeeded,
            Position destination,
            Entry<Position, Piece> prey,
            Entry<Arrangement, Piece> companion,
            List<Position> free
    ) {
        this.idNeeded = idNeeded;
        this.destination = destination;
        this.prey = prey;
        this.companion = companion;
        this.free = free;
        this.id = -1;
    }

    public Entry<Integer, ArrayList<Arrangement>> getArrangements(Position start, Player.Orientation orientation, Board board) {
        Entry<Integer, ArrayList<Arrangement>> arrangements =
                new Entry<Integer, ArrayList<Arrangement>>(
                        id, new ArrayList<Arrangement>()
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
        if (self == null || self.getPieceType() == null ||
                (idNeeded != null && !self.getLastMoveId().equals(idNeeded)))
        {
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

        Entry<Arrangement, Piece> actualCompanion = null;
        if (companion != null) {
            actualCompanion = new Entry<Arrangement, Piece>(
                    new Arrangement(
                            new Position(
                                    start.getX() + sign * companion.getKey().getStart().getX(),
                                    start.getY() + sign * companion.getKey().getStart().getY()
                            ),
                            new Position(
                                    start.getX() + sign * companion.getKey().getFinish().getX(),
                                    start.getY() + sign * companion.getKey().getFinish().getY()
                            )
                    ),
                    companion.getValue()
            );

            Piece companionPiece = board.getPiece(actualCompanion.getKey().getStart());
            if (
                    companionPiece == null ||
                            companionPiece.getPieceType() == null ||
                            !self.getPlayer().equals(companionPiece.getPlayer()) ||
                            !companionPiece.equals(actualCompanion.getValue())
                    ) {
                return arrangements;
            }
        }

        for (Position position : free) {
            Position actualPosition = new Position(
                    start.getX() + sign * position.getX(),
                    start.getY() + sign * position.getY()
            );

            Piece noPiece = board.getPiece(actualPosition);
            if (noPiece == null || noPiece.getPieceType() != null) {
                return arrangements;
            }
        }

        if (actualPrey != null) {
            arrangements.getValue().add(new Arrangement(actualPrey.getKey(), null));
        } else {
            arrangements.getValue().add(new Arrangement(null, null));
        }

        arrangements.getValue().add(new Arrangement(start, actualDestination));

        if (actualCompanion != null) {
            arrangements.getValue().add(new Arrangement(
                    actualCompanion.getKey().getStart(), actualCompanion.getKey().getFinish())
            );
        }

        return arrangements;
    }

}
