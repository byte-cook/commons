package de.kobich.commons.runtime.executor.command;

public class CommandDefinitionEnv {
	private final ICommandId id;
	private final String key;
	private final String value;

	public CommandDefinitionEnv(ICommandId id, String key, String value) {
		this.id = id;
		this.key = key;
		this.value = value;
	}

	public ICommandId getCommandId() {
		return id;
	}
	
	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
