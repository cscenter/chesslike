package view;

import IO.RulesParser;
import model.Game;
import model.coord.Entry;
import model.coord.Position;
import model.coord.Route;
import model.set.Piece;
import AI.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardPanel extends JPanel {

    private static enum State {
        waiting,
        trying
    }

    private State state;
    private Game game;
    private Position current;
    private Square[][] boardGrid;
    private AI[] players;
    Image boardImage;

    public BoardPanel(String rules, AI first, AI second) {
        this.game = RulesParser.parse(rules);
        this.boardImage = game.getBoardImage();

        this.players = new AI[]{first, second};

        setLayout(new GridLayout(game.getBoard().getXSize(), game.getBoard().getYSize()));

        this.state = State.waiting;
        this.boardGrid = new Square[game.getBoard().getXSize()][game.getBoard().getYSize()];

        boolean bows = true;
        boolean bowc = bows;
        Color color;
        for (int y = game.getBoard().getYSize() - 1; y >= 0; y--) {

            bowc = bows;
            for (int x = 0; x < game.getBoard().getXSize(); x++) {

                if (game.getBoard().getPiece(x, y) != null) {
                    if (boardImage == null) {
                        if (bowc) {
                            color = Color.white;
                        } else {
                            color = Color.darkGray;
                        }
                    } else {
                        color = Color.white;
                    }
                } else {
                    color = Color.blue;
                }
                boardGrid[x][y] = new Square(color);
                ActionListener actionListener = new onMousePressed(x, y);
                boardGrid[x][y].addActionListener(actionListener);
                if (boardImage != null) {
                    boardGrid[x][y].setOpaque(false);
                }
                add(boardGrid[x][y]);
                bowc = !bowc;
            }
            bows = !bows;
        }
        paint();
    }

    public void onClick(Entry<Integer, Integer> xy) {
        onClick(xy.getKey(), xy.getValue());
    }

    public void onClick(int x, int y) {

        AI currentPlayer = players[game.getCurrentTurn()];

        if (currentPlayer.isAI() == false) {
            if (x != -1) {

                if (state == State.waiting) {
                    int[][] s = game.getTypes(x, y);
                    boolean flag = false;
                    for (int xx = 0; xx < game.getBoard().getXSize(); xx++) {
                        for (int yy = 0; yy < game.getBoard().getYSize(); yy++) {
                            if (s[xx][yy] == 1) {
                                boardGrid[xx][yy].makeColor(Color.cyan);
                                flag = true;
                            } else if (s[xx][yy] == 2) {
                                boardGrid[xx][yy].makeColor(Color.red);
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

        } else {
            Route route = currentPlayer.getRoute(game);
            boolean flag = game.move(route.getStartPosition(), route.getDestPosition());
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
        Color currentColor;
        boolean isBase;

        public Square(Color color){
            isBase = true;
            baseColor = color;
            currentColor = color;
            setBackground(color);
        }

        public void makeColor(Color color) {
            isBase = false;
            currentColor = color;
            setBackground(color);
        }

        void setBaseColor() {
            isBase = true;
            currentColor = baseColor;
            setBackground(baseColor);
        }

        public void paint(Graphics g) {
            super.paint(g);

            if (!isBase) {

                BufferedImage buttonImage = getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
                Graphics2D graphics = buttonImage.createGraphics();

                graphics.setPaint(currentColor);
                graphics.fillRect(0, 0, buttonImage.getWidth(), buttonImage.getHeight());

                Graphics2D g2d = (Graphics2D) g;
                AlphaComposite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
                g2d.setComposite(newComposite);

                g2d.drawImage(buttonImage, 0, 0, null);
            }
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

    Game getGame() {
        return game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (boardImage != null) {
            g.drawImage(boardImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
