package de.kobich.commons.misc.tokenizer;

public class TokenType {
	private final String name;
	private final String category;

	public TokenType(String type) {
		this(type, null);
	}

	public TokenType(String type, String category) {
		this.name = type;
		this.category = category;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return category
	 */
	public String getCategory() {
		return category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenType other = (TokenType) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		}
		else if (!category.equals(other.category))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

}
