package de.kobich.commons.runtime.executor.command;

import java.util.List;

public class CommandDefinition {
	private final String command;
	private final List<CommandDefinitionParam> params;
	private final List<CommandDefinitionEnv> environment;

	public CommandDefinition(String command, List<CommandDefinitionParam> params, List<CommandDefinitionEnv> environment) {
		this.command = command;
		this.params = params;
		this.environment = environment;
	}

	public String getCommand() {
		return command;
	}

	public List<CommandDefinitionParam> getParams() {
		return params;
	}

	public List<CommandDefinitionEnv> getEnvironment() {
		return environment;
	}

}
