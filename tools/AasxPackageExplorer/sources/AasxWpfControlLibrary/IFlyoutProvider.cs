using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;

namespace AasxPackageExplorer
{
    public interface IFlyoutProvider
    {
        /// <summary>
        /// Returns true, if already an flyout is shown
        /// </summary>
        /// <returns></returns>
        bool IsInFlyout();

        /// <summary>
        /// Starts an Flyout based on an instantiated UserControl. The UserControl has to implement
        /// the interface IFlyoutControl
        /// </summary>
        /// <param name="uc"></param>
        void StartFlyover(UserControl uc);

        /// <summary>
        /// Initiate closing an existing flyout
        /// </summary>
        void CloseFlyover();

        /// <summary>
        /// Start UserControl as modal flyout. The UserControl has to implement
        /// the interface IFlyoutControl
        /// </summary>
        /// <param name="uc"></param>
        void StartFlyoverModal(UserControl uc);

        /// <summary>
        /// Show MessageBoxFlyout with contents
        /// </summary>
        /// <param name="message">Message on the main screen</param>
        /// <param name="caption">Caption string (title)</param>
        /// <param name="buttons">Buttons according to WPF standard messagebox</param>
        /// <param name="image">Image according to WPF standard messagebox</param>
        /// <returns></returns>
        MessageBoxResult MessageBoxFlyoutShow(string message, string caption, MessageBoxButton buttons, MessageBoxImage image);
    }
}
