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
			for (int y = 0; y < ySize; ++y) {
				for (int x = 0; x < xSize; ++x) {
					newField[x][y] = sc.nextInt();
				}
			}
			//
			int [][] playersId = new int[xSize][ySize];
			for (int y = 0; y < ySize; ++y) {
				for (int x = 0; x < xSize; ++x) {
					playersId[x][y] = sc.nextInt();
				}
			}
			sc.close();
			//
			game = RulesParser.parse("./rules/Chess.xml");
			game.forcedMove(newField, playersId, 0);
			
			/*
			Board ownBoard = game.getBoard();
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
			*/
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "File not found");
		}
		assertTrue(true);
    } 	
	
	@Ignore
	@Test
	public void testForcedMove01() {
		int xSize = 8;
		int ySize = 8;
		int [][] newField = new int[xSize][ySize];
		newField[0][1] = 6;
		newField[1][1] = 6;	
		newField[2][1] = 6;
		newField[3][1] = 6;	
		newField[4][1] = 6;
		newField[5][1] = 6;	
		newField[6][1] = 6;
		newField[7][1] = 6;	
		newField[0][6] = 6;
		newField[1][6] = 6;	
		newField[2][6] = 6;
		newField[3][6] = 6;	
		newField[4][6] = 6;
		newField[5][6] = 6;	
		newField[6][6] = 6;
		newField[7][6] = 6;	
		
		int [][] playersId = new int[xSize][ySize];
		playersId[0][1] = 2;
		playersId[1][1] = 2;
		playersId[2][1] = 2;
		playersId[3][1] = 2;
		playersId[4][1] = 2;
		playersId[5][1] = 2;
		playersId[6][1] = 2;
		playersId[7][1] = 2;
		playersId[0][6] = 1;
		playersId[1][6] = 1;
		playersId[2][6] = 1;
		playersId[3][6] = 1;
		playersId[4][6] = 1;
		playersId[5][6] = 1;
		playersId[6][6] = 1;
		playersId[7][6] = 1;
		
		game = RulesParser.parse("./rules/Chess.xml");
		game.forcedMove(newField, playersId, 0);
			//
		Board ownBoard = game.getBoard();
		System.out.print(xSize);
		System.out.print('\n');
		System.out.print(ySize);
		System.out.print('\n');
		Piece ownPiece = new Piece();
		for (int y = ySize - 1; y > -1; --y) {
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
		for (int y = ySize - 1; y > -1; --y) {
			for (int x = 0; x < xSize; ++x) {
				ownPiece = ownBoard.getPiece(x, y);
				if (ownPiece != null) {
					if (ownPiece.getPlayer() != null) {
						System.out.print(ownPiece.getPlayer().getId());
					} else {
						System.out.print('-');
					}
				} else {
					System.out.print('-');
				}
			}
			System.out.print('\n');
		}
		assertTrue(true);
	}
}

