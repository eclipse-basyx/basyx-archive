package org.eclipse.basyx.aas.metamodel.map.parts;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.reference.ReferenceHelper;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * ConceptDictionary class as described in DAAS document
 * 
 * @author elsheikh, schnicke
 *
 */
public class ConceptDictionary extends VABModelMap<Object> implements IConceptDictionary {
	public static final String CONCEPTDESCRIPTION = "conceptDescription";

	// Extension of meta model
	public static final String CONCEPTDESCRIPTIONS = "conceptDescriptions";

	/**
	 * Constructor
	 */
	public ConceptDictionary() {
		// Add qualifier (Referable)
		putAll(new Referable());
		put(CONCEPTDESCRIPTION, new HashSet<String>());
		put(CONCEPTDESCRIPTIONS, new HashSet<IConceptDescription>());
	}

	public ConceptDictionary(Collection<IReference> ref) {
		// Add qualifier (Referable)
		putAll(new Referable());
		put(CONCEPTDESCRIPTION, ref);
	}

	/**
	 * Creates a ConceptDictionary object from a map
	 * 
	 * @param obj
	 *            a ConceptDictionary object as raw map
	 * @return a ConceptDictionary object, that behaves like a facade for the given
	 *         map
	 */
	public static ConceptDictionary createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		ConceptDictionary ret = new ConceptDictionary();
		ret.setMap(map);
		return ret;
	}

	@Override
	public String getIdShort() {
		return Referable.createAsFacade(this).getIdShort();
	}

	@Override
	public String getCategory() {
		return Referable.createAsFacade(this).getCategory();
	}

	@Override
	public LangStrings getDescription() {
		return Referable.createAsFacade(this).getDescription();
	}

	@Override
	public IReference getParent() {
		return Referable.createAsFacade(this).getParent();
	}

	public void setIdShort(String idShort) {
		Referable.createAsFacade(this).setIdShort(idShort);
	}

	public void setCategory(String category) {
		Referable.createAsFacade(this).setCategory(category);
	}

	public void setDescription(LangStrings description) {
		Referable.createAsFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		Referable.createAsFacade(this).setParent(obj);
	}

	@Override
	public Collection<IReference> getConceptDescription() {
		return ReferenceHelper.transform(get(ConceptDictionary.CONCEPTDESCRIPTION));
	}

	public void setConceptDescription(Collection<IReference> ref) {
		put(ConceptDictionary.CONCEPTDESCRIPTION, ref);
	}

	@SuppressWarnings("unchecked")
	public void addConceptDescription(IConceptDescription description) {
		((Collection<IConceptDescription>) get(CONCEPTDESCRIPTIONS)).add(description);
	}
}
