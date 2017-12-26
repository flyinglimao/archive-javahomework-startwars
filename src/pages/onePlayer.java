package pages;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import org.magiclen.magicaudioplayer.AudioPlayer;

import java.util.prefs.*;

import alien.normal;
import main.Bomb;
import main.Bullet;
import main.EntryPoint;
import main.Item;
import main.PanelController;
import main.Shot;
import player.player;

public class onePlayer implements ActionListener, PanelController {
	private Preferences gameConf = Preferences.userNodeForPackage(EntryPoint.class);
	private Graphics myBuffer;
	private ImageIcon bgimg = new ImageIcon(getClass().getResource("/source/a/Backgrounds/blue.png"));
	private int height, width;
	protected int score = 0;
	private int level = 0;
	private int levelup = 0;
	protected player p1 = new player(1, 48, 48);
	private int dx = 0, dy = 0;
	private boolean fire = false;
	private boolean fireable = true;
	private boolean gameOvered = false;
	private boolean pause = false;
	private playerBullet firstBullet = new playerBullet();
	private enemyBullet firstEnemyBullet = new enemyBullet();
	private items firstItem = new items();
	private enemy firstEnemy = new enemy();
	private int enemyCount = 0;
	private int stage = 0;
	private int stagetmp = 0;

	private int bgsy = -256; // 滾軸背景

	public onePlayer(Graphics g, int h, int w) {
		firstBullet.next = null;
		firstBullet.me = null;
		myBuffer = g;
		height = h;
		width = w;
		onStart();
		newKeyListener(new p1control());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (pause) {
			myBuffer.setColor(Color.white);
			myBuffer.fillRect(100, 200, 400, 100);
			myBuffer.setColor(Color.BLACK);
			myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 40));
			myBuffer.drawString("按下 ESC 離開暫停", 140, 250);
			repainter();
			return;
		}
		// Background Drawing
		int bgx = 0, bgy = bgsy;
		while (bgy < height) {
			while (bgx < width) {
				myBuffer.drawImage(bgimg.getImage(), bgx, bgy, bgimg.getIconWidth(), bgimg.getIconHeight(), null);
				bgx += bgimg.getIconWidth();
			}
			bgx = 0;
			bgy += bgimg.getIconHeight();
		}
		bgsy++;
		if (bgsy > 0)
			bgsy = -256;
		// Game over condition
		if (gameOvered || p1.getHp() <= 0) {
			gameOvered = true;
			if (score > gameConf.getInt("BESTSCORE", 0)) {
				gameConf.putInt("BESTSCORE", score);
			}
			myBuffer.setColor(Color.GRAY);
			myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 96));
			myBuffer.drawString("遊戲結束", 110, 310);
			myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 45));
			myBuffer.drawString("獲得分數", 110, 400);
			myBuffer.drawString("" + score, 110, 520);
			myBuffer.drawString("按下 Enter 回到主畫面", 110, 610);
			repainter();
			return;
		}
		// Always
		alwaysDraw();
		// Enemy
		int enemyCounter = 0;
		enemy fe = firstEnemy;
		while (fe.hasNext()) {
			fe = fe.next;
			enemyCounter++;
		}
		if (gameConf.getBoolean("stagemode", false) && stage < 3) {
			if (enemyCounter == 0) {
				fe = firstEnemy;
				while (fe.hasNext())
					fe = fe.next;
				switch (stage) {
				case 0:
					for (int r = 0; r < 4; r++) {
						for (int c = 0; c < 8; c++) {
							fe.next = new enemy();
							fe = fe.next;
							fe.me = new normal(8 + 48 * c, -36 + 48 * r, 1, 0, 36, 36, 150, 5, 0, 0, 5) {
								private int toX = 192;

								@Override
								public void move() {
									if (toX > 0) {
										x += dx;
										toX--;
									} else {
										y += 4;
										dx *= -1;
										toX = 192;
									}
									if (hp <= 0) {
										AudioPlayer.createPlayer(new File("source/a/Bonus/crash.wav")).play();
										this.bye = true;
									}
								}

								@Override
								public boolean doShot() {
									return super.doShot();
								}
							};
						}
					}
					stage += 1;
					break;
				case 1:
					for (int c = 0; c < 48; c++) {
						fe.next = new enemy();
						fe = fe.next;
						fe.me = new normal((int)(Math.random() * 600), (int)(Math.random() * 250), 0, 0, 36, 36, 250, 15, 0, 0, 5) {
							@Override					
							public void move() {
								if(dx <= 0.5 && dx >= -0.5) dx = Math.random() * 16 - 8;
								if(dy <= 0.5 && dy >= -0.5) dy = Math.random() * 8 - 4;
								
								if(x >= 550) {
									dx = - Math.random() * 8;
								} else if (x <= 0) {
									dx = Math.random() * 8;
								}

								if(y >= 212) {
									dy = - Math.random() * 4;
								} else if (y <= 0) {
									dy = Math.random() * 4;
								}
								x += dx;
								y += dy;
								if (hp <= 0) bye = true;
							}

							@Override
							public boolean doShot() {
								return super.doShot();
							}
						};
					}
					stage += 1;
					break;
				case 2:
					stage += 1;
					break;
				}
			}
		} else {
			if (enemyCount > (100 / Math.pow(1 + (float) level / 10, 5))) {
				enemyCreater();
				enemyCount = 0;
			}
			enemyCount++;
		}

		if (fire && fireable) {
			fireable = false;
			bang();
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(p1.getCD() * 15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					fireable = true;
				}
			}.start();
		}
		// drops
		items fi = firstItem;
		while (fi.hasNext()) {
			fi = fi.next;
			fi.me.hit(p1);
		}

		// Shooting
		playerBullet b = firstBullet;
		while (b.hasNext()) {
			b = b.next;
			enemy en = firstEnemy;
			while (en.hasNext()) {
				en = en.next;
				// when player hit
				score += b.me.hit(en.me);
			}
		}
		enemyBullet fb = firstEnemyBullet;
		while (fb.hasNext()) {
			fb = fb.next;
			fb.me.hit(p1);
		}
		enemy en = firstEnemy;
		while (en.hasNext()) {
			en = en.next;
			if (en.me.doCrashed(p1)) {
				p1.setHp(p1.getHp() - en.me.getHp());
				en.me.bye = true;
			}
			if (en.me.doShot()) {
				enemyBullet fbe = firstEnemyBullet;
				while (fbe.hasNext()) {
					fbe = fbe.next;
				}
				fbe.next = new enemyBullet();
				fbe = fbe.next;
				fbe.me = en.me.getBomb();
			}
		}
		if (score / 1000 > level || levelup > 0) {
			if (levelup == 0) {
				levelup = 100;
				level += 1;
				p1.defen();
				p1.setMaxHp(p1.getMaxHp() + 20);
				p1.setHp(p1.getMaxHp());
				p1.setAtk(p1.getAtk() + 5);
				AudioPlayer.createPlayer(new File("source/a/Bonus/lvup.wav")).play();
			}
			myBuffer.setColor(Color.RED);
			myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 25));
			myBuffer.drawString("等級提升！Max HP +20、Atk + 5、防護罩 Got", 100, 500);
			levelup--;
		}
		repainter();
	}

	private void enemyCreater() {
		enemy e = firstEnemy;
		while (e.hasNext())
			e = e.next;
		e.next = new enemy();
		e = e.next;
		e.me = new normal((int) (Math.random() * 552), -36, 0, 2, 36, 36,
				(int) (20 * Math.pow(1 + (float) level / 10, 3.0)), (int) (5 * Math.pow(1 + (float) level / 10, 2)), 0,
				0, 10 * level);
	}

	private void bang() {
		AudioPlayer tmpP = AudioPlayer.createPlayer(new File("source/a/Bonus/sfx_laser1.wav"));
		tmpP.setPlayCount(1);
		tmpP.play();
		playerBullet b = firstBullet;
		while (b.hasNext()) {
			b = b.next;
		}
		b.next = new playerBullet();
		b = b.next;
		b.me = new Shot(p1.getX(), p1.getY(), p1.getAtk());

		b.next = new playerBullet();
		b = b.next;
		b.me = new Shot(p1.getX() + p1.getWidth(), p1.getY(), p1.getAtk());
	}

	public class p1control implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP)
				dy = -3;
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				dy = 3;
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)
				dx = -3;
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				dx = 3;
			else if (e.getKeyCode() == KeyEvent.VK_SPACE)
				fire = true;
			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				pause = !pause;
			else if (e.getKeyCode() == KeyEvent.VK_ENTER && gameOvered)
				onStop();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP)
				dy = 0;
			else if (e.getKeyCode() == KeyEvent.VK_DOWN)
				dy = 0;
			else if (e.getKeyCode() == KeyEvent.VK_LEFT)
				dx = 0;
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				dx = 0;
			else if (e.getKeyCode() == KeyEvent.VK_SPACE)
				fire = false;
		}

	}

	private void alwaysDraw() {
		// GameObjects
		// Enemies
		enemy e = firstEnemy;
		while (e.hasNext()) {
			if (e.next.me.bye) {
				if (e.next.me.drop() != null) {
					items it = firstItem;
					while (it.hasNext()) {
						it = it.next;
					}
					it.next = new items();
					it = it.next;
					it.me = e.next.me.drop();
				}

				e.next = e.next.next;
				continue;
			}
			e = e.next;
			e.me.drawObject(myBuffer);
			e.me.move();
			if (e.me.getY() > 600)
				gameOvered = true;
		}
		// Player
		p1.setX(p1.getX() + dx);
		p1.setY(p1.getY() + dy);
		p1.drawObject(myBuffer);

		// Bullets
		playerBullet b = firstBullet;
		while (b.hasNext()) {
			if (b.next.me.bye) {
				b.next = b.next.next;
				continue;
			}
			b = b.next;
			b.me.drawObject(myBuffer);
			b.me.move();
		}

		// Drops

		items i = firstItem;
		while (i.hasNext()) {
			if (i.next.me.bye) {
				i.next = i.next.next;
				continue;
			}
			i = i.next;
			i.me.drawObject(myBuffer);
			i.me.move();
		}

		enemyBullet fb = firstEnemyBullet;
		while (fb.hasNext()) {
			if (fb.next.me.bye) {
				fb.next = fb.next.next;
				continue;
			}
			fb = fb.next;
			fb.me.drawObject(myBuffer);
			fb.me.move();
		}
		// Player Panel Drawing
		myBuffer.setColor(new Color(244, 185, 66));
		myBuffer.fillRect(0, 540, 600, 160);
		// 血條
		myBuffer.setColor(Color.RED);
		myBuffer.fillRect(80, 575, 200, 25);
		myBuffer.setColor(Color.GREEN);
		myBuffer.fillRect(80 + 200 * (p1.getMaxHp() - p1.getHp()) / p1.getMaxHp(), 575,
				200 * p1.getHp() / p1.getMaxHp(), 25);
		// Label
		myBuffer.setColor(Color.black);
		myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		myBuffer.drawString("Player", 20, 560);
		myBuffer.drawString("HP", 20, 595);
		myBuffer.drawString("ATK", 20, 630);
		myBuffer.drawString("" + p1.getAtk(), 80, 630);
		myBuffer.drawString("SCORE: " + score, 400, 630);
		myBuffer.setColor(Color.WHITE);
		myBuffer.drawString("" + p1.getHp() + "/" + p1.getMaxHp(), 140, 595);
	}

	private class playerBullet {
		Bullet<normal> me;
		playerBullet next = null;

		boolean hasNext() {
			return next != null;
		}
	}

	private class enemyBullet {
		Bullet<player> me;
		enemyBullet next = null;

		boolean hasNext() {
			return next != null;
		}
	}

	private class items {
		Item me;
		items next = null;

		boolean hasNext() {
			return next != null;
		}
	}

	private class enemy {
		normal me;
		enemy next = null;

		boolean hasNext() {
			return next != null;
		}
	}

	public void rateUp() {

	}

	public void addScore(int k) {

	}
}
