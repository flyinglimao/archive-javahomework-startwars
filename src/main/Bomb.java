package main;

import java.util.prefs.Preferences;

import javax.swing.ImageIcon;

import player.player;;

public class Bomb extends Bullet<player> {
	private Preferences gameConf = Preferences.userNodeForPackage(EntryPoint.class);
	
	public Bomb(int x, int y, int atk) {
		type = 1;
		this.x = x;
		this.y = y;
		this.dx = 0;
		this.dy = 6;
		this.atk = atk;
		img = new ImageIcon(getClass().getResource("/source/a/PNG/Lasers/laserRed01rev.png"));
	}
	public Bomb(int x, int y, float dx, float dy, int atk) {
		type = 1;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.atk = atk;
		img = new ImageIcon(getClass().getResource("/source/a/PNG/Lasers/laserBlue01rev.png"));
	}
	public Bomb(int x, int y, float dx, float dy, float ax, float ay, int atk) {
		type = 1;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.atk = atk;
		this.ax = ax;
		this.ay = ay;
		img = new ImageIcon(getClass().getResource("/source/a/PNG/Lasers/laserBlue01rev.png"));
	}
	public int hit(player a) {
		if ((x > a.getX() && y > a.getY() && x < a.getX() + a.getWidth() && y < a.getY() + a.getHeight())
				|| (x + width > a.getX() && y > a.getY() && x + width < a.getX() + a.getWidth()
						&& y < a.getY() + a.getHeight())
				|| (x + width > a.getX() && y + height > a.getY() && x + width < a.getX() + a.getWidth()
						&& y + height < a.getY() + a.getHeight())
				|| (x > a.getX() && y + height > a.getY() && x < a.getX() + a.getWidth()
						&& y + height < a.getY() + a.getHeight())) {
			if (a.defense) a.defense = false;
			else if (gameConf.getBoolean("bf", false))
				a.setHp(0);
			else if (gameConf.getBoolean("nd", false))
				;
			else
				a.setHp(a.getHp() - atk);
			bye = true;
			return a.score;
		} else return 0;
	}
	
}
