using Logging;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

using AdminShellNS;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AasxPackageExplorer
{
    /// <summary>
    /// Interaktionslogik für DispEditAasEntity.xaml
    /// </summary>
    public partial class DispEditAasxEntity : UserControl
    {

        private AdminShell.PackageEnv thePackage = null;
        private VisualElementGeneric theEntity = null;
        private bool theEditMode = false;

        private ModifyRepo theModifyRepo = new ModifyRepo();

        private DispEditHelper helper = new DispEditHelper();

        #region Public events and properties
        //
        // Public events and properties
        //

        public DispEditAasxEntity()
        {
            InitializeComponent();
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            // Timer for below
            System.Windows.Threading.DispatcherTimer dispatcherTimer = new System.Windows.Threading.DispatcherTimer();
            dispatcherTimer.Tick += new EventHandler(dispatcherTimer_Tick);
            dispatcherTimer.Interval = new TimeSpan(0, 0, 0, 0, 100);
            dispatcherTimer.Start();
        }

        private void dispatcherTimer_Tick(object sender, EventArgs e)
        {
            // check for wishes from the modify repo
            if (theModifyRepo != null && theModifyRepo.WishForOutsideAction != null)
            {
                while (theModifyRepo.WishForOutsideAction.Count > 0)
                {
                    var temp = theModifyRepo.WishForOutsideAction[0];
                    theModifyRepo.WishForOutsideAction.RemoveAt(0);

                    // what?
                    if (temp is ModifyRepo.LambdaActionRedrawEntity)
                        if (thePackage != null && theEntity != null)
                            DisplayOrEditVisualAasxElement(thePackage, theEntity, theEditMode, auxPackages: helper.auxPackages, flyoutProvider: helper.flyoutProvider);
                    if (temp is ModifyRepo.LambdaActionRedrawAllElements
                        || temp is ModifyRepo.LambdaActionContentsChanged
                        || temp is ModifyRepo.LambdaActionContentsTakeOver)
                        // twice as ugly :-(
                        this.WishForOutsideAction.Add(temp);
                }
            }
        }

        private void ContentUndo_Click(object sender, RoutedEventArgs e)
        {
            if (theModifyRepo != null)
                theModifyRepo.CallUndoChanges();
        }

        public List<ModifyRepo.LambdaAction> WishForOutsideAction = new List<ModifyRepo.LambdaAction>();

        public void CallUndo()
        {
            if (theModifyRepo != null)
                theModifyRepo.CallUndoChanges();
        }

        #endregion


        #region Element View Drawing

        //
        //
        // --- Asset
        //
        //

        public void DisplayOrEditAasEntityAsset(AdminShell.PackageEnv package, AdminShell.AdministrationShellEnv env, AdminShell.Asset asset, bool editMode, ModifyRepo repo, StackPanel stack, Brush[][] levelColors, bool embedded = false, bool hintMode = false)
        {
            helper.AddGroup(stack, "Asset", levelColors[0][0], levelColors[0][1]);

            // Up/ down/ del
            if (editMode && !embedded)
            {
                helper.EntityListUpDownDeleteHelper<AdminShell.Asset>(stack, repo, env.Assets, asset, env, "Asset:");
            }

            // Referable
            helper.AddGroup(stack, "Referable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return asset.idShort == null || asset.idShort.Length < 1; }, 
                    "idShort is mandatory for assets. It is a short, unique identifier that is unique just in its context, its name space. ", breakIfTrue: true),
                new HintCheck( () => { if (asset.idShort == null) return false; return AdminShellUtil.HasWhitespace(asset.idShort); }, 
                    "idShort shall not contain whitespace." )
            });
            helper.AddKeyValue(stack, "idShort", asset.idShort, null, repo, v => { asset.idShort = v as string; return new ModifyRepo.LambdaActionNone(); });

            helper.AddHintBubble(stack, hintMode, new HintCheck( () => { return asset.category != null && asset.category.Trim().Length >= 1; }, "The use of category is unusual here.", severityLevel: HintCheck.Severity.Notice) );
            helper.AddKeyValue(stack, "category", asset.category, null, repo, v => { asset.category = v as string; return new ModifyRepo.LambdaActionNone(); },
                comboBoxItems: AdminShell.Referable.ReferableCategoryNames, comboBoxIsEditable: true);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck(() => { return asset.description == null || asset.description.langString == null || asset.description.langString.Count < 1;  },
                    "The use of an description is recommended to allow the consumer of an asset to understand the nature of it.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck(() => { return asset.description.langString.Count < 2; },
                    "Consider having description in multiple langauges.", severityLevel: HintCheck.Severity.Notice)
            });
            if (helper.SafeguardAccess(stack, repo, asset.description, "description:", "Create data element!", v =>
            {
                asset.description = new AdminShell.Description();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddHintBubble(stack, hintMode, new HintCheck(() => { return asset.description.langString == null || asset.description.langString.Count < 1; }, "Please add some descriptions in your main languages here to help consumers of your Administration shell to understand your intentions."));
                helper.AddKeyListLangStr(stack, "description", asset.description.langString, repo);
            }

            // hasDataSpecification are MULTIPLE references. That is: multiple x multiple keys!
            if (helper.SafeguardAccess(stack, repo, asset.hasDataSpecification, "HasDataSpecification:", "Create data element!", v => {
                asset.hasDataSpecification = new AdminShell.HasDataSpecification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "HasDataSpecification", levelColors[1][0], levelColors[1][1]);

                if (editMode)
                {
                    // let the user control the number of references
                    helper.AddAction(stack, "Specifications:", new string[] { "Add Reference", "Delete last reference" }, repo, (buttonNdx) =>
                    {
                        if (buttonNdx is int)
                        {
                            if ((int)buttonNdx == 0)
                                asset.hasDataSpecification.reference.Add(new AdminShell.Reference());

                            if ((int)buttonNdx == 1 && asset.hasDataSpecification.reference.Count > 0)
                                asset.hasDataSpecification.reference.RemoveAt(asset.hasDataSpecification.reference.Count - 1);
                        }
                        return new ModifyRepo.LambdaActionRedrawEntity();
                    });
                }

                // now use the normal mechanism to deal with editMode or not ..                    
                if (asset.hasDataSpecification != null && asset.hasDataSpecification.reference != null && asset.hasDataSpecification.reference.Count > 0)
                {
                    for (int i = 0; i < asset.hasDataSpecification.reference.Count; i++)
                        helper.AddKeyListKeys(stack, String.Format("reference[{0}]", i), asset.hasDataSpecification.reference[i].Keys, repo, package, "All");
                }
            }

            // Identifiable

            helper.AddGroup(stack, "Identifiable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return asset.identification == null; },
                    "Providing a worldwide unique identification is mandatory.", breakIfTrue: true),
                new HintCheck( () => { return asset.identification.idType != "URI"; },
                    "Check if identification type is correct. Use of URIs is usual here.", severityLevel: HintCheck.Severity.Notice ),
                new HintCheck( () => { return asset.identification.id.Trim() == ""; },
                    "Identification id shall not be empty. You could use the 'Generate' button in order to generate a worldwide unique id. The template of this id could be set by commandline arguments." )

            });
            if (helper.SafeguardAccess(stack, repo, asset.identification, "identification:", "Create data element!", v => {
                asset.identification = new AdminShell.Identification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddKeyValue(stack, "idType", asset.identification.idType, null, repo, v => { asset.identification.idType = v as string; return new ModifyRepo.LambdaActionNone(); },
                    comboBoxItems: AdminShell.Key.IdentifierTypeNames);
                helper.AddKeyValue(stack, "id", asset.identification.id, null, repo, v => { asset.identification.id = v as string; return new ModifyRepo.LambdaActionNone(); },
                    auxButtonTitle: "Generate", auxButtonLambda: v => {
                        asset.identification.idType = "URI";
                        asset.identification.id = Options.GenerateIdAccordingTemplate(Options.TemplateIdAsset);
                        return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: asset);
                    });
            }

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return asset.administration == null; },
                    "Check if providing admistrative information on version/ revision would be useful. This allows for better version management.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck( () => { return asset.administration.version.Trim() == "" || asset.administration.revision.Trim() == ""; },
                    "Admistrative information fields should not be empty.", severityLevel: HintCheck.Severity.Notice )
            });
            if (helper.SafeguardAccess(stack, repo, asset.administration, "administration:", "Create data element!", v => {
                asset.administration = new AdminShell.Administration();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddKeyValue(stack, "version", asset.administration.version, null, repo, v => { asset.administration.version = v as string; return new ModifyRepo.LambdaActionNone(); });
                helper.AddKeyValue(stack, "revision", asset.administration.revision, null, repo, v => { asset.administration.revision = v as string; return new ModifyRepo.LambdaActionNone(); });
            }

            // Kind

            helper.AddGroup(stack, "Kind:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return asset.kind == null; },
                    "Providing kind information is mandatory. Typically you want to model instances. A manufacturer would define types of assets, as well.", breakIfTrue: true),
                new HintCheck( () => { return asset.kind.kind.Trim().ToLower() != "instance"; },
                    "Check for kind setting. 'Instance' is the usual choice.", severityLevel: HintCheck.Severity.Notice )
            });
            if (helper.SafeguardAccess(stack, repo, asset.kind, "kind:", "Create data element!", v => {
                asset.kind = new AdminShell.Kind();
                return new ModifyRepo.LambdaActionRedrawEntity();
            })) helper.AddKeyValue(stack, "kind", asset.kind.kind, null, repo, v => { asset.kind.kind = v as string; return new ModifyRepo.LambdaActionNone(); }, new string[] { "Type", "Instance" });

            // AssetIdentificationModelRef

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return asset == null; },
                    "No asset is associated with this Administration Shell. This might be, because some identification changed. Use 'Add existing' to assign the Administration Shell with an existing asset in the AAS environment or use 'Add blank' to create an arbitray reference."),
            });
            if (helper.SafeguardAccess(stack, repo, asset.assetIdentificationModelRef, "assetIdentificationModelRef:", "Create data element!", v =>
            {
                asset.assetIdentificationModelRef = new AdminShell.SubmodelRef();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "Asset Identification Model Reference", levelColors[1][0], levelColors[1][1]);
                helper.AddKeyListKeys(stack, "assetIdentificationModelRef", asset.assetIdentificationModelRef.Keys, repo, package, "SubmodelRef");
            }
        }

        //
        // 
        // --- AAS Env
        //
        //

        static string PackageSourcePath = "";
        static string PackageTargetFn = "";
        static string PackageTargetDir = "/aasx";
        static bool PackageEmbedAsThumbnail = false;

        public void DisplayOrEditAasEntityAasEnv(AdminShell.PackageEnv package, AdminShell.AdministrationShellEnv env, VisualElementEnvironmentItem.ItemType envItemType, bool editMode, ModifyRepo repo, StackPanel stack, Brush[][] levelColors, bool hintMode = false)
        {
            helper.AddGroup(stack, "Environment of Asset Administration Shells", levelColors[0][0], levelColors[0][1]);

            // automatically and silently fix errors
            if (env.AdministrationShells == null)
                env.AdministrationShells = new List<AdminShell.AdministrationShell>();
            if (env.Assets == null)
                env.Assets = new List<AdminShell.Asset>();
            if (env.ConceptDescriptions == null)
                env.ConceptDescriptions = new List<AdminShell.ConceptDescription>();
            if (env.Submodels == null)
                env.Submodels = new List<AdminShell.Submodel>();

            if (editMode && (envItemType == VisualElementEnvironmentItem.ItemType.Env || envItemType == VisualElementEnvironmentItem.ItemType.Shells
                || envItemType == VisualElementEnvironmentItem.ItemType.Assets || envItemType == VisualElementEnvironmentItem.ItemType.ConceptDescriptions))
            {
                // some hints
                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return env.Assets == null || env.Assets.Count() < 1; },
                        "There are no assets in this AAS environment. You should consider adding an asset by clicking 'Add asset' on the edit panel below."),
                    new HintCheck( () => { return env.AdministrationShells == null || env.AdministrationShells.Count() < 1; },
                        "There are no Administration Shells in this AAS environment. You should consider adding an Administration Shell by clicking 'Add asset' on the edit panel below. Typically, this is done after adding an asset, as the Administration Shell needs to refer to it.", breakIfTrue: true),
                    new HintCheck( () => { return env.Submodels == null || env.Submodels.Count() < 1; },
                        "There are no Submodels in this AAS environment. In this application, Submodels are created by adding them to associated to Administration Shells. Therefore, an Adminstration Shell shall exist before and shall be selected. You could then add Submodels by clicking 'Create new Submodel of kind Type/Instance' on the edit panel. This step is typically done after creating asset and Administration Shell."),
                    new HintCheck( () => { return env.ConceptDescriptions == null || env.ConceptDescriptions.Count() < 1; },
                        "There are no ConceptDescriptions in this AAS environment. Even if SubmodelElements can reference external concept descriptions, it is best practice to include (duplicates of the) concept descriptions inside the AAS environment. You should consider adding a ConceptDescription by clicking 'Add ConceptDescription' on the panel below or adding a SubmodelElement to a Submodel. This step is typically done after creating assets and Administration Shell and when creating SubmodelElements."),
                });

                // let the user control the number of entities
                helper.AddAction(stack, "Entities:", new string[] { "Add Asset", "Add AAS", "Add ConceptDescription" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int)
                    {
                        if ((int)buttonNdx == 0)
                        {
                            var asset = new AdminShell.Asset();
                            env.Assets.Add(asset);
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: asset);
                        }

                        if ((int)buttonNdx == 1)
                        {
                            var aas = new AdminShell.AdministrationShell();
                            env.AdministrationShells.Add(aas);
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: aas);
                        }

                        if ((int)buttonNdx == 2)
                        {
                            var cd = new AdminShell.ConceptDescription();
                            env.ConceptDescriptions.Add(cd);
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: cd);
                        }
                    }
                    return new ModifyRepo.LambdaActionNone();
                });

                // Copy Concept Descriptions
                if (envItemType == VisualElementEnvironmentItem.ItemType.ConceptDescriptions)
                {
                    helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                        new HintCheck( () => { return helper.auxPackages != null;  },
                            "You have opened an auxiliary AASX package. You can copy elements from it!", severityLevel: HintCheck.Severity.Notice)
                    });
                    helper.AddAction(stack, "Copy from existing ConceptDescription:", new string[] { "Copy single entity" }, repo, (buttonNdx) =>
                    {
                        if (buttonNdx is int)
                        {
                            if ((int)buttonNdx == 0)
                            {
                                var rve = helper.SmartSelectAasEntityVisualElement(package.AasEnv, "ConceptDescription", package: package, auxPackages: helper.auxPackages) as VisualElementConceptDescription;
                                if (rve != null)
                                {
                                    var mdo = rve.GetMainDataObject();
                                    if (rve != null && mdo != null && mdo is AdminShell.ConceptDescription)
                                    {
                                        var clone = new AdminShell.ConceptDescription(mdo as AdminShell.ConceptDescription);
                                        if (env.ConceptDescriptions == null)
                                            env.ConceptDescriptions = new List<AdminShell.ConceptDescription>();
                                        env.ConceptDescriptions.Add(clone);
                                        return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: clone);
                                    }
                                }
                            }
                        }
                        return new ModifyRepo.LambdaActionNone();
                    });
                }
            }
            else
            if (envItemType == VisualElementEnvironmentItem.ItemType.SupplFiles && package != null)
            {
                helper.AddGroup(stack, "Supplementary file to add:", levelColors[1][0], levelColors[1][1]);

                var g = helper.AddSmallGrid(5, 3, new string[] { "#", "*", "#" });
                helper.AddSmallLabelTo(g, 0, 0, padding: new Thickness(2, 0, 0, 0), content: "Source path: ");
                repo.RegisterControl(helper.AddSmallTextBoxTo(g, 0, 1, margin: new Thickness(2, 2, 2, 2), text: PackageSourcePath),
                        (o) => {
                            if (o is string)
                                PackageSourcePath = o as string;
                            return new ModifyRepo.LambdaActionNone();
                        });
                repo.RegisterControl(helper.AddSmallButtonTo(g, 0, 2, margin: new Thickness(2, 2, 2, 2), padding: new Thickness(5, 0, 5, 0), content: "Select"),
                        (o) => {
                            var dlg = new Microsoft.Win32.OpenFileDialog();
                            var res = dlg.ShowDialog();
                            if (res == true)
                            {
                                PackageSourcePath = dlg.FileName;
                                PackageTargetFn = System.IO.Path.GetFileName(dlg.FileName);
                                PackageTargetFn = PackageTargetFn.Replace(" ", "_");
                            }
                            return new ModifyRepo.LambdaActionRedrawEntity();
                        });
                helper.AddSmallLabelTo(g, 1, 0, padding: new Thickness(2, 0, 0, 0), content: "Target filename: ");
                repo.RegisterControl(helper.AddSmallTextBoxTo(g, 1, 1, margin: new Thickness(2, 2, 2, 2), text: PackageTargetFn),
                        (o) => {
                            if (o is string)
                                PackageTargetFn = o as string;
                            return new ModifyRepo.LambdaActionNone();
                        });
                helper.AddSmallLabelTo(g, 2, 0, padding: new Thickness(2, 0, 0, 0), content: "Target path: ");
                repo.RegisterControl(helper.AddSmallTextBoxTo(g, 2, 1, margin: new Thickness(2, 2, 2, 2), text: PackageTargetDir),
                        (o) => {
                            if (o is string)
                                PackageTargetDir = o as string;
                            return new ModifyRepo.LambdaActionNone();
                        });
                repo.RegisterControl(helper.AddSmallCheckBoxTo(g, 3, 1, margin: new Thickness(2, 2, 2, 2), content: "Embed as thumbnail (only one file per package!)", isChecked: PackageEmbedAsThumbnail),
                        (o) => {
                            if (o is bool)
                                PackageEmbedAsThumbnail = (bool)o;
                            return new ModifyRepo.LambdaActionNone();
                        });
                repo.RegisterControl(helper.AddSmallButtonTo(g, 4, 1, margin: new Thickness(2, 2, 2, 2), padding: new Thickness(5, 0, 5, 0), content: "Add file to package"),
                        (o) => {
                            try
                            {
                                var ptd = PackageTargetDir;
                                if (PackageEmbedAsThumbnail)
                                    ptd = "/";
                                package.AddSupplementaryFileToStore(PackageSourcePath, ptd, PackageTargetFn, PackageEmbedAsThumbnail);
                                Log.Info("Added {0} to pending package items. A save-operation is required.", PackageSourcePath);
                            }
                            catch (Exception ex)
                            {
                                Logging.Log.Error(ex, "Adding file to package");
                            }
                            PackageSourcePath = "";
                            PackageTargetFn = "";
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: VisualElementEnvironmentItem.GiveDataObject(VisualElementEnvironmentItem.ItemType.Package));
                        });
                stack.Children.Add(g);
            }
            else
            {
                // Default
                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return env.AdministrationShells.Count < 1; },
                    "There are no AdministrationShell entities in the environment. Select the 'Administration Shells' item on the middle panel and select 'Add AAS' to add a new entity."),
                new HintCheck( () => { return env.Assets.Count < 1; },
                    "There are no Asset entities in the environment. Select the 'Assets' item on the middle panel and select 'Add asset' to add a new entity."),
                new HintCheck( () => { return env.ConceptDescriptions.Count < 1; },
                    "There are no embedded ConceptDescriptions in the environment. It is a good practive to have those. Select or add an AdministrationShell, Submodel and SubmodelElement and add a ConceptDescription.", severityLevel: HintCheck.Severity.Notice),
            });

                // overview information

                var g = helper.AddSmallGrid(6, 1, new string[] { "*" }, margin: new Thickness(5,5,0,0));
                helper.AddSmallLabelTo(g, 0, 0, content: "This structure hold the main entites of Administration shells.");
                helper.AddSmallLabelTo(g, 1, 0, content: String.Format("#admin shells: {0}.", env.AdministrationShells.Count), margin: new Thickness(0, 5, 0, 0));
                helper.AddSmallLabelTo(g, 2, 0, content: String.Format("#assets: {0}.", env.Assets.Count));
                helper.AddSmallLabelTo(g, 3, 0, content: String.Format("#submodels: {0}.", env.Submodels.Count));
                helper.AddSmallLabelTo(g, 4, 0, content: String.Format("#concept descriptions: {0}.", env.ConceptDescriptions.Count));
                stack.Children.Add(g);
            }
        }


        //
        //
        // --- Supplementary file
        //
        //

        public void DisplayOrEditAasEntitySupplementaryFile(AdminShell.PackageEnv package, AdminShell.PackageSupplementaryFile psf, bool editMode, ModifyRepo repo, StackPanel stack, Brush[][] levelColors)
        {
            // 
            // Package
            //
            helper.AddGroup(stack, "Supplementary file for package of AASX", levelColors[0][0], levelColors[0][1]);

            if (editMode && package != null && psf != null)
            {
                helper.AddAction(stack, "Action", new string[] { "Delete" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int)
                    {
                        if ((int)buttonNdx == 0)
                            if (MessageBoxResult.Yes == MessageBox.Show("Delete selected entity? This operation can not be reverted!", "AASX", MessageBoxButton.YesNo, MessageBoxImage.Warning))
                            {
                                try
                                {
                                    package.DeleteSupplementaryFile(psf);
                                    Log.Info("Added {0} to pending package items to be deleted. A save-operation might be required.", PackageSourcePath);
                                }
                                catch (Exception ex)
                                {
                                    Logging.Log.Error(ex, "Deleting file in package");
                                }
                                return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: VisualElementEnvironmentItem.GiveDataObject(VisualElementEnvironmentItem.ItemType.Package));
                            }
                    }
                    return new ModifyRepo.LambdaActionNone();
                });
            }
        }

        //
        //
        // --- AAS
        //
        //

        public void DisplayOrEditAasEntityAas(AdminShell.PackageEnv package, AdminShell.AdministrationShellEnv env, AdminShell.AdministrationShell aas, bool editMode, ModifyRepo repo, StackPanel stack, Brush[][] levelColors, bool hintMode = false)
        {
            helper.AddGroup(stack, "Asset Administration Shell", levelColors[0][0], levelColors[0][1]);

            // Entities
            if (editMode)
            {
                helper.AddGroup(stack, "Editing of entities", levelColors[0][0], levelColors[0][1]);

                // Up/ down/ del
                helper.EntityListUpDownDeleteHelper<AdminShell.AdministrationShell>(stack, repo, env.AdministrationShells, aas, env, "AAS:");

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return aas.submodelRefs.Count < 1;  },
                        "You have no Submodels referenced by this Administration Shell. This is rather unusual, as the Submodels are the actual carriers of information. Most likely, you want to click 'Create new Submodel of kind Instance'. You might also consider to load another AASX as auxiliary AASX (see 'File' menu) to copy structures from.", severityLevel: HintCheck.Severity.Notice)
                });// adding submodels
                helper.AddAction(stack, "SubmodelRef:", new string[] { "Reference to existing Submodel", "Create new Submodel of kind Type", "Create new Submodel of kind Instance" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int)
                    {
                        if ((int)buttonNdx == 0)
                        {
                            if (MessageBoxResult.Yes != MessageBox.Show("This operation creates a reference to an existing Submodel. " +
                                "By this, two AAS will share exactly the same data records. Changing one will cause the other AAS's information to change as well. " +
                                "This operation is rather special. Do you want to proceed?", "Submodel sharing", MessageBoxButton.YesNo, MessageBoxImage.Warning))
                                return new ModifyRepo.LambdaActionNone();

                            // select existing Submodel
                            var ks = helper.SmartSelectAasEntityKeys(package.AasEnv, "Submodel");
                            if (ks != null)
                            {
                                // create ref
                                var smr = new AdminShell.SubmodelRef();
                                smr.Keys.AddRange(ks);
                                aas.submodelRefs.Add(smr);

                                // redraw
                                return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: smr, isExpanded: true);
                            }
                        }

                        if ((int)buttonNdx == 1 || (int)buttonNdx == 2)
                        {
                            // create new submodel
                            var submodel = new AdminShell.Submodel();
                            env.Submodels.Add(submodel);

                            // directly create identification, as we need it!
                            submodel.identification.idType = "URI";
                            if ((int)buttonNdx == 1)
                                submodel.identification.id = Options.GenerateIdAccordingTemplate(Options.TemplateIdSubmodelType);
                            else
                                submodel.identification.id = Options.GenerateIdAccordingTemplate(Options.TemplateIdSubmodelInstance);

                            // create ref
                            var smr = new AdminShell.SubmodelRef();
                            smr.Keys.Add(new AdminShell.Key("Submodel", true, submodel.identification.idType, submodel.identification.id));
                            aas.submodelRefs.Add(smr);

                            // redraw
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: smr, isExpanded: true);

                        }

                    }
                    return new ModifyRepo.LambdaActionNone();
                });

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return helper.auxPackages != null;  },
                        "You have opened an auxiliary AASX package. You can copy elements from it!", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddAction(stack, "Copy from existing Submodel:", new string[] { "Copy single entity ", "Copy recursively" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int)
                    {
                        if ((int)buttonNdx == 0 || (int)buttonNdx == 1)
                        {
                            var rve = helper.SmartSelectAasEntityVisualElement(package.AasEnv, "SubmodelRef", package: package, auxPackages: helper.auxPackages) as VisualElementSubmodelRef;
                            if (rve != null)
                            {
                                var mdo = rve.GetMainDataObject();
                                if (rve != null && mdo != null && mdo is AdminShell.SubmodelRef)
                                {
                                    var clone = env.CopySubmodelRefAndCD(rve.theEnv, mdo as AdminShell.SubmodelRef, copySubmodel: true, copyCD: true, shallowCopy: (int)buttonNdx == 0);
                                    if (aas.submodelRefs == null)
                                        aas.submodelRefs = new List<AdminShell.SubmodelRef>();
                                    aas.submodelRefs.Add(clone);
                                    return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: clone, isExpanded: true);
                                }
                            }
                        }
                    }
                    return new ModifyRepo.LambdaActionNone();
                });

                // let the user control the number of entities
                helper.AddAction(stack, "Entities:", new string[] { "Add View" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int)
                    {
                        if ((int)buttonNdx == 0)
                        {
                            var view = new AdminShell.View();
                            aas.AddView(view);
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: view);
                        }
                    }
                    return new ModifyRepo.LambdaActionNone();
                });
            }

            // Referable
            helper.AddGroup(stack, "Referable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return aas.idShort != null && aas.idShort.Length > 0; },
                    "idShort is at least not required here. The specification lists it as 'n/a' for Administration Shells.", severityLevel: HintCheck.Severity.Notice ),
                new HintCheck( () => { if (aas.idShort == null) return false; return AdminShellUtil.HasWhitespace(aas.idShort); },
                    "idShort shall not contain whitespace." )
            });
            helper.AddKeyValue(stack, "idShort", aas.idShort, null, repo, v => { aas.idShort = v as string; return new ModifyRepo.LambdaActionNone(); });

            helper.AddHintBubble(stack, hintMode, new HintCheck(() => { return aas.category != null && aas.category.Length >= 1; }, "The use of category is unusual here.", severityLevel: HintCheck.Severity.Notice));
            helper.AddKeyValue(stack, "category", aas.category, null, repo, v => { aas.category = v as string; return new ModifyRepo.LambdaActionNone(); },
                comboBoxItems: AdminShell.Referable.ReferableCategoryNames, comboBoxIsEditable: true);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck(() => { return aas.description == null || aas.description.langString == null || aas.description.langString.Count < 1;  },
                    "The use of an description is recommended to allow the consumer of an Administration Shell to understand the nature of it.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck(() => { return aas.description.langString.Count < 2; },
                    "Consider having description in multiple langauges.", severityLevel: HintCheck.Severity.Notice)
            });
            if (helper.SafeguardAccess(stack, repo, aas.description, "description:", "Create data element!", v =>
            {
                aas.description = new AdminShell.Description();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddHintBubble(stack, hintMode, new HintCheck(() => { return aas.description.langString == null || aas.description.langString.Count < 1; }, "Please add some descriptions in your main languages here to help consumers of your Admin shell to understand your intentions."));
                helper.AddKeyListLangStr(stack, "description", aas.description.langString, repo);
            }

            // hasDataSpecification are MULTIPLE references. That is: multiple x multiple keys!
            if (helper.SafeguardAccess(stack, repo, aas.hasDataSpecification, "HasDataSpecification:", "Create data element!", v => {
                aas.hasDataSpecification = new AdminShell.HasDataSpecification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "HasDataSpecification", levelColors[1][0], levelColors[1][1]);

                if (editMode)
                {
                    // let the user control the number of references
                    helper.AddAction(stack, "Specifications:", new string[] { "Add Reference", "Delete last reference" }, repo, (buttonNdx) =>
                    {
                        if (buttonNdx is int)
                        {
                            if ((int)buttonNdx == 0)
                                aas.hasDataSpecification.reference.Add(new AdminShell.Reference());

                            if ((int)buttonNdx == 1 && aas.hasDataSpecification.reference.Count > 0)
                                aas.hasDataSpecification.reference.RemoveAt(aas.hasDataSpecification.reference.Count - 1);
                        }
                        return new ModifyRepo.LambdaActionRedrawEntity();
                    });
                }

                // now use the normal mechanism to deal with editMode or not ..                    
                if (aas.hasDataSpecification != null && aas.hasDataSpecification.reference != null && aas.hasDataSpecification.reference.Count > 0)
                {
                    for (int i = 0; i < aas.hasDataSpecification.reference.Count; i++)
                        helper.AddKeyListKeys(stack, String.Format("reference[{0}]", i), aas.hasDataSpecification.reference[i].Keys, repo, package, "All");
                }
            }

            helper.AddGroup(stack, "Identifiable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return aas.identification == null; },
                    "Providing a worldwide unique identification is mandatory.", breakIfTrue: true),
                new HintCheck( () => { return aas.identification.idType != "URI"; },
                    "Check if identification type is correct. Use of URIs is usual here.", severityLevel: HintCheck.Severity.Notice ),
                new HintCheck( () => { return aas.identification.id.Trim() == ""; },
                    "Identification id shall not be empty. You could use the 'Generate' button in order to generate a worldwide unique id. The template of this id could be set by commandline arguments." )

            });
            if (helper.SafeguardAccess(stack, repo, aas.identification, "identification:", "Create data element!", v => {
                aas.identification = new AdminShell.Identification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddKeyValue(stack, "idType", aas.identification.idType, null, repo, v => { aas.identification.idType = v as string; return new ModifyRepo.LambdaActionNone(); },
                    comboBoxItems: AdminShell.Key.IdentifierTypeNames);
                helper.AddKeyValue(stack, "id", aas.identification.id, null, repo, v => { aas.identification.id = v as string; return new ModifyRepo.LambdaActionNone(); },
                    auxButtonTitle: "Generate", auxButtonLambda: v => {
                        aas.identification.idType = "URI";
                        aas.identification.id = Options.GenerateIdAccordingTemplate(Options.TemplateIdAas);
                        return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: aas);
                    });
            }

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return aas.administration == null; },
                    "Check if providing admistrative information on version/ revision would be useful. This allows for better version management.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck( () => { return aas.administration.version.Trim() == "" || aas.administration.revision.Trim() == ""; },
                    "Admistrative information fields should not be empty.", severityLevel: HintCheck.Severity.Notice )
            });
            if (helper.SafeguardAccess(stack, repo, aas.administration, "administration:", "Create data element!", v => {
                aas.administration = new AdminShell.Administration();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddKeyValue(stack, "version", aas.administration.version, null, repo, v => { aas.administration.version = v as string; return new ModifyRepo.LambdaActionNone(); });
                helper.AddKeyValue(stack, "revision", aas.administration.revision, null, repo, v => { aas.administration.revision = v as string; return new ModifyRepo.LambdaActionNone(); });
            }

            // use some asset reference

            var asset = env.FindAsset(aas.assetRef);

            // derivedFrom

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return asset != null && asset.kind != null && asset.kind.IsInstance && ( aas.derivedFrom == null || aas.derivedFrom.Count < 1); },
                    "You have decided to create an AAS for kind = 'Instance'. You might derive this from another AAS of kind = 'Instance' or from another AAS of kind = 'Type'. It is perfectly fair to create an AdministrationShell with no 'derivedFrom' relation! However, for example, if you're an supplier of products which stem from a series-type, you might want to maintain a relation of the AAS's of the individual prouct instances to the AAS of the series type.", severityLevel: HintCheck.Severity.Notice)
            });
            if (helper.SafeguardAccess(stack, repo, aas.derivedFrom, "derivedFrom:", "Create data element!", v =>
            {
                aas.derivedFrom = new AdminShell.AssetAdministrationShellRef();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "Derived From", levelColors[1][0], levelColors[1][1]);
                helper.AddKeyListKeys(stack, "derivedFrom", aas.derivedFrom.Keys, repo, package, "AssetAdministrationShell");
            }

            // assetRef

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return asset == null; },
                    "No asset is associated with this Administration Shell. This might be, because some identification changed. Use 'Add existing' to assign the Administration Shell with an existing asset in the AAS environment or use 'Add blank' to create an arbitray reference."),
            });
            if (helper.SafeguardAccess(stack, repo, aas.assetRef, "assetRef:", "Create data element!", v =>
            {
                aas.assetRef = new AdminShell.AssetRef();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "Asset Reference", levelColors[1][0], levelColors[1][1]);
                helper.AddKeyListKeys(stack, "assetRef", aas.assetRef.Keys, repo, package, "Asset");
            }

            //
            // Asset linked with AAS
            //

            if (asset != null)
            {
                DisplayOrEditAasEntityAsset(package, env, asset, editMode, repo, stack, levelColors, hintMode: hintMode);
            }
        }

        //
        //
        // --- Submodel Ref
        //
        //

        public void DisplayOrEditAasEntitySubmodelRef(AdminShell.PackageEnv package, AdminShell.AdministrationShellEnv env, AdminShell.AdministrationShell aas, AdminShell.SubmodelRef smref, AdminShell.Submodel submodel, bool editMode, ModifyRepo repo, StackPanel stack, Brush[][] levelColors, bool hintMode = false)
        {
            if (editMode)
            {
                helper.AddGroup(stack, "Editing of entities", levelColors[0][0], levelColors[0][1]);

                helper.EntityListUpDownDeleteHelper<AdminShell.SubmodelRef>(stack, repo, aas.submodelRefs, smref, aas, "SubmodelRef:");

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return submodel.submodelElements == null || submodel.submodelElements.Count() < 1; },
                        "This Submodel currently has no SubmodelElements, yet. These are the actual carriers of information. You could create them by clicking the 'Add ..' buttons below. Subsequently, when having a SubmodelElement established, you could add meaning by relating it to a ConceptDefinition.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddAction(stack, "SubmodelElement:", new string[] { "Add Property", "Add File", "Add Blob", "Add Reference", "Add Collection", "Add Relationship" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int && (int)buttonNdx>=0 && (int)buttonNdx<=5)
                    {
                        // which?
                        AdminShell.SubmodelElement sme2 = null;
                        switch ((int)buttonNdx)
                        {
                            case 0:
                                sme2 = new AdminShell.Property(); 
                                break;
                            case 1:
                                sme2 = new AdminShell.File();
                                break;
                            case 2:
                                sme2 = new AdminShell.Blob();
                                break;
                            case 3:
                                sme2 = new AdminShell.ReferenceElement();
                                break;
                            case 4:
                                sme2 = new AdminShell.SubmodelElementCollection();
                                break;
                            case 5:
                                sme2 = new AdminShell.RelationshipElement();
                                break;
                        }

                        // add
                        var smw = new AdminShell.SubmodelElementWrapper();
                        smw.submodelElement = sme2;
                        if (submodel.submodelElements == null)
                            submodel.submodelElements = new List<AdminShell.SubmodelElementWrapper>();
                        submodel.submodelElements.Add(smw);

                        // redraw
                        return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: sme2, isExpanded: true);
                    }
                    return new ModifyRepo.LambdaActionNone();
                });

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return helper.auxPackages != null;  },
                        "You have opened an auxiliary AASX package. You can copy elements from it!", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddAction(stack, "Copy from existing SubmodelElement:", new string[] { "Copy single entity", "Copy recursively" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int)
                    {
                        if ((int)buttonNdx == 0 || (int)buttonNdx == 1)
                        {
                            var rve = helper.SmartSelectAasEntityVisualElement(package.AasEnv, "SubmodelElement", package: package, auxPackages: helper.auxPackages) as VisualElementSubmodelElement;
                            if (rve != null)
                            {
                                var mdo = rve.GetMainDataObject();
                                if (rve != null && mdo != null && mdo is AdminShell.SubmodelElement)
                                {
                                    var clone = env.CopySubmodelElementAndCD(rve.theEnv, mdo as AdminShell.SubmodelElement, copyCD: true, shallowCopy: (int)buttonNdx == 0);
                                    if (submodel.submodelElements == null)
                                        submodel.submodelElements = new List<AdminShell.SubmodelElementWrapper>();
                                    submodel.submodelElements.Add(clone);
                                    return new ModifyRepo.LambdaActionRedrawAllElements(submodel, isExpanded: true);
                                }
                            }
                        }
                    }
                    return new ModifyRepo.LambdaActionNone();
                });

            }

            helper.AddGroup(stack, "Referable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return submodel.idShort == null || submodel.idShort.Length < 1; },
                    "idShort is mandatory for Submodels. It is a short, unique identifier that is unique just in its context, its name space. ", breakIfTrue: true),
                new HintCheck( () => { if (submodel.idShort == null) return false; return AdminShellUtil.HasWhitespace(submodel.idShort); },
                    "idShort shall not contain whitespace." )
            });
            helper.AddKeyValue(stack, "idShort", submodel.idShort, null, repo, v => { submodel.idShort = v as string; return new ModifyRepo.LambdaActionNone(); });

            helper.AddHintBubble(stack, hintMode, new HintCheck(() => { return submodel.category != null && submodel.category.Length >= 1; }, "The use of category is unusual here.", severityLevel: HintCheck.Severity.Notice));
            helper.AddKeyValue(stack, "category", submodel.category, null, repo, v => { submodel.category = v as string; return new ModifyRepo.LambdaActionNone(); },
                comboBoxItems: AdminShell.Referable.ReferableCategoryNames, comboBoxIsEditable: true);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck(() => { return submodel.description == null || submodel.description.langString == null || submodel.description.langString.Count < 1;  },
                    "The use of an description is recommended to allow the consumer of an Submodel to understand the nature of it.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck(() => { return submodel.description.langString.Count < 2; },
                    "Consider having description in multiple langauges.", severityLevel: HintCheck.Severity.Notice)
            });
            if (helper.SafeguardAccess(stack, repo, submodel.description, "description:", "Create data element!", v =>
            {
                submodel.description = new AdminShell.Description();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddHintBubble(stack, hintMode, new HintCheck(() => { return submodel.description.langString == null || submodel.description.langString.Count < 1; }, "Please add some descriptions in your main languages here to help consumers of your Admin shell to understand your intentions."));
                helper.AddKeyListLangStr(stack, "description", submodel.description.langString, repo);
            }

            // hasDataSpecification are MULTIPLE references. That is: multiple x multiple keys!
            if (helper.SafeguardAccess(stack, repo, submodel.hasDataSpecification, "HasDataSpecification:", "Create data element!", v => {
                submodel.hasDataSpecification = new AdminShell.HasDataSpecification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "HasDataSpecification", levelColors[1][0], levelColors[1][1]);

                if (editMode)
                {
                    // let the user control the number of references
                    helper.AddAction(stack, "Specifications:", new string[] { "Add Reference", "Delete last reference" }, repo, (buttonNdx) =>
                    {
                        if (buttonNdx is int)
                        {
                            if ((int)buttonNdx == 0)
                                submodel.hasDataSpecification.reference.Add(new AdminShell.Reference());

                            if ((int)buttonNdx == 1 && submodel.hasDataSpecification.reference.Count > 0)
                                submodel.hasDataSpecification.reference.RemoveAt(submodel.hasDataSpecification.reference.Count - 1);
                        }
                        return new ModifyRepo.LambdaActionRedrawEntity();
                    });
                }

                // now use the normal mechanism to deal with editMode or not ..                    
                if (submodel.hasDataSpecification != null && submodel.hasDataSpecification.reference != null && submodel.hasDataSpecification.reference.Count > 0)
                {
                    for (int i = 0; i < submodel.hasDataSpecification.reference.Count; i++)
                        helper.AddKeyListKeys(stack, String.Format("reference[{0}]", i), submodel.hasDataSpecification.reference[i].Keys, repo, package, "All");
                }
            }

            helper.AddGroup(stack, "Identifiable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return submodel.identification == null; },
                    "Providing a worldwide unique identification is mandatory.", breakIfTrue: true),
                new HintCheck( () => { return submodel.kind != null && submodel.kind.IsInstance && submodel.identification.idType != "URI"; },
                    "Check if identification type is correct. Use of URIs is usual for instances of Submodels.", severityLevel: HintCheck.Severity.Notice ),
                new HintCheck( () => { return submodel.identification.id.Trim() == ""; },
                    "Identification id shall not be empty. You could use the 'Generate' button in order to generate a worldwide unique id. The template of this id could be set by commandline arguments." )
            });
            if (helper.SafeguardAccess(stack, repo, submodel.identification, "identification:", "Create data element!", v => {
                submodel.identification = new AdminShell.Identification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddKeyValue(stack, "idType", submodel.identification.idType, null, repo, v => { submodel.identification.idType = v as string; return new ModifyRepo.LambdaActionNone(); },
                    comboBoxItems: AdminShell.Key.IdentifierTypeNames);
                helper.AddKeyValue(stack, "id", submodel.identification.id, null, repo, v => { submodel.identification.id = v as string; return new ModifyRepo.LambdaActionNone(); },
                    auxButtonTitle: "Generate", auxButtonLambda: v => {
                        submodel.identification.idType = "URI";
                        if (submodel.kind.kind.Trim().ToLower() == "type")
                            submodel.identification.id = Options.GenerateIdAccordingTemplate(Options.TemplateIdSubmodelType);
                        else
                            submodel.identification.id = Options.GenerateIdAccordingTemplate(Options.TemplateIdSubmodelInstance);
                        return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: submodel);
                    });
            }

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return submodel.administration == null; },
                    "Check if providing admistrative information on version/ revision would be useful. This allows for better version management.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck( () => { return submodel.administration.version.Trim() == "" || submodel.administration.revision.Trim() == ""; },
                    "Admistrative information fields should not be empty.", severityLevel: HintCheck.Severity.Notice )
            });
            if (helper.SafeguardAccess(stack, repo, submodel.administration, "administration:", "Create data element!", v => {
                submodel.administration = new AdminShell.Administration();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddKeyValue(stack, "version", submodel.administration.version, null, repo, v => { submodel.administration.version = v as string; return new ModifyRepo.LambdaActionNone(); });
                helper.AddKeyValue(stack, "revision", submodel.administration.revision, null, repo, v => { submodel.administration.revision = v as string; return new ModifyRepo.LambdaActionNone(); });
            }

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return submodel.semanticId == null || submodel.semanticId.IsEmpty; },
                    "Check if you want to add an semantic reference. The semanticId may be either a reference to a submodel with kind=Type (within the same or another Administration Shell) or it can be an external reference to an external standard defining the semantics of the submodel  (for example an PDF if a standard).", severityLevel: HintCheck.Severity.Notice )
            });
            helper.AddGroup(stack, "Semantic ID", levelColors[1][0], levelColors[1][1]);
            if (helper.SafeguardAccess(stack, repo, submodel.semanticId, "semanticId:", "Create data element!", v => {
                submodel.semanticId = new AdminShell.SemanticId();
                return new ModifyRepo.LambdaActionRedrawEntity();
            })) helper.AddKeyListKeys(stack, "semanticId", submodel.semanticId.Keys, repo);

            helper.AddGroup(stack, "Kind:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return submodel.kind == null; },
                    "Providing kind information is mandatory. Typically you want to model instances. A manufacturer would define types of assets, as well.", breakIfTrue: true),
                new HintCheck( () => { return submodel.kind.IsType; },
                    "Check for kind setting. 'Instance' is the usual choice, except if you want to declare a Submodel, which is been standardised by you or a standardisation body.", severityLevel: HintCheck.Severity.Notice )
            });
            if (helper.SafeguardAccess(stack, repo, submodel.kind, "kind:", "Create data element!", v => {
                submodel.kind = new AdminShell.Kind();
                return new ModifyRepo.LambdaActionRedrawEntity();
            })) helper.AddKeyValue(stack, "kind", submodel.kind.kind, null, repo, v => { submodel.kind.kind = v as string; return new ModifyRepo.LambdaActionNone(); }, new string[] { "Type", "Instance" });
        }

        //
        //
        // --- Concept Description
        //
        //

        public void DisplayOrEditAasEntityConceptDescription(AdminShell.PackageEnv package, AdminShell.AdministrationShellEnv env, AdminShell.ConceptDescription cd, bool editMode, ModifyRepo repo, StackPanel stack, Brush[][] levelColors, bool embedded = false, bool hintMode = false)
        {
            helper.AddGroup(stack, "ConceptDescription", levelColors[0][0], levelColors[0][1]);

            // Up/ down/ del
            if (editMode && !embedded)
            {
                helper.EntityListUpDownDeleteHelper<AdminShell.ConceptDescription>(stack, repo, env.ConceptDescriptions, cd, env, "CD:");
            }

            // Referable
            helper.AddGroup(stack, "Referable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return cd.idShort == null || cd.idShort.Length < 1; },
                    "idShort is not mandatory for concept descriptions. It is a short, unique identifier that is unique just in its context, its name space. Recommendation of the specification is to make it same as the short name of the concept, referred. ", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck( () => { if (cd.idShort == null) return false; return AdminShellUtil.HasWhitespace(cd.idShort); },
                    "idShort shall not contain whitespace." )
            });
            helper.AddKeyValue(stack, "idShort", cd.idShort, null, repo, v => { cd.idShort = v as string; return new ModifyRepo.LambdaActionNone(); });

            helper.AddHintBubble(stack, hintMode, new HintCheck(() => { return cd.category != null && cd.category.Length >= 1; }, "The use of category is unusual here.", severityLevel: HintCheck.Severity.Notice));
            helper.AddKeyValue(stack, "category", cd.category, null, repo, v => { cd.category = v as string; return new ModifyRepo.LambdaActionNone(); },
                comboBoxItems: AdminShell.Referable.ReferableCategoryNames, comboBoxIsEditable: true);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck(() => { return cd.description == null || cd.description.langString == null || cd.description.langString.Count < 1;  },
                    "The use of an description is recommended to allow the consumer of an ConceptDescription to understand the nature of it.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck(() => { return cd.description.langString.Count < 2; },
                    "Consider having description in multiple langauges.", severityLevel: HintCheck.Severity.Notice)
            });
            if (helper.SafeguardAccess(stack, repo, cd.description, "description:", "Create data element!", v =>
            {
                cd.description = new AdminShell.Description();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddHintBubble(stack, hintMode, new HintCheck(() => { return cd.description.langString == null || cd.description.langString.Count < 1; }, "Please add some descriptions in your main languages here to help consumers of your Administration shell to understand your intentions."));
                helper.AddKeyListLangStr(stack, "description", cd.description.langString, repo);
            }

            helper.AddGroup(stack, "Identifiable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return cd.identification == null; },
                    "Providing a worldwide unique identification is mandatory. If the concept description is a copy from an external dictionary like eCl@ss it may use the same global id as it is used in the external dictionary.  ", breakIfTrue: true),
                new HintCheck( () => { return cd.identification.id.Trim() == ""; },
                    "Identification id shall not be empty. You could use the 'Generate' button in order to generate a worldwide unique id. The template of this id could be set by commandline arguments." )

            });
            if (helper.SafeguardAccess(stack, repo, cd.identification, "identification:", "Create data element!", v => {
                cd.identification = new AdminShell.Identification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddKeyValue(stack, "idType", cd.identification.idType, null, repo, v => { cd.identification.idType = v as string; return new ModifyRepo.LambdaActionNone(); },
                    comboBoxItems: AdminShell.Key.IdentifierTypeNames);
                helper.AddKeyValue(stack, "id", cd.identification.id, null, repo, v => { cd.identification.id = v as string; return new ModifyRepo.LambdaActionNone(); },
                auxButtonTitle: "Generate", auxButtonLambda: v => {
                    cd.identification.idType = "URI";
                    cd.identification.id = Options.GenerateIdAccordingTemplate(Options.TemplateIdConceptDescription);
                    return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: cd);
                });
            }

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return cd.administration == null; },
                    "Check if providing admistrative information on version/ revision would be useful. This allows for better version management.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck( () => { return cd.administration.version.Trim() == "" || cd.administration.revision.Trim() == ""; },
                    "Admistrative information fields should not be empty.", severityLevel: HintCheck.Severity.Notice )
            });
            if (helper.SafeguardAccess(stack, repo, cd.administration, "administration:", "Create data element!", v => {
                cd.administration = new AdminShell.Administration();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddKeyValue(stack, "version", cd.administration.version, null, repo, v => { cd.administration.version = v as string; return new ModifyRepo.LambdaActionNone(); });
                helper.AddKeyValue(stack, "revision", cd.administration.revision, null, repo, v => { cd.administration.revision = v as string; return new ModifyRepo.LambdaActionNone(); });
            }

            // isCaseOf are MULTIPLE references. That is: multiple x multiple keys!
            if (helper.SafeguardAccess(stack, repo, cd.IsCaseOf, "isCaseOf:", "Create data element!", v => {
                cd.IsCaseOf = new List<AdminShell.Reference>();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "IsCaseOf", levelColors[1][0], levelColors[1][1]);

                if (editMode)
                {
                    // let the user control the number of references
                    helper.AddAction(stack, "IsCaseOf:", new string[] { "Add Reference", "Delete last reference" }, repo, (buttonNdx) =>
                    {
                        if (buttonNdx is int)
                        {
                            if ((int)buttonNdx == 0)
                                cd.IsCaseOf.Add(new AdminShell.Reference());

                            if ((int)buttonNdx == 1 && cd.IsCaseOf.Count > 0)
                                cd.IsCaseOf.RemoveAt(cd.IsCaseOf.Count - 1);
                        }
                        return new ModifyRepo.LambdaActionRedrawEntity();
                    });
                }

                // now use the normal mechanism to deal with editMode or not ..                    
                if (cd.IsCaseOf != null && cd.IsCaseOf.Count > 0)
                {
                    for (int i = 0; i < cd.IsCaseOf.Count; i++)
                        helper.AddKeyListKeys(stack, String.Format("reference[{0}]", i), cd.IsCaseOf[i].Keys, repo, package, "All");
                }
            }

            /* OLD
            if (helper.SafeguardAccess(stack, repo, cd.conceptDefinitionRef, "conceptDefinitionRef:", "Create data element!", v =>
            {
                cd.conceptDefinitionRef = new AdminShell.Reference();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "Concept Definition Reference", levelColors[1][0], levelColors[1][1]);
                helper.AddKeyListKeys(stack, "reference", cd.conceptDefinitionRef.Keys, repo, package, "All");
            }
            */

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return cd.embeddedDataSpecification == null; },
                    "Providing embeddedDataSpecification is mandatory. This holds the descriptive information of an concept and allows for an off-line understanding of the meaning of an concept/ SubmodelElement. Please create this data element.", breakIfTrue: true),
            });
            if (helper.SafeguardAccess(stack, repo, cd.embeddedDataSpecification, "embeddedDataSpecification:", "Create data element!", v =>
            {
                cd.embeddedDataSpecification = new AdminShell.EmbeddedDataSpecification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                // has data spec
                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return cd.embeddedDataSpecification.hasDataSpecification == null; },
                        "Providing hasDataSpecification is mandatory. This holds the external global reference to the specification, which defines the data template, which attributes are featured within the ConceptDescription. Typically, it refers to www.admin-shell.io/DataSpecificationTemplates/DataSpecificationIEC61360.")
                });
                if (helper.SafeguardAccess(stack, repo, cd.embeddedDataSpecification.hasDataSpecification, "hasDataSpecification:", "Create data element!", v =>
                {
                    cd.embeddedDataSpecification.hasDataSpecification = new AdminShell.DataSpecificationRef();
                    return new ModifyRepo.LambdaActionRedrawEntity();
                }))
                {
                    helper.AddGroup(stack, "HasDataSpecification", levelColors[1][0], levelColors[1][1]);

                    helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return cd.embeddedDataSpecification.hasDataSpecification == null || cd.embeddedDataSpecification.hasDataSpecification.Count != 1 || cd.embeddedDataSpecification.hasDataSpecification[0].type != AdminShell.Key.GlobalReference; },
                        "hasDataSpecification holds the external global reference to the specification, which defines the data template, which attributes are featured within the ConceptDescription. Typically, it refers to www.admin-shell.io/DataSpecificationTemplates/DataSpecificationIEC61360.", severityLevel: HintCheck.Severity.Notice),
                    });
                    helper.AddKeyListKeys(stack, "hasDataSpecification", cd.embeddedDataSpecification.hasDataSpecification.Keys, repo, package, "All");
                }

                // data spec content
                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return cd.embeddedDataSpecification.dataSpecificationContent == null; },
                        "Providing dataSpecificationContent is mandatory. This holds the attributes describing the concept. Please create this data element.")
                });
                if (helper.SafeguardAccess(stack, repo, cd.embeddedDataSpecification.dataSpecificationContent, "dataSpecificationContent:", "Create data element!", v =>
                {
                    cd.embeddedDataSpecification.dataSpecificationContent = new AdminShell.DataSpecificationContent();
                    return new ModifyRepo.LambdaActionRedrawEntity();
                }))
                {
                    helper.AddGroup(stack, "DataSpecificationContent", levelColors[1][0], levelColors[1][1]);

                    // 61360?
                    helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return cd.embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360 == null; },
                        "As of January 2019, there is only a data specification for IEC 61360. Please create this data element.")
                    });
                    if (helper.SafeguardAccess(stack, repo, cd.embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360, "dataSpecificationIEC61360:", "Create data element!", v =>
                    {
                        cd.embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360 = new AdminShell.DataSpecificationIEC61360();
                        return new ModifyRepo.LambdaActionRedrawEntity();
                    }))
                    {
                        var dsiec = cd.embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360;
                        helper.AddGroup(stack, "Data Specification Content IEC61360", levelColors[1][0], levelColors[1][1]);

                        helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                            new HintCheck(() => { return dsiec.preferredName == null || dsiec.preferredName.Count < 1; }, "Please add a preferred name, which could be used on user interfaces to identify the concept to a human person.", breakIfTrue: true),
                            new HintCheck(() => { return dsiec.preferredName.Count <2; }, "Please add multiple languanges.", severityLevel: HintCheck.Severity.Notice)
                        });
                        if (helper.SafeguardAccess(stack, repo, dsiec.preferredName, "preferredName:", "Create data element!", v =>
                        {
                            dsiec.preferredName = new AdminShell.LangStringIEC61360();
                            return new ModifyRepo.LambdaActionRedrawEntity();
                        })) helper.AddKeyListLangStr(stack, "preferredName", dsiec.preferredName.langString, repo);

                        helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                            new HintCheck( () => { return dsiec.shortName == null || dsiec.shortName.Trim().Length < 1; },
                                "Please provide a shortName, which is a reduced, even symbolic version of the preferred name. IEC 61360 defines some symbolic rules (e.g. greek characters) for this name.")
                        });
                        helper.AddKeyValue(stack, "shortName", dsiec.shortName, null, repo, v => { dsiec.shortName = v as string; return new ModifyRepo.LambdaActionNone(); });

                        helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                            new HintCheck( () => { return (dsiec.unitId == null || dsiec.unitId.Count < 1) && ( dsiec.unit == null || dsiec.unit.Trim().Length < 1); },
                                "Please check, if you can provide a unit or a unitId, in which the concept is being measured. Usage of SI-based units is encouraged.")
                        });
                        helper.AddKeyValue(stack, "unit", dsiec.unit, null, repo, v => { dsiec.unit = v as string; return new ModifyRepo.LambdaActionNone(); });

                        helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                            new HintCheck( () => { return ( dsiec.unit == null || dsiec.unit.Trim().Length < 1) && ( dsiec.unitId == null || dsiec.unitId.Count < 1); },
                                "Please check, if you can provide a unit or a unitId, in which the concept is being measured. Usage of SI-based units is encouraged.")
                        });
                        if (helper.SafeguardAccess(stack, repo, dsiec.unitId, "unitId:", "Create data element!", v =>
                        {
                            dsiec.unitId = new AdminShell.UnitId();
                            return new ModifyRepo.LambdaActionRedrawEntity();
                        }))
                        {
                            helper.AddGroup(stack, "UnitID", levelColors[1][0], levelColors[1][1]);
                            helper.AddKeyListKeys(stack, "unitId", dsiec.unitId.Keys, repo, package, AdminShell.Key.GlobalReference);
                        }

                        helper.AddKeyValue(stack, "valueFormat", dsiec.valueFormat, null, repo, v => { dsiec.valueFormat = v as string; return new ModifyRepo.LambdaActionNone(); });

                        if (helper.SafeguardAccess(stack, repo, dsiec.sourceOfDefinition, "sourceOfDefinition:", "Create data element!", v =>
                        {
                            dsiec.sourceOfDefinition = new List<AdminShell.LangStr>();
                            return new ModifyRepo.LambdaActionRedrawEntity();
                        })) helper.AddKeyListLangStr(stack, "sourceOfDefinition", dsiec.sourceOfDefinition, repo);

                        helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                            new HintCheck( () => { return dsiec.symbol == null || dsiec.symbol.Trim().Length < 1; },
                                "Please check, if you can provide formulaic character for the concept.", severityLevel: HintCheck.Severity.Notice)
                        });
                        helper.AddKeyValue(stack, "symbol", dsiec.symbol, null, repo, v => { dsiec.symbol = v as string; return new ModifyRepo.LambdaActionNone(); });

                        helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                            new HintCheck( () => { return dsiec.dataType == null || dsiec.dataType.Trim().Length < 1; },
                                "Please check, if you can provide data type for the concept. Data types are provided by the IEC 61360.", severityLevel: HintCheck.Severity.Notice)
                        });
                        helper.AddKeyValue(stack, "dataType", dsiec.dataType, null, repo, v => { dsiec.dataType = v as string; return new ModifyRepo.LambdaActionNone(); },
                            comboBoxIsEditable: true, comboBoxItems: new string[] { "STRING", "STRING_TRANSLATABLE", "REAL_MEASURE", "REAL_COUNT", "REAL_CURRENCY", "INTEGER_MEASURE", "INTEGER_COUNT", "INTEGER_CURRENCY", "BOOLEAN", "URL", "RATIONAL", "RATIONAL_MEASURE", "TIME", "TIMESTAMP", "DATE" });

                        helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                            new HintCheck(() => { return dsiec.definition == null || dsiec.definition.Count < 1; }, "Please add a definition, which could be used to describe exactly, how to establish a value/ measurement for the concept.", breakIfTrue: true),
                            new HintCheck(() => { return dsiec.definition.Count <2; }, "Please add multiple languanges.", severityLevel: HintCheck.Severity.Notice)
                        });
                        if (helper.SafeguardAccess(stack, repo, dsiec.definition, "definition:", "Create data element!", v =>
                        {
                            dsiec.definition = new AdminShell.LangStringIEC61360();
                            return new ModifyRepo.LambdaActionRedrawEntity();
                        })) helper.AddKeyListLangStr(stack, "definition", dsiec.definition.langString, repo);
                    }

                }

            }
        }

        //
        //
        // --- Submodel Element
        //
        //

        public void DisplayOrEditAasEntitySubmodelElement(AdminShell.PackageEnv package, AdminShell.AdministrationShellEnv env, AdminShell.Referable parentContainer, AdminShell.SubmodelElementWrapper wrapper, bool editMode, ModifyRepo repo, StackPanel stack, Brush[][] levelColors, bool hintMode = false)
        {
            //
            // Submodel Element GENERAL
            //
            var sme = wrapper.submodelElement;
            if (sme == null)
                return;

            if (editMode)
            {
                helper.AddGroup(stack, "Editing of entities", levelColors[0][0], levelColors[0][1]);

                // entities
                if (parentContainer != null && parentContainer is AdminShell.Submodel)
                    helper.EntityListUpDownDeleteHelper<AdminShell.SubmodelElementWrapper>(stack, repo, (parentContainer as AdminShell.Submodel).submodelElements, wrapper, env, "SubmodelElement:");
                if (parentContainer != null && parentContainer is AdminShell.SubmodelElementCollection)
                    helper.EntityListUpDownDeleteHelper<AdminShell.SubmodelElementWrapper>(stack, repo, (parentContainer as AdminShell.SubmodelElementCollection).value, wrapper, env, "SubmodelElement:");

                // guess kind or instances
                AdminShell.Kind parentKind = null;
                if (parentContainer != null && parentContainer is AdminShell.Submodel)
                    parentKind = (parentContainer as AdminShell.Submodel).kind;
                if (parentContainer != null && parentContainer is AdminShell.SubmodelElementCollection)
                    parentKind = (parentContainer as AdminShell.SubmodelElementCollection).kind;

                // adding
                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return sme.semanticId == null || sme.semanticId.IsEmpty; },
                    "The semanticId (see below) is empty. This SubmodelElement ist currently not assigned to any ConceptDescription. However, it is recommended to do such assignemt. With the 'Assign ..' buttons below you might create and/or assign the SubmodelElement to an ConceptDescription.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddAction(stack, "SubmodelElement:", new string[] { "Assign to existing CD", "Create empty and assign", "Create and assign from eCl@ss" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int)
                    {
                        if ((int)buttonNdx == 0)
                        {
                            // select existing CD
                            var ks = helper.SmartSelectAasEntityKeys(package.AasEnv);
                            if (ks != null)
                            {
                                // set the semantic id
                                sme.semanticId = AdminShell.SemanticId.CreateFromKeys(ks);
                                
                                // if empty take over shortName
                                var cd = env.FindConceptDescription(sme.semanticId.Keys);
                                if ((sme.idShort == null || sme.idShort.Trim() == "") && cd != null)
                                    sme.idShort = cd.GetShortName();

                                // can set kind?
                                if (parentKind != null && sme.kind == null)
                                    sme.kind = AdminShell.Kind.CreateFrom(parentKind);
                            }
                            // redraw
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: sme);
                        }

                        if ((int)buttonNdx == 1)
                        {
                            // create empty CD
                            var cd = new AdminShell.ConceptDescription();

                            // make an ID, automatically
                            cd.identification.idType = "URI";
                            cd.identification.id = Options.GenerateIdAccordingTemplate(Options.TemplateIdConceptDescription);

                            // store in AAS enviroment
                            env.ConceptDescriptions.Add(cd);

                            // go over to SubmodelElement
                            // set the semantic id
                            sme.semanticId = AdminShell.SemanticId.CreateFromKey(new AdminShell.Key("ConceptDescription", true, cd.identification.idType, cd.identification.id));

                            // can set kind?
                            if (parentKind != null && sme.kind == null)
                                sme.kind = AdminShell.Kind.CreateFrom(parentKind);

                            // redraw
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: sme);
                        }

                        if ((int)buttonNdx == 2)
                        {
                            // feature available
                            if (Options.EclassDir == null)
                            {
                                // eclass dir?
                                MessageBox.Show("The AASX Package Explore can take over eCl@ss definition. In order to do so, the commandine parameter -eclass has" +
                                    "to refer to a folder withe eCl@ss XML files.", "Information", MessageBoxButton.OK, MessageBoxImage.Information);
                                return new ModifyRepo.LambdaActionNone();
                            }

                            // select
                            var dlg = new SelectEclassEntity(Options.EclassDir);
                            if (dlg.ShowDialog() == true && dlg.ResultIRDI != null)
                            {
                                // create the concept description itself, if available
                                if (dlg.ResultCD != null) { 
                                    var newcd = dlg.ResultCD;
                                    env.ConceptDescriptions.Add(newcd);
                                }

                                // set the semantic key
                                sme.semanticId = AdminShell.SemanticId.CreateFromKey(new AdminShell.Key("ConceptDescription", true, "IRDI", dlg.ResultIRDI));

                                // if empty take over shortName
                                var cd = env.FindConceptDescription(sme.semanticId.Keys);
                                if ((sme.idShort == null || sme.idShort.Trim() == "") && cd != null)
                                    sme.idShort = cd.GetShortName();

                                // can set kind?
                                if (parentKind != null && sme.kind == null)
                                    sme.kind = AdminShell.Kind.CreateFrom(parentKind);
                            }
                            // redraw
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: sme);
                        }

                    }
                    return new ModifyRepo.LambdaActionNone();
                });

            }

            if (editMode && sme is AdminShell.SubmodelElementCollection)
            {
                var smc = sme as AdminShell.SubmodelElementCollection;

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return smc.value == null || smc.value.Count() < 1; },
                        "This SubmodelElementCollection currently has no SubmodelElements, yet. These are the actual carriers of information. You could create them by clicking the 'Add ..' buttons below. Subsequently, when having a SubmodelElement established, you could add meaning by relating it to a ConceptDefinition.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddAction(stack, "SubmodelElement:", new string[] { "Add Property", "Add File", "Add Blob", "Add Reference", "Add Collection", "Add Relationship" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int && (int)buttonNdx >= 0 && (int)buttonNdx <= 5)
                    {
                        // which?
                        AdminShell.SubmodelElement sme2 = null;
                        switch ((int)buttonNdx)
                        {
                            case 0:
                                sme2 = new AdminShell.Property();
                                break;
                            case 1:
                                sme2 = new AdminShell.File();
                                break;
                            case 2:
                                sme2 = new AdminShell.Blob();
                                break;
                            case 3:
                                sme2 = new AdminShell.ReferenceElement();
                                break;
                            case 4:
                                sme2 = new AdminShell.SubmodelElementCollection();
                                break;
                            case 5:
                                sme2 = new AdminShell.RelationshipElement();
                                break;
                        }

                        // add
                        var smw = new AdminShell.SubmodelElementWrapper();
                        smw.submodelElement = sme2;
                        if (smc.value == null)
                            smc.value = new List<AdminShell.SubmodelElementWrapper>();
                        smc.value.Add(smw);

                        // redraw
                        return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: sme2);
                    }
                    return new ModifyRepo.LambdaActionNone();
                });

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return helper.auxPackages != null;  },
                        "You have opened an auxiliary AASX package. You can copy elements from it!", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddAction(stack, "Copy from existing SubmodelElement:", new string[] { "Copy single single", "Copy recursively" }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int)
                    {
                        if ((int)buttonNdx == 0 || (int)buttonNdx == 1)
                        {
                            var rve = helper.SmartSelectAasEntityVisualElement(package.AasEnv, "SubmodelElement", package: package, auxPackages: helper.auxPackages) as VisualElementSubmodelElement;
                            if (rve != null)
                            {
                                var mdo = rve.GetMainDataObject();
                                if (rve != null && mdo != null && mdo is AdminShell.SubmodelElement)
                                {
                                    var clone = env.CopySubmodelElementAndCD(rve.theEnv, mdo as AdminShell.SubmodelElement, copyCD: true, shallowCopy: (int)buttonNdx == 0);
                                    if (smc.value == null)
                                        smc.value = new List<AdminShell.SubmodelElementWrapper>();
                                    smc.value.Add(clone);
                                    return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: smc, isExpanded: true);
                                }
                            }
                        }
                    }
                    return new ModifyRepo.LambdaActionNone();
                });

            }

            helper.AddGroup(stack, "Submodel Element", levelColors[0][0], levelColors[0][1]);

            helper.AddGroup(stack, "Referable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return sme.idShort == null || sme.idShort.Length < 1; },
                    "idShort is mandatory for SubmodelElements. It is a short, unique identifier that is unique just in its context, its name space. It is not required to be unique over multiple SubmodelElementCollections.", breakIfTrue: true),
                new HintCheck( () => { if (sme.idShort == null) return false; return AdminShellUtil.HasWhitespace(sme.idShort); },
                    "idShort shall not contain whitespace." )
            });
            helper.AddKeyValue(stack, "idShort", sme.idShort, null, repo, v => { sme.idShort = v as string; return new ModifyRepo.LambdaActionNone(); });

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck(() => { return sme is AdminShell.Property && sme.category == null || sme.category.Trim().Length < 1; },
                    "The use of category is strongly recommended for SubmodelElements which are properties! Please check which pre-defined category fits most to the application of the SubmodelElement. \r\n" +
                    "CONSTANT => A constant property is a property with a value that does not change over time. In eCl@ss this kind of category has the category 'Coded Value'. \r\n" +
                    "PARAMETER => A parameter property is a property that is once set and then typically does not change over time. This is for example the case for configuration parameters. \r\n" +
                    "VARIABLE => A variable property is a property that is calculated during runtime, i.e. its value is a runtime value. ", severityLevel: HintCheck.Severity.Notice)
            });
            helper.AddKeyValue(stack, "category", sme.category, null, repo, v => { sme.category = v as string; return new ModifyRepo.LambdaActionNone(); },
                comboBoxItems: AdminShell.Referable.ReferableCategoryNames, comboBoxIsEditable: true);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck(() => { return sme.description == null || sme.description.langString == null || sme.description.langString.Count < 1;  },
                    "The use of an description is recommended to allow the consumer of an SubmodelElement to understand the nature of it.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck(() => { return sme.description.langString.Count < 2; },
                    "Consider having description in multiple langauges.", severityLevel: HintCheck.Severity.Notice)
            });
            if (helper.SafeguardAccess(stack, repo, sme.description, "description:", "Create data element!", v => {
                sme.description = new AdminShell.Description();
                return new ModifyRepo.LambdaActionRedrawEntity();
            })) helper.AddKeyListLangStr(stack, "description", sme.description.langString, repo);

            // hasDataSpecification are MULTIPLE references. That is: multiple x multiple keys!
            if (helper.SafeguardAccess(stack, repo, sme.hasDataSpecification, "HasDataSpecification:", "Create data element!", v => {
                sme.hasDataSpecification = new AdminShell.HasDataSpecification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "HasDataSpecification", levelColors[1][0], levelColors[1][1]);

                if (editMode)
                {
                    // let the user control the number of references
                    helper.AddAction(stack, "Specifications:", new string[] { "Add Reference", "Delete last reference" }, repo, (buttonNdx) =>
                    {
                        if (buttonNdx is int)
                        {
                            if ((int)buttonNdx == 0)
                                sme.hasDataSpecification.reference.Add(new AdminShell.Reference());

                            if ((int)buttonNdx == 1 && sme.hasDataSpecification.reference.Count > 0)
                                sme.hasDataSpecification.reference.RemoveAt(sme.hasDataSpecification.reference.Count - 1);
                        }
                        return new ModifyRepo.LambdaActionRedrawEntity();
                    });
                }

                // now use the normal mechanism to deal with editMode or not ..                    
                if (sme.hasDataSpecification != null && sme.hasDataSpecification.reference != null && sme.hasDataSpecification.reference.Count > 0)
                {
                    for (int i = 0; i < sme.hasDataSpecification.reference.Count; i++)
                        helper.AddKeyListKeys(stack, String.Format("reference[{0}]", i), sme.hasDataSpecification.reference[i].Keys, repo, package, "All");
                }
            }

            helper.AddGroup(stack, "Kind:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return sme.kind == null; },
                    "Providing kind information is mandatory. Typically you want to model instances. A manufacturer would define types of assets, as well.", breakIfTrue: true),
                new HintCheck( () => { return !sme.kind.IsInstance; },
                    "Please check for kind setting. 'Instance' is the usual choice.", severityLevel: HintCheck.Severity.Notice )
            });
            if (helper.SafeguardAccess(stack, repo, sme.kind, "kind:", "Create data element!", v => {
                sme.kind = new AdminShell.Kind();
                return new ModifyRepo.LambdaActionRedrawEntity();
            })) helper.AddKeyValue(stack, "kind", sme.kind.kind, null, repo, v => { sme.kind.kind = v as string; return new ModifyRepo.LambdaActionNone(); }, new string[] { "Type", "Instance" });

            helper.AddGroup(stack, "Semantic ID", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return sme.semanticId == null || sme.semanticId.IsEmpty; },
                    "The use of semanticId for SubmodelElements is mandatory! Only by this means, an automatic system can identify and understand the meaning of the SubmodelElements and, for example, its unit or logical datatype. The semanticId shall reference to a ConceptDescription within the AAS environment or an external repository, such as IEC CDD or eCl@ss or a company / consortia repository.")
            });
            if (helper.SafeguardAccess(stack, repo, sme.semanticId, "semanticId:", "Create data element!", v =>
            {
                sme.semanticId = new AdminShell.SemanticId();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return sme.semanticId.IsEmpty; },
                        "The use of semanticId for SubmodelElements is mandatory! Only by this means, an automatic system can identify and understand the meaning of the SubmodelElements and, for example, its unit or logical datatype. The semanticId shall reference to a ConceptDescription within the AAS environment or an external repository, such as IEC CDD or eCl@ss or a company / consortia repository.", breakIfTrue: true),
                    new HintCheck( () => { return sme.semanticId[0].type != AdminShell.Key.ConceptDescription; },
                        "The semanticId usually refers to a ConceptDescription within the respective repository.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddKeyListKeys(stack, "semanticId", sme.semanticId.Keys, repo);
            }

            //
            // ConceptDescription <- via semantic ID ?!
            //

            if (sme.semanticId != null && sme.semanticId.Count > 0)
            {
                var cd = env.FindConceptDescription(sme.semanticId.Keys);
                if (cd == null)
                {
                    helper.AddGroup(stack, "ConceptDescription cannot be looked up within the AAS environment!", levelColors[0][0], levelColors[0][1]);
                }
                else
                {
                    DisplayOrEditAasEntityConceptDescription(package, env, cd, editMode, repo, stack, levelColors, embedded: true, hintMode: hintMode);
                }
            }

            //
            // Submodel Element VALUES
            //
            if (sme is AdminShell.Property)
            {
                var p = sme as AdminShell.Property;
                helper.AddGroup(stack, "Property", levelColors[0][0], levelColors[0][1]);

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return p.valueType == null || p.valueType.Trim().Length < 1; },
                        "Please check, if you can provide a value type for the concept. Value types are provided by built-in types of XML Schema Definition 1.1.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddKeyValue(stack, "valueType", p.valueType, null, repo, v => { p.valueType = v as string; return new ModifyRepo.LambdaActionNone(); },
                    comboBoxIsEditable: true, comboBoxItems: new string[] {
                        "anyType", "complexType", "anySimpleType", "anyAtomicType", "anyURI", "base64Binary", "boolean", "date", "dateTime",
                        "dateTimeStamp", "decimal", "integer", "long", "int", "short", "byte", "nonNegativeInteger", "positiveInteger",
                        "unsignedLong", "unsignedShort", "unsignedByte", "nonPositiveInteger", "negativeInteger", "double", "duration",
                        "dayTimeDuration", "yearMonthDuration", "float", "hexBinary", "string", "langString", "time"
                    });

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return p.value == null || p.value.Trim().Length < 1; },
                        "The value of the Property. Please provide a string representation (without quotes, '.' as decimal separator, in XML number representation).", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddKeyValue(stack, "value", p.value, null, repo, v => { p.value = v as string; return new ModifyRepo.LambdaActionNone(); });

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return (p.value == null || p.value.Trim().Length < 1) && (p.valueId == null || p.valueId.IsEmpty); },
                        "Yon can express the value also be referring to a (enumumerated) value in a (the respective) repository. Below, you can create a reference to the value in the external repository.", severityLevel: HintCheck.Severity.Notice)
                });
                if (helper.SafeguardAccess(stack, repo, p.valueId, "valueId:", "Create data element!", v =>
                {
                    p.valueId = new AdminShell.Reference();
                    return new ModifyRepo.LambdaActionRedrawEntity();
                }))
                {
                    helper.AddGroup(stack, "ValueID", levelColors[1][0], levelColors[1][1]);
                    helper.AddKeyListKeys(stack, "valueId", p.valueId.Keys, repo, package, AdminShell.Key.GlobalReference);
                }
            }
            else
            if (sme is AdminShell.File)
            {
                var p = sme as AdminShell.File;
                helper.AddGroup(stack, "File", levelColors[0][0], levelColors[0][1]);

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return p.mimeType == null || p.mimeType.Trim().Length < 1 || p.mimeType.IndexOf('/') < 1 || p.mimeType.EndsWith("/"); },
                        "The mime-type of the file. Mandatory information. See RFC2046.")
                });
                helper.AddKeyValue(stack, "mimeType", p.mimeType, null, repo, v => { p.mimeType = v as string; return new ModifyRepo.LambdaActionNone(); },
                    comboBoxIsEditable: true, comboBoxItems: AdminShell.File.GetPopularMimeTypes());

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return p.value == null || p.value.Trim().Length < 1; },
                        "The path to an external file or a file relative the AASX package root('/'). Files are typically relative to '/aasx/' or sub-directories of it. External files typically comply to an URL, e.g. starting with 'https://..'.", breakIfTrue: true),
                    new HintCheck( () => { return p.value.IndexOf('\\') >= 0; },
                        "Backslashes ('\') are not allow. Please use '/' as path delimiter.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddKeyValue(stack, "value", p.value, null, repo, v => { p.value = v as string; return new ModifyRepo.LambdaActionNone(); },
                    auxButtonTitle: "Choose supplementary file", auxButtonLambda: (o) =>
                    {
                        var ve = helper.SmartSelectAasEntityVisualElement(package.AasEnv, "File", package: package);
                        if (ve != null)
                        {
                            var sf = (ve.GetMainDataObject()) as AdminShell.PackageSupplementaryFile;
                            if (sf != null)
                            {
                                p.value = sf.uri.ToString();
                                return new ModifyRepo.LambdaActionRedrawEntity();
                            }
                        }
                        return new ModifyRepo.LambdaActionNone();
                    });
            }
            else
            if (sme is AdminShell.Blob)
            {
                var p = sme as AdminShell.Blob;
                helper.AddGroup(stack, "Blob", levelColors[0][0], levelColors[0][1]);

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return p.mimeType == null || p.mimeType.Trim().Length < 1 || p.mimeType.IndexOf('/') < 1 || p.mimeType.EndsWith("/"); },
                        "The mime-type of the file. Mandatory information. See RFC2046.")
                });
                helper.AddKeyValue(stack, "mimeType", p.mimeType, null, repo, v => { p.mimeType = v as string; return new ModifyRepo.LambdaActionNone(); },
                    comboBoxIsEditable: true, comboBoxItems: AdminShell.File.GetPopularMimeTypes());

                helper.AddKeyValue(stack, "value", p.value, null, repo, v => { p.value = v as string; return new ModifyRepo.LambdaActionNone(); });
            }
            else
            if (sme is AdminShell.ReferenceElement)
            {
                var p = sme as AdminShell.ReferenceElement;
                helper.AddGroup(stack, "ReferenceElement", levelColors[0][0], levelColors[0][1]);

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return p.value == null || p.value.IsEmpty; },
                        "Please choose the target of the reference. You refer to any Referable, if local within the AAS environment or outside. The semantics of your reference shall be described by the concept referred by semanticId.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddKeyListKeys(stack, "value", p.value.Keys, repo, thePackage, AdminShell.Key.AllElements);
            }
            else
            if (sme is AdminShell.RelationshipElement)
            {
                var p = sme as AdminShell.RelationshipElement;
                helper.AddGroup(stack, "RelationshipElement", levelColors[0][0], levelColors[0][1]);

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return p.first == null || p.first.IsEmpty; },
                        "Please choose the first element of the relationship. In terms of a semantic triple, it would be the subject. The semantics of your reference (the predicate) shall be described by the concept referred by semanticId.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddKeyListKeys(stack, "first", p.first.Keys, repo, thePackage, AdminShell.Key.AllElements);

                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return p.second == null || p.second.IsEmpty; },
                        "Please choose the second element of the relationship. In terms of a semantic triple, it would be the object. The semantics of your reference (the predicate) shall be described by the concept referred by semanticId.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddKeyListKeys(stack, "second", p.second.Keys, repo, thePackage, AdminShell.Key.AllElements);
            }
            else
            if (sme is AdminShell.SubmodelElementCollection)
            {
                var p = sme as AdminShell.SubmodelElementCollection;
                helper.AddGroup(stack, "SubmodelElementCollection", levelColors[0][0], levelColors[0][1]);
                if (p.value != null)
                    helper.AddKeyValue(stack, "# of values", "" + p.value.Count);
            }
            else
                helper.AddGroup(stack, "Submodel Element is unknown!", levelColors[0][0], levelColors[0][1]);
        }

        //
        //
        // --- View
        //
        //

        public void DisplayOrEditAasEntityView(AdminShell.PackageEnv package, AdminShell.AdministrationShellEnv env, AdminShell.AdministrationShell shell, AdminShell.View view, bool editMode, ModifyRepo repo, StackPanel stack, Brush[][] levelColors, bool hintMode = false)
        {
            // 
            // View
            //
            helper.AddGroup(stack, "View", levelColors[0][0], levelColors[0][1]);

            if (editMode)
            {
                // Up/ down/ del
                if (editMode)
                {
                    helper.EntityListUpDownDeleteHelper<AdminShell.View>(stack, repo, shell.views.views, view, env, "View:");
                }

                // let the user control the number of references
                helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                    new HintCheck( () => { return view.containedElements == null || view.containedElements.Count < 1; },
                        "This View currently has no references to SubmodelElements, yet. You could create them by clicking the 'Add ..' button below.", severityLevel: HintCheck.Severity.Notice)
                });
                helper.AddAction(stack, "containedElements:", new string[] { "Add Reference to SubmodelElement", }, repo, (buttonNdx) =>
                {
                    if (buttonNdx is int)
                    {
                        if ((int)buttonNdx == 0)
                        {
                            var ks = helper.SmartSelectAasEntityKeys(package.AasEnv, "SubmodelElement");
                            if (ks != null)
                            {
                                view.AddContainedElement(ks);
                            }
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: view);
                        }
                    }
                    return new ModifyRepo.LambdaActionNone();
                });

            }
            else
            {
                int num = 0;
                if (view.containedElements != null && view.containedElements.reference != null)
                    num = view.containedElements.reference.Count;

                var g = helper.AddSmallGrid(1, 1, new string[] { "*" });
                helper.AddSmallLabelTo(g, 0, 0, content: $"# of containedElements: {num}");
                stack.Children.Add(g);
            }

            helper.AddGroup(stack, "Referable members:", levelColors[1][0], levelColors[1][1]);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return view.idShort == null || view.idShort.Length < 1; },
                    "idShort is mandatory for SubmodelElements. It is a short, unique identifier that is unique just in its context, its name space.", breakIfTrue: true),
                new HintCheck( () => { if (view.idShort == null) return false; return AdminShellUtil.HasWhitespace(view.idShort); },
                    "idShort shall not contain whitespace." )
            });
            helper.AddKeyValue(stack, "idShort", view.idShort, null, repo, v => { view.idShort = v as string; return new ModifyRepo.LambdaActionNone(); });

            helper.AddHintBubble(stack, hintMode, new HintCheck(() => { return view.category != null && view.category.Trim().Length >= 1; }, "The use of category is unusual here.", severityLevel: HintCheck.Severity.Notice));
            helper.AddKeyValue(stack, "category", view.category, null, repo, v => { view.category = v as string; return new ModifyRepo.LambdaActionNone(); },
                comboBoxItems: AdminShell.Referable.ReferableCategoryNames, comboBoxIsEditable: true);

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck(() => { return view.description == null || view.description.langString == null || view.description.langString.Count < 1;  },
                    "The use of an description is recommended to allow the consumer of an view to understand the nature of it.", breakIfTrue: true, severityLevel: HintCheck.Severity.Notice),
                new HintCheck(() => { return view.description.langString.Count < 2; },
                    "Consider having description in multiple langauges.", severityLevel: HintCheck.Severity.Notice)
            });
            if (helper.SafeguardAccess(stack, repo, view.description, "description:", "Create data element!", v => {
                view.description = new AdminShell.Description();
                return new ModifyRepo.LambdaActionRedrawEntity();
            })) helper.AddKeyListLangStr(stack, "description", view.description.langString, repo);

            // HasSemantics

            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return view.semanticId == null || view.semanticId.IsEmpty; },
                    "Check if you want to add an semantic reference to an external repository. Only by adding this, a computer can distinguish, for what the view is really meant for.", severityLevel: HintCheck.Severity.Notice )
            });
            helper.AddGroup(stack, "Semantic ID", levelColors[1][0], levelColors[1][1]);
            if (helper.SafeguardAccess(stack, repo, view.semanticId, "semanticId:", "Create data element!", v => {
                view.semanticId = new AdminShell.SemanticId();
                return new ModifyRepo.LambdaActionRedrawEntity();
            })) helper.AddKeyListKeys(stack, "semanticId", view.semanticId.Keys, repo);

            // hasDataSpecification are MULTIPLE references. That is: multiple x multiple keys!
            helper.AddHintBubble(stack, hintMode, new HintCheck[] {
                new HintCheck( () => { return view.hasDataSpecification == null || view.hasDataSpecification.reference == null || view.hasDataSpecification.reference.Count<1; },
                    "Check if you want to add an data specification link to a global, external ressource. Only by adding this, a human can understand, for what the view is really meant for.", severityLevel: HintCheck.Severity.Notice )
            });
            if (helper.SafeguardAccess(stack, repo, view.hasDataSpecification, "HasDataSpecification:", "Create data element!", v => {
                view.hasDataSpecification = new AdminShell.HasDataSpecification();
                return new ModifyRepo.LambdaActionRedrawEntity();
            }))
            {
                helper.AddGroup(stack, "HasDataSpecification", levelColors[1][0], levelColors[1][1]);

                if (editMode)
                {
                    // let the user control the number of references
                    helper.AddAction(stack, "Specifications:", new string[] { "Add Reference", "Delete last reference" }, repo, (buttonNdx) =>
                    {
                        if (buttonNdx is int)
                        {
                            if ((int)buttonNdx == 0)
                                view.hasDataSpecification.reference.Add(new AdminShell.Reference());

                            if ((int)buttonNdx == 1 && view.hasDataSpecification.reference.Count > 0)
                                view.hasDataSpecification.reference.RemoveAt(view.hasDataSpecification.reference.Count - 1);
                        }
                        return new ModifyRepo.LambdaActionRedrawEntity();
                    });
                }

                // now use the normal mechanism to deal with editMode or not ..                    
                if (view.hasDataSpecification != null && view.hasDataSpecification.reference != null && view.hasDataSpecification.reference.Count > 0)
                {
                    for (int i = 0; i < view.hasDataSpecification.reference.Count; i++)
                        helper.AddKeyListKeys(stack, String.Format("reference[{0}]", i), view.hasDataSpecification.reference[i].Keys, repo, package, "All");
                }
            }

        }

        public void DisplayOrEditAasEntityViewReference(AdminShell.PackageEnv package, AdminShell.AdministrationShellEnv env, AdminShell.View view, AdminShell.ContainedElementRef reference, bool editMode, ModifyRepo repo, StackPanel stack, Brush[][] levelColors)
        {
            // 
            // View
            //
            helper.AddGroup(stack, "Reference (containedElement) of View ", levelColors[0][0], levelColors[0][1]);

            if (editMode)
            {
                // Up/ down/ del
                if (editMode)
                {
                    helper.EntityListUpDownDeleteHelper<AdminShell.ContainedElementRef>(stack, repo, view.containedElements.reference, reference, "Reference:");
                }
            }

            // normal reference
            helper.AddKeyListKeys(stack, "containedElement", reference.Keys, repo, package, "Asset");
        }

        //
        //
        // --- Overall calling function
        //
        //

        public void DisplayClear()
        {
            ElementStackPanel.Children.Clear();
        }

        public void DisplayOrEditVisualAasxElement(AdminShell.PackageEnv package, VisualElementGeneric entity, bool editMode, bool hintMode = false, AdminShell.PackageEnv[] auxPackages = null, IFlyoutProvider flyoutProvider = null)
        {
            //
            // Start
            //
            if (ElementStackPanel == null || entity == null)
                return;
            var stack = ElementStackPanel;

            Brush[][] levelColors = new Brush[][]
            {
                new Brush[] { Brushes.DarkBlue, Brushes.White },
                new Brush[] { Brushes.LightBlue, Brushes.Black }
            };

            // hint mode disable, when not edit

            hintMode = hintMode && editMode;

            // remember objects for UI thread / redrawing
            this.thePackage = package;
            this.theEntity = entity;
            this.theEditMode = editMode;
            helper.auxPackages = auxPackages;
            helper.flyoutProvider = flyoutProvider;

            // modify repository
            ModifyRepo repo = null;
            if (editMode)
            {
                repo = theModifyRepo;
                repo.Clear();
            }

            // visual clear
            stack.Children.Clear();

            // 
            // Dispatch
            //

            if (entity is VisualElementEnvironmentItem)
            {
                var x = entity as VisualElementEnvironmentItem;
                DisplayOrEditAasEntityAasEnv(package, x.theEnv, x.theItemType, editMode, repo, stack, levelColors, hintMode: hintMode);
            }
            else
            if (entity is VisualElementAdminShell)
            {
                var x = entity as VisualElementAdminShell;
                DisplayOrEditAasEntityAas(package, x.theEnv, x.theAas, editMode, repo, stack, levelColors, hintMode: hintMode);
            }
            else
            if (entity is VisualElementAsset)
            {
                var x = entity as VisualElementAsset;
                DisplayOrEditAasEntityAsset(package, x.theEnv, x.theAsset, editMode, repo, stack, levelColors, hintMode: hintMode);
            }
            else
             if (entity is VisualElementSubmodelRef)
            {
                var x = entity as VisualElementSubmodelRef;
                AdminShell.AdministrationShell aas = null;
                if (x.Parent is VisualElementAdminShell)
                    aas = (x.Parent as VisualElementAdminShell).theAas;
                DisplayOrEditAasEntitySubmodelRef(package, x.theEnv, aas, x.theSubmodelRef, x.theSubmodel, editMode, repo, stack, levelColors, hintMode: hintMode);
            }
            else
            if (entity is VisualElementSubmodelElement)
            {
                var x = entity as VisualElementSubmodelElement;
                DisplayOrEditAasEntitySubmodelElement(package, x.theEnv, x.theContainer, x.theWrapper, editMode, repo, stack, levelColors, hintMode: hintMode);
            }
            else
            if (entity is VisualElementConceptDescription)
            {
                var x = entity as VisualElementConceptDescription;
                DisplayOrEditAasEntityConceptDescription(package, x.theEnv, x.theCD, editMode, repo, stack, levelColors, hintMode: hintMode);
            }
            else
            if (entity is VisualElementView)
            {
                var x = entity as VisualElementView;
                if (x.Parent != null && x.Parent is VisualElementAdminShell) 
                    DisplayOrEditAasEntityView(package, x.theEnv, (x.Parent as VisualElementAdminShell).theAas, x.theView, editMode, repo, stack, levelColors, hintMode: hintMode);
                else
                    helper.AddGroup(stack, "View is corrupted!", levelColors[0][0], levelColors[0][1]);
            }
            else
            if (entity is VisualElementReference)
            {
                var x = entity as VisualElementReference;
                if (x.Parent != null && x.Parent is VisualElementView)
                    DisplayOrEditAasEntityViewReference(package, x.theEnv, (x.Parent as VisualElementView).theView, (AdminShell.ContainedElementRef) x.theReference, editMode, repo, stack, levelColors);
                else
                    helper.AddGroup(stack, "Reference is corrupted!", levelColors[0][0], levelColors[0][1]);
            }
            else
            if (entity is VisualElementSupplementalFile)
            {
                var x = entity as VisualElementSupplementalFile;
                DisplayOrEditAasEntitySupplementaryFile(package, x.theFile, editMode, repo, stack, levelColors);
            }
            else
                helper.AddGroup(stack, "Entity is unknown!", levelColors[0][0], levelColors[0][1]);

        }

        #endregion
    }
}
