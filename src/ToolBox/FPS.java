package ToolBox;

public class FPS {
    private static final int UPDATE_INTERVAL = 100; // Update every third of a second (333 milliseconds)

    private long lastUpdateTime;
    private long elapsedTime;
    private int frameCount;
    private int fps;

    public FPS() {
        lastUpdateTime = getTime();
        elapsedTime = 0;
        frameCount = 0;
        fps = 0;
    }

    // Get the current system time in milliseconds
    private long getTime() {
        return System.currentTimeMillis();
    }

    // Update the FPS counter
    public void update() {
        long currentTime = getTime();
        long deltaTime = currentTime - lastUpdateTime;
        elapsedTime += deltaTime;
        frameCount++;

        if (elapsedTime >= UPDATE_INTERVAL) {
            fps = (int) (frameCount * 1000 / elapsedTime);
            elapsedTime = 0;
            frameCount = 0;
            lastUpdateTime = currentTime; // Update lastUpdateTime for smoother operation
        }
    }

    // Get the current FPS
    public int getFPS() {
        return fps;
    }
}
