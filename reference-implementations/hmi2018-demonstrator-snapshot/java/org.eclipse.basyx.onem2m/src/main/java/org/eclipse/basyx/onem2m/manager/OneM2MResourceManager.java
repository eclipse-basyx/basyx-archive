package org.eclipse.basyx.onem2m.manager;

import java.math.BigInteger;
import java.util.Arrays;

import org.eclipse.basyx.onem2m.clients.IOneM2MClient;
import org.eclipse.basyx.onem2m.util.OneM2MResourceUtil;
import org.eclipse.basyx.onem2m.xml.protocols.Ae;
import org.eclipse.basyx.onem2m.xml.protocols.Cb;
import org.eclipse.basyx.onem2m.xml.protocols.Cin;
import org.eclipse.basyx.onem2m.xml.protocols.Cnt;
import org.eclipse.basyx.onem2m.xml.protocols.PrimitiveContent;
import org.eclipse.basyx.onem2m.xml.protocols.Resource;
import org.eclipse.basyx.onem2m.xml.protocols.Rqp;
import org.eclipse.basyx.onem2m.xml.protocols.Rsp;
import org.eclipse.basyx.onem2m.xml.protocols.Sub;

public class OneM2MResourceManager {

	IOneM2MClient client;
	OneM2MSubscriptionHandler subHandler = null;

	public OneM2MResourceManager(IOneM2MClient _client) {
		this.client = _client;
	}

	public void startSubscriptionHandler(String handlerid, String host, int port) throws Exception {
		this.subHandler = new OneM2MSubscriptionHandler(handlerid, host, port, this);
		this.subHandler.clearAllSubscriptions();
		this.subHandler.start();
	}

	public OneM2MSubscriptionHandler getSubscriptionHandler() {
		return this.subHandler;
	}

	public void stopSubscriptionHandler() throws Exception {
		this.subHandler.stop();
		this.subHandler = null;
	}

	public String addHandledSubscription(String parentRi, IOneM2MNotificationHandler notificationHandler)
			throws Exception {
		return this.addHandledSubscription(parentRi, notificationHandler, false);
	}

	public String addHandledSubscription(String parentRi, IOneM2MNotificationHandler notificationHandler,
			boolean hierarchical) throws Exception {
		if (subHandler == null) {
			throw new IllegalStateException("No handler has been started.");
		}
		return this.subHandler.addHandledSubscription(parentRi, notificationHandler, hierarchical);
	}

	public ResourceResult<Resource> createResource(String _path, boolean _hierarchical, Resource _resource)
			throws Exception {
		Rqp rqp = this.client.createDefaultRequest(_path, _hierarchical);
		rqp.setTy(BigInteger.valueOf(OneM2MResourceUtil.getTyFromResource(_resource)));
		rqp.setOp(BigInteger.valueOf(1));
		rqp.setPc(new PrimitiveContent());
		rqp.getPc().getAnyOrAny().add(_resource);

		Rsp rsp = this.client.send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2001))) {
			return new ResourceResult<Resource>(rsp, (Resource) rsp.getPc().getAnyOrAny().get(0));
		}
		return new ResourceResult<Resource>(rsp, null);
	}

	public ResourceResult<Resource> retrieveResource(String path, boolean hierarchical) throws Exception {
		Rqp rqp = this.client.createDefaultRequest(path, hierarchical);
		rqp.setOp(BigInteger.valueOf(2));
		Rsp rsp = this.client.send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2000))) {
			return new ResourceResult<Resource>(rsp, (Resource) rsp.getPc().getAnyOrAny().get(0));
		}
		return new ResourceResult<Resource>(rsp, null);
	}

	public ResourceResult<Resource> retrieveResourceWithChildren(String path, boolean hierarchical) throws Exception {
		Rqp rqp = this.client.createDefaultRequest(path, hierarchical);
		rqp.setOp(BigInteger.valueOf(2));
		rqp.setRcn(BigInteger.valueOf(4)); // add children
		Rsp rsp = this.client.send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2000))) {
			return new ResourceResult<Resource>(rsp, (Resource) rsp.getPc().getAnyOrAny().get(0));
		}
		return new ResourceResult<Resource>(rsp, null);
	}

	public DataResult<Boolean> deleteResource(String path, boolean hierarchical) throws Exception {
		Rqp rqp = this.client.createDefaultRequest(path, hierarchical);
		rqp.setOp(BigInteger.valueOf(4));
		Rsp rsp = this.client.send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2002))) {
			return new DataResult<Boolean>(rsp, true);
		}
		return new DataResult<Boolean>(rsp, false);
	}

	// Cb
	public ResourceResult<Cb> retrieveCSEBase() throws Exception {

		ResourceResult<Resource> result = this.retrieveResource("", false);

		if (result.resource instanceof Cb) {
			return new ResourceResult<Cb>(result.response, (Cb) result.resource);
		}
		return new ResourceResult<Cb>(result.response, null);
	}

	// ae
	public ResourceResult<Ae> createApplicationEntity(String path, Ae ae, boolean hierarchical) throws Exception {
		Rqp rqp = this.client.createDefaultRequest(path, hierarchical);
		rqp.setOp(BigInteger.valueOf(1));
		rqp.setTy(BigInteger.valueOf(OneM2MResourceUtil.getTyFromResource(ae)));
		rqp.setPc(new PrimitiveContent());
		rqp.getPc().getAnyOrAny().add(ae);

		Rsp rsp = this.client.send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2001))) {
			return new ResourceResult<Ae>(rsp, (Ae) rsp.getPc().getAnyOrAny().get(0));
		}
		return new ResourceResult<Ae>(rsp, null);
	}

	public ResourceResult<Ae> createApplicationEntity01(String path, boolean hierarchical) throws Exception {
		String appId = new Long(System.currentTimeMillis() / 1000l).toString();
		return this.createApplicationEntity02(path, appId, hierarchical);
	}

	public ResourceResult<Ae> createApplicationEntity02(String path, String appId, boolean hierarchical)
			throws Exception {
		Ae ae = new Ae();
		ae.setApi(appId);
		ae.setRr(false);
		ae.setRn(appId);
		return this.createApplicationEntity(path, ae, hierarchical);
	}

	public ResourceResult<Ae> retrieveApplicationEntity(String path, boolean hierarchical) throws Exception {
		ResourceResult<Resource> rr = this.retrieveResource(path, hierarchical);
		return new ResourceResult<Ae>(rr.response, (Ae) rr.resource);
	}

	public ResourceResult<Cnt> createContainer(String path, Cnt cnt, boolean hierarchical) throws Exception {
		Rqp rqp = this.client.createDefaultRequest(path, hierarchical);
		rqp.setOp(BigInteger.valueOf(1));
		rqp.setTy(BigInteger.valueOf(OneM2MResourceUtil.getTyFromResource(cnt)));
		rqp.setPc(new PrimitiveContent());
		rqp.getPc().getAnyOrAny().add(cnt);

		Rsp rsp = this.client.send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2001))) {
			return new ResourceResult<Cnt>(rsp, (Cnt) rsp.getPc().getAnyOrAny().get(0));
		}
		return new ResourceResult<Cnt>(rsp, null);
	}

	public ResourceResult<Cnt> createContainer01(String path, boolean hierarchical) throws Exception {
		Cnt cnt = new Cnt();
		return this.createContainer(path, cnt, hierarchical);
	}

	public ResourceResult<Cnt> createContainer02(String path, String rn, boolean hierarchical) throws Exception {
		Cnt cnt = new Cnt();
		cnt.setRn(rn);
		return this.createContainer(path, cnt, hierarchical);
	}

	public ResourceResult<Cnt> retrieveContainer(String path, boolean hierarchical) throws Exception {
		ResourceResult<Resource> rr = this.retrieveResource(path, hierarchical);
		return new ResourceResult<Cnt>(rr.response, (Cnt) rr.resource);
	}

	// content instance
	public ResourceResult<Cin> createContentInstance(String path, Cin cin, boolean hierarchical) throws Exception {
		Rqp rqp = this.client.createDefaultRequest(path, hierarchical);
		rqp.setOp(BigInteger.valueOf(1));
		rqp.setTy(BigInteger.valueOf(OneM2MResourceUtil.getTyFromResource(cin)));
		rqp.setPc(new PrimitiveContent());
		rqp.getPc().getAnyOrAny().add(cin);

		Rsp rsp = this.client.send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2001))) {
			return new ResourceResult<Cin>(rsp, (Cin) rsp.getPc().getAnyOrAny().get(0));
		}
		return new ResourceResult<Cin>(rsp, null);
	}

	public ResourceResult<Cin> createContentInstance01(String path, String content, boolean hierarchical)
			throws Exception {
		Cin cin = new Cin();
		cin.setCon(content);
		return this.createContentInstance(path, cin, hierarchical);
	}

	public ResourceResult<Cin> createContentInstance02(String path, String rn, String content, boolean hierarchical)
			throws Exception {
		Cin cin = new Cin();
		cin.setCon(content);
		cin.setRn(rn);
		return this.createContentInstance(path, cin, hierarchical);
	}

	public ResourceResult<Cin> retrieveContentInstance(String path, boolean hierarchical) throws Exception {
		ResourceResult<Resource> rr = this.retrieveResource(path, hierarchical);
		return new ResourceResult<Cin>(rr.response, (Cin) rr.resource);
	}

	// Sub
	public ResourceResult<Sub> createSubscription(String path, Sub sub, boolean hierarchical) throws Exception {
		Rqp rqp = this.client.createDefaultRequest(path, hierarchical);
		rqp.setOp(BigInteger.valueOf(1));
		rqp.setTy(BigInteger.valueOf(OneM2MResourceUtil.getTyFromResource(sub)));
		rqp.setPc(new PrimitiveContent());
		rqp.getPc().getAnyOrAny().add(sub);

		Rsp rsp = this.client.send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2001))) {
			return new ResourceResult<Sub>(rsp, (Sub) rsp.getPc().getAnyOrAny().get(0));
		}
		return new ResourceResult<Sub>(rsp, null);
	}

	public ResourceResult<Sub> createSubscription01(String path, String name, String[] urls, boolean hierarchical)
			throws Exception {
		Sub sub = new Sub();
		sub.setRn(name);
		sub.setNct(BigInteger.valueOf(1)); // default
		sub.getNu().addAll(Arrays.asList(urls));

		return this.createSubscription(path, sub, hierarchical);
	}

	public ResourceResult<Sub> createSubscription02(String path, String[] urls, boolean hierarchical) throws Exception {
		Sub sub = new Sub();
		sub.setNct(BigInteger.valueOf(1)); // default
		sub.getNu().addAll(Arrays.asList(urls));
		return this.createSubscription(path, sub, hierarchical);
	}

	public IOneM2MClient getClient() {
		return this.client;
	}

}
