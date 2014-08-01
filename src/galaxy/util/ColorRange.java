package galaxy.util;

import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ColorRange {
    
    private Color[] colors;
    private Random r;
    
    public ColorRange(String ref) {
        r = new Random();
        Image image = null;
        
        try {
            image = new Image(ref);
        } catch (SlickException e) {
            throw new RuntimeException("Failed to load colormap: " + e);
        }
        
        ArrayList<Color> colorList = new ArrayList<Color>();
        
        for (int i=0;i<image.getWidth();i++) {
            Color color = image.getColor(i,1);
            if (!colorList.contains(color))
                colorList.add(color);
        }
        
        colors = new Color[colorList.size()];
        
        colorList.toArray(colors);
    }
    
    public Color getColor() {
        return colors[r.nextInt(colors.length-1)];
    }
}