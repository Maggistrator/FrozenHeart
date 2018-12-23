package core;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Button{
	/**включает отрисовку активных границ*/
	public boolean DEBUG = false;
	/**выравнивание текста по левому краю*/
	public static final int LEFT = 0;
	/**выравнивание текста по центру*/
	public static final int CENTER = 1;
	
	public String text;
	public Image cover = null;
	public float x = 0;
	public float y = 0;
	public int width = 0, height = 0;
	
	private TrueTypeFont font;
	public Rectangle hitbox;
	private Graphics g;	
	
	private int current_align = LEFT;
	
	private boolean selected = false;
	
	public Button(String text, float yesX, float yesY) {
		super();
		this.x = yesX;
		this.y = yesY;
		this.text = text;
		
		hitbox = new Rectangle(yesX, yesY, width, height);
	}

	public Button(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		if(this.g==null) this.g = g;
		if(font == null) font = (TrueTypeFont) g.getFont();
		
		if(cover != null) cover.draw();
		
		if(width == 0 || height == 0) {
			width = font.getWidth(text);
			width += width * 0.05f;
			height = font.getHeight(text);
			height += height * 0.05f;
			x += (int) (width * 0.05f);
			y += (int) (height * 0.05f);
			
			hitbox = new Rectangle(x, y, width, height);
		}

		switch(current_align) {
			case LEFT:
				g.drawString(text, x, y);
			break;
			case CENTER:
				g.drawString(text, x + width/2 - font.getWidth(text)/2, y + height/2 - font.getHeight()/2);
			break;
		}
		
		if(DEBUG) g.draw(hitbox);
	}
		
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
		hitbox.setSize(width, height);
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
		hitbox.setLocation(x, y);
	}

	public boolean contains(float x, float y) {
		return hitbox.contains(x, y);
	}

	public boolean contains(Shape other) {
		return hitbox.contains(other);
	}
	
	public int getCurrent_align() {
		return current_align;
	}
	
	public void setCurrent_align(int current_align) {
		this.current_align = current_align;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setCover(Image cover) {
		this.cover = cover.getScaledCopy(width, height);
	}

	public void removeCover(){
		cover = null;
	}
	
	public boolean mouseClicked() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
