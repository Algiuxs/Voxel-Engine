package ReonCraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import Chunks.Chunk;
import Chunks.ChunkMesh;
import Cube.Block;
import Entities.Camera;
import Entities.Entity;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import Shaders.StaticShader;
import Textures.ModelTexture;
import ToolBox.FPS;
import ToolBox.InputHandler;
import ToolBox.PerlinNoiseGenerator;

public class MainGameLoop {
	static boolean isrunning = false;
	public static Loader loader1 = null;
	public static StaticShader shader1 = null;
	
	static List<ChunkMesh> chunks = Collections.synchronizedList(new ArrayList<ChunkMesh>());
	static Vector3f camPos = new Vector3f(0, 0, 0);
	static List<Vector3f> usedPos = new ArrayList<Vector3f>();
	
	static List<Entity> entities = new ArrayList<Entity>();
	
	static final int width = 4 * 32;
	static final int height = width;

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		loader1 = loader;
		StaticShader shader = new StaticShader();
		shader1 = shader;
		MasterRenderer renderer = new MasterRenderer();
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("atlas"));//TODO IMG
		
		Camera cam = new Camera(new Vector3f(0,15,0), 0, 0, 0);
		
		PerlinNoiseGenerator perlinNoise = new PerlinNoiseGenerator();
		InputHandler input = new InputHandler();
		
		try {
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		isrunning = true;
		new Thread(new Runnable() {
			public void run() {
				while (isrunning) {
					for(int x = (int) (camPos.x - width)/32; x < (camPos.x + width)/32; x++) {
						for(int z = (int) (camPos.z - height)/32; z < (camPos.z + height)/32; z++) {
							if (!usedPos.contains(new Vector3f(x*32, 0, z*32))) {
								List<Block> blocks = new ArrayList<Block>();
								
								for(int i = 0; i < 32; i++) {
									for(int j = 0; j < 32; j++) {
										int height = (int) perlinNoise.generateHeight(i+(x*32), j+(z*32));
										blocks.add(new Block(i, (int) height, j, Block.Grass));
									}
								}
								
								Chunk chunk = new Chunk(blocks, new Vector3f(x * 32, 0, z * 32));
								ChunkMesh mesh = new ChunkMesh(chunk);
								
								chunks.add(mesh);
								usedPos.add(new Vector3f(x*32, 0, z*32));
							}
						}
					}
					
				}
			}
			
		}).start();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		try {
			Display.makeCurrent();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		int index = 0;
		

		FPS fps = new FPS();

		
		boolean pressed = false;
		int oldFPS = 0;
		while (!Display.isCloseRequested()) {
			fps.update();
			cam.move();
			camPos = cam.getPos();
			if(input.isMouseDown(0)){
				if(!pressed){
					
					pressed = true;
				}
				
			} else {
				pressed = false;
			}
				
			if (index < chunks.size()) {
				RawModel rawmodel = loader.loadToVAO(chunks.get(index).positions, chunks.get(index).uvs);
				TexturedModel texxmodel = new TexturedModel(rawmodel, texture);
				Entity entity = new Entity(texxmodel, chunks.get(index).chunk.origin, 0, 0, 0, 1);
				entities.add(entity);
				chunks.get(index).positions = null;
				chunks.get(index).normals = null;
				chunks.get(index).uvs = null;
				index++;
			}
			for (int i = 0; i < entities.size(); i++) {
				Vector3f origin = entities.get(i).getPos();
				int distX = (int) (camPos.x - origin.x);
				int distZ = (int) (camPos.z - origin.z);
				if(distX < 0) {
					distX = -distX;
				}
				if(distZ < 0) {
					distZ = -distZ;
				}
				if((distX <= width)&&(distZ <= height)) {
					renderer.addEntities(entities.get(i));	
				}
			}
			
			
			renderer.render(cam);
			DisplayManager.updateDisplay();
			if (fps.getFPS() != oldFPS){
				oldFPS = fps.getFPS();
				
			}
		}
		isrunning = false;
		Mouse.destroy();
		
		System.out.println(GL11.glGetError());
		DisplayManager.closeDisplay();
	}

}
