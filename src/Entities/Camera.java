package Entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	Vector3f pos;
	float rotX = 0;
	float rotY;
	float rotZ;
	
	float speed = 0.5f;
	float oldspeed = speed;
	float mouseSensitivity = 0.5f;
	float moveAt = 0;
	public Camera(Vector3f pos, float rotX, float rotY, float rotZ) {
		this.pos = pos;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}
	public void move() {
		if(Mouse.isGrabbed()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				moveAt = -speed;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				moveAt = speed;
			}else{
				moveAt = 0;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				pos.y += speed/2;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				pos.y += -speed/2;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				speed = 1f;
			} else if(oldspeed < speed && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
				speed = oldspeed;
			}
			
			rotX += -Mouse.getDY() * mouseSensitivity;
			rotY += Mouse.getDX() * mouseSensitivity;
			if (rotX > 90){
				rotX = 90;
			} else if (rotX < -90){
				rotX = -90;
			}
			float dx = (float) -(moveAt * Math.sin(Math.toRadians(rotY)));
			//float dy = (float) (moveAt * Math.sin(Math.toRadians(rotX)));
			float dz = (float) (moveAt * Math.cos(Math.toRadians(rotY)));
		
			pos.x += dx;
			//pos.y += dy;
			pos.z += dz;
		}
	}
	public Vector3f getPos() {
		return pos;
	}
	public float getRotX() {
		return rotX;
	}
	public float getRotY() {
		return rotY;
	}
	public float getRotZ() {
		return rotZ;
	}
	
}
