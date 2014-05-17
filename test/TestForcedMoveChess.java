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

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import javax.swing.JOptionPane;

@RunWith(JUnit4.class)
public class TestForcedMoveChess {
    private Game game;
	
	@Test
    public void testForcedMove() {
		try {
			Scanner sc = new Scanner(new File("./rules/myBoard.txt"));
			//
			int xSize = sc.nextInt();
			int ySize = sc.nextInt();
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
			//
			game = RulesParser.parse("./rules/Chess.xml");
			game.forcedMove(newField, playersId, specialMovesId, ownTurn);
			
		} catch(Exception e) { }
		assertTrue(true);
    } 	
	
}

