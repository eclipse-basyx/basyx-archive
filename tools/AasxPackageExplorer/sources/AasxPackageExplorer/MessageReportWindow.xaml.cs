using System;
using System.Collections.Generic;
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

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AasxPackageExplorer
{
    /// <summary>
    /// Interaktionslogik für MessageReportWindow.xaml
    /// </summary>
    public partial class MessageReportWindow : Window
    {
        public MessageReportWindow()
        {
            InitializeComponent();
        }

        public string Text
        {
            get { return TextReport.Text; }
            set { TextReport.Text = value; }
        }

        private void ButtonEmailToClipboard_Click(object sender, RoutedEventArgs e)
        {
            Clipboard.SetText("michael.hoffmeister@festo.com");
        }

        private void ButtonCopyToClipboard_Click(object sender, RoutedEventArgs e)
        {
            Clipboard.SetText(TextReport.Text);
            this.DialogResult = true;
        }

    }
}
