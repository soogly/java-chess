package chess;

import java.util.Scanner;

public class ChessTest {
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        System.out.println(board);
        Scanner in  = new Scanner(System.in);
        while (in.hasNext()) {
            int x1 = in.nextInt(), y1 = in.nextInt(),
                    x2 = in.nextInt(),y2 = in.nextInt();
            board.move(x1, y1, x2, y2);
            System.out.println(board);
        }
    }
}
