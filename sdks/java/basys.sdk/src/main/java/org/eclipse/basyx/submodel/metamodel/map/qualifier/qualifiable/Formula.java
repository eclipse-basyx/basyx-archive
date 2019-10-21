package org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IFormula;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable.FormulaFacade;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * Forumla class as defined by DAAS document
 * 
 * @author schnicke
 *
 */
public class Formula extends Constraint implements IFormula {

	private static final long serialVersionUID = 1L;

	public static final String DEPENDSON = "dependsOn";

	public static final String MODELTYPE = "Formula";

	/**
	 * Constructor
	 */
	public Formula() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		put(DEPENDSON, new HashSet<Reference>());
	}

	/**
	 * 
	 * @param dependsOn
	 *            set of References the formula depends on
	 */
	public Formula(Set<Reference> dependsOn) {
		put(DEPENDSON, dependsOn);
	}

	public void setDependsOn(Set<IReference> dependsOn) {
		new FormulaFacade(this).setDependsOn(dependsOn);

	}

	@Override
	public Set<IReference> getDependsOn() {
		return new FormulaFacade(this).getDependsOn();
	}

}
