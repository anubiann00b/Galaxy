package galaxy.render;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class VerticesManager {
    
    private float r;
    private float g;
    private float b;
    
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    
    private int length = 0;
    
    public VerticesManager(int newLength) {
        r = 0;
        g = 0;
        b = 0;
        
        this.length = newLength;
        
        vertexBuffer = BufferUtils.createFloatBuffer(length);
        colorBuffer = BufferUtils.createFloatBuffer(length);
    }
    
    public int size() { return length; }
    
    public void setColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public void addVertex(float x, float y, float z) {
        vertexBuffer.put(x);
        vertexBuffer.put(y);
        vertexBuffer.put(z);

        colorBuffer.put(r);
        colorBuffer.put(g);
        colorBuffer.put(b);
    }
    
    public FloatBuffer getPositionData() {
        return vertexBuffer;
    }
    
    public FloatBuffer getColorData() {
        return colorBuffer;
    }
}