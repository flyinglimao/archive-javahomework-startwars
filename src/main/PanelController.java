package main;

import java.awt.event.*;

public interface PanelController {
	default void onStop() {}
	default void onStart() {};
	default void onPause() {};
	default void repainter() {};
	default void newKeyListener(KeyListener k) {};
	default void newMouseListener(MouseListener m) {};
}
