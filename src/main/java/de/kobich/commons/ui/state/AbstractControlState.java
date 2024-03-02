package de.kobich.commons.ui.state;


public abstract class AbstractControlState implements IControlState {
	public boolean singleSelection; 
	public boolean multiSelection;
	public boolean noSelection;
	
	public AbstractControlState() {
		this.singleSelection = true;
		this.multiSelection = true;
		this.noSelection = false;
	}

	/**
	 * @return the singleSelection
	 */
	public boolean isSingleSelection() {
		return singleSelection;
	}
	/**
	 * @return the multiSelection
	 */
	public boolean isMultiSelection() {
		return multiSelection;
	}
	/**
	 * @return the noSelection
	 */
	public boolean isNoSelection() {
		return noSelection;
	}
}
