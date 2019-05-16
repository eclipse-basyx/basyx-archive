using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
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

namespace AasxPackageExplorer
{
    /// <summary>
    /// Interaktionslogik für AboutBox.xaml
    /// </summary>
    public partial class AboutBox : Window
    {
        public AboutBox()
        {
            InitializeComponent();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            // HEADER
            this.HeaderText.Text = "AASX Package Explorer\n" +
                "Copyright (c) 2018-2019 Festo AG & Co. KG\n" +
                "Version: post build190301";
            // BODY: try to fill LICENSE.txt
            try
            {
                using (var stream = Assembly.GetExecutingAssembly().GetManifestResourceStream("AasxPackageExplorer.LICENSE.txt"))
                {
                    TextReader tr = new StreamReader(stream);
                    string fileContents = tr.ReadToEnd();
                    this.InfoBox.Text = fileContents;
                }
            }
            catch { }
        }
    }
}
