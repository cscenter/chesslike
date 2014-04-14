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

@RunWith(JUnit4.class)
public class TestGameMaharajah {
    private Game game;
	
	@Test
    public void testPawnPossibleMove() {
		int x, y;
		x = 3;
		y = 6;
		ArrayList<Integer> xDest = new ArrayList();
		ArrayList<Integer> yDest = new ArrayList();
		xDest.add(3);
		yDest.add(5);
		xDest.add(3);
		yDest.add(4);
		int size = xDest.size();
		boolean expected = true;
		for (int i = 0; i < size; ++i) {
			game = RulesParser.parse("./rules/Maharajah.xml");
			boolean result = game.move(new Position(x, y), new Position(xDest.get(i), yDest.get(i)));
			assertEquals(expected, result);
		}
    } 
	
	@Ignore
    @Test
    public void test01Move() {
		game = RulesParser.parse("./rules/Maharajah.xml");
        int x = 3;
        int y = 6;
        int xDest = 3;
        int yDest = 4;
        
        boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
        boolean expected = true;
		assertEquals(expected, result);
		System.out.print("test01 passed");
    } 
	
	@Ignore
	@Test
	public void test02Move() {
		game = RulesParser.parse("./rules/Maharajah.xml");
        int x = 3;
        int y = 6;
        int xDest = 3;
        int yDest = 5;
        String piece = game.getBoard().getPiece(3, 6).getPieceType().getName();
        boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
        boolean expected = true;
        //assertTrue("Error", expected == result);
		assertEquals(expected, result);
		System.out.print(piece);
		System.out.print(" test02 passed");
    } 
	
	@Ignore
	@Test
	public void test03Move() {
		game = RulesParser.parse("./rules/Maharajah.xml");
        int x = 1;
        int y = 6;
        int xDest = 1;
        int yDest = 5;
        
        boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
        boolean expected = true;
        //assertTrue("Error", expected == result);
		if(result != expected) {
			fail("Not equals in 03");
		}
		System.out.print(" test03 passed");
    }
	
	@Ignore
	@Test
	public void test04Move() {
		game = RulesParser.parse("./rules/Maharajah.xml");
        int x = 4;
        int y = 0;
        int xDest = 4;
        int yDest = 1;
        
        boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
        boolean expected = true;
        //assertTrue("Error", expected == result);
		if(result != expected) {
			fail("Not equals in 04");
		}
		System.out.print(" test04 passed");
    } 
}
