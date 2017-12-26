package main;

import java.awt.*;
import javax.swing.*;

public class Bullet<E extends GameObject> {
	protected int type = 0;
	protected ImageIcon img;
	protected int atk;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected float dx;
	protected float dy;
	protected float ax = 0;
	protected float ay = 0;
	public boolean bye = false;

	public void drawObject(Graphics g) {
		if (width == 0)
			width = img.getIconWidth();
		if (height == 0)
			height = img.getIconHeight();
		g.drawImage(img.getImage(), x, y, width, height, null);
	}

	public int hit(E a) {
		if ((x > a.getX() && y > a.getY() && x < a.getX() + a.getWidth() && y < a.getY() + a.getHeight())
				|| (x + width > a.getX() && y > a.getY() && x + width < a.getX() + a.getWidth()
						&& y < a.getY() + a.getHeight())
				|| (x + width > a.getX() && y + height > a.getY() && x + width < a.getX() + a.getWidth()
						&& y + height < a.getY() + a.getHeight())
				|| (x > a.getX() && y + height > a.getY() && x < a.getX() + a.getWidth()
						&& y + height < a.getY() + a.getHeight())) {
			a.setHp(a.getHp() - atk);
			bye = true;
			return a.score;
		} else return 0;
	}

	public void move() {
		x += dx;
		y += dy;
		if (x < -width || y < -height || x > 600 || y > 600) {
			bye = true;
		}
	}

	public boolean intersect1D(int x1, int x2, int x3, int x4) {
		if (x1 > x2) { // reverse
			x2 += x1;
			x1 -= x2;
			x2 += x1;
			x1 *= -1;
		}
		if (x3 > x4) { // reverse
			x2 += x1;
			x1 -= x2;
			x2 += x1;
			x1 *= -1;
		}
		if (x1 >= x3 && x1 <= x4)
			return true;
		else if (x2 >= x3 && x2 <= x4)
			return true;
		else if (x3 >= x1 && x3 <= x2)
			return true;
		else if (x4 >= x1 && x4 <= x2)
			return true;
		return false;
	}

	public boolean lineIntersect(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		return intersect1D(x1, x2, x3, x4) || intersect1D(y1, y2, y3, y4);
	}
}
