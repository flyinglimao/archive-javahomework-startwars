package main;

import java.awt.*;
import javax.swing.*;

public class EntryPoint{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame("一個星際大戰");
		f.setSize(600, 700);
		f.setLocation(100, 50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(new MainPanel());
		f.setVisible(true);
	}

}
