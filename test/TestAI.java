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

import model.coord.Route;
import model.coord.Entry;
import model.coord.Position;
import model.moves.Move;
import model.set.Board;
import model.set.Piece;
import model.set.PieceType;
import model.set.Player;
import AI.AI;
import AI.AlphaBetaAI;
import AI.MiniMaxAI;
import AI.RandomAI;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import javax.swing.JOptionPane;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@RunWith(JUnit4.class)
public class TestAI {
    private Game game;
	
	@Test
    public void testMiniMaxRandomPlayer() {
		game = RulesParser.parse("./rules/Chess.xml");
		
		try {
			AI ownAI = new MiniMaxAI(4, 2);
			AI randomPlayer = new RandomAI(17);
			int i = 0;
			while (game.endOfGame().getKey() == (Boolean) false) {
				if (i % 2 == 0) {
					++i;
					
					Route answer =  ownAI.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
					
				} else {
					++i;
					
					Route answer =  randomPlayer.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
				}
			}
			assertTrue("MiniMax is failed", i % 2 == 1);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}

	@Test
    public void testAlphaBetaRandomPlayer() {
		game = RulesParser.parse("./rules/Chess.xml");
		
		try {
			AI ownAI = new AlphaBetaAI(4, 2);
			AI randomPlayer = new RandomAI(17);
			int i = 0;
			while (game.endOfGame().getKey() == (Boolean) false) {
				if (i % 2 == 0) {
					++i;
					
					Route answer =  ownAI.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
					
				} else {
					++i;
					
					Route answer =  randomPlayer.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
				}
			}
			assertTrue("AlphaBeta is failed", i % 2 == 1);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}
	
	@Test
    public void testAlphaBeta() {
		game = RulesParser.parse("./rules/Chess.xml");
		
		try {
			AI firstAI = new AlphaBetaAI(4, 2);
			AI secondAI = new AlphaBetaAI(2, 1);
			int i = 0;
			while (game.endOfGame().getKey() == (Boolean) false) {
				if (i % 2 == 0) {
					++i;
					
					Route answer =  firstAI.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
					
				} else {
					++i;
					
					Route answer =  secondAI.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
				}
			}
			assertTrue("Deeper AI is failed", i % 2 == 1);
		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}
	
	@Test
    public void testAlphaBetaTime() {
		game = RulesParser.parse("./rules/Chess.xml");
		long alphaBetaTime = 0;
		
		try {
			AI firstAI = new AlphaBetaAI(4, 2);
			AI secondAI = new MiniMaxAI(2, 1);
			int i = 0;
			long first;
			while (game.endOfGame().getKey() == (Boolean) false) {
				if (i % 2 == 0) {
					++i;
					
					first = System.currentTimeMillis();
					
					Route answer =  firstAI.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
					
					alphaBetaTime = alphaBetaTime + System.currentTimeMillis() - first;
					
				} else {
					++i;
					
					Route answer =  secondAI.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		
		game = RulesParser.parse("./rules/Chess.xml");
		long miniMaxTime = 0;
		
		try {
			AI firstAI = new MiniMaxAI(4, 2);
			AI secondAI = new MiniMaxAI(2, 1);
			int i = 0;
			long first;
			while (game.endOfGame().getKey() == (Boolean) false) {
				if (i % 2 == 0) {
					++i;
					
					first = System.currentTimeMillis();
					
					Route answer =  firstAI.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
					
					miniMaxTime = miniMaxTime + System.currentTimeMillis() - first;
					
				} else {
					++i;
					
					Route answer =  secondAI.getRoute(game);
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					game.move(start, dest);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		
		assertTrue("AlphaBeta is slower", alphaBetaTime < miniMaxTime);
		
	}
}

