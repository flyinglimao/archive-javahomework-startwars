package player;

import java.awt.*;
import javax.swing.*;

import main.GameObject;

public class player extends GameObject{
	protected int maxhp = 100;
	protected int fireCD = 30;
	public boolean defense = false;
		
	public player(int img, int w, int h) {
		this.x = 240;
		this.y = 490;
		this.colx = 4;
		this.coly = 8;
		this.colwidth = w-8;
		this.colheight = h-16;
		this.hp = 100;
		this.maxhp = 100;
		this.width = w;
		this.height = h;
		this.atk = 10;
		switch(img) {
		case 0:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/playerShip2_blue.png"));
			break;
		case 1:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/playerShip2_green.png"));
			break;
		case 2:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/playerShip2_orange.png"));
			break;
		case 3:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/playerShip2_red.png"));
			break;
		default:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/ufoBlue.png"));
		}
	}
	
	public player(int x, int y, int hp, int atk, int img) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.atk = atk;
		switch(img) {
		case 0:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/Damage/playerShip2_blue.png"));
			break;
		case 1:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/Damage/playerShip2_green.png"));
			break;
		case 2:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/Damage/playerShip2_orange.png"));
			break;
		case 3:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/Damage/playerShip2_red.png"));
			break;
		default:
			this.img = new ImageIcon(getClass().getResource("/source/a/PNG/Damage/ufoBlue.png"));
		}
	}
	
	public void defen() {
		defense = true;
	}
	
	public int getMaxHp() {
		return maxhp;
	}
	
	public void setMaxHp(int p) {
		maxhp = p;
	}
	
	public void setX(int x) {
		this.x = x;
		if (this.x < 0) this.x = 0;
		else if (this.x > 540) this.x = 540;
	}
	
	public void setY(int y) {
		this.y = y;
		if (this.y < 0) this.y = 0;
		else if (this.y > 490) this.y = 490;
	}
	
	public int getCD() {
		return fireCD;
	}

	public void setCD(int cd) {
		this.fireCD = cd;
		if (this.fireCD < 0) this.fireCD = 1;
	}
	
	public void explodeObject(Graphics g) {
		
	}
	
	public void drawObject(Graphics g) {
		super.drawObject(g);
		if(defense) {
			g.drawImage(new ImageIcon(getClass().getResource("/source/a/PNG/Effects/shield3.png")).getImage(), x-4, y-4, 56, 56, null);
		}
	}

}
