package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IReferable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.facades.ReferableFacade;

/**
 * Referable class
 * 
 * @author kuhn, schnicke
 *
 */
public class Referable extends HashMap<String, Object> implements IReferable {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String IDSHORT="idShort";
	
	public static final String CATEGORY="category";
	
	public static final String DESCRIPTION="description";
	
	public static final String PARENT="parent";

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
		put(DESCRIPTION, "");
		// Reference to the parent of this element (Referable)
		put(PARENT, null);
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
	public Referable(String idShort, String category, String description) {
		// Identifies an element within its name space (String)
		put(IDSHORT, idShort);
		// Coded value that gives further meta information w.r.t. to the type of the
		// element. It affects the
		// expected existence of attributes and the applicability of constraints.
		// (String)
		put(CATEGORY, category);
		// Description or comments on the element (String)
		put(DESCRIPTION, description);
		// Reference to the parent of this element (Referable)
		put(PARENT, null);
	}

	@Override
	public String getIdshort() {
	return new ReferableFacade(this).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public String getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public IReference  getParent() {
		return new ReferableFacade(this).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		new ReferableFacade(this).setIdshort(idShort);
		
	}

	@Override
	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
		
	}

	@Override
	public void setDescription(String description) {
		new ReferableFacade(this).setDescription(description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		new ReferableFacade(this).setParent(obj);
		
	}


}
