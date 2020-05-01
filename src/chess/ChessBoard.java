package chess;

public class ChessBoard {

    public ChessFigure [][] cells;
    public String currentUser = "white";
    boolean shah = isShah();

    King kingWhite = new King(0,4, ChessFigure.white);
    King kingBlack = new King(7,4, ChessFigure.black);

    protected boolean isInBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < 8 && y < 8;
    }

    public ChessBoard() {
        cells = new ChessFigure[8][8];
        cells[0][4] = kingWhite;
        cells[7][4] = kingBlack;
        cells[0][0] = new Rook(0,0, ChessFigure.white);
        cells[0][1] = new Knight(0,1, ChessFigure.white);
        cells[0][7] = new Rook(0,7, ChessFigure.white);
        cells[0][6] = new Knight(0,6, ChessFigure.white);
        cells[7][0] = new Rook(7,0, ChessFigure.black);
        cells[7][7] = new Rook(7,7, ChessFigure.black);
        cells[7][1] = new Knight(7,1, ChessFigure.black);
        cells[7][6] = new Knight(7,6, ChessFigure.black);
        cells[0][2] = new Bishop(0,2, ChessFigure.white);
        cells[0][5] = new Bishop(0,5, ChessFigure.white);
        cells[7][2] = new Bishop(7,2, ChessFigure.black);
        cells[7][5] = new Bishop(7,5, ChessFigure.black);
    }

    public boolean checkMate(){
        if (!shah){
            return false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j] != null){
                    ChessFigure figure = cells[i][j];
                    if (!currentUser.equals(figure.getColor())){
                        continue;
                    }
                    // check all possible moves of figure that can avoid check
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (figure.can(k, l) && isMoveLegal(i, j, k, l)){
                                boolean shahBack = shah;
                                safeMove(i, j, k, l, true);
                                if (shah){
                                    continue;
                                }
                                // if shah is canceled
                                shah = shahBack;
                                // that mean that is not checkmate
                                return false;
                            };
                        }
                    }
                }
            }
        }
        // Checkmate!
        return true;
    }

    public boolean isMoveLegal(int x1, int y1, int x2, int y2) {
        if (isInBoard(x1, y1) && cells[x1][y1] != null && cells[x1][y1].can(x2, y2)) {

            ChessFigure movingFigure = cells[x1][y1];

            if (cellIsEmpty(x2, y2)){
              if (movingFigure.isKnight()){
                return true;
              } else {
                  return movingFigure.isPathClear(x2, y2);
              }

            // check that figure in target cell is enemy

            } else if (!currentUser.equals(cells[x2][y2].getColor())){
                if(movingFigure instanceof Pawn) {
                    int dx = x2 - x1;
                    int dy = y2 - y1;
                    if(movingFigure.getColor().equals(ChessFigure.white)) {
                        if (dy == 1 && Math.abs(dx) == 1) {
                            return true;
                        } else {
                            System.out.println("Cannot move movingFigure to " + x2 + " " + y2);
                            return false;
                        }
                    } else if (movingFigure.getColor().equals(ChessFigure.black){
                        if (dy == -1 && Math.abs(dx) == 1) {
                            return true;
                        } else {
                            System.out.println("Cannot move movingFigure to " + x2 + " " + y2);
                            return false;
                        }
                    }
                }
                if (movingFigure.isKnight()){
                    return true;
                } else {
                    return movingFigure.isPathClear(x2, y2);
                }
            }

        } else {
            System.out.println("Cannot move figure to " + x2 + " " + y2);
            return false;
        }
    }

    public boolean cellIsEmpty(int x2, int y2){
        return cells[x2][y2] == null;
    }

    public boolean isShah(){
        King currentKing = currentUser.equals(ChessFigure.white) ? kingWhite : kingBlack;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j] != null){
                    if (currentUser.equals(cells[i][j].getColor())){
                        continue;
                    }
                    cells[i][j].can(currentKing.getX(), currentKing.getY());
                    if (cells[i][j].isKnight()) {
                        System.out.println("Ш А Х");
                        return true;
                    }
                    if (cells[i][j].isPathClear(currentKing.getX(), currentKing.getY())){
                        System.out.println("Ш А Х");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void move(int x1, int y1, int x2, int y2) {
        ChessFigure figure = cells[x1][y1];

        cells[x2][y2] = figure;
        figure.setXY(x2, y2);
        cells[x1][y1] = null;
    }

    public void safeMove(int x1, int y1, int x2, int y2, boolean virtual){

        if (isMoveLegal(x1, y1, x2, y2)) {
            ChessFigure tempFig = null;
            if (!cellIsEmpty(x2, y2)){
                tempFig = cells[x2][y2];
            }
            move(x1, y1, x2, y2);
//            shah = virtual ? shah : isShah();
            shah = isShah();
            if (shah || virtual){
                move(x2, y2, x1, y1);
                if (!(tempFig == null)){
                    cells[x2][y2] = tempFig;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s =new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j] == null) {
                    s.append(" _");
                } else {
                    s.append(" ").append(cells[i][j]);
                }
            }
            s.append('\n');
        }
        return s.toString();
    }
}
