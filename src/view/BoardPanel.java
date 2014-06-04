package view;

import model.Game;
import model.coord.Entry;
import model.coord.Position;
import model.set.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class BoardPanel extends JPanel {

    private static enum State {
        waiting,
        trying
    }

    State state;
    Game game;
    Position current;
    Square[][] boardGrid;

    public void onClick(Entry<Integer, Integer> xy) {
        onClick(xy.getKey(), xy.getValue());
    }

    public void onClick(int x, int y) {
        if (state == State.waiting) {
            int[][] s = game.getTypes(x, y);
            boolean flag = false;
            for (int xx = 0; xx < game.getBoard().getXSize(); xx++) {
                for (int yy = 0; yy < game.getBoard().getYSize(); yy++) {
                    if (s[xx][yy] == 1) {
                        boardGrid[xx][yy].setBackground(Color.cyan);
                        flag = true;
                    } else if (s[xx][yy] == 2) {
                        boardGrid[xx][yy].setBackground(Color.red);
                        flag = true;
                    } else {
                        boardGrid[xx][yy].setBaseColor();
                    }
                }
            }
            if (flag) {
                current = new Position(x, y);
                state = State.trying;
            }
        } else if (state == State.trying) {
            boolean flag = game.move(current.getX(), current.getY(), x, y);
            paint();
            current = null;
            state = State.waiting;
        }
    }

    public class onMousePressed implements ActionListener {
        Entry<Integer, Integer> xy;

        public onMousePressed(int x, int y) {
            super();
            this.xy = new Entry<Integer, Integer>(x, y);
        }

        public onMousePressed(Entry<Integer, Integer> xy) {
            super();
            this.xy = xy;
        }

        public void actionPerformed(ActionEvent e) {
            onClick(xy);
        }
    }


    public static class Square extends JButton {

        Color baseColor;

        public Square(Color color){
            baseColor = color;
            setBackground(color);
        }

        void setBaseColor() {
            setBackground(baseColor);
        }

    }

    void paint() {
        for (int x = 0; x < game.getBoard().getXSize(); x++) {
            for (int y = game.getBoard().getYSize() - 1; y >= 0; y--) {
                boardGrid[x][y].setBaseColor();
                try {
                    Piece piece = game.getBoard().getPiece(x, y);
                    ImageIcon icon = new ImageIcon(piece.getPieceType().getIcon(piece.getPlayer().getId())) ;
                    boardGrid[x][y].setIcon(icon);
                } catch(Exception e) {
                    boardGrid[x][y].setIcon(null);
                }
            }
        }
    }

    public BoardPanel(Game game) {
        setLayout(new GridLayout(game.getBoard().getXSize(), game.getBoard().getYSize()));
        this.game = game;
        this.state = State.waiting;
        this.boardGrid = new Square[game.getBoard().getXSize()][game.getBoard().getYSize()];

        boolean bows = true;
        boolean bowc = bows;
        Color color;
        for (int y = game.getBoard().getYSize() - 1; y >= 0; y--) {

            bowc = bows;
            for (int x = 0; x < game.getBoard().getXSize(); x++) {
                if (game.getBoard().getPiece(x, y) != null) {
                    if (bowc) {
                        color = Color.white;
                    } else {
                        color = Color.darkGray;
                    }
                } else {
                    color = Color.red;
                }
                boardGrid[x][y] = new Square(color);
                ActionListener actionListener = new onMousePressed(x, y);
                boardGrid[x][y].addActionListener(actionListener);
                add(boardGrid[x][y]);
                bowc = !bowc;
            }
            bows = !bows;
        }
        paint();
    }
}
