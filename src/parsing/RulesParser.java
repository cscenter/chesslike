package parsing;

import board.Board;
import game.Game;
import move.Capture;
import move.Jump;
import move.Move;
import move.Slide;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import piece.Piece;
import piece.PieceType;
import player.Player;
import player.ParameterProcessor;
import board.Position;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RulesParser {

    public static Game parse(String fileName) {

        try {

            //file
            File rulesFile = new File(fileName);
            Document rules = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(rulesFile);
            rules.getDocumentElement().normalize();

            //board
            NodeList eBoards = rules.getElementsByTagName("board");
            if (eBoards.getLength() != 1) {
                System.out.println("!!! Not 1 board\n");
                return null;
            }
            Element eBoard = (Element) eBoards.item(0);


            Board board = new Board(
                    Integer.parseInt(eBoard.getAttribute("xsize")),
                    Integer.parseInt(eBoard.getAttribute("xsize"))
            );

            NodeList eRects = eBoard.getElementsByTagName("rect");
            for (int i = 0; i < eRects.getLength(); i++) {
                Element eRect = (Element) eRects.item(i);

                board.addRectangle(
                        Integer.parseInt(eRect.getAttribute("x")) - 1,
                        Integer.parseInt(eRect.getAttribute("y")) - 1,
                        Integer.parseInt(eRect.getAttribute("xsize")),
                        Integer.parseInt(eRect.getAttribute("ysize"))
                );
            }

            NodeList eFields = eBoard.getElementsByTagName("square");
            for (int i = 0; i < eFields.getLength(); i++) {
                Element eField = (Element) eFields.item(i);

                board.addSquare(
                        Integer.parseInt(eField.getAttribute("x")) - 1,
                        Integer.parseInt(eField.getAttribute("y")) - 1
                );
            }

            //pieces
            NodeList ePieceTypes = rules.getElementsByTagName("piece");
            Map<Integer, PieceType> pieceTypes = new HashMap<Integer, PieceType>();

            for (int i = 0; i < ePieceTypes.getLength(); i++) {
                Element ePiece = (Element) ePieceTypes.item(i);

                Element eMoves = (Element) ePiece.getElementsByTagName("moves").item(0);
                NodeList eJumps = eMoves.getElementsByTagName("jump");
                NodeList eSlides = eMoves.getElementsByTagName("slide");

                Element eCaptures = (Element) ePiece.getElementsByTagName("captures").item(0);
                NodeList eCaptureJumps = eCaptures.getElementsByTagName("jump");
                NodeList eCaptureSlides = eCaptures.getElementsByTagName("slide");

                Vector<Move> moves = new Vector<Move>();

                for (int j = 0; j < eJumps.getLength(); j++) {
                    Element eJump = (Element) eJumps.item(j);
                    Move jump = new Jump(
                            new Position(
                                    Integer.parseInt(eJump.getAttribute("x")),
                                    Integer.parseInt(eJump.getAttribute("y"))
                            )
                    );
                    moves.add(jump);
                }

                for (int s = 0; s < eSlides.getLength(); s++) {
                    Element eSlide = (Element) eSlides.item(s);
                    Move slide = new Slide(
                            new Position(
                                    Integer.parseInt(eSlide.getAttribute("x")),
                                    Integer.parseInt(eSlide.getAttribute("y"))
                            )
                    );
                    moves.add(slide);
                }

                if (Integer.parseInt(eCaptures.getAttribute("diff")) == 0) {
                    for (int m = 0; m < eJumps.getLength() + eSlides.getLength(); m++) {
                        moves.add(new Capture(moves.elementAt(m)));
                    }
                } else {
                    for (int j = 0; j < eCaptureJumps.getLength(); j++) {
                        Element eCaptureJump = (Element) eCaptureJumps.item(j);
                        Move jump = new Jump(
                                new Position(
                                        Integer.parseInt(eCaptureJump.getAttribute("x")),
                                        Integer.parseInt(eCaptureJump.getAttribute("y"))
                                )
                        );
                        moves.add(new Capture(jump));
                    }

                    for (int s = 0; s < eCaptureSlides.getLength(); s++) {
                        Element eCaptureSlide = (Element) eCaptureSlides.item(s);
                        Move slide = new Slide(
                                new Position(
                                        Integer.parseInt(eCaptureSlide.getAttribute("x")),
                                        Integer.parseInt(eCaptureSlide.getAttribute("y"))
                                )
                        );
                        moves.add(new Capture(slide));
                    }
                }

                PieceType pieceType = new PieceType(ePiece.getAttribute("name"), ePiece.getAttribute("short"), moves);
                pieceTypes.put(Integer.parseInt(ePiece.getAttribute("id")), pieceType);
            }

            //players
            NodeList ePlayers = rules.getElementsByTagName("player");
            if (ePlayers.getLength() == 0) {
                System.out.println("!!! No players\n");
                return null;
            }

            Map<Integer, Player> players = new HashMap<Integer, Player>();
            Map<Integer, Piece> pieces = new HashMap<Integer, Piece>();
            Map<Integer, Integer> turns = new HashMap<Integer, Integer>();

            int pieceId = 1;

            for (int i = 0; i < ePlayers.getLength(); i++) {
                Element ePlayer = (Element) ePlayers.item(i);
                NodeList ePieces = ePlayer.getElementsByTagName("start");

                int playerId = Integer.parseInt(ePlayer.getAttribute("id"));
                Vector<Integer> playerPieces = new Vector<Integer>();

                for (int j = 0; j < ePieces.getLength(); j++) {
                    Element ePiece = (Element) ePieces.item(j);

                    int x = Integer.parseInt(ePiece.getAttribute("x")) - 1;
                    int y = Integer.parseInt(ePiece.getAttribute("y")) - 1;

                    Piece piece = new Piece(
                            Integer.parseInt(ePiece.getAttribute("id")),
                            playerId,
                            new Position(x, y)
                    );

                    pieces.put(pieceId, piece);
                    playerPieces.add(pieceId);
                    board.putPiece(pieceId, x, y);
                    pieceId++;
                }

                players.put(
                        playerId,
                        new Player(
                                ParameterProcessor.getColor(ePlayer.getAttribute("color")),
                                ePlayer.getAttribute("name"),
                                ParameterProcessor.getDirection(ePlayer.getAttribute("orientation")),
                                playerPieces
                        )
                );

                turns.put(Integer.parseInt(ePlayer.getAttribute("turn")), playerId);
            }

            return new Game(board, pieceTypes, players, pieces, turns);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
