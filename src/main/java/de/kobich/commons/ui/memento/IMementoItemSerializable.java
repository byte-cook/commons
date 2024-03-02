package de.kobich.commons.ui.memento;



public interface IMementoItemSerializable {
	/**
	 * Saves the state
	 * @param memento
	 */
	public void saveState();
	
	/**
	 * Restores the state
	 * @param memento
	 */
	public void restoreState();
}
