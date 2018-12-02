package logic.event;

public class CameraEvent {

	public float previous_x;
	public float previous_y;
	
	public float new_x;
	public float new_y;
	
	
	public CameraEvent(float x, float y) {
		this.new_x = x;
		this.new_y = y;
	}
	
}
