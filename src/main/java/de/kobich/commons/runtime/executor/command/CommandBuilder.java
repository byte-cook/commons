package de.kobich.commons.runtime.executor.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.lang3.StringUtils;

import de.kobich.commons.Reject;
import de.kobich.commons.utils.PlaceholderUtil;

public class CommandBuilder {
	private final CommandDefinition definition;
	private List<String> command;
	private Map<String, String> environment;
	private static final String PLACEHOLDER_ALL = PlaceholderUtil.PLACEHOLDER_BEGIN + "*" + PlaceholderUtil.PLACEHOLDER_END;
	
	public CommandBuilder(File definitionFile) throws IOException, XMLStreamException {
		Reject.ifNull(definitionFile, "Definition file is null");
		Reject.ifFalse(definitionFile.exists(), "Definition file does not exist");
		CommandDefinitionXMLParser parser = new CommandDefinitionXMLParser();
		this.definition = parser.parseFile(definitionFile);
	}
	public CommandBuilder(InputStream definitionStream) throws IOException, XMLStreamException {
		Reject.ifNull(definitionStream, "Definition stream is null");
		CommandDefinitionXMLParser parser = new CommandDefinitionXMLParser();
		this.definition = parser.parseStream(definitionStream);
	}
	public CommandBuilder(CommandDefinition definition) {
		Reject.ifNull(definition, "Definition is null");
		this.definition = definition;
	}
	
	/**
	 * Creates the command by the given parameters
	 * @param params
	 */
	public void createCommand(List<CommandVariable> params) {
		createCommand(params, null);
	}
	
	/**
	 * Creates the command by the given parameters and environments
	 * @param params
	 * @param envs
	 */
	public void createCommand(List<CommandVariable> params, List<CommandVariable> envs) {
		// command
		command = new ArrayList<String>();
		command.add(definition.getCommand());
		for (CommandDefinitionParam param : definition.getParams()) {
			String cmdId = param.getCommandId().getName();
			if (StringUtils.isBlank(cmdId)) {
				command.add(param.getValue());
			}
			else {
				for (CommandVariable variable : getVariablesByName(cmdId, params)) {
					if (variable != null) {
						String cmd = replacePlaceholders(param.getValue(), variable.getValues());
						command.add(cmd);
					}
				}
			}
		}
		// env
		environment = new HashMap<String, String>();
		for (CommandDefinitionEnv env : definition.getEnvironment()) {
			String cmdId = env.getCommandId().getName();
			if (StringUtils.isBlank(cmdId)) {
				environment.put(env.getKey(), env.getValue());
			}
			else {
				for (CommandVariable variable : getVariablesByName(cmdId, envs)) {
					if (variable != null) {
						String key = replacePlaceholders(env.getKey(), variable.getValues());
						String value = replacePlaceholders(env.getValue(), variable.getValues());
						environment.put(key, value);
					}
				}
			}
		}
	}
	
	private List<CommandVariable> getVariablesByName(String name, List<CommandVariable> variables) {
		List<CommandVariable> result = new ArrayList<CommandVariable>();
		if (variables != null) {
			for (CommandVariable p : variables) {
				if (name.equals(p.getKey().getName())) {
					result.add(p);
				}
			}
		}
		return result;
	}
	
	private String replacePlaceholders(String param, List<String> values) {
		param = PlaceholderUtil.replace(param, values.toArray(new String[values.size()]));
		if (param.contains(PLACEHOLDER_ALL)) {
			String allValues = StringUtils.join(values.toArray(), " ");
			param = param.replace(PLACEHOLDER_ALL, allValues);
		}
		return param;
	}
	
	/**
	 * Returns the command as string
	 * @return
	 */
	public String getCommandAsString() {
		StringBuilder cmd = new StringBuilder();
		for (String c : command) {
			if (!StringUtils.isEmpty(cmd.toString())) {
				cmd.append(" ");
			}
			cmd.append(c);
		}
		return cmd.toString();
	}
	
	/**
	 * Returns the command 
	 * @return
	 */
	public List<String> getCommand() {
		return command;
	}
	
	public CommandDefinition getCommandDefinition() {
		return definition;
	}
	
	/**
	 * Returns the environment
	 * @return
	 */
	public Map<String, String> getEnvironment() {
		return environment;
	}
}
