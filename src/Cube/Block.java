package Cube;

public class Block {
	public int x, y, z;
	
	public static int Air = -1;
	public static int Grass = 0;
	public static int Dirt = 1;
	public static int Stone = 2;
	public static int Log = 3;
	public static int Leaves = 4;
	
	public int type;
	
	public Block(int x, int y, int z , int type) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.type = type;
	}
}
