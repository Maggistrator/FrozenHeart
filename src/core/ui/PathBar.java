package core.ui;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import it.marteEngine.entity.Entity;

public class PathBar extends Entity{
	public static float SCALE_FACTOR = 1;
	
	public float initialX, initialY;
	
	float initialDistance;
	float delta;
	float distance;
	
	public PathBar(float initialDistance, int containerWidth, int containerHeight) {
		super((containerWidth - 600 * SCALE_FACTOR)/2, containerHeight - 20*SCALE_FACTOR*2);
		this.initialDistance = initialDistance;
		initialX = x;
		initialY = y;
		
		width = (int) (600 * SCALE_FACTOR);
		height = (int) (20 * SCALE_FACTOR);
		
		//ищем соотношение 1% расстояния с 1% длины прогрессбара
		delta = (width/100) / (initialDistance/100); 
	}
	
	public void update(GameContainer container, float x, float distance, int delta) throws SlickException {
		super.update(container, delta);
		this.distance = distance;
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		super.render(container, g);
		g.setColor(Color.gray);
		g.drawRoundRect(x, y, width, height, 4);
		
		String distanceLabel = "Distance to get safe: "+(int)(initialDistance - distance);
		AngelCodeFont font = (AngelCodeFont) g.getFont();
		
		g.drawString(distanceLabel, x, initialY - height - 4);

		float internalWidth = initialDistance - distance > delta ? distance * delta : width - 4;
		internalWidth = internalWidth > 0 ? internalWidth : 0;// не даём прогресс-бару пересекать левую границу
		g.fillRoundRect(x + 2, y + 3, internalWidth, height - 5, 4);
		
		g.setColor(Color.white);
	}
	
}
