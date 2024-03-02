package de.kobich.commons.runtime.executor.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandVariable {
	private final ICommandId key;
	private final List<String> values;
	
	public CommandVariable(String keyName) {
		this(new CommandId(keyName), new ArrayList<String>());
	}
	
	public CommandVariable(String keyName, String value) {
		this(new CommandId(keyName), Collections.singletonList(value));
	}
	
	public CommandVariable(String keyName, List<String> values) {
		this(new CommandId(keyName), values);
	}
	public CommandVariable(ICommandId key, List<String> values) {
		this.key = key;
		this.values = Collections.unmodifiableList(values);
	}

	public ICommandId getKey() {
		return key;
	}

	public List<String> getValues() {
		return values;
	}
}
