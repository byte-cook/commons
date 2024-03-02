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
 * Swaps names.
 * @author ckorn
 */
public class SwappingRenameRule implements IRenameRule {
	private final Collection<StructureVariable> variables;
	private final String sourcePattern;
	private final String targetPattern;
	
	/**
	 * Constructor
	 */
	public SwappingRenameRule(Collection<StructureVariable> variables, String sourcePattern, String targetPattern) {
		this.variables = variables;
		this.sourcePattern = sourcePattern;
		this.targetPattern = targetPattern;
	}

	@Override
	public void rename(IRenameable renameable, RenameFilterChain chain, boolean categoryChanged) {
		String name = renameable.getName();
		
		Set<IText> texts = new HashSet<IText>();
		texts.add(new TextAdapter(name));
		ExtractStructureResponse response = Extractor.extract(texts, sourcePattern, variables);
		
		Map<IText, Map<StructureVariable, String>> succeededTexts = response.getSucceededTexts();
		for (IText text : succeededTexts.keySet()) {
			// assert text.getText().equals(name);
			Map<StructureVariable, String> variable2Name = succeededTexts.get(text);
			name = targetPattern;
			for (StructureVariable variable : variable2Name.keySet()) {
				name = name.replace(variable.getName(), variable2Name.get(variable));
			}
		}
		
		renameable.setName(name);
		chain.doFilter(renameable);
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
