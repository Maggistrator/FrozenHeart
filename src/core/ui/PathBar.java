package core.ui;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class PathBar {
	public float initialX, initialY;
	public float x, y;
	private float width = 600, height = 20;
	
	float initialDistance;
	float delta;
	float distance;
	
	public PathBar(float initialDistance, int containerWidth, int containerHeight) {
		this.initialDistance = initialDistance;
		initialX = x = (containerWidth - width)/2;
		initialY = y = containerHeight - height*2;
		
		//ищем соотношение 1% расстояния с 1% длины прогрессбара
		delta = (width/100) / (initialDistance/100); 
	}
	
	public void update(float x, float distance) {
		this.x = initialX + x;
		this.distance = distance;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.drawRoundRect(x, y, width, height, 4);
		
		String distanceLabel = "Distance to get safe: "+(int)(initialDistance - distance);
		AngelCodeFont font = (AngelCodeFont) g.getFont();
		
		g.drawString(distanceLabel, x, initialY - height - 4);
		
		float internalWidth = initialDistance - distance > delta ? distance*delta : width-4;
		g.fillRoundRect(x+2, y+3, internalWidth, height-5, 4);
		g.setColor(Color.white);
	}
	
}
