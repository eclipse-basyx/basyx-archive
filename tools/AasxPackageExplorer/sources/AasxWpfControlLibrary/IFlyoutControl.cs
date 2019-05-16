using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace AasxPackageExplorer
{
    public delegate void IFlyoutControlClosed();

    public interface IFlyoutControl
    {
        /// <summary>
        /// Event emitted by the Flyout in order to end the dialogue.
        /// </summary>
        event IFlyoutControlClosed ControlClosed;

        /// <summary>
        /// ´Called by the main window immediately after start
        /// </summary>
        void ControlStart();

        /// <summary>
        /// Called by main window, as soon as a keyboard input is avilable
        /// </summary>
        /// <param name="e"></param>
        void ControlPreviewKeyDown(KeyEventArgs e);
    }
}
