package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;

public abstract class GameObject {
	private Preferences gameConf = Preferences.userNodeForPackage(EntryPoint.class);
	protected double dx = 0; // speed
	protected double dy = 1;
	protected double ax = 0; // acceleration
	protected double ay = 0;
	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int height = 0;
	protected int colx = 0;
	protected int coly = 0;
	protected int colwidth = 0;
	protected int colheight = 0;
	protected int hp = 100;
	protected int maxhp = 100;
	protected int atk = 10;
	protected int score = 10;
	protected Color c = Color.white;

	public void drawObject(Graphics g) {
		g.drawImage(img.getImage(), x, y, width, height, null);
		if (gameConf.getBoolean("colbox", false)) {
			g.setColor(c);
			g.drawRect(x + colx, y + coly, colwidth, colheight);
		}
	}

	abstract public void explodeObject(Graphics g);
	protected ImageIcon img;
	
	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}
	
	public int getAtk() {
		return atk;
	}
	
	public void setAtk(int atk) {
		this.atk = atk;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setSpeed(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setSpeed(double delta) {
		this.dx *= delta;
		this.dy *= delta;
	}
	
	
	public void move() { // 絕大多數時候該使用的
		this.x += this.dx;
		this.y += this.dy;
		this.dx += this.ax;
		this.dy += this.ay;
	}
	
	public void moveX(int x) {
		this.x += x;
	}
	
	public void moveY(int y) {
		this.x += y;
	}
	
	public int getColX() {
		return x + colx;
	}
	
	public int getColY() {
		return y + coly;
	}
	
	public int getColWidth() {
		return colwidth;
	}
	
	public int getColHeight() {
		return colheight;
	}
}
