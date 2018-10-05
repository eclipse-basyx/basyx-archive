package org.eclipse.basyx.onem2m.clients;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig.Protocol;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;
import org.eclipse.basyx.onem2m.util.OneM2MResourceUtil;
import org.eclipse.basyx.onem2m.xml.protocols.Cb;
import org.eclipse.basyx.onem2m.xml.protocols.PrimitiveContent;
import org.eclipse.basyx.onem2m.xml.protocols.Resource;
import org.eclipse.basyx.onem2m.xml.protocols.Rqp;
import org.eclipse.basyx.onem2m.xml.protocols.Rsp;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.FutureResponseListener;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OneM2MHttpClient implements IOneM2MClient {

	public static final int CSE_RETRY_MS = 5000;
	
	protected HttpClient httpClient = new HttpClient();
	protected OneM2MHttpClientConfig config;
	protected int rqiCounter = 0; 
	protected Cb cseBase;
	
	public OneM2MHttpClient(OneM2MHttpClientConfig _config) {
		this.config = _config;				
	}
	
	@Override
	public void start() throws Exception {
		this.start(true);
	}

	/**
	 * Starts the client and actively waits (blocked call) until the CSE is available.
	 */
	@Override
	public void start(boolean waitForCSE) throws Exception {
		this.httpClient.start();
		if (waitForCSE) {
			this.waitForCSE();
		}
	}

	@Override
	public void stop() throws Exception {
		this.httpClient.stop();
	}

	@Override
	public Rqp createDefaultRequest(String path, boolean hierarchical) {
		Rqp rqp = new Rqp();
		String to = (this.config.protocol.equals(Protocol.HTTP) ? "http://" : "https://")
						+ this.config.host + ":" + this.config.port
						+ (hierarchical ? "/"+ this.cseBase.getRn() + "/" : "/")  
						+ path;
		
		rqp.setTo(to);
		rqp.setFr(this.config.from);
		rqp.setRqi(Integer.toString(this.rqiCounter++));
				
		return rqp;
	}

	@Override
	public Rsp send(Rqp rqp) throws InterruptedException, TimeoutException, ExecutionException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		String mime = "application/json";
		String url = rqp.getTo();
		
		Request request = httpClient.newRequest(url);
		request.header("Accept", mime);
		
		if(rqp.getOp().equals(BigInteger.valueOf(1)) && rqp.getTy() == null) {
			new IllegalArgumentException("Ty must be set if op = 1");
		}
		request.header("Content-Type", rqp.getOp().equals(BigInteger.valueOf(1)) ? (mime + ";ty=" + rqp.getTy().toString()) : mime);		
		request.header("X-M2M-Origin", rqp.getFr());
		request.header("X-M2M-Ri", rqp.getRqi());
				
	    if (rqp.getDrt() != null) {
	        request.param("drt", rqp.getDrt().toString());
	    }
	    if (rqp.getFc() != null) {
	        // filter criteria
	        if (rqp.getFc().getFu() != null) {
	        	request.param("fu", rqp.getFc().getFu().toString());
	        }
	        if (rqp.getFc().getLvl() != null) {
	        	request.param("lvl", rqp.getFc().getLvl().toString());
	        }
	        for (String lbl : rqp.getFc().getLbl()) {
	        	request.param("lbl", lbl);
	        }
	    }
		
	    if (rqp.getRcn() != null) {
	        request.param("rcn", rqp.getRcn().toString());
	    }
	    		
		switch (rqp.getOp().intValue()) {
		case 1: { // Create
			request.method(HttpMethod.POST);
			request.content(new StringContentProvider(this.createPayload(rqp.getPc())));
			break;
			}
			
		case 2: { // Retrieve
			request.method(HttpMethod.GET);			
			break;
			}

		case 3: { // Update
			throw new NotImplementedException("Update not implemented yet");
			//break;
			}

		case 4: { // Delete
			request.method(HttpMethod.DELETE);						
			break;
			}

		case 5: { // Notify
			// not implemented
			throw new NotImplementedException("Notify not implemented yet");
			//break;
			}
		
		default:
			throw new IllegalArgumentException("Unknown operation type");
		}
		
		
		
		FutureResponseListener listener = new FutureResponseListener(request, 32 * 1024 * 1024); // 32MB Limit
		request.send(listener);
		ContentResponse response = listener.get(5, TimeUnit.SECONDS);
		//ContentResponse response = request.send(); old code (default limit 2MB)		
		Rsp rsp = this.createRsp(response);		
		return rsp;
	}
	
	protected Rsp createRsp(ContentResponse response) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Rsp rsp = new Rsp();
		String content = response.getContentAsString();
		if (content.length() > 0 && 
				(response.getStatus() >= 200 &&  response.getStatus() < 300
				)) { 
			JsonObject json = new JsonParser().parse(content).getAsJsonObject();
			Set<Entry<String, JsonElement>> entrySet = json.entrySet();
	        for(Map.Entry<String,JsonElement> entry : entrySet){

	        	if (OneM2MResourceUtil.isResource(entry.getKey())) {
		        	JsonObject jsonObj = entry.getValue().getAsJsonObject();
		        	
		        	String className = entry.getKey().substring("m2m:".length());
		        	String fullClassName = "org.eclipse.basyx.onem2m.xml.protocols." + className.substring(0, 1).toUpperCase() + className.substring(1);
		        	Class<?> c = Class.forName(fullClassName);
		        	
		        	Object o = new Gson().fromJson(jsonObj, c);
		        	
		        	for (Entry<String, JsonElement> childEntry : jsonObj.entrySet()) {
		        		if (childEntry.getKey().startsWith("m2m:")) {
		        			// child found
				        	String childClassName = childEntry.getKey().substring("m2m:".length());
				        	String childFullClassName = "org.eclipse.basyx.onem2m.xml.protocols." + childClassName.substring(0, 1).toUpperCase() + childClassName.substring(1);
				        	Class<?> childClass = Class.forName(childFullClassName);
				        	if (childEntry.getValue() instanceof JsonObject) {
				        		Object child = new Gson().fromJson(childEntry.getValue(), childClass);
				        		OneM2MResourceUtil.addChildToParent((Resource) o, (Resource) child);
				        	} else if (childEntry.getValue() instanceof JsonArray) {
				        		JsonArray arr = (JsonArray) childEntry.getValue();
				        		for (JsonElement element : arr) {
					        		Object child = new Gson().fromJson(element, childClass);
					        		OneM2MResourceUtil.addChildToParent((Resource) o, (Resource) child);				        			
				        		}
				        	}
		        		}
		        	}
		        	
		        	rsp.setPc(new PrimitiveContent());
		        	rsp.getPc().getAnyOrAny().add(o);
	        	} else if (OneM2MResourceUtil.isOtherPrimitiveContent(entry.getKey())) {
		        	Object o = OneM2MResourceUtil.createOtherPrimitiveContent(entry.getKey(), entry.getValue());
		        	rsp.setPc(new PrimitiveContent());
		        	rsp.getPc().getAnyOrAny().add(o);
	        	}
	        }
		}
        rsp.setFr(response.getHeaders().get("x-m2m-origin"));
        rsp.setRqi(response.getHeaders().get("x-m2m-ri"));
        rsp.setRsc(new BigInteger(response.getHeaders().get("x-m2m-rsc")));
		return rsp;
	}
	
	private String createPayload(PrimitiveContent pc) {
		if (pc.getAnyOrAny().size() == 1) {
			Resource resource = (Resource) pc.getAnyOrAny().iterator().next();
	        String payload = "{\"" + OneM2MResourceUtil.getXsdTypeNameFromResource(resource) + "\":"+ new Gson().toJson(resource) + "}";
	        return payload;			
		}
		throw new NotImplementedException("Create payload from array not implemented yet.");
	}
	
	private void waitForCSE(){
		while(this.cseBase == null){
			try{
				this.cseBase = (new OneM2MResourceManager(this).retrieveCSEBase()).getResource();
			}catch (Exception e){
				System.out.println("Cannot connect to CSE, retry in " + (OneM2MHttpClient.CSE_RETRY_MS/1000) + "s");
				try {
					Thread.sleep(OneM2MHttpClient.CSE_RETRY_MS);
				} catch (InterruptedException ie) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public OneM2MHttpClientConfig getConfig() {
		return this.config;
	}
	
}