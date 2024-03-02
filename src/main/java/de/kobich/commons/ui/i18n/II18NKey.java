package de.kobich.commons.ui.i18n;

import java.util.ResourceBundle;

public interface II18NKey {
	public String getId();
	
	public ResourceBundle getBundle();
	
	public String getDefault();
}
