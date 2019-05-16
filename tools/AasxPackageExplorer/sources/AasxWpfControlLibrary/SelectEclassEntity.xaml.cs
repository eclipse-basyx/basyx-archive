using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using System.Xml;

using AdminShellNS;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AasxPackageExplorer
{
    /// <summary>
    /// Interaktionslogik für SelectAasEntityDialogue.xaml
    /// </summary>
    public partial class SelectEclassEntity : Window
    {
        private string eclassFullPath = null;

        public string ResultIRDI = null;
        public AdminShell.ConceptDescription ResultCD = null;

        public class SelectItem
        {
            public string Entity { get; set; }
            public string IRDI { get; set; }
            public string Info { get; set; }
            public XmlNode ContentNode { get; set; }

            public SelectItem()
            {
                Entity = "";
                IRDI = "";
                Info = "";
                ContentNode = null;
            }

            public SelectItem(string entity, string irdi, string info, XmlNode content)
            {
                Entity = entity;
                IRDI = irdi;
                Info = info;
                ContentNode = content;
            }
        }

        public SelectEclassEntity(string eclassFullPath = null)
        {
            InitializeComponent();
            this.eclassFullPath = eclassFullPath;
        }

        private readonly BackgroundWorker worker = new BackgroundWorker();

        private class BackgroundArguments
        {
            public int maxItems = 1000;
            public List<string> eclassFiles = new List<string>();
            public string searchText = "";
            public bool searchInClasses = true;
            public bool searchInDatatypes = true;
            public bool searchInProperties = true;
            public bool searchInUnits = true;
            public int numClass = 0;
            public int numDatatype = 0;
            public int numProperty = 0;
            public bool tooMany = false;
            public List<SelectItem> items = new List<SelectItem>();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            worker.DoWork += worker_DoWork;
            worker.RunWorkerCompleted += worker_RunWorkerCompleted;
        }

        private void ButtonSearchStart_Click(object sender, RoutedEventArgs e)
        {
            if (!worker.IsBusy && eclassFullPath != null)
            {
                SearchProgress.Value = 0;
                EntityList.Items.Clear();
                EntityContent.Text = "";
                var args = new BackgroundArguments();

                args.searchInClasses = this.SearchInClasses.IsChecked == true;
                args.searchInDatatypes = this.SearchInDatatypes.IsChecked == true;
                args.searchInProperties = this.SearchInProperties.IsChecked == true;
                args.searchInUnits = this.SearchInUnits.IsChecked == true;

                args.eclassFiles.Clear();
                args.eclassFiles.AddRange(System.IO.Directory.GetFiles(eclassFullPath, "*.xml"));

                args.searchText = SearchFor.Text.Trim().ToLower();
                if (args.searchText.Length < 2)
                    MessageBox.Show(this, "The search string needs to comprise at least 2 characters in order to limit the amount of search results!", "Info", MessageBoxButton.OK, MessageBoxImage.Exclamation);
                else
                {
                    worker.RunWorkerAsync(args);
                }
            }
        }

        private void SearchFor_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                e.Handled = true;
                // fake mouse click
                ButtonSearchStart_Click(null, null);
            }
        }

        private void worker_DoWork(object sender, DoWorkEventArgs e)
        {
            // run all background tasks here

            var a = (e.Argument as BackgroundArguments);
            if (a == null)
                return;
            if (a.eclassFiles == null || a.eclassFiles.Count < 1)
                return;

            double progressPerFile = 1.0 / a.eclassFiles.Count;

            for (int fileNdx = 0; fileNdx < a.eclassFiles.Count; fileNdx++)
            {

                long totalSize = 1 + new System.IO.FileInfo(a.eclassFiles[fileNdx]).Length;
                try
                {

                    using (FileStream fileSteam = File.OpenRead(a.eclassFiles[fileNdx]))
                    {
                        XmlReaderSettings settings;

                        settings = new XmlReaderSettings();
                        settings.ConformanceLevel = ConformanceLevel.Document;

                        int numElems = 0;
                        using (XmlReader reader = XmlReader.Create(fileSteam, settings))
                        {
                            while (reader.Read())
                            {
                                if (reader.IsStartElement())
                                {
                                    string searchForType = null;
                                    if (reader.Name == "ontoml:class" && a.searchInClasses)
                                    {
                                        searchForType = "cls";
                                        a.numClass++;
                                    }
                                    if (reader.Name == "ontoml:datatype" && a.searchInDatatypes)
                                    {
                                        searchForType = "dt";
                                        a.numDatatype++;
                                    }

                                    if (reader.Name == "ontoml:property" && a.searchInProperties)
                                    {
                                        searchForType = "prop";
                                        a.numProperty++;
                                    }
                                    if (reader.Name == "unitsml:Unit" && a.searchInUnits)
                                    {
                                        searchForType = "unit";
                                        a.numProperty++;
                                    }

                                    if (searchForType != null)
                                    {
                                        // always get the XmlDocument (can either read outer xml or the same as a node)
                                        var doc = new XmlDocument();
                                        var node = doc.ReadNode(reader);
                                        // contains the text
                                        if (node.OuterXml.Trim().ToLower().Contains(a.searchText))
                                        {
                                            // get the IRDI
                                            if (node.Attributes != null && node.Attributes["id"] != null)
                                            {
                                                var irdi = node.Attributes["id"].InnerText;
                                                var info = "";
                                                if (searchForType == "prop" && node.HasChildNodes)
                                                {
                                                    var n2 = node.SelectSingleNode("preferred_name");
                                                    if (n2 != null && n2.HasChildNodes)
                                                        foreach (var c2 in n2.ChildNodes)
                                                            if (c2 is XmlNode && (c2 as XmlNode).Name == "label")
                                                                info = (c2 as XmlNode).InnerText;
                                                }
                                                a.items.Add(new SelectItem(searchForType, irdi, info, node));

                                                // now more than max
                                                if (a.items.Count > a.maxItems)
                                                {
                                                    a.tooMany = true;
                                                    break;
                                                }
                                            }
                                        }
                                        // count anyway
                                    }

                                    numElems++;
                                    if (numElems % 500 == 0)
                                    {
                                        long currPos = fileSteam.Position;
                                        double frac = Math.Min( 100.0d * progressPerFile * (fileNdx) + (100.0d * currPos) * progressPerFile / totalSize, 100.0);
                                        SearchProgress.Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Background, new Action(() => this.SearchProgress.Value = frac));
                                    }
                                }
                            }
                        }
                    }
                }
                catch { }

            }

            // set result
            e.Result = a;
        }

        private void worker_RunWorkerCompleted(object sender,
                                               RunWorkerCompletedEventArgs e)
        {
            //update ui once worker complete his work
            SearchProgress.Value = 100;

            // access
            var a = (e.Result as BackgroundArguments);
            if (a == null)
                return;

            // may be inform upon too many elements
            if (a.tooMany)
                MessageBox.Show(this, "Too many search result. Search aborted!", "Search entities", MessageBoxButton.OK, MessageBoxImage.Exclamation);

            // sort
            a.items.Sort(delegate (SelectItem si1, SelectItem si2)
            {
                if (si1.Entity != si2.Entity)
                    return si1.Entity.CompareTo(si2.Entity);
                else
                    return si1.IRDI.CompareTo(si2.IRDI);
            });

            // re-fill into the UI list
            EntityList.Items.Clear();
            foreach (var it in a.items)
                EntityList.Items.Add(it);
        }

        private void ButtonSelect_Click(object sender, RoutedEventArgs e)
        {
            if (FinalizeSelection())
                this.DialogResult = true;
        }

        private void Tree_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            if (FinalizeSelection())
                this.DialogResult = true;
        }

        private void EntityList_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            // access
            if (EntityContent == null || EntityList == null || EntityList.SelectedItem == null || !(EntityList.SelectedItem is SelectItem))
                return;

            // set
            var si = EntityList.SelectedItem as SelectItem;
            if (si.ContentNode != null)
                EntityContent.Text = si.ContentNode.OuterXml;
            else
                EntityContent.Text = "";
        }

        private bool FinalizeSelection()
        {
            // get the IRDI
            var si = EntityList.SelectedItem as SelectItem;
            if (si == null)
                return false;

            // simply put the IRDI
            this.ResultIRDI = si.IRDI;

            // special case: property selected
            if (si.Entity == "prop")
            {
                var input = new List<SelectItem>();
                input.Add(si);
                foreach (SelectItem di in EntityList.Items)
                    if (di != null && di.Entity == si.Entity && di.IRDI == si.IRDI && si != di)
                        input.Add(di);

                // own function
                this.ResultCD = GenerateConceptDescription(input);
            }

            // ok
            return true;
        }

        private string GetChildInnerText (XmlNode node, string childElemName, string otherwise)
        {
            if (node == null)
                return otherwise;

            var n = node.SelectSingleNode(childElemName);
            if (n != null)
                return n.InnerText;

            return otherwise;
        }

        private void FildChildLangStrings (XmlNode node, string childName, string childChildName, string langCodeAttrib, Action<AdminShell.LangStr> action)
        {
            if (node == null || action == null)
                return;
            var n1 = node.SelectSingleNode(childName);
            if (n1 != null)
            {
                var nl = n1.SelectNodes(childChildName);
                foreach (XmlNode ni in nl)
                    if (ni.Attributes != null && ni.Attributes[langCodeAttrib] != null)
                    {
                        var ls = new AdminShell.LangStr();
                        ls.lang = ni.Attributes["language_code"].InnerText;
                        ls.str = ni.InnerText;
                        action(ls);
                    }
            }
        }

        private AdminShell.ConceptDescription GenerateConceptDescription (List<SelectItem> input)
        {
            // access
            if (input == null || input.Count < 1)
                return null;

            // new cd
            var res = new AdminShell.ConceptDescription();

            var ds = new AdminShell.DataSpecificationIEC61360();
            res.embeddedDataSpecification.dataSpecificationContent.dataSpecificationIEC61360 = ds;

            // over all, first is significant
            for (int i=0; i<input.Count; i++)
            {
                var node = input[i].ContentNode;
                if (node == null)
                    continue;

                XmlNode n1;

                // first is significant
                if (i == 0)
                {
                    res.identification.idType = "IRDI";
                    res.identification.id = input[i].IRDI;

                    res.AddIsCaseOf(AdminShell.Reference.CreateIrdiReference(input[i].IRDI));

                    res.administration = new AdminShell.Administration();
                    n1 = node.SelectSingleNode("revision");
                    if (n1 != null)
                        res.administration.revision = "" + n1.InnerText;

                    FildChildLangStrings(node, "short_name", "label", "language_code", (ls) =>
                    {
                        ds.shortName = ls.str;
                        res.idShort = ls.str;
                    });
                }

                // all have language texts
                FildChildLangStrings(node, "preferred_name", "label", "language_code", (ls) =>
                {
                    if (ds.preferredName == null)
                        ds.preferredName = new AdminShell.LangStringIEC61360();
                    ds.preferredName.langString.Add(ls);
                });

                FildChildLangStrings(node, "definition", "text", "language_code", (ls) =>
                {
                    if (ds.definition == null)
                        ds.definition = new AdminShell.LangStringIEC61360();
                    ds.definition.langString.Add(ls);
                });

            }

            // ok
            return res;
        }
    }
}
