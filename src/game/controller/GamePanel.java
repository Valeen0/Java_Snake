package game.controller;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
	
	private static final int SCREEN_WIDTH = 500;
	private static final int SCREEN_HEIGHT = 500;
	private static final int UNIT_SIZE = 20;
	private static final int GAME_UNITS = (SCREEN_HEIGHT *  SCREEN_HEIGHT)/UNIT_SIZE;
	private static final Color APPLECOLOR = new Color(240, 90, 70);
	private static final Color HEADCOLOR = new Color(120, 250, 60);
	private static final Color BODYCOLOR = new Color(95, 200, 50);
	private static final Color SCORECOLOR = new Color(222, 233, 65);
	private static final Color BG_COLOR = new Color(40,40,40);
	private static int delay = 0;	
	private final int x[] = new int[GAME_UNITS];
	private final int y[] = new int[GAME_UNITS];
	private int bodyParts = 6;
	private int applesEaten;
	private int appleX;
	private int appleY;
	private char direction = 'R';
	private boolean running = false;
	private Timer timer;
	private Random random;
	private boolean keyInput = false;
	private GameFrame parentFrame;
	private int difficult;
	
	public GamePanel(JFrame parentFrame) {
		this.parentFrame = (GameFrame) parentFrame;
		difficult = ((GameFrame) parentFrame).getDifficult();
		random = new Random();
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setFocusable(true);
		setBackground(BG_COLOR);
		this.addKeyListener(new MyKeyAdapter());
	}
	
	public void startGame(int diff) {
		
		switch(diff) {
		case 1: delay = 100; break;
		case 2: delay = 75; break;
		case 3: delay = 50; break;
		case 4: delay = 30; break;
		}
		
		bodyParts = 6;
		applesEaten = 0;
		
		for(int i = 0; i < bodyParts; i++) {
			x[i] = 0;
			y[i] = 0;
		}
		
		direction = 'R';
		
		newApple();
		running = true;
		timer = new Timer(delay, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running) {
			
			// Dibuja una cuadrícula.
			/*
			for(int i = 0; i<SCREEN_WIDTH/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}*/
			
			g.setColor(APPLECOLOR);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			// Dibujo de la serpiente.
			for(int i = 0; i<bodyParts; i++) {
				if(i == 0) {
					g.setColor(HEADCOLOR);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}else {
					g.setColor(BODYCOLOR);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			g.setColor(SCORECOLOR);
			g.setFont(new Font("MS Gothic", Font.PLAIN, 20));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString(
					"Score: " + applesEaten,
					SCREEN_WIDTH - metrics1.stringWidth("SCORE: " + applesEaten ) - 10,
					g.getFont().getSize());
			
			FontMetrics fontSize = g.getFontMetrics();
			
			if(!timer.isRunning()) {
				 g.setColor(Color.white);
		         g.setFont(new Font("MS Gothic", Font.PLAIN, 58));
		         
		         fontSize = g.getFontMetrics();
		         int fontX = (SCREEN_WIDTH - fontSize.stringWidth("Game Over :(")) / 2 ;
		         int fontY = (SCREEN_HEIGHT - fontSize.getHeight()) /2;
		         
		         g.drawString("Game Over :(", fontX, fontY);
		         
		         g.setFont(new Font("MS Gothic", Font.PLAIN, 24));
		         fontSize = g.getFontMetrics();
		         
		         fontX = (SCREEN_WIDTH - fontSize.stringWidth("Press F2 to restart")) / 2 ;
		         fontY = fontY + fontSize.getHeight() + 20;
		         g.drawString("Press F2 to restart", fontX, fontY);
			}
			
		}else {
			gameOver();
		}
	}
	public void newApple() {
		
		// Defino la ubicación de la manzana.
		appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
		appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
		
	}
	public void move() {
		for(int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] -= UNIT_SIZE;
			break;
		case 'D':
			y[0] += UNIT_SIZE;
			break;
		case 'L':
			x[0] -= UNIT_SIZE;
			break;
		case 'R':
			x[0] += UNIT_SIZE;
			break;
		}
		keyInput = false;
	}
	public void eatApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void collisions() {
		
		if(x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT ) {
			gameOver();
		}
		
		// Revisa que la cabeza no choque con el cuerpo.
		for(int i = bodyParts ; i>0; i--) {
			if(x[0] == x[i] && y[0] == y[i]) {
				gameOver();
			}
		}
			
	}
	public void gameOver() {
		if(timer != null) {
			timer.stop();
		}
		// Game Over :C
		/*
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics2.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT/2);
	*/}
	
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			eatApple();
			collisions();
		}
		repaint();
	}
	
	private class MyKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent k) {
			switch(k.getKeyCode()){
            case (KeyEvent.VK_DOWN):
                if(direction != 'U' && keyInput == false){
                    direction = 'D';
                    keyInput = true;
                }
                break;
            case (KeyEvent.VK_UP):
                if(direction != 'D' && !keyInput){
                    direction = 'U';
                    keyInput = true;
                }
                break;
            case (KeyEvent.VK_LEFT):
                if(direction != 'R' && keyInput == false){
                    direction = 'L';
                    keyInput = true;
                }
                break;
            case (KeyEvent.VK_RIGHT):
                if(direction != 'L' && keyInput == false){
                    direction = 'R';
                    keyInput = true;
                }
                break;
            case (KeyEvent.VK_F2):
                if(!timer.isRunning()){
                    startGame(difficult);
                }
                break;
            case KeyEvent.VK_ESCAPE:
				parentFrame.switchToLobby();
				break;
			}
		}
	}
}
