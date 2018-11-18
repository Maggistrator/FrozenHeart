package core.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class GameUI {

	Texture ult;
	
	public GameUI() throws FileNotFoundException, IOException {
		ult = TextureLoader.getTexture("PNG", new FileInputStream("textures/sprites/starlight/starlight_calm.png"), false);
	}

}
