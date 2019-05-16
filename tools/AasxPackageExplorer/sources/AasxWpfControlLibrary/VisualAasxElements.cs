using Logging;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Media;

using AdminShellNS;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AasxPackageExplorer
{
    public class TreeViewLineCache
    {
        public Dictionary<object, bool> IsExpanded = new Dictionary<object, bool>();
    }

    public class VisualElementGeneric : INotifyPropertyChanged
    {
        // bi-directional tree
        public VisualElementGeneric Parent = null;
        public ObservableCollection<VisualElementGeneric> Members { get; set; }

        // cache for expanded states
        public TreeViewLineCache Cache = null;

        // members (some dedicated for list / tree like visualisation
        private bool _isExpanded = false;
        private bool _isExpandedTouched = false;
        private bool _isSelected = false;
        public string TagString { get; set; }
        public string Caption { get; set; }
        public string Info { get; set; }
        public string Value { get; set; }
        public string ValueInfo { get; set; }
        public Brush Background { get; set; }
        public Brush Border { get; set; }
        public Brush TagFg { get; set; }
        public Brush TagBg { get; set; }
        public bool IsTopLevel = false;

        public VisualElementGeneric()
        {
            this.Members = new ObservableCollection<VisualElementGeneric>();
        }

        /// <summary>
        /// Get the main (business) data object, e.g. a submodel
        /// </summary>
        /// <returns></returns>
        public virtual object GetMainDataObject()
        {
            return null;
        }

        /// <summary>
        /// Restores the state of the IsExpanded from an cache. The cache associates with the MainDataObject and therefore survives, even if the the TreeViewLines are completely rebuilt.
        /// </summary>
        public void RestoreFromCache()
        {
            var o = this.GetMainDataObject();
            if (o != null && Cache != null && Cache.IsExpanded.ContainsKey(o))
            {
                this._isExpanded = Cache.IsExpanded[o];
                this._isExpandedTouched = true;
            }
        }

        /// <summary>
        /// For each different sub-class type of TreeViewLineGeneric, this methods refreshes attributes such as Caption and Info. Required, if updates to the MainDataObject shall be reflected on the UI.
        /// </summary>
        public virtual void RefreshFromMainData()
        {
            // to be overloaded for sub-classes!
        }

        /// <summary>
        /// Gets/sets whether the TreeViewItem 
        /// associated with this object is expanded.
        /// </summary>
        public bool IsExpanded
        {
            get { return _isExpanded; }
            set
            {
                if (value != _isExpanded)
                {
                    _isExpanded = value;
                    this.OnPropertyChanged("IsExpanded");
                }

                // store in cache
                var o = this.GetMainDataObject();
                if (o != null && Cache != null)
                    Cache.IsExpanded[o] = value;

                _isExpandedTouched = true;
            }
        }

        public void SetIsExpandedIfNotTouched(bool isExpanded)
        {
            if (!_isExpandedTouched)
                _isExpanded = isExpanded;
        }

        /// <summary>
        /// Gets/sets whether the TreeViewItem 
        /// associated with this object is selected.
        /// </summary>
        public bool IsSelected
        {
            get { return _isSelected; }
            set
            {
                if (value != _isSelected)
                {
                    _isSelected = value;
                    this.OnPropertyChanged("IsSelected");
                }
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;

        protected virtual void OnPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
        }

        //
        //
        //

        private bool SearchForDescendentAndCallIfFound(VisualElementGeneric descendent, Action<VisualElementGeneric> lambda)
        {
            var res = false;
            if (this == descendent)
            {
                if (lambda != null)
                    lambda(this);
                res = true; // meaning: in this sub-tree, descendent was found!
            }
            if (this.Members != null)
                foreach (var mem in this.Members)
                    res = res || mem.SearchForDescendentAndCallIfFound(descendent, lambda);
            return res;
        }

        public void ForAllDescendents(Action<VisualElementGeneric> lambda)
        {
            if (lambda != null)
                lambda(this);
            if (this.Members != null)
                foreach (var mem in this.Members)
                    mem.ForAllDescendents(lambda);
        }

        public void CollectListOfTopLevelNodes(List<VisualElementGeneric> list)
        {
            if (list == null)
                return;
            if (this.IsTopLevel)
            {
                list.Add(this);
                if (this.Members != null)
                    foreach (var mem in this.Members)
                        mem.CollectListOfTopLevelNodes(list);
            }
        }

        public bool CheckIfDescendent(VisualElementGeneric descendent)
        {
            if (descendent == null)
                return false;
            if (this == descendent)
                return true;
            var res = false;
            if (this.Members != null)
                foreach (var mem in this.Members)
                    res = res || mem.CheckIfDescendent(descendent);
            return res;
        }
    }

    public class VisualElementEnvironmentItem : VisualElementGeneric
    {
        public enum ItemType { Env = 0, Shells, Assets, ConceptDescriptions, Package, SupplFiles, EmptySet };
        public static string[] ItemTypeNames = new string[] { "Environment", "AdministrationShells", "Assets", "ConceptDescriptions", "Package", "Supplementary files", "Empty" };

        public AdminShell.PackageEnv thePackage = null;
        public AdminShell.AdministrationShellEnv theEnv = null;
        public ItemType theItemType = ItemType.Env;

        public VisualElementEnvironmentItem(VisualElementGeneric parent, TreeViewLineCache cache, AdminShell.PackageEnv package, AdminShell.AdministrationShellEnv env, ItemType itemType)
        : base()
        {
            this.Parent = parent;
            this.Cache = cache;
            this.thePackage = package;
            this.theEnv = env;
            this.theItemType = itemType;
            this.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#B0B0B0"));
            this.Border = (SolidColorBrush)(new BrushConverter().ConvertFrom("#404040"));
            this.Caption = $"\"{ItemTypeNames[(int)itemType]}\"";
            this.Info = "";
            this.IsTopLevel = true;
            this.TagString = "Env";
            if (theItemType == ItemType.EmptySet)
            {
                this.TagString = "\u2205";
                this.Caption = "No information available";
            }
            if (theItemType == ItemType.Package && thePackage != null)
            {
                this.TagString = "\u25a2";
                this.Info += "" + thePackage.Filename;
            }
            this.TagBg = this.Border;
            this.TagFg = Brushes.White;
            RestoreFromCache();
        }

        public static object GiveDataObject(ItemType t)
        {
            return ItemTypeNames[(int)t];
        }

        public override object GetMainDataObject()
        {
            return GiveDataObject(theItemType);
        }

        public override void RefreshFromMainData()
        {
        }

    }

    public class VisualElementAdminShell : VisualElementGeneric
    {
        public AdminShell.AdministrationShellEnv theEnv = null;
        public AdminShell.AdministrationShell theAas = null;

        public VisualElementAdminShell(VisualElementGeneric parent, TreeViewLineCache cache, AdminShell.AdministrationShellEnv env, AdminShell.AdministrationShell aas)
            : base()
        {
            this.Parent = parent;
            this.Cache = cache;
            this.theEnv = env;
            this.theAas = aas;
            this.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#88A6D2"));
            this.Border = (SolidColorBrush)(new BrushConverter().ConvertFrom("#4370B3"));
            this.TagString = "AAS";
            this.TagBg = this.Border;
            this.TagFg = Brushes.White;
            RefreshFromMainData();
            RestoreFromCache();
        }

        public override object GetMainDataObject()
        {
            return theAas;
        }

        public override void RefreshFromMainData()
        {
            if (theAas != null)
            {
                var ci = theAas.ToCaptionInfo();
                this.Caption = ci.Item1;
                this.Info = ci.Item2;
                var asset = theEnv.FindAsset(theAas.assetRef);
                if (asset != null)
                    this.Info += $" of [{asset.identification.idType}, {asset.identification.id}, {asset.kind.kind}]";
            }
        }
    }

    public class VisualElementAsset : VisualElementGeneric
    {
        public AdminShell.AdministrationShellEnv theEnv = null;
        public AdminShell.Asset theAsset = null;

        public VisualElementAsset(VisualElementGeneric parent, TreeViewLineCache cache, AdminShell.AdministrationShellEnv env, AdminShell.Asset asset)
            : base()
        {
            this.Parent = parent;
            this.Cache = cache;
            this.theEnv = env;
            this.theAsset = asset;
            this.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#B0B0B0"));
            this.Border = (SolidColorBrush)(new BrushConverter().ConvertFrom("#404040"));
            this.TagString = "Asset";
            this.TagBg = this.Border;
            this.TagFg = Brushes.White;
            RefreshFromMainData();
            RestoreFromCache();
        }

        public override object GetMainDataObject()
        {
            return theAsset;
        }

        public override void RefreshFromMainData()
        {
            if (theAsset != null)
            {
                var ci = theAsset.ToCaptionInfo();
                this.Caption = "" + ci.Item1;
                this.Info = ci.Item2;
            }
        }
    }

    public class VisualElementSubmodelRef : VisualElementGeneric
    {
        public AdminShell.AdministrationShellEnv theEnv = null;
        public AdminShell.SubmodelRef theSubmodelRef = null;
        public AdminShell.Submodel theSubmodel = null;

        public VisualElementSubmodelRef(VisualElementGeneric parent, TreeViewLineCache cache, AdminShell.AdministrationShellEnv env, AdminShell.SubmodelRef smr, AdminShell.Submodel sm)
            : base()
        {
            this.Parent = parent;
            this.Cache = cache;
            this.theEnv = env;
            this.theSubmodelRef = smr;
            this.theSubmodel = sm;
            this.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#CBD8EB"));
            this.Border = (SolidColorBrush)(new BrushConverter().ConvertFrom("#4370B3"));
            this.TagString = "Sub";
            this.TagBg = this.Border;
            this.TagFg = Brushes.White;
            RefreshFromMainData();
            RestoreFromCache();
        }

        public override object GetMainDataObject()
        {
            return theSubmodelRef;
        }

        public override void RefreshFromMainData()
        {
            if (theSubmodel != null)
            {
                var ci = theSubmodel.ToCaptionInfo();
                this.Caption = "" + ci.Item1;
                this.Info = ci.Item2;
            }
        }

    }

    public class VisualElementView : VisualElementGeneric
    {
        public AdminShell.AdministrationShellEnv theEnv = null;
        public AdminShell.View theView = null;

        public VisualElementView(VisualElementGeneric parent, TreeViewLineCache cache, AdminShell.AdministrationShellEnv env, AdminShell.View vw)
            : base()
        {
            this.Parent = parent;
            this.Cache = cache;
            this.theEnv = env;
            this.theView = vw;
            this.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#C0C0C0"));
            this.Border = (SolidColorBrush)(new BrushConverter().ConvertFrom("#404040"));
            this.TagString = "View";
            this.TagBg = this.Border;
            this.TagFg = Brushes.White;
            RefreshFromMainData();
            RestoreFromCache();
        }

        public override object GetMainDataObject()
        {
            return theView;
        }

        public override void RefreshFromMainData()
        {
            if (theView != null)
            {
                var ci = theView.ToCaptionInfo();
                this.Caption = "" + ci.Item1;
                this.Info = ci.Item2;
            }
        }

    }

    public class VisualElementReference : VisualElementGeneric
    {
        public AdminShell.AdministrationShellEnv theEnv = null;
        public AdminShell.Reference theReference = null;

        public VisualElementReference(VisualElementGeneric parent, TreeViewLineCache cache, AdminShell.AdministrationShellEnv env, AdminShell.Reference rf)
            : base()
        {
            this.Parent = parent;
            this.Cache = cache;
            this.theEnv = env;
            this.theReference = rf;
            this.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#D0D0D0"));
            this.Border = (SolidColorBrush)(new BrushConverter().ConvertFrom("#606060"));
            this.TagString = "\u2b95";
            this.TagBg = this.Border;
            this.TagFg = Brushes.White;
            RefreshFromMainData();
            RestoreFromCache();
        }

        public override object GetMainDataObject()
        {
            return theReference;
        }

        public override void RefreshFromMainData()
        {
            if (theReference != null && theReference.Keys != null)
            {
                this.Caption = "";
                this.Info = "";
                foreach (var x in theReference.Keys)
                {
                    if (x == null)
                        continue;
                    if (this.Info != "") this.Info += "/ ";
                    this.Info += x.value;
                }
            }
        }

    }

    public class VisualElementSubmodelElement : VisualElementGeneric
    {
        public AdminShell.AdministrationShellEnv theEnv = null;
        public AdminShell.Referable theContainer = null;
        public AdminShell.SubmodelElementWrapper theWrapper = null;

        private AdminShell.ConceptDescription cachedCD = null;

        public VisualElementSubmodelElement(VisualElementGeneric parent, TreeViewLineCache cache, AdminShell.AdministrationShellEnv env, AdminShell.Referable parentContainer, AdminShell.SubmodelElementWrapper wrap)
            : base()
        {
            this.Parent = parent;
            this.Cache = cache;
            this.theEnv = env;
            this.theContainer = parentContainer;
            this.theWrapper = wrap;
            this.Background = Brushes.White;
            this.Border = Brushes.White;

            this.TagString = "Elem";
            if (wrap != null && wrap.submodelElement != null)
            {
                if (wrap.submodelElement is AdminShell.Property) this.TagString = "Prop";
                if (wrap.submodelElement is AdminShell.File) this.TagString = "File";
                if (wrap.submodelElement is AdminShell.Blob) this.TagString = "Blob";
                if (wrap.submodelElement is AdminShell.ReferenceElement) this.TagString = "Ref";
                if (wrap.submodelElement is AdminShell.RelationshipElement) this.TagString = "Rel";
                if (wrap.submodelElement is AdminShell.SubmodelElementCollection) this.TagString = "Coll";
            }

            this.TagBg = (SolidColorBrush)(new BrushConverter().ConvertFrom("#707070")); ;
            this.TagFg = Brushes.White;
            RefreshFromMainData();
            RestoreFromCache();
        }

        public override object GetMainDataObject()
        {
            return theWrapper.submodelElement;
        }

        public override void RefreshFromMainData()
        {
            if (theWrapper != null)
            {
                var sme = theWrapper.submodelElement;
                var ci = sme.ToCaptionInfo();
                this.Caption = "" + ci.Item1;
                this.Info = ci.Item2;

                if (sme is AdminShell.Property)
                {
                    var smep = sme as AdminShell.Property;
                    if (smep.value != null && smep.value != "")
                        this.Info += "= " + smep.value;
                    else if (smep.valueId != null && !smep.valueId.IsEmpty)
                        this.Info += "<= " + smep.valueId.ToString();

                    // cache ConceptDescription?
                    if (sme.semanticId != null && sme.semanticId.Keys != null)
                    {
                        if (this.cachedCD == null)
                            this.cachedCD = this.theEnv.FindConceptDescription(sme.semanticId.Keys);
                        if (this.cachedCD != null && this.cachedCD.embeddedDataSpecification != null
                            && this.cachedCD.embeddedDataSpecification.dataSpecificationContent != null
                            && this.cachedCD.embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360 != null)
                        {
                            var iecprop = this.cachedCD.embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360;
                            if (iecprop.unit != null && iecprop.unit != "")
                                this.Info += " [" + iecprop.unit + "]";
                        }
                    }
                }

                if (sme is AdminShell.File)
                {
                    var smef = sme as AdminShell.File;
                    if (smef.value != null && smef.value != "")
                        this.Info += "-> " + smef.value;
                }

                if (sme is AdminShell.ReferenceElement)
                {
                    var smere = sme as AdminShell.ReferenceElement;
                    if (smere.value != null && !smere.value.IsEmpty)
                        this.Info += "~> " + smere.value.ToString();
                }

                if (sme is AdminShell.SubmodelElementCollection)
                {
                    var smesec = sme as AdminShell.SubmodelElementCollection;
                    if (smesec.value != null)
                        this.Info += "(" + smesec.value.Count + " elements)";
                }
            }
        }

    }

    public class VisualElementConceptDescription : VisualElementGeneric
    {
        public AdminShell.AdministrationShellEnv theEnv = null;
        public AdminShell.ConceptDescription theCD = null;

        public VisualElementConceptDescription(VisualElementGeneric parent, TreeViewLineCache cache, AdminShell.AdministrationShellEnv env, AdminShell.ConceptDescription cd)
            : base()
        {
            this.Parent = parent;
            this.Cache = cache;
            this.theEnv = env;
            this.theCD = cd;
            this.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#D0D0D0"));
            this.Border = (SolidColorBrush)(new BrushConverter().ConvertFrom("#606060"));
            this.TagString = "CD";
            this.TagBg = (SolidColorBrush)(new BrushConverter().ConvertFrom("#707070")); ;
            this.TagFg = Brushes.White;
            RefreshFromMainData();
            RestoreFromCache();
        }

        public override object GetMainDataObject()
        {
            return theCD;
        }

        public override void RefreshFromMainData()
        {
            if (theCD != null)
            {
                var ci = theCD.ToCaptionInfo();
                this.Caption = "" + ci.Item1 + " ";
                this.Info = ci.Item2;
            }
        }

    }

    public class VisualElementSupplementalFile : VisualElementGeneric
    {
        public AdminShell.PackageEnv thePackage = null;
        public AdminShell.PackageSupplementaryFile theFile = null;

        public VisualElementSupplementalFile(VisualElementGeneric parent, TreeViewLineCache cache, AdminShell.PackageEnv package, AdminShell.PackageSupplementaryFile sf)
            : base()
        {
            this.Parent = parent;
            this.Cache = cache;
            this.thePackage = package;
            this.theFile = sf;
            this.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#D0D0D0"));
            this.Border = (SolidColorBrush)(new BrushConverter().ConvertFrom("#606060"));
            this.TagString = "\u25a4";
            this.TagBg = (SolidColorBrush)(new BrushConverter().ConvertFrom("#707070")); ;
            this.TagFg = Brushes.White;
            RefreshFromMainData();
            RestoreFromCache();
        }

        public override object GetMainDataObject()
        {
            return theFile;
        }

        public override void RefreshFromMainData()
        {
            if (theFile != null)
            {
                this.Caption = "" + theFile.uri.ToString();
                this.Info = "";

                if (theFile.location == AdminShell.PackageSupplementaryFile.LocationType.AddPending)
                    this.Info += "(add pending) ";
                if (theFile.location == AdminShell.PackageSupplementaryFile.LocationType.DeletePending)
                    this.Info += "(delete pending) ";
                if (theFile.sourcePath != null)
                    this.Info += "\u2b60 " + theFile.sourcePath;

                if (theFile.specialHandling == AdminShell.PackageSupplementaryFile.SpecialHandlingType.EmbedAsThumbnail)
                    this.Info += " [Thumbnail]";
            }
        }

    }

    //
    // Generators
    //

    public class Generators
    {
        public static void GenerateVisualElementsFromShellEnvAddElements(TreeViewLineCache cache, AdminShell.AdministrationShellEnv env, VisualElementGeneric parent, AdminShell.Referable parentContainer, AdminShell.SubmodelElementWrapper el)
        {
            var ti = new VisualElementSubmodelElement(parent, cache, env, parentContainer, el);
            parent.Members.Add(ti);
            var elc = el.submodelElement as AdminShell.SubmodelElementCollection;
            if (elc != null)
                foreach (var elcc in elc.value)
                    GenerateVisualElementsFromShellEnvAddElements(cache, env, ti, elc, elcc);
        }

        public static List<VisualElementGeneric> GenerateVisualElementsFromShellEnv(TreeViewLineCache cache, AdminShell.AdministrationShellEnv env, AdminShell.PackageEnv package = null, bool editMode = false, int expandMode = 0)
        {
            // clear tree
            var res = new List<VisualElementGeneric>();
            // valid?
            if (env == null)
                return res;

            // need some attach points
            VisualElementEnvironmentItem tiPackage = null, tiEnv = null, tiShells = null, tiAssets = null, tiCDs = null;

            // many operytions -> make it bulletproof
            try
            {

                if (editMode)
                {
                    // package
                    tiPackage = new VisualElementEnvironmentItem(null /* Parent */, cache, package, env, VisualElementEnvironmentItem.ItemType.Package);
                    tiPackage.SetIsExpandedIfNotTouched(true);
                    res.Add(tiPackage);

                    // env
                    tiEnv = new VisualElementEnvironmentItem(tiPackage, cache, package, env, VisualElementEnvironmentItem.ItemType.Env);
                    tiEnv.SetIsExpandedIfNotTouched(expandMode > 0);
                    tiPackage.Members.Add(tiEnv);

                    // shells
                    tiShells = new VisualElementEnvironmentItem(tiEnv, cache, package, env, VisualElementEnvironmentItem.ItemType.Shells);
                    tiShells.SetIsExpandedIfNotTouched(expandMode > 0);
                    tiEnv.Members.Add(tiShells);

                    // assets
                    tiAssets = new VisualElementEnvironmentItem(tiEnv, cache, package, env, VisualElementEnvironmentItem.ItemType.Assets);
                    tiAssets.SetIsExpandedIfNotTouched(expandMode > 0);
                    tiEnv.Members.Add(tiAssets);

                    // concept descriptions
                    tiCDs = new VisualElementEnvironmentItem(tiEnv, cache, package, env, VisualElementEnvironmentItem.ItemType.ConceptDescriptions);
                    tiCDs.SetIsExpandedIfNotTouched(expandMode > 0);
                    tiEnv.Members.Add(tiCDs);
                }

                // over all Admin shells
                foreach (var aas in env.AdministrationShells)
                {
                    // item
                    var tiAas = new VisualElementAdminShell(null, cache, env, aas);
                    tiAas.SetIsExpandedIfNotTouched(expandMode > 0);

                    // add item
                    if (editMode)
                    {
                        tiAas.Parent = tiShells;
                        tiShells.Members.Add(tiAas);
                    }
                    else
                    {
                        res.Add(tiAas);
                    }

                    // have submodels?
                    if (aas.submodelRefs != null)
                        foreach (var smr in aas.submodelRefs)
                        {
                            var sm = env.FindSubmodel(smr);
                            if (sm == null)
                            {
                                Log.Error("Cannot find some submodel!");
                                continue;
                            }
                            // item
                            var tiSm = new VisualElementSubmodelRef(tiAas, cache, env, smr, sm);
                            tiSm.SetIsExpandedIfNotTouched(expandMode > 1);
                            // recursively into the submodel elements
                            if (sm.submodelElements != null)
                                foreach (var sme in sm.submodelElements)
                                    GenerateVisualElementsFromShellEnvAddElements(cache, env, tiSm, sm, sme);
                            // add
                            tiAas.Members.Add(tiSm);
                        }

                    // have views?
                    if (aas.views != null && aas.views.views != null)
                        foreach (var vw in aas.views.views)
                        {
                            // item
                            var tiVw = new VisualElementView(tiAas, cache, env, vw);
                            tiVw.SetIsExpandedIfNotTouched(expandMode > 1);
                            // recursion -> submodel elements
                            if (vw.containedElements != null && vw.containedElements.reference != null)
                                foreach (var ce in vw.containedElements.reference)
                                {
                                    var tiRf = new VisualElementReference(tiVw, cache, env, ce);
                                    tiVw.Members.Add(tiRf);
                                }
                            // add
                            tiAas.Members.Add(tiVw);
                        }
                }

                // if edit mode, then display further ..
                if (editMode)
                {
                    // over all assets
                    foreach (var asset in env.Assets)
                    {
                        // item
                        var tiAsset = new VisualElementAsset(tiAssets, cache, env, asset);
                        tiAssets.Members.Add(tiAsset);
                    }

                    // over all concept descriptions
                    foreach (var cd in env.ConceptDescriptions)
                    {
                        // item
                        var tiCD = new VisualElementConceptDescription(tiCDs, cache, env, cd);
                        tiCDs.Members.Add(tiCD);
                    }
                }

                // package as well?
                if (editMode && package != null && tiPackage != null)
                {
                    // file folder
                    var tiFiles = new VisualElementEnvironmentItem(tiPackage, cache, package, env, VisualElementEnvironmentItem.ItemType.SupplFiles);
                    tiFiles.SetIsExpandedIfNotTouched(expandMode > 0);
                    tiPackage.Members.Add(tiFiles);

                    // single files
                    var files = package.GetListOfSupplementaryFiles();
                    foreach (var fi in files)
                        tiFiles.Members.Add(new VisualElementSupplementalFile(tiFiles, cache, package, fi));
                }

            } catch (Exception ex)
            {
                Log.Error(ex, "Generating tree of visual elements");
            }

            // end
            return res;

        }

    }

    }
