package model.set;

public class Piece {

    private PieceType pieceType;
    private Player player;
    private Integer lastMoveId;

    public Piece(PieceType pieceType, Player player, Integer lastMovedId) {
        this.pieceType = pieceType;
        this.player = player;
        this.lastMoveId = lastMovedId;
    }

    public Piece(PieceType pieceType, Player player) {
        this.pieceType = pieceType;
        this.player = player;
        lastMoveId = 0;
    }

    public Piece() {
        this.pieceType = null;
        this.player = null;
        lastMoveId = 0;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getLastMoveId() {
        return lastMoveId;
    }

    public void setLastMoveId(int lastMoveId) {
        this.lastMoveId = lastMoveId;
    }

    public boolean equals(Piece piece) {
        if (piece == null) {
            return false;
        } else if (
                (piece.getPieceType() == null || piece.getPieceType().equals(pieceType)) &&
                (piece.getPlayer() == null || piece.getPlayer().equals(player)) &&
                (piece.getLastMoveId() == null || piece.getLastMoveId().equals(lastMoveId))
        ) {
            return true;
        } else {
            return false;
        }
    }

}
