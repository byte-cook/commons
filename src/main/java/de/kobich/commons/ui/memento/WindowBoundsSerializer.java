package de.kobich.commons.ui.memento;

public class WindowBoundsSerializer implements IMementoItemSerializer2<WindowBounds> {
	private static final String HEIGHT_POSTFIX = "-Height";
	private static final String WIDTH_POSTFIX = "-Width";
	private static final String X_POSTFIX = "-X";
	private static final String Y_POSTFIX = "-Y";
	private static final String MAXIMIZED_POSTFIX = "-Maximized";
	private static final String MINIMIZED_POSTFIX = "-Minimized";
	private final String stateName;
	
	/**
	 * @param stateName
	 */
	public WindowBoundsSerializer(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public void save(WindowBounds b, IMementoItem mementoItem) {
		mementoItem.putInteger(stateName + HEIGHT_POSTFIX, b.height);
		mementoItem.putInteger(stateName + WIDTH_POSTFIX, b.width);
		mementoItem.putInteger(stateName + X_POSTFIX, b.x);
		mementoItem.putInteger(stateName + Y_POSTFIX, b.y);
		mementoItem.putBoolean(stateName + MAXIMIZED_POSTFIX, b.maximized);
		mementoItem.putBoolean(stateName + MINIMIZED_POSTFIX, b.minimized);
	}

	@Override
	public void restore(WindowBounds b, IMementoItem mementoItem) {
		WindowBounds bounds = restore(mementoItem);
		b.height = bounds.height;
		b.width = bounds.width;
		b.x = bounds.x;
		b.y = bounds.y;
		b.maximized = bounds.maximized;
		b.minimized = bounds.minimized;
	}

	@Override
	public WindowBounds restore(IMementoItem mementoItem) {
		WindowBounds bounds = new WindowBounds();
		bounds.height = mementoItem.getInteger(stateName + HEIGHT_POSTFIX, 800);
		bounds.width = mementoItem.getInteger(stateName + WIDTH_POSTFIX, 1000);
		bounds.x = mementoItem.getInteger(stateName + X_POSTFIX, 100);
		bounds.y = mementoItem.getInteger(stateName + Y_POSTFIX, 100);
		bounds.maximized = mementoItem.getBoolean(stateName + MAXIMIZED_POSTFIX);
		bounds.minimized = mementoItem.getBoolean(stateName + MINIMIZED_POSTFIX);
		return bounds;
	}

}
