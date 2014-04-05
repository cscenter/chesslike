package model;

import model.coord.Entry;
import model.coord.Position;
import model.moves.Move;
import model.moves.SpecialMove;
import model.set.Board;
import model.set.Piece;
import model.set.PieceType;
import model.set.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;
    private List<PieceType> pieceTypes;
    private List<Player> players;

    private List<Player> turns;
    private Player currentPlayer;
    private int currentTurn;

    public Game(
            Board board,
            List<PieceType> pieceTypes,
            List<Player> players,
            List<Player> turns
    ) {
        this.board = board;
        this.pieceTypes = pieceTypes;
        this.players = players;
        this.turns = turns;

        currentTurn = 0;
        currentPlayer = turns.get(currentTurn);
    }

    public List<Position> getDestinations(int x, int y) {
        return getDestinations(new Position(x, y));
    }

    public List<Position> getDestinations(Position start) {
        List<Position> destinations = new ArrayList<Position>();

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

    public ArrayList<Entry<Integer, ArrayList<Entry<Position, Position>>>> getArrangements(int x, int y) {
        return getArrangements(new Position(x, y));
    }

    public ArrayList<Entry<Integer, ArrayList<Entry<Position, Position>>>> getArrangements(Position start) {
        ArrayList<Entry<Integer, ArrayList<Entry<Position, Position>>>> arrangements =
                new ArrayList<Entry<Integer, ArrayList<Entry<Position,Position>>>>();

        Piece piece = board.getPiece(start);
        if (piece == null || piece.getPieceType() == null) {
            return arrangements;
        }

        List<SpecialMove> specialMoves = piece.getPieceType().getSpecialMoves();
        for (SpecialMove specialMove : specialMoves) {
            arrangements.add(
                    (Entry<Integer, ArrayList<Entry<Position, Position>>>)
                            specialMove.getArrangements(start, piece.getPlayer().getOrientation(), board)
            );
        }

        return arrangements;
    }

    public boolean move (int x, int y, int xDest, int yDest) {
        return move(new Position(x, y), new Position(xDest, yDest));
    }

    public boolean move(Position start, Position dest) {
        Piece piece = board.getPiece(start);

        if (piece == null || piece.getPieceType() == null || !piece.getPlayer().equals(currentPlayer)) {
            return false;
        }

        List<Position> destinations = getDestinations(start);

        for (Position destination : destinations) {
            if (destination.equals(dest)) {
                piece.setLastMoveId(-1);

                board.putPiece(piece, dest);
                board.putPiece(Piece.FREE_SQUARE, start);

                nextPlayer();
                return true;
            }
        }

        ArrayList<Entry<Integer, ArrayList<Entry<Position, Position>>>> allArrangements = getArrangements(start);

        for (Entry<Integer, ArrayList<Entry<Position, Position>>> arrangements : allArrangements) {
            if (arrangements.getValue().size() != 0 && arrangements.getValue().get(1).getValue().equals(dest)) {
                for (Entry<Position, Position> arrangement : arrangements.getValue()) {
                    if (arrangement.getKey()!= null) {
                        if (arrangement.getValue() == null) {
                            board.putPiece(Piece.FREE_SQUARE, arrangement.getValue());
                        } else {
                            piece = board.getPiece(arrangement.getKey());
                            piece.setLastMoveId(arrangements.getKey());

                            board.putPiece(piece, arrangement.getValue());
                            board.putPiece(Piece.FREE_SQUARE, arrangement.getKey());
                        }
                    }
                }
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

        List<Position> destinations = getDestinations(x, y);
        for (Position destination : destinations) {
            if (board.getPiece(destination).getPieceType() == null) {
                s[destination.getX()][destination.getY()] = " O ";
            } else {
                s[destination.getX()][destination.getY()] = " X ";
            }
        }

        return s;
    }

    private void nextPlayer() {
        currentTurn += 1;
        currentTurn %= turns.size();

        currentPlayer = turns.get(currentTurn);
    }

    public Board getBoard() {
        return board;
    }

}
