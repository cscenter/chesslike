package IO;

import model.coord.Entry;
import model.moves.SpecialMove;
import model.set.Board;
import model.coord.Position;
import model.Game;
import model.moves.Jump;
import model.moves.Move;
import model.moves.Slide;
import model.set.PieceType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import model.set.Piece;
import model.set.Player;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RulesParser {

    public static Game parse(String fileName) {

        try {

            //file
            File rulesFile = new File(fileName);
            Document rules = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(rulesFile);
            rules.getDocumentElement().normalize();

            Element ePlayerNumber = (Element) rules.getElementsByTagName("players").item(0);
            int numberOfPlayers = Integer.parseInt(ePlayerNumber.getAttribute("number"));

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
            Map<Integer, PieceType> pieceTypesMap = new HashMap<Integer, PieceType>();
            List<PieceType> pieceTypes = new ArrayList<PieceType>();

            for (int i = 0; i < ePieceTypes.getLength(); i++) {
                Element ePiece = (Element) ePieceTypes.item(i);

                Element eMoves = (Element) ePiece.getElementsByTagName("moves").item(0);
                NodeList eJumps = eMoves.getElementsByTagName("jump");
                NodeList eSlides = eMoves.getElementsByTagName("slide");

                Element eCaptures = (Element) ePiece.getElementsByTagName("captures").item(0);
                NodeList eCaptureJumps = eCaptures.getElementsByTagName("jump");
                NodeList eCaptureSlides = eCaptures.getElementsByTagName("slide");

                List<Move> moves = new ArrayList<Move>();

                for (int j = 0; j < eJumps.getLength(); j++) {
                    Element eJump = (Element) eJumps.item(j);
                    Move jump = new Jump(
                            new Position(
                                    Integer.parseInt(eJump.getAttribute("x")),
                                    Integer.parseInt(eJump.getAttribute("y"))
                            ),
                            false
                    );
                    moves.add(jump);
                }

                for (int s = 0; s < eSlides.getLength(); s++) {
                    Element eSlide = (Element) eSlides.item(s);
                    Move slide = new Slide(
                            new Position(
                                    Integer.parseInt(eSlide.getAttribute("x")),
                                    Integer.parseInt(eSlide.getAttribute("y"))
                            ),
                            false
                    );
                    moves.add(slide);
                }

                if (Integer.parseInt(eCaptures.getAttribute("diff")) == 0) {
                    for (int m = 0; m < eJumps.getLength() + eSlides.getLength(); m++) {
                        moves.add(moves.get(m).makeCapture());
                    }
                } else {
                    for (int j = 0; j < eCaptureJumps.getLength(); j++) {
                        Element eCaptureJump = (Element) eCaptureJumps.item(j);
                        moves.add(new Jump(
                                new Position(
                                        Integer.parseInt(eCaptureJump.getAttribute("x")),
                                        Integer.parseInt(eCaptureJump.getAttribute("y"))
                                ),
                                true
                        ));
                    }

                    for (int s = 0; s < eCaptureSlides.getLength(); s++) {
                        Element eCaptureSlide = (Element) eCaptureSlides.item(s);
                        moves.add(new Slide(
                                new Position(
                                        Integer.parseInt(eCaptureSlide.getAttribute("x")),
                                        Integer.parseInt(eCaptureSlide.getAttribute("y"))
                                ),
                                true
                        ));
                    }
                }

                NodeList eImages = ePiece.getElementsByTagName("image");

                Image[] images = new Image[numberOfPlayers];

                for (int j = 0; j < eImages.getLength(); j++) {
                    try {
                        images[j] = ImageIO.read(new File(((Element) eImages.item(j)).getAttribute("address")));
                        images[j] = images[j].getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    } catch (Exception e) {}
                }

                int id = Integer.parseInt(ePiece.getAttribute("id"));
                PieceType pieceType = new PieceType(
                        id, ePiece.getAttribute("name"), ePiece.getAttribute("short"), moves,
                        Integer.parseInt(ePiece.getAttribute("weight")),
                        images
                );
                pieceTypes.add(pieceType);
                pieceTypesMap.put(id, pieceType);
            }

            for (int i = 0; i < ePieceTypes.getLength(); i++) {
                Element ePiece = (Element) ePieceTypes.item(i);

                NodeList eSpecials = ePiece.getElementsByTagName("special");
                List<SpecialMove> specialMoves = new ArrayList<SpecialMove>();

                for (int s = 0; s < eSpecials.getLength(); s++) {
                    Element eSpecial = (Element) eSpecials.item(s);

                    Element eSelf = (Element) eSpecial.getElementsByTagName("self").item(0);
                    Position destination = new Position(
                            Integer.parseInt(eSelf.getAttribute("x")),
                            Integer.parseInt(eSelf.getAttribute("y"))
                    );

                    Integer moveIdSelf = null;

                    String moveIdSelfString = eSelf.getAttribute("moved");
                    if (moveIdSelfString != null && !moveIdSelfString.equals("")) {
                        moveIdSelf = Integer.parseInt(moveIdSelfString);
                    }

                    Entry<Position, Piece> prey = null;

                    NodeList ePreys = eSpecial.getElementsByTagName("prey");
                    if (ePreys.getLength() == 1) {
                        Element ePrey = (Element) ePreys.item(0);

                        Integer moveId = null;

                        String moveIdString = ePrey.getAttribute("moved");
                        if (moveIdString != null && !moveIdString.equals("")) {
                            moveId = Integer.parseInt(moveIdString);
                        }

                        Integer pieceTypeId = null;

                        String pieceTypeIdString = ePrey.getAttribute("id");
                        if (pieceTypeIdString != null && !pieceTypeIdString.equals("")) {
                            pieceTypeId = Integer.parseInt(pieceTypeIdString);
                        }

                        Position preyPosition = new Position(
                                Integer.parseInt(ePrey.getAttribute("x")),
                                Integer.parseInt(ePrey.getAttribute("y"))
                        );

                        prey = new Entry<Position, Piece>(
                                preyPosition,
                                new Piece(
                                        pieceTypesMap.get(pieceTypeId),
                                        null,
                                        moveId
                                )
                        );
                    }

                    Entry<SpecialMove.Arrangement, Piece> companion = null;

                    NodeList eCompanions = eSpecial.getElementsByTagName("companion");
                    if (eCompanions.getLength() == 1) {
                        Element eCompanion = (Element) eCompanions.item(0);

                        Integer moveId = null;

                        String moveIdString = eCompanion.getAttribute("moved");
                        if (moveIdString != null && !moveIdString.equals("")) {
                            moveId = Integer.parseInt(moveIdString);
                        }

                        Integer pieceTypeId = null;

                        String pieceTypeIdString = eCompanion.getAttribute("id");
                        if (pieceTypeIdString != null && !pieceTypeIdString.equals("")) {
                            pieceTypeId = Integer.parseInt(pieceTypeIdString);
                        }

                        Position companionPosition = new Position(
                                Integer.parseInt(eCompanion.getAttribute("x")),
                                Integer.parseInt(eCompanion.getAttribute("y"))
                        );

                        Position companionDestination;

                        String xdString = eCompanion.getAttribute("xd");
                        String ydString = eCompanion.getAttribute("yd");

                        if (
                                xdString != null &&
                                        ydString != null &&
                                        !xdString.equals("") &&
                                        !ydString.equals("")
                                ) {
                            companionDestination = new Position(
                                    Integer.parseInt(xdString),
                                    Integer.parseInt(ydString)
                            );
                        } else {
                            companionDestination = companionPosition;
                        }

                        companion = new Entry<SpecialMove.Arrangement, Piece>(
                                new SpecialMove.Arrangement(
                                        companionPosition,
                                        companionDestination
                                ),
                                new Piece(
                                        pieceTypesMap.get(pieceTypeId),
                                        null,
                                        moveId
                                )
                        );
                    }


                    List<Position> free = new ArrayList<Position>();

                    NodeList eFrees = eSpecial.getElementsByTagName("free");

                    for (int j = 0; j < eFrees.getLength(); j++) {
                        Element eFree = (Element) eFrees.item(j);
                        int x = Integer.parseInt(eFree.getAttribute("x"));
                        int y = Integer.parseInt(eFree.getAttribute("y"));
                        free.add(new Position(x, y));
                    }

                    int specialId = -1;
                    String idString = eSpecial.getAttribute("id");
                    if (!idString.equals("")) {
                        specialId = Integer.parseInt(idString);
                    }

                    specialMoves.add(new SpecialMove(moveIdSelf, destination, prey, companion, free, specialId));
                }

                int id = Integer.parseInt(ePiece.getAttribute("id"));
                pieceTypesMap.get(id).addSpecialMoves(specialMoves);
            }

            //players
            NodeList ePlayers = rules.getElementsByTagName("player");
            if (ePlayers.getLength() == 0) {
                System.out.println("!!! No players\n");
                return null;
            }

            List<Player> players = new ArrayList<Player>();
            Map<Integer, Player> turns = new HashMap<Integer, Player>();

            int pieceId = 1;

            for (int i = 0; i < ePlayers.getLength(); i++) {
                Element ePlayer = (Element) ePlayers.item(i);
                NodeList ePieces = ePlayer.getElementsByTagName("start");

                int playerId = Integer.parseInt(ePlayer.getAttribute("id")) - 1;

                Player player = new Player(
                        playerId,
                        Player.getColor(ePlayer.getAttribute("color")),
                        ePlayer.getAttribute("name"),
                        Player.getOrientation(ePlayer.getAttribute("orientation")),
                        new ArrayList<Piece>()
                );
                players.add(player);

                for (int j = 0; j < ePieces.getLength(); j++) {
                    Element ePiece = (Element) ePieces.item(j);

                    int x = Integer.parseInt(ePiece.getAttribute("x")) - 1;
                    int y = Integer.parseInt(ePiece.getAttribute("y")) - 1;

                    Piece piece = new Piece(
                            pieceTypesMap.get(Integer.parseInt(ePiece.getAttribute("id"))),
                            player
                    );

                    player.addPiece(piece);
                    board.putPiece(piece, x, y);
                    pieceId++;
                }

                turns.put(Integer.parseInt(ePlayer.getAttribute("turn")), player);
            }

            List turnsList = new ArrayList();
            for (int i = 1; i <= turns.size(); i++) {
                turnsList.add(turns.get(i));
            }

            return new Game(board, pieceTypes, players, turnsList);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
