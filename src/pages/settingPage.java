package pages;

import java.awt.*;
import java.awt.event.*;
import java.util.prefs.Preferences;

import javax.swing.*;

import main.EntryPoint;
import main.PanelController;

public class settingPage implements ActionListener, PanelController {
	private Graphics myBuffer;
	private int height, width, selected = 0;

	private Preferences gameConf = Preferences.userNodeForPackage(EntryPoint.class);

	public settingPage(Graphics g, int h, int w) {
		myBuffer = g;
		height = h;
		width = w;
		newKeyListener(new startKey());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		drawBackground(); // Always
		drawForm(); // Always
		
		repainter();
	}

	public void save() {
		
	}
	
	public class startKey extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				save();
				onStop();
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				selected -= 1;
				if (selected == -1)
					selected = 4;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				selected += 1;
				if (selected == 5)
					selected = 0;
			}	
		}
	}

	public void onStop() {
	}

	public void onStart() {
	}

	public void onPause() {
	}

	public void repainter() {
	}

	public void newKeyListener(KeyListener k) {
	}

	public void newMouseListener(MouseListener m) {
	}
	public void drawBackground() {
		ImageIcon bgimg = new ImageIcon(getClass().getResource("/source/a/Backgrounds/blue.png"));
		int bgx = 0, bgy = 0;
		while (bgy < height) {
			while (bgx < width) {
				myBuffer.drawImage(bgimg.getImage(), bgx, bgy, bgimg.getIconWidth(), bgimg.getIconHeight(), null);
				bgx += bgimg.getIconWidth();
			}
			bgx = 0;
			bgy += bgimg.getIconHeight();
		}
	}
	private void drawForm() {
		myBuffer.setColor(new Color(221, 221, 219));
		myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 96));
		myBuffer.drawString("遊戲設置", 110, 110);
		myBuffer.setColor(Color.white);
		myBuffer.setFont(new Font("微軟正黑體", Font.BOLD, 30));
		myBuffer.drawString("　音效", 110, 200);
		myBuffer.drawString("允許作弊", 110, 260);
		myBuffer.drawString("重置紀錄", 110, 600);
	}
}
