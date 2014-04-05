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
    public void test01Move() {
		game = RulesParser.parse("./rules/Maharajah.xml");
        int x = 4;
        int y = 7;
        int xDest = 4;
        int yDest = 5;
        
        boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
        boolean expected = true;
		assertEquals(expected, result);
		System.out.print("test01 passed");
    } 
	
	@Test
	public void test02Move() {
		game = RulesParser.parse("./rules/Maharajah.xml");
        int x = 4;
        int y = 7;
        int xDest = 4;
        int yDest = 6;
        
        boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
        boolean expected = true;
        //assertTrue("Error", expected == result);
		assertEquals(expected, result);
		System.out.print(" test02 passed");
    } 
	
	@Test
	public void test03Move() {
		game = RulesParser.parse("./rules/Maharajah.xml");
        int x = 2;
        int y = 7;
        int xDest = 2;
        int yDest = 6;
        
        boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
        boolean expected = true;
        //assertTrue("Error", expected == result);
		if(result != expected) {
			fail("Not equals in 03");
		}
		System.out.print(" test03 passed");
    } 
	
	@Test
	public void test04Move() {
		game = RulesParser.parse("./rules/Maharajah.xml");
        int x = 5;
        int y = 1;
        int xDest = 5;
        int yDest = 2;
        
        boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
        boolean expected = true;
        //assertTrue("Error", expected == result);
		if(result != expected) {
			fail("Not equals in 04");
		}
		System.out.print(" test04 passed");
    } 
}
