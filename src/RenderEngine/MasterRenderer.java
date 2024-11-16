package RenderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import Entities.Camera;
import Entities.Entity;
import Models.TexturedModel;
import Shaders.StaticShader;

public class MasterRenderer {
	
	Matrix4f projectionMatrix;
	
	private static final float fov=  70f;
	private static final float near_plane = 0.1f;
	private static final float far_plane = 10000f;
	
	StaticShader shader = new StaticShader();
	EntityRenderer renderer = new EntityRenderer();
	
	Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	
	public MasterRenderer() {
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	
	
	public void prepare() {

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.4f, 0.7f, 1.0f, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Camera cam) {
		prepare();
		shader.start();
		shader.loadviewMatrix(cam);
		renderer.render(entities);
		shader.stop();
		
		entities.clear();
	}
	
	public void addEntities(Entity entity) {
		TexturedModel model = entity.getModel();
		List<Entity> batch = entities.get(model);
		
		if(batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(model, newBatch);
		}
	}
	
	public void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float scaleY = (float) ( 1f / Math.tan(Math.toRadians(fov / 2f)));
		float scaleX = scaleY / aspectRatio;
		float zp = far_plane + near_plane;
		float zm = far_plane - near_plane;
		projectionMatrix.m00 = scaleX;
		projectionMatrix.m11 = scaleY;
		projectionMatrix.m22 = -zp/zm;
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -(2*far_plane*near_plane)/zm;
		projectionMatrix.m33 = 0;
	}

}
