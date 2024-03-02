package de.kobich.commons.runtime.executor.command;

public class CommandId implements ICommandId {
	private final String name;

	public CommandId(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
