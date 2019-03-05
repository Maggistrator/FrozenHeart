package logic.pony;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import core.TrueTypeFont;

public class Statistics {
	
	/**Нанесённый урон.*/
	public float damageDealed = 0;
	/**Убито противников.*/
	public float enemiesKilled = 0;

	/**Сколько стресспоинтов восстановлено на матч*/
	public float stresspointsRestored = 0;
	/**Время прохождения в секундах*/
	public float timeUsed = 0; 

	
	//TODO: зарефакторить через функцию, обрабатывающую эти строки однотипным образом, и сократить код в 4 раза
	public void render(float x, float y, Graphics g, GameContainer container) {
		String damageString = "Урона нанесено: " + (int)damageDealed;
		String killsString = "Монстров убито: " + (int)enemiesKilled;
		String stresspointsString = "Цветов стресса восстановлено: " + (int)stresspointsRestored;
		String timeString = "Время прохождения: " + (int)timeUsed/60 + ":" + (int)timeUsed % 60;
		
		TrueTypeFont font = (TrueTypeFont) g.getFont();
		
		float damageX = x + (container.getWidth() - font.getWidth(damageString))/2;
		float killsX = x + (container.getWidth() - font.getWidth(killsString))/2;
		float stressX = x + (container.getWidth() - font.getWidth(stresspointsString))/2;
		float timeX = x + (container.getWidth() - font.getWidth(timeString))/2;
		
		g.drawString(damageString, damageX, y);
		g.drawString(killsString, killsX, y+20);
		g.drawString(stresspointsString, stressX, y+40);
		g.drawString(timeString, timeX, y+60);
	}
}
