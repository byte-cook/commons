package de.kobich.commons.ui.state;

public interface IControlState {
	/**
	 * Enables the receiver if the argument is <code>true</code>, and disables it otherwise
	 * @param b
	 */
	public void setEnabled(boolean b);

	/**
	 * @return the singleSelection
	 */
	public boolean isSingleSelection();

	/**
	 * @return the multiSelection
	 */
	public boolean isMultiSelection();

	/**
	 * @return the noSelection
	 */
	public boolean isNoSelection();
}
