package game;

import board.Board;
import move.Move;
import piece.Piece;
import piece.PieceType;
import player.Player;
import player.ParameterProcessor;
import board.Position;

import java.util.Map;
import java.util.Vector;

public class Game {

    private Board board;
    private Map<Integer, PieceType> pieceTypes;
    private Map<Integer, Player> players;
    private Map<Integer, Piece> pieces;

    private Map<Integer, Integer> turns;
    private int currentId;
    private int currentTurn;

    public Game(
            Board board,
            Map<Integer, PieceType> pieceTypes,
            Map<Integer, Player> players,
            Map<Integer, Piece> pieces,
            Map<Integer, Integer> turns
    ) {
        this.board = board;
        this.pieceTypes = pieceTypes;
        this.players = players;
        this.pieces = pieces;
        this.turns = turns;

        currentTurn = 1;
        currentId = turns.get(currentTurn);
    }

    public Vector<Position> getDestinations(int x, int y) {
        return getDestinations(new Position(x, y));
    }

    public Vector<Position> getDestinations(Position start) {
        Vector<Position> destinations = new Vector<Position>();

        int pieceId = board.getPieceId(start);
        if (pieceId <= 0) {
            return destinations;
        }

        Piece piece = pieces.get(pieceId);
        PieceType pieceType = pieceTypes.get(piece.getTypeId());
        Player player = players.get(piece.getPlayerId());
        Vector<Move> moves = pieceType.getMoves();

        for (Move move : moves) {
            destinations.addAll(move.getDestinations(start, player.getOrientation(), board));
        }

        return destinations;
    }

    public boolean move (int x, int y, int xDest, int yDest) {
        return move(new Position(x, y), new Position(xDest, yDest));
    }

    public boolean move(Position start, Position dest) {
        if (pieces.get(board.getPieceId(start)).getPlayerId() != currentId) {
            return false;
        }

        Vector<Position> destinations = getDestinations(start);

        for (Position destination : destinations) {
            if (destination.equals(dest)) {
                board.putPiece(board.getPieceId(start), dest);
                board.putPiece(0, start);

                nextPlayer();
                return true;
            }
        }

        return false;
    }

    public void print() {
        print(board);
        System.out.println(players.get(currentId).getName());
    }

    public void print(Board board) {
        int[][] field = board.getField();

        System.out.print(" y\\x");
        for (int x = 0; x < field.length; x++) {
            System.out.print(String.format("  %-4s", x + 1));
        }
        System.out.print("\n");

        for (int y = field[0].length - 1; y >= 0 ; y--) {
            System.out.print(String.format(" %-2s ", y + 1));

            for (int x = 0; x < field.length; x++) {
                Piece piece = pieces.get(field[x][y]);

                if (piece == null) {
                    if (field[x][y] == -1) {
                        System.out.print("      ");
                    } else if (field[x][y] == -2) {
                        System.out.print("| O | ");
                    } else if (field[x][y] == -3) {
                        System.out.print("| X | ");
                    } else {
                        System.out.print("|   | ");
                    }
                } else {
                    PieceType pieceType = pieceTypes.get(piece.getTypeId());
                    Player player = players.get(piece.getPlayerId());

                    System.out.print(
                            "|"
                            + ParameterProcessor.getColorShortName(player.getColor())
                            + pieceType.getShortName()
                            + "| "
                    );
                }
            }

            System.out.print("\n");
        }
    }

    public void printDestinations(int x, int y) {
        if (board.getPieceId(x, y) <= 0) {
            return;
        }

        Piece piece = pieces.get(board.getPieceId(x, y));

        System.out.println(
                ParameterProcessor.getColorName(
                        players.get(piece.getPlayerId()).getColor()
                ) + " " + pieceTypes.get(piece.getTypeId()).getName()
                + " at " + (x + 1) + "x" + (y + 1)
        );

        Vector<Position> destinations = getDestinations(x, y);

        Board possible = new Board(board);

        for (Position destination : destinations) {
            if (possible.getPieceId(destination) == 0) {
                possible.putPiece(-2, destination.getX(), destination.getY());
            } else if (possible.getPieceId(destination) > 0) {
                possible.putPiece(-3, destination.getX(), destination.getY());
            }
        }

        print(possible);
    }

    public void nextPlayer() {
        Integer nextId = turns.get(currentTurn + 1);
        if (nextId == null) {
            currentTurn = 1;
            currentId = turns.get(currentTurn);
        } else {
            currentTurn++;
            currentId = nextId;
        }
    }

    public Board getBoard() {
        return board;
    }

}
