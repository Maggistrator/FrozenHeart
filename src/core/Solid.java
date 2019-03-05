package core;

import org.newdawn.slick.Image;

import it.marteEngine.entity.Entity;

public class Solid extends Entity {

	public Solid(float x, float y) {
		super(x, y);
	}
	
	public Solid(float x, float y, Image img) { 
		super(x, y, img);
	}
	
}
