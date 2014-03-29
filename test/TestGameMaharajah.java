/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Денис
 */
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import game.Game;
import board.Board;
import move.Move;
import piece.Piece;
import piece.PieceType;
import player.Player;
import player.ParameterProcessor;
import board.Position;

import java.util.Map;
import java.util.Vector;
import parsing.RulesParser;

@RunWith(JUnit4.class)
public class TestGameMaharajah {
    private Game game;
	
    @Test
    public void testMove() {
		game = RulesParser.parse("./rules/Maharajah.xml");
        int x = 1;
        int y = 7;
        int xDest = 1;
        int yDest = 5;
        
        boolean result = game.move(new Position(x, y), new Position(xDest, yDest));
        boolean expected = false;
        assertTrue(result);
    } 
}
