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
	
	Image title_snowhat;
	Image starsky;
	Image moon;
	Image background;
	
	
	Font title_font = null;
	Font cool_font = null;
	Font active_cool_font = null;
	
    public TrueTypeFont titleTTFont;
    public TrueTypeFont slicFont;
	private TrueTypeFont activeSlicFont;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		int button_x = 20, button_y = 140;
		new_game = new Button("Новая игра", button_x, button_y);
		about = new Button("Об авторе", button_x, button_y + 40);
		exit = new Button("Выход", button_x, button_y + 80);
		
		title_snowhat = new Image("textures/main_menu/snow4.png").getScaledCopy(0.6f);
		starsky = new Image("textures/main_menu/stars.png").getScaledCopy(0.35f);
		moon = new Image("textures/main_menu/moon.png").getScaledCopy(0.2f);
		background = new Image("textures/main_menu/background.png");

		btnSound = new Sound("res/sounds/mparsons99__snow-crunch.ogg");
		bgmusic = new Music("res/music/chaykovskiy.ogg");//37.5
		//bgmusic.play();
		
		try {
			title_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/BighausTitulBrkHll.ttf")).deriveFont(34f);
			titleTTFont = new TrueTypeFont(title_font, true); 
			
			cool_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/10447.ttf")).deriveFont(22f);
			active_cool_font = cool_font.deriveFont(26f);
			
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
		background.draw();
		//starsky.draw(240, 80);
		moon.draw(container.getWidth() - 240, -40);
		
		g.setFont(titleTTFont);
		g.setColor(Color.white);
		g.drawString("Frozen Heart", 20, 70);
		g.drawImage(title_snowhat, -10, -30);
		
		if (selected == 1) {
			g.setFont(activeSlicFont);
			g.setColor(Color.yellow);
		}
		else {
			g.setFont(slicFont);
			g.setColor(Color.white);
		}
		new_game.draw(g);
		
		if (selected == 2) {
			g.setFont(activeSlicFont);
			g.setColor(Color.yellow);
		}
		else {
			g.setFont(slicFont);
			g.setColor(Color.white);
		}
		about.draw(g);
		
		if (selected == 3) {
			g.setFont(activeSlicFont);
			g.setColor(Color.yellow);
		}
		else {
			g.setFont(slicFont);
			g.setColor(Color.white);
		}
		exit.draw(g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

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
	
	int mouse_x;
	int mouse_y;
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);

		if(new_game.hitbox.includes(x, y))
			//новая игра
		if(about.hitbox.includes(x, y))
			//об авторе
		if(exit.hitbox.contains(x, y)) System.exit(0);
		mouse_x = x;
		mouse_y = y;
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
			case 3:
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
