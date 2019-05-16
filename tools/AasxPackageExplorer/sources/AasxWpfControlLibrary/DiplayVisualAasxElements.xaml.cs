using Logging;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
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
    /// Interaktionslogik für DiplayAasxElements.xaml
    /// </summary>
    public partial class DiplayVisualAasxElements : UserControl
    {
        private List<VisualElementGeneric> displayedTreeViewLines = new List<VisualElementGeneric>();
        private TreeViewLineCache treeViewLineCache = null;

        #region Public events and properties
        //
        // Public events and properties
        //

        public event EventHandler SelectedItemChanged = null;

        public VisualElementGeneric SelectedItem
        {
            get
            {
                return tv1.SelectedItem as VisualElementGeneric;
            }
        }

        // Enumerate all the descendants of the visual object.
        static public void EnumVisual(Visual myVisual, object dataObject)
        {
            for (int i = 0; i < VisualTreeHelper.GetChildrenCount(myVisual); i++)
            {
                // Retrieve child visual at specified index value.
                Visual childVisual = (Visual)VisualTreeHelper.GetChild(myVisual, i);

                // Do processing of the child visual object.
                var tvi = childVisual as TreeViewItem;
                if (tvi != null && tvi.DataContext != null && tvi.DataContext == dataObject) {
                    tvi.BringIntoView();
                    tvi.IsSelected = true;
                    tvi.Focus();
                    return;
                }

                // Enumerate children of the child visual object.
                EnumVisual(childVisual, dataObject);
            }
        }

        public void Woodoo(object dataObject)
        {
            if (dataObject == null)
                return;
            // VisualTreeHelper.GetChild(tv1, )
            displayedTreeViewLines[0].IsSelected = false;
            EnumVisual(tv1, dataObject);
/*
            TreeViewItem tvItem = (TreeViewItem)tv1
                          .ItemContainerGenerator
                          .ContainerFromItem(dataObject);
            tvItem.Focus();
*/
            tv1.UpdateLayout();
        }

        /// <summary>
        /// Activates the caching of the "expanded" states of the tree, even if the tree is multiple
        /// times rebuilt via <code>RebuildAasxElements</code>.
        /// </summary>
        public void ActivateElementStateCache ()
        {
            this.treeViewLineCache = new TreeViewLineCache();
        }

        public new Brush Background
        {
            get
            {
                return tv1.Background;
            }
            set
            {
                tv1.Background = value;
            }
        }

        #endregion

        #region XAML
        //
        // XAML / UI
        //

        public DiplayVisualAasxElements()
        {
            InitializeComponent();
        }

        private void tv1_RequestBringIntoView(object sender, RequestBringIntoViewEventArgs e)
        {
            base.BringIntoView();
            // e.Handled = true;

            var scrollViewer = tv1.Template.FindName("_tv_scrollviewer_", tv1) as ScrollViewer;
            if (scrollViewer != null)
                Dispatcher.BeginInvoke(System.Windows.Threading.DispatcherPriority.Loaded, (Action)(() => scrollViewer.ScrollToLeftEnd()));
        }

        private void tv1_SelectedItemChanged(object sender, RoutedPropertyChangedEventArgs<object> e)
        {
            if (sender != tv1)
                return;
            if (SelectedItemChanged != null)
                SelectedItemChanged(this, e);
        }

        public void Refresh()
        {
            tv1.Items.Refresh();
            tv1.UpdateLayout();
        }

        public void ExpandOnlyBranchWithin(VisualElementGeneric node)
        {
            // make a list of all top-level nodes
            var list = new List<VisualElementGeneric>();
            foreach (var dl in displayedTreeViewLines)
                dl.CollectListOfTopLevelNodes(list);
            // contract ALL nodes
            foreach (var tl in list)
                tl.ForAllDescendents((x) => { (x as VisualElementGeneric).IsExpanded = false; });
            // expand all nodes having descendent
            foreach (var tl in list)
                if (tl.CheckIfDescendent(node))
                    tl.ForAllDescendents((x) => { (x as VisualElementGeneric).IsExpanded = true; });
        }

        #endregion

        #region Elememt view drawing / handling
        
        //
        // Element View Drawing
        //

        private VisualElementGeneric SearchInListOfVisualElements (VisualElementGeneric tvl, object dataObject)
        {
            if (tvl == null || dataObject == null)
                return null;
            if (tvl.GetMainDataObject() == dataObject)
                return tvl;
            foreach (var mem in tvl.Members) { 
                var x = SearchInListOfVisualElements(mem, dataObject);
                if (x != null)
                    return x;
            }
            return null;
        }

        public VisualElementGeneric SearchVisualElementOnMainDataObject(object dataObject)
        {
            if (displayedTreeViewLines == null)
                return null;
            foreach (var tvl in displayedTreeViewLines)
            {
                var x = SearchInListOfVisualElements(tvl, dataObject);
                if (x != null)
                    return x;
            }
            return null;
        }


        /// <summary>
        /// Return true, if <code>mem</code> has to be deleted, because not in filter.
        /// </summary>
        /// <param name="mem"></param>
        /// <param name="fullFilterElementName"></param>
        /// <returns></returns>
        public bool FilterLeafsOfVisualElements(VisualElementGeneric mem, string fullFilterElementName)
        {
            if (fullFilterElementName == null)
                return (false);
            fullFilterElementName = fullFilterElementName.Trim().ToLower();
            if (fullFilterElementName == "")
                return (false);

            // has Members -> is not leaf!
            if (mem.Members != null && mem.Members.Count > 0)
            {
                // go into non-leafs mode -> simply go over list
                var todel = new List<VisualElementGeneric>();
                foreach (var x in mem.Members)
                    if (FilterLeafsOfVisualElements(x, fullFilterElementName))
                        todel.Add(x);
                // delete items on list
                foreach (var td in todel)
                    mem.Members.Remove(td);
            }
            else
            {
                // this member is a leaf!!
                var isIn = false;
                var mdo = mem.GetMainDataObject();
                if (mdo != null && mdo is AdminShell.Referable)
                {
                    var mdoen = (mdo as AdminShell.Referable).GetElementName().Trim().ToLower();
                    isIn = fullFilterElementName.IndexOf(mdoen) >= 0;                        
                }
                if (mdo != null && mdo is AdminShell.Reference)
                {
                    var mdoen = (mdo as AdminShell.Reference).GetElementName().Trim().ToLower();
                    isIn = fullFilterElementName.IndexOf(mdoen) >= 0;
                }
                return !isIn;
            }
            return false;
        }

        public void RebuildAasxElements(
            AdminShell.AdministrationShellEnv env = null, 
            AdminShell.PackageEnv package = null, 
            AdminShell.PackageEnv[] auxPackages = null, bool editMode = false, string filterElementName = null)
        {
            // clear tree
            displayedTreeViewLines = new List<VisualElementGeneric>();

            // valid?
            if (env != null)
            {

                // generate lines, add
                var x = Generators.GenerateVisualElementsFromShellEnv(treeViewLineCache, env, package, editMode, expandMode: 1);
                foreach (var xx in x)
                    displayedTreeViewLines.Add(xx);

                // more?
                if (auxPackages != null)
                    foreach (var aux in auxPackages)
                    {
                        var x2 = Generators.GenerateVisualElementsFromShellEnv(treeViewLineCache, aux.AasEnv, aux, editMode, expandMode: 1);
                        foreach (var xx in x2)
                            displayedTreeViewLines.Add(xx);
                    }

                // may be filter
                if (filterElementName != null)
                    foreach (var dtl in displayedTreeViewLines)
                        // it is not likely, that we have to delete on this level, therefore don't care
                        FilterLeafsOfVisualElements(dtl, filterElementName);

                // any of these lines?
                if (displayedTreeViewLines.Count < 1)
                {
                    // emergency
                    displayedTreeViewLines.Add(new VisualElementEnvironmentItem(null /* no parent */, treeViewLineCache, package, env, VisualElementEnvironmentItem.ItemType.EmptySet));
                }

            }

            // redraw
            tv1.ItemsSource = displayedTreeViewLines;
            tv1.UpdateLayout();

            // select 1st
            if (displayedTreeViewLines.Count > 0)
                displayedTreeViewLines[0].IsSelected = true;
        }

        #endregion

    }
}
