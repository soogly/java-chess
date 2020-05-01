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

            return (dx == 0 &&  Math.abs(dy) <= 2);
        }
        return false;
    }
}
