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
using BaSyx.Models.Core.AssetAdministrationShell.Identification;
using System;

namespace BaSyx.Models.Core.Attributes
{
    [AttributeUsage(AttributeTargets.Class, Inherited = false, AllowMultiple = true)]
    sealed class DataSpecificationAttribute : Attribute
    {
        readonly IReference reference;

        public DataSpecificationAttribute(string dataSpecificationReference)
        {
            reference = new Reference(
                new GlobalKey(KeyElements.GlobalReference, KeyType.IRI, dataSpecificationReference));
        }

        public IReference Reference
        {
            get { return reference; }
        }
    }

    [AttributeUsage(AttributeTargets.Property, Inherited = false, AllowMultiple = true)]
    sealed class DataSpecificationContentAttribute : Attribute
    {
        readonly Type contentType;
        public string ShortNamespace { get; }

        public DataSpecificationContentAttribute(Type contentType, string shortNamespace)
        {
            this.contentType = contentType;
            ShortNamespace = shortNamespace;
        }

        public Type ContentType
        {
            get { return contentType; }
        }
    }
}
