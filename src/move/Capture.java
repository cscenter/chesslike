package move;

import board.Board;
import board.Position;
import player.Player;

import java.util.Vector;

public class Capture implements Move {

    Move move;

    public Capture(Move move) {
        this.move = move;
    }

    public Vector<Position> getDestinations(Position start, Player.Orientation orientation, Board board) {
        Board newBoard = new Board(board);
        int[][] field = newBoard.getField();

        for (int x = 0; x < board.getXSize(); x++) {
            for (int y = 0; y < board.getYSize(); y++) {
                if (field[x][y] > 0) {
                    field[x][y] = -2;
                }
            }
        }

        return move.getDestinations(start, orientation, newBoard);
    }

}