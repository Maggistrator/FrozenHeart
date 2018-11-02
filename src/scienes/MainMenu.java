package scienes;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.TrueTypeFont;
import core.ui.Button;

public class MainMenu extends BasicGameState {

	Button new_game;
	Button about;
	Button exit;

	int selected = 1;

	Sound switchSound;
	Sound btnSound;

	Music bgmusic;
	StateBasedGame game;
	
	Image newgame_snowhat;
	Image about_snowhat;
	Image exit_snowhat;
	
	Font cool_font = null;
	Font active_cool_font = null;
	
    public TrueTypeFont slicFont;
	private TrueTypeFont activeSlicFont;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		int button_x = 20, button_y = 80;
		new_game = new Button("New Game", button_x, button_y, 120, 20);
		about = new Button("About", button_x, button_y + 65, 120, 20);
		exit = new Button("Exit", button_x, button_y + 120, 60, 20);
		
		newgame_snowhat = new Image("textures/ui/snow.png").getScaledCopy(1.15f);
		about_snowhat = new Image("textures/ui/snow.png").getScaledCopy(0.9f);
		exit_snowhat = new Image("textures/ui/snow.png").getScaledCopy(0.7f);
		
		btnSound = new Sound("res/sounds/mparsons99__snow-crunch.ogg");
		bgmusic = new Music("res/music/chaykovskiy.ogg");
		bgmusic.play();
		
		try {
			cool_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/10447.ttf")).deriveFont(24f);
			active_cool_font = cool_font.deriveFont(28f);
			
			slicFont = new TrueTypeFont(cool_font, true,("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
			activeSlicFont = new TrueTypeFont(active_cool_font, true,
					("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		setCharset_Russian(g);

		if (selected == 1) {
			g.setFont(activeSlicFont);
			g.setColor(Color.cyan);
		}
		else {
			g.setFont(slicFont);
			g.setColor(Color.white);
		}
		new_game.draw(g);
		g.drawImage(newgame_snowhat, new_game.x-10, new_game.y - 45);
		
		if (selected == 2) {
			g.setFont(activeSlicFont);
			g.setColor(Color.cyan);
		}
		else {
			g.setFont(slicFont);
			g.setColor(Color.white);
		}
		about.draw(g);
		g.drawImage(about_snowhat, about.x-15, about.y - 35);
		
		if (selected == 3) {
			g.setFont(activeSlicFont);
			g.setColor(Color.cyan);
		}
		else {
			g.setFont(slicFont);
			g.setColor(Color.white);
		}
		exit.draw(g);
		g.drawImage(exit_snowhat, exit.x-17, exit.y - 25);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		int previousSelectedItem = selected;
		
		if(new_game.hitbox.contains(newx, newy)) selected = 1;
		if(about.hitbox.contains(newx, newy)) selected = 2;
		if(exit.hitbox.contains(newx, newy)) selected = 3;
		
		if(selected != previousSelectedItem) btnSound.play(1, 0.3f);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);

		if(new_game.hitbox.contains(x, y))
			//новая игра
		if(about.hitbox.contains(x, y))
			//об авторе
		if(exit.hitbox.contains(x, y)) 
			//выход
			System.exit(0);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_UP && selected > 1) {
			selected--;
			btnSound.play(1, 0.3f);
		}
		if (key == Input.KEY_DOWN && selected < 3) {
			selected++;
			btnSound.play(1, 0.3f);
		}
		if (key == Input.KEY_ENTER) {
			switch (selected) {
			case 1:
				//"начать игру"
				break;
			case 2:
				//это для "about"
				break;
			case 4:
				//выход
				System.exit(0);
				break;
			}
		}
	}
	
	/**проверяет, установлен ли русский шрифт, и если это не так, устанавливает его*/
	private void setCharset_Russian(Graphics g) {
		if(!g.getFont().equals(slicFont)) {
			g.setFont(slicFont);
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
