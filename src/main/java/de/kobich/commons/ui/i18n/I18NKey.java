package de.kobich.commons.ui.i18n;

import java.util.ResourceBundle;

public class I18NKey implements II18NKey {
	private final String id;
	private final ResourceBundle bundle;
	private final String defaultText;
	
	public I18NKey(String id, ResourceBundle bundle) {
		this(id, bundle, null);
	}
	public I18NKey(String id, ResourceBundle bundle, String defaultText) {
		this.id = id;
		this.bundle = bundle;
		this.defaultText = defaultText;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ResourceBundle getBundle() {
		return bundle;
	}

	@Override
	public String getDefault() {
		return defaultText;
	}

}
