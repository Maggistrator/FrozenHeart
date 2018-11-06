package core.ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import core.TrueTypeFont;

public class Button {
	/**включает отрисовку активных границ*/
	public boolean DEBUG = false;
	/**выравнивание текста по левому краю*/
	public static final int LEFT = 0;
	/**выравнивание текста по центру*/
	public static final int CENTER = 1;
	
	public String text;
	public int x = 0, y = 0;
	public int width = 0, height = 0;
	
	private TrueTypeFont font;
	public Rectangle hitbox;
	private Graphics g;	
	
	private int current_align = LEFT;
	
	public Button(String text, int x, int y) {
		this.x = x;
		this.y = y;
		this.text = text;
		
		hitbox = new Rectangle(x, y, width, height);
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

	public int getCurrent_align() {
		return current_align;
	}
	
	public void setCurrent_align(int current_align) {
		this.current_align = current_align;
	}

	public boolean mouseClicked() {
		// TODO Auto-generated method stub
		return false;
	}
}
