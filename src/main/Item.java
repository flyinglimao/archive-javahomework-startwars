package main;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import player.player;

public class Item extends GameObject{
	private int type;
	public boolean bye = false;
	
	public Item(int type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
		width = 18;
		height = 18;
		img = new ImageIcon(getClass().getResource("/source/item/"+type+".png"));
	}
	
	public void move() {
		this.y += 6;
	}
	// bring = [Hp UP, Defense, HpMax Up, Atk Up, Special Bullet, RateUp]
	//    5%   [ 52% ,  15%   , 5%      , 10%   , 15%           , 3%    ]
	public void hit(player a) {
		if ((x > a.getX() && y > a.getY() && x < a.getX() + a.getWidth() && y < a.getY() + a.getHeight())
				|| (x + width > a.getX() && y > a.getY() && x + width < a.getX() + a.getWidth()
						&& y < a.getY() + a.getHeight())
				|| (x + width > a.getX() && y + height > a.getY() && x + width < a.getX() + a.getWidth()
						&& y + height < a.getY() + a.getHeight())
				|| (x > a.getX() && y + height > a.getY() && x < a.getX() + a.getWidth()
						&& y + height < a.getY() + a.getHeight())) {
			switch(type) {
			case 1:
				a.setHp(a.getHp() > a.getMaxHp()*0.9 ? a.getMaxHp() :a.getHp() + (int)(a.getMaxHp() * 0.1));
				break;
			case 2:
				a.defen();
				break;
			case 3:
				a.setMaxHp((int)(a.getMaxHp() * 1.5));
				break;
			case 4:
				a.setAtk(a.getAtk()+5);
				break;
			case 5:
				break;
			case 6:
				a.setCD(a.getCD() - 1);
			}
			bye = true;
		}
	}
	
	@Override
	public void explodeObject(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
