import IO.RulesParser;
import view.MainFrame;

import javax.swing.*;

public class ChessLike {

    private static MainFrame ex;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ex = new MainFrame("./rules/Maharajah.xml");
                ex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ex.setVisible(true);
            }
        });

    }

}
