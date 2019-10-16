package org.eclipse.basyx.testsuite.regression.vab.coder.json;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * PrintWriter stub that simulates response stream
 * @author pschorn
 *
 */
public class PrintWriterStub extends PrintWriter {
	
	
	private String acceptor;
	private String result;
	
	
	public PrintWriterStub(String fileName, String acceptor) throws FileNotFoundException {
		super(fileName);
		// TODO Auto-generated constructor stub
		
		this.acceptor = acceptor;
	}

	@Override
	public void write(String stream) {
		// check for
		System.out.println("Writing to output: "+stream);
		
		if (!acceptor.equals("ignore")) {
			assertTrue(acceptor.equals(stream));
		}
		
		result = stream;
	}
	
	@Override
	public void flush() {
		// do nothing
		System.out.println("Flushing..");
	}
	
	public String getResult() {
		return result;
	}
}
