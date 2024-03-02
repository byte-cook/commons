package de.kobich.commons.runtime.executor.command;


public enum CommandDefinitionXMLTag {
	ROOT("command-definition"),
	CMD("command"),
	PARAM("param"),
	ENV("env"),
	ATT_ID("id"),
	KEY("key"),
	VALUE("value"),
	;
	
	private final String tag;
	
	private CommandDefinitionXMLTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
}
