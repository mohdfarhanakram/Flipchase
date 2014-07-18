package com.flipchase.android.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Captures URI template variable names. */
	private static final Pattern NAMES_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

	/** Replaces template variables in the URI template. */
	private static final String DEFAULT_VARIABLE_PATTERN = "(.*)";

	private final List<String> variableNames;

	private final Pattern matchPattern;

	private final String uriTemplate;


	public String getUriTemplate() {
		return uriTemplate;
	}

	public UriTemplate(String uriTemplate) {
		Parser parser = new Parser(uriTemplate);
		this.uriTemplate = uriTemplate;
		this.variableNames = parser.getVariableNames();
		this.matchPattern = parser.getMatchPattern();
	}

	public List<String> getVariableNames() {
		return this.variableNames;
	}

	public boolean matches(String uri) {
		if (uri == null) {
			return false;
		}
		Matcher matcher = this.matchPattern.matcher(uri);
		return matcher.matches();
	}

	public Map<String, String> match(String uri) {
		Map<String, String> result = new LinkedHashMap<String, String>(this.variableNames.size());
		Matcher matcher = this.matchPattern.matcher(uri);
		if (matcher.find()) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				String name = this.variableNames.get(i - 1);
				String value = matcher.group(i);
				result.put(name, value);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return this.uriTemplate;
	}


	/**
	 * Static inner class to parse URI template strings into a matching regular expression.
	 */
	private static class Parser {

		private final List<String> variableNames = new LinkedList<String>();
		private final StringBuilder patternBuilder = new StringBuilder();

		private Parser(String uriTemplate) {
			Matcher m = NAMES_PATTERN.matcher(uriTemplate);
			int end = 0;
			while (m.find()) {
				this.patternBuilder.append(quote(uriTemplate, end, m.start()));
				String match = m.group(1);
				int colonIdx = match.indexOf(':');
				if (colonIdx == -1) {
					this.patternBuilder.append(DEFAULT_VARIABLE_PATTERN);
					this.variableNames.add(match);
				}
				else {
					if (colonIdx + 1 == match.length()) {
						throw new IllegalArgumentException("No custom regular expression specified after ':' in \"" + match	+ "\"");
					}
					String variablePattern = match.substring(colonIdx + 1, match.length());
					this.patternBuilder.append('(');
					this.patternBuilder.append(variablePattern);
					this.patternBuilder.append(')');
					String variableName = match.substring(0, colonIdx);
					this.variableNames.add(variableName);
				}
				end = m.end();
			}
			this.patternBuilder.append(quote(uriTemplate, end, uriTemplate.length()));
			int lastIdx = this.patternBuilder.length() - 1;
			if (lastIdx >= 0 && this.patternBuilder.charAt(lastIdx) == '/') {
				this.patternBuilder.deleteCharAt(lastIdx);
			}
		}

		private String quote(String fullPath, int start, int end) {
			if (start == end) {
				return "";
			}
			return Pattern.quote(fullPath.substring(start, end));
		}

		private List<String> getVariableNames() {
			return Collections.unmodifiableList(this.variableNames);
		}

		private Pattern getMatchPattern() {
			return Pattern.compile(this.patternBuilder.toString());
		}
	}


}
