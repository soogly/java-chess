package chess;

public class ChessBoard {

    private ChessFigure [][] board;

    protected boolean isInBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < 8 && y < 8;
    }

    public ChessBoard() {
        board = new ChessFigure[8][8];
        board[0][0] = new Rook(0,0, "white");
        board[0][1] = new Knight(0,1, "white");
        board[0][7] = new Rook(0,7, "white");
        board[0][6] = new Knight(0,6, "white");
        board[7][0] = new Rook(7,0, "black");
        board[7][7] = new Rook(7,7, "black");
        board[7][1] = new Knight(7,1, "black");
        board[7][6] = new Knight(7,6, "black");
        board[0][2] = new Bishop(0,2,"white");
        board[0][5] = new Bishop(0,5,"white");
        board[7][2] = new Bishop(7,2,"black");
        board[7][5] = new Bishop(7,5,"black");
    }

    public void move(int x1, int y1, int x2, int y2) {
        if (isInBoard(x1, y1) && board[x1][y1] != null &&
                board[x1][y1].can(x2, y2)) {
            ChessFigure figure = board[x1][y1];
            board[x2][y2] = figure;
            figure.setXY(x2,y2);
            board[x1][y1] = null;
        } else {
            System.out.println("Cannot move figure to " + x2 + " " + y2);
        }
    }

    @Override
    public String toString() {
        StringBuilder s =new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    s.append(" _");
                } else {
                    s.append(" ").append(board[i][j]);
                }
            }
            s.append('\n');
        }
        return s.toString();
    }
}
