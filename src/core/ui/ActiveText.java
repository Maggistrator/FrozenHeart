package core.ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import core.TrueTypeFont;

public class ActiveText {
	public String text;
	public boolean DEBUG = false;
	public int x, y = 0;
	public int width, height = 100;
	/**выравнивание текста по левому краю компонента*/
	public static final int LEFT = 0;
	/**выравнивание текста по центру*/
	public static final int CENTER = 1;
	private TrueTypeFont font;
	public Rectangle hitbox;
	private int current_align = LEFT;
	Graphics g;		
	
	public ActiveText(String text) {
		this.text = text;
		hitbox = new Rectangle(x, y, width, height);
	}

	public ActiveText(String text, int x, int y, int width, int height) {
		this.text = text;
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		if(this.g==null) this.g = g;
		if(font == null) font = (TrueTypeFont) g.getFont();
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

	public int getCurrent_align() {
		return current_align;
	}
	
	public void setCurrent_align(int current_align) {
		this.current_align = current_align;
	}
}
