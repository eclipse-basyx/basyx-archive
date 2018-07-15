package org.eclipse.basyx.aas.backend.modelprovider.basyx;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.modelprovider.JSONProvider;




/**
 * Provider class that enables access to an IModelProvider via native BaSyx protocol 
 * 
 * @author kuhn
 *
 */
public class BaSyxTCPProvider<T extends IModelProvider> extends Thread {

	
	/**
	 * BaSyx get command
	 */
	public static final byte BASYX_GET = 0x01;

	/**
	 * BaSyx set command
	 */
	public static final byte BASYX_SET = 0x02;

	/**
	 * BaSyx create command
	 */
	public static final byte BASYX_CREATE = 0x03;

	/**
	 * BaSyx delete command
	 */
	public static final byte BASYX_DELETE = 0x04;

	/**
	 * BaSyx invoke command
	 */
	public static final byte BASYX_INVOKE = 0x05;

	
	
	
	
	
	/**
	 * Reference to IModelProvider backend
	 */
	protected JSONProvider<T> providerBackend = null;
	
	
	/**
	 * TCP communication socket
	 */
	protected Socket tcpCommSocket = null;
	
	
	/**
	 * TCP input stream
	 */
	protected BufferedInputStream inputStream = null;
	
	
	/**
	 * TCP output stream
	 */
	protected PrintWriter outputStream = null;
	
	
	
	
	/**
	 * Constructor
	 */
	public BaSyxTCPProvider(T modelProviderBackend, Socket communicationSocket) {
		// Store reference to socket and backend
		providerBackend = new JSONProvider<T>(modelProviderBackend);
		tcpCommSocket   = communicationSocket; 
		
		// Create input and output stream
		try {
			inputStream  = new BufferedInputStream(tcpCommSocket.getInputStream());
			outputStream = new PrintWriter(tcpCommSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Get backend reference
	 */
	public T getBackendReference() {
		return providerBackend.getBackendReference();
	}
	
	
	/**
	 * Process input frame
	 */
	protected void processInputFrame(byte[] rxFrame) throws IOException {
		// Create output streams
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
		PrintWriter           output          = new PrintWriter(byteArrayOutput);
		
		// Get command
		switch(rxFrame[0]) {
			
			case BASYX_GET: {
					// Get path string
					int    pathLen = CoderTools.getInt32(rxFrame, 1);
					String path    = new String(rxFrame, 1+4, pathLen);
					// Invoke get operation
					providerBackend.processBaSysGet(path, output);
					// - Create response frame
					byte[] encodedResult       = byteArrayOutput.toByteArray();
					int    resultFrameSize     = encodedResult.length+1;
					byte[] frameLength         = new byte[4];
					byte[] encodedResultLength = new byte[4];
					CoderTools.setInt32(frameLength, 0, resultFrameSize+4);
					CoderTools.setInt32(encodedResultLength, 0, encodedResult.length);
					// - Transmit response frame				
					tcpCommSocket.getOutputStream().write(frameLength);
					tcpCommSocket.getOutputStream().write(0x00);
					tcpCommSocket.getOutputStream().write(encodedResultLength);
					tcpCommSocket.getOutputStream().write(encodedResult);
					byteArrayOutput.reset();
					break;
				}

			case BASYX_SET: {
					// Get path string length and value
					int    pathLen   = CoderTools.getInt32(rxFrame, 1);
					String path      = new String(rxFrame, 1+4, pathLen);
					// Get value string length and value
					int    jsonValueLen = CoderTools.getInt32(rxFrame, 1+4+pathLen);
					String jsonValue    = new String(rxFrame, 1+4+pathLen+4, jsonValueLen);
					// Invoke get operation
					providerBackend.processBaSysSet(path, jsonValue);
					// - Create response frame
					byte[] frameLength     = new byte[4];
					CoderTools.setInt32(frameLength, 0, 1);
					// - Transmit response frame				
					tcpCommSocket.getOutputStream().write(frameLength);
					tcpCommSocket.getOutputStream().write(0x00);
					break;
				}

			case BASYX_CREATE: {
				// Get path string length and value
				int    pathLen   = CoderTools.getInt32(rxFrame, 1);
				String path      = new String(rxFrame, 1+4, pathLen);
				// Get value string length and value
				int    jsonValueLen = CoderTools.getInt32(rxFrame, 1+4+pathLen);
				String jsonValue    = new String(rxFrame, 1+4+pathLen+4, jsonValueLen);
				// Invoke get operation
				providerBackend.processBaSysCreate(path, jsonValue);
				// - Create response frame
				byte[] frameLength     = new byte[4];
				CoderTools.setInt32(frameLength, 0, 1);
				// - Transmit response frame				
				tcpCommSocket.getOutputStream().write(frameLength);
				tcpCommSocket.getOutputStream().write(0x00);
				break;
			}

			case BASYX_DELETE: {
				// Get path string length and value
				int    pathLen   = CoderTools.getInt32(rxFrame, 1);
				String path      = new String(rxFrame, 1+4, pathLen);
				// Get value string length and value
				int    jsonValueLen = CoderTools.getInt32(rxFrame, 1+4+pathLen);
				String jsonValue    = new String(rxFrame, 1+4+pathLen+4, jsonValueLen);
				// Invoke get operation
				providerBackend.processBaSysDelete(path, jsonValue);
				// - Create response frame
				byte[] frameLength     = new byte[4];
				CoderTools.setInt32(frameLength, 0, 1);
				// - Transmit response frame				
				tcpCommSocket.getOutputStream().write(frameLength);
				tcpCommSocket.getOutputStream().write(0x00);
				break;
			}

			case BASYX_INVOKE: {
				// Get path string length and value
				int    pathLen   = CoderTools.getInt32(rxFrame, 1);
				String path      = new String(rxFrame, 1+4, pathLen);
				// Get value string length and value
				int    jsonValueLen = CoderTools.getInt32(rxFrame, 1+4+pathLen);
				String jsonValue    = new String(rxFrame, 1+4+pathLen+4, jsonValueLen);
				System.out.println("Invoking:"+path+"---"+jsonValue+"---");
				// Invoke get operation
				providerBackend.processBaSysInvoke(path, jsonValue, output);
				// - Create response frame
				byte[] encodedResult       = byteArrayOutput.toByteArray();
				int    resultFrameSize     = encodedResult.length+1;
				byte[] frameLength         = new byte[4];
				byte[] encodedResultLength = new byte[4];
				CoderTools.setInt32(frameLength, 0, resultFrameSize+4);
				CoderTools.setInt32(encodedResultLength, 0, encodedResult.length);
				// - Transmit response frame				
				tcpCommSocket.getOutputStream().write(frameLength);
				tcpCommSocket.getOutputStream().write(0x00);
				tcpCommSocket.getOutputStream().write(encodedResultLength);
				tcpCommSocket.getOutputStream().write(encodedResult);
				System.out.println("Result:"+path+"---"+encodedResult.length+"---");
				for (int i=0; i<encodedResult.length; i++) System.out.print(""+((char) (encodedResult[i] & 0xFF))); System.out.println("");
				byteArrayOutput.reset();
				break;
			}

			default:
				throw new RuntimeException("Unknown BaSyx TCP command received");
		}
	}
	
	
	/**
	 * Thread main function
	 */
	@Override
	public void run() {
		// Run forever (until socket is closed)
		while (true) {
			// Received data
			// - 4 Bytes containing frame length
			byte[] lengthBytes = new byte[4];

			// Process inputs
			try {
				System.out.println(">> Wait RX");

				// Wait for incoming TCP frame
				// - Wait for leading 4 byte header that contains frame length
				while (inputStream.available() < 4) sleep(1);

				System.out.println(">> RX");

				// Get frame size
				inputStream.read(lengthBytes, 0, 4);
				int frameSize = CoderTools.getInt32(lengthBytes, 0);

				System.out.println(">> Wait Frame");
				// Wait for frame to arrive
				while (inputStream.available() < frameSize) sleep(1);
				// - Receive frame
				byte[] rxFrame = new byte[frameSize];
				System.out.println(">> RX Frame "+frameSize);
				inputStream.read(rxFrame, 0, frameSize);

				// Process input frame
				processInputFrame(rxFrame);
			} catch (IOException | InterruptedException e) {
				// End when TCP socket is closed
				if (tcpCommSocket.isClosed()) return;

				// output error
				e.printStackTrace();
			}
		}
	}
}

