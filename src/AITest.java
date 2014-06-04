
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
import model.set.Board;
import model.set.Piece;
import model.set.PieceType;
import model.set.Player;
import AI.AI;
import AI.MiniMaxAI;
import AI.RandomAI;
import AI.AlphaBetaAI;

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

public class AITest {
	
	public static void main(String[] args) {
		
        Game game = RulesParser.parse("./rules/Chess.xml");
			
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			
		try {
			//AI firstAI = new MiniMaxAI(2, 1);
			//AI secondAI = new AlphaBetaAI(4, 2);
			
			//AI firstAI = new AlphaBetaAI(4, 2);
			//AI secondAI = new MiniMaxAI(2, 1);
			
			//AI firstAI = new MiniMaxAI(4, 1);
			//AI secondAI = new AlphaBetaAI(6, 2);
			
			//AI firstAI = new MiniMaxAI(4, 2);
			//AI secondAI = new MiniMaxAI(2, 1);
			
			AI firstAI = new MiniMaxAI(2, 2);
			AI secondAI = new AlphaBetaAI(4, 1);
			
			//AI firstAI = new MiniMaxAI(2, 2);
			//AI secondAI = new MiniMaxAI(4, 1);
			int i = 0;
			long firstTime = 0;
			long secondTime = 0;
			long first;
			while (game.endOfGame().getKey() == (Boolean) false) {
				if (i % 2 == 0) {
					++i;
					
					first = System.currentTimeMillis();
					
					Route answer =  firstAI.getRoute(game);
					System.out.print(answer.getStartPosition() + "    " + answer.getDestPosition());
					System.out.print('\n');
					
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					
					System.out.print(game.move(start, dest) + "  turn number: " + i);
					System.out.print('\n');
					System.out.println(TextViewer.print(game.print(), ""));
					
					firstTime = firstTime + System.currentTimeMillis() - first;
					
				} else {
					++i;
				
					first = System.currentTimeMillis();
				
					Route answer =  secondAI.getRoute(game);
					System.out.print(answer.getStartPosition() + "    " + answer.getDestPosition());
					System.out.print('\n');
					
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					
					System.out.print(game.move(start, dest) + "  turn number: " + i);
					System.out.print('\n');
					System.out.println(TextViewer.print(game.print(), ""));
					
					secondTime = secondTime + System.currentTimeMillis() - first;
				}
			}
			
			System.out.print("firstTime: " + firstTime + '\n');
			System.out.print("secondTime: " + secondTime + '\n');
			
		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}	
}

