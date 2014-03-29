package model;

import model.coord.Entry;
import model.coord.Position;
import model.moves.Move;
import model.set.Board;
import model.set.Piece;
import model.set.PieceType;
import model.set.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {

    private Board board;
    private List<PieceType> pieceTypes;
    private List<Player> players;

    private Map<Integer, Player> turns;
    private Player currentPlayer;
    private int currentTurn;

    public Game(
            Board board,
            List<PieceType> pieceTypes,
            List<Player> players,
            Map<Integer, Player> turns
    ) {
        this.board = board;
        this.pieceTypes = pieceTypes;
        this.players = players;
        this.turns = turns;

        currentTurn = 1;
        currentPlayer = turns.get(currentTurn);
    }

    public List<Entry<Integer, Position>> getDestinations(int x, int y) {
        return getDestinations(new Position(x, y));
    }

    public List<Entry<Integer, Position>> getDestinations(Position start) {
        List<Entry<Integer, Position>> destinations = new ArrayList<Entry<Integer, Position>>();

        Piece piece = board.getPiece(start);
        if (piece == null || piece.getPieceType() == null) {
            return destinations;
        }

        List<Move> moves = piece.getPieceType().getMoves();
        for (Move move : moves) {
            destinations.addAll(move.getDestinations(start, piece.getPlayer().getOrientation(), board));
        }

        return destinations;
    }

    public boolean move (int x, int y, int xDest, int yDest) {
        return move(new Position(x, y), new Position(xDest, yDest));
    }

    public boolean move(Position start, Position dest) {
        Piece piece = board.getPiece(start);

        if (piece == null || piece.getPieceType() == null || !piece.getPlayer().equals(currentPlayer)) {
            return false;
        }

        List<Entry<Integer, Position>> destinations = getDestinations(start);

        for (Entry<Integer, Position> destination : destinations) {
            Position position = destination.getValue();
            if (position.equals(dest)) {
                piece.setLastMoveId(destination.getKey());

                board.putPiece(piece, dest);
                board.putPiece(new Piece(), start);

                nextPlayer();
                return true;
            }
        }

        return false;
    }

    public String[][] print() {
        return print(board);
    }

    public String[][] print(Board board) {
        String[][] s = new String[board.getXSize()][board.getYSize()];

        for (int y = board.getYSize() - 1; y >= 0 ; y--) {
            for (int x = 0; x < board.getXSize(); x++) {
                Piece piece = board.getPiece(x, y);

                if (piece == null) {
                    s[x][y] = "---";
                } else if (piece.getPieceType() == null) {
                    s[x][y] = "   ";
                } else {
                    s[x][y] = Player.getColorShortName(piece.getPlayer().getColor())
                            + piece.getPieceType().getShortName();
                }
            }
        }

        return s;
    }

    public String[][] printDestinations(Position start) {
        return printDestinations(start.getX(), start.getY());
    }

    public String[][] printDestinations(int x, int y) {
        String[][] s = print();

        List<Entry<Integer, Position>> destinations = getDestinations(x, y);
        for (Entry<Integer, Position> destination : destinations) {
            Position position = destination.getValue();
            if (board.getPiece(position).getPieceType() == null) {
                s[position.getX()][position.getY()] = " O ";
            } else {
                s[position.getX()][position.getY()] = " X ";
            }
        }

        return s;
    }

    private void nextPlayer() {
        Player nextPlayer = turns.get(currentTurn + 1);
        if (nextPlayer == null) {
            currentTurn = 1;
            currentPlayer = turns.get(currentTurn);
        } else {
            currentTurn++;
            currentPlayer = nextPlayer;
        }
    }

    public Board getBoard() {
        return board;
    }

}
