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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.Button;
import core.TrueTypeFont;
import scienes.Launcher;

public class About extends BasicGameState { 
	StateBasedGame game;
	
	Image background;
	Button back;
	
	private Font cool_font;
	private Font active_cool_font;
	private TrueTypeFont slicFont;
	private TrueTypeFont activeSlicFont;
	private boolean active = false;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		
		background = new Image("textures/misc/colored sketch.png");
		back = new Button("Назад", 20, container.getHeight() - 40);
		
		//подключаем шрифты
		try {
			cool_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/10447.ttf")).deriveFont(18f);
			active_cool_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/10447.ttf")).deriveFont(24f);
			slicFont = new TrueTypeFont(cool_font, true,("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
			activeSlicFont = new TrueTypeFont(active_cool_font, true, ("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		setCharset_Russian(g);
		g.setColor(Color.black);
		
		background.draw();
		
		g.drawString("Автор: Сова", 10, 10);
		g.drawString("Жанр: Shoot 'em up", 10, 30);
		g.drawString("Дата начала разработки: 20 октября 2018 года", 10, 50);
		g.drawString("Движок: Slick2D (Java)", 10, 70);
		g.drawString("Сделано на \"Shoot 'em up\" Табунский Конкурс Игростроения", 10, 90);
		
		if(active) g.setFont(activeSlicFont);
		else g.setFont(slicFont);
		back.draw(g);
		container.setShowFPS(false);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		if(back.hitbox.contains(newx, newy)) active = true;
		else active = false;
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		if(back.hitbox.contains(x, y)) game.enterState(Launcher.MENU);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if(key == Input.KEY_ENTER || key == Input.KEY_ESCAPE) game.enterState(Launcher.MENU);
	}

	@Override
	public int getID() {
		return 2;
	}
	
	/**проверяет, установлен ли русский шрифт, и если это не так, устанавливает его*/
	private void setCharset_Russian(Graphics g) {
		if(!g.getFont().equals(slicFont)) {
			g.setFont(slicFont);
		}
	}
}
