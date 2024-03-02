package de.kobich.commons.runtime.executor.command;

public class CommandDefinitionParam {
	private final ICommandId id;
	private final String value;

	public CommandDefinitionParam(ICommandId id, String value) {
		this.id = id;
		this.value = value;
	}

	public ICommandId getCommandId() {
		return id;
	}

	public String getValue() {
		return value;
	}

}
