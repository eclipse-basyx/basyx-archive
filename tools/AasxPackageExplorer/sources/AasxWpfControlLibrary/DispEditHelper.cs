using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;

using AdminShellNS;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AasxPackageExplorer
{
    //
    // Hinting (will be used below)
    //

    public class HintCheck
    {
        public enum Severity { High, Notice };

        public Func<bool> CheckPred = null;
        public string TextToShow = null;
        public bool BreakIfTrue = false;
        public Severity SeverityLevel = Severity.High;

        /// <summary>
        /// Formulate a check, which can cause a hint.
        /// </summary>
        /// <param name="check">Lambda to check. If returns true, trigger the hint.</param>
        /// <param name="text">Hint in plain text form.</param>
        /// <param name="breakIfTrue">If check was true, abort checking of further hints. Use: avoid checking of null for every hint.</param>
        public HintCheck(Func<bool> check, string text, bool breakIfTrue = false, Severity severityLevel = Severity.High)
        {
            this.CheckPred = check;
            this.TextToShow = text;
            this.BreakIfTrue = breakIfTrue;
            this.SeverityLevel = severityLevel;
        }
    }

    //
    // Helpers
    //

    public class DispEditHelper
    {
        private string[] defaultLanguages = new string[] { "DE", "EN", "FR", "ES", "IT", "CN", "KR" };

        public AdminShell.PackageEnv[] auxPackages = null;

        public IFlyoutProvider flyoutProvider = null;

        public Grid AddSmallGrid(int rows, int cols, string[] colWidths = null, Thickness margin = new Thickness())
        {
            var g = new Grid();
            g.Margin = margin;

            // Cols
            for (int ci = 0; ci < cols; ci++)
            {
                var gc = new ColumnDefinition();
                // default
                gc.Width = new GridLength(1.0, GridUnitType.Star);
                // width definition
                if (colWidths != null && colWidths.Length > ci && colWidths[ci] != null)
                {
                    double scale = 1.0;
                    var kind = colWidths[ci].Trim();
                    var m = Regex.Match(colWidths[ci].Trim(), @"([0-9.+-]+)(.$)");
                    if (m.Success && m.Groups.Count >= 2)
                    {
                        var scaleSt = m.Groups[1].ToString().Trim();
                        double d = 1.0;
                        if (Double.TryParse(scaleSt, NumberStyles.Float, CultureInfo.InvariantCulture, out d))
                            scale = d;
                        kind = m.Groups[2].ToString().Trim();
                    }
                    if (kind == "#")
                        gc.Width = new GridLength(scale, GridUnitType.Auto);
                    if (kind == "*")
                        gc.Width = new GridLength(scale, GridUnitType.Star);
                    if (kind == ":")
                        gc.Width = new GridLength(scale, GridUnitType.Pixel);
                }
                g.ColumnDefinitions.Add(gc);
            }

            // Rows
            for (int ri = 0; ri < rows; ri++)
            {
                var gr = new RowDefinition();
                g.RowDefinitions.Add(gr);
            }

            return g;
        }

        public void AddGroup(StackPanel view, string name, Brush background, Brush foreground)
        {
            var g = new Grid();
            g.Margin = new Thickness(0, 13, 0, 0);

            var l = new Label();
            l.Margin = new Thickness(0, 0, 0, 0);
            l.Padding = new Thickness(5, 0, 0, 0);
            l.Background = background;
            l.Foreground = foreground;
            l.Content = "" + name;
            l.FontWeight = FontWeights.Bold;
            Grid.SetRow(l, 0);
            Grid.SetColumn(l, 0);
            g.Children.Add(l);
            view.Children.Add(g);
        }

        public Label AddSmallLabelTo(Grid g, int row, int col, Thickness margin = new Thickness(), Thickness padding = new Thickness(), string content = "", Brush foreground = null, Brush background = null)
        {
            var lab = new Label();
            lab.Margin = margin;
            lab.Padding = padding;
            if (foreground != null)
                lab.Foreground = foreground;
            if (background != null)
                lab.Background = background;
            lab.Content = content;
            Grid.SetRow(lab, row);
            Grid.SetColumn(lab, col);
            g.Children.Add(lab);
            return (lab);
        }

        public WrapPanel AddSmallWrapPanelTo(Grid g, int row, int col, Thickness margin = new Thickness(), Brush background = null)
        {
            var wp = new WrapPanel();
            wp.Margin = margin;
            if (background != null)
                wp.Background = background;
            Grid.SetRow(wp, row);
            Grid.SetColumn(wp, col);
            g.Children.Add(wp);
            return (wp);
        }

        public TextBox AddSmallTextBoxTo(Grid g, int row, int col, Thickness margin = new Thickness(), Thickness padding = new Thickness(), string text = "", Brush foreground = null, Brush background = null)
        {
            var tb = new TextBox();
            tb.Margin = margin;
            tb.Padding = padding;
            if (foreground != null)
                tb.Foreground = foreground;
            if (background != null)
                tb.Background = background;
            tb.Text = text;
            Grid.SetRow(tb, row);
            Grid.SetColumn(tb, col);
            g.Children.Add(tb);
            return (tb);
        }

        public ComboBox AddSmallComboBoxTo(Grid g, int row, int col, Thickness margin = new Thickness(), Thickness padding = new Thickness(), string text = "", Brush foreground = null, Brush background = null, int minWidth = -1, int maxWidth = -1, string[] items = null, bool isEditable = false)
        {
            var cb = new ComboBox();
            cb.Margin = margin;
            cb.Padding = padding;
            if (foreground != null)
                cb.Foreground = foreground;
            if (background != null)
                cb.Background = background;
            if (minWidth >= 0)
                cb.MinWidth = minWidth;
            if (maxWidth >= 0)
                cb.MaxWidth = maxWidth;
            if (items != null)
                foreach (var i in items)
                    cb.Items.Add("" + i);
            cb.Text = text;
            cb.IsEditable = isEditable;
            cb.HorizontalAlignment = HorizontalAlignment.Left;
            Grid.SetRow(cb, row);
            Grid.SetColumn(cb, col);
            g.Children.Add(cb);
            return (cb);
        }

        public void SmallComboBoxSelectNearestItem(ComboBox cb, string text)
        {
            if (cb == null || text == null || cb.Items == null)
                return;
            int foundI = -1;
            for (int i = 0; i < cb.Items.Count; i++)
                if (cb.Items[i].ToString().Trim().ToLower() == text.Trim().ToLower())
                    foundI = i;
            if (foundI >= 0)
                cb.SelectedIndex = foundI;
        }

        public Button AddSmallButtonTo(Grid g, int row, int col, Thickness margin = new Thickness(), Thickness padding = new Thickness(), string content = "", Brush foreground = null, Brush background = null)
        {
            var but = new Button();
            but.Margin = margin;
            but.Padding = padding;
            if (foreground != null)
                but.Foreground = foreground;
            if (background != null)
                but.Background = background;
            but.Content = content;
            Grid.SetRow(but, row);
            Grid.SetColumn(but, col);
            g.Children.Add(but);
            return (but);
        }

        public CheckBox AddSmallCheckBoxTo(Grid g, int row, int col, Thickness margin = new Thickness(), Thickness padding = new Thickness(), string content = "", bool isChecked = false, Brush foreground = null, Brush background = null)
        {
            var cb = new CheckBox();
            cb.Margin = margin;
            cb.Padding = padding;
            if (foreground != null)
                cb.Foreground = foreground;
            if (background != null)
                cb.Background = background;
            cb.Content = content;
            cb.IsChecked = isChecked;
            Grid.SetRow(cb, row);
            Grid.SetColumn(cb, col);
            g.Children.Add(cb);
            return (cb);
        }

        public void AddKeyValue(
            StackPanel view, string key, string value, string nullValue = null,
            ModifyRepo repo = null, Func<object, ModifyRepo.LambdaAction> setValue = null,
            string[] comboBoxItems = null, bool comboBoxIsEditable = false,
            string auxButtonTitle = null, Func<object, ModifyRepo.LambdaAction> auxButtonLambda = null)
        {
            // draw anyway?
            if (repo != null && value == null)
            {
                // generate default value
                value = "";
            }
            else
            {
                // normal handling
                if (value == null && nullValue == null)
                    return;
                if (value == null)
                    value = nullValue;
            }


            // Grid
            var g = new Grid();
            g.Margin = new Thickness(0, 1, 0, 1);
            var gc1 = new ColumnDefinition();
            gc1.Width = new GridLength(100);
            g.ColumnDefinitions.Add(gc1);
            var gc2 = new ColumnDefinition();
            gc2.Width = new GridLength(1.0, GridUnitType.Star);
            g.ColumnDefinitions.Add(gc2);
            var auxButton = repo != null && auxButtonTitle != null && auxButtonLambda != null;
            if (auxButton)
            {
                var gc3 = new ColumnDefinition();
                gc3.Width = new GridLength(1.0, GridUnitType.Auto);
                g.ColumnDefinitions.Add(gc3);
            }

            // Label for key
            AddSmallLabelTo(g, 0, 0, padding: new Thickness(5, 0, 0, 0), content: "" + key + ":");

            // Label / TextBox for value
            if (repo == null)
            {
                AddSmallLabelTo(g, 0, 1, padding: new Thickness(2, 0, 0, 0), content: "" + value);
            }
            else if (comboBoxItems != null)
            {
                // guess some max width, in order 
                var maxc = 5;
                foreach (var c in comboBoxItems)
                    if (c.Length > maxc)
                        maxc = c.Length;
                var maxWidth = 10 * maxc; // about one em
                // use combo box
                repo.RegisterControl(
                    AddSmallComboBoxTo(g, 0, 1, margin: new Thickness(0, 2, 2, 2), padding: new Thickness(2, 0, 2, 0), text: "" + value,
                        minWidth: 60, maxWidth: maxWidth, items: comboBoxItems, isEditable: comboBoxIsEditable),
                    setValue);
            }
            else
            {
                // use plain text box
                repo.RegisterControl(AddSmallTextBoxTo(g, 0, 1, margin: new Thickness(0, 2, 2, 2), text: "" + value),
                    setValue);
            }

            if (auxButton)
            {
                repo.RegisterControl(AddSmallButtonTo(g, 0, 2, margin: new Thickness(2, 2, 2, 2), padding: new Thickness(5, 0, 5, 0), content: auxButtonTitle),
                    auxButtonLambda);
            }

            // in total
            view.Children.Add(g);
        }

        public void AddKeyMultiValue(StackPanel view, string key, string[][] value, string[] widths)
        {
            // draw anyway?
            if (value == null)
                return;

            // get some dimensions
            var rows = value.Length;
            var cols = 1;
            foreach (var r in value)
                if (r.Length > cols)
                    cols = r.Length;

            // Grid
            var g = new Grid();
            g.Margin = new Thickness(0, 0, 0, 0);

            var gc1 = new ColumnDefinition();
            gc1.Width = new GridLength(100);
            g.ColumnDefinitions.Add(gc1);

            for (int c = 0; c < cols; c++)
            {
                var gc2 = new ColumnDefinition();
                if (widths[c] == "*")
                    gc2.Width = new GridLength(1.0, GridUnitType.Star);
                else
                if (widths[c] == "#")
                    gc2.Width = new GridLength(1.0, GridUnitType.Auto);
                else
                {
                    int i;
                    if (Int32.TryParse(widths[c], out i))
                        gc2.Width = new GridLength(i);
                }
                g.ColumnDefinitions.Add(gc2);
            }

            for (int r = 0; r < rows; r++)
            {
                var gr = new RowDefinition();
                gr.Height = new GridLength(1.0, GridUnitType.Auto);
                g.RowDefinitions.Add(gr);
            }

            // Label for key
            var l1 = new Label();
            l1.Margin = new Thickness(0, 0, 0, 0);
            l1.Padding = new Thickness(5, 0, 0, 0);
            l1.Content = "" + key + ":";
            Grid.SetRow(l1, 0);
            Grid.SetColumn(l1, 0);
            g.Children.Add(l1);

            // Label for any values
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++)
                {
                    var l2 = new Label();
                    l2.Margin = new Thickness(0, 0, 0, 0);
                    l2.Padding = new Thickness(2, 0, 0, 0);
                    l2.Content = "" + value[r][c];
                    Grid.SetRow(l2, 0 + r);
                    Grid.SetColumn(l2, 1 + c);
                    g.Children.Add(l2);
                }

            // in total
            view.Children.Add(g);
        }

        public void AddAction(StackPanel view, string key, string[] actionStr, ModifyRepo repo = null, Func<object, ModifyRepo.LambdaAction> action = null)
        {
            // access 
            if (repo == null || actionStr == null)
                return;
            var numButton = actionStr.Length;

            // Grid
            var g = new Grid();
            g.Margin = new Thickness(0, 5, 0, 5);

            // 0 key
            var gc = new ColumnDefinition();
            gc.Width = new GridLength(1.0, GridUnitType.Auto);
            g.ColumnDefinitions.Add(gc);

            // 1+x button
            for (int i = 0; i < 1 /* numButton*/ ; i++)
            {
                gc = new ColumnDefinition();
                gc.Width = new GridLength(1.0, GridUnitType.Star);
                g.ColumnDefinitions.Add(gc);
            }

            // 0 row
            var gr = new RowDefinition();
            gr.Height = new GridLength(1.0, GridUnitType.Star);
            g.RowDefinitions.Add(gr);

            // key label
            var x = AddSmallLabelTo(g, 0, 0, margin: new Thickness(5, 0, 0, 0), content: "" + key);
            x.VerticalContentAlignment = VerticalAlignment.Center;

            // 1 + action button
#if never
            for (int i = 0; i < numButton; i++)
            {
                int currentI = i;
                repo.RegisterControl(ElemViewAddSmallButtonTo(g, 0, 1+i, margin: new Thickness(0, 0, 5, 0), padding: new Thickness(5,0,5,0), content: "" + actionStr[i]),
                    (o) => {
                        return action(currentI); // button # as argument!
                    });
            }
#else
            var wp = AddSmallWrapPanelTo(g, 0, 1, margin: new Thickness(5, 0, 5, 0));
            for (int i = 0; i < numButton; i++)
            {
                int currentI = i;
                var b = new Button();
                b.Content = "" + actionStr[i];
                b.Margin = new Thickness(2, 2, 2, 2);
                b.Padding = new Thickness(5, 0, 5, 0);
                wp.Children.Add(b);
                repo.RegisterControl(b,
                    (o) => {
                        return action(currentI); // button # as argument!
                    });
            }
#endif
            // in total
            view.Children.Add(g);
        }

        public void AddAction(StackPanel view, string key, string actionStr, ModifyRepo repo = null, Func<object, ModifyRepo.LambdaAction> action = null)
        {
            AddAction(view, key, new string[] { actionStr }, repo, action);
        }

        public void AddKeyListLangStr(StackPanel view, string key, List<AdminShell.LangStr> langStr, ModifyRepo repo = null)
        {
            // sometimes needless to show
            if (repo == null && (langStr == null || langStr.Count < 1))
                return;
            int rows = 1; // default!
            if (langStr != null && langStr.Count > 1)
                rows = langStr.Count;
            int rowOfs = 0;
            if (repo != null)
                rowOfs = 1;

            // Grid
            var g = new Grid();
            g.Margin = new Thickness(0, 0, 0, 0);

            // 0 key
            var gc = new ColumnDefinition();
            gc.Width = new GridLength(100);
            g.ColumnDefinitions.Add(gc);

            // 1 langs
            gc = new ColumnDefinition();
            gc.Width = new GridLength(1.0, GridUnitType.Auto);
            g.ColumnDefinitions.Add(gc);

            // 2 values
            gc = new ColumnDefinition();
            gc.Width = new GridLength(1.0, GridUnitType.Star);
            g.ColumnDefinitions.Add(gc);

            // 3 buttons behind it
            gc = new ColumnDefinition();
            gc.Width = new GridLength(1.0, GridUnitType.Auto);
            g.ColumnDefinitions.Add(gc);

            // rows
            for (int r = 0; r < rows + rowOfs; r++)
            {
                var gr = new RowDefinition();
                gr.Height = new GridLength(1.0, GridUnitType.Auto);
                g.RowDefinitions.Add(gr);
            }

            // populate key
            AddSmallLabelTo(g, 0, 0, margin: new Thickness(5, 0, 0, 0), content: "" + key + ":");

            // populate [+]
            if (repo != null)
            {
                repo.RegisterControl(AddSmallButtonTo(g, 0, 3, margin: new Thickness(2, 2, 2, 2), padding: new Thickness(5, 0, 5, 0), content: "Add blank"),
                    (o) =>
                    {
                        var ls = new AdminShell.LangStr();
                        langStr.Add(ls);
                        return new ModifyRepo.LambdaActionRedrawEntity();
                    });
            }

            // contents?
            if (langStr != null)
                for (int i = 0; i < langStr.Count; i++)
                    if (repo == null)
                    {
                        // lang
                        AddSmallLabelTo(g, 0 + i + rowOfs, 1, padding: new Thickness(2, 0, 0, 0), content: "[" + langStr[i].lang + "]");

                        // str
                        AddSmallLabelTo(g, 0 + i + rowOfs, 2, padding: new Thickness(2, 0, 0, 0), content: "" + langStr[i].str);
                    }

                    else
                    {
                        // save in current context
                        var currentI = 0 + i;
                        // lang       
                        repo.RegisterControl(
                            AddSmallComboBoxTo(g, 0 + i + rowOfs, 1, margin: new Thickness(0, 2, 2, 2), text: "" + langStr[currentI].lang,
                                minWidth: 50, items: defaultLanguages, isEditable: true),
                            (o) =>
                            {
                                langStr[currentI].lang = o as string;
                                return new ModifyRepo.LambdaActionNone();
                            });

                        // str
                        repo.RegisterControl(
                            AddSmallTextBoxTo(g, 0 + i + rowOfs, 2, margin: new Thickness(2, 2, 2, 2), text: "" + langStr[currentI].str),
                            (o) =>
                            {
                                langStr[currentI].str = o as string;
                                return new ModifyRepo.LambdaActionNone();
                            });

                        // button [-]
                        repo.RegisterControl(AddSmallButtonTo(g, 0 + i + rowOfs, 3, margin: new Thickness(2, 2, 2, 2), padding: new Thickness(5, 0, 5, 0), content: "-"),
                            (o) => {
                                langStr.RemoveAt(currentI);
                                return new ModifyRepo.LambdaActionRedrawEntity();
                            });
                    }

            // in total
            view.Children.Add(g);
        }

        public List<AdminShell.Key> SmartSelectAasEntityKeys(AdminShell.AdministrationShellEnv env, string filter = null, AdminShell.PackageEnv package = null, AdminShell.PackageEnv[] auxPackages = null)
        {
            if (this.flyoutProvider == null)
            {
                var dlg = new SelectAasEntityDialogueByTree(env, filter, package, auxPackages);
                if (dlg.ShowDialog() == true)
                    return dlg.ResultKeys;
            }
            else
            {
                var uc = new SelectAasEntityFlyout(env, filter, package, auxPackages);
                this.flyoutProvider.StartFlyoverModal(uc);
                if (uc.ResultKeys != null)
                    return uc.ResultKeys;
            }
            return null;
        }

        public VisualElementGeneric SmartSelectAasEntityVisualElement(AdminShell.AdministrationShellEnv env, string filter = null, AdminShell.PackageEnv package = null, AdminShell.PackageEnv[] auxPackages = null)
        {
            if (this.flyoutProvider == null)
            {
                var dlg = new SelectAasEntityDialogueByTree(env, filter, package, auxPackages);
                if (dlg.ShowDialog() == true)
                    return dlg.ResultVisualElement;
            }
            else
            {
                var uc = new SelectAasEntityFlyout(env, filter, package, auxPackages);
                this.flyoutProvider.StartFlyoverModal(uc);
                if (uc.ResultVisualElement != null)
                    return uc.ResultVisualElement;
            }
            return null;
        }

        public void AddKeyListKeys(
            StackPanel view, string key, 
            List<AdminShell.Key> keys, 
            ModifyRepo repo = null, 
            AdminShell.PackageEnv package = null,              
            string addExistingEntities = null)
        {
            // sometimes needless to show
            if (repo == null && (keys == null || keys.Count < 1))
                return;
            int rows = 1; // default!
            if (keys != null && keys.Count > 1)
                rows = keys.Count;
            int rowOfs = 0;
            if (repo != null)
                rowOfs = 1;

            // Grid
            var g = new Grid();
            g.Margin = new Thickness(0, 0, 0, 0);

            // 0 key
            var gc = new ColumnDefinition();
            gc.Width = new GridLength(100);
            g.ColumnDefinitions.Add(gc);

            // 1 type
            gc = new ColumnDefinition();
            gc.Width = new GridLength(1.0, GridUnitType.Auto);
            g.ColumnDefinitions.Add(gc);

            // 2 local
            gc = new ColumnDefinition();
            gc.Width = new GridLength(1.0, GridUnitType.Auto);
            g.ColumnDefinitions.Add(gc);

            // 3 id type
            gc = new ColumnDefinition();
            gc.Width = new GridLength(1.0, GridUnitType.Auto);
            g.ColumnDefinitions.Add(gc);

            // 4 value
            gc = new ColumnDefinition();
            gc.Width = new GridLength(1.0, GridUnitType.Star);
            g.ColumnDefinitions.Add(gc);

            // 5 buttons behind it
            gc = new ColumnDefinition();
            gc.Width = new GridLength(1.0, GridUnitType.Auto);
            g.ColumnDefinitions.Add(gc);

            // rows
            for (int r = 0; r < rows + rowOfs; r++)
            {
                var gr = new RowDefinition();
                gr.Height = new GridLength(1.0, GridUnitType.Auto);
                g.RowDefinitions.Add(gr);
            }

            // populate key
            AddSmallLabelTo(g, 0, 0, margin: new Thickness(5, 0, 0, 0), content: "" + key + ":");

            // populate [+] and [Select] buttons
            if (repo != null)
            {
                var g2 = AddSmallGrid(1, 3, new string[] { "*", "#", "#" });
                Grid.SetRow(g2, 0);
                Grid.SetColumn(g2, 1);
                Grid.SetColumnSpan(g2, 5);
                g.Children.Add(g2);

                if (addExistingEntities != null && package != null)
                    repo.RegisterControl(AddSmallButtonTo(g2, 0, 1, margin: new Thickness(2, 2, 2, 2), padding: new Thickness(5, 0, 5, 0), content: "Add existing"),
                        (o) => {
                            var k2 = SmartSelectAasEntityKeys(package.AasEnv, addExistingEntities);
                            if (k2 != null)
                            {
                                keys.Clear();
                                keys.AddRange(k2);
                            }
                            return new ModifyRepo.LambdaActionRedrawEntity();
                        });

                repo.RegisterControl(AddSmallButtonTo(g2, 0, 2, margin: new Thickness(2, 2, 2, 2), padding: new Thickness(5, 0, 5, 0), content: "Add blank"),
                    (o) => {
                        var k = new AdminShell.Key();
                        keys.Add(k);
                        return new ModifyRepo.LambdaActionRedrawEntity();
                    });
            }

            // contents?
            if (keys != null)
                for (int i = 0; i < keys.Count; i++)
                    if (repo == null)
                    {
                        // lang
                        AddSmallLabelTo(g, 0 + i + rowOfs, 1, padding: new Thickness(2, 0, 0, 0), content: "(" + keys[i].type + ")");

                        // local
                        AddSmallLabelTo(g, 0 + i + rowOfs, 2, padding: new Thickness(2, 0, 0, 0), content: "" + ((keys[i].local) ? "(Local)" : "(no-Local)"));

                        // id type
                        AddSmallLabelTo(g, 0 + i + rowOfs, 3, padding: new Thickness(2, 0, 0, 0), content: "[" + keys[i].idType + "]");

                        // value
                        AddSmallLabelTo(g, 0 + i + rowOfs, 4, padding: new Thickness(2, 0, 0, 0), content: "" + keys[i].value);
                    }

                    else
                    {
                        // save in current context
                        var currentI = 0 + i;

                        // type
                        var cbType = repo.RegisterControl(
                            AddSmallComboBoxTo(g, 0 + i + rowOfs, 1, margin: new Thickness(2, 2, 2, 2), text: "" + keys[currentI].type,
                                minWidth: 100, items: AdminShell.Key.KeyElements, isEditable: false),
                            (o) =>
                            {
                                keys[currentI].type = o as string;
                                return new ModifyRepo.LambdaActionNone();
                            }) as ComboBox;
                        SmallComboBoxSelectNearestItem(cbType, cbType.Text);

                        // local
                        repo.RegisterControl(
                            AddSmallCheckBoxTo(g, 0 + i + rowOfs, 2, margin: new Thickness(2, 2, 2, 2), content: "local", isChecked: keys[currentI].local),
                            (o) =>
                            {
                                keys[currentI].local = (bool)o;
                                return new ModifyRepo.LambdaActionNone();
                            });

                        // id type
                        repo.RegisterControl(
                            AddSmallComboBoxTo(g, 0 + i + rowOfs, 3, margin: new Thickness(2, 2, 2, 2), text: "" + keys[currentI].idType,
                                minWidth: 100, items: AdminShell.Key.IdentifierTypeNames, isEditable: false),
                            (o) =>
                            {
                                keys[currentI].idType = o as string;
                                return new ModifyRepo.LambdaActionNone();
                            });

                        // value
                        repo.RegisterControl(
                            AddSmallTextBoxTo(g, 0 + i + rowOfs, 4, margin: new Thickness(2, 2, 2, 2), text: "" + keys[currentI].value),
                            (o) =>
                            {
                                keys[currentI].value = o as string;
                                return new ModifyRepo.LambdaActionNone();
                            });

                        // button [-]
                        repo.RegisterControl(AddSmallButtonTo(g, 0 + i + rowOfs, 5, margin: new Thickness(2, 2, 2, 2), padding: new Thickness(5, 0, 5, 0), content: "-"),
                            (o) => {
                                keys.RemoveAt(currentI);
                                return new ModifyRepo.LambdaActionRedrawEntity();
                            });
                    }

            // in total
            view.Children.Add(g);
        }

        //
        // Safeguarding functions (checking if somethingis null and doing ..)
        //

        public bool SafeguardAccess(StackPanel view, ModifyRepo repo, object data, string key, string actionStr, Func<object, ModifyRepo.LambdaAction> action)
        {
            if (repo != null && data == null)
                AddAction(view, key, actionStr, repo, action);
            return (data != null);
        }

        //
        // List manipulations
        //

        public void MoveElementInListUpwards<T>(List<T> list, T entity)
        {
            if (list == null || list.Count < 2 || entity == null)
                return;
            int ndx = list.IndexOf(entity);
            if (ndx < 1)
                return;
            list.RemoveAt(ndx);
            list.Insert(Math.Max(ndx - 1, 0), entity);
        }

        public void MoveElementInListDownwards<T>(List<T> list, T entity)
        {
            if (list == null || list.Count < 2 || entity == null)
                return;
            int ndx = list.IndexOf(entity);
            if (ndx < 0 || ndx >= list.Count - 1)
                return;
            list.RemoveAt(ndx);
            list.Insert(Math.Min(ndx + 1, list.Count), entity);
        }

        public object DeleteElementInListDownwards<T>(List<T> list, T entity, object alternativeReturn)
        {
            if (list == null || entity == null)
                return alternativeReturn;
            int ndx = list.IndexOf(entity);
            if (ndx < 0)
                return alternativeReturn;
            list.RemoveAt(ndx);
            if (ndx > 0)
                return list.ElementAt(ndx - 1);
            return alternativeReturn;
        }

        public void EntityListUpDownDeleteHelper<T>(StackPanel stack, ModifyRepo repo, List<T> list, T entity, object alternativeReturn, string label = "Entities:")
        {
            AddAction(stack, label, new string[] { "Move up", "Move down", "Delete" }, repo, (buttonNdx) =>
            {
                if (buttonNdx is int)
                {
                    if ((int)buttonNdx == 0)
                    {
                        MoveElementInListUpwards<T>(list, entity);
                        return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: entity, isExpanded: null);
                    }

                    if ((int)buttonNdx == 1)
                    {
                        MoveElementInListDownwards<T>(list, entity);
                        return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: entity, isExpanded: null);
                    }

                    if ((int)buttonNdx == 2)
                        if (MessageBoxResult.Yes == MessageBox.Show("Delete selected entity? This operation can not be reverted!", "AASX", MessageBoxButton.YesNo, MessageBoxImage.Warning))
                        {
                            var ret = DeleteElementInListDownwards<T>(list, entity, alternativeReturn);
                            return new ModifyRepo.LambdaActionRedrawAllElements(nextFocus: ret, isExpanded: null);
                        }
                }
                return new ModifyRepo.LambdaActionNone();
            });
        }

        //
        // Hinting
        //

        public void AddHintBubble(StackPanel view, bool hintMode, HintCheck[] hints)
        {
            // access
            if (!hintMode || view == null || hints == null)
                return;

            // check, if something to do. Execute all predicates
            List<string> textsToShow = new List<string>();
            HintCheck.Severity highestSev = HintCheck.Severity.Notice;
            foreach (var hc in hints)
                if (hc.CheckPred != null && hc.TextToShow != null)
                {
                    try
                    {
                        if (hc.CheckPred())
                        {
                            textsToShow.Add(hc.TextToShow);
                            if (hc.SeverityLevel == HintCheck.Severity.High)
                                highestSev = HintCheck.Severity.High;
                            if (hc.BreakIfTrue)
                                break;
                        }
                    }
                    catch (Exception ex)
                    {
                        textsToShow.Add($"Error while checking hints: {ex.Message} at {AdminShellUtil.ShortLocation(ex)}");
                        highestSev = HintCheck.Severity.High;
                    }
                }

            // some?
            if (textsToShow.Count < 1)
                return;

            // show!
            var bubble = new HintBubble();
            bubble.Margin = new Thickness(2, 4, 2, 0);
            bubble.Text = string.Join("\r\n", textsToShow);
            if (highestSev == HintCheck.Severity.High)
            {
                bubble.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#D42044"));
                bubble.Foreground = (SolidColorBrush)(new BrushConverter().ConvertFrom("#ffffff"));
            }
            if (highestSev == HintCheck.Severity.Notice)
            {
                bubble.Background = (SolidColorBrush)(new BrushConverter().ConvertFrom("#cce0ff"));
                bubble.Foreground = (SolidColorBrush)(new BrushConverter().ConvertFrom("#003380"));
            }
            view.Children.Add(bubble);
        }

        public void AddHintBubble(StackPanel view, bool hintMode, HintCheck hint)
        {
            AddHintBubble(view, hintMode, new HintCheck[] { hint });
        }

    }
}
