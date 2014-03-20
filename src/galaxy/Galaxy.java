package galaxy;

import galaxy.render.VerticesManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.GLU;

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
        
        int delta = 0;
        
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            long startTime = System.nanoTime();
            galaxy.clearScreen();
            galaxy.render(delta);
            Display.update();
            long endTime = System.nanoTime();
            Display.sync(60);
            endTime = System.nanoTime();
            delta = (int)((endTime - startTime)/1000000);
        }
        Display.destroy();
        System.exit(0);
    }
    
    private VerticesManager starVertices;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    
    public final int points = 1000000;
    
    private void init() {
        Camera.init();
        this.initialize3D();
        VBOVertexHandle = GL15.glGenBuffers();
        VBOColorHandle = GL15.glGenBuffers();
        
        starVertices = new VerticesManager(points*3);
        
        for (int i=0;i<points;i++) {
            starVertices.setColor((float)Math.random(),(float)Math.random(),(float)Math.random());
            starVertices.addVertex((float)(Math.random()*1000-500),(float)(Math.random()*1000-500),(float)(Math.random()*1000-500));
        }
        
        starVertices.getPositionData().rewind();
        starVertices.getColorData().rewind();
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,starVertices.getPositionData(),GL15.GL_STREAM_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,starVertices.getColorData(),GL15.GL_STREAM_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
    }
    
    private void render(int delta) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
        GLU.gluPerspective(90.0f,(float)Display.getWidth()/(float)Display.getHeight(),0.1f,1500.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

        GL11.glPushMatrix();
        
        Camera.update(delta);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOVertexHandle);
        GL11.glVertexPointer(3,GL11.GL_FLOAT,0,0L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,VBOColorHandle);
        GL11.glColorPointer(3,GL11.GL_FLOAT,0,0L);
        GL11.glDrawArrays(GL11.GL_POINTS,0,points);
        
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();
    }
    
    public void clearScreen() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, 1366, 768);
    }
    
    public void initialize3D() {
        GL11.glShadeModel(GL11.GL_SMOOTH); // Smoother textures.
        GL11.glClearColor(0.0f,0.0f,0.0f,0.0f); // BG color. 6698FF
        GL11.glClearDepth(1.0); // Buffer depth, allows objects to draw over things behind them.
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Depth testing (see above).
        GL11.glDepthFunc(GL11.GL_LEQUAL); // Type of depth testing.
        
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY); // Needed for VBO's.
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY); // Needed to send color with VBO's.
        
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Sets matrix mode to displaying pixels.
        GL11.glLoadIdentity(); // Loads the above matrix mode.
        
        // Sets default perspective location.                       Render Distances: Min   Max
        GLU.gluPerspective(90.0f,(float)Display.getWidth()/(float)Display.getHeight(),0.1f,1500.0f);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Sets the matrix to displaying objects.
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,GL11.GL_NICEST); // Something for quality.
        GL11.glEnable(GL11.GL_CULL_FACE); // Efficient rendering.
        GL11.glPointSize(0.25f);
        GL11.glEnable(GL11.GL_BLEND);
    }
}