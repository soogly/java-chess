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
                    if (!currentUser.equals(cells[i][j].getColor())){
                        continue;
                    }

                    ChessFigure protectorFigure = cells[i][j];

                    if (protectorFigure instanceof Pawn) {
                        if (ChessFigure.white.equals(protectorFigure.getColor())) {
                            ChessFigure enemy = cells[protectorFigure.getY() + 1][protectorFigure.getX() - 1];
                            if (enemy != null && enemy.getColor().equals(ChessFigure.black)) {
                                if (safeMove(protectorFigure.getY(), protectorFigure.getX(),
                                        protectorFigure.getY() + 1, protectorFigure.getX() - 1, true)) {
                                    continue;
                                }
                                return false;
                            }
                            enemy = cells[protectorFigure.getY() + 1][protectorFigure.getX() + 1];
                            if (enemy != null && enemy.getColor().equals(ChessFigure.black)) {
                                if (safeMove(protectorFigure.getY(), protectorFigure.getX(),
                                        protectorFigure.getY() + 1, protectorFigure.getX() + 1, true)) {
                                    continue;
                                }
                                return false;
                            }
                        } else if (ChessFigure.black.equals(protectorFigure.getColor())) {
                            ChessFigure enemy = cells[protectorFigure.getY() - 1][protectorFigure.getX() - 1];
                            if (enemy != null && enemy.getColor().equals(ChessFigure.black)) {
                                if (safeMove(protectorFigure.getY(), protectorFigure.getX(),
                                        protectorFigure.getY() - 1, protectorFigure.getX() - 1, true)) {
                                    continue;
                                }
                                return false;
                            }
                            enemy = cells[protectorFigure.getY() - 1][protectorFigure.getX() + 1];
                            if (enemy != null && enemy.getColor().equals(ChessFigure.black)) {
                                if (safeMove(protectorFigure.getY(), protectorFigure.getX(),
                                        protectorFigure.getY() - 1, protectorFigure.getX() + 1, true)) {
                                    continue;
                                }
                                return false;
                            }
                        }
                    }
                    // check all possible moves of figure that can avoid check
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {

                            if (protectorFigure.can(k, l) && isMoveLegal(i, j, k, l)){

                                if (safeMove(i, j, k, l, true)){
                                    continue;
                                }
                                // if shah is canceled
                                // that mean that is not checkmate
                                return false;
                            }
                        }
                    }
                }
            }
        }
        // Checkmate!
        return true;
    }

    public boolean isMoveLegal(int x1, int y1, int x2, int y2) {
        if (isInBoard(x1, y1) && cells[x1][y1] != null) {

            ChessFigure movingFigure = cells[x1][y1];

            if (cellIsEmpty(x2, y2)){
                if (movingFigure.can(x2, y2)) {
                    if (movingFigure.isKnight()) {
                        return true;
                    } else {
                        return isPathClear(movingFigure, x2, y2);
                    }
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
                    } else if (movingFigure.getColor().equals(ChessFigure.black)){
                        if (dy == -1 && Math.abs(dx) == 1) {
                            return true;
                        } else {
                            System.out.println("Cannot move movingFigure to " + x2 + " " + y2);
                            return false;
                        }
                    }
                }
                if (movingFigure.can(x2, y2)) {
                    if (movingFigure.isKnight()) {
                        return true;
                    } else {
                        return isPathClear(movingFigure, x2, y2);
                    }
                }
            } else {
                return false;
            }

        } else {
            System.out.println("No figure in " + x1 + " " + y1);
            return false;
        }
        return true; // TODO WTF??
    }

    public boolean isPathClear(ChessFigure fig, int x, int y) {
        if (fig instanceof Knight || fig instanceof King) {
            return true;
        }

        int dx = x - fig.getX();
        int dy = y - fig.getY();

        boolean horizontal = dy == 0;
        boolean toRight = dx > 0;
        boolean toLeft = dx < 0;

        boolean vertical = dx == 0;
        boolean toTop = dy > 0;
        boolean toBottom = dy < 0;

        if (fig instanceof Rook || fig instanceof Queen) {
            if (horizontal) {
                if (toRight) {
                    for (int i = fig.getX() + 1; i < x; i++) {
                        if (cells[y][i] != null)
                            return false;
                    }
                    return true;
                }
                if (toLeft){
                    for (int i = fig.getX() - 1; i > x; i--) {
                        if (cells[y][i] != null)
                            return false;
                    }
                    return true;
                }
            }
            if (vertical) {
                if (toTop) {
                    for (int i = fig.getY() - 1; i > y; i--) {
                        if (cells[i][x] != null)
                            return false;
                    }
                    return true;
                }
                if (toBottom) {
                    for (int i = fig.getY() + 1; i < y; i++) {
                        if (cells[i][x] != null)
                            return false;
                    }
                    return true;
                }
            }
        }

        if (fig instanceof Bishop || fig instanceof Queen) {
            if (dx > 0 && dy < 0) {
                for (int i = 1; i < dx; i++) {
                    if (cells[fig.getY() - i][fig.getX() + i] != null)
                        return false;
                }
                return true;
            }
            if (dx < 0 && dy > 0) {
                for (int i = -1; i > dx; i--) {
                    if (cells[fig.getY() - i][fig.getX() + i] != null)
                        return false;
                }
                return true;
            }

            // Move is vertical
            if (dx < 0 && dy < 0) {
                for (int i = -1; i > dx; i--) {
                    if (cells[fig.getY() + i][fig.getX() + i] != null)
                        return false;
                }
                return true;
            }
            if (dx > 0 && dy > 0) {
                for (int i = 1; i < dx; i++) {
                    if (cells[fig.getY() + i][fig.getX() + i] != null)
                        return false;
                    }
                    return true;
            }
        }
        if(fig instanceof Pawn){
            if(Math.abs(dy) == 1){
                return true;
            }
            if(Math.abs(dy) == 2){
                if(dy>0){
                    if(cells[fig.getY()+1][fig.getX()] != null)
                        return false;
                    else return true;
                }
                if (dy<0){
                    if(cells[fig.getY()-1][fig.getX()] != null)
                        return false;
                    else return true;
                }
            }
        }
        return true;
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

                    ChessFigure checkingEnemiesFigure = cells[i][j];

                    if (checkingEnemiesFigure instanceof Pawn){
                        if (ChessFigure.white.equals(checkingEnemiesFigure.getColor())){
                            if (cells[checkingEnemiesFigure.getY() + 1][checkingEnemiesFigure.getX() - 1] instanceof King ||
                            cells[checkingEnemiesFigure.getY() + 1][checkingEnemiesFigure.getX() + 1] instanceof King){
                                return true;
                            }
                        }
                        if (ChessFigure.black.equals(checkingEnemiesFigure.getColor())){
                            if (cells[checkingEnemiesFigure.getY() - 1][checkingEnemiesFigure.getX() - 1] instanceof King ||
                                    cells[checkingEnemiesFigure.getY() - 1][checkingEnemiesFigure.getX() + 1] instanceof King){
                                return true;
                            }
                        }
                    }
                    if (checkingEnemiesFigure.can(currentKing.getX(), currentKing.getY())){
                        if (checkingEnemiesFigure instanceof Knight) {
                            System.out.println("Ш А Х");
                            return true;
                        }
                        if (isPathClear(checkingEnemiesFigure, currentKing.getX(), currentKing.getY())){
                            System.out.println("Ш А Х");
                            return true;
                        }
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

    public boolean safeMove(int x1, int y1, int x2, int y2, boolean virtual){

        boolean virtualShah = isShah();

        if (isMoveLegal(x1, y1, x2, y2)) {
            ChessFigure tempFig = null;
            if (!cellIsEmpty(x2, y2)){
                tempFig = cells[x2][y2];
            }
            move(x1, y1, x2, y2);
            virtualShah = isShah();
            if (virtualShah || virtual){
                move(x2, y2, x1, y1);
                if (!(tempFig == null)){
                    cells[x2][y2] = tempFig;
                }
            }
        }
        return virtualShah;
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
