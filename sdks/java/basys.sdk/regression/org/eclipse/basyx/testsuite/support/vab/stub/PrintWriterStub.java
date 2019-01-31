package org.eclipse.basyx.testsuite.support.vab.stub;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PrintWriterStub extends PrintWriter {
	
	
	private String acceptor;
	
	
	public PrintWriterStub(String fileName, String acceptor) throws FileNotFoundException {
		super(fileName);
		// TODO Auto-generated constructor stub
		
		this.acceptor = acceptor;
	}

	@Override
	public void write(String stream) {
		// check for
		System.out.println("Writing to output: "+stream);
		
		assertTrue(acceptor.equals(stream));
		
	}
	
	@Override
	public void flush() {
		// do nothing
		System.out.println("Flushing..");
	}
}
