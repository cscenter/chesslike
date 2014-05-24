package model.set;

import model.coord.Position;

import java.util.Arrays;

public class Board implements Cloneable{

    private int xSize;
    private int ySize;

    private Piece[][] field;

    public Board(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;

        field = new Piece[xSize][ySize];
        for (Piece[] row : field) {
            Arrays.fill(row, null);
        }
    }

    public Board(Board board) {
        xSize = board.xSize;
        ySize = board.ySize;

        field = new Piece[xSize][ySize];
        for (int x = 0; x < ySize; x++) {
            for (int y = 0; y < ySize; y++) {
                field[x][y] = board.getPiece(x, y);
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
                    field[i][j] = Piece.FREE_SQUARE;
                }
            }

            return true;
        }
    }

    public boolean addSquare(int x, int y) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize) {
            return false;
        } else {
            field[x][y] = Piece.FREE_SQUARE;
            return true;
        }
    }

    public boolean putPiece(Piece piece, Position pos) {
        return putPiece(piece, pos.getX(), pos.getY());
    }

    public boolean putPiece(Piece piece, int x, int y) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize) {
            return false;
        } else if (field[x][y] == null) {
            return false;
        } else if (piece == null) {
            return false;
        } else {
            field[x][y] = piece;
            return true;
        }
    }

    public Piece getPiece(Position pos) {
        return getPiece(pos.getX(), pos.getY());
    }

    public Piece getPiece(int x, int y) {
        if (x < 0 || x >= xSize || y < 0 || y >= ySize) {
            return null;
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
                string += String.format("  %-4s", field[x][y].getPieceType().getShortName());
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
	
	public boolean equals(Board compareBoard) {
        if (compareBoard == null) {
            return false;
        } else if (compareBoard.getClass().equals(getClass())) {
			if ((compareBoard.getXSize() == getXSize()) && (compareBoard.getYSize() == getYSize())) {
				boolean flag = true;
				int x = 0;
				while ((x < xSize) && (flag == true)) {
					int y = 0;
					while ((y < ySize) && (flag == true)) {
						if (field[x][y] != null) {
							if (field[x][y].equals(compareBoard.getPiece(x, y))) {
								++y;
							} else {
								flag = false;
							}
						} else {
							++y;
						}
					}
					++x;
				}
				if (flag == true) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Board clone() throws CloneNotSupportedException {
        Board newBoard = (Board)super.clone();
		newBoard.field = new Piece[xSize][ySize];
		for (int x = 0; x < xSize; ++ x) {
			for (int y = 0; y < ySize; ++y) {
				newBoard.field[x][y] = field[x][y].clone();
			}
		}
		return newBoard;
    }
	
}