package chess;

import java.util.Scanner;

public class Game {
    static Scanner in  = new Scanner(System.in);
    static ChessBoard board = new ChessBoard();
    static boolean gameOver = false;
    static int[] turn;
    static int x1, x2, y1, y2;


    public static void main(String[] args) {

        System.out.println(board);

        while (!gameOver) {

            turn = getTurn();
            x1 = turn[0];
            y1 = turn[1];
            x2 = turn[2];
            y2 = turn[3];

            board.safeMove(x1, y1, x2, y2, false);

            while (board.shah){
                turn = getTurn();
                x1 = turn[0];
                y1 = turn[1];
                x2 = turn[2];
                y2 = turn[3];
                board.safeMove(x1, y1, x2, y2, false);
            }

            System.out.println(board);
            // change player
            board.currentUser = board.currentUser.equals("white") ? "black" : "white";
            // check game isn't over
            gameOver = board.checkMate();

        }
    }

    static private void doTurn(){

//            if (board.isMoveLegal(x1, y1, x2, y2)) {
//                ChessFigure tempFig = null;
//                if (!board.cellIsEmpty(x2, y2)){
//                    tempFig = board.cells[x2][y2];
//                }
//                board.move(x1, y1, x2, y2);
//                shah = board.isShah();
//                if (shah){
//                    board.move(x2, y2, x1, y1);
//                    if (!(tempFig == null)){
//                        board.cells[x2][y2] = tempFig;
//                    }
//                }
//            }
        }

    static private int[] getTurn(){
        for (int i = 0; i < 5; i++) {
            turn[i] = in.nextInt();
        }
        return turn;
    }
}
