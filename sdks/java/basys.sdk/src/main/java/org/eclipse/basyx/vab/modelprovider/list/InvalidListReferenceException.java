package org.eclipse.basyx.vab.modelprovider.list;

/**
 * Exception that indicates that a list reference has been used, which is not
 * valid for the list it has been used for.
 * 
 * @author espen
 *
 */
public class InvalidListReferenceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String message = null;

	public InvalidListReferenceException(Integer reference) {
		this.message = "The list reference '" + reference + "' is invalid";
	}

	@Override
	public String getMessage() {
		return message;
	}
}
