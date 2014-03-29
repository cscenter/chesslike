import model.Game;
import IO.RulesParser;
import IO.TextViewer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChessLike {

    public static void main(String[] args) {
        Game game;

        if (args.length > 0) {
            System.out.println(args[0]);
            game = RulesParser.parse(args[0]);
        } else {
            //game = RulesParser.parse("./rules/Citadel.xml");
            game = RulesParser.parse("./rules/Chess.xml");
            //game = RulesParser.parse("./rules/Grand Chess.xml");
            //game = RulesParser.parse("./rules/Maharajah.xml");
            //game = RulesParser.parse("./rules/Shatranj.xml");
            //game = RulesParser.parse("./rules/Shogi.xml");
        }

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        game.print();
        while (true) {
            try {
                System.out.print("Action: ");
                String action = bufferRead.readLine();
                if (
                        action.equals("end")
                        || action.equals("finish")
                        || action.equals("exit")
                        || action.equals("quit")
                ) {
                    break;
                } else if (action.equals("print")) {
                    System.out.println(TextViewer.print(game.print(), ""));
                } else if (action.equals("show")) {
                    System.out.print("x = ");
                    int x = Integer.parseInt(bufferRead.readLine());

                    System.out.print("y = ");
                    int y = Integer.parseInt(bufferRead.readLine());

                    System.out.println(TextViewer.print(game.printDestinations(x - 1, y - 1), ""));
                } else if (action.equals("move")) {
                    System.out.print("Start:\nx = ");
                    int x = Integer.parseInt(bufferRead.readLine());

                    System.out.print("y = ");
                    int y = Integer.parseInt(bufferRead.readLine());

                    System.out.print("Destination:\nx = ");
                    int xDest = Integer.parseInt(bufferRead.readLine());

                    System.out.print("y = ");
                    int yDest = Integer.parseInt(bufferRead.readLine());

                    game.move(x - 1, y - 1, xDest - 1, yDest - 1);
                    System.out.println(TextViewer.print(game.print(), ""));
                }
            } catch (Exception e) {}
        }
    }

}
