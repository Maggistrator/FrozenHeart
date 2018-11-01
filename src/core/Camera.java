package core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import it.marteEngine.entity.Entity;

public class Camera {
	// ��������, �� ������� ������� ������
	private Entity toFollow;
	// ������� ������
	private Rectangle bounds;
	// ������� ��������, �� ������� ������� ������
	private Rectangle hitBox2Follow = new Rectangle(0,0,20,20);
	
	//���������� ������
	public float X = 0;
	public float Y = 0;
	
	
	/**
	 * ����������� ������
	 * @param toFollow - ��������, �� ������� ������ ��������� ������
	 * @param bounds - ������� ������ (������, ������ �������� �������� ����) 
	 * @param container - ������� ���������, �� ��� ������� ������� ������������� ��������
	 **/
	public Camera(Entity toFollow, Rectangle bounds, GameContainer container) {
		// �������������� ���������� 
		X = toFollow.x;
		Y = toFollow.y;
		this.toFollow = toFollow;
		this.bounds = bounds;
		hitBox2Follow = new Rectangle(toFollow.x,toFollow.y,toFollow.hitboxWidth,toFollow.hitboxHeight);
		/*
		 * ���� �������, �� ������� ����� ������� �� ������� 
		 * �� �����, �� ��� ����������� �� [������� ������� - (������� ����������+�������)/2]
		 * ��������, ��� ��������� �������, � ������� ������ ������ �� �������
		 * �� � ���������, ������ ������������
		 * */
		if(bounds.getWidth()>container.getWidth()){
			bounds.setWidth(bounds.getWidth()-(container.getWidth()+hitBox2Follow.getWidth())/2);
			bounds.setX(bounds.getX()+(container.getWidth()+hitBox2Follow.getWidth())/2);
		}
		// ������ ��� ���� ��������
		if(bounds.getHeight()>container.getHeight()){
			bounds.setHeight(bounds.getHeight()-(container.getHeight()+hitBox2Follow.getHeight())/2);
			bounds.setY(bounds.getY()+(container.getHeight()+hitBox2Follow.getHeight())/2);
		}
		
	}
	
	/**
	 * ���� ������ �� ��������������, �� ���������� ������������ ���������, ������������
	 * ������ �������� �������� ������ ���������� ������ �����
	 * */
	public void draw(GameContainer container, Graphics g) throws SlickException{
		float hitboxless_boundsX = container.getWidth() - hitBox2Follow.getWidth();
		float hitboxless_boundsY = container.getHeight()- hitBox2Follow.getHeight();
		
		/*
		 * ��� ������ �����������, ���������� ���� ����������� ��������
		 * �� ����������, ��������������� ����������� ��������,
		 * �� ��� ���, ���� �� ������� ������������� ������� �� ���������
		 * ����������, ������ �������� ����������
		 * (�������� ������ � (���������+�������)/2, ��� �������������)
		 * */
		g.translate(-(X - hitboxless_boundsX / 2), -(Y - hitboxless_boundsY / 2));
	}

	public void update(int delta) {
		/*
		 * ������� ����������� �������� ������������ ��� �������� ������ ����������
		 * � ������ �������. ���� �������� �� ����� �� ��� �������, ������ ������
		 * �� ���
		 * */
		sasha_approximation_algorythm(toFollow, this, delta);
	}
	
	//�������� ����������, �������� ���������� ��������, 
	//���� ��� �� ������ �� ������� ���������� �������
	private float bufferedX = 0;
	private float bufferedY = 0;
	
	//����������� �������� ������
	private Vector2f speed_coefficient = new Vector2f(0.005f, 0.002f);
	
	private void sasha_approximation_algorythm(Entity target, Camera camera, int delta) { 
		//������������� �������� ���������� � ���������
		if(target.x > bounds.getX() && target.x < bounds.getWidth()) bufferedX = target.x;
		if(target.y > bounds.getY() && target.y < bounds.getHeight()) bufferedY = target.y;
		
		//���������� �������������
		float deltaX = bufferedX - camera.X;
		float deltaY = bufferedY - camera.Y;
		
		camera.X += deltaX * speed_coefficient.x * delta;
		camera.Y += deltaY * speed_coefficient.y * delta;		
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Camera:");
		result.append("\n"+"x:"+bounds.getX());
		result.append("\n"+"y:"+bounds.getY());
		result.append("\n"+"width: "+bounds.getWidth());
		result.append("\n"+"height: "+bounds.getHeight());
		return result.toString();
	}
	
}
