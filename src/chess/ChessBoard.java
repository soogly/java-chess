package chess;

public class ChessBoard {

    public static String message = "";
    public ChessFigure [][] cells = new ChessFigure[8][8];
    public String currentPlayer = "black";
    boolean shah = isShah();

    King kingWhite = new King(4,0, ChessFigure.white);
    King kingBlack = new King(4,7, ChessFigure.black);

    protected boolean isInBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < 8 && y < 8;
    }

    public ChessBoard() {
        cells[0][4] = kingWhite;
        cells[7][4] = kingBlack;
        cells[0][0] = new Rook(0,0, ChessFigure.white);
        cells[0][1] = new Knight(1,0, ChessFigure.white);
        cells[0][7] = new Rook(7,0, ChessFigure.white);
        cells[0][6] = new Knight(6,0, ChessFigure.white);
        cells[7][0] = new Rook(0,7, ChessFigure.black);
        cells[7][7] = new Rook(7,7, ChessFigure.black);
        cells[7][1] = new Knight(1,7, ChessFigure.black);
        cells[7][6] = new Knight(6,7, ChessFigure.black);
        cells[0][2] = new Bishop(2,0, ChessFigure.white);
        cells[0][5] = new Bishop(5,0, ChessFigure.white);
        cells[7][2] = new Bishop(2,7, ChessFigure.black);
        cells[7][5] = new Bishop(5,7, ChessFigure.black);
        cells[0][3] = new Queen(3,0, ChessFigure.white);
        cells[7][3] = new Queen(3,7, ChessFigure.black);

        for (int i = 1; i <= 6; i+=5) {
            for (int j = 0; j < 8; j++) {
                switch (i){
                    case 1:
                        cells[i][j] = new Pawn(j, i, ChessFigure.white);
                        break;
                    case 6:
                        cells[i][j] = new Pawn(j, i, ChessFigure.black);
                }
            }
        }
    }


    public ChessBoard(String combination) {
        if (combination.equals("shah")) {
            cells[0][4] = kingWhite;
            cells[7][4] = kingBlack;
            cells[0][0] = new Rook(0, 0, ChessFigure.white);
            cells[0][1] = new Knight(1, 0, ChessFigure.white);
            cells[0][7] = new Rook(7, 0, ChessFigure.white);
            cells[0][6] = new Knight(6, 0, ChessFigure.white);
            cells[7][0] = new Rook(0, 7, ChessFigure.black);
            cells[7][7] = new Rook(7, 7, ChessFigure.black);
            cells[7][1] = new Knight(1, 7, ChessFigure.black);
            cells[7][6] = new Knight(6, 7, ChessFigure.black);
            cells[0][2] = new Bishop(2, 0, ChessFigure.white);
            cells[0][5] = new Bishop(5, 0, ChessFigure.white);
            cells[7][2] = new Bishop(2, 7, ChessFigure.black);
            cells[4][2] = new Bishop(2, 4, ChessFigure.black);
            cells[0][3] = new Queen(3, 0, ChessFigure.white);
            cells[5][5] = new Queen(5, 5, ChessFigure.black);

            for (int i = 1; i <= 6; i += 5) {
                for (int j = 0; j < 8; j++) {
                    switch (i) {
                        case 1:
                            cells[i][j] = new Pawn(j, i, ChessFigure.white);
                            break;
                        case 6:
                            cells[i][j] = new Pawn(j, i, ChessFigure.black);
                    }
                }
            }
            cells[4][4] = cells[6][4];
            cells[4][4].setXY(4, 4);
            cells[6][4] = null;
            cells[1][5] = null;
        }
        if (combination.equals("twoKingTwoPawn")) {
            cells[0][4] = kingWhite;
            cells[7][4] = kingBlack;
            cells[6][2] = new Pawn(2, 6, ChessFigure.white);
            cells[1][2] = new Pawn(2, 1, ChessFigure.black);
        }
    }


    public boolean checkMate(){
        if (!isShah()){
            return false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j] != null){
                    if (!currentPlayer.equals(cells[i][j].getColor())){
                        continue;
                    }

                    ChessFigure protectorFigure = cells[i][j];

                    if (protectorFigure instanceof Pawn) {
                        if (ChessFigure.white.equals(protectorFigure.getColor())) {
                            if(protectorFigure.getX() > 0 && protectorFigure.getY()<7) {
                                ChessFigure enemy = cells[protectorFigure.getY() + 1][protectorFigure.getX() - 1];
                                if (enemy != null && enemy.getColor().equals(ChessFigure.black)) {
                                    if (!safeMove(protectorFigure.getY(), protectorFigure.getX(),
                                            protectorFigure.getY() + 1, protectorFigure.getX() - 1, true)) {
                                        return false;
                                    }
                                }
                            }
                            if(protectorFigure.getX() < 7 && protectorFigure.getY() < 7) {
                                ChessFigure enemy = cells[protectorFigure.getY() + 1][protectorFigure.getX() + 1];
                                if (enemy != null && enemy.getColor().equals(ChessFigure.black)) {
                                    if (!safeMove(protectorFigure.getY(), protectorFigure.getX(),
                                            protectorFigure.getY() + 1, protectorFigure.getX() + 1, true)) {
                                        return false;
                                    }
                                }
                            }
                        } else if (ChessFigure.black.equals(protectorFigure.getColor())) {
                            if(protectorFigure.getX() > 0 && protectorFigure.getY() > 0) {
                                ChessFigure enemy = cells[protectorFigure.getY() - 1][protectorFigure.getX() - 1];
                                if (enemy != null && enemy.getColor().equals(ChessFigure.black)) {
                                    if (!safeMove(protectorFigure.getY(), protectorFigure.getX(),
                                            protectorFigure.getY() - 1, protectorFigure.getX() - 1, true)) {
                                        return false;
                                    }
                                }
                            }
                            if(protectorFigure.getX() < 7 && protectorFigure.getY() > 0) {
                                ChessFigure enemy = cells[protectorFigure.getY() - 1][protectorFigure.getX() + 1];
                                if (enemy != null && enemy.getColor().equals(ChessFigure.black)) {
                                    if (!safeMove(protectorFigure.getY(), protectorFigure.getX(),
                                            protectorFigure.getY() - 1, protectorFigure.getX() + 1, true)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    // check all possible moves of figure that can avoid check
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {

                            if (protectorFigure.can(l, k) && isMoveLegal(j, i, l, k)){

                                if (!safeMove(j, i, l, k, true)){
                                    // if shah is canceled
                                    // that mean that is not checkmate

//                                    message = "First fined saving move: " + cells[i][j] + " from "+ i + j + " to " + k + l;
                                    System.out.println("First fined saving move: " + cells[i][j] + " from "+ i + j + " to " + k + l);
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        // Checkmate!
        message = "M A T";
        System.out.println(message);
        return true;
    }

    public boolean isMoveLegal(int x1, int y1, int x2, int y2) {
        if (isInBoard(x1, y1) && cells[y1][x1] != null && cells[y1][x1].getColor().equals(currentPlayer)) {

            ChessFigure figInHand = cells[y1][x1];

            if (cellIsEmpty(x2, y2)){
                if (figInHand.can(x2, y2)) {
                    if (figInHand.isKnight()) {
                        return true;
                    } else {
                        return isPathClear(figInHand, x2, y2);
                    }
                }
                // if cell is empty but figure in hand can't go in it
                message = "That figure can't move like you want";
                System.out.println(message);
                return false;

            // check that figure in target cell is enemy

            } else if (!currentPlayer.equals(cells[y2][x2].getColor())){
                if(figInHand instanceof Pawn) {
                    int dx = x2 - x1;
                    int dy = y2 - y1;
                    if(figInHand.getColor().equals(ChessFigure.white)) {
                        if (dy == 1 && Math.abs(dx) == 1) {
                            return true;
                        } else {
                            message = "Cannot move figInHand to " + x2 + " " + y2;
                            System.out.println(message);
                            return false;
                        }
                    } else if (figInHand.getColor().equals(ChessFigure.black)){
                        if (dy == -1 && Math.abs(dx) == 1) {
                            return true;
                        } else {
                            message = "Cannot move figInHand to " + x2 + " " + y2;
                            System.out.println(message);
                            return false;
                        }
                    }
                }
                if (figInHand.can(x2, y2)) {
                    if (figInHand.isKnight()) {
                        return true;
                    } else {
                        return isPathClear(figInHand, x2, y2);
                    }
                } else {
                    // if cell is empty but figure in hand can't go in it
                    message = "That figure can't move like you want";
                    System.out.println(message);
                    return false;
                }
            } else {
                // is not enemy in move target
//                message = "Is not enemy in move target";
                System.out.println("Is not enemy in move target");
                return false;
            }
        } else {
            message = "No your figure in " + x1 + " " + y1;
            System.out.println(message);
            return false;
        }
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
        boolean toTop = dy < 0;
        boolean toBottom = dy > 0;

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
        return cells[y2][x2] == null;
    }

    public boolean isShah(){
        King currentKing = currentPlayer.equals(ChessFigure.white) ? kingWhite : kingBlack;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j] != null){
                    if (currentPlayer.equals(cells[i][j].getColor())){
                        continue;
                    }

                    ChessFigure checkingEnemiesFigure = cells[i][j];

                    if (checkingEnemiesFigure instanceof Pawn){
                        if (ChessFigure.white.equals(checkingEnemiesFigure.getColor())){
                            if (checkingEnemiesFigure.getX() > 0 && checkingEnemiesFigure.getY() < 7 && cells[checkingEnemiesFigure.getY() + 1][checkingEnemiesFigure.getX() - 1] instanceof King)
                                return true;
                            if (checkingEnemiesFigure.getX() < 7 && checkingEnemiesFigure.getY() < 7 && cells[checkingEnemiesFigure.getY() + 1][checkingEnemiesFigure.getX() + 1] instanceof King)
                                return true;
                        }
                        if (ChessFigure.black.equals(checkingEnemiesFigure.getColor())){
                            if (checkingEnemiesFigure.getX() > 0 && checkingEnemiesFigure.getY() > 0 && cells[checkingEnemiesFigure.getY() - 1][checkingEnemiesFigure.getX() - 1] instanceof King)
                                return true;
                            if (checkingEnemiesFigure.getX() < 7 && checkingEnemiesFigure.getY() > 0 && cells[checkingEnemiesFigure.getY() - 1][checkingEnemiesFigure.getX() + 1] instanceof King)
                                return true;
                        }
                    }
                    if (checkingEnemiesFigure.can(currentKing.getX(), currentKing.getY())){
                        if (checkingEnemiesFigure instanceof Knight) {
                            message = "Ш А Х";
                            System.out.println(message);
                            return true;
                        }
                        if (isPathClear(checkingEnemiesFigure, currentKing.getX(), currentKing.getY())){
                            message = "Ш А Х";
                            System.out.println(message);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void move(int x1, int y1, int x2, int y2) {
        ChessFigure figure = cells[y1][x1];
        if(figure instanceof Pawn) {
            if (figure.getColor().equals(ChessFigure.black) && figure.getY() == 1) {

                cells[y2][x2] = new Queen(x2, y2, "black");
                cells[y1][x1] = null;
                return;

            }
            if (figure.getColor().equals(ChessFigure.white) && figure.getY() == 6) {

                cells[y2][x2] = new Queen(x2, y2, "white");
                cells[y1][x1] = null;
                return;
            }

        }

        figure.setXY(x2, y2);
        cells[y2][x2] = figure;
        cells[y1][x1] = null;
    }

    public boolean safeMove(int x1, int y1, int x2, int y2, boolean virtual){

        boolean virtualShah;

            ChessFigure tempFig = null;
            if (!cellIsEmpty(x2, y2)){
                tempFig = cells[y2][x2];
            }
            move(x1, y1, x2, y2);
            virtualShah = isShah();
            if (virtualShah || virtual){
                move(x2, y2, x1, y1);
                if (!(tempFig == null)){
                    cells[y2][x2] = tempFig;
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
