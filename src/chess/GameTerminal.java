package chess;

import java.util.Scanner;

public class GameTerminal {
    static Scanner in  = new Scanner(System.in);
//    static ChessBoard board = new ChessBoard("twoKingTwoPawn");
    static ChessBoard board = new ChessBoard();
    static boolean gameOver = false;
    static int[] turn = new int[4];
    static int x1, x2, y1, y2;


    public static void main(String[] args) {

        System.out.println(board);
        while (!gameOver) {

            System.out.println(board.currentPlayer + "'s turn");
            board.shah = board.isShah();
            turn = getTurn();
            y1 = turn[0];
            x1 = turn[1];
            y2 = turn[2];
            x2 = turn[3];

            while (!board.isMoveLegal(x1, y1, x2, y2)) {
                System.out.println(board);
                System.out.println("move NOT LEGAL");
                System.out.println(board.currentPlayer + "'s turn");
                turn = getTurn();
                y1 = turn[0];
                x1 = turn[1];
                y2 = turn[2];
                x2 = turn[3];
            }

            board.shah = board.safeMove(x1, y1, x2, y2, false);

            while (board.shah){
                turn = getTurn();
                y1 = turn[0];
                x1 = turn[1];
                y2 = turn[2];
                x2 = turn[3];
                board.shah = board.safeMove(x1, y1, x2, y2, false);
            }

            System.out.println(board);
            // change player
            board.currentPlayer = board.currentPlayer.equals("white") ? "black" : "white";
            // check game isn't over
            gameOver = board.checkMate();

        }

        System.out.println("G A M E  O V E R");
    }


    static private int[] getTurn(){
        for (int i = 0; i < 4; i++) {
            turn[i] = in.nextInt();
        }
        return turn;
    }
}
