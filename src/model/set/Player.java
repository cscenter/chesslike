package model.set;

import java.util.List;

public class Player {

    public static enum Orientation {
        UP,
        DOWN
    }

    public static enum Color {
        BLACK,
        WHITE
    }

    private int id;
    private Color color;
    private String name;
    private Orientation orientation;
    private List<Piece> pieces;

    public Player(int id, Color color, String name, Orientation orientation, List<Piece> pieces) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.orientation = orientation;
        this.pieces = pieces;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public static Player.Color getColor(String colorName) {
        colorName = colorName.toLowerCase();

        if (colorName.equals("black")) {
            return Player.Color.BLACK;
        } else if (colorName.equals("white")) {
            return Player.Color.WHITE;
        } else {
            return null;
        }
    }

    public static String getColorName(Player.Color color) {
        if (color.equals(Player.Color.BLACK)) {
            return "Black";
        } else if (color.equals(Player.Color.WHITE)) {
            return "White";
        } else {
            return null;
        }
    }

    public static String getColorShortName(Player.Color color) {
        if (color.equals(Player.Color.BLACK)) {
            return "B";
        } else if (color.equals(Player.Color.WHITE)) {
            return "W";
        } else {
            return null;
        }
    }

    public static Player.Orientation getOrientation(String orientationName) {
        orientationName = orientationName.toLowerCase();

        if (orientationName.equals("up")) {
            return Player.Orientation.UP;
        } else if (orientationName.equals("down")) {
            return Player.Orientation.DOWN;
        } else {
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public List<Piece> getPieceIds() {
        return pieces;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean equals(Object player) {
        if (player == null) {
            return false;
        } else if (player.getClass().equals(getClass())) {
            if (id == ((Player) player).getId()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
	
}
