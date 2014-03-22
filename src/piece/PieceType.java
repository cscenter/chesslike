package piece;

import move.Move;

import java.util.Vector;

public class PieceType {

    private String name;
    private String shortName;
    private Vector<Move> moves;

    public PieceType(String name, String shortName, Vector<Move> moves) {
        this.name = name;
        this.shortName = shortName;
        this.moves = moves;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public Vector<Move> getMoves() {
        return moves;
    }

}
