package org.eclipse.basyx.vab.backend.server.basyx;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.eclipse.basyx.vab.backend.server.BaSysCommunicationInterface;
import org.eclipse.basyx.vab.backend.server.utils.JSONProvider;
import org.eclipse.basyx.vab.core.IModelProvider;


/**
 * Provider class that enables access to an IModelProvider via native BaSyx
 * protocol
 * 
 * @author kuhn, pschorn
 *
 */
public class VABBaSyxTCPInterface<ModelProvider extends IModelProvider> extends Thread implements BaSysCommunicationInterface<ModelProvider> {

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
	protected JSONProvider<ModelProvider> providerBackend = null;

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
	public VABBaSyxTCPInterface(ModelProvider modelProviderBackend, Socket communicationSocket) {
		// Store reference to socket and backend
		providerBackend = new JSONProvider<ModelProvider>(modelProviderBackend);
		tcpCommSocket = communicationSocket;

		// Create input and output stream
		try {
			inputStream = new BufferedInputStream(tcpCommSocket.getInputStream());
			outputStream = new PrintWriter(tcpCommSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Get JSON Provider from backend
	 */
	@Override
	public JSONProvider<ModelProvider> getProviderBackend() {
		return providerBackend;
	}
	

	/**
	 * Get backend reference
	 */
	public ModelProvider getBackendReference() {
		return providerBackend.getBackendReference();
	}

	/**
	 * Process input frame
	 */
	protected void processInputFrame(byte[] rxFrame) throws IOException {
		// Create output streams
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
		PrintWriter output = new PrintWriter(byteArrayOutput);

		// Get command
		switch (rxFrame[0]) {

		case BASYX_GET: {
			// Get path string
			int pathLen = CoderTools.getInt32(rxFrame, 1);
			String path = new String(rxFrame, 1 + 4, pathLen);

			// Forward request to provider
			providerBackend.processBaSysGet(path, output);

			// Send response frame
			sendResponseFrame(byteArrayOutput);

			break;
		}

		case BASYX_SET: {
			// Get path string length and value
			int pathLen = CoderTools.getInt32(rxFrame, 1);
			String path = new String(rxFrame, 1 + 4, pathLen);
			// Get value string length and value
			int jsonValueLen = CoderTools.getInt32(rxFrame, 1 + 4 + pathLen);
			String jsonValue = new String(rxFrame, 1 + 4 + pathLen + 4, jsonValueLen);

			// Invoke get operation
			providerBackend.processBaSysSet(path, jsonValue, output);

			// Send response frame
			sendResponseFrame(byteArrayOutput);

			break;
		}

		case BASYX_CREATE: {
			// Get path string length and value
			int pathLen = CoderTools.getInt32(rxFrame, 1);
			String path = new String(rxFrame, 1 + 4, pathLen);
			// Get value string length and value
			int jsonValueLen = CoderTools.getInt32(rxFrame, 1 + 4 + pathLen);
			String jsonValue = new String(rxFrame, 1 + 4 + pathLen + 4, jsonValueLen);

			// Invoke get operation
			providerBackend.processBaSysCreate(path, jsonValue, output);

			// Send response frame
			sendResponseFrame(byteArrayOutput);

			break;
		}

		case BASYX_DELETE: {
			// Get path string length and value
			int pathLen = CoderTools.getInt32(rxFrame, 1);
			String path = new String(rxFrame, 1 + 4, pathLen);

			// Get value string length and value if available; default is null value  
			String jsonValue = "{\"basystype\":\"null\"}";
			try {
				int jsonValueLen = CoderTools.getInt32(rxFrame, 1 + 4 + pathLen);
				jsonValue = new String(rxFrame, 1 + 4 + pathLen + 4, jsonValueLen);

			} catch (ArrayIndexOutOfBoundsException e) {
				// pass, provide serialize null argument to processBaSysDelete to indicate that an
				// entity should be removed
			}

			// Invoke delete operation
			providerBackend.processBaSysDelete(path, jsonValue, output);

			// Send response frame
			sendResponseFrame(byteArrayOutput);

			break;
		}

		case BASYX_INVOKE: {
			// Get path string length and value
			int pathLen = CoderTools.getInt32(rxFrame, 1);
			String path = new String(rxFrame, 1 + 4, pathLen);
			// Get value string length and value
			int jsonValueLen = CoderTools.getInt32(rxFrame, 1 + 4 + pathLen);
			String jsonValue = new String(rxFrame, 1 + 4 + pathLen + 4, jsonValueLen);
			// Invoke get operation
			providerBackend.processBaSysInvoke(path, jsonValue, output);

			// Send response frame
			sendResponseFrame(byteArrayOutput);

			break;
		}

		default:
			throw new RuntimeException("Unknown BaSyx TCP command received");
		}
	}

	/**
	 * Sends a response to the client that carries the JSON response
	 * 
	 * @param byteArrayOutput
	 * @throws IOException
	 */
	private void sendResponseFrame(ByteArrayOutputStream byteArrayOutput) throws IOException {
		// - Create response frame
		byte[] encodedResult = byteArrayOutput.toByteArray();
		int resultFrameSize = encodedResult.length + 1;
		byte[] frameLength = new byte[4];
		byte[] encodedResultLength = new byte[4];
		CoderTools.setInt32(frameLength, 0, resultFrameSize + 4);
		CoderTools.setInt32(encodedResultLength, 0, encodedResult.length);
		// - Transmit response frame
		tcpCommSocket.getOutputStream().write(frameLength);
		tcpCommSocket.getOutputStream().write(0x00);
		tcpCommSocket.getOutputStream().write(encodedResultLength);
		tcpCommSocket.getOutputStream().write(encodedResult);
		byteArrayOutput.reset();
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

				// Wait for incoming TCP frame
				// - Wait for leading 4 byte header that contains frame length
				while (inputStream.available() < 4)
					sleep(1);

				// Get frame size
				inputStream.read(lengthBytes, 0, 4);
				int frameSize = CoderTools.getInt32(lengthBytes, 0);
				// Wait for frame to arrive
				while (inputStream.available() < frameSize)
					sleep(1);
				// - Receive frame
				byte[] rxFrame = new byte[frameSize];
				inputStream.read(rxFrame, 0, frameSize);

				// Process input frame
				processInputFrame(rxFrame);

			} catch (IOException | InterruptedException e) {
				// End when TCP socket is closed
				if (tcpCommSocket.isClosed())
					return;

				// output error
				e.printStackTrace();
			}
		}
	}
}
