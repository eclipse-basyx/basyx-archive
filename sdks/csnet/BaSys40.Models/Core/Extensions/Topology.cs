using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations.DataElementSubtypes;
using BaSys40.Models.Core.Extensions.References;
using BaSys40.Models.Core.Identification;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace BaSys40.Models.Core.Extensions
{
    [DataContract]
    public class Topology : Submodel
    {
        [IgnoreDataMember]
        public List<TopologyElement> TopologyElements { get; private set; } 
        [IgnoreDataMember]
        public List<TopologyRelation> TopologyRelations { get; private set; }

        public Topology()
        { }

        public void InitializeSubmodel()
        {
            Identification = Identification ?? new Identifier(Guid.NewGuid().ToString(), KeyType.Custom);
            IdShort = IdShort ?? "Topology";
            Descriptions = Descriptions ?? new List<Description>() { new Description("EN", "Topology Submodel") };
            Kind = Kind ?? AssetAdministrationShell.Enums.Kind.Instance;
            SemanticId = SemanticId ?? new Reference(new ModelKey(KeyElements.Submodel, KeyType.URI, "urn:basys:org.eclipse.basyx:submodels:Topology:1.0.0"));
            DataElements = DataElements ?? new ElementContainer<IDataElement>()
            {
                new DataElementCollection(null)
                {
                    IdShort = "TopologyElements",
                    Parent = new Reference(new ModelKey(KeyElements.Submodel, Identification.IdType.Value, Identification.Id)),
                    Descriptions = new List<Description>() { new Description("EN", "Elements of Topology") },
                }
            };
        }

        public void AddTopologyElements(List<TopologyElement> topologyElements)
        {
            if (topologyElements != null)
                foreach (var topologyElement in topologyElements)
                {
                    (this.DataElements["TopologyElements"] as DataElementCollection).Value.Add(
                        new ReferenceElement(null)
                        {
                            Value = topologyElement.Reference
                        });
                }
            TopologyElements = topologyElements;
        }

        [DataContract]
        public class TopologyElement
        {
            [DataMember]
            public Reference Reference { get; set; }
        }

        [DataContract]
        public class TopologyRelation
        {
            [DataMember]
            public TopologyElement Source { get; set; }
            [DataMember]
            public TopologyElement Target { get; set; }
        }

        public class BaSysRelationship : TopologyRelation
        { }
        public class ConnectedTo : BaSysRelationship
        { }
        public class RelativePositionFrom : BaSysRelationship
        { }
        public class ConsistsOf : BaSysRelationship
        { }

    }
}
