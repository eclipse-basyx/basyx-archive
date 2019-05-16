
using System;
using System.ComponentModel;
using System.Linq;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using Logging;
using System.IO;
using System.Collections.Generic;
using System.Text.RegularExpressions;

using AdminShellNS;
using System.Windows.Media.Effects;
using System.Windows.Threading;
using System.Threading;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AasxPackageExplorer
{
    /// <summary>
    /// Interaktionslogik für MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window, IFlyoutProvider
    {
#region Members

        //
        // Members
        //

#if WITHOUTCEF
        private FakeBrowser theBrowser = null;
        private string browserHandlesFiles = ".jpeg .jpg .png .bmp";
#else
        private CefSharp.Wpf.ChromiumWebBrowser theBrowser = null;
        private string browserHandlesFiles = ".jpeg .jpg .png .bmp .pdf .xml .txt *";
#endif

        public AdminShell.PackageEnv thePackageEnv = new AdminShell.PackageEnv();
        public AdminShell.PackageEnv thePackageAux = null;
        private string showContentPackageUri = null;
        private IFlyoutControl currentFlyoutControl = null;

        public MainWindow()
        {
            InitializeComponent();
        }

#endregion

#region Callbacks

        //
        // Callbacks
        //

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            // making up "empty" picture
            this.AasId.Text = "<id unknown!>";
            this.AssetId.Text = "<id unknown!>";

            /*
            Image image = new Image();
            image.Source = new BitmapImage(new Uri("Resources/USB_Hexagon_offen.jpeg", UriKind.RelativeOrAbsolute));
            this.AssetPic.Source = new BitmapImage(new Uri("Resources/USB_Hexagon_offen.jpeg", UriKind.RelativeOrAbsolute));
            */

            // display elements has a cache
            DisplayElements.ActivateElementStateCache();

            // show Logo?
            if (Options.LogoFile != null)
                try
                {
                    var bi = new BitmapImage(new Uri(Options.LogoFile, UriKind.RelativeOrAbsolute));
                    this.LogoImage.Source = bi;
                    this.LogoImage.UpdateLayout();
                } catch { }

            // adding the CEF Browser conditionally
#if WITHOUTCEF
            theBrowser = new FakeBrowser();
            CefContainer.Child = theBrowser;
            theBrowser.Address = "https://github.com/admin-shell/io/blob/master/README.md";
#else
            theBrowser = new CefSharp.Wpf.ChromiumWebBrowser();
            CefContainer.Child = theBrowser;
            theBrowser.Address = "https://github.com/admin-shell/io/blob/master/README.md";
#endif

            // window size?
            if (Options.WindowLeft > 0) this.Left = Options.WindowLeft;
            if (Options.WindowTop > 0) this.Top = Options.WindowTop;
            if (Options.WindowWidth > 0) this.Width = Options.WindowWidth;
            if (Options.WindowHeight > 0) this.Height = Options.WindowHeight;
            if (Options.WindowMaximized)
                this.WindowState = WindowState.Maximized;

            // Timer for below
            System.Windows.Threading.DispatcherTimer dispatcherTimer = new System.Windows.Threading.DispatcherTimer();
            dispatcherTimer.Tick += new EventHandler(dispatcherTimer_Tick);
            dispatcherTimer.Interval = new TimeSpan(0, 0, 0, 0, 100);
            dispatcherTimer.Start();

            // Last task here ..
            Log.Info("Application started ..");

            // Try to load?
            if (Options.AasxToLoad != null)
            {
                // make a fully qualified filename from that one provided as input argument
                var fn = System.IO.Path.GetFullPath(Options.AasxToLoad);
                UiLoadPackageEnv(fn);
            }

            // test sequence
            /*
            var asset1 = new AdminShell.Asset();
            Log.Info("asset1" + asset1.GetElementName());
            AdminShell.Referable ref1 = asset1;
            Log.Info("ref1" + ref1.GetElementName());
            var s = Options.GenerateIdAccordingTemplate(Options.TemplateIdSubmodelType);
            */

            /*
            var blur = new BlurEffect();
            blur.Radius = 5;
            
            var current = this.Background;
            this.Background = new SolidColorBrush(Colors.DarkGray);
            this.Effect = blur;
            MessageBox.Show("hello");
            this.Effect = null;
            this.Background = current;
            this.InnerGrid.Opacity = 0.5;
            this.InnerGrid.Effect = blur;
            */
        }

        private void dispatcherTimer_Tick(object sender, EventArgs e)
        {
            // check for Stored Prints in Log
            if (Log.StoredPrints.Count > 0)
            {
                var sp = Log.StoredPrints.Last();
                Message.Content = "" + sp.msg;
                if (sp.color == Log.ColorBlack)
                    Message.Background = Brushes.White;
                if (sp.color == Log.ColorBlue)
                    Message.Background = Brushes.LightBlue;
                if (sp.color == Log.ColorRed)
                    Message.Background = Brushes.Red;
            }

            // check if Display/ Edit Control has some work to do ..
            try
            {
                if (DispEditEntityPanel != null && DispEditEntityPanel.WishForOutsideAction != null)
                {
                    while (DispEditEntityPanel.WishForOutsideAction.Count > 0)
                    {
                        var temp = DispEditEntityPanel.WishForOutsideAction[0];
                        DispEditEntityPanel.WishForOutsideAction.RemoveAt(0);

                        // what to do?
                        if (temp is ModifyRepo.LambdaActionRedrawAllElements)
                        {
                            var wish = temp as ModifyRepo.LambdaActionRedrawAllElements;
                            // edit mode affects the total element view
                            RedrawAllAasxElements();
                            // the selection will be shifted ..
                            if (wish.NextFocus != null)
                            {
                                var si = DisplayElements.SearchVisualElementOnMainDataObject(wish.NextFocus);
                                if (si != null)
                                {
                                    si.IsSelected = true;
                                    if (wish.IsExpanded == true)
                                    {
                                        // go upward the tree in order to expand, as well
                                        var sii = si;
                                        while (sii != null)
                                        {
                                            sii.IsExpanded = wish.IsExpanded == true;
                                            sii = sii.Parent;
                                        }
                                    }
                                    if (wish.IsExpanded == false)
                                        si.IsExpanded = wish.IsExpanded == true;
                                    DisplayElements.Woodoo(si);
                                }
                            }
                            // fake selection
                            RedrawElementView();
                            DisplayElements.Refresh();
                            ContentTakeOver.IsEnabled = false;
                        }

                        if (temp is ModifyRepo.LambdaActionContentsChanged)
                        {
                            // enable button
                            ContentTakeOver.IsEnabled = true;
                        }

                        if (temp is ModifyRepo.LambdaActionContentsTakeOver)
                        {
                            // rework list
                            ContentTakeOver_Click(null, null);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.Error(ex, "While responding to a user interaction");
            }
        }

        private void ButtonReport_Click(object sender, RoutedEventArgs e)
        {
            // report on message / exception
            var report = @"
                |Dear user,
                |thank you for reporting an error / bug / unexpected behaviour back to the AASX package explorer team. 
                |Please provide the following details:
                |
                |  User: <who was working with the application>
                |
                |  Steps to reproduce: <what was the user doing, when the unexpected behaviour occurred>
                |
                |  Expected results: <what should happen>
                |
                |  Actual Results: <what was actually happening>
                |
                |  Message: {0}
                |
                |Please consider attaching the AASX package (you might rename this to .zip), you were working on, as well as an screen shot.
                |
                |Please mail your report to: michael.hoffmeister@festo.com
                |or you can directly add it at github: https://github.com/admin-shell/aasx-package-explorer/issues

            ";

            report = report.Replace("{0}", Message.Content.ToString());

            report = Regex.Replace(report, @"^(\s+)\|", "", RegexOptions.Multiline);

            var dlg = new MessageReportWindow();
            dlg.Text = report;
            dlg.ShowDialog();
        }

        private void CommandBinding_Executed(object sender, ExecutedRoutedEventArgs e)
        {
            // decode
            if (e == null || e.Command == null || !(e.Command is RoutedUICommand))
                return;
            var cmd = (e.Command as RoutedUICommand).Text.Trim().ToLower();

            // decide
            if (cmd == "new")
            {
                if ((!Options.UseFlyovers && MessageBoxResult.Yes == MessageBox.Show("Create new Adminshell environment? This operation can not be reverted!", "AASX", MessageBoxButton.YesNo, MessageBoxImage.Warning))
                    ||
                    (Options.UseFlyovers && MessageBoxResult.Yes == MessageBoxFlyoutShow("Create new Adminshell environment? This operation can not be reverted!", "AASX", MessageBoxButton.YesNo, MessageBoxImage.Warning))
                    )
                {
                    try
                    {
                        // create new AASX package
                        thePackageEnv = new AdminShell.PackageEnv();
                        // redraw everything
                        RedrawAllAasxElements();
                        RedrawElementView();
                    }
                    catch (Exception ex)
                    {
                        Log.Error(ex, "When creating new AASX, an error occurred");
                        return;
                    }
                }
            }
            if (cmd == "open" || cmd == "openaux")
            {
                var dlg = new Microsoft.Win32.OpenFileDialog();
                try
                {
                    dlg.InitialDirectory = System.IO.Path.GetDirectoryName(thePackageEnv.Filename);
                }
                catch { }
                dlg.Filter = "AASX package files (*.aasx)|*.aasx|AAS XML file (*.xml)|*.xml|All files (*.*)|*.*";
                if (Options.UseFlyovers) this.StartFlyover(new EmptyFlyout());
                var res = dlg.ShowDialog();
                if (Options.UseFlyovers) this.CloseFlyover();
                if (res == true)
                {
                    if (cmd == "open")
                        UiLoadPackageEnv(dlg.FileName);
                    if (cmd == "openaux")
                        UiLoadPackageAux(dlg.FileName);
                }
            }
            if (cmd == "save")
            {
                try
                {
                    // save
                    thePackageEnv.SaveAs(thePackageEnv.Filename);
                    // as saving changes the structure of pending supplementary files, re-display
                    RedrawAllAasxElements();
                }
                catch (Exception ex)
                {
                    Log.Error(ex, "When saving AASX, an error occurred");
                    return;
                }
                Log.Info("AASX saved successfully: {0}", thePackageEnv.Filename);
            }
            if (cmd == "saveas")
            {
                var dlg = new Microsoft.Win32.SaveFileDialog();
                try
                {
                    dlg.InitialDirectory = System.IO.Path.GetDirectoryName(thePackageEnv.Filename);
                }
                catch { }
                dlg.FileName = thePackageEnv.Filename;
                dlg.DefaultExt = "*.aasx";
                dlg.Filter = "AASX package files (*.aasx)|*.aasx|AAS XML file (*.xml)|*.xml|All files (*.*)|*.*";
                if (Options.UseFlyovers) this.StartFlyover(new EmptyFlyout());
                var res = dlg.ShowDialog();
                if (Options.UseFlyovers) this.CloseFlyover();
                if (res == true)
                {
                    try
                    {
                        // save
                        thePackageEnv.SaveAs(dlg.FileName);
                        // as saving changes the structure of pending supplementary files, re-display
                        RedrawAllAasxElements();
                    }
                    catch (Exception ex)
                    {
                        Log.Error(ex, "When saving AASX, an error occurred");
                        return;
                    }
                    Log.Info("AASX saved successfully as: {0}", dlg.FileName);
                }
            }
            if (cmd == "close" && thePackageEnv != null)
            {
                if ((!Options.UseFlyovers && MessageBoxResult.Yes == MessageBox.Show(this, "Do you want to close the open package? Please make sure that you have saved before.", "Close Package?", MessageBoxButton.YesNo, MessageBoxImage.Question))
                    ||
                    (Options.UseFlyovers && MessageBoxResult.Yes == MessageBoxFlyoutShow("Do you want to close the open package? Please make sure that you have saved before.", "Close Package?", MessageBoxButton.YesNo, MessageBoxImage.Question))
                    )
                    try
                    {
                        thePackageEnv.Close();
                        RedrawAllAasxElements();
                    }
                    catch (Exception ex)
                    {
                        Log.Error(ex, "When closing AASX, an error occurred");
                    }
            }

            if (cmd == "closeaux" && thePackageAux != null)
                try
                {
                    thePackageAux.Close();
                }
                catch (Exception ex)
                {
                    Log.Error(ex, "When closing auxiliary AASX, an error occurred");
                }
            if (cmd == "exit")
                /*
                if ((!Options.UseFlyovers && MessageBoxResult.Yes == MessageBox.Show(this, "Exit the application? Please make sure that you have saved before.", "Exit Application?", MessageBoxButton.YesNo, MessageBoxImage.Question))
                    ||
                    (Options.UseFlyovers && MessageBoxResult.Yes == MessageBoxFlyoutShow("Exit the application? Please make sure that you have saved before.", "Exit Application?", MessageBoxButton.YesNo, MessageBoxImage.Question))
                    )
                    */
                    System.Windows.Application.Current.Shutdown();
            if (cmd == "connect")
                if (!Options.UseFlyovers)
                    MessageBox.Show(this, "In future versions, this feature will allow connecting to an online Administration Shell via OPC UA or similar.\n" +
                        "This feature is scope of the specification 'Details of the Adminstration Shell Part 1 V1.1' and 'Part 2'.", "Connect", MessageBoxButton.OK);
                else
                    MessageBoxFlyoutShow("In future versions, this feature will allow connecting to an online Administration Shell via OPC UA or similar.", "Connect", MessageBoxButton.OK, MessageBoxImage.Hand);
            if (cmd == "about")
            {
                var ab = new AboutBox();
                ab.ShowDialog();
            }
            if (cmd == "helpgithub")
            {
                theBrowser.Address = @"https://github.com/admin-shell/aasx-package-explorer/blob/master/help/index.md";
                Dispatcher.BeginInvoke((Action)(() => ElementTabControl.SelectedIndex = 1));
            }
            if (cmd == "editkey")
                MenuItemWorkspaceEdit.IsChecked = !MenuItemWorkspaceEdit.IsChecked;
            if (cmd == "hintskey")
                MenuItemWorkspaceHints.IsChecked = !MenuItemWorkspaceHints.IsChecked;
            if (cmd == "editmenu" || cmd == "editkey" || cmd == "hintsmenu" || cmd == "hintskey" )
            {
                // edit mode affects the total element view
                RedrawAllAasxElements();
                // fake selection
                RedrawElementView();
            }
            if (cmd == "test")
            {
                /*
                string err = null;
                Log.Append(err + "");
                var p1 = new AdminShell.Property();
                p1.value = "42";
                */
                /*
                var w1 = new AdminShell.SubmodelElementWrapper();
                w1.submodelElement = p1;
                var w2 = new AdminShell.SubmodelElementWrapper(w1);
                */
                // var w2 = new AdminShell.SubmodelElementWrapper(p1);
                var uc = new MessageBoxFlyout("My very long message text!", "Caption", MessageBoxButton.OK, MessageBoxImage.Error);
                uc.ControlClosed += () =>
                {
                    Log.Info("Yes!");
                };
                this.StartFlyover(uc);
            }
            if (cmd == "queryrepo")
            { 
                var uc = new SelectFromRepositoryFlyout();
                uc.Margin = new Thickness(10);
                if (uc.LoadAasxRepoFile(Options.AasxRepositoryFn))
                {
                    uc.ControlClosed += () =>
                    {
                        var fn = uc.ResultFilename;
                        if (fn != null && fn != "")
                        {
                            Log.Info("Switching to {0} ..", fn);
                            UiLoadPackageEnv(fn);
                        }

                    };
                    this.StartFlyover(uc);
                }
            }

        }

        // private bool inSelectedItemChanged = false;

        private void DisplayElements_SelectedItemChanged(object sender, EventArgs e)
        {
            // access
            if (sender != DisplayElements)
                return;

            //if (inSelectedItemChanged)
            //    return;
            //inSelectedItemChanged = true;

            // expand some nodes?
            //if (1 == 1)
            //{
            //    DisplayElements.ExpandOnlyBranchWithin(DisplayElements.SelectedItem);
            //}

            // redraw view
            RedrawElementView();

            //// end
            //inSelectedItemChanged = false;
        }

        public void PrepareDispEditEntity(AdminShell.PackageEnv package, VisualElementGeneric entity, bool editMode, bool hintMode)
        {
            // make UI visible settings ..
            // panels
            if (!editMode)
            {
                ContentPanelNoEdit.Visibility = Visibility.Visible;
                ContentPanelEdit.Visibility = Visibility.Hidden;
            }
            else
            {
                ContentPanelNoEdit.Visibility = Visibility.Hidden;
                ContentPanelEdit.Visibility = Visibility.Visible;
            }
            // further
            ShowContent.IsEnabled = false;
            DragSource.Foreground = Brushes.DarkGray;
            DownloadContent.IsEnabled = false;
            this.showContentPackageUri = null;

            // update element view
            DispEditEntityPanel.DisplayOrEditVisualAasxElement(package, entity, editMode, hintMode, (thePackageAux == null) ? null : new AdminShell.PackageEnv[] { thePackageAux }, flyoutProvider: this);

            // show it
            Dispatcher.BeginInvoke((Action)(() => ElementTabControl.SelectedIndex = 0));

            // some entities require special handling
            if (entity is VisualElementSubmodelElement && (entity as VisualElementSubmodelElement).theWrapper.submodelElement is AdminShell.File)
            {
                var elem = (entity as VisualElementSubmodelElement).theWrapper.submodelElement;
                ShowContent.IsEnabled = true;
                this.showContentPackageUri = (elem as AdminShell.File).value;
                DragSource.Foreground = Brushes.Black;
            }
        }

        public void RedrawElementView() {
            if (DisplayElements == null)
                return;

            // the AAS will cause some more visual effects
            var tvlaas = DisplayElements.SelectedItem as VisualElementAdminShell;
            if (thePackageEnv != null && tvlaas != null && tvlaas.theAas != null && tvlaas.theEnv != null)
            {
                // AAS
                // update graphic left

                // what is AAS specific?
                this.AasId.Text = WrappingWpfString(AdminShellUtil.EvalToNonNullString("{0}", tvlaas.theAas.identification.id, "<id missing!>"));

                // what is asset specific?
                this.AssetPic.Source = null;
                this.AssetId.Text = "<id missing!>";
                var asset = tvlaas.theEnv.FindAsset(tvlaas.theAas.assetRef);
                if (asset != null)
                {

                    // text id
                    if (asset.identification != null)
                        this.AssetId.Text = WrappingWpfString(AdminShellUtil.EvalToNonNullString("{0}", asset.identification.id, ""));

                    // asset thumbail
                    try
                    {
                        var bi = new BitmapImage();
                        bi.BeginInit();
                        bi.CacheOption = BitmapCacheOption.OnLoad;
                        bi.StreamSource = thePackageEnv.GetLocalThumbnailStream();
                        bi.EndInit();
                        this.AssetPic.Source = bi;                    
                        // this.AssetPic.Source = new BitmapImage(new Uri("Resources/USB_Hexagon_offen.jpeg", UriKind.RelativeOrAbsolute));
                    }
                    catch
                    {
                        // no error, intended behaviour, as thumbnail might not exist / be faulty in some way (not violating the spec)
                        // Log.Error(ex, "Error loading package's thumbnail");
                    }
                }                
            }

            // for all, prepare the display
            PrepareDispEditEntity(thePackageEnv, DisplayElements.SelectedItem, MenuItemWorkspaceEdit.IsChecked, MenuItemWorkspaceHints.IsChecked);

        }

        private void Window_Closing(object sender, CancelEventArgs e)
        {
            if (this.IsInFlyout())
            {
                e.Cancel = true;
                return;
            }

            var positiveQuestion =
                (!Options.UseFlyovers && MessageBoxResult.Yes == MessageBox.Show(this, "Do you want to proceed closing the application? Make sure, that you have saved your data before.", "Exit application?", MessageBoxButton.YesNo, MessageBoxImage.Question))
                ||
                (Options.UseFlyovers && MessageBoxResult.Yes == MessageBoxFlyoutShow("Do you want to proceed closing the application? Make sure, that you have saved your data before.", "Exit application?", MessageBoxButton.YesNo, MessageBoxImage.Question));

            if (!positiveQuestion)
            {
                e.Cancel = true;
                return;
            }

            Log.Info("Closing ..");
            try
            {
                thePackageEnv.Close();
            }
            catch (Exception)
            { }

            e.Cancel = false;
        }

        private void Window_SizeChanged(object sender, SizeChangedEventArgs e)
        {
            if (this.ActualWidth > 800)
            {
                Log.Info($"Resizing window to {this.ActualWidth}x{this.ActualHeight} ..");
                if (MainSpaceGrid != null && MainSpaceGrid.ColumnDefinitions.Count >= 3)
                {
                    MainSpaceGrid.ColumnDefinitions[0].Width = new GridLength(this.ActualWidth / 5);
                    MainSpaceGrid.ColumnDefinitions[4].Width = new GridLength(this.ActualWidth / 3);
                }
            }
        }

        private void ShowContent_Click(object sender, RoutedEventArgs e)
        {
            if (sender == ShowContent && this.showContentPackageUri != null && this.thePackageEnv != null)
            {
                Log.Info("Trying display content {0} ..", this.showContentPackageUri);
                try
                {
                    var contentUri = this.showContentPackageUri;

                    // if local in the package, then make a tempfile
                    if (!this.showContentPackageUri.ToLower().Trim().StartsWith("http://")
                        && !this.showContentPackageUri.ToLower().Trim().StartsWith("https://"))
                    {
                        // make it as file
                        contentUri = MakeFileAvailableAsTempFile(this.showContentPackageUri);
                    }

                    // decide, how to handle the file ..
                    var ext = System.IO.Path.GetExtension(contentUri.ToLower());
                    if (ext == "")
                        ext = "*";
                    if (browserHandlesFiles.Contains(ext))
                    {
                        // try view in browser
                        Log.Info($"Displaying {this.showContentPackageUri} locally in embedded browser ..");
                        theBrowser.Address = contentUri;
                        Dispatcher.BeginInvoke((Action)(() => ElementTabControl.SelectedIndex = 1));
                    }
                    else
                    {
                        // open externally
                        Log.Info($"Displaying {this.showContentPackageUri} remotely in external viewer ..");
                        System.Diagnostics.Process.Start(contentUri);
                    }

                    /*

                    // check if remote ..
                    if (this.showContentPackageUri.ToLower().Trim().StartsWith("http://")
                        || this.showContentPackageUri.ToLower().Trim().StartsWith("https://"))
                    {
                        if (theBrowser != null)
                        {
                            // directly pass to browser
                            Log.Info($"Displaying {this.showContentPackageUri} remotely in embedded browser ..");
                            theBrowser.Address = this.showContentPackageUri;
                            Dispatcher.BeginInvoke((Action)(() => ElementTabControl.SelectedIndex = 1));
                        }
                    }
                    else
                    {
                        // make it as file
                        var tempfile = MakeFileAvailableAsTempFile(this.showContentPackageUri);
                        if (theBrowser != null)
                        {
                            if (browserHandlesFiles.Contains(System.IO.Path.GetExtension(tempfile.ToLower())))
                            {
                                // try view in browser
                                Log.Info($"Displaying {this.showContentPackageUri} locally in embedded browser ..");
                                theBrowser.Address = tempfile;
                                Dispatcher.BeginInvoke((Action)(() => ElementTabControl.SelectedIndex = 1));
                            }
                            else
                            {
                                // open externally
                                Log.Info($"Displaying {this.showContentPackageUri} remotely in external viewer ..");
                                System.Diagnostics.Process.Start(tempfile);
                            }
                        }
                    }

                    */
                }
                catch (Exception ex)
                {
                    Log.Error(ex, $"When displaying content {this.showContentPackageUri}, an error occurred");
                    return;
                }
                Log.Info("Content {0} displayed.", this.showContentPackageUri);
            }
        }

        private void ContentUndo_Click(object sender, RoutedEventArgs e)
        {
            DispEditEntityPanel.CallUndo();
        }

        private void ContentTakeOver_Click(object sender, RoutedEventArgs e)
        {
            var x = DisplayElements.SelectedItem;
            if (x != null && x is VisualElementGeneric)
                x.RefreshFromMainData();
            DisplayElements.Refresh();
            ContentTakeOver.IsEnabled = false;
        }

        private void DispEditEntityPanel_ContentsChanged(object sender, int kind)
        {

        }

        private void mainWindow_PreviewKeyDown(object sender, KeyEventArgs e)
        {
            if ( (e.Key == Key.OemPlus || e.Key == Key.Add) && Keyboard.Modifiers == ModifierKeys.Control)
            {
                if (theBrowser != null)
                {
                    theBrowser.ZoomLevel += 0.25;
                }
            }

            if ((e.Key == Key.OemMinus || e.Key == Key.Subtract) && Keyboard.Modifiers == ModifierKeys.Control)
            {
                if (theBrowser != null)
                {
                    theBrowser.ZoomLevel -= 0.25;
                }
            }

            if (this.IsInFlyout() && currentFlyoutControl != null)
            {
                currentFlyoutControl.ControlPreviewKeyDown(e);
            }
        }

        #endregion

        #region Flyover 

        //
        // Flyover user controls
        //

        public bool IsInFlyout()
        {
            if (this.GridFlyover.Children.Count > 0)
                return true;
            return false;
        }

        public void StartFlyover(UserControl uc)
        {
            // uc needs to implement IFlyoverControl
            var ucfoc = uc as IFlyoutControl;
            if (ucfoc == null)
                return;

            // blur the normal grid
            this.InnerGrid.IsEnabled = false;
            var blur = new BlurEffect();
            blur.Radius = 5;
            this.InnerGrid.Opacity = 0.5;
            this.InnerGrid.Effect = blur;

            // populate the flyover grid
            this.GridFlyover.Visibility = Visibility.Visible;
            this.GridFlyover.Children.Clear();
            this.GridFlyover.Children.Add(uc);

            // register the event
            ucfoc.ControlClosed += Ucfoc_ControlClosed;
            currentFlyoutControl = ucfoc;

            // start (focus)
            ucfoc.ControlStart();
        }

        private void Ucfoc_ControlClosed()
        {
            CloseFlyover();
        }

        public void CloseFlyover()
        {
            // blur the normal grid
            this.InnerGrid.Opacity = 1.0;
            this.InnerGrid.Effect = null;
            this.InnerGrid.IsEnabled = true;

            // un-populate the flyover grid
            this.GridFlyover.Children.Clear();
            this.GridFlyover.Visibility = Visibility.Hidden;

            // unregister
            currentFlyoutControl = null;
        }

        public void StartFlyoverModal(UserControl uc)
        {
            // uc needs to implement IFlyoverControl
            var ucfoc = uc as IFlyoutControl;
            if (ucfoc == null)
                return;

            // blur the normal grid
            this.InnerGrid.IsEnabled = false;
            var blur = new BlurEffect();
            blur.Radius = 5;
            this.InnerGrid.Opacity = 0.5;
            this.InnerGrid.Effect = blur;

            // populate the flyover grid
            this.GridFlyover.Visibility = Visibility.Visible;
            this.GridFlyover.Children.Clear();
            this.GridFlyover.Children.Add(uc);

            // register the event
            var frame = new DispatcherFrame();
            ucfoc.ControlClosed += () =>
            {
                Log.Info("Want to end!");
                frame.Continue = false; // stops the frame
            };

            currentFlyoutControl = ucfoc;

            // start (focus)
            ucfoc.ControlStart();

            // This will "block" execution of the current dispatcher frame
            // and run our frame until the dialog is closed.
            Dispatcher.PushFrame(frame);

            // blur the normal grid
            this.InnerGrid.Opacity = 1.0;
            this.InnerGrid.Effect = null;
            this.InnerGrid.IsEnabled = true;

            // un-populate the flyover grid
            this.GridFlyover.Children.Clear();
            this.GridFlyover.Visibility = Visibility.Hidden;

            // unregister
            currentFlyoutControl = null;
        }

        public MessageBoxResult MessageBoxFlyoutShow(string message, string caption, MessageBoxButton buttons, MessageBoxImage image)
        {
            var uc = new MessageBoxFlyout(message, caption, buttons, image);
            StartFlyoverModal(uc);
            return uc.Result;
        }

        #endregion


        #region Drag&Drop

        //
        // Drag & Drop
        //

        private void Window_DragEnter(object sender, DragEventArgs e)
        {
            if (!e.Data.GetDataPresent("myFormat") || sender == e.Source)
            {
                e.Effects = DragDropEffects.None;
            }
        }

        private void Window_Drop(object sender, DragEventArgs e)
        {
            if (e.Data.GetDataPresent(DataFormats.FileDrop, true))
            {
                // Note that you can have more than one file.
                string[] files = (string[])e.Data.GetData(DataFormats.FileDrop);

                // Assuming you have one file that you care about, pass it off to whatever
                // handling code you have defined.
                if (files.Length > 0)
                    UiLoadPackageEnv(files[0]);
            }
        }

        private bool isDragging = false;
        private Point dragStartPoint = new Point(0, 0);

        private void DragSource_PreviewMouseMove(object sender, MouseEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed && !isDragging && (dragStartPoint.X != 0 && dragStartPoint.Y != 0)
                && this.showContentPackageUri != null && this.thePackageEnv != null)
            {
                Point position = e.GetPosition(null);
                if (Math.Abs(position.X - dragStartPoint.X) > SystemParameters.MinimumHorizontalDragDistance ||
                    Math.Abs(position.Y - dragStartPoint.Y) > SystemParameters.MinimumVerticalDragDistance)
                {
                    // check if it an address in the package only
                    if (!this.showContentPackageUri.Trim().StartsWith("/"))
                        return;

                    // lock
                    isDragging = true;

                    // fail safe
                    try { 
                        // hastily prepare temp file ..
                        var tempfile = MakeFileAvailableAsTempFile(this.showContentPackageUri);

                        // Package the data.
                        DataObject data = new DataObject();
                        // data.SetData(DataFormats.FileDrop, tempfile);
                        data.SetFileDropList(new System.Collections.Specialized.StringCollection() { tempfile });

                        // Inititate the drag-and-drop operation.
                        DragDrop.DoDragDrop(this, data, DragDropEffects.Copy | DragDropEffects.Move);
                    }
                    catch (Exception ex)
                    {
                        Log.Error(ex, $"When dragging content {this.showContentPackageUri}, an error occurred");
                        return;
                    }

                    // unlock
                    isDragging = false;
                }
            }
        }

        private void DragSource_PreviewMouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            dragStartPoint = e.GetPosition(null);
        }

#endregion

#region General UI

        //
        // General UI 
        //

        public void UiLoadPackageEnv(string fn)
        {
            Log.Info("Loading AASX from: {0} ..", fn);
            // loading
            try
            {
                if (thePackageEnv != null)
                    thePackageEnv.Close();
                thePackageEnv = new AdminShell.PackageEnv(fn);
            } catch (Exception ex)
            {
                Log.Error(ex, $"When loading {fn}, an error occurred");
                return;
            }
            // displaying
            try
            {
                RedrawAllAasxElements();
            }
            catch (Exception ex)
            {
                Log.Error(ex, $"When displaying element tree of {fn}, an error occurred");
                return;
            }
            Log.Info("AASX {0} loaded.", fn);

        }

        public void UiLoadPackageAux(string fn)
        {
            Log.Info("Loading auxiliary AASX from: {0} ..", fn);
            // loading
            try
            {
                if (thePackageAux != null)
                    thePackageAux.Close();
                thePackageAux = new AdminShell.PackageEnv(fn);
            }
            catch (Exception ex)
            {
                Log.Error(ex, $"When loading auxiliary {fn}, an error occurred");
                return;
            }
            // displaying
            try
            {
                RedrawAllAasxElements();
            }
            catch (Exception ex)
            {
                Log.Error(ex, $"When displaying element tree of {fn}, an error occurred");
                return;
            }
            Log.Info("Auxiliary AASX {0} loaded.", fn);

        }

        #endregion

        #region Utils

        //
        // Utils
        //

        public static string WrappingWpfString(string str)
        {
            var res = "";
            foreach (var c in str)
                res += c + "\u200b";
            return res;
        }

        private string MakeFileAvailableAsTempFile(string packageUri)
        {
            // get input stream
            var input = this.thePackageEnv.GetLocalStreamFromPackage(this.showContentPackageUri);
            // copy to temp file
            string tempext = System.IO.Path.GetExtension(this.showContentPackageUri);
            string temppath = System.IO.Path.GetTempFileName().Replace(".tmp", tempext);
            Log.Info($"Writing to temporary file {temppath} ..");
            var temp = File.OpenWrite(temppath);
            input.CopyTo(temp);
            temp.Close();
            return temppath;
        }

#endregion

        public void RedrawAllAasxElements()
        {
            var t = "AASX Package Explorer";
            if (thePackageEnv != null)
                t += " - " + System.IO.Path.GetFileName(thePackageEnv.Filename);
            if (thePackageAux != null)
                t += " (auxiliary AASX: " + System.IO.Path.GetFileName(thePackageAux.Filename) + ")";
            this.Title = t;

            // clear the right section, first (might be rebuild by callback from below)
            DispEditEntityPanel.DisplayClear();
            ContentTakeOver.IsEnabled = false;

            // rebuild middle section
            DisplayElements.RebuildAasxElements(this.thePackageEnv.AasEnv, this.thePackageEnv, null, MenuItemWorkspaceEdit.IsChecked);
            DisplayElements.Refresh();

        }

    }
}
