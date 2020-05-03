package chess;

public class King extends ChessFigure{
    @Override
    public boolean isKnight() {
        return false;
    }

    public King(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public String toString() {

        if (getColor().equals("white"))return "I";
        else return "i";
    }

    @Override
    public boolean can(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        if (isInBoard()) {
            if (Math.abs(dx) == 1) {
                return (Math.abs(dy) == 1 || dy == 0);
            }
            if (Math.abs(dy) == 1) {
                return (Math.abs(dx) == 1 || dx == 0);
            }
        }
        return false;
    }
}
