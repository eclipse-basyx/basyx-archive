package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This Class is a List, which holds LangString Objects <br/>
 * It is used to hold a text in multiple languages
 * 
 * @author conradi
 *
 */
public class LangStrings extends HashSet<HashMap<String, Object>> {
	private static final long serialVersionUID = 1L;

	private static final String LANGUAGE = "language";
	private static final String TEXT = "text";
	
	public LangStrings() {}
	
	public LangStrings(String language, String text) {
		add(language, text);
	}
	
	public LangStrings(HashSet<HashMap<String, Object>> set) {
		this.addAll(set);
	}

	/**
	 * 
	 * @param language
	 * @return The String for the specified language or <br/>
	 * an empty String if no matching LangString is found
	 */
	public String get(String language) {
		for (HashMap<String, Object> langString : this) {
			if(langString.get(LANGUAGE).toString().equalsIgnoreCase(language)) {
				return (String) langString.get(TEXT);
			}
		}
		return "";
	}
	
	/**
	 * 
	 * @return A Set of Strings containing all languages of this LangStrings Object
	 */
	public Set<String> getLanguages() {
		HashSet<String> languageSet = new HashSet<>();
		for (HashMap<String, Object> langString : this) {
			languageSet.add((String) langString.get(LANGUAGE));
		}
		return languageSet;
	}
	
	/**
	 * 
	 * @param lang The language the new LangString is in.
	 * @param text The content of the LangString.
	 */
	public void add(String lang, String text) {
		HashMap<String, Object> langString = new HashMap<>();
		langString.put(LANGUAGE, lang);
		langString.put(TEXT, text);
		add(langString);
	}
}