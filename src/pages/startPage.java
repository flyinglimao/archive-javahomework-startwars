package pages;

import java.awt.*;
import java.awt.event.*;
import java.util.prefs.*;

import javax.swing.*;

import main.EntryPoint;
import main.PanelController;

public class startPage implements ActionListener, PanelController {
	ImageIcon bgimg = new ImageIcon(getClass().getResource("/source/a/Backgrounds/blue.png"));
	ImageIcon tour = new ImageIcon(getClass().getResource("/source/tour.png"));
	private Graphics myBuffer;
	private int height, width, selected = 0;
	
	private Preferences gameConf = Preferences.userNodeForPackage(EntryPoint.class);
	private int bestScore = gameConf.getInt("BESTSCORE", 0);
	
	public startPage(Graphics g, int h, int w) {
		myBuffer = g;
		height = h;
		width = w;
		onStart();
		newKeyListener(new startKey());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		onStart();
		int bgx = 0, bgy = 0;			
		while (bgy < height) {
			while (bgx < width) {
				myBuffer.drawImage(bgimg.getImage(), bgx, bgy, bgimg.getIconWidth(), bgimg.getIconHeight(), null);
				bgx += bgimg.getIconWidth();
			}
			bgx = 0;
			bgy += bgimg.getIconHeight();
		}
		myBuffer.setColor(new Color(221, 221, 219));
		myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 96));
		myBuffer.drawString("星際大戰", 110, 200);
		myBuffer.setColor(Color.WHITE);
		myBuffer.setFont(new Font("微軟正黑體", Font.PLAIN, 26));
		myBuffer.drawString("Press Enter To Start", 200, 330);
		myBuffer.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
		myBuffer.drawString("↑↓←→ 控制方向", 180, 380);
		myBuffer.drawString("空白發射子彈", 180, 420);
		myBuffer.drawString("ESC暫停", 180, 460);
		myBuffer.setColor(new Color(221, 221, 219));
		myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 30));
		myBuffer.drawString("BEST: " + bestScore, 40, 610);
		myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		myBuffer.drawString("「Audionautix」創作的「Spots Action」是根據「Creative Commons Attribution」", 0, 630);
		myBuffer.drawString(" 授權使用 演出者：http://audionautix.com/ ", 0, 650);
		repainter();
	}
	
	public class startKey extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				selected -= 1;
				if (selected == -1) selected = 2;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				selected += 1;
				if (selected == 3) selected = 0;
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				onStop(selected);
			}
		}
	}
	
	public void onStop(int frameID) {
	}
}
