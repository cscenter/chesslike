import IO.RulesParser;
import view.MainFrame;

import javax.swing.*;

public class ChessLike {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame ex = new MainFrame(RulesParser.parse("./rules/Chess.xml"));
                ex.setVisible(true);
            }
        });

    }

}
