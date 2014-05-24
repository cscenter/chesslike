
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
import AI.MiniMaxAI;
import AI.RandomAI;
import AI.AlphaBetaAI;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

import java.io.File;
import javax.swing.JOptionPane;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AITest {
	
	public static void main(String[] args) {
		
		Date myDate = new Date();
		System.out.print(myDate.getTime() % 100000);
		System.out.print('\n');
		
        Game game = RulesParser.parse("./rules/Chess.xml");
			
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			
		try {
			AI ownAI = new MiniMaxAI(4, 1);
			//AI ownAI = new MiniMaxAI(4, 2);
			AI randomPlayer = new AlphaBetaAI(6, 5);
			int i = 0;
			while (game.endOfGame().getKey() == (Boolean) false) {
				if (i % 2 == 0) {
					++i;
					
					Route answer =  ownAI.getRoute(game);
					System.out.print(answer.getStartPosition() + "    " + answer.getDestPosition());
					System.out.print('\n');
					
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					
					System.out.print(game.move(start, dest) + "  turn number: " + i);
					System.out.print('\n');
					System.out.println(TextViewer.print(game.print(), ""));
					
				} else {
					++i;
				
					Route answer =  randomPlayer.getRoute(game);
					System.out.print(answer.getStartPosition() + "    " + answer.getDestPosition());
					System.out.print('\n');
					
					Position start = answer.getStartPosition();
					Position dest = answer.getDestPosition();
					
					System.out.print(game.move(start, dest) + "  turn number: " + i);
					System.out.print('\n');
					System.out.println(TextViewer.print(game.print(), ""));
				}
			}
			
			myDate = new Date();
			System.out.print(myDate.getTime() % 100000);
			System.out.print('\n');
			
		} catch (Exception e) {
			System.out.print(e.toString());
		}
	}	
}

