package RenderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import ReonCraft.MainGameLoop;

public class DisplayManager {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 60;
	private static boolean isFullscreen = false;
	
	public static void createDisplay() {
		ContextAttribs attribs = new ContextAttribs(3,2)
				.withForwardCompatible(false)
				.withProfileCore(true)
				.withProfileCompatibility(true);
		
		try {
			Display.setFullscreen(isFullscreen);
			if(!isFullscreen) {
				Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			}
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("VoxelCraft");
			
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Mouse.setGrabbed(true);
		
	}
	
	public static void updateDisplay() {
		
		Display.sync(FPS_CAP);
		Display.update();
		//Controls for mouse, display
		while(Keyboard.next()) {
			if(Keyboard.getEventKeyState()) {
				
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) | Keyboard.isKeyDown(Keyboard.KEY_Q) && Mouse.isGrabbed()) {
					closeDisplay();
				}
				
				if(Keyboard.isKeyDown(Keyboard.KEY_E) && Mouse.isGrabbed()) {
					Mouse.setGrabbed(false);
					Mouse.setCursorPosition(WIDTH/2, HEIGHT/2);
				} else if(Keyboard.isKeyDown(Keyboard.KEY_E) && !Mouse.isGrabbed()) {
					Mouse.setGrabbed(true);
				}
				
			}
		}
		
	}
	
	public static void closeDisplay() {
		
		MainGameLoop.loader1.cleanUp();
		MainGameLoop.shader1.cleanUp();
		
		Display.destroy();
		System.exit(0);
		
	}

}
