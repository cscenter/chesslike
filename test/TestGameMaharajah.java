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
		int yBlack = 6;
		boolean expected = true;
		boolean result;
		for (int x = 0; x < 8; ++x) {
			game = RulesParser.parse("./rules/Maharajah.xml");
			result = game.move(new Position(x, yBlack), new Position(x, yBlack - 1));
			assertTrue("turn from " + x + " " + yBlack + " to " + x + " " + (yBlack - 1) + " is not correct", (expected == result));
			game = RulesParser.parse("./rules/Maharajah.xml");
			result = game.move(new Position(x, yBlack), new Position(x, yBlack - 2));
			assertTrue("turn from " + x + " " + yBlack + " to " + x + " " + (yBlack - 2) + " is not correct", (expected == result));
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
		assertEquals(expected, result);
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
    } 
}
