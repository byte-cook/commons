package de.kobich.commons.ui.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows to control the enabled state of ui controls depending on selection events (e.g. in a ui table).
 * @author ckorn
 */
public class ControlStateRepository {
	private List<IControlState> items;
	
	public ControlStateRepository() {
		this.items = new ArrayList<IControlState>();
	}
	
	/**
	 * Adds an ui control
	 * @param item
	 */
	public void add(IControlState item) {
		items.add(item);
	}
	
	/**
	 * Informs about single item selection
	 */
	public void fireSingleItemSelected() {
		for (IControlState item : items) {
			item.setEnabled(item.isSingleSelection());
		}
	}
	
	/**
	 * Informs about multiple items selection
	 */
	public void fireMultipleItemsSelected() {
		for (IControlState item : items) {
			item.setEnabled(item.isMultiSelection());
		}
	}
	
	/**
	 * Informs about no item selection
	 */
	public void fireNoItemSelected() {
		for (IControlState item : items) {
			item.setEnabled(item.isNoSelection());
		}
	}
}
