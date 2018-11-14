package emitters;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public class SnowEmitter implements ParticleEmitter {
	
	private int interval = 500;
	private int timer;
	private float size = 5f;
	private int life = 2000;
	Random rand = new Random();
	float last = 0.990f;
	boolean isIncreasingSize = true;
	private float deltasize = 0.01f;
	
	private int spawnRangeWidth = 500;
	private int spawnRangeHeight = 250;
	
	
	private boolean isEnabled = true;
	private boolean completed = false;
	private final int STOP_CODE = -256;
	
	public SnowEmitter(int spawnRangeWidth, int spawnRangeHeight) throws SlickException {
		this.spawnRangeWidth = spawnRangeWidth;
		this.spawnRangeHeight = spawnRangeHeight;
	}

	
	public void update(ParticleSystem system, int delta) {
		if (timer != STOP_CODE) {
			interval = rand.nextInt(interval);
			timer -= delta;
			
			if (timer <= 0 && timer > STOP_CODE) {
				Particle particle = system.getNewParticle(this, life);
				particle.setPosition(rand.nextInt(spawnRangeWidth), rand.nextInt(spawnRangeHeight));
				particle.setSize(size);
				particle.setLife(life);
				//particle.setColor(0.0f,0.0f,0.0f,0.0f);
				
				timer = interval;
			}
		}
	}

	public void updateParticle(Particle particle, int delta) {
		Color color = particle.getColor();
		float size = particle.getSize();
		
		if(isIncreasingSize) deltasize+=0.01f; else deltasize -= 0.01f;
		if(deltasize>0.07f || deltasize<-0.07f) isIncreasingSize=!isIncreasingSize;

	}
	
	public int getSpawnRangeWidth() {
		return spawnRangeWidth;
	}

	public int getSpawnRangeHeight() {
		return spawnRangeHeight;
	}
	
	public void setSpawnRangeHeight(int spawnRangeHeight) {
		this.spawnRangeHeight = spawnRangeHeight;
	}

	public void setSpawnRangeWidth(int spawnRangeWidth) {
		this.spawnRangeWidth = spawnRangeWidth;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean enabled) {
	}

	public boolean completed() {
		return completed;
	}

	public boolean useAdditive() {
		return false;
	}

	public Image getImage() {
		return null;
	}

	public boolean usePoints(ParticleSystem system) {
		return false;
	}

	public boolean isOriented() {
		return false;
	}

	public void wrapUp() {
		timer = STOP_CODE;
		completed = true;
		isEnabled = false;
	}

	public void resetState() {
		interval = 500;
		size = 7f;
		life = 2000;
		last = 0.990f;
	    isIncreasingSize = true;
		deltasize = 0.01f;
		spawnRangeWidth = 500;
		spawnRangeHeight = 250;
		completed = false;
		isEnabled = true;
		timer = 0;
	}

}
