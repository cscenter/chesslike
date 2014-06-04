package view;

import model.Game;
import model.set.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame{

    Game game;

    public MainFrame(Game game) {
        this.game = game;
        initUI();
    }

    private void initUI() {

        add(new BoardPanel(game));

        setTitle("Chesslike");
        setSize(new Dimension(game.getBoard().getXSize() * 80, game.getBoard().getYSize() * 80));
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
