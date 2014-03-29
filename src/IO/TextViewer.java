package IO;

public class TextViewer {

    public static String print(String[][] board, String note) {
        String s = new String();

        s += " y\\x";
        for (int x = 0; x < board.length; x++) {
            s += String.format("  %-4s", x + 1);
        }
        s += "\n";

        for (int y = board[0].length - 1; y >= 0 ; y--) {
            s += String.format(" %-2s ", y + 1);

            for (int x = 0; x < board.length; x++) {
                s += String.format("|%-3s| ", board[x][y]);
            }

            s += "\n";
        }

        s += note;

        return s;
    }

}
