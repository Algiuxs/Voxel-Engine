package ToolBox;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputHandler {
	public boolean IsKeyDown(int Key) {
		return Keyboard.isKeyDown(Key);
	}
	public boolean IsKeyUp(int Key) {
		return !Keyboard.isKeyDown(Key);
	}
	public boolean isMouseDown(int key) {
		return Mouse.isButtonDown(key);
	}
}
