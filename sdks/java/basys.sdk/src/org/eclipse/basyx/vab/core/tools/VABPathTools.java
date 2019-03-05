package org.eclipse.basyx.vab.core.tools;

/**
 * Utility functions to handle a VAB path
 * 
 * @author kuhn, espen
 * 
 */
public class VABPathTools {
	public static final String SEPERATOR = "/";

	/**
	 * Split a path into path elements e.g. /a/b/c -> [ a, b, c ]
	 */
	public static String[] splitPath(String path) {
		// Remove leading separator, otherwise /a leads to {"", "a"}
		String fixedPath = removePrefix(path, SEPERATOR);

		if (fixedPath.equals("")) {
			// without the check, [""] is returned
			return new String[] {};
		}

		String[] result = fixedPath.split(SEPERATOR);

		return result;
	}

	/**
	 * Remove the last element from the path
	 */
	public static String getParentPath(String path) {
		int lastIndex = path.lastIndexOf(SEPERATOR);
		if (lastIndex == path.length() - 1) {
			lastIndex = path.lastIndexOf(SEPERATOR, path.length() - 2);
		}
		if (lastIndex >= 0) {
			return removePrefix(path.substring(0, lastIndex), SEPERATOR);
		} else {
			return "";
		}
	}

	/**
	 * Get the last element of a path. Return null if there is no element in the
	 * path
	 */
	public static String getLastElement(String path) {
		String[] elements = splitPath(path);
		if (elements.length > 0) {
			return elements[elements.length - 1];
		}
		return "";
	}

	/**
	 * Remove prefix from beginning of path
	 */
	public static String removePrefix(String path, String prefix) {
		if (path.startsWith(prefix)) {
			return path.substring(prefix.length());
		} else {
			return path;
		}
	}

	public static String append(String path, String element) {
		if (path.lastIndexOf(SEPERATOR) == path.length() - 1) {
			return path + element;
		} else {
			return path + SEPERATOR + element;
		}
	}

	/**
	 * Build and return a path with pathElements[startIndex] as the root element
	 */
	public static String buildPath(String[] pathElements, int startIndex) {
		if (startIndex >= pathElements.length) {
			return "";
		}

		// This will store the resulting path
		StringBuilder result = new StringBuilder();

		// Build path
		for (int i = startIndex; i < pathElements.length; i++)
			result.append(pathElements[i] + SEPERATOR);

		// Remove last '/'
		result.deleteCharAt(result.length() - 1);

		// Return created path
		return result.toString();
	}

	/**
	 * Check if the path to an VAB elements leads to an operation. In this case, the
	 * element path conforms to /aas/submodels/{subModelId}/operations
	 */
	public static boolean isOperationPath(String path) {
		// Split path
		String[] pathElements = splitPath(path);

		// Look for the 'operation' element inside the path
		for (String s : pathElements) {
			if (s.equals("operations")) {
				return true;
			}
		}

		// No operation
		return false;
	}

	/**
	 * Gets the address entry of a path <br />
	 * E.g. basyx://127.0.0.1:6998//https://localhost/test/ will return
	 * basyx://127.0.0.1:6998
	 * 
	 * @param path
	 * @return
	 */
	public static String getAddressEntry(String path) {
		if (path == null || !path.contains("//")) {
			return "";
		} else {
			String splitted[] = path.split("//");
			return splitted[0] + "//" + splitted[1];
		}
	}

	/**
	 * Removes from a path the address part <br/>
	 * E.g. basyx://127.0.0.1:6998//https://localhost/test/ will return
	 * https://localhost/test/
	 * 
	 * @param path
	 * @return
	 */
	public static String removeAddressEntry(String path) {
		if (!path.contains("//")) {
			return path;
		} else {
			path = path.replaceFirst(getAddressEntry(path), "");
			if (path.startsWith("//")) {
				path = path.replaceFirst("//", "");
			}
			return path;
		}
	}

}
