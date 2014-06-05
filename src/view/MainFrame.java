package view;

import AI.*;
import IO.RulesParser;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;

public class MainFrame extends JFrame{

    private class AITry extends TimerTask {
        public void run() {
            doSomething();
        }
    }

    Game game;
    BoardPanel boardPanel;
    Timer timer;

    public MainFrame(String rules) {
        initUI(rules);
    }

    public void makeBoard(String rules) {

        getContentPane().remove(boardPanel);
        boardPanel = new BoardPanel(rules, new Human(), new AlphaBetaAI(4));
        game = boardPanel.getGame();
        add(boardPanel);

        setSize(1, 1);
        setSize(game.getBoard().getXSize() * 80, game.getBoard().getYSize() * 80);
    }

    private void initUI(String rules) {

        this.boardPanel = new BoardPanel(rules, new Human(), new AlphaBetaAI(4));
        game = boardPanel.getGame();
        add(boardPanel);

        setTitle("Chesslike");
        setSize(new Dimension(game.getBoard().getXSize() * 80, game.getBoard().getYSize() * 80));
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        timer = new java.util.Timer();
        timer.schedule(new AITry(), 0, 200);

        JMenuItem openBtn = new JMenuItem("Open");

        openBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser openFile = new JFileChooser();
                openFile.showOpenDialog(null);
                System.out.println(openFile.getSelectedFile().getAbsolutePath());
                makeBoard(openFile.getSelectedFile().getAbsolutePath());
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu menu;

        menu = new JMenu("New game");
        menu.getAccessibleContext().setAccessibleDescription(
                "Start a new game");
        menuBar.add(menu);
        menu.add(openBtn);

        setJMenuBar(menuBar);
    }

    public Object doSomething(){
        try {
            boardPanel.onClick(-1, -1);
        } catch(Exception e) {

        }
        return null;
    }
}
