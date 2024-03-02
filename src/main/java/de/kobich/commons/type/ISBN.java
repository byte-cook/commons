package de.kobich.commons.type;

public class ISBN {
	private final String code;
	private String prefix;
	private String language;
	private String publisher;
	private String title;
	private String checksum;
	
	public static boolean isValid(String code) {
		String[] tokens = code.split("-");
		// ISBN-10: 3-86680-192-9
		if (tokens.length == 4) {
			code = code.replaceAll("-", "");
			int checkSum = 0;
			for (int i = 0; i < 10; ++i) {
				if (code.charAt(i) == 'X' || code.charAt(i) == 'x') {
					checkSum += 10 * (i + 1);
				}
				else {
					checkSum += (Integer.parseInt(code.substring(i, i + 1)) * (i + 1));
				}
			}
			if (checkSum % 11 == 0) {
				return true;
			}
		}
		// ISBN-13: 978-3-86680-192-9
		else if (tokens.length == 5) {
			code = code.replaceAll("-", "");
			int checkSum = 0;
			int digit = 0;
			for (int i = 0; i < 13; ++i) {
				if (code.charAt(i) == 'X' || code.charAt(i) == 'x') {
					digit = 10;
				}
				else {
					digit = Integer.parseInt(code.substring(i, i + 1));
				}
				if (i % 2 == 1) {
					digit *= 3;
				}
				checkSum += digit;
			}
			if (checkSum % 10 == 0) {
				return true;
			}
		}
		return false;
	}
	
	public ISBN(String code) throws IllegalArgumentException {
		if (!isValid(code)) {
			throw new IllegalArgumentException("Illegal ISBN: " + code);
		}
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getLanguage() {
		if (this.language == null) {
			parse();
		}
		return this.language;
	}
	
	public String getPublisher() {
		if (this.publisher == null) {
			parse();
		}
		return this.publisher;
	}
	
	public String getPrefix() {
		if (this.prefix == null) {
			parse();
		}
		return this.prefix;
	}
	
	public String getTitle() {
		if (this.title == null) {
			parse();
		}
		return this.title;
	}
	
	public String getChecksum() {
		if (this.checksum == null) {
			parse();
		}
		return this.checksum;
	}
	
	private void parse() {
		String[] tokens = code.split("-");
		// ISBN-10: 3-86680-192-9
		if (tokens.length == 4) {
			this.prefix = "978";
			this.language = tokens[0];
			this.publisher = tokens[1];
			this.title = tokens[2];
			this.checksum = tokens[3];
		}
		// ISBN-13: 978-3-86680-192-9
		else if (tokens.length == 5) {
			this.prefix = tokens[0];
			this.language = tokens[1];
			this.publisher = tokens[2];
			this.title = tokens[3];
			this.checksum = tokens[4];
		}		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ISBN other = (ISBN) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		}
		else if (!code.equals(other.code))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return code;
	}
}
