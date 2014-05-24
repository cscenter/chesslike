package model;

import model.coord.Route;
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
import java.util.Iterator;

public class Game implements Cloneable {

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

    public ArrayList<Entry<Integer, ArrayList<SpecialMove.Arrangement>>> getArrangements(int x, int y) {
        return getArrangements(new Position(x, y));
    }

    public ArrayList<Entry<Integer, ArrayList<SpecialMove.Arrangement>>> getArrangements(Position start) {
        ArrayList<Entry<Integer, ArrayList<SpecialMove.Arrangement>>> arrangements =
                new ArrayList<Entry<Integer, ArrayList<SpecialMove.Arrangement>>>();

        Piece piece = board.getPiece(start);
        if (piece == null || piece.getPieceType() == null) {
            return arrangements;
        }

        List<SpecialMove> specialMoves = piece.getPieceType().getSpecialMoves();
        for (SpecialMove specialMove : specialMoves) {
            arrangements.add(
                    specialMove.getArrangements(start, piece.getPlayer().getOrientation(), board)
            );
        }

        return arrangements;
    }

    public boolean move (int x, int y, int xDest, int yDest) {
        return move(new Position(x, y), new Position(xDest, yDest));
    }

    public boolean move(Position start, Position dest) {
        boolean valid = false;

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

                valid = true;
                break;
            }
        }

        ArrayList<Entry<Integer, ArrayList<SpecialMove.Arrangement>>> allArrangements = getArrangements(start);

        for (Entry<Integer, ArrayList<SpecialMove.Arrangement>> block : allArrangements) {
            if (block.getValue() != null
                    && block.getValue().size() != 0
                    && block.getValue().get(1) != null
                    && block.getValue().get(1).getFinish()!= null
                    && block.getValue().get(1).getFinish().equals(dest)) {
                for (SpecialMove.Arrangement arrangement : block.getValue()) {
                    if (arrangement.getStart()!= null) {
                        if (arrangement.getFinish() == null) {
                            board.putPiece(Piece.FREE_SQUARE, arrangement.getStart());
                        } else {
                            piece = board.getPiece(arrangement.getStart());
                            piece.setLastMoveId(block.getKey());

                            board.putPiece(piece, arrangement.getFinish());
                            board.putPiece(Piece.FREE_SQUARE, arrangement.getStart());

                            valid = true;
                        }
                    }
                }
            }
        }

        if (valid) {
            nextPlayer();
            return true;
        } else {
            return false;
        }
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

        ArrayList<Entry<Integer, ArrayList<SpecialMove.Arrangement>>> blocks = getArrangements(x, y);
        for (Entry<Integer, ArrayList<SpecialMove.Arrangement>> block : blocks) {

            ArrayList<SpecialMove.Arrangement> arrangements = block.getValue();
            if (arrangements == null || arrangements.size() < 2) {
                continue;
            }

            if(arrangements.get(0) != null && arrangements.get(0).getStart() != null) {
                Position preyPosition = block.getValue().get(0).getStart();
                s[preyPosition.getX()][preyPosition.getY()] = " X ";
            }

            if(block.getValue().get(1) != null) {
                Position destination = block.getValue().get(1).getFinish();
                s[destination.getX()][destination.getY()] = " O ";
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

	public List<PieceType> getPieceTypes() {
		return pieceTypes;
	}
	
	public List<Player> getTurns() {
		return turns;
	}
	
	public int getCurrentTurn() {
		return currentTurn;
	}
	
	public PieceType getPieceTypeFromId(int id) {
		Iterator<PieceType> it = pieceTypes.iterator();
		PieceType ownType = it.next();
		while ((id != ownType.getId()) && (it.hasNext())) {
			ownType = it.next();
		}
		if (id == ownType.getId()) {
			return ownType;
		} else {
			return null;
		}
	}
	
	public Player getPlayerFromId(int id) {
		Iterator<Player> it = turns.iterator();
		Player ownPlayer = it.next();
		while ((id != ownPlayer.getId()) && (it.hasNext())) {
			ownPlayer = it.next();
		}
		if (id == ownPlayer.getId()) {
			return ownPlayer;
		} else {
			return null;
		}
	}
	
	public void forcedMove(int [][] newField, int [][] playersId, int [][] specialMovesId, int ownTurn) {
		int xSize = newField.length;
		int ySize = newField[0].length;
		Board newBoard = new Board(xSize, ySize);
		newBoard.addRectangle(0, 0, xSize, ySize);
		//
		for (int x = 0; x < ySize; x++) {
            for (int y = ySize - 1; y > -1; y--) {
                if (newField[x][y] != 0) {
					int id = newField[x][y];
					int playerId = playersId[x][y];
					Piece ownPiece =  new Piece(this.getPieceTypeFromId(id), this.getPlayerFromId(playerId), specialMovesId[x][y]);
					newBoard.putPiece(ownPiece, x, y);
				}
            }
        }
		board = newBoard;
		currentTurn = ownTurn;
        currentTurn %= turns.size();
        currentPlayer = turns.get(currentTurn);
	}
	
	public void forcedTurn() {
		nextPlayer();
	}
	
	public ArrayList<Route> allDestinations() {
		ArrayList<Route> destinations = new ArrayList<Route>();
		
		int xSize = board.getXSize();
		int ySize = board.getYSize();
		
 		for (int x = 0; x < xSize; ++x) {
			for (int y = 0; y < ySize; ++y) {
				Piece ownPiece = board.getPiece(x, y);
				Position startPosition = new Position(x, y);
				if (currentPlayer.equals(ownPiece.getPlayer())) {
					List<Position> ownDestinations = getDestinations(x, y);
					Iterator<Position> it = ownDestinations.iterator();
					while (it.hasNext()) {
						Position destPosition = it.next();
						destinations.add(new Route(startPosition, destPosition));
					}
				}
			}
		}
		
		return destinations;
	}	
	
	public int estimation() {
		int xSize = board.getXSize();
		int ySize = board.getYSize();
		
		int ownEstimation = 0;
		
		for (int x = 0; x < xSize; ++x) {
			for (int y = 0; y < ySize; ++y) {
				Player ownPlayer = board.getPiece(x, y).getPlayer();
				PieceType ownPieceType = board.getPiece(x, y).getPieceType();
				if (ownPieceType != null) {
					if (currentPlayer.equals(ownPlayer)) {
						ownEstimation = ownEstimation + ownPieceType.getWeight();
					} else {
						ownEstimation = ownEstimation - ownPieceType.getWeight();
					}
				}
			}
		}
		return ownEstimation;
	}
	
	public Entry<Boolean, Player> endOfGame() {
		Integer ownEstimation = estimation();
		if (ownEstimation < -500) {
			Iterator<Player> it = turns.iterator();
			Player ownPlayer = it.next();
			if (ownPlayer == currentPlayer) {
				return new Entry<Boolean, Player>((Boolean)true, it.next());
			} else {
				return new Entry<Boolean, Player>((Boolean)true, ownPlayer);
			}
		} else {
			return new Entry<Boolean, Player>((Boolean)false, currentPlayer);
		}
	}
	
	public Game clone() throws CloneNotSupportedException {
		Game newGame = (Game)super.clone();
		newGame.board = (Board)board.clone();
		return newGame;
    }
}
