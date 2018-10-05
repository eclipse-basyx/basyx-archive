using System;
using System.Collections.Generic;
using static oneM2MClient.Resources.AttributeAttribute;

namespace oneM2MClient.Resources
{
    public interface IResource
    {
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.NotProvided, LongName = "resourceName", ShortName = "rn")]
        string ResourceName { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.NotProvided, UpdateOptionality = RequestOptionality.NotProvided, LongName = "resourceType", ShortName = "ty")]
        oneM2M.ResourceType? ResourceType { get; }
        [Attribute(CreateOptionality = RequestOptionality.NotProvided, UpdateOptionality = RequestOptionality.NotProvided, LongName = "resourceID", ShortName = "ri")]
        string ResourceID { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.NotProvided, UpdateOptionality = RequestOptionality.NotProvided, LongName = "parentID", ShortName = "pi")]
        string ParentID { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.NotProvided, UpdateOptionality = RequestOptionality.NotProvided, LongName = "creationTime", ShortName = "ct")]
        string CreationTime { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.NotProvided, UpdateOptionality = RequestOptionality.NotProvided, LongName = "lastModifiedTime", ShortName = "lt")]
        string LastModifiedTime { get; set; }
        [Attribute(CreateOptionality = RequestOptionality.Optional, UpdateOptionality = RequestOptionality.Optional, LongName = "labels", ShortName = "lbl")]
        List<string> Labels { get; set; }
        List<Type> SupportedChildResourceTypes { get;}
    }
}
