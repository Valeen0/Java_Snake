package game.controller;

import game.execute.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameIndex extends JPanel {
	
	private static final int SCREEN_WIDTH = 500;
	private static final int SCREEN_HEIGHT = 500;
	private static final Dimension DIMENSION_BUTTONS = new Dimension(350,30);
	private static final Color BG_COLOR = new Color(40, 40, 40);
	private static final Color BTN_COLOR = new Color(210, 210, 210);
	private static final Font BTN_FONT = new Font("Arial", Font.PLAIN, 18);
	
	private JPanel titlePanel;
	private JPanel buttonsPanel;
	private JPanel creditsPanel;
	private GameFrame parentFrame;
	private JButton initGame;
	
	public GameIndex(JFrame parentFrame) {
		this.parentFrame = (GameFrame) parentFrame;
		
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setFocusable(true);
		setBackground(BG_COLOR);
		
		setLayout(new BorderLayout());
		
		titlePanel = new JPanel();
		JLabel titleGame = new JLabel("Snake Game");
		titleGame.setForeground(new Color(130, 241, 69));
		titleGame.setFont(new Font("Ink Free", Font.BOLD, 40));
		
		titlePanel.add(titleGame);
		titlePanel.setBackground(BG_COLOR);
		add(titlePanel, BorderLayout.NORTH);
		
		buttonsPanel = new JPanel();
		buttonsPanel.setBackground(BG_COLOR);
		
		initGame = new JButton("Comenzar");
		initGame.addActionListener(new ButtonEvents());
		initGame.setPreferredSize(DIMENSION_BUTTONS);
		initGame.setBackground(BTN_COLOR);
		initGame.setFont(BTN_FONT);
		buttonsPanel.add(initGame);
		
		JMenuBar barraMenu = new JMenuBar();
		barraMenu.setPreferredSize(DIMENSION_BUTTONS);
		barraMenu.setBackground(BTN_COLOR);
		JMenu velocidades = new JMenu("Velocidad: Estándar");
		velocidades.setPreferredSize(DIMENSION_BUTTONS);
		
		barraMenu.add(velocidades);
		
		crearDificultad(velocidades, "Lento", "1");
		crearDificultad(velocidades, "Estándar", "2");
		crearDificultad(velocidades, "Rapidisimo", "3");
		crearDificultad(velocidades, "Flash?", "4");
		
		buttonsPanel.add(barraMenu);
		add(buttonsPanel, BorderLayout.CENTER);
		
		creditsPanel = new JPanel();
		creditsPanel.setBackground(this.getBackground());
		JLabel creditLabel = new JLabel("Made by Valen");
		creditLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		creditLabel.setForeground(Color.GREEN);
		
		creditsPanel.add(creditLabel);
		add(creditsPanel, BorderLayout.SOUTH);
	}
	private void crearDificultad(JMenu menu, String diffText, String dificultad) {
		JMenuItem item = new JMenuItem(diffText);
		item.setActionCommand(dificultad);
		item.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				menu.setText("Velocidad: " + item.getText());
				int diff = Integer.parseInt(e.getActionCommand());
				System.out.println(diff);
				parentFrame.setDifficult(diff);
			}
			
		});
		menu.setFont(BTN_FONT);
		menu.add(item);
		item.setFont(BTN_FONT);
		item.setBackground(BTN_COLOR);
		item.setPreferredSize(DIMENSION_BUTTONS);
	}
	
	private class ButtonEvents implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.out.println("click " + e.getActionCommand());
			
			if(e.getActionCommand().equals("Comenzar")) {
				parentFrame.switchToGame();
			}
		}
		
	}
	
}
