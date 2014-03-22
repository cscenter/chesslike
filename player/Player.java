package player;

import java.util.Vector;

public class Player {

    public static enum Orientation {
        UP,
        DOWN
    }

    public static enum Color {
        BLACK,
        WHITE
    }

    private Color color;
    private String name;
    private Orientation orientation;
    private Vector<Integer> pieceIds;

    public Player(Color color, String name, Orientation orientation, Vector<Integer> pieceIds) {
        this.color = color;
        this.name = name;
        this.orientation = orientation;
        this.pieceIds = pieceIds;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Vector<Integer> getPieceIds() {
        return pieceIds;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
