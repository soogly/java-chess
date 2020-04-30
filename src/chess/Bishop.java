package chess;

public class Bishop extends ChessFigure {
    @Override
    public boolean isKnight() {
        return false;
    }

    public Bishop(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public String toString() {
        if (getColor().equals("white"))return "B";
        else return "b";
    }

    @Override
    public boolean can(int x, int y) {
        int dx = x - getX();
        int dy = y - getY();
        if (isInBoard()) {
            return (Math.abs(dy) == Math.abs(dx));
        }
        return false;
    }

}
