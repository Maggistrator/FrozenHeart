package logic.entity;

import java.util.Observable;

public class Pony extends Observable {

	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}
}
