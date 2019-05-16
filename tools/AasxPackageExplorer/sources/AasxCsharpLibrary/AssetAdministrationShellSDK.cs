using System.Xml;
using System.Runtime;
using System.ComponentModel;
using System.Collections;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.Text;
using System.Text.RegularExpressions;
using System.IO;
using System;
using System.IO.Packaging;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AdminShellNS
{
    #region Utils
    // 
    // Utils
    //

    public class AdminShellUtil
    {
        public static string EvalToNonNullString(string fmt, object o, string elseString = "")
        {
            if (o == null)
                return elseString;
            return string.Format(fmt, o);
        }

        public static string EvalToNonEmptyString(string fmt, string o, string elseString = "")
        {
            if (o == "")
                return elseString;
            return string.Format(fmt, o);
        }

        public static string FilterFriendlyName(string src)
        {
            if (src == null)
                return null;
            return Regex.Replace(src, @"[^a-zA-Z0-9\-_]", "");
        }

        public static bool HasWhitespace(string src)
        {
            if (src == null)
                throw new ArgumentNullException("src");
            for (var i = 0; i < src.Length; i++)
                if (char.IsWhiteSpace(src[i]))
                    return true;
            return false;
        }

        public static string ShortLocation(Exception ex)
        {
            if (ex == null || ex.StackTrace == null)
                return "";
            string[] lines = ex.StackTrace.Split(new[] { "\r\n", "\r", "\n" }, StringSplitOptions.None);
            if (lines.Length < 1)
                return "";
            // search for " in "
            var p = lines[0].IndexOf(" in ");
            if (p < 0)
                return "";
            // search last "\" or "/", to get only filename portion and position
            p = lines[0].LastIndexOfAny(new char[] { '\\', '/' });
            if (p < 0)
                return "";
            // return this
            return lines[0].Substring(p);
        }

    }

    #endregion

    /// <summary>
    /// This empty class derives always from the current version of the Administration Shell class hierarchy.
    /// </summary>
    public class AdminShell : AdminShellV10 { }

    #region AdminShell_V1_0

    /// <summary>
    /// Version of Details of Administration Shell Part 1 V1.0 published Nov/Dec/Jan 2018/19
    /// </summary>
    public class AdminShellV10
    {

        public class Identification
        {

            // members

            [XmlAttribute]
            public string idType = "";
            [XmlText]
            public string id = "";

            // constructors

            public Identification() { }

            public Identification(Identification src)
            {
                this.idType = src.idType;
                this.id = src.id;
            }

            public override string ToString()
            {
                return $"[{this.idType}] {this.id}";
            }
        }

        public class Administration
        {

            // members

            public string version = "";
            public string revision = "";

            // constructors

            public Administration() { }

            public Administration(Administration src)
            {
                this.version = src.version;
                this.revision = src.revision;
            }

            public override string ToString()
            {
                return $"R={this.version}, V={this.revision}";
            }
        }

        public class Key
        {
            [XmlAttribute]
            public string type = "";
            [XmlAttribute]
            public bool local = false;
            [XmlAttribute]
            public string idType = "";
            [XmlText]
            public string value = "";

            public Key()
            {
            }

            public Key(Key src)
            {
                this.type = src.type;
                this.local = src.local;
                this.idType = src.idType;
                this.value = src.value;
            }

            public Key(string type, bool local, string idType, string value)
            {
                this.type = type;
                this.local = local;
                this.idType = idType;
                this.value = value;
            }

            public static Key CreateNew(string type, bool local, string idType, string value)
            {
                var k = new Key();
                k.type = type;
                k.local = local;
                k.idType = idType;
                k.value = value;
                return (k);
            }

            public static Key GetFromRef(Reference r)
            {
                if (r == null || r.Count != 1)
                    return null;
                return r[0];
            }

            public override string ToString()
            {
                var local = (this.local) ? "Local" : "not Local";
                return $"[{this.type}, {local}, {this.idType}, {this.value}]";
            }

            public static string KeyListToString(List<Key> keys)
            {
                if (keys == null || keys.Count < 1)
                    return "";
                // normally, exactly one key
                if (keys.Count == 1)
                    return keys[0].ToString();
                // multiple!
                var s = "[ ";
                foreach (var k in keys)
                {
                    if (s.Length > 0)
                        s += ", ";
                    s += k.ToString();
                }
                return s + " ]";
            }

            public static string[] KeyElements = new string[] {
            "GlobalReference",
            "AccessPermissionRule",
            "Asset",
            "AssetAdministrationShell",
            "ConceptDescription",
            "Submodel",
            "SubmodelRef", // not completely right, but used by Package Explorer
            "Blob",
            "ConceptDictionary",
            "DataElement",
            "File",
            "Event",
            "Operation",
            "OperationVariable",
            "Property",
            "ReferenceElement",
            "RelationshipElement",
            "SubmodelElement",
            "SubmodelElementCollection",
            "View" };

            public static string[] ReferableElements = new string[] {
            "AccessPermissionRule",
            "Asset",
            "AssetAdministrationShell",
            "ConceptDescription",
            "Submodel",
            "Blob",
            "ConceptDictionary",
            "DataElement",
            "File",
            "Event",
            "Operation",
            "OperationVariable",
            "Property",
            "ReferenceElement",
            "RelationshipElement",
            "SubmodelElement",
            "SubmodelElementCollection",
            "View"
        };

            public static string[] SubmodelElements = new string[] {
            "DataElement",
            "File",
            "Event",
            "Operation",
            "Property",
            "ReferenceElement",
            "RelationshipElement",
            "SubmodelElementCollection"};

            public static string[] IdentifiableElements = new string[] {
            "Asset",
            "AssetAdministrationShell",
            "ConceptDescription",
            "Submodel" };

            // use this in list to designate all of the above elements
            public static string AllElements = "All";

            // use this in list to designate the GlobalReference
            public static string GlobalReference = "GlobalReference";
            public static string ConceptDescription = "ConceptDescription";

            public static string[] IdentifierTypeNames = new string[] { "idShort", "Custom", "IRDI", "URI" };

            public enum IdentifierType { IdShort = 0, Custom, IRDI, URI };

            public static string GetIdentifierTypeName(IdentifierType t)
            {
                return IdentifierTypeNames[(int)t];
            }

        }

        // the whole class shall not be serialized by having it private
        public class KeyList
        {

            // members

            [XmlIgnore] // anyway, as it is privat
            private List<Key> key = new List<Key>();

            // getters / setters

            [XmlIgnore]
            public List<Key> Keys { get { return key; } }
            [XmlIgnore]
            public bool IsEmpty { get { return key == null || key.Count < 1; } }
            [XmlIgnore]
            public int Count { get { if (key == null) return 0; return key.Count; } }
            [XmlIgnore]
            public Key this[int index] { get { return key[index]; } }

            // constructors / creators

            public void Add(Key k)
            {
                key.Add(k);
            }
        }

        [XmlType(TypeName = "reference")]
        public class Reference
        {

            // members

            [XmlIgnore] // anyway, as it is privat
            private KeyList keys = new KeyList();

            // getters / setters

            [XmlArray("keys")]
            [XmlArrayItem("key")]
            public List<Key> Keys { get { if (keys == null) return null; return keys.Keys; } }

            [XmlIgnore]
            public bool IsEmpty { get { return keys == null || keys.Count < 1; } }
            [XmlIgnore]
            public int Count { get { if (keys == null) return 0; return keys.Count; } }
            [XmlIgnore]
            public Key this[int index] { get { return keys[index]; } }

            // constructors / creators

            public Reference()
            {
            }

            public Reference(Key k)
            {
                keys.Keys.Add(k);
            }

            public Reference(Reference src)
            {
                foreach (var k in src.Keys)
                    keys.Add(new Key(k));
            }


            public static Reference CreateNew(Key k)
            {
                var r = new Reference();
                r.keys.Keys.Add(k);
                return r;
            }

            public static Reference CreateNew(List<Key> k)
            {
                var r = new Reference();
                r.keys.Keys.AddRange(k);
                return r;
            }

            public static Reference CreateIrdiReference(string irdi)
            {
                var r = new Reference();
                r.keys.Keys.Add(new Key(Key.GlobalReference, false, "IRDI", irdi));
                return r;
            }

            public bool IsExactlyOneKey(string type, bool local, string idType, string id)
            {
                if (keys == null || keys.Keys == null || keys.Count != 1)
                    return false;
                var k = keys.Keys[0];
                return k.type == type && k.local == local && k.idType == idType && k.value == id;
            }
            public override string ToString()
            {
                var res = "";
                if (keys != null && keys.Keys != null)
                    foreach (var k in keys.Keys)
                        res += k.ToString() + ",";
                return res.TrimEnd(',');
            }

            public virtual string GetElementName()
            {
                return "Reference";
            }
        }

        [XmlType(TypeName = "derivedFrom")]
        public class AssetAdministrationShellRef : Reference
        {
            // constructors

            public AssetAdministrationShellRef() : base() { }

            public AssetAdministrationShellRef(Key k) : base(k) { }

            public AssetAdministrationShellRef(Reference src) : base(src) { }

            // further methods

            public override string GetElementName()
            {
                return "AssetAdministrationShellRef";
            }
        }

        [XmlType(TypeName = "assetRef")]
        public class AssetRef : Reference
        {
            // constructors

            public AssetRef() : base() { }

            public AssetRef(AssetRef src) : base(src) { }

            // further methods

            public override string GetElementName()
            {
                return "AssetRef";
            }
        }

        [XmlType(TypeName = "submodelRef")]
        public class SubmodelRef : Reference
        {
            // constructors

            public SubmodelRef() : base() { }

            public SubmodelRef(SubmodelRef src) : base(src) { }

            // further methods

            public override string GetElementName()
            {
                return "SubmodelRef";
            }
        }

        [XmlType(TypeName = "conceptDescriptionRef")]
        public class ConceptDescriptionRef : Reference
        {
            // constructors

            public ConceptDescriptionRef() : base() { }

            public ConceptDescriptionRef(ConceptDescriptionRef src) : base(src) { }

            // further methods

            public override string GetElementName()
            {
                return "ConceptDescriptionRef";
            }
        }

        [XmlType(TypeName = "dataSpecificationRef")]
        public class DataSpecificationRef : Reference
        {
            // constructors

            public DataSpecificationRef() : base() { }

            public DataSpecificationRef(DataSpecificationRef src) : base(src) { }

            // further methods

            public override string GetElementName()
            {
                return "DataSpecificationRef";
            }
        }

        [XmlType(TypeName = "conceptDescriptions")]
        public class ConceptDescriptionRefs
        {
            [XmlElement(ElementName = "conceptDescriptionRef")]
            public List<ConceptDescriptionRef> conceptDescriptions = new List<ConceptDescriptionRef>();
        }

        [XmlType(TypeName = "containedElementRef")]
        public class ContainedElementRef : Reference
        {
            public static ContainedElementRef FromReference(Reference otherref)
            {
                var r = ContainedElementRef.CreateNew(otherref.Keys);
                return (ContainedElementRef)r;
            }

            // further methods

            public override string GetElementName()
            {
                return "ContainedElementRef";
            }
        }

        [XmlType(TypeName = "hasDataSpecification")]
        public class HasDataSpecification
        {
            [XmlElement(ElementName = "reference")] // make "reference" go away by magic?!
            public List<Reference> reference = new List<Reference>();

            public HasDataSpecification() { }

            public HasDataSpecification(HasDataSpecification src)
            {
                foreach (var r in src.reference)
                    reference.Add(new Reference(r));
            }
        }

        [XmlType(TypeName = "ContainedElements")]
        public class ContainedElements
        {

            // members

            [XmlElement(ElementName = "containedElementRef")] // make "reference" go away by magic?!
            public List<ContainedElementRef> reference = new List<ContainedElementRef>();

            // getter / setter

            public bool IsEmpty { get { return reference == null || reference.Count < 1; } }
            public int Count { get { if (reference == null) return 0; return reference.Count; } }
            public ContainedElementRef this[int index] { get { return reference[index]; } }

        }

        [XmlType(TypeName = "langString", Namespace = "http://www.admin-shell.io/1/0")]
        public class LangStr
        {

            // members

            [XmlAttribute(Namespace = "http://www.admin-shell.io/1/0")]
            public string lang = "";
            [XmlText]
            public string str = "";

            // constructors

            public LangStr() { }

            public LangStr(LangStr src)
            {
                this.lang = src.lang;
                this.str = src.str;
            }

            public static LangStr CreateNew(string lang, string str)
            {
                var l = new LangStr();
                l.lang = lang;
                l.str = str;
                return (l);
            }

            public static List<LangStr> CreateManyFromStringArray(string[] s)
            {
                var r = new List<LangStr>();
                var i = 0;
                while ((i + 1) < s.Length)
                {
                    r.Add(LangStr.CreateNew(s[i], s[i + 1]));
                    i += 2;
                }
                return r;
            }
        }

        public class Description
        {

            // members

            [XmlElement(ElementName = "langString")]
            public List<LangStr> langString = new List<LangStr>();

            // constructors

            public Description() { }

            public Description(Description src)
            {
                if (src != null)
                    foreach (var ls in src.langString)
                        langString.Add(new LangStr(ls));
            }
        }

        public class Kind
        {
            [XmlText]
            public string kind = "Instance";

            // getters / setters

            public bool IsInstance { get { return kind.Trim().ToLower() == "instance"; } }
            public bool IsType { get { return kind.Trim().ToLower() == "type"; } }

            // constructors / creators

            public Kind() { }

            public Kind(Kind src)
            {
                kind = src.kind;
            }

            public static Kind CreateFrom(Kind k)
            {
                var res = new Kind();
                res.kind = k.kind;
                return res;
            }
        }

        public class SemanticId
        {

            // members

            [XmlIgnore] // will be ignored, anyway
            private KeyList keys = new KeyList();

            // getters / setters

            [XmlArray("keys")]
            [XmlArrayItem("key")]
            public List<Key> Keys { get { return keys.Keys; } }
            [XmlIgnore]
            public bool IsEmpty { get { return keys == null || keys.IsEmpty; } }
            [XmlIgnore]
            public int Count { get { if (keys == null) return 0; return keys.Count; } }
            [XmlIgnore]
            public Key this[int index] { get { return keys[index]; } }

            public override string ToString()
            {
                return Key.KeyListToString(keys.Keys);
            }

            // constructors / creators

            public SemanticId()
            {
            }

            public SemanticId(SemanticId src)
            {
                foreach (var k in src.Keys)
                    keys.Add(k);
            }

            public static SemanticId CreateFromKey(Key key)
            {
                if (key == null)
                    return null;
                var res = new SemanticId();
                res.Keys.Add(key);
                return res;
            }

            public static SemanticId CreateFromKeys(List<Key> keys)
            {
                if (keys == null)
                    return null;
                var res = new SemanticId();
                res.Keys.AddRange(keys);
                return res;
            }
        }

        public class Referable
        {

            // members

            public string idShort = null;
            public string category = null;
            [XmlElement(ElementName = "description")]
            public Description description = null;
            [XmlIgnore]
            public Referable parent = null;

            public static string[] ReferableCategoryNames = new string[] { "CONSTANT", "PARAMETER", "VARIABLE" };

            // constructors

            public Referable() { }

            public Referable(Referable src)
            {
                this.idShort = src.idShort;
                this.category = src.category;
                if (src.description != null)
                    this.description = new Description(src.description);
            }

            public void AddDescription(string lang, string str)
            {
                if (description == null)
                    description = new Description();
                description.langString.Add(LangStr.CreateNew(lang, str));
            }

            public virtual string GetElementName()
            {
                return "GlobalReference"; // not correct, but this method wasn't overridden correctly
            }

            public string GetFriendlyName()
            {
                return AdminShellUtil.FilterFriendlyName(this.idShort);
            }

            public void CollectReferencesByParent(List<Key> refs)
            {
                // check, if this is identifiable
                if (this is Identifiable)
                {
                    var idf = this as Identifiable;
                    var k = Key.CreateNew(idf.GetElementName(), true, idf.identification.idType, idf.identification.id);
                    refs.Insert(0, k);
                }
                else
                {
                    var k = Key.CreateNew(this.GetElementName(), true, "idShort", this.idShort);
                    refs.Insert(0, k);
                    // recurse upwards!
                    if (parent != null && parent is Referable)
                        (this.parent).CollectReferencesByParent(refs);
                }
            }
        }

        public class Identifiable : Referable
        {

            // members

            public Identification identification = new Identification();
            public Administration administration = null;

            // constructors

            public Identifiable() : base() { }

            public Identifiable(Identifiable src)
                : base(src)
            {
                if (src.identification != null)
                    this.identification = new Identification(src.identification);
                if (src.administration != null)
                    this.administration = new Administration(src.administration);
            }

            public void SetIdentification(string idType, string id, string idShort = null)
            {
                identification.idType = idType;
                identification.id = id;
                if (idShort != null)
                    this.idShort = idShort;
            }

            public void SetAdminstration(string version, string revision)
            {
                if (administration == null)
                    administration = new Administration();
                administration.version = version;
                administration.revision = revision;
            }

            public new string GetFriendlyName()
            {
                if (identification != null && identification.id != "")
                    return AdminShellUtil.FilterFriendlyName(this.identification.id);
                return AdminShellUtil.FilterFriendlyName(this.idShort);
            }

        }

        public class AdministrationShell : Identifiable
        {

            // from hasDataSpecification:
            [XmlElement(ElementName = "hasDataSpecification")]
            public HasDataSpecification hasDataSpecification = null;

            // from this very class
            public AssetAdministrationShellRef derivedFrom = null;
            public AssetRef assetRef = new AssetRef();
            public List<SubmodelRef> submodelRefs = new List<SubmodelRef>();
            public Views views = null;
            public List<ConceptDictionary> conceptDictionaries = null;

            public static AdministrationShell CreateNew(string idType, string id, string version = null, string revision = null)
            {
                var s = new AdministrationShell();
                s.identification.idType = idType;
                s.identification.id = id;
                if (version != null)
                    s.SetAdminstration(version, revision);
                return (s);
            }

            public void AddView(View v)
            {
                if (views == null)
                    views = new Views();
                views.views.Add(v);
            }

            public void AddConceptDictionary(ConceptDictionary d)
            {
                if (conceptDictionaries == null)
                    conceptDictionaries = new List<ConceptDictionary>();
                conceptDictionaries.Add(d);
            }

            public void AddDataSpecification(Key k)
            {
                if (hasDataSpecification == null)
                    hasDataSpecification = new HasDataSpecification();
                var r = new Reference();
                r.Keys.Add(k);
                hasDataSpecification.reference.Add(r);
            }

            public override string GetElementName()
            {
                return "AssetAdministrationShell";
            }

            public Tuple<string, string> ToCaptionInfo()
            {
                var caption = AdminShellUtil.EvalToNonNullString("\"{0}\" ", idShort, "\"AAS\"");
                var info = "";
                if (identification != null)
                    info = $"[{identification.idType}, {identification.id}]";
                return Tuple.Create(caption, info);
            }

            public override string ToString()
            {
                var ci = ToCaptionInfo();
                return string.Format("{0}{1}", ci.Item1, (ci.Item2 != "") ? " / " + ci.Item2 : "");
            }

        }

        public class Asset : Identifiable
        {
            // from hasDataSpecification:
            [XmlElement(ElementName = "hasDataSpecification")]
            public HasDataSpecification hasDataSpecification = null;
            // from HasKind
            [XmlElement(ElementName = "kind")]
            public Kind kind = new Kind();
            // from this very class
            [XmlElement(ElementName = "assetIdentificationModelRef")]
            public SubmodelRef assetIdentificationModelRef = null;

            // Getter & setters

            public AssetRef GetReference()
            {
                var r = new AssetRef();
                r.Keys.Add(Key.CreateNew(this.GetElementName(), true, this.identification.idType, this.identification.id));
                return r;
            }

            public override string GetElementName()
            {
                return "Asset";
            }

            public Tuple<string, string> ToCaptionInfo()
            {
                var caption = AdminShellUtil.EvalToNonNullString("\"{0}\" ", idShort, "<no idShort!>");
                var info = "";
                if (identification != null)
                    info = $"[{identification.idType}, {identification.id}]";
                return Tuple.Create(caption, info);
            }

            public override string ToString()
            {
                var ci = ToCaptionInfo();
                return string.Format("{0}{1}", ci.Item1, (ci.Item2 != "") ? " / " + ci.Item2 : "");
            }

        }

        public class View : Referable
        {

            // members

            // from hasSemanticId:
            [XmlElement(ElementName = "semanticId")]
            public SemanticId semanticId = null;
            // from hasDataSpecification
            [XmlElement(ElementName = "hasDataSpecification")]
            public HasDataSpecification hasDataSpecification = null;
            // from this very class
            public ContainedElements containedElements = null;

            // getter / setter

            public bool IsEmpty { get { return containedElements == null || containedElements.Count < 1; } }
            public int Count { get { if (containedElements == null) return 0; return containedElements.Count; } }
            public ContainedElementRef this[int index] { get { return containedElements[index]; } }

            // constructors / creators

            public static View CreateNew(string idShort)
            {
                var v = new View();
                v.idShort = idShort;
                return (v);
            }

            public void AddDataSpecification(Key k)
            {
                if (hasDataSpecification == null)
                    hasDataSpecification = new HasDataSpecification();
                var r = new Reference();
                r.Keys.Add(k);
                hasDataSpecification.reference.Add(r);
            }

            public void AddContainedElement(Key k)
            {
                if (containedElements == null)
                    containedElements = new ContainedElements();
                var r = new ContainedElementRef();
                r.Keys.Add(k);
                containedElements.reference.Add(r);
            }

            public void AddContainedElement(List<Key> keys)
            {
                if (containedElements == null)
                    containedElements = new ContainedElements();
                var r = new ContainedElementRef();
                foreach (var k in keys)
                    r.Keys.Add(k);
                containedElements.reference.Add(r);
            }

            public void AddContainedElement(Reference r)
            {
                if (containedElements == null)
                    containedElements = new ContainedElements();
                containedElements.reference.Add(ContainedElementRef.FromReference(r));
            }

            public void AddContainedElement(List<Reference> rlist)
            {
                if (containedElements == null)
                    containedElements = new ContainedElements();
                foreach (var r in rlist)
                    containedElements.reference.Add(ContainedElementRef.FromReference(r));
            }

            public override string GetElementName()
            {
                return "View";
            }

            public Tuple<string, string> ToCaptionInfo()
            {
                var caption = AdminShellUtil.EvalToNonNullString("\"{0}\" ", idShort, "<no idShort!>");
                var info = "";
                if (this.semanticId != null)
                    info = Key.KeyListToString(this.semanticId.Keys);
                if (this.containedElements != null && this.containedElements.reference != null)
                    info = (info + " ").Trim() + String.Format("({0} elements)", this.containedElements.reference.Count);
                return Tuple.Create(caption, info);
            }

            public override string ToString()
            {
                var ci = ToCaptionInfo();
                return string.Format("{0}{1}", ci.Item1, (ci.Item2 != "") ? " / " + ci.Item2 : "");
            }
        }

        public class Views
        {
            [XmlElement(ElementName = "view")]
            public List<View> views = new List<View>();
        }

        public class LangStringIEC61360
        {

            // members

            [XmlElement(ElementName = "langString", Namespace = "http://www.admin-shell.io/aas/1/0")]
            public List<LangStr> langString = new List<LangStr>();

            // getters / setters

            public bool IsEmpty { get { return langString == null || langString.Count < 1; } }
            public int Count { get { if (langString == null) return 0; return langString.Count; } }
            public LangStr this[int index] { get { return langString[index]; } }

            // constructors

            public LangStringIEC61360() { }

            public LangStringIEC61360(LangStringIEC61360 src)
            {
                if (src.langString != null)
                    foreach (var ls in src.langString)
                        this.langString.Add(new LangStr(ls));
            }

        }

        public class UnitId
        {

            // members

            [XmlElement(ElementName = "keys")]
            public KeyList keys = new KeyList();

            // getter / setters

            public List<Key> Keys { get { if (keys == null) return null; return keys.Keys; } }
            public bool IsEmpty { get { return keys == null || keys.IsEmpty; } }
            public int Count { get { if (keys == null) return 0; return keys.Count; } }
            public Key this[int index] { get { return keys.Keys[index]; } }

            // constructors / creators

            public UnitId() { }

            public UnitId(UnitId src)
            {
                if (src.keys != null)
                    foreach (var k in src.Keys)
                        this.keys.Add(new Key(k));
            }

            public static UnitId CreateNew(string type, bool local, string idType, string value)
            {
                var u = new UnitId();
                u.keys.Keys.Add(Key.CreateNew(type, local, idType, value));
                return u;
            }
        }

        [XmlRoot(Namespace = "http://www.admin-shell.io/IEC61360/1/0")]
        public class DataSpecificationIEC61360
        {

            // members

            // public List<LangStr> preferredName = new List<LangStr>();
            public LangStringIEC61360 preferredName = new LangStringIEC61360();
            public string shortName = "";
            public string unit = "";
            public UnitId unitId = null;
            public string valueFormat = null;
            public List<LangStr> sourceOfDefinition = new List<LangStr>();
            public string symbol = null;
            public string dataType = "";
            // public List<LangStr> definition = new List<LangStr>();    
            public LangStringIEC61360 definition = new LangStringIEC61360();

            // getter / setters 

            // constructors

            public DataSpecificationIEC61360() { }

            public DataSpecificationIEC61360(DataSpecificationIEC61360 src)
            {
                if (src.preferredName != null)
                    this.preferredName = new LangStringIEC61360(src.preferredName);
                this.shortName = src.shortName;
                this.unit = src.unit;
                if (src.unitId != null)
                    this.unitId = new UnitId(src.unitId);
                this.valueFormat = src.valueFormat;
                if (src.sourceOfDefinition != null)
                    foreach (var sod in src.sourceOfDefinition)
                        this.sourceOfDefinition.Add(sod);
                this.symbol = src.symbol;
                this.dataType = src.dataType;
                if (src.definition != null)
                    this.definition = new LangStringIEC61360(src.definition);
            }

            public static DataSpecificationIEC61360 CreateNew(
                string[] preferredName = null,
                string shortName = "",
                string unit = "",
                UnitId unitId = null,
                string valueFormat = null,
                string[] sourceOfDefinition = null,
                string symbol = null,
                string dataType = "",
                string[] definition = null
            )
            {
                var d = new DataSpecificationIEC61360();
                if (preferredName != null)
                    d.preferredName.langString = LangStr.CreateManyFromStringArray(preferredName);
                d.shortName = shortName;
                d.unit = unit;
                d.unitId = unitId;
                d.valueFormat = valueFormat;
                if (sourceOfDefinition != null)
                    d.sourceOfDefinition = LangStr.CreateManyFromStringArray(sourceOfDefinition);
                d.symbol = symbol;
                d.dataType = dataType;
                if (definition != null)
                    d.definition.langString = LangStr.CreateManyFromStringArray(definition);
                return (d);
            }
        }

        public class DataSpecificationISO99999
        {
        }

        public class DataSpecificationContent
        {

            // members

            public DataSpecificationIEC61360 dataSpecificationIEC61360 = new DataSpecificationIEC61360();
            public DataSpecificationISO99999 dataSpecificationISO99999 = null;

            // constructors

            public DataSpecificationContent() { }

            public DataSpecificationContent(DataSpecificationContent src)
            {
                if (src.dataSpecificationIEC61360 != null)
                    this.dataSpecificationIEC61360 = new DataSpecificationIEC61360(src.dataSpecificationIEC61360);
            }
        }

        public class EmbeddedDataSpecification
        {
            // members

            public DataSpecificationRef hasDataSpecification = new DataSpecificationRef();
            public DataSpecificationContent dataSpecificationContent = new DataSpecificationContent();

            // constructors

            public EmbeddedDataSpecification() { }

            public EmbeddedDataSpecification(EmbeddedDataSpecification src)
            {
                if (src.hasDataSpecification != null)
                    this.hasDataSpecification = new DataSpecificationRef(src.hasDataSpecification);
                if (src.dataSpecificationContent != null)
                    this.dataSpecificationContent = new DataSpecificationContent(src.dataSpecificationContent);
            }
        }

        public class ConceptDescription : Identifiable, System.IDisposable
        {
            // members

            // do this in order to be IDisposable, that is: suitable for (using)
            void System.IDisposable.Dispose() { }
            public void GetData() { }
            // from HasDataSpecification
            [XmlElement(ElementName = "embeddedDataSpecification")]
            public EmbeddedDataSpecification embeddedDataSpecification = new EmbeddedDataSpecification();

            // old
            // [XmlElement(ElementName="conceptDefinitionRef")]
            // public Reference conceptDefinitionRef = null ;

            // this class
            [XmlIgnore]
            private List<Reference> isCaseOf = null;

            // getter / setter

            [XmlElement(ElementName = "isCaseOf")]
            public List<Reference> IsCaseOf
            {
                get { return isCaseOf; }
                set { isCaseOf = value; }
            }

            // constructors / creators

            public ConceptDescription() : base() { }

            public ConceptDescription(ConceptDescription src)
                : base(src)
            {
                if (src.embeddedDataSpecification != null)
                    this.embeddedDataSpecification = new EmbeddedDataSpecification(src.embeddedDataSpecification);
                if (src.isCaseOf != null)
                    foreach (var ico in src.isCaseOf)
                    {
                        if (this.isCaseOf == null)
                            this.isCaseOf = new List<Reference>();
                        this.isCaseOf.Add(new Reference(ico));
                    }
            }

            public static ConceptDescription CreateNew(string idType, string id, string version = null, string revision = null)
            {
                var cd = new ConceptDescription();
                cd.identification.idType = idType;
                cd.identification.id = id;
                if (version != null)
                {
                    if (cd.administration == null)
                        cd.administration = new Administration();
                    cd.administration.version = version;
                    cd.administration.revision = revision;
                }
                return (cd);
            }

            public ConceptDescriptionRef GetReference()
            {
                var r = new ConceptDescriptionRef();
                r.Keys.Add(Key.CreateNew(this.GetElementName(), true, this.identification.idType, this.identification.id));
                return r;
            }

            public Key GetGlobalDataSpecRef()
            {
                if (embeddedDataSpecification.hasDataSpecification.Count != 1)
                    return null;
                return (embeddedDataSpecification.hasDataSpecification[0]);
            }

            public void SetIEC61360Spec(
                string[] preferredNames = null,
                string shortName = "",
                string unit = "",
                UnitId unitId = null,
                string valueFormat = null,
                string[] sourceOfDefinition = null,
                string symbol = null,
                string dataType = "",
                string[] definition = null
            )
            {
                this.embeddedDataSpecification = new EmbeddedDataSpecification();
                this.embeddedDataSpecification.hasDataSpecification.Keys.Add(Key.CreateNew("GlobalReference", false, "URI", "www.admin-shell.io/DataSpecificationTemplates/DataSpecificationIEC61360"));
                this.embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360 = AdminShell.DataSpecificationIEC61360.CreateNew(
                    preferredNames, shortName, unit, unitId, valueFormat, sourceOfDefinition, symbol, dataType, definition
                );
                this.AddIsCaseOf(Reference.CreateNew(new Key("ConceptDescription", false, this.identification.idType, this.identification.id)));
            }

            public string GetShortName()
            {
                if (embeddedDataSpecification != null && embeddedDataSpecification.dataSpecificationContent != null && embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360 != null)
                    return embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360.shortName;
                return "";
            }

            public override string GetElementName()
            {
                return "ConceptDescription";
            }

            public Tuple<string, string> ToCaptionInfo()
            {
                var caption = "";
                if (this.idShort != null && this.idShort.Trim() != "")
                    caption = $"\"{this.idShort.Trim()}\"";
                if (this.identification != null)
                    caption = (caption + " " + this.identification).Trim();

                var info = "";
                if (embeddedDataSpecification != null && embeddedDataSpecification.dataSpecificationContent != null && embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360 != null)
                    info += embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360.shortName;

                return Tuple.Create(caption, info);
            }

            public override string ToString()
            {
                var ci = ToCaptionInfo();
                return string.Format("{0}{1}", ci.Item1, (ci.Item2 != "") ? " / " + ci.Item2 : "");
            }

            public void AddIsCaseOf(Reference ico)
            {
                if (isCaseOf == null)
                    isCaseOf = new List<Reference>();
                isCaseOf.Add(ico);
            }
        }

        public class ConceptDictionary : Referable
        {
            [XmlElement(ElementName = "conceptDescriptions")]
            public ConceptDescriptionRefs conceptDescriptionsRefs = null;

            public static ConceptDictionary CreateNew(string idShort = null)
            {
                var d = new ConceptDictionary();
                if (idShort != null)
                    d.idShort = idShort;
                return (d);
            }

            public void AddReference(Reference r)
            {
                var cdr = (ConceptDescriptionRef)(ConceptDescriptionRef.CreateNew(r.Keys));
                if (conceptDescriptionsRefs == null)
                    conceptDescriptionsRefs = new ConceptDescriptionRefs();
                conceptDescriptionsRefs.conceptDescriptions.Add(cdr);
            }

            public override string GetElementName()
            {
                return "ConceptDictionary";
            }
        }

        [XmlRoot(ElementName = "aasenv", Namespace = "http://www.admin-shell.io/aas/1/0")]
        public class AdministrationShellEnv
        {
            // [XmlElement(ElementName="assetAdministrationShells")]
            [XmlIgnore] // will be ignored, anyway
            private List<AdministrationShell> administrationShells = new List<AdministrationShell>();
            [XmlIgnore] // will be ignored, anyway
            private List<Asset> assets = new List<Asset>();
            [XmlIgnore] // will be ignored, anyway
            private List<Submodel> submodels = new List<Submodel>();
            [XmlIgnore] // will be ignored, anyway
            private List<ConceptDescription> conceptDescriptions = new List<ConceptDescription>();

            // getter / setters

            [XmlArray("assetAdministrationShells")]
            [XmlArrayItem("assetAdministrationShell")]
            public List<AdministrationShell> AdministrationShells
            {
                get { return administrationShells; }
                set { administrationShells = value; }
            }

            [XmlArray("assets")]
            [XmlArrayItem("asset")]
            public List<Asset> Assets
            {
                get { return assets; }
                set { assets = value; }
            }

            [XmlArray("submodels")]
            [XmlArrayItem("submodel")]
            public List<Submodel> Submodels
            {
                get { return submodels; }
                set { submodels = value; }
            }

            [XmlArray("conceptDescriptions")]
            [XmlArrayItem("conceptDescription")]
            public List<ConceptDescription> ConceptDescriptions
            {
                get { return conceptDescriptions; }
                set { conceptDescriptions = value; }
            }

            // constructor / creators

            public Asset FindAsset(AssetRef aref)
            {
                // trivial
                if (aref == null)
                    return null;
                // can only refs with 1 key
                if (aref.Count != 1)
                    return null;
                // and we're picky
                var key = aref[0];
                if (!key.local || key.type.ToLower().Trim() != "asset")
                    return null;
                // brute force
                foreach (var a in assets)
                    if (a.identification.idType.ToLower().Trim() == key.idType.ToLower().Trim()
                        && a.identification.id.ToLower().Trim() == key.value.ToLower().Trim())
                        return a;
                // uups 
                return null;
            }

            public Submodel FindSubmodel(SubmodelRef smref)
            {
                // trivial
                if (smref == null)
                    return null;
                // can only refs with 1 key
                if (smref.Count != 1)
                    return null;
                // and we're picky
                var key = smref.Keys[0];
                if (!key.local || key.type.ToLower().Trim() != "submodel")
                    return null;
                // brute force
                foreach (var sm in submodels)
                    if (sm.identification.idType.ToLower().Trim() == key.idType.ToLower().Trim()
                        && sm.identification.id.ToLower().Trim() == key.value.ToLower().Trim())
                        return sm;
                // uups 
                return null;
            }

            public ConceptDescription FindConceptDescription(List<Key> keys)
            {
                // trivial
                if (keys == null)
                    return null;
                // can only refs with 1 key
                if (keys.Count != 1)
                    return null;
                // and we're picky
                var key = keys[0];
                if (!key.local || key.type.ToLower().Trim() != "conceptdescription")
                    return null;
                // brute force
                foreach (var cd in conceptDescriptions)
                    if (cd.identification.idType.ToLower().Trim() == key.idType.ToLower().Trim()
                        && cd.identification.id.ToLower().Trim() == key.value.ToLower().Trim())
                        return cd;
                // uups 
                return null;
            }

            public ConceptDescription FindConceptDescription(Key key)
            {
                if (key == null)
                    return null;
                var l = new List<Key>();
                l.Add(key);
                return (FindConceptDescription(l));
            }

            private void CopyConceptDescriptionsFrom(AdministrationShellEnv srcEnv, SubmodelElement src, bool shallowCopy = false)
            {
                // access
                if (srcEnv == null || src == null || src.semanticId == null)
                    return;
                // check for this SubmodelElement in Surce
                var cdSrc = srcEnv.FindConceptDescription(src.semanticId.Keys);
                if (cdSrc == null)
                    return;
                // check for this SubmodelElement in Destnation (this!)
                var cdDest = this.FindConceptDescription(src.semanticId.Keys);
                if (cdDest != null)
                    return;
                // copy new
                this.ConceptDescriptions.Add(new ConceptDescription(cdSrc));
                // recurse?
                if (!shallowCopy && src is SubmodelElementCollection)
                    foreach (var m in (src as SubmodelElementCollection).value)
                        CopyConceptDescriptionsFrom(srcEnv, m.submodelElement, shallowCopy);

            }

            public SubmodelElementWrapper CopySubmodelElementAndCD(AdministrationShellEnv srcEnv, SubmodelElement srcElem, bool copyCD = false, bool shallowCopy = false)
            {
                // access
                if (srcEnv == null || srcElem == null)
                    return null;

                // 1st result pretty easy (calling function will add this to the appropriate Submodel)
                var res = new SubmodelElementWrapper(srcElem);

                // copy the CDs..
                if (copyCD)
                    CopyConceptDescriptionsFrom(srcEnv, srcElem, shallowCopy);

                // give back
                return res;
            }

            public SubmodelRef CopySubmodelRefAndCD(AdministrationShellEnv srcEnv, SubmodelRef srcSubRef, bool copySubmodel = false, bool copyCD = false, bool shallowCopy = false)
            {
                // access
                if (srcEnv == null || srcSubRef == null)
                    return null;

                // need to have the source Submodel
                var srcSub = srcEnv.FindSubmodel(srcSubRef);
                if (srcSub == null)
                    return null;

                // 1st result pretty easy (calling function will add this to the appropriate AAS)
                var dstSubRef = new SubmodelRef(srcSubRef);

                // maybe we need the Submodel in our environment, as well
                var dstSub = this.FindSubmodel(dstSubRef);
                if (dstSub == null && copySubmodel)
                {
                    dstSub = new Submodel(srcSub, shallowCopy);
                    this.Submodels.Add(dstSub);
                }
                else
                {
                    // there is already an submodel, just add members
                    if (!shallowCopy && srcSub.submodelElements != null)
                    {
                        if (dstSub.submodelElements == null)
                            dstSub.submodelElements = new List<SubmodelElementWrapper>();
                        foreach (var smw in srcSub.submodelElements)
                            dstSub.submodelElements.Add(new SubmodelElementWrapper(smw.submodelElement, shallowCopy));
                    }
                }

                // copy the CDs..
                if (copyCD && srcSub.submodelElements != null)
                    foreach (var smw in srcSub.submodelElements)
                        CopyConceptDescriptionsFrom(srcEnv, smw.submodelElement, shallowCopy);

                // give back
                return dstSubRef;
            }
        }

        //
        // Submodel + Submodel elements
        //

        public interface IGetReference
        {
            Reference GetReference();
        }

        public class Qualifier
        {

            // member

            // from hasSemantics:
            [XmlElement(ElementName = "semanticId")]
            public SemanticId semanticId = null;
            // this class
            public string qualifierType = null;
            public string qualifierValue = null;

            // constructors

            public Qualifier() { }

            public Qualifier(Qualifier src)
            {
                if (src.semanticId != null)
                    this.semanticId = new SemanticId(src.semanticId);
                this.qualifierType = src.qualifierType;
                this.qualifierValue = src.qualifierValue;
            }
        }

        public class SubmodelElement : Referable, System.IDisposable, IGetReference
        {

            // members

            // do this in order to be IDisposable, that is: suitable for (using)
            void System.IDisposable.Dispose() { }
            public void GetData() { }
            // from hasDataSpecification:
            [XmlElement(ElementName = "hasDataSpecification")]
            public HasDataSpecification hasDataSpecification = null;
            // from hasSemantics:
            [XmlElement(ElementName = "semanticId")]
            public SemanticId semanticId = null;
            // from hasKind:
            [XmlElement(ElementName = "kind")]
            public Kind kind = null;
            // from Qualifiable:
            public List<Qualifier> qualifier = null;

            // getter / setter

            // constructors / creators

            public SubmodelElement()
                : base() { }

            public SubmodelElement(SubmodelElement src)
                : base(src)
            {
                if (src.hasDataSpecification != null)
                    hasDataSpecification = new HasDataSpecification(src.hasDataSpecification);
                if (src.semanticId != null)
                    semanticId = new SemanticId(src.semanticId);
                if (src.kind != null)
                    kind = new Kind(src.kind);
                if (src.qualifier != null)
                    foreach (var q in src.qualifier)
                        qualifier.Add(new Qualifier(q));
            }

            public void CreateNewLogic(string idShort = null, string category = null, Key semanticIdKey = null)
            {
                if (idShort != null)
                    this.idShort = idShort;
                if (category != null)
                    this.category = category;
                if (semanticIdKey != null)
                {
                    if (this.semanticId == null)
                        this.semanticId = new SemanticId();
                    this.semanticId.Keys.Add(semanticIdKey);
                }
            }

            public void AddQualifier(string qualifierType = null, string qualifierValue = null, KeyList semanticKeys = null)
            {
                if (this.qualifier == null)
                    this.qualifier = new List<Qualifier>();
                var q = new Qualifier();
                q.qualifierType = qualifierType;
                q.qualifierValue = qualifierValue;
                q.semanticId = SemanticId.CreateFromKeys(semanticKeys.Keys);
                this.qualifier.Add(q);
            }

            public override string GetElementName()
            {
                return "SubmodelElement";
            }

            public Reference GetReference()
            {
                Reference r = new Reference();
                // this is the tail of our referencing chain ..
                r.Keys.Add(Key.CreateNew(GetElementName(), true, "idShort", this.idShort));
                // try to climb up ..
                var current = this.parent;
                while (current != null)
                {
                    if (current is Identifiable)
                    {
                        // add big information set
                        r.Keys.Insert(0, Key.CreateNew(
                            current.GetElementName(),
                            true,
                            (current as Identifiable).identification.idType,
                            (current as Identifiable).identification.id));
                    }
                    else
                    {
                        // reference via idShort
                        r.Keys.Insert(0, Key.CreateNew(
                            current.GetElementName(),
                            true,
                            "idShort", this.idShort));
                    }
                    current = current.parent;
                }
                return r;
            }

            public Tuple<string, string> ToCaptionInfo()
            {
                var caption = AdminShellUtil.EvalToNonNullString("\"{0}\" ", idShort, "<no idShort!>");
                var info = "";
                if (semanticId != null)
                    AdminShellUtil.EvalToNonEmptyString("\u21e8 {0}", semanticId.ToString(), "");
                return Tuple.Create(caption, info);
            }

            public override string ToString()
            {
                var ci = ToCaptionInfo();
                return string.Format("{0}{1}", ci.Item1, (ci.Item2 != "") ? " / " + ci.Item2 : "");
            }


        }

        [XmlType(TypeName = "submodelElement")]
        public class SubmodelElementWrapper
        {

            // members

            [XmlElement(ElementName = "property", Type = typeof(Property))]
            [XmlElement(ElementName = "file", Type = typeof(File))]
            [XmlElement(ElementName = "blob", Type = typeof(Blob))]
            [XmlElement(ElementName = "referenceElement", Type = typeof(ReferenceElement))]
            [XmlElement(ElementName = "relationshipElement", Type = typeof(RelationshipElement))]
            [XmlElement(ElementName = "submodelElementCollection", Type = typeof(SubmodelElementCollection))]
            public SubmodelElement submodelElement;

            // constructors

            public SubmodelElementWrapper() { }

            /*
            public SubmodelElementWrapper(SubmodelElementWrapper src, bool shallowCopy = false)
            {
                if (src.submodelElement is Property)
                    this.submodelElement = new Property(src.submodelElement as Property);
                if (src.submodelElement is File)
                    this.submodelElement = new File(src.submodelElement as File);
                if (src.submodelElement is Blob)
                    this.submodelElement = new Blob(src.submodelElement as Blob);
                if (src.submodelElement is ReferenceElement)
                    this.submodelElement = new ReferenceElement(src.submodelElement as ReferenceElement);
                if (src.submodelElement is RelationshipElement)
                    this.submodelElement = new RelationshipElement(src.submodelElement as RelationshipElement);
                if (src.submodelElement is SubmodelElementCollection)
                    this.submodelElement = new SubmodelElementCollection(src.submodelElement as SubmodelElementCollection, shallowCopy: shallowCopy);
            }
            */

public SubmodelElementWrapper(SubmodelElement src, bool shallowCopy = false)
            {
                if (src is Property)
                    this.submodelElement = new Property(src as Property);
                if (src is File)
                    this.submodelElement = new File(src as File);
                if (src is Blob)
                    this.submodelElement = new Blob(src as Blob);
                if (src is ReferenceElement)
                    this.submodelElement = new ReferenceElement(src as ReferenceElement);
                if (src is RelationshipElement)
                    this.submodelElement = new RelationshipElement(src as RelationshipElement);
                if (src is SubmodelElementCollection)
                    this.submodelElement = new SubmodelElementCollection(src as SubmodelElementCollection, shallowCopy: shallowCopy);
            }

        }

        public class Submodel : Identifiable, System.IDisposable, IGetReference
        {

            // members

            // do this in order to be IDisposable, that is: suitable for (using)
            void System.IDisposable.Dispose() { }
            public void GetData() { }
            // from hasDataSpecification:
            [XmlElement(ElementName = "hasDataSpecification")]
            public HasDataSpecification hasDataSpecification = null;
            // from hasSemanticId:
            [XmlElement(ElementName = "semanticId")]
            public SemanticId semanticId = new SemanticId();
            [XmlElement(ElementName = "kind")]
            public Kind kind = new Kind();

            // from this very class        
            public List<SubmodelElementWrapper> submodelElements = null;

            // getter / setter

            // constructors / creators

            public Submodel() : base() { }

            public Submodel(Submodel src, bool shallowCopy = false)
                : base(src)
            {
                if (src.hasDataSpecification != null)
                    this.hasDataSpecification = new HasDataSpecification(src.hasDataSpecification);
                if (src.semanticId != null)
                    this.semanticId = new SemanticId(src.semanticId);
                if (src.kind != null)
                    this.kind = new Kind(src.kind);
                if (!shallowCopy && src.submodelElements != null)
                {
                    if (this.submodelElements == null)
                        this.submodelElements = new List<SubmodelElementWrapper>();
                    foreach (var smw in src.submodelElements)
                        this.submodelElements.Add(new SubmodelElementWrapper(smw.submodelElement, shallowCopy));
                }
            }

            public static Submodel CreateNew(string idType, string id, string version = null, string revision = null)
            {
                var s = new Submodel();
                s.identification.idType = idType;
                s.identification.id = id;
                if (version != null)
                {
                    if (s.administration == null)
                        s.administration = new Administration();
                    s.administration.version = version;
                    s.administration.revision = revision;
                }
                return (s);
            }

            public override string GetElementName()
            {
                return "Submodel";
            }

            public Reference GetReference()
            {
                SubmodelRef l = new SubmodelRef();
                l.Keys.Add(Key.CreateNew(this.GetElementName(), true, this.identification.idType, this.identification.id));
                return l;
            }

            public void Add(SubmodelElement se)
            {
                if (submodelElements == null)
                    submodelElements = new List<SubmodelElementWrapper>();
                var sew = new SubmodelElementWrapper();
                se.parent = this; // track parent here!
                sew.submodelElement = se;
                submodelElements.Add(sew);
            }

            public void AddDataSpecification(Key k)
            {
                if (hasDataSpecification == null)
                    hasDataSpecification = new HasDataSpecification();
                var r = new Reference();
                r.Keys.Add(k);
                hasDataSpecification.reference.Add(r);
            }

            public Tuple<string, string> ToCaptionInfo()
            {
                var caption = AdminShellUtil.EvalToNonNullString("\"{0}\" ", idShort, "<no idShort!>");
                var info = "";
                if (identification != null)
                    info = $"[{identification.idType}, {identification.id}]";
                return Tuple.Create(caption, info);
            }

            public override string ToString()
            {
                var ci = ToCaptionInfo();
                return string.Format("{0}{1}", ci.Item1, (ci.Item2 != "") ? " / " + ci.Item2 : "");
            }

            private static void SetParentsForSME(Referable parent, SubmodelElement se)
            {
                se.parent = parent;
                var smc = se as SubmodelElementCollection;
                if (smc != null)
                    foreach (var sme in smc.value)
                        SetParentsForSME(se, sme.submodelElement);
            }

            public void SetAllParents()
            {
                foreach (var sme in this.submodelElements)
                    SetParentsForSME(this, sme.submodelElement);
            }

        }

        //
        // Derived from SubmodelElements
        //

        public class DataElement : SubmodelElement
        {

            public DataElement() { }

            public DataElement(DataElement src)
                : base(src)
            { }

            public override string GetElementName()
            {
                return "DataElement";
            }
        }

        public class Property : DataElement
        {

            // members

            public string valueType = "";
            public string value = "";
            public Reference valueId = null;

            // constructors

            public Property() { }

            public Property(Property src)
                : base(src)
            {
                this.valueType = src.value;
                this.value = src.value;
                if (src.valueId != null)
                    src.valueId = new Reference(src.valueId);
            }

            public static Property CreateNew(string idShort = null, string category = null, Key semanticIdKey = null)
            {
                var x = new Property();
                x.CreateNewLogic(idShort, category, semanticIdKey);
                return (x);
            }

            public void Set(string valueType = "", string value = "")
            {
                this.valueType = valueType;
                this.value = value;
            }

            public void Set(string type, bool local, string idType, string value)
            {
                this.valueId = Reference.CreateNew(Key.CreateNew(type, local, idType, value));
            }

            public override string GetElementName()
            {
                return "Property";
            }
        }

        public class Blob : DataElement
        {

            // members

            public string mimeType = "";
            public string value = "";

            // constructors

            public Blob() { }

            public Blob(Blob src)
                : base(src)
            {
                this.mimeType = src.mimeType;
                this.value = src.value;
            }

            public static Blob CreateNew(string idShort = null, string category = null, Key semanticIdKey = null)
            {
                var x = new Blob();
                x.CreateNewLogic(idShort, category, semanticIdKey);
                return (x);
            }

            public void Set(string mimeType = "", string value = "")
            {
                this.mimeType = mimeType;
                this.value = value;
            }

            public override string GetElementName()
            {
                return "Blob";
            }

        }

        public class File : DataElement
        {

            // members

            public string mimeType = "";
            public string value = "";

            // constructors

            public File() { }

            public File(File src)
                : base(src)
            {
                this.mimeType = src.mimeType;
                this.value = src.value;
            }

            public static File CreateNew(string idShort = null, string category = null, Key semanticIdKey = null)
            {
                var x = new File();
                x.CreateNewLogic(idShort, category, semanticIdKey);
                return (x);
            }

            public void Set(string mimeType = "", string value = "")
            {
                this.mimeType = mimeType;
                this.value = value;
            }

            public override string GetElementName()
            {
                return "File";
            }

            public static string[] GetPopularMimeTypes()
            {
                return
                    new string[] {
                    System.Net.Mime.MediaTypeNames.Text.Plain,
                    System.Net.Mime.MediaTypeNames.Text.Xml,
                    "application/json",
                    "application/rdf+xml",
                    System.Net.Mime.MediaTypeNames.Application.Pdf,
                    System.Net.Mime.MediaTypeNames.Image.Jpeg,
                    "image/png",
                    System.Net.Mime.MediaTypeNames.Image.Gif,
                    "application/iges",
                    "application/step"
                    };
            }
        }

        public class ReferenceElement : DataElement
        {

            // members

            public Reference value = new Reference();

            // constructors

            public ReferenceElement() { }

            public ReferenceElement(ReferenceElement src)
                : base(src)
            {
                if (src.value != null)
                    this.value = new Reference(src.value);
            }

            public static ReferenceElement CreateNew(string idShort = null, string category = null, Key semanticIdKey = null)
            {
                var x = new ReferenceElement();
                x.CreateNewLogic(idShort, category, semanticIdKey);
                return (x);
            }

            public void Set(Reference value = null)
            {
                this.value = value;
            }

            public override string GetElementName()
            {
                return "ReferenceElement";
            }

        }

        public class RelationshipElement : DataElement
        {
            // members

            public Reference first = new Reference();
            public Reference second = new Reference();

            // constructors

            public RelationshipElement() { }

            public RelationshipElement(RelationshipElement src)
                : base(src)
            {
                if (src.first != null)
                    this.first = new Reference(src.first);
                if (src.second != null)
                    this.second = new Reference(src.second);
            }

            public static RelationshipElement CreateNew(string idShort = null, string category = null, Key semanticIdKey = null)
            {
                var x = new RelationshipElement();
                x.CreateNewLogic(idShort, category, semanticIdKey);
                return (x);
            }

            public void Set(Reference first = null, Reference second = null)
            {
                this.first = first;
                this.second = second;
            }

            public override string GetElementName()
            {
                return "RelationshipElement";
            }
        }

        public class SubmodelElementCollection : SubmodelElement
        {

            // members

            public List<SubmodelElementWrapper> value = new List<SubmodelElementWrapper>();
            public bool ordered = false;
            public bool allowDuplicates = false;

            // constructors

            public SubmodelElementCollection() { }

            public SubmodelElementCollection(SubmodelElementCollection src, bool shallowCopy = false)
                : base(src)
            {
                this.ordered = src.ordered;
                this.allowDuplicates = src.allowDuplicates;
                if (!shallowCopy)
                    foreach (var smw in src.value)
                        value.Add(new SubmodelElementWrapper(smw.submodelElement));
            }

            public static SubmodelElementCollection CreateNew(string idShort = null, string category = null, Key semanticIdKey = null)
            {
                var x = new SubmodelElementCollection();
                x.CreateNewLogic(idShort, category, semanticIdKey);
                return (x);
            }

            public void Set(bool allowDuplicates = false, bool ordered = false)
            {
                this.allowDuplicates = allowDuplicates;
                this.ordered = ordered;
            }

            public void Add(SubmodelElement se)
            {
                if (value == null)
                    value = new List<SubmodelElementWrapper>();
                var sew = new SubmodelElementWrapper();
                se.parent = this; // track parent here!
                sew.submodelElement = se;
                value.Add(sew);
            }

            public override string GetElementName()
            {
                return "SubmodelElementCollection";
            }
        }

        //
        // Handling of packages
        //

        public class PackageSupplementaryFile : Referable
        {
            public enum LocationType { InPackage, AddPending, DeletePending }

            public enum SpecialHandlingType { None, EmbedAsThumbnail }

            public Uri uri = null;
            public string sourcePath = null;
            public LocationType location = LocationType.InPackage;
            public SpecialHandlingType specialHandling = SpecialHandlingType.None;

            public PackageSupplementaryFile(Uri uri, string sourcePath = null, LocationType location = LocationType.InPackage, SpecialHandlingType specialHandling = SpecialHandlingType.None)
            {
                this.uri = uri;
                this.sourcePath = sourcePath;
                this.location = location;
                this.specialHandling = specialHandling;
            }

            // class derives from Referable in order to provide GetElementName
            public override string GetElementName()
            {
                return "File";
            }

        }

        public class PackageEnv
        {
            private string fn = "New Package";
            private AdministrationShellEnv aasenv = new AdministrationShellEnv();
            private Package openPackage = null;
            private List<PackageSupplementaryFile> pendingFilesToAdd = new List<PackageSupplementaryFile>();
            private List<PackageSupplementaryFile> pendingFilesToDelete = new List<PackageSupplementaryFile>();

            public PackageEnv()
            {
            }

            public PackageEnv(AdministrationShellEnv env)
            {
                if (env != null)
                    this.aasenv = env;
            }

            public PackageEnv(string fn)
            {
                Load(fn);
            }

            public bool IsOpen
            {
                get
                {
                    return openPackage != null;
                }
            }

            public string Filename
            {
                get
                {
                    return fn;
                }
            }

            public AdminShell.AdministrationShellEnv AasEnv
            {
                get
                {
                    return aasenv;
                }
            }

            public bool Load(string fn)
            {
                this.fn = fn;
                if (this.openPackage != null)
                    this.openPackage.Close();
                this.openPackage = null;

                if (fn.ToLower().EndsWith(".xml"))
                {
                    // load only XML
                    try
                    {
                        XmlSerializer serializer = new XmlSerializer(typeof(AdminShell.AdministrationShellEnv), "http://www.admin-shell.io/aas/1/0");
                        TextReader reader = new StreamReader(fn);
                        this.aasenv = serializer.Deserialize(reader) as AdminShell.AdministrationShellEnv;
                        if (this.aasenv == null)
                            throw (new Exception("Type error for XML file!"));
                        reader.Close();
                    }
                    catch (Exception ex)
                    {
                        throw (new Exception(string.Format("While reading AAS {0} at {1} gave: {2}", fn, AdminShellUtil.ShortLocation(ex), ex.Message)));
                    }
                    return true;
                }

                if (fn.ToLower().EndsWith(".aasx"))
                {
                    // load package AASX
                    try
                    {
                        var package = Package.Open(fn, FileMode.Open);

                        // get the origin from the package
                        PackagePart originPart = null;
                        var xs = package.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aasx-origin");
                        foreach (var x in xs)
                            if (x.SourceUri.ToString() == "/")
                            {
                                originPart = package.GetPart(x.TargetUri);
                                break;
                            }
                        if (originPart == null)
                            throw (new Exception(string.Format("Unable to find AASX origin. Aborting!")));

                        // get the specs from the package
                        PackagePart specPart = null;
                        xs = originPart.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aas-spec");
                        foreach (var x in xs)
                        {
                            specPart = package.GetPart(x.TargetUri);
                            break;
                        }
                        if (specPart == null)
                            throw (new Exception(string.Format("Unable to find AASX spec(s). Aborting!")));

                        // open spec part to read
                        using (var s = specPart.GetStream(FileMode.Open))
                        {
                            // own catch loop to be more specific
                            try
                            {
                                XmlSerializer serializer = new XmlSerializer(typeof(AdminShell.AdministrationShellEnv), "http://www.admin-shell.io/aas/1/0");
                                this.aasenv = serializer.Deserialize(s) as AdminShell.AdministrationShellEnv;
                                this.openPackage = package;
                                if (this.aasenv == null)
                                    throw (new Exception("Type error for XML file!"));
                                s.Close();
                            }
                            catch (Exception ex)
                            {
                                throw (new Exception(string.Format("While reading AAS {0} spec at {1} gave: {2}", fn, AdminShellUtil.ShortLocation(ex), ex.Message)));
                            }
                        }
                    }
                    catch (Exception ex)
                    {
                        throw (new Exception(string.Format("While reading AASX {0} at {1} gave: {2}", fn, AdminShellUtil.ShortLocation(ex), ex.Message)));
                    }
                    return true;
                }

                // Don't know to handle
                throw (new Exception(string.Format($"Not able to handle {fn}.")));
            }

            public bool SaveAs(string fn, bool writeFreshly = false)
            {

                if (this.fn.ToLower().EndsWith(".xml"))
                {
                    // save only XML
                    this.fn = fn;
                    try
                    {
                        using (var s = new StreamWriter(this.fn))
                        {
                            var serializer = new XmlSerializer(typeof(AdminShell.AdministrationShellEnv));
                            var nss = new XmlSerializerNamespaces();
                            nss.Add("aas", "http://www.admin-shell.io/aas/1/0");
                            nss.Add("IEC61360", "http://www.admin-shell.io/IEC61360/1/0");
                            serializer.Serialize(s, this.aasenv, nss);
                        }
                    }
                    catch (Exception ex)
                    {
                        throw (new Exception(string.Format("While writing AAS {0} at {1} gave: {2}", fn, AdminShellUtil.ShortLocation(ex), ex.Message)));
                    }
                    return true;
                }

                if (fn.ToLower().EndsWith(".aasx"))
                {
                    // save package AASX
                    try
                    {
                        // we want existing contents to be preserved, but no possiblity to change file name
                        // therefore: copy file to new name, re-open!
                        // fn could be changed, therefore close "old" package first
                        if (this.openPackage != null)
                        {
                            try
                            {
                                this.openPackage.Close();
                                if (!writeFreshly)
                                    System.IO.File.Copy(this.fn, fn);
                            }
                            catch { }
                            this.openPackage = null;
                        }

                        // approach is to utilize the existing package, if possible. If not, create from scratch
                        var package = Package.Open(fn, (writeFreshly) ? FileMode.Create : FileMode.OpenOrCreate);
                        this.fn = fn;

                        // get the origin from the package
                        PackagePart originPart = null;
                        var xs = package.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aasx-origin");
                        foreach (var x in xs)
                            if (x.SourceUri.ToString() == "/")
                            {
                                originPart = package.GetPart(x.TargetUri);
                                break;
                            }
                        if (originPart == null)
                        {
                            // create, as not existing
                            originPart = package.CreatePart(new Uri("/aasx/aasx-origin", UriKind.RelativeOrAbsolute), System.Net.Mime.MediaTypeNames.Text.Plain, CompressionOption.Maximum);
                            using (var s = originPart.GetStream(FileMode.Create))
                            {
                                var bytes = System.Text.Encoding.ASCII.GetBytes("Intentionally empty.");
                                s.Write(bytes, 0, bytes.Length);
                            }
                            package.CreateRelationship(originPart.Uri, TargetMode.Internal, "http://www.admin-shell.io/aasx/relationships/aasx-origin");
                        }

                        // get the specs from the package
                        PackagePart specPart = null;
                        xs = originPart.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aas-spec");
                        foreach (var x in xs)
                        {
                            specPart = package.GetPart(x.TargetUri);
                            break;
                        }

                        if (specPart == null)
                        {
                            // create, as not existing
                            var frn = "aasenv-with-no-id";
                            if (this.aasenv.AdministrationShells.Count > 0)
                                frn = this.aasenv.AdministrationShells[0].GetFriendlyName() ?? frn;
                            var aas_spec_fn = "/aasx/#/#.aas.xml".Replace("#", "" + frn);
                            specPart = package.CreatePart(new Uri(aas_spec_fn, UriKind.RelativeOrAbsolute), System.Net.Mime.MediaTypeNames.Text.Xml, CompressionOption.Maximum);
                            originPart.CreateRelationship(specPart.Uri, TargetMode.Internal, "http://www.admin-shell.io/aasx/relationships/aas-spec");
                        }

                        // now, shall be != null!
                        using (var s = specPart.GetStream(FileMode.Create))
                        {
                            var serializer = new XmlSerializer(typeof(AdminShell.AdministrationShellEnv));
                            var nss = new XmlSerializerNamespaces();
                            nss.Add("aas", "http://www.admin-shell.io/aas/1/0");
                            nss.Add("IEC61360", "http://www.admin-shell.io/IEC61360/1/0");
                            serializer.Serialize(s, this.aasenv, nss);
                        }

                        // there might be pending files to be deleted (first delete, then add, in case of identical files in both categories)
                        foreach (var psfDel in pendingFilesToDelete)
                        {
                            // try find an existing part for that file ..
                            var found = false;
                            xs = specPart.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aas-suppl");
                            foreach (var x in xs)
                                if (x.TargetUri == psfDel.uri)
                                {
                                    // try to delete
                                    specPart.DeleteRelationship(x.Id);
                                    package.DeletePart(psfDel.uri);
                                    found = true;
                                    break;
                                }
                            if (!found)
                                throw (new Exception($"Not able to delete pending file {psfDel.uri} in saving package {fn}"));
                        }

                        // after this, there are no more pending for delete files
                        pendingFilesToDelete.Clear();

                        // write pending supplementary files
                        foreach (var psfAdd in pendingFilesToAdd)
                        {
                            // make sure ..
                            if (psfAdd.sourcePath == null || psfAdd.location != PackageSupplementaryFile.LocationType.AddPending)
                                continue;

                            // normal file?
                            if (psfAdd.specialHandling == PackageSupplementaryFile.SpecialHandlingType.None)
                            {

                                // try find an existing part for that file ..
                                PackagePart filePart = null;
                                xs = specPart.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aas-suppl");
                                foreach (var x in xs)
                                    if (x.TargetUri == psfAdd.uri)
                                    {
                                        filePart = package.GetPart(x.TargetUri);
                                        break;
                                    }

                                if (filePart == null)
                                {
                                    // create new part and link
                                    filePart = package.CreatePart(psfAdd.uri, AdminShell.PackageEnv.GuessMimeType(psfAdd.sourcePath), CompressionOption.Maximum);
                                    specPart.CreateRelationship(filePart.Uri, TargetMode.Internal, "http://www.admin-shell.io/aasx/relationships/aas-suppl");
                                }

                                // now should be able to write
                                using (var s = filePart.GetStream(FileMode.Create))
                                {
                                    var bytes = System.IO.File.ReadAllBytes(psfAdd.sourcePath);
                                    s.Write(bytes, 0, bytes.Length);
                                }
                            }

                            // thumbnail file?
                            if (psfAdd.specialHandling == PackageSupplementaryFile.SpecialHandlingType.EmbedAsThumbnail)
                            {
                                // try find an existing part for that file ..
                                PackagePart filePart = null;
                                xs = package.GetRelationshipsByType("http://schemas.openxmlformats.org/package/2006/relationships/metadata/thumbnail");
                                foreach (var x in xs)
                                    if (x.SourceUri.ToString() == "/" && x.TargetUri == psfAdd.uri)
                                    {
                                        filePart = package.GetPart(x.TargetUri);
                                        break;
                                    }

                                if (filePart == null)
                                {
                                    // create new part and link
                                    filePart = package.CreatePart(psfAdd.uri, AdminShell.PackageEnv.GuessMimeType(psfAdd.sourcePath), CompressionOption.Maximum);
                                    package.CreateRelationship(filePart.Uri, TargetMode.Internal, "http://schemas.openxmlformats.org/package/2006/relationships/metadata/thumbnail");
                                }

                                // now should be able to write
                                using (var s = filePart.GetStream(FileMode.Create))
                                {
                                    var bytes = System.IO.File.ReadAllBytes(psfAdd.sourcePath);
                                    s.Write(bytes, 0, bytes.Length);
                                }
                            }
                        }

                        // after this, there are no more pending for add files
                        pendingFilesToAdd.Clear();

                        // flush, but leave open
                        package.Flush();
                        this.openPackage = package;
                    }
                    catch (Exception ex)
                    {
                        throw (new Exception(string.Format("While write AASX {0} at {1} gave: {2}", fn, AdminShellUtil.ShortLocation(ex), ex.Message)));
                    }
                    return true;
                }

                // Don't know to handle
                throw (new Exception(string.Format($"Not able to handle {fn}.")));
            }

            public Stream GetLocalStreamFromPackage(string uriString)
            {
                // access
                if (this.openPackage == null)
                    throw (new Exception(string.Format($"AASX Package {this.fn} not opened. Aborting!")));
                var part = this.openPackage.GetPart(new Uri(uriString, UriKind.RelativeOrAbsolute));
                if (part == null)
                    throw (new Exception(string.Format($"Cannot access URI {uriString} in {this.fn} not opened. Aborting!")));
                return part.GetStream(FileMode.Open);
            }

            public Stream GetLocalThumbnailStream()
            {
                // access
                if (this.openPackage == null)
                    throw (new Exception(string.Format($"AASX Package {this.fn} not opened. Aborting!")));
                // get the thumbnail over the relationship
                PackagePart thumbPart = null;
                var xs = this.openPackage.GetRelationshipsByType("http://schemas.openxmlformats.org/package/2006/relationships/metadata/thumbnail");
                foreach (var x in xs)
                    if (x.SourceUri.ToString() == "/")
                    {
                        thumbPart = this.openPackage.GetPart(x.TargetUri);
                        break;
                    }
                if (thumbPart == null)
                    throw (new Exception(string.Format("Unable to find AASX thumbnail. Aborting!")));
                return thumbPart.GetStream(FileMode.Open);
            }

            public List<PackageSupplementaryFile> GetListOfSupplementaryFiles()
            {
                // new result
                var result = new List<PackageSupplementaryFile>();

                // access
                if (this.openPackage != null)
                {

                    // get the thumbnail(s) from the package
                    var xs = this.openPackage.GetRelationshipsByType("http://schemas.openxmlformats.org/package/2006/relationships/metadata/thumbnail");
                    foreach (var x in xs)
                        if (x.SourceUri.ToString() == "/")
                        {
                            result.Add(new PackageSupplementaryFile(
                                x.TargetUri,
                                location: PackageSupplementaryFile.LocationType.InPackage,
                                specialHandling: PackageSupplementaryFile.SpecialHandlingType.EmbedAsThumbnail));
                        }

                    // get the origin from the package
                    PackagePart originPart = null;
                    xs = this.openPackage.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aasx-origin");
                    foreach (var x in xs)
                        if (x.SourceUri.ToString() == "/")
                        {
                            originPart = this.openPackage.GetPart(x.TargetUri);
                            break;
                        }

                    if (originPart != null)
                    {
                        // get the specs from the origin
                        PackagePart specPart = null;
                        xs = originPart.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aas-spec");
                        foreach (var x in xs)
                        {
                            specPart = this.openPackage.GetPart(x.TargetUri);
                            break;
                        }

                        if (specPart != null)
                        {
                            // get the supplementaries from the package, derived from spec
                            xs = specPart.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aas-suppl");
                            foreach (var x in xs)
                            {
                                result.Add(new PackageSupplementaryFile(x.TargetUri, location: PackageSupplementaryFile.LocationType.InPackage));
                            }
                        }
                    }
                }

                // add or modify the files to delete
                foreach (var psfDel in pendingFilesToDelete)
                {
                    // already in
                    var found = result.Find(x => { return x.uri == psfDel.uri; });
                    if (found != null)
                        found.location = PackageSupplementaryFile.LocationType.DeletePending;
                    else
                    {
                        psfDel.location = PackageSupplementaryFile.LocationType.DeletePending;
                        result.Add(psfDel);
                    }
                }

                // add the files to store as well
                foreach (var psfAdd in pendingFilesToAdd)
                {
                    // already in (should not happen ?!)
                    var found = result.Find(x => { return x.uri == psfAdd.uri; });
                    if (found != null)
                        found.location = PackageSupplementaryFile.LocationType.AddPending;
                    else
                    {
                        psfAdd.location = PackageSupplementaryFile.LocationType.AddPending;
                        result.Add(psfAdd);
                    }
                }

                // done
                return result;
            }

            public static string GuessMimeType(string fn)
            {
                var file_ext = System.IO.Path.GetExtension(fn).ToLower().Trim();
                var content_type = System.Net.Mime.MediaTypeNames.Text.Plain;
                if (file_ext == ".pdf") content_type = System.Net.Mime.MediaTypeNames.Application.Pdf;
                if (file_ext == ".xml") content_type = System.Net.Mime.MediaTypeNames.Text.Xml;
                if (file_ext == ".txt") content_type = System.Net.Mime.MediaTypeNames.Text.Plain;
                if (file_ext == ".xml") content_type = System.Net.Mime.MediaTypeNames.Text.Xml;
                if (file_ext == ".igs") content_type = "application/iges";
                if (file_ext == ".iges") content_type = "application/iges";
                if (file_ext == ".stp") content_type = "application/step";
                if (file_ext == ".step") content_type = "application/step";
                if (file_ext == ".jpg") content_type = System.Net.Mime.MediaTypeNames.Image.Jpeg;
                if (file_ext == ".jpeg") content_type = System.Net.Mime.MediaTypeNames.Image.Jpeg;
                if (file_ext == ".png") content_type = "image/png";
                if (file_ext == ".gig") content_type = System.Net.Mime.MediaTypeNames.Image.Gif;
                return content_type;
            }

            public void AddSupplementaryFileToStore(string sourcePath, string targetDir, string targetFn, bool embedAsThumb)
            {
                // beautify parameters
                sourcePath = sourcePath.Trim();
                targetDir = targetDir.Trim();
                if (!targetDir.EndsWith("/"))
                    targetDir += "/";
                targetDir = targetDir.Replace(@"\", "/");
                targetFn = targetFn.Trim();
                if (sourcePath == "" || targetDir == "" || targetFn == "")
                    throw (new Exception(string.Format("Trying add supplementary file with empty name or path!")));

                var file_fn = "" + targetDir.Trim() + targetFn.Trim();

                // add record
                pendingFilesToAdd.Add(
                    new PackageSupplementaryFile(
                        new Uri(file_fn, UriKind.RelativeOrAbsolute),
                        sourcePath,
                        location: PackageSupplementaryFile.LocationType.AddPending,
                        specialHandling: (embedAsThumb ? PackageSupplementaryFile.SpecialHandlingType.EmbedAsThumbnail : PackageSupplementaryFile.SpecialHandlingType.None)
                    ));
            }

            public void DeleteSupplementaryFile(PackageSupplementaryFile psf)
            {
                if (psf == null)
                    throw (new Exception(string.Format("No supplementary file given!")));

                if (psf.location == PackageSupplementaryFile.LocationType.AddPending)
                {
                    // is still pending in add list -> remove
                    pendingFilesToAdd.RemoveAll((x) => { return x.uri == psf.uri; });
                }

                if (psf.location == PackageSupplementaryFile.LocationType.InPackage)
                {
                    // add to pending delete list
                    pendingFilesToDelete.Add(psf);
                }
            }

            /*
            private void AddSupplementaryFileDirect(string srcFn, string targetDir, string targetFn)
            {
                // access
                if (this.openPackage == null)
                    throw (new Exception(string.Format($"AASX Package {this.fn} not opened. Aborting!")));

                srcFn = srcFn.Trim();
                targetDir = targetDir.Trim();
                if (!targetDir.EndsWith("/"))
                    targetDir += "/";
                targetDir = targetDir.Replace(@"\", "/");
                targetFn = targetFn.Trim();
                if (srcFn == "" || targetDir == "" || targetFn == "")
                    throw (new Exception(string.Format("Trying add supplementary file with empty name or path!")));

                // get the origin from the package
                PackagePart originPart = null;
                var xs = this.openPackage.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aasx-origin");
                foreach (var x in xs)
                    if (x.SourceUri.ToString() == "/")
                    {
                        originPart = this.openPackage.GetPart(x.TargetUri);
                        break;
                    }
                if (originPart == null)
                    throw (new Exception(string.Format("Unable to find AASX origin. Aborting!")));

                // get the specs from the package
                PackagePart specPart = null;
                xs = originPart.GetRelationshipsByType("http://www.admin-shell.io/aasx/relationships/aas-spec");
                foreach (var x in xs)
                {
                    specPart = this.openPackage.GetPart(x.TargetUri);
                    break;
                }
                if (specPart == null)
                    throw (new Exception(string.Format("Unable to find AASX spec(s). Aborting!")));

                // add a supplementary file
                var file_fn = "" + targetDir.Trim() + targetFn.Trim();
                var file_part = this.openPackage.CreatePart(new Uri(file_fn, UriKind.RelativeOrAbsolute), AdminShell.PackageEnv.GuessMimeType(srcFn), CompressionOption.Maximum);
                using (var s = file_part.GetStream(FileMode.Create))
                {
                    var bytes = System.IO.File.ReadAllBytes(srcFn);
                    s.Write(bytes, 0, bytes.Length);
                }
                specPart.CreateRelationship(file_part.Uri, TargetMode.Internal, "http://www.admin-shell.io/aasx/relationships/aas-suppl");
            }
            */

            public void Close()
            {
                if (this.openPackage != null)
                    this.openPackage.Close();
                this.openPackage = null;
                this.fn = "";
                this.aasenv = null;
            }
        }

    }

    #endregion
}