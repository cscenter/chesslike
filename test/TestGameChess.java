/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 */
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
//
import model.Game;
import IO.RulesParser;
import IO.TextViewer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import model.coord.Entry;
import model.coord.Position;
import model.moves.Move;
import model.set.Board;
import model.set.Piece;
import model.set.PieceType;
import model.set.Player;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

@RunWith(JUnit4.class)
public class TestGameChess {
    private Game game;
	private Game compareGame;
	private Board ownBoard;
	private Piece ownPiece;
	
	@Test
    public void testPawnPossibleMove() {
		int yWhite = 1;
		int yBlack = 6;
		boolean expected = true;
		boolean result;
		for (int x = 0; x < 8; ++x) {
			game = RulesParser.parse("./rules/Chess.xml");
			result = game.move(new Position(x, yWhite), new Position(x, yWhite + 1));
			assertEquals("turn is not correct", expected, result);
			result = game.move(new Position(x, yBlack), new Position(x, yBlack - 1));
			assertEquals("turn is not correct", expected, result);
			game = RulesParser.parse("./rules/Chess.xml");
			result = game.move(new Position(x, yWhite), new Position(x, yWhite + 2));
			assertEquals("turn is not correct", expected, result);
			result = game.move(new Position(x, yBlack), new Position(x, yBlack - 2));
			assertEquals("turn is not correct", expected, result);
		}
    } 
	
	@Test
    public void testPawnImpossibleMove() {
		game = RulesParser.parse("./rules/Chess.xml");
		int yWhite = 1;
		boolean expected = false;
		boolean result;
		//
		for (int x = 0; x < 8; ++x) {
			for (Integer xDest = 0; xDest < 8; ++xDest) {
				for (Integer yDest = 0; yDest < 8; ++yDest) {
					if ( (xDest != x) || ( (yDest != yWhite + 1) && (yDest != yWhite + 2) ) ) {
						result = game.move(new Position(x, yWhite), new Position(xDest, yDest));
						assertEquals("turn is not correct", expected, result);
					}
				}
			}
		}
		//
		game.forcedTurn();
		int yBlack = 6;
		for (int x = 0; x < 8; ++x) {
			for (Integer xDest = 0; xDest < 8; ++xDest) {
				for (Integer yDest = 0; yDest < 8; ++yDest) {
					if ( (xDest != x) || ( (yDest != yWhite - 1) && (yDest != yWhite - 2) ) ) {
						result = game.move(new Position(x, yWhite), new Position(xDest, yDest));
						assertEquals("turn is not correct", expected, result);
					}
				}
			}
		}
	}
	
	@Test
    public void testImpossibleMove() {
		try {
			game = RulesParser.parse("./rules/Chess.xml");
			Scanner sc = new Scanner(new File("./test/impossibleMoveBoard.txt"));
			//
			int xSize = 8;
			int ySize = 8;
			//
			int [][] newField = new int[xSize][ySize];
			for (int y = ySize - 1; y > -1; --y) {
				for (int x = 0; x < xSize; ++x) {
					newField[x][y] = sc.nextInt();
				}
			}
			//
			int [][] playersId = new int[xSize][ySize];
			for (int y = ySize - 1; y > -1; --y) {
				for (int x = 0; x < xSize; ++x) {
					playersId[x][y] = sc.nextInt();
				}
			}
			//
			int [][] specialMovesId = new int[xSize][ySize];
			for (int y = ySize - 1; y > -1; --y) {
				for (int x = 0; x < xSize; ++x) {
					specialMovesId[x][y] = sc.nextInt();
				}
			}
			int ownTurn = sc.nextInt();
			
			sc.close();
			
			game.forcedMove(newField, playersId, specialMovesId, ownTurn);
		} catch(Exception e) {
			System.out.print("File not found");
		}
		boolean expected = false;
		//Bishop
		boolean result = game.move(new Position(4, 2), new Position(2, 0));
		assertEquals("Bishop have captured own Pawn", expected, result);
		
		result = game.move(new Position(4, 2), new Position(6, 1));
		assertEquals("Bishops`s move is incorrect", expected, result);
		
		result = game.move(new Position(4, 2), new Position(7, -1));
		assertEquals("Bishop have moved out of board", expected, result);
		//Knight
		result = game.move(new Position(3, 2), new Position(2, 0));
		assertEquals("Knight have captured own Pawn", expected, result);
		
		result = game.move(new Position(3, 2), new Position(4, 0));
		assertEquals("Knight have captured own Rock", expected, result);
		
		result = game.move(new Position(6, 1), new Position(7, -1));
		assertEquals("Knight have moved out of board", expected, result);
		//Rock
		result = game.move(new Position(4, 0), new Position(4, 2));
		assertEquals("Rock have captured own Bishop", expected, result);
		
		result = game.move(new Position(4, 0), new Position(4, 4));
		assertEquals("Rock`s move is incorrect", expected, result);
		//Pawn
		result = game.move(new Position(2, 0), new Position(2, 1));
		assertEquals("Pawns`s move is incorrect", expected, result);
		
		result = game.move(new Position(2, 0), new Position(2, 2));
		assertEquals("Pawns`s move is incorrect", expected, result);
		
		result = game.move(new Position(2, 0), new Position(2, -1));
		assertEquals("Pawn have moved out of board", expected, result);
	}
	
	@Test
	public void testDebuts() {
		try {
			compareGame = RulesParser.parse("./rules/Chess.xml");
			Scanner sc = new Scanner(new File("./test/Debuts.txt"));
			int debutsNumber = sc.nextInt();
		
			int xSize = 8;
			int ySize = 8;
			
			for (int i = 0; i < debutsNumber; ++i) {
				game = RulesParser.parse("./rules/Chess.xml");
			
				int [][] newField = new int[xSize][ySize];
				for (int y = ySize - 1; y > -1; --y) {
					for (int x = 0; x < xSize; ++x) {
						newField[x][y] = sc.nextInt();
					}
				}
			
				int [][] playersId = new int[xSize][ySize];
				for (int y = ySize - 1; y > -1; --y) {
					for (int x = 0; x < xSize; ++x) {
						playersId[x][y] = sc.nextInt();
					}
				}
			
				int [][] specialMovesId = new int[xSize][ySize];
				for (int y = ySize - 1; y > -1; --y) {
					for (int x = 0; x < xSize; ++x) {
						specialMovesId[x][y] = sc.nextInt();
					}
				}
				int ownTurn = sc.nextInt();
				
				compareGame.forcedMove(newField, playersId, specialMovesId, ownTurn);
				
				//
				int turnsNumber = sc.nextInt();
				int x, y, xDest, yDest;
				for (int j = 0; j < turnsNumber; ++j) {
					x = sc.nextInt();
					y = sc.nextInt();
					xDest = sc.nextInt();
					yDest = sc.nextInt();
					assertTrue("failed in " + (i + 1) + " debut in " + (j + 1) + " turn", game.move(x, y, xDest, yDest));
				}
				
				Board gameBoard = game.getBoard();
				Board compareBoard = compareGame.getBoard();
				/*
				String [][] reflex = game.print();
				for (int k = ySize - 1; k > - 1; --k) {
					for (int l = 0; l < xSize; ++l) {
						System.out.print(reflex[l][k]);
					}
					System.out.print('\n');
				}
				*/
				assertTrue("Game is not correct", gameBoard.equals(compareBoard));
			}
			sc.close();
		} catch(Exception e) {
			System.out.print(e.toString());
		}
	}
}
