package chess;

public class Rook extends ChessFigure{

    @Override
    public boolean isKnight() {
        return false;
    }

    public Rook(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public String toString() {

        if (getColor().equals("white"))return "R";
        else return "r";
    }

    @Override
    public boolean can(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        if (isInBoard()) {
            return (dy == 0 && dx != 0) || (dx == 0 && dy != 0);
        }
        return false;
    }
}
