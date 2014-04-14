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
import model.set.Player.Color;
import model.set.Player.Orientation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

@RunWith(JUnit4.class)
public class TestTurns {
    private Game game;
	
	/*
	@Before
	public void setup() {
		game = RulesParser.parse("./rules/Chess.xml");
	}
	*/
	
    @Test
    public void testInitialTurns() {
		game = RulesParser.parse("./rules/Chess.xml");
		List<Player> ownTurns = game.getTurns();
		Iterator<Player> it = ownTurns.iterator();
		//
		while (it.hasNext()) {
			Player ownPlayer = it.next();
			int id = ownPlayer.getId();
			System.out.print(id);
			System.out.print("  ");
			Color color = ownPlayer.getColor();
			System.out.print(color);
			System.out.print("  ");
			String name = ownPlayer.getName();
			System.out.print(name);
			System.out.print("  ");
			Orientation orientation = ownPlayer.getOrientation();
			System.out.print(orientation);
			System.out.print("  ");
			System.out.print('\n');
		}
		int currentTurn = game.getCurrentTurn();
		System.out.print(currentTurn);
		assertTrue(true);
    } 
}
