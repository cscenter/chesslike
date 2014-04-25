package view;

import model.set.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public static class Square extends JPanel {
        public Square(Color color){
            setLayout(new GridLayout(1, 1));
            setBackground(color);
        }
    }

    public BoardPanel(Board board) {
        setLayout(new GridLayout(board.getXSize(), board.getYSize()));

        Square[][] boardGrid = new Square[board.getXSize()][board.getYSize()];

        boolean bows = true;
        boolean bowc = bows;
        Color color;
        for (int x = 0; x < board.getXSize(); x++) {
            bowc = bows;
            for (int y = 0; y < board.getYSize(); y++) {
                if (bowc) {
                    color = Color.BLACK;
                } else {
                    color = Color.WHITE;
                }
                boardGrid[x][y] = new Square(color);
                add(boardGrid[x][y]);
                bowc = !bowc;
            }
            bows = !bows;
        }
    }
}
