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
}
