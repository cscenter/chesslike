package model.set;

import model.moves.Move;
import model.moves.SpecialMove;

import java.util.ArrayList;
import java.util.List;

public class PieceType {

    private int id;
    private String name;
    private String shortName;
    private List<Move> moves;
    private List<SpecialMove> specialMoves;
    int weight;

    public PieceType(int id, String name, String shortName, List<Move> moves, int weight) {
		this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.moves = moves;
        this.specialMoves = new ArrayList<SpecialMove>();
        this.weight = weight;
    }

    public void addSpecialMoves(List<SpecialMove> specialMoves) {
        this.specialMoves.addAll(specialMoves);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public List<SpecialMove> getSpecialMoves() {
        return specialMoves;
    }

    public int getWeight() {
        return weight;
    }

    public boolean equals(PieceType type) {
        if (type == null) {
            return false;
        } else if (type.getClass().equals(getClass())) {
            if (id == type.getId()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
