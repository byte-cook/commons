package de.kobich.commons.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kobich.commons.misc.validate.IValidatable;
import de.kobich.commons.misc.validate.ValidateMessage;
import de.kobich.commons.misc.validate.Validator;
import de.kobich.commons.misc.validate.rule.DigitRule;
import de.kobich.commons.misc.validate.rule.IValidationRule;
import de.kobich.commons.misc.validate.rule.LowerCaseRule;
import de.kobich.commons.ui.callback.ICallback;

public class ValidatorTest {
	@BeforeEach
	public void setUp() {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
	}
	
	@Test
	public void testValidator() {
		List<IValidatable> validatables = new ArrayList<IValidatable>();
		validatables.add(new V("1"));
		validatables.add(new V("2"));
		validatables.add(new V("drei"));
		validatables.add(new V("VIER"));
		
		List<IValidationRule> rules = new ArrayList<IValidationRule>();
		rules.add(new LowerCaseRule());
		rules.add(new DigitRule());
		ICallback<ValidateMessage, Void> callback = new ICallback<ValidateMessage, Void>() {

			@Override
			public Void execute(ValidateMessage param) {
				System.out.println(param.getMessageType() + ": " + param.getMessage());
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}
			
		};
		Validator.validate(validatables, rules, callback);
	}
	
	class V implements IValidatable {
		private final String name;
		
		public V(String name) {
			this.name = name;
		}

		@Override
		public String getId() {
			return name;
		}

		@Override
		public String getText() {
			return name;
		}

		@Override
		public String getCategory() {
			return name;
		}
		 
	}
}
