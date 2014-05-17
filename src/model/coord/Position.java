package model.coord;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
	
	public Position() {
		this.x = 0;
		this.y = 0;
	}

    public boolean equals(Object position) {
        if (position == null) {
            return false;
        } else if (position.getClass().equals(getClass())) {
            if (((Position) position).getX() == getX() &&
                    ((Position) position).getY() == getY()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

	public String toString() {
		String string = x + " " + y;
		return string;
	}	
	
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
