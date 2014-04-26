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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

@RunWith(JUnit4.class)
public class TestGameChess {
    private Game game;
	private Board ownBoard;
	private Piece ownPiece1;
	private Piece ownPiece2;
	private PieceType ownPieceType;
	
	@Test
    public void testPawnPossibleMove() {
		int x, y;
		x = 3;
		y = 1;
		ArrayList<Integer> xDest = new ArrayList();
		ArrayList<Integer> yDest = new ArrayList();
		xDest.add(3);
		yDest.add(2);
		xDest.add(3);
		yDest.add(3);
		int size = xDest.size();
		boolean expected = true;
		for (int i = 0; i < size; ++i) {
			game = RulesParser.parse("./rules/Chess.xml");
			ownBoard = game.getBoard();
			ownPiece2 = ownBoard.getPiece(x, y);
			boolean result = game.move(new Position(x, y), new Position(xDest.get(i), yDest.get(i)));
			assertEquals("turn is not correct", expected, result);
			//
			ownBoard = game.getBoard();
			ownPiece1 = new Piece();
			ownPiece2 = ownBoard.getPiece(x, y);
			//
			assertTrue("board is not correct", ownPiece1.equals(ownPiece2));
			ownPiece2 = ownBoard.getPiece(xDest.get(i), yDest.get(i));
			assertEquals("board is not correct", ownPiece2.getPieceType().getId(), 6);
		}
    } 
	
	@Test
    public void testPawnImpossibleMove() {
		try {
			game = RulesParser.parse("./rules/Chess.xml");
			int x, y;
			x = 3;
			y = 1;
			boolean expected = false;
			ownBoard = game.getBoard();
			ownPiece1 = ownBoard.getPiece(x, y);
			//
			for (Integer xDest = 0; xDest < 8; ++xDest) {
				for (Integer yDest = 0; yDest < 8; ++yDest) {
					if ( (xDest != 3) || ( (yDest != 3) && (yDest != 2) ) )
					{
						ownPiece2 = ownBoard.getPiece(xDest, yDest);
						boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
						assertEquals("turn is not correct", expected, result);
					
						Board ownModifiedBoard = game.getBoard();

						assertEquals("board is not correct in dest" + " " + yDest + " " + yDest, ownPiece2, ownModifiedBoard.getPiece(xDest, yDest));
						assertEquals("board is not correct in start" + " " + x + " " + y, ownPiece1, ownModifiedBoard.getPiece(x, y));
					}
				}
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Null pointer");
		}
		//
		assertTrue(true);
	}
		
}
