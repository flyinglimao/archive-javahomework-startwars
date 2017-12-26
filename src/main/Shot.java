package main;

import javax.swing.*;

import alien.normal;

public class Shot extends Bullet<normal>{	
	public Shot(int x, int y, int atk) {
		type = -1;
		this.x = x;
		this.y = y;
		this.dx = 0;
		this.dy = -6;
		this.atk = atk;
		img = new ImageIcon(getClass().getResource("/source/a/PNG/Lasers/laserBlue01.png"));
	}
	public Shot(int x, int y, float dx, float dy, int atk) {
		type = -1;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.atk = atk;
		img = new ImageIcon(getClass().getResource("/source/a/PNG/Lasers/laserBlue01.png"));
	}
	public Shot(int x, int y, float dx, float dy, float ax, float ay, int atk) {
		type = -1;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.atk = atk;
		this.ax = ax;
		this.ay = ay;
		img = new ImageIcon(getClass().getResource("/source/a/PNG/Lasers/laserBlue01.png"));
	}
	
}
