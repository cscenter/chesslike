package view;

import AI.*;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainFrame extends JFrame{

    private class AITry extends TimerTask {
        public void run() {
            doSomething();
        }
    }

    Game game;
    BoardPanel boardPanel;

    public MainFrame(Game game) {
        this.game = game;
        initUI();
    }

    private void initUI() {

        this.boardPanel = new BoardPanel(game, new Human(), new AlphaBetaAI(4));
        add(boardPanel);

        setTitle("Chesslike");
        setSize(new Dimension(game.getBoard().getXSize() * 80, game.getBoard().getYSize() * 80));
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new AITry(), 0, 10);
    }

    public Object doSomething(){
        boardPanel.onClick(-1, -1);
        return null;
    }
}
