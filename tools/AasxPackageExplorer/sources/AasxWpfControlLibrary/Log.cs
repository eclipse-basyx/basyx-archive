using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace Logging
{
    public class Log
    {
        /// <summary>
        /// Will display debug messages only if level is smaller/equal than this level
        /// </summary>
        public static int DebugLevel = 0;

        public const int ColorBlack = 0;
        public const int ColorBlue = 1;
        public const int ColorRed = 2;

        private static string appendToFileFN = "";
        private static StreamWriter appendToFileWriter = null;
        public static string AppendToFile
        {
            get
            {
                return appendToFileFN;
            }
            set
            {
                appendToFileFN = value.Trim();
                if (appendToFileFN.Length < 1 && appendToFileWriter != null)
                {
                    appendToFileWriter.Close();
                    appendToFileWriter = null;
                }
                else
                if (appendToFileFN.Length > 0 && appendToFileWriter == null)
                {
                    appendToFileWriter = new StreamWriter(appendToFileFN, true);
                }
            }
        }

        public static void CloseFiles()
        {
            if (appendToFileWriter != null)
                appendToFileWriter.Close();
        }

        public class StoredPrint
        {
            public int color = 0;
            public string msg = "";
            public string linkTxt = "";
            /// <param name="msg">The complete message; can contain %LINK% as substitude position for link text</param>
            public StoredPrint(string msg)
            {
                this.color = ColorBlack;
                this.msg = msg;
            }
            /// <param name="color">Color code black/ blue/ red</param>
            /// <param name="msg">The complete message; can contain %LINK% as substitude position for link text</param>
            public StoredPrint(int color, string msg)
            {
                this.color = color;
                this.msg = msg;
            }
            /// <param name="color">Color code black/ blue/ red</param>
            /// <param name="msg">The complete message; can contain %LINK% as substitude position for link text</param>
            /// <param name="linkTxt">Link text</param>
            public StoredPrint(int color, string msg, string linkTxt)
            {
                this.color = color;
                this.msg = msg;
                this.linkTxt = linkTxt;
            }
        }

        public static List<StoredPrint> StoredPrints = new List<StoredPrint>();


        /// <summary>
        /// Incremented for each error
        /// </summary>
        public static int NumberErrors = 0;

        /// <summary>
        /// Clears errors
        /// </summary>
        public static void ClearNumberErrors()
        {
            NumberErrors = 0;
        }

        /// <summary>
        /// Only append to file, if file name is set
        /// </summary>
        public static void Silent(string msg, params object[] args)
        {
            Append(msg, args);
        }

        /// <summary>
        /// Display a message, which is for information only
        /// </summary>
        public static void Info(string msg, params object[] args)
        {
            Append(msg, args);
            Print(ColorBlack, msg, args);
        }

        /// <summary>
        /// Display a message, which is for information only
        /// </summary>
        public static void Info(int level, string msg, params object[] args)
        {
            Append(msg, args);

            if (level > DebugLevel)
                return;

            Print(ColorBlack, msg, args);
        }

        /// <summary>
        /// Display a message, which is for information only        
        /// </summary>
        /// <param name="link">The %LINK% portion in the message will be substituded with a hyperlink</param>
        public static void InfoWithHyperlink(int level, string msg, string link, params object[] args)
        {
            Append(msg, args);

            if (level > DebugLevel)
                return;

            PrintWithHyperlink(ColorBlack, msg, link, args);
        }

        /*
        public static string ShortLocation (Exception ex)
        {
            if (ex == null || ex.StackTrace == null)
                return "";
            string[] lines = ex.StackTrace.Split(new[] { "\r\n", "\r", "\n" }, StringSplitOptions.None);
            if (lines.Length < 1)
                return "";
            // search for " in "
            var p = lines[0].IndexOf(" in ");
            if (p < 0)
                return "";
            // search last "\" or "/", to get only filename portion and position
            p = lines[0].LastIndexOfAny(new char[] { '\\', '/' });
            if (p < 0)
                return "";
            // return this
            return lines[0].Substring(p);
        }
        */

        /// <summary>
        /// Display a message, which is for errors
        /// </summary>
        public static void Error(string msg, params object[] args)
        {
            Append(msg, args);
            NumberErrors++;
            Print(ColorRed, msg, args);
        }

        /// <summary>
        /// Display a message, which is for errors
        /// </summary>
        public static void Error(Exception ex, string where)
        {
            Append("Error {0}: {1} {2} at {3}.",
                where,
                ex.Message,
                ((ex.InnerException != null) ? ex.InnerException.Message : ""),
                AdminShellNS.AdminShellUtil.ShortLocation(ex));
            NumberErrors++;
            Print(ColorRed, "Error {0}: {1} {2} at {3}.",
                where,
                ex.Message,
                ((ex.InnerException != null) ? ex.InnerException.Message : ""),
                AdminShellNS.AdminShellUtil.ShortLocation(ex));
        }

        /// <summary>
        /// Display a message, which is for derrors      
        /// </summary>
        /// <param name="link">The %LINK% portion in the message will be substituded with a hyperlink</param>
        public static void ErrorWithHyperlink(string msg, string link, params object[] args)
        {
            Append(msg, args);
            PrintWithHyperlink(ColorBlack, msg, link, args);
        }

        public static void Print(int color, string msg, params object[] args)
        {
            var s = String.Format(msg, args);
            StoredPrints.Add(new StoredPrint(color, s));
            Console.WriteLine(s);
        }

        public static void PrintWithHyperlink(int color, string msg, string link, params object[] args)
        {
            var s = String.Format(msg, args);
            StoredPrints.Add(new StoredPrint(color, s, link));
            Console.WriteLine(s);
        }

        public static void Append(string msg, params object[] args)
        {
            if (appendToFileWriter == null)
                return;
            appendToFileWriter.WriteLine(msg, args);
            appendToFileWriter.Flush();
        }

        /// <summary>
        /// Uses mutex for thread safe output
        /// </summary>
        /// <param name="msg"></param>
        /// <param name="args"></param>
        public static void LockedInfo(int level, string msg, params object[] args)
        {
            lock (Log.StoredPrints)
            {
                Log.Info(level, msg, args);
            }
        }

        /// <summary>
        /// Uses mutex for thread safe output
        /// </summary>
        /// <param name="msg"></param>
        /// <param name="args"></param>
        public static void LockedInfo(string msg, params object[] args)
        {
            lock (Log.StoredPrints)
            {
                Log.Info(msg, args);
            }
        }

        /// <summary>
        /// Uses mutex for thread safe output
        /// </summary>
        /// <param name="msg"></param>
        /// <param name="args"></param>
        public static void LockedError(string msg, params object[] args)
        {
            lock (Log.StoredPrints)
            {
                Log.Error(msg, args);
            }
        }
    }
}
