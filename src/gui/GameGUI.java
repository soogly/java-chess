package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import chess.ChessBoard;
import chess.ChessFigure;
import gui.components.Cell;
import gui.components.FigureGUI;

public class GameGUI extends JFrame implements gui.Face {

    // gui
    int boardSize = 400;
    int cellSize = boardSize/8;

    Cell [][] map;
    Cell selected;

    static FigureGUI [][] figsMap = new FigureGUI[8][8];

    JLayeredPane lp = getLayeredPane();

    static JLabel currentPlayerLabel;
    static JLabel message;

    int menuBarHeight = 20;


    // game
    static public ChessBoard board = new ChessBoard();
    static boolean gameOver = false;

    int xFrom = 0;
    int yFrom = 0;
    int xTo = 0;
    int yTo = 0;

    public GameGUI(String title){

        super(title);
        JMenuBar bar = new JMenuBar();
        bar.add(createFileMenu());
        setJMenuBar(bar);

        setSize(boardSize, boardSize + 107);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        // Creating label for messages
        message = new JLabel("Wellcome!");
        message.setVerticalAlignment(JLabel.CENTER);
        message.setHorizontalAlignment(JLabel.CENTER);
        message.setOpaque(true);
        message.setForeground(Color.black);
        message.setBounds(0, menuBarHeight, boardSize, menuBarHeight);
        lp.add(message, 100);

        // Creating label show player
        currentPlayerLabel = new JLabel("Ходят: " + board.currentPlayer);
        currentPlayerLabel.setVerticalAlignment(JLabel.BOTTOM);
        currentPlayerLabel.setHorizontalAlignment(JLabel.CENTER);
        currentPlayerLabel.setOpaque(true);
        currentPlayerLabel.setForeground(Color.black);
        currentPlayerLabel.setBounds(0, menuBarHeight*2+cellSize*8, boardSize, menuBarHeight);
        lp.add(currentPlayerLabel, new Integer(-1));

    }

    public void drawBoard(){

        map = new Cell[8][8];

        Color cellColor = Color.black;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                // Selecting color of cell
                int colorIndx = (i + j + 1) % 2;
                switch (colorIndx) {
                    case 0:
                        cellColor = new Color(222, 100, 59, 255);
                        break;
                    case 1:
                        cellColor = new Color(255, 190, 135, 255);
                        break;
                }

                // create cell and move it to its place
                Cell cell = new Cell(cellColor, j, i, cellSize);
                // move cell to it's place
                cell.setBounds(j * cellSize, i * cellSize+45, cellSize, cellSize);

                // add to the default layer
                lp.add(cell, JLayeredPane.DEFAULT_LAYER);

                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        super.mouseClicked(me);
                        if (gameOver){
                            return;
                        }
                        if (selected == null) {
                            xFrom = cell.x_;
                            yFrom = cell.y_;
                            if(board.cells[yFrom][xFrom] == null || !board.cells[yFrom][xFrom].getColor().equals(board.currentPlayer)){
                                message.setText("No your figure in this cell");
                            } else {
                                message.setText("");
                                selected = cell;
                                figsMap[yFrom][xFrom].setForeground(new Color(36, 154, 184,255));
                                figsMap[yFrom][xFrom].repaint();
                            }
                        } else {
                            figsMap[yFrom][xFrom].setForeground(selected.normalColor);
                            selected.repaint();
                            selected = null;
                            xTo = cell.x_;
                            yTo = cell.y_;
                            doTurn(xFrom, yFrom, xTo, yTo);

                        }
                        System.out.println(cell.y_ + " " + cell.x_);
                    }
                });
                map[i][j] = cell;
            }
        }

    }

    public void doTurn(int x1, int y1, int x2, int y2) {

        if (!board.isMoveLegal(x1, y1, x2, y2)) {
            message.setText("Move not legal");
            return;
        } else {
            if (board.safeMove(x1, y1, x2, y2, false)) {
                message.setText("Ш А Х");
                return;
            } else {
                message.setText("");
//                board.shah = false;
                FigureGUI figInHand = figsMap[y1][x1];
                figInHand.setBounds(x2 * cellSize, y2 * cellSize + menuBarHeight * 2 + 5, cellSize, cellSize);
                figInHand.setForeground(map[y2][x2].color);

                if (figsMap[y2][x2] != null) {
                    figsMap[y2][x2].setVisible(false);
                    figsMap[y2][x2] = null;
                }

                figsMap[y2][x2] = figInHand;
                figsMap[y1][x1] = null;

                // show board in terminal
                System.out.println(board);
                System.out.println(board.currentPlayer);

                message.setText(ChessBoard.message);
                board.currentPlayer = board.currentPlayer.equals("white") ? "black" : "white";
                currentPlayerLabel.setText(board.currentPlayer + "'s turn");

                board.shah = board.isShah(); //?
                if(board.shah){
                    message.setText("Ш А Х");
                } else{
                    message.setText("");
                }
            }
        }
//        board.currentPlayer = board.currentPlayer.equals("white") ? "black" : "white";
//        currentPlayerLabel.setText(board.currentPlayer + "'s turn");

        if(board.checkMate()) {
            message.setText("G A M E   O V E R");
            gameOver = true;
        }
    }

    public void arrangeFigures(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessFigure fig = board.cells[i][j];
                if (fig != null){
                    FigureGUI figGui = new FigureGUI(fig.toString(), j, i, cellSize);

                    figGui.setForeground(map[i][j].color);

                    figGui.setBounds(j * cellSize, i * cellSize+45, cellSize, cellSize);
                    figGui.setOpaque(true);
                    lp.add(figGui, new Integer(1000));

                    figsMap[i][j] = figGui;
                }
            }
        }
    }

    private JMenu createFileMenu() {

        JMenu file = new JMenu("Файл");
        JMenuItem open = new JMenuItem("Новая игра");
        JMenuItem exit = new JMenuItem("Выход");
        exit.addActionListener(actionEvent -> {
            System.out.println(actionEvent);
            dispose();
        });
        open.addActionListener(actionEvent -> {
            GameGUI gameWindow = new GameGUI("Chess");
            GameGUI.board = new ChessBoard();
            gameWindow.drawBoard();
            gameWindow.arrangeFigures();
            gameOver = false;
            dispose();
        });
        file.add(open);
        file.addSeparator();
        file.add(exit);
        return file;
    }

    public static void main(String[] args) {
        GameGUI gameWindow = new GameGUI("Chess");
        gameWindow.drawBoard();
        gameWindow.arrangeFigures();
        message.setText("");
        currentPlayerLabel.setText(board.currentPlayer + "'s turn");
    }
}