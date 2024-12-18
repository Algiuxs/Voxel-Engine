package ToolBox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Entities.Camera;

public class Maths {
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rotX, float rotY, float rotZ, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotX), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotY), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotZ), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}
	public static Matrix4f createViewMatrix(Camera cam) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		Matrix4f.rotate((float) Math.toRadians(cam.getRotX()), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(cam.getRotY()), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(cam.getRotZ()), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.translate(new Vector3f(-cam.getPos().x,-cam.getPos().y,-cam.getPos().z), matrix, matrix);
		return matrix;
	}
}
