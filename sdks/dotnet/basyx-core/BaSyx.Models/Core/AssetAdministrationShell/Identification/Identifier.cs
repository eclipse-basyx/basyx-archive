/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
using BaSyx.Models.Core.AssetAdministrationShell.References;
using Newtonsoft.Json;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace BaSyx.Models.Core.AssetAdministrationShell.Identification
{
    /// <summary>
    /// Used to uniquely identify an entity by using an identifier.
    /// </summary>
    [DataContract]
    public class Identifier
    {
        /// <summary>
        /// Identifier of the element. Its type is defined in idType.
        /// </summary>
        [DataMember(EmitDefaultValue = false, IsRequired = true, Name = "id")]
        [JsonProperty(Required = Required.Always, DefaultValueHandling = DefaultValueHandling.Include)]
        [XmlText]
        public string Id { get; set; }

        /// <summary>
        /// Type of the Identifierr, e.g. IRI, IRDI etc. The supported Identifier types are defined in the enumeration “IdentifierType”. 
        /// </summary>
        [DataMember(EmitDefaultValue = false, IsRequired = true, Name = "idType")]
        [JsonProperty(Required = Required.Always, DefaultValueHandling = DefaultValueHandling.Include)]
        [XmlAttribute("idType")]
        public KeyType IdType { get; set; }

        internal Identifier() { }

        public Identifier(string id, KeyType idType)
        {
            Id = id;
            IdType = idType;
        }


        public class UniformResourceIdentifier : Identifier
        {
            public string Organisation { get; internal set; }
            public string SubUnit { get; internal set; }
            public string DomainId { get; internal set; }
            public string Version { get; internal set; }
            public string Revision { get; internal set; }
            public string ElementId { get; internal set; }
            public string InstanceNumber { get; internal set; }

            public UniformResourceIdentifier(string organisation, string subUnit, string domainId, string version, string revision, string elementId, string instanceNumber)
                : base(ToUrn(organisation, subUnit, domainId, version, revision, elementId, instanceNumber), KeyType.IRI)
            {
                Organisation = organisation;
                SubUnit = subUnit;
                DomainId = domainId;
                Version = version;
                Revision = revision;
                ElementId = elementId;
                InstanceNumber = instanceNumber;
            }

            public string ToUri() => ToUri(Organisation, SubUnit, DomainId, Version, Revision, ElementId, InstanceNumber);
            public string ToUrn() => ToUrn(Organisation, SubUnit, DomainId, Version, Revision, ElementId, InstanceNumber);

            public static string ToUri(string organisation, string subUnit, string domainId, string version, string revision, string elementId, string instanceNumber)
            {
                string uri = "http://";

                uri += organisation + "/";

                if (!string.IsNullOrEmpty(subUnit))
                    uri += subUnit + "/";
                if (!string.IsNullOrEmpty(domainId))
                    uri += domainId + "/";
                if (!string.IsNullOrEmpty(version))
                    uri += version + "/";
                if (!string.IsNullOrEmpty(revision))
                    uri += revision + "/";
                if (!string.IsNullOrEmpty(elementId))
                    uri += elementId + "/";

                if (!string.IsNullOrEmpty(instanceNumber))
                {
                    uri = uri.Substring(0, uri.Length - 2);
                    uri += "#" + instanceNumber;
                }
                return uri;
            }
            public static string ToUrn(string organisation, string subUnit, string domainId, string version, string revision, string elementId, string instanceNumber)
            {
                string urn = "urn:";

                urn += organisation + ":";

                if (!string.IsNullOrEmpty(subUnit))
                    urn += subUnit + ":";
                if (!string.IsNullOrEmpty(domainId))
                    urn += domainId + ":";
                if (!string.IsNullOrEmpty(version))
                    urn += version + ":";
                if (!string.IsNullOrEmpty(revision))
                    urn += revision + ":";
                if (!string.IsNullOrEmpty(elementId))
                    urn += elementId + ":";

                if (!string.IsNullOrEmpty(instanceNumber))
                {
                    urn = urn.Substring(0, urn.Length - 2);
                    urn += "#" + instanceNumber;
                }
                return urn;
            }
        }

    }

}
