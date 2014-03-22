package player;

public class ParameterProcessor {

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

    public static Player.Orientation getDirection(String orientationName) {
        orientationName = orientationName.toLowerCase();

        if (orientationName.equals("up")) {
            return Player.Orientation.UP;
        } else if (orientationName.equals("down")) {
            return Player.Orientation.DOWN;
        } else {
            return null;
        }
    }

}
