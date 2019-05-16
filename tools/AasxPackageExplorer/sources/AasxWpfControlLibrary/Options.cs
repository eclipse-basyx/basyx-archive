using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AasxPackageExplorer
{
    public static class Options
    {
        /// <summary>
        /// This file shall be loaded at start of application
        /// </summary>
        public static string AasxToLoad = null;

        /// <summary>
        /// if not -1, the left of window
        /// </summary>
        public static int WindowLeft = -1;

        /// <summary>
        /// if not -1, the top of window
        /// </summary>
        public static int WindowTop = -1;

        /// <summary>
        /// if not -1, the width of window
        /// </summary>
        public static int WindowWidth = -1;

        /// <summary>
        /// if not -1, the height of window
        /// </summary>
        public static int WindowHeight = -1;

        /// <summary>
        /// if True, then maximize window on application startup
        /// </summary>
        public static bool WindowMaximized = false;

        /// <summary>
        /// Template string for the id of an AAS. Could contain up to 16 placeholders of:
        /// D = decimal digit, X = hex digit, A = alphanumerical digit
        /// </summary>
        public static string TemplateIdAas = "www.company.com/ids/aas/DDDD_DDDD_DDDD_DDDD";

        /// <summary>
        /// Template string for the id of an aaset. Could contain up to 16 placeholders of:
        /// D = decimal digit, X = hex digit, A = alphanumerical digit
        /// </summary>
        public static string TemplateIdAsset = "www.company.com/ids/asset/DDDD_DDDD_DDDD_DDDD";

        /// <summary>
        /// Template string for the id of an submodel of kind instance. Could contain up to 16 placeholders of:
        /// D = decimal digit, X = hex digit, A = alphanumerical digit
        /// </summary>
        public static string TemplateIdSubmodelInstance = "www.company.com/ids/sm/DDDD_DDDD_DDDD_DDDD";

        /// <summary>
        /// Template string for the id of an submodel of kind type. Could contain up to 16 placeholders of:
        /// D = decimal digit, X = hex digit, A = alphanumerical digit
        /// </summary>
        public static string TemplateIdSubmodelType = "www.company.com/ids/sm/DDDD_DDDD_DDDD_DDDD";

        /// <summary>
        /// Template string for the id of a concept description. Could contain up to 16 placeholders of:
        /// D = decimal digit, X = hex digit, A = alphanumerical digit
        /// </summary>
        public static string TemplateIdConceptDescription = "www.company.com/ids/cd/DDDD_DDDD_DDDD_DDDD";

        /// <summary>
        /// Path to eCl@ss files
        /// </summary>
        public static string EclassDir = null;

        /// <summary>
        /// Path to an image to be displayed as logo
        /// </summary>
        public static string LogoFile = null;

        /// <summary>
        /// Path to a JSON, defining a set of AasxPackage-Files, which serve as repository
        /// </summary>
        public static string AasxRepositoryFn = null;

        /// <summary>
        /// If true, use transparent flyover dialogs, where possible
        /// </summary>
        public static bool UseFlyovers = true;

        /// <summary>
        /// Parse given commandline arguments.
        /// </summary>
        /// <param name="args"></param>
        public static void ParseArgs(string[] args)
        {
            for (int index=0; index<args.Length; index++)
            {
                var arg = args[index].Trim().ToLower();
                var morearg = (args.Length - 1) - index;

                // flags
                if (arg == "-maximized")
                {
                    WindowMaximized = true;
                    continue;
                }
                if (arg == "-noflyouts")
                {
                    UseFlyovers = false;
                    continue;
                }

                // options
                if (arg == "-left" && morearg>0)
                {
                    int i;
                    if (Int32.TryParse(args[index + 1], out i))
                        WindowLeft = i;
                    index++;
                    continue;
                }
                if (arg == "-top" && morearg > 0)
                {
                    int i;
                    if (Int32.TryParse(args[index + 1], out i))
                        WindowTop = i;
                    index++;
                    continue;
                }
                if (arg == "-width" && morearg > 0)
                {
                    int i;
                    if (Int32.TryParse(args[index + 1], out i))
                        WindowWidth = i;
                    index++;
                    continue;
                }
                if (arg == "-height" && morearg > 0)
                {
                    int i;
                    if (Int32.TryParse(args[index + 1], out i))
                        WindowHeight = i;
                    index++;
                    continue;
                }

                if (arg == "-id-aas" && morearg > 0)
                {
                    TemplateIdAas = args[index + 1];
                    index++;
                    continue;
                }
                if (arg == "-id-asset" && morearg > 0)
                {
                    TemplateIdAsset = args[index + 1];
                    index++;
                    continue;
                }
                if (arg == "-id-sm-type" && morearg > 0)
                {
                    TemplateIdSubmodelType = args[index + 1];
                    index++;
                    continue;
                }
                if (arg == "-id-sm-instance" && morearg > 0)
                {
                    TemplateIdSubmodelInstance = args[index + 1];
                    index++;
                    continue;
                }
                if (arg == "-id-cd" && morearg > 0)
                {
                    TemplateIdConceptDescription = args[index + 1];
                    index++;
                    continue;
                }
                if (arg == "-eclass" && morearg > 0)
                {
                    EclassDir = System.IO.Path.GetFullPath(args[index + 1]);
                    index++;
                    continue;
                }
                if (arg == "-logo" && morearg > 0)
                {
                    LogoFile = System.IO.Path.GetFullPath(args[index + 1]);
                    index++;
                    continue;
                }
                if (arg == "-aasxrepo" && morearg > 0)
                {
                    AasxRepositoryFn = System.IO.Path.GetFullPath(args[index + 1]);
                    index++;
                    continue;
                }

                // tail. last argument shall be file to load
                if (System.IO.File.Exists(args[index]))
                    AasxToLoad = args[index];
                break;
            }
        }

        private static Random MyRnd = new Random(); 

        public static string GenerateIdAccordingTemplate (string tpl)
        {
            // generate a deterministic decimal digit string
            var decimals = String.Format("{0:ffffyyMMddHHmmss}", DateTime.UtcNow);
            decimals = new string(decimals.Reverse().ToArray());
            // convert this to an int
            Int64 decii;
            if (!Int64.TryParse(decimals, out decii))
                decii = MyRnd.Next(Int32.MaxValue);
            // make an hex out of this
            string hexamals = decii.ToString("X");
            // make an alphanumeric string out of this
            string alphamals = "";
            var dii = decii;
            while (dii>=1)
            {
                var m = dii % 26;
                alphamals += Convert.ToChar(65 + m);
                dii = dii / 26;
            }

            // now, "salt" the strings
            for (int i=0; i<32; i++)
            {
                var c = Convert.ToChar(48 + MyRnd.Next(10));
                decimals += c;
                hexamals += c;
                alphamals += c;
            }

            // now, can just use the template
            var id = "";
            for (int i=0; i<tpl.Length; i++)
            {
                if (tpl[i] == 'D' && decimals.Length > 0)
                {
                    id += decimals[0];
                    decimals = decimals.Remove(0, 1);
                }
                else
                if (tpl[i] == 'X' && hexamals.Length > 0)
                {
                    id += hexamals[0];
                    hexamals = hexamals.Remove(0, 1);
                }
                else
                if (tpl[i] == 'A' && alphamals.Length > 0)
                {
                    id += alphamals[0];
                    alphamals = alphamals.Remove(0, 1);
                }
                else
                    id += tpl[i];
            }

            // ok
            return id;
        }
    }
}
