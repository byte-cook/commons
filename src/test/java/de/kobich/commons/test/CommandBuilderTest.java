package de.kobich.commons.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.runtime.executor.command.CommandBuilder;
import de.kobich.commons.runtime.executor.command.CommandId;
import de.kobich.commons.runtime.executor.command.CommandVariable;

public class CommandBuilderTest {
	private static final Logger logger = Logger.getLogger(CommandBuilderTest.class);
	
	@BeforeEach
	public void init() {
		BasicConfigurator.configure();
	}

	@Test
	public void testCmdBuilder() throws Exception {
		final String PROXY_HOST = "127.0.0.1";
		
		File definitionFile = new File(CommandBuilderTest.class.getResource("/commandbuilder/cmd-definition.xml").toURI());
		CommandBuilder cb = new CommandBuilder(definitionFile);
		List<CommandVariable> params = new ArrayList<CommandVariable>();
		params.add(new CommandVariable("classpath", "*.jar"));
		params.add(new CommandVariable("classpath", definitionFile.getAbsolutePath()));
		params.add(new CommandVariable("properties", Arrays.asList("user", "test")));
		params.add(new CommandVariable("properties", Arrays.asList("pwd", "abc")));
//		params.add(new CommandParam("src", "\"MyClass.java\""));
		params.add(new CommandVariable(new CommandId("src"), Arrays.asList("\"MyClass.java\"", "\"MyClass_2.java\"")));
		List<CommandVariable> envs = new ArrayList<CommandVariable>();
		envs.add(new CommandVariable("proxy", Arrays.asList("proxy.host", PROXY_HOST)));
		cb.createCommand(params, envs);
		
		logger.info(cb.getCommandAsString());
		logger.info(cb.getEnvironment());
		assertTrue(cb.getCommandAsString().contains(definitionFile.getAbsolutePath()));
		assertTrue(cb.getEnvironment().containsValue(PROXY_HOST));
	}
}
