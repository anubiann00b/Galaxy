package galaxy;

import galaxy.render.VerticesManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Galaxy {
    
    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(1366,768));
            Display.create();
        } catch (LWJGLException e) {
            System.out.println(e);
        }
        Display.setTitle("Galaxy");
        Galaxy galaxy = new Galaxy();
        galaxy.init();
    }
    
    private VerticesManager vertices;
    
    private void init() {
        vertices = new VerticesManager(100);
    }
}