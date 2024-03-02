package de.kobich.commons.misc.rename.rule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.kobich.commons.misc.extract.ExtractStructureResponse;
import de.kobich.commons.misc.extract.Extractor;
import de.kobich.commons.misc.extract.IText;
import de.kobich.commons.misc.extract.StructureVariable;
import de.kobich.commons.misc.rename.IRenameable;
import de.kobich.commons.misc.rename.RenameFilterChain;



/**
 * Selects a part to rename.
 * @author ckorn
 */
public class SelectingByPatternRenameRule implements IRenameRule {
	private final Collection<StructureVariable> variables;
	private final StructureVariable targetVariable;
	private final String pattern;

	/**
	 * Constructor
	 */
	public SelectingByPatternRenameRule(Collection<StructureVariable> variables, StructureVariable targetVariable, String pattern) {
		this.variables = variables;
		this.targetVariable = targetVariable;
		this.pattern = pattern;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();
		
		Map<StructureVariable, String> variable2Name = null;
		Set<IText> texts = new HashSet<IText>();
		texts.add(new TextAdapter(name));
		ExtractStructureResponse response = Extractor.extract(texts, pattern, variables);
		Map<IText, Map<StructureVariable, String>> succeededTexts = response.getSucceededTexts();
		if (succeededTexts.isEmpty()) {
			// do not propagate to filter chain
			return;
		}
		for (IText text : succeededTexts.keySet()) {
			// assert text.getText().equals(name);
			variable2Name = succeededTexts.get(text);
			for (StructureVariable variable : variable2Name.keySet()) {
				if (variable.getName().equalsIgnoreCase(targetVariable.getName())) {
					name = variable2Name.get(variable);
				}
			}
		}
		
		// rename part
		DummyRenameable dummy = new DummyRenameable(renameable);
		dummy.setName(name);
		chain.doFilter(dummy);
		
		String result = pattern;
		for (StructureVariable variable : variable2Name.keySet()) {
			if (variable.getName().equalsIgnoreCase(targetVariable.getName())) {
				result = result.replace(variable.getName(), dummy.getName());
			}
			else {
				result = result.replace(variable.getName(), variable2Name.get(variable));
			}
		}
		
		renameable.setName(result);
	}
	
	private class TextAdapter implements IText {
		private String text;
		
		public TextAdapter(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}
	}
}
