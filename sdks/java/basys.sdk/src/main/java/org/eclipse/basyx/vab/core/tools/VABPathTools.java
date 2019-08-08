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
		// Return null result for null argument
		if (path == null) {
			return null;
		}

		// includes null-values, "" and "/";
		if (VABPathTools.isEmptyPath(path)) {
			return new String[] {};
		}

		// Remove leading separator, otherwise /a leads to {"", "a"}
		String fixedPath = removePrefix(path, SEPERATOR);

		return fixedPath.split(SEPERATOR);
	}

	/**
	 * Remove the last element from the path
	 */
	public static String getParentPath(String path) {
		// Return null result for null argument
		if (path == null) {
			return null;
		}

		if (isEmptyPath(path)) {
			return "";
		}
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
	 * Get the last element of a path. Return "" if there is no element in the path
	 */
	public static String getLastElement(String path) {
		// Return null result for null argument
		if (path == null) {
			return null;
		}

		String[] elements = splitPath(path);
		if (elements.length > 0) {
			return elements[elements.length - 1];
		} else {
			return "";
		}
	}

	/**
	 * Remove prefix from beginning of path
	 */
	public static String removePrefix(String path, String prefix) {
		// Return null result for null argument
		if (path == null) {
			return null;
		}

		if (VABPathTools.isEmptyPath(path)) {
			// same result as for any other "empty" path, like "" and "/"
			return "";
		}
		if (path.startsWith(prefix)) {
			return path.substring(prefix.length());
		} else {
			return path;
		}
	}

	public static String append(String path, String element) {
		// Return null result for null argument
		if (path == null || element == null) {
			return null;
		}

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
		// Return null result for null argument
		if (pathElements == null) {
			return null;
		}

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
		// null-Paths are no operation paths
		if (path == null) {
			return false;
		}

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
	 * Check, if the path does not contain any elements.
	 */
	public static boolean isEmptyPath(String path) {
		return path.equals("") || path.equals("/");
	}

	/**
	 * Gets the address entry of a path <br />
	 * E.g. basyx://127.0.0.1:6998//https://localhost/test/ will return
	 * basyx://127.0.0.1:6998
	 */
	public static String getAddressEntry(String path) {
		// Return null result for null argument
		if (path == null) {
			return null;
		}

		if (isEmptyPath(path) || !path.contains("//")) {
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
	 */
	public static String removeAddressEntry(String path) {
		// Return null result for null argument
		if (path == null) {
			return null;
		}

		if (isEmptyPath(path)) {
			return "";
		} else if (!path.contains("//")) {
			return path;
		} else {
			path = path.replaceFirst(getAddressEntry(path), "");
			if (path.startsWith("//")) {
				path = path.replaceFirst("//", "");
			}
			return path;
		}
	}

	/**
	 * Concatenate two paths
	 */
	public static String concatenatePaths(String... paths) {
		// Return null result for null argument
		if (paths == null || paths.length == 0) {
			return null;
		}

		// Store result
		StringBuffer result = new StringBuffer();

		// Flag that indicates whether processed path segment is first segment
		boolean isFirst = true;

		// Process all path segments
		for (String pathSegment : paths) {
			// Return empty result, if any element is null
			if (pathSegment == null) {
				return null;
			}

			// Remove leading and trailing "/" from pathsegment
			while (pathSegment.endsWith("/"))
				pathSegment = pathSegment.substring(0, pathSegment.length() - 1);
			while (pathSegment.startsWith("/"))
				pathSegment = pathSegment.substring(1);

			// Add path to result; if its first segment, do not split with "'"
			if (!isFirst)
				result.append("/");
			else
				isFirst = false;
			result.append(pathSegment);
		}

		// Return combined path
		return result.toString();
	}
}
