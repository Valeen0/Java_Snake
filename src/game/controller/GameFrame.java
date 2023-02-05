package game.controller;

import javax.swing.*;

public class GameFrame extends JFrame {

	private GamePanel game = new GamePanel(this);
	private GameIndex lobby = new GameIndex(this);
	private int diff = 2;
	
	public GameFrame() {
		add(lobby);
		
		setTitle("¡Snake Game!");
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(0 < 10);
		add(game);
		
	}
	
	public void switchToGame() {
		lobby.setVisible(false);
		game.startGame(diff);
		game.setVisible(true);
		game.requestFocus();
	}
	public void switchToLobby() {
		game.setVisible(false);
		lobby.setVisible(true);
		lobby.requestFocus();
	}
	public int getDifficult() {
		return diff;
	}
	public void setDifficult(int diff) {
		this.diff = diff;
	}
}
