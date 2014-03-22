package board;

import java.util.Arrays;

public class Board {

    private int xSize;
    private int ySize;

    private int[][] field;

    public Board(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;

        field = new int[xSize][ySize];
        for (int[] row : field) {
            Arrays.fill(row, -1);
        }
    }

    public Board(Board board) {
        xSize = board.xSize;
        ySize = board.ySize;

        field = new int[xSize][ySize];
        for (int x = 0; x < ySize; x++) {
            for (int y = 0; y < ySize; y++) {
                field[x][y] = board.getField()[x][y];
            }
        }
    }

    public boolean addRectangle(int x, int y, int xsize, int ysize) {
        int xFar = x + xsize - 1;
        int yFar = y + ysize - 1;

        if (xSize <= 0 || ySize <= 0) {
            return false;
        } else if (x < 0 || x >= xSize || y < 0 || y >= ySize) {
            return false;
        } else if (xFar < 0 || xFar >= xSize || yFar < 0 || yFar >= ySize) {
            return false;
        } else {
            for (int i = x; i <= xFar; i++) {
                for (int j = y; j <= yFar; j++) {
                    field[i][j] = 0;
                }
            }

            return true;
        }
    }

    public boolean addSquare(int x, int y) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize) {
            return false;
        } else {
            field[x][y] = 0;
            return true;
        }
    }

    public boolean putPiece(int id, Position pos) {
        return putPiece(id, pos.getX(), pos.getY());
    }

    public boolean putPiece(int id, int x, int y) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize) {
            return false;
        } else if (field[x][y] == -1) {
            return false;
        } else {
            field[x][y] = id;
            return true;
        }
    }

    public int getPieceId(Position pos) {
        return getPieceId(pos.getX(), pos.getY());
    }

    public int getPieceId(int x, int y) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize) {
            return -1;
        } else {
            return field[x][y];
        }
    }

    public String toString() {
        String string = new String();

        string += " y\\x";
        for (int x = 0; x < field.length; x++) {
            string += String.format("  %-4s", x + 1);
        }
        string += "\n";

        for (int y = field[0].length - 1; y >=0 ; y--) {
            string += String.format(" %-2s ", y + 1);

            for (int x = 0; x < field.length; x++) {
                string += String.format("  %-4s", field[x][y]);
            }

            string += "\n";
        }

        return string;
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public int[][] getField() {
        return field;
    }

}