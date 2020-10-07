package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Referable class
 * 
 * @author kuhn, schnicke
 *
 */
public class Referable extends VABModelMap<Object> implements IReferable {
	public static final String IDSHORT="idShort";
	
	public static final String CATEGORY="category";
	
	public static final String DESCRIPTION="description";
	
	public static final String PARENT="parent";

	private KeyElements elem;

	/**
	 * Constructor
	 */
	public Referable() {
		// Identifies an element within its name space (String)
		put(IDSHORT, "");
		// Coded value that gives further meta information w.r.t. to the type of the
		// element. It affects the
		// expected existence of attributes and the applicability of constraints.
		// (String)
		put(CATEGORY, "");
		// Description or comments on the element (String)
		
		put(DESCRIPTION, new LangStrings());
		// Reference to the parent of this element (Referable)
		put(PARENT, null);
	}
	
	/**
	 * Constructor with mandatory attribute
	 * @param idShort
	 */
	public Referable(String idShort) {
		setIdShort(idShort);
	}

	/**
	 * Constructor with idShort, category and description
	 * 
	 * @param idShort
	 *            will be matched case insensitive; may only feature letters,
	 *            digits, underscores and start with a letter
	 * @param category
	 * @param description
	 */
	public Referable(String idShort, String category, LangStrings description) {
		// Identifies an element within its name space (String)
		put(IDSHORT, idShort);
		// Coded value that gives further meta information w.r.t. to the type of the
		// element. It affects the
		// expected existence of attributes and the applicability of constraints.
		// (String)
		put(CATEGORY, category);
		// Description or comments on the element
		put(DESCRIPTION, description);
		// Reference to the parent of this element (Referable)
		put(PARENT, null);
	}

	/**
	 * Creates a Referable object from a map
	 * 
	 * @param obj
	 *            a Referable object as raw map
	 * @return a Referable object, that behaves like a facade for the given map
	 */
	public static Referable createAsFacade(Map<String, Object> map, KeyElements type) {
		if (map == null) {
			return null;
		}

		Referable ret = new Referable();
		ret.setMap(map);
		ret.setElementType(type);

		return ret;
	}

	@Override
	public String getIdShort() {
		return (String) get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) get(Referable.CATEGORY);
	}

	@Override
	@SuppressWarnings("unchecked")
	public LangStrings getDescription() {
		return LangStrings.createAsFacade((Collection<Map<String, Object>>) get(Referable.DESCRIPTION));
	}

	@Override
	@SuppressWarnings("unchecked")
	public IReference getParent() {
		return Reference.createAsFacade((Map<String, Object>) get(Referable.PARENT));
	}

	public void setIdShort(String idShort) {
		put(Referable.IDSHORT, idShort);
	}

	public void setCategory(String category) {
		put(Referable.CATEGORY, category);
	}

	public void setDescription(LangStrings description) {
		put(Referable.DESCRIPTION, description);
	}

	/**
	 * Sets the parent and additionally updates the own reference
	 * 
	 * @param obj
	 */
	public void setParent(IReference obj) {
		put(Referable.PARENT, obj);
	}

	protected void setElementType(KeyElements elem) {
		this.elem = elem;
	}

	protected KeyType getKeyType() {
		return KeyType.IDSHORT;
	}

	protected String getId() {
		return getIdShort();
	}

	protected boolean isLocal() {
		return true;
	}

	@Override
	public IReference getReference() {
		List<IKey> keys = new ArrayList<>();
		IReference parent = getParent();
		if (parent != null) {
			keys.addAll(parent.getKeys());
		}
		keys.add(new Key(elem, isLocal(), getId(), getKeyType()));
		return new Reference(keys);
	}

}
