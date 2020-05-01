package chess;

public abstract class ChessFigure {

    private int x, y; // 0...7
    private final String color;
    public static final String black = "black";
    public static final String white = "white";
    public String getColor() {
        return color;
    }

    public abstract boolean isKnight();

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ChessFigure(int x, int y, String color) {
        this.x = x;
        this.y = y;
        if (!color.equals("black") && !color.equals("white")) {
            throw new RuntimeException("color should be black or white");
        }
        this.color = color;
    }

    protected boolean isInBoard() {
        return x >= 0 && y >= 0 && x < 8 && y < 8;
    }

    //геометрическая логика хода:
    public abstract boolean can(int x, int y);

}
