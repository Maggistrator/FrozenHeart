package scienes.playable;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import core.Button;
import core.TrueTypeFont;
import it.marteEngine.World;
import logic.entity.StarlightGlimmer;
import scienes.Launcher;

public class LastState extends World {
	
	Font title_font = null;
	Font cool_font = null;
	Font statsFont = null;
	Font active_cool_font = null;
	
    public TrueTypeFont titleTTFont;
    public TrueTypeFont slicFont;
    public TrueTypeFont trueTypeStatsFont;
	private TrueTypeFont activeSlicFont;
	
	String title;
	String description;
	
	Color titlecolor;
	
	Button yes;
	Button no;
	
	float titleX = 0, titleY = 0;
	float descriptionX = 0, descriptionY = 0;	
	
	private StateBasedGame game;
	StarlightGlimmer pony;

	public LastState(int id, StarlightGlimmer pony) {
		super(id);
		this.pony = pony;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		if(pony.hope > 0) {
			title = "Победа!";
			titlecolor = Color.green;
		} else {
			title = "Поражение.";
			titlecolor = Color.red;
		}
		description = "Поможешь Старли сразиться с её кошмарами ещё раз?";
		
		this.container = container;
		this.game = game;
		
		loadFonts();
		
		titleX = (container.getWidth() - titleTTFont.getWidth(title))/2;
		titleY = container.getHeight()/2 - 100;
		
		descriptionX = (container.getWidth() - slicFont.getWidth(description))/2;
		descriptionY = titleY + 150;
		
		
		float yesX = (container.getWidth() - slicFont.getWidth("yes"))/2 - (slicFont.getWidth("yes") + 20);
		float yesY = descriptionY + 30;
		yes = new Button("Да", yesX, yesY);
		
		float noX = (container.getWidth() - slicFont.getWidth("no"))/2 + slicFont.getWidth("no")+20;
		float noY = yesY;
		no = new Button("Нет", noX, noY);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		super.render(container, game, g);
		setCharset_Russian(g);

		//заголовок, соответствующим случаю цветом
		g.setColor(titlecolor);
		g.setFont(titleTTFont);
		g.drawString(title, titleX, titleY);
		
		//описание
		g.setColor(Color.yellow);
		g.setFont(slicFont);
		g.drawString(description, descriptionX, descriptionY);
		
		//статы
		g.setFont(trueTypeStatsFont);
		g.setColor(Color.white);
		float longestString = trueTypeStatsFont.getWidth("Цветов стресса восстановлено: "+(int)pony.stats.stresspointsRestored);
		pony.stats.render(0,  titleY + 52, g, container);
		
		//кнопка "да"
		g.setColor(Color.yellow);
		if(yes.isSelected()) g.setFont(activeSlicFont);
		else g.setFont(slicFont);
		yes.draw(g);
		
		//кнопка нет
		if(no.isSelected()) g.setFont(activeSlicFont);
		else g.setFont(slicFont);
		no.draw(g);
		
		g.setColor(Color.white);
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		if(yes.contains(newx, newy)) yes.setSelected(true);
		else yes.setSelected(false);
		
		if(no.contains(newx, newy)) no.setSelected(true);
		else no.setSelected(false);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		if(yes.contains(x, y)) game.enterState(Launcher.SCIENE_1);
		if(no.contains(x, y)) game.enterState(Launcher.MENU);
	}
	
	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if(key == Input.KEY_ENTER) game.enterState(Launcher.SCIENE_1);
		if(key == Input.KEY_ESCAPE) game.enterState(Launcher.MENU);		
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
			title_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/BighausTitulBrkHll.ttf")).deriveFont(32f);
			statsFont = new Font("Courier New", Font.PLAIN, 16);
			cool_font = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/10447.ttf")).deriveFont(20f);
			//cool_font = new Font("Arial", Font.PLAIN, 22);
			active_cool_font = cool_font.deriveFont(26f);

			titleTTFont = new TrueTypeFont(title_font, true, ("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray()); 
			slicFont = new TrueTypeFont(cool_font, true,("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
			trueTypeStatsFont = new TrueTypeFont(statsFont, true,("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
			activeSlicFont = new TrueTypeFont(active_cool_font, true, ("йцукенгшщзхъфывапролджэячсмитьбюё".toUpperCase()+"йцукенгшщзхъфывапролджэячсмитьбюё").toCharArray());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
