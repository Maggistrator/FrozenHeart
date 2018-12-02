package logic.event;

public class PlayerEvent {

	public static final String POWER_USED = "spellcast";
	public static final String POWER_RECHARGED = "recharge";
	public static final String DAMAGED = "hope vanished";
	public static final String ULT_CHARGED = "ult_percentage";	
	public static final String ICON_CHANGED = "ico";	
	
	public String type;
	public Object obj;
	
	public PlayerEvent(String type, Object arg) {
		this.type = type;
		this.obj = arg;
	}
}
