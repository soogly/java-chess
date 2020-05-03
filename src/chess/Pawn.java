package chess;

public class Pawn extends ChessFigure {
    @Override
    public boolean isKnight() {
        return false;
    }

    public Pawn(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public String toString() {

        if (getColor().equals("white"))return "P";
        else return "p";
    }

    @Override
    public boolean can(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        if (isInBoard()) {
            if(this.getColor().equals(ChessFigure.white)) {
                if (getY() == 1) {
                    return (dx == 0 && dy <= 2 && dy >= 1);
                } else {
                    return (dx == 0 && dy == 1);
                }
            }
            if(this.getColor().equals(ChessFigure.black)) {
                if(getY() == 6 ){
                    return (dx == 0 &&  dy >= -2 && dy <= -1);
                } else {
                    return (dx == 0 && dy == -1);
                }
            }
        }
        return false;
    }
}
