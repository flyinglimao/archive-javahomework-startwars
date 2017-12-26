package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;

import javax.swing.*;

import org.magiclen.magicaudioplayer.AudioPlayer;

import java.util.prefs.*;

import pages.*;
import player.*;

public class MainPanel extends JPanel {
	private final int width = 595, height = 692;
	private BufferedImage bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);;
	private Graphics myBuffer = bufferImage.getGraphics();
	private int img = 0;
	private int heal = 0;
	private int atk = 0;
	private int addScore = 0;
	private int ratechange = 0;
	private Preferences gameConf = Preferences.userNodeForPackage(EntryPoint.class);
	private Timer timer = new Timer(20, new onePlayer(myBuffer, height, width) {
		@Override
		public void repainter() {
			repaint();
		}

		@Override
		public void onStop() {
			frameControl(4);
		}
	});
	private String cheatBuffer = "";

	public MainPanel() {
		AudioPlayer k = AudioPlayer.createPlayer(new File("source/a/Bonus/bg.wav"));
		k.setPlayCount(0);
		k.play();
		frameControl(4);
		setFocusable(true);
		addKeyListener(new cheater());
		timer.start();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(bufferImage, 0, 0, width, height, null);
	}

	public void frameControl(int id) {
		timer.stop();
		switch (id) {
		case 0:
			timer = new Timer(20, new onePlayer(myBuffer, height, width) {
				KeyListener key1;
				MouseListener mouse1;

				@Override
				public void repainter() {
					p1.setCD(p1.getCD() + ratechange);
					ratechange = 0;
					p1.setHp(p1.getHp() + heal);
					heal = 0;
					p1.setAtk(p1.getAtk() + atk);
					atk = 0;
					score += addScore;
					addScore = 0;
					repaint();
				}

				@Override
				public void onStop() {
					removeKeyListener(key1);
					removeMouseListener(mouse1);
					frameControl(4);
				}

				@Override
				public void onStart() {
					p1 = new player(img, 48, 48);
				}

				@Override
				public void newKeyListener(KeyListener k) {
					key1 = k;
					addKeyListener(k);
				}

				@Override
				public void newMouseListener(MouseListener m) {
					mouse1 = m;
					addMouseListener(m);
				}
			});
			break;
		/*
		 * case 1: timer = new Timer(20, new twoPlayer()); break; //
		 */
		case 2:
			timer = new Timer(20, new settingPage(myBuffer, height, width) {
				KeyListener k;
				MouseListener m;
				
				@Override
				public void repainter() {
					repaint();
				}

				@Override
				public void onStop() {
					removeKeyListener(k);
					removeMouseListener(m);
					frameControl(4);
				}

				@Override
				public void newKeyListener(KeyListener k) {
					this.k = k;
					addKeyListener(k);
				}

				@Override
				public void newMouseListener(MouseListener m) {
					this.m = m;
					addMouseListener(m);
				}
			});
			break;
		case 4:
			timer = new Timer(20, new startPage(myBuffer, height, width) {
				KeyListener k;
				MouseListener m;

				@Override
				public void repainter() {
					repaint();
				}

				@Override
				public void onStop(int frameID) {
					removeKeyListener(k);
					removeMouseListener(m);
					frameControl(frameID);
				}

				@Override
				public void newKeyListener(KeyListener k) {
					this.k = k;
					addKeyListener(k);
				}

				@Override
				public void newMouseListener(MouseListener m) {
					this.m = m;
					addMouseListener(m);
				}
			});
			break;
		}
		timer.start();
	}

	public class cheater extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			cheatBuffer += e.getKeyChar();
			if (cheatBuffer.indexOf(":wq") > -1) {
				System.exit(0);
			} else if (cheatBuffer.indexOf("blue") > -1) {
				img = 0;
			} else if (cheatBuffer.indexOf("green") > -1) {
				img = 1;
			} else if (cheatBuffer.indexOf("orange") > -1) {
				img = 2;
			} else if (cheatBuffer.indexOf("red") > -1) {
				img = 3;
			} else if (cheatBuffer.indexOf("ufo") > -1) {
				img = 4;
			} else if (cheatBuffer.indexOf("backtostart") > -1) {
				frameControl(4);
			} else if (cheatBuffer.indexOf("showcollisionbox") > -1) {
				gameConf.putBoolean("colbox", true);
			} else if (cheatBuffer.indexOf("hidecollisionbox") > -1) {
				gameConf.putBoolean("colbox", false);
			} else if (cheatBuffer.indexOf("rateup") > -1) {
				ratechange = -2;
			} else if (cheatBuffer.indexOf("ratedown") > -1) {
				ratechange = 2;
			} else if (cheatBuffer.indexOf("heal") > -1) {
				heal = 200;
			} else if (cheatBuffer.indexOf("atkup") > -1) {
				atk = 200;
			} else if (cheatBuffer.indexOf("bestfriend") > -1) {
				gameConf.putBoolean("bf", !gameConf.getBoolean("bf", false));
			} else if (cheatBuffer.indexOf("stagemode") > -1) {
				gameConf.putBoolean("stagemode", !gameConf.getBoolean("stagemode", false));
				System.out.println(gameConf.getBoolean("stagemode", false));
			} else if (cheatBuffer.indexOf("addscore") > -1) {
				addScore = 800;
			} else if (cheatBuffer.indexOf("neverdie") > -1) {
				gameConf.putBoolean("nd", !gameConf.getBoolean("nd", false));
			} else if (cheatBuffer.length() <= 20){
				return;
			}
			cheatBuffer = "";
		}
	}
}
