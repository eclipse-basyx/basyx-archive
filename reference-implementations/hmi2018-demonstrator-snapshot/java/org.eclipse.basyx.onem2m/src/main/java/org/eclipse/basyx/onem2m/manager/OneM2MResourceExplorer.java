package org.eclipse.basyx.onem2m.manager;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.basyx.onem2m.clients.IOneM2MClient;
import org.eclipse.basyx.onem2m.util.OneM2MResourceUtil;
import org.eclipse.basyx.onem2m.xml.protocols.FilterCriteria;
import org.eclipse.basyx.onem2m.xml.protocols.Resource;
import org.eclipse.basyx.onem2m.xml.protocols.Rqp;
import org.eclipse.basyx.onem2m.xml.protocols.Rsp;

public class OneM2MResourceExplorer {

	IOneM2MClient client;

	public OneM2MResourceExplorer(IOneM2MClient _client) {
		this.client = _client;
	}

	@SuppressWarnings("unchecked")
	public DataResult<List<String>> findResourcesWithLabels01(String path, String[] labels, boolean hierarchical)
			throws Exception {
		Rqp rqp = this.client.createDefaultRequest(path, hierarchical);
		rqp.setOp(BigInteger.valueOf(2));
		FilterCriteria fc = new FilterCriteria();
		fc.setFu(BigInteger.valueOf(1));
		fc.getLbl().addAll(new ArrayList<>(Arrays.asList(labels)));
		rqp.setFc(fc);
		rqp.setDrt(BigInteger.valueOf(2));

		Rsp rsp = this.client.send(rqp);
		if (rsp.getRsc().equals(BigInteger.valueOf(2000))) {
			return new DataResult<List<String>>(rsp, (List<String>) rsp.getPc().getAnyOrAny().get(0));
		}
		return new DataResult<List<String>>(rsp, null);
	}

	public ResourceResults<Resource> retrieveResourceWithChildrenRecursive(String path, boolean hierarchical)
			throws Exception {
		return this.retrieveResourceWithChildrenRecursive(path, hierarchical, 0, true);
	}

	public ResourceResults<Resource> retrieveResourceWithChildrenRecursive(String path, boolean hierarchical, int depth)
			throws Exception {
		return this.retrieveResourceWithChildrenRecursive(path, hierarchical, depth, false);
	}

	private ResourceResults<Resource> retrieveResourceWithChildrenRecursive(String path, boolean hierarchical,
			int depth, boolean endless) throws Exception {

		Rqp rqp = this.client.createDefaultRequest(path, hierarchical);
		rqp.setOp(BigInteger.valueOf(2));
		rqp.setRcn(BigInteger.valueOf(4));

		Rsp rsp = this.client.send(rqp);
		ResourceResults<Resource> rer = new ResourceResults<Resource>();
		rer.responses.add(rsp);

		if (rsp.getRsc().equals(BigInteger.valueOf(2000))) {
			Resource res = (Resource) rsp.getPc().getAnyOrAny().iterator().next();

			rer.resource = res;
			List<ResourceResults<Resource>> resultsInnerSet = new ArrayList<>();
			List<Resource> children = OneM2MResourceUtil.getChildren(res);
			if (children != null) {
				for (Resource elem : children) {
					List<Resource> childChildren = OneM2MResourceUtil.getChildren(elem);
					if (childChildren != null) {
						if (childChildren.size() == 0) {
							// watch out for new childs
							if (endless) {
								ResourceResults<Resource> innerResult = this
										.retrieveResourceWithChildrenRecursive(elem.getRi(), false);
								OneM2MResourceUtil.addChildToParent(res, innerResult.resource);
								resultsInnerSet.add(innerResult);
							} else {
								if (depth > 0) {
									ResourceResults<Resource> innerResult = this
											.retrieveResourceWithChildrenRecursive(elem.getRi(), false, depth - 1);
									OneM2MResourceUtil.addChildToParent(res, innerResult.resource);
									resultsInnerSet.add(innerResult);
								}

							}
						} else if (childChildren.size() > 0) {
						}
					}
				}
			}

			for (ResourceResults<Resource> resultsInner : resultsInnerSet) {
				rer.responses.addAll(resultsInner.responses);
			}
		}
		return rer;
	}

}
