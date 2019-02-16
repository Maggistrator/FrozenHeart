package scienes.transfer;

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
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Button;
import core.TrueTypeFont;
import scienes.Launcher;

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
	
	ParticleSystem snow;
	ParticleSystem flakes;
	ParticleSystem snowcrunch;
	
    public TrueTypeFont titleTTFont;
    public TrueTypeFont slicFont;
	private TrueTypeFont activeSlicFont;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		int button_x = 20, button_y = 140;
		new_game = new Button("Новая игра", button_x, button_y);
		about = new Button("О проекте", button_x, button_y + 40);
		exit = new Button("Выход", button_x, button_y + 80);
		
		title_snowhat = new Image("textures/main_menu/snow4.png").getScaledCopy(0.6f);
		starsky = new Image("textures/main_menu/stars.png").getScaledCopy(0.35f);
		moon = new Image("textures/main_menu/moon.png").getScaledCopy(0.3f);
		background = new Image("textures/main_menu/background.png");

		btnSound = new Sound("res/sounds/mparsons99__snow-crunch.ogg");
		
		if (bgmusic == null) {
			bgmusic = new Music("res/music/PMV - Control.ogg");
			bgmusic.loop();
		}

		loadFonts();
		loadEmitters();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		setCharset_Russian(g);
		background.draw();
		moon.draw(container.getWidth() - 300, -80);
		starsky.draw();
		
		g.setFont(titleTTFont);
		g.setColor(Color.white);
		g.drawString("Frozen Heart", 20, 70);
		g.drawImage(title_snowhat, -10, -30);
		
		if (selected == 1) {
			g.setFont(activeSlicFont);
			g.setColor(Color.yellow);
			snowcrunch.render(new_game.x, new_game.y + new_game.height);
		}
		else {
			g.setFont(slicFont);
			g.setColor(Color.white);
		}
		new_game.draw(g);
		
		if (selected == 2) {
			g.setFont(activeSlicFont);
			g.setColor(Color.yellow);
			snowcrunch.render(about.x, about.y + about.height);
		}
		else {
			g.setFont(slicFont);
			g.setColor(Color.white);
		}
		about.draw(g);
		
		if (selected == 3) {
			g.setFont(activeSlicFont);
			g.setColor(Color.yellow);
			snowcrunch.render(exit.x, exit.y + exit.height);
		}
		else {
			g.setFont(slicFont);
			g.setColor(Color.white);
		}
		exit.draw(g);
		
		snow.render();
		flakes.render();
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		snow.update(delta);
		flakes.update(delta);
		snowcrunch.update(delta);
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		int previousSelectedItem = selected;
		
		if(new_game.hitbox.contains(newx, newy)) {
			selected = 1;
			//настройка ширины эмиттера - все надписи разные
			resetSnowcrunchWidth(); 
		}
		if(about.hitbox.contains(newx, newy)) {
			selected = 2;
			resetSnowcrunchWidth(); 
		}
		if(exit.hitbox.contains(newx, newy)) {
			selected = 3;
			resetSnowcrunchWidth(); 
		}
		
		if(selected != previousSelectedItem) {
			btnSound.play(1, 0.3f);
			snowcrunch.reset();
		}

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		if (new_game.hitbox.contains(x, y)) {
			try {
				bgmusic.stop();
				Sound sound = new Sound("res/sounds/aiwha__ding.ogg");
				sound.play();
				game.enterState(Launcher.SCIENE_1);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		// новая игра
		if (about.hitbox.contains(x, y)) {
			game.enterState(Launcher.ABOUT);
		}
		// об авторе
		if (exit.hitbox.contains(x, y)) {
			System.exit(0);
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_UP && selected > 1) {
			selected--;
			snowcrunch.reset();
			resetSnowcrunchWidth(); 
			btnSound.play(1, 0.3f);
		}
		if (key == Input.KEY_DOWN && selected < 3) {
			selected++;
			snowcrunch.reset();
			resetSnowcrunchWidth(); 
			btnSound.play(1, 0.3f);
		}
		if (key == Input.KEY_ENTER) {
			switch (selected) {
			case 1:
				game.enterState(Launcher.SCIENE_1);
				bgmusic.stop();
				break;
			case 2:
				game.enterState(Launcher.ABOUT);
				break;
			case 3:
				//выход
				bgmusic.stop();
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

	/**
	 * подключаем шрифты
	 * */
	private void loadFonts() {
		try {
			title_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/BighausTitulBrkHll.ttf")).deriveFont(34f);
			titleTTFont = new TrueTypeFont(title_font, true); 
			
			cool_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/10447.ttf")).deriveFont(22f);
			active_cool_font = cool_font.deriveFont(26f);
			
			slicFont = new TrueTypeFont(cool_font, true,("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
			activeSlicFont = new TrueTypeFont(active_cool_font, true, ("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Эта волшебная функция загружает эмиттеры снежка в менюшку
	 * */
	private void loadEmitters() {
		try {
			//системы частиц
			snow = new ParticleSystem("textures/particles/snowflake2.png");
			flakes = new ParticleSystem("textures/particles/snowflake.png");
			snowcrunch = new ParticleSystem("textures/particles/snowflake2.png");
			snowcrunch.setRemoveCompletedEmitters(false);

			//эмиттеры, вбрасывает IOException
			ConfigurableEmitter snowEmitter = ParticleIO.loadEmitter("res/emitters/snow.xml");
			ConfigurableEmitter snowCrunchEmitter = ParticleIO.loadEmitter("res/emitters/snowcrunch.xml");
			
			snow.addEmitter(snowEmitter);
			flakes.addEmitter(snowEmitter);
			snowcrunch.addEmitter(snowCrunchEmitter);
		} catch (IOException e) {
			System.err.println("частицы снега сожрал разработчик, чтобы схватить киберангину и не ходить на электропары");
		}
	}

	private void resetSnowcrunchWidth() {
		//для того, чтобы эмиттер не срабатывал без принудительного селекта
		if(snowcrunch.getEmitter(0) == null) loadEmitters();
		
		ConfigurableEmitter emitter = (ConfigurableEmitter) snowcrunch.getEmitter(0);

		switch (selected) {
		case 1:
			emitter.xOffset.setMax(new_game.width);
			break;
		case 2:
			emitter.xOffset.setMax(about.width);
			break;
		case 3:
			emitter.xOffset.setMax(exit.width);
			break;
		}
	}

	@Override
	public int getID() {
		return 0;
	}

}
