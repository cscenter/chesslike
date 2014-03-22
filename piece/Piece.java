package piece;

import board.Position;

public class Piece {

    private int typeId;
    private int playerId;
    private Position position;
    private boolean moved;

    public Piece(int typeId, int playerId, Position position) {
        this.typeId = typeId;
        this.playerId = playerId;
        this.position = position;
        moved = false;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isMoved() {
        return moved;
    }

}
