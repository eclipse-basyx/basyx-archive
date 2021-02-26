/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.vab.exception.provider;

/**
 * Used to indicate a general exception in a ModelProvider
 * 
 * @author conradi
 *
 */
public class ProviderException extends RuntimeException {

	
	/**
	 * Version information for serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Store message
	 */
	protected String message = null;
	
	
	/**
	 * Constructor
	 */
	public ProviderException(String msg) {
		// Store message
		message = msg;
	}
	
		
	public ProviderException(Exception e) {
		super(e);
	}


	public ProviderException(String message, Throwable cause) {
		super(cause);
		this.message = message;
	}


	/**
	 * Return detailed message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
