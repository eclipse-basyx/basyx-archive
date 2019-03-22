package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IFormula;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.facades.FormulaFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * Forumla class as defined by DAAS document
 * 
 * @author schnicke
 *
 */
public class Formula extends Constraint implements IFormula {

	private static final long serialVersionUID = 1L;
	
	public static final String DEPENDSON="dependsOn";

	/**
	 * Constructor
	 */
	public Formula() {
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

	@Override
	public void setDependsOn(Set<IReference> dependsOn) {
		new FormulaFacade(this).setDependsOn(dependsOn);
		
	}

	@Override
	public Set<IReference> getDependsOn() {
		return new FormulaFacade(this).getDependsOn();
	}

}
