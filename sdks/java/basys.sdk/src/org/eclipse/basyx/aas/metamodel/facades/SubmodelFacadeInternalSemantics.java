package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Collection;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Qualifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Typable;





/**
 * Template class that supports the development of sub models using IRDI (International Registration Data Identifier) semantic definitions
 * 
 * @author kuhn
 *
 */
public class SubmodelFacadeInternalSemantics extends SubmodelFacade {

		
	
	/**
	 * Constructor without arguments - create a sub model with all meta properties empty / set to default values
	 */
	public SubmodelFacadeInternalSemantics() {
		// Create sub model
		SubModel_ submodelData = new SubModel_();

		// Load predefined elements from sub model into this provider
		elements.putAll(submodelData);
	}

	
	/**
	 * Sub model constructor for sub models that conform to a globally defined semantics with IRDI (International Registration Data Identifier) 
	 * 
	 * Create an instance sub model with all meta properties empty / set to default values
	 * 
	 * @param semanticsInternal String that describes the sub model semantics e.g. its type (e.g. basys.semantics.transportsystem)
	 * @param idType            Type of sub model ID (Identification.IRDI, Identification.URI, Identification.Internal)
	 * @param id                Sub model ID according to idType
	 * @param idShort           Short ID of the sub model (e.g. "subsystemTopology")
	 * @param category          Additional coded meta information regarding the element type that affects expected existence of attributes (e.g. "transportSystemTopology")
	 * @param description       Descriptive sub model description (e.g. "This is a machine readable description of the transport system topology")
	 * @param qualifier         The qualifier of this sub model (e.g. "plant.maintransport")
	 * @param version           Sub model version
	 * @param revision          Sub model revision
	 */
	public SubmodelFacadeInternalSemantics(String semanticsInternal, int idType, String id, String idShort, String category, String description, String qualifier, String version, String revision) {
		// Create sub model
		SubModel_ submodelData = new SubModel_(
					new HasSemantics(new Identification(Identification.Internal, semanticsInternal)),
					new Identifiable(version, revision, idShort, category, description, idType, id),
					new Qualifiable(qualifier),
					new Typable(Typable.KIND_INSTANCE)
				);

		// Load predefined elements from sub model into this provider
		elements.putAll(submodelData);
	}



	/**
	 * Sub model constructor for sub models that conform to semantics
	 * 
	 * Create an instance sub model with all meta properties empty / set to default values
	 * 
	 * @param semanticsInternal String that describes the sub model semantics e.g. its type (e.g. basys.semantics.transportsystem)
	 * @param idType            Type of sub model ID (Identification.IRDI, Identification.URI, Identification.Internal)
	 * @param id                Sub model ID according to idType
	 * @param idShort           Short ID of the sub model (e.g. "subsystemTopology")
	 * @param category          Additional coded meta information regarding the element type that affects expected existence of attributes (e.g. "transportSystemTopology")
	 * @param description       Descriptive sub model description (e.g. "This is a machine readable description of the transport system topology")
	 * @param qualifier         The collection of qualifiers of this sub model (e.g. ["plant.maintransport", "maintransport."])
	 * @param version           Sub model version
	 * @param revision          Sub model revision
	 */
	public SubmodelFacadeInternalSemantics(String semanticsInternal, int idType, String id, String idShort, String category, String description, Collection<String> qualifier, String version, String revision) {
		// Create sub model
		SubModel_ submodelData = new SubModel_(
					new HasSemantics(new Identification(Identification.Internal, semanticsInternal)),
					new Identifiable(version, revision, idShort, category, description, idType, id),
					new Qualifiable(qualifier),
					new Typable(Typable.KIND_INSTANCE)
				);
		
		// Load predefined elements from sub model into this provider
		elements.putAll(submodelData);
	}
}

