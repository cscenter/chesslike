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
import java.util.Iterator;

@RunWith(JUnit4.class)
public class TestPieceType {
    private Game game;
	
	/*
	@Before
	public void setup() {
		game = RulesParser.parse("./rules/Chess.xml");
	}
	*/
	
    @Test
    public void testChessPieceType() {
		game = RulesParser.parse("./rules/Chess.xml");
		//
		List<PieceType> allTypes = game.getPieceTypes();
		Iterator<PieceType> it = allTypes.iterator();
		//
		int id;
		String name;
		//
		while (it.hasNext()) {
			PieceType ownType = it.next();
			id = ownType.getId();
			name = ownType.getName();
			System.out.print(id);
			System.out.print("  ");
			System.out.print(name);
			System.out.print('\n');
		}
		assertTrue(true);
    } 
	
	@Test
	public void testChessBoardPieceType() {
		game = RulesParser.parse("./rules/Chess.xml");
		Board ownBoard = game.getBoard();
		//
		int xSize = ownBoard.getXSize();
		int ySize = ownBoard.getYSize();
		//
		System.out.print(xSize);
		System.out.print('\n');
		System.out.print(ySize);
		System.out.print('\n');
		Piece ownPiece = new Piece();
		for (int y = 0; y < ySize; ++y) {
			for (int x = 0; x < xSize; ++x) {
				ownPiece = ownBoard.getPiece(x, y);
				PieceType ownType = ownPiece.getPieceType();
				if (ownType != null) {
					System.out.print(ownType.getId());
				}
				else {
					System.out.print('-');
				}
			}
			System.out.print('\n');
		}
		assertTrue(true);
	}
}
