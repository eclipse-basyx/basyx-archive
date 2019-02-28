package org.eclipse.basyx.vab.core.tools;

/**
 * Utility functions to handle a VAB path
 * 
 * @author kuhn
 * 
 */
public class VABPathTools {

	/**
	 * Split a path into path elements
	 */
	public static String[] splitPath(String path) {
		// Split path
		String[] result = path.split("/");

		// Return path elements
		return result;
	}

	/**
	 * Remove prefix from beginning of path
	 */
	public static String removePrefix(String path, String prefix) {
		return path.substring(prefix.length());
	}

	/**
	 * Build and return a path
	 */
	public static String buildPath(String[] pathElements, int startIndex) {
		// This will store the resulting path
		StringBuilder result = new StringBuilder();

		// Build path
		for (int i = startIndex; i < pathElements.length; i++)
			result.append(pathElements[i] + "/");

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
