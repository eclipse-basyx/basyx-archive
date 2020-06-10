package org.eclipse.basyx.extensions.aas.directory.tagged.api;

import java.util.Set;

import org.eclipse.basyx.aas.registration.api.IAASRegistryService;

/**
 * A tagged directory is a registry that allows to register AAS and associate
 * tags with them. It is possible to retrieve AAS based on tags
 * 
 * @author schnicke
 *
 */
public interface IAASTaggedDirectory extends IAASRegistryService {
	public void register(TaggedAASDescriptor descriptor);

	/**
	 * Looks up all AAS that are tagged with <i>tag</i>
	 * 
	 * @param tag
	 * @return
	 */
	public Set<TaggedAASDescriptor> lookupTag(String tag);

	/**
	 * Looks up all AAS that are tagged with all <i>tags</i>
	 * 
	 * @param tag
	 * @return
	 */
	public Set<TaggedAASDescriptor> lookupTags(Set<String> tags);

}
