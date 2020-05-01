package chess;

public class Queen extends ChessFigure{
    @Override
    public boolean isKnight() {
        return false;
    }

    public Queen(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public String toString() {

        if (getColor().equals("white"))return "Q";
        else return "q";
    }

    @Override
    public boolean can(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        if (isInBoard()) {
            return (dy == 0 && dx != 0) || (dx == 0 && dy != 0) || (Math.abs(dy) == Math.abs(dx));
        }
        return false;
    }
}
