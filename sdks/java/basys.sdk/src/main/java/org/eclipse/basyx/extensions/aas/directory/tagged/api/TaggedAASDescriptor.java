package org.eclipse.basyx.extensions.aas.directory.tagged.api;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Extension of {@link AASDescriptor} that allows to add tags to the descriptor
 * 
 * @author schnicke
 *
 */
public class TaggedAASDescriptor extends AASDescriptor {
	public static final String MODELTYPE = "TaggedAASDescriptor";
	public static final String TAGS = "tags";

	/**
	 * Create a new aas descriptor that retrieves the necessary information from a
	 * passend AssetAdministrationShell
	 * 
	 * @param iAssetAdministrationShell
	 * @param endpoint
	 */
	public TaggedAASDescriptor(IAssetAdministrationShell assetAdministrationShell, String endpoint) {
		super(assetAdministrationShell, endpoint);
		initialize();
	}

	protected TaggedAASDescriptor() {
		super();
	}

	@SuppressWarnings("unchecked")
	public static TaggedAASDescriptor createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		Collection<String> tags = (Collection<String>) map.get(TAGS);
		if (tags instanceof List<?>) {
			map.put(TAGS, new HashSet<String>(tags));
		}

		TaggedAASDescriptor desc = new TaggedAASDescriptor();
		desc.putAll(map);
		return desc;
	}

	/**
	 * Create a new descriptor with minimal information
	 */
	public TaggedAASDescriptor(String idShort, IIdentifier id, String httpEndpoint) {
		super(idShort, id, httpEndpoint);
		initialize();
	}

	private void initialize() {
		put(TAGS, new HashSet<>());
	}

	/**
	 * Adds a tag to the AAS descriptor
	 * 
	 * @param tag
	 */
	public void addTag(String tag) {
		getTags().add(tag);
	}

	/**
	 * Adds a list of tags to the AAS descriptor
	 * 
	 * @param tags
	 */
	public void addTags(List<String> tags) {
		tags.stream().forEach(this::addTag);
	}

	/**
	 * Retrieves the Tags associated with the AAS descriptor
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getTags() {
		return (Set<String>) get(TAGS);
	}

	@Override
	protected String getModelType() {
		return MODELTYPE;
	}
}
