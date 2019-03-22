package org.eclipse.basyx.onem2m.manager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.basyx.onem2m.util.OneM2MResourceUtil;
import org.eclipse.basyx.onem2m.xml.protocols.Notification;
import org.eclipse.basyx.onem2m.xml.protocols.Sub;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class OneM2MSubscriptionHandler implements Handler {

	class SubscriptionEntry {
		Sub sub;
		IOneM2MNotificationHandler handler;
	}

	String id;
	String host;
	int port;
	Server server;
	OneM2MResourceManager resourceManager;

	Map<String, SubscriptionEntry> subs = new HashMap<>(); // sub ri => entry

	public OneM2MSubscriptionHandler(String handlerid, String host, int port, OneM2MResourceManager resourceManager) {
		this.id = handlerid;
		this.host = host;
		this.port = port;

		QueuedThreadPool threadPool = new QueuedThreadPool(10, 5);
		this.server = new Server(threadPool);

		ServerConnector http = new ServerConnector(this.server, new HttpConnectionFactory());
		http.setPort(port);
		this.server.addConnector(http);

		this.server.setHandler(this);
		this.resourceManager = resourceManager;
	}

	public String getMyURL() {
		return "http://" + this.host + ":" + this.port;
	}

	public String getMyLabel() {
		return "sub-handler:" + this.id;
	}

	public void clearAllSubscriptions() throws Exception {
		DataResult<List<String>> dr = new OneM2MResourceExplorer(this.resourceManager.getClient())
				.findResourcesWithLabels01("", new String[] { this.getMyLabel() }, true);
		for (String ur : dr.data) {
			this.resourceManager.deleteResource(ur, false);
		}
		this.subs.clear();
	}

	public String addHandledSubscription(String parentRi, IOneM2MNotificationHandler notificationHandler,
			boolean hierarchical) throws Exception {
		String[] urls = { this.getMyURL() };

		Sub sub = new Sub();
		sub.getLbl().add(this.getMyLabel());
		sub.setNct(BigInteger.valueOf(1)); // default
		sub.getNu().addAll(Arrays.asList(urls));

		ResourceResult<Sub> rr = this.resourceManager.createSubscription(parentRi, sub, hierarchical);

		if (rr.getResponse().getRsc().equals(BigInteger.valueOf(2001))) {
			SubscriptionEntry oe = new SubscriptionEntry();
			oe.sub = rr.getResource();
			oe.handler = notificationHandler;
			String subri = rr.getResource().getRi();
			this.subs.put(subri, oe);
			return subri;
		} else {
			return null;
		}
	}

	@Override
	public void start() throws Exception {
		this.server.start();
	}

	@Override
	public void stop() throws Exception {
		this.server.stop();
	}

	@Override
	public boolean isRunning() {
		return this.server.isRunning();
	}

	@Override
	public boolean isStarted() {
		return this.server.isStarted();
	}

	@Override
	public boolean isStarting() {
		return this.server.isStarting();
	}

	@Override
	public boolean isStopping() {
		return this.server.isStopping();
	}

	@Override
	public boolean isStopped() {
		return this.server.isStopped();
	}

	@Override
	public boolean isFailed() {
		return this.server.isFailed();
	}

	@Override
	public void addLifeCycleListener(Listener listener) {
		throw new NotImplementedException("addLifeCycleListener");
	}

	@Override
	public void removeLifeCycleListener(Listener listener) {
		throw new NotImplementedException("removeLifeCycleListener");
	}

	@Override
	public void setServer(Server server) {
		this.server = server;
	}

	@Override
	public Server getServer() {
		return this.server;
	}

	@Override
	public void destroy() {
		throw new NotImplementedException("destroy");
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String content = IOUtils.toString(baseRequest.getReader());
		if (content.isEmpty()) {
			return;
		}

		Notification n = (Notification) OneM2MResourceUtil.jsonToObject(content);

		String subri = n.getSur();
		if (subri != null) {
			IOneM2MNotificationHandler nHandler = this.subs.get(subri).handler;
			Sub sub = this.subs.get(subri).sub;
			nHandler.incomingNotification(this, sub, subri, n);
		}

		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);

	}
}
