package org.eclipse.basyx.submodel.metamodel.api.reference;

import java.util.List;

/**
 * Reference to either a model element of the same or another AAs or to an
 * external entity. <br />
 * <br />
 * A reference is an ordered list of keys, each key referencing an element. The
 * complete list of keys may for example be concatenated to a path that then
 * gives unique access to an element or entity.
 * 
 * @author rajashek, schnicke
 *
 */

public interface IReference {

	/**
	 * Gets the keys describing the reference.
	 * 
	 * @return
	 */
	public List<IKey> getKeys();
}
