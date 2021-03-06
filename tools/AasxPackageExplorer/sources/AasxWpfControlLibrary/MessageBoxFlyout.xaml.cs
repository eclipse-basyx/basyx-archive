using Newtonsoft.Json;
using System;
using System.Collections.Generic;
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
using System.Windows.Navigation;
using System.Windows.Shapes;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AasxPackageExplorer
{
    /// <summary>
    /// Interaktionslogik für SelectFromRepository.xaml
    /// </summary>
    public partial class MessageBoxFlyout : UserControl, IFlyoutControl
    {
        public event IFlyoutControlClosed ControlClosed;

        public MessageBoxResult Result = MessageBoxResult.None;

        private Dictionary<Button, MessageBoxResult> buttonToResult = new Dictionary<Button, MessageBoxResult>();
        
        public MessageBoxFlyout(string message, string caption, MessageBoxButton buttons, MessageBoxImage image)
        {
            InitializeComponent();

            // texts
            this.TextBlockTitle.Text = caption;
            this.TextBlockMessage.Text = message;

            // image
            this.ImageIcon.Source = null;
            if (image == MessageBoxImage.Error)
                this.ImageIcon.Source = new BitmapImage(new Uri("/AasxWpfControlLibrary;component/Resources/msg_error.png", UriKind.RelativeOrAbsolute));
            if (image == MessageBoxImage.Hand)
                this.ImageIcon.Source = new BitmapImage(new Uri("/AasxWpfControlLibrary;component/Resources/msg_hand.png", UriKind.RelativeOrAbsolute));
            if (image == MessageBoxImage.Information)
                this.ImageIcon.Source = new BitmapImage(new Uri("/AasxWpfControlLibrary;component/Resources/msg_info.png", UriKind.RelativeOrAbsolute));
            if (image == MessageBoxImage.Question)
                this.ImageIcon.Source = new BitmapImage(new Uri("/AasxWpfControlLibrary;component/Resources/msg_question.png", UriKind.RelativeOrAbsolute));
            if (image == MessageBoxImage.Warning)
                this.ImageIcon.Source = new BitmapImage(new Uri("/AasxWpfControlLibrary;component/Resources/msg_warning.png", UriKind.RelativeOrAbsolute));

            // buttons
            List<string> buttonDefs = new List<string>();
            List<MessageBoxResult> buttonResults = new List<MessageBoxResult>();
            if (buttons == MessageBoxButton.OK)
            {
                buttonDefs.Add("OK");
                buttonResults.Add(MessageBoxResult.OK);
            }
            if (buttons == MessageBoxButton.OKCancel)
            {
                buttonDefs.Add("OK");
                buttonResults.Add(MessageBoxResult.OK);
                buttonDefs.Add("Cancel");
                buttonResults.Add(MessageBoxResult.Cancel);
            }
            if (buttons == MessageBoxButton.YesNo)
            {
                buttonDefs.Add("Yes");
                buttonResults.Add(MessageBoxResult.Yes);
                buttonDefs.Add("No");
                buttonResults.Add(MessageBoxResult.No);
            }
            if (buttons == MessageBoxButton.YesNoCancel)
            {
                buttonDefs.Add("Yes");
                buttonResults.Add(MessageBoxResult.Yes);
                buttonDefs.Add("No");
                buttonResults.Add(MessageBoxResult.No);
                buttonDefs.Add("Cancel");
                buttonResults.Add(MessageBoxResult.Cancel);
            }
            this.StackPanelButtons.Children.Clear();
            buttonToResult = new Dictionary<Button, MessageBoxResult>();
            foreach (var bd in buttonDefs)
            {
                var b = new Button();
                b.Style = (Style)FindResource("TranspRoundCorner");
                b.Content = "" + bd;
                b.Height = 40;
                b.Width = 40;
                b.Margin = new Thickness(5, 0, 5, 0);
                b.Foreground = Brushes.White;
                b.Click += StackPanelButton_Click;
                this.StackPanelButtons.Children.Add(b);
                if (buttonResults.Count > 0)
                {
                    buttonToResult[b] = buttonResults[0];
                    buttonResults.RemoveAt(0);
                }
            }
        }

        //
        // Outer
        //

        public void ControlStart()
        {
        }

        private void StackPanelButton_Click(object sender, RoutedEventArgs e)
        {
            if (buttonToResult.ContainsKey(sender as Button))
            {
                this.Result = buttonToResult[sender as Button];
                ControlClosed();
            }
        }

        //
        // Mechanics
        //

        private void ButtonClose_Click(object sender, RoutedEventArgs e)
        {
            this.Result = MessageBoxResult.None;
            ControlClosed();
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
        }

        public void ControlPreviewKeyDown(KeyEventArgs e)
        {
            if (Keyboard.Modifiers != ModifierKeys.None && Keyboard.Modifiers != ModifierKeys.Shift)
                return;

            if (e.Key == Key.Y && buttonToResult.ContainsValue(MessageBoxResult.Yes))
            {
                this.Result = MessageBoxResult.Yes;
                ControlClosed();
            }
            if ((e.Key == Key.N || e.Key == Key.Escape) && buttonToResult.ContainsValue(MessageBoxResult.No))
            {
                this.Result = MessageBoxResult.No;
                ControlClosed();
            }
            if ((e.Key == Key.O || e.Key == Key.Return) && buttonToResult.ContainsValue(MessageBoxResult.OK))
            {
                this.Result = MessageBoxResult.OK;
                ControlClosed();
            }
            if ((e.Key == Key.C || e.Key == Key.Escape) && buttonToResult.ContainsValue(MessageBoxResult.Cancel))
            {
                this.Result = MessageBoxResult.Cancel;
                ControlClosed();
            }
        }
    }
}
