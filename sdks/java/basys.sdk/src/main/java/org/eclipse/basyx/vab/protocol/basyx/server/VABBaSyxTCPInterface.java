package org.eclipse.basyx.vab.protocol.basyx.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.eclipse.basyx.vab.coder.json.provider.JSONProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.basyx.CoderTools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Provider class that enables access to an IModelProvider via native BaSyx
 * protocol
 * 
 * @author kuhn, pschorn
 *
 */
public class VABBaSyxTCPInterface<ModelProvider extends IModelProvider> extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(VABBaSyxTCPInterface.class);

	
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
	 * BaSyx result 'OK' : 0x00
	 */
	public static final byte BASYX_RESULT_OK = 0x00;

	
	/**
	 * Reference to IModelProvider backend
	 */
	protected JSONProvider<ModelProvider> providerBackend = null;

	
	/**
	 * Socket communication channel
	 */
	protected SocketChannel commChannel = null;


	
	
	/**
	 * Constructor that accepts an already created server socket channel
	 */
	public VABBaSyxTCPInterface(ModelProvider modelProviderBackend, SocketChannel channel) {
		// Store reference to channel and backend
		providerBackend   = new JSONProvider<ModelProvider>(modelProviderBackend);
		commChannel = channel;
	}
	
	/**
	 * Process input frame
	 */
	public void processInputFrame(byte[] rxFrame) throws IOException {
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

			// System.out.println("Processed GET:"+path);

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
			String jsonValue = "";
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
		// Create response frame with positive response
		sendResponseFrame(byteArrayOutput, BASYX_RESULT_OK);
	}

	
	/**
	 * Sends a response to the client that carries the JSON response
	 * 
	 * @param byteArrayOutput
	 * @throws IOException
	 */
	private void sendResponseFrame(ByteArrayOutputStream byteArrayOutput, int result) throws IOException {
		// Create response frame
		byte[] encodedResult = byteArrayOutput.toByteArray();
		int resultFrameSize = encodedResult.length + 1;
		byte[] frameLength = new byte[4];
		byte[] encodedResultLength = new byte[4];
		CoderTools.setInt32(frameLength, 0, resultFrameSize + 4);
		CoderTools.setInt32(encodedResultLength, 0, encodedResult.length);
		
		// Place response frame in buffer
		ByteBuffer buffer = ByteBuffer.allocate(resultFrameSize + 4 + 4);
		buffer.put(frameLength);
		buffer.put((byte) result);
		buffer.put(encodedResultLength);
		buffer.put(encodedResult);
		buffer.flip();
		
		// Transmit response frame
		commChannel.write(buffer);
		
		// System.out.println("TXRESP:"+cnt); // result of commChannel.write(buffer)

		// Reset output stream
		byteArrayOutput.reset();
	}

	
	/**
	 * Read a number of bytes
	 */
	protected void readBytes(ByteBuffer bytes, int expectedBytes) {
		// Exception handling
		try {
			// System.out.println("Reading:"+expectedBytes);
			// Read bytes until buffer is full
			while (bytes.position() < expectedBytes) {
				// System.out.println("Pos:" + bytes.position());
				commChannel.read(bytes);
			}
			// System.out.println("Read:"+expectedBytes);
		} catch (IOException e) {
			// Output exception
			logger.error("Exception in readBytes", e);
		}
	}

	
	/**
	 * Thread main function
	 */
	@Override
	public void run() {
		// Run forever (until socket is closed)
		while (true) {
			// Process inputs
			try {
				// Read response
				// - Wait for leading 4 byte header that contains frame length
				ByteBuffer rxBuffer1 = ByteBuffer.allocate(4);
				readBytes(rxBuffer1, 4);
				int frameSize = CoderTools.getInt32(rxBuffer1.array(), 0);

				// Wait for frame to arrive
				ByteBuffer rxBuffer2 = ByteBuffer.allocate(frameSize);
				readBytes(rxBuffer2, frameSize);
				byte[] rxFrame = rxBuffer2.array();

				// Process input frame
				processInputFrame(rxFrame);
			} catch (IOException e) {
				// End when TCP socket is closed
				if (!commChannel.isConnected()) return;

				// Output error
				logger.error("Exception in run", e);
			}
		}
	}
}
