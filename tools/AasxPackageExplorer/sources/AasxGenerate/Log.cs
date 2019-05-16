using System;
using System.IO;
using System.Text;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace SimpleLog {
     
     public class Log {

          public static int verbosity = 2;

          public static void WriteLine(int level, string fmt, params object[] args) {
               if (level > verbosity)
                    return;
               var st = string.Format(fmt, args);
               Console.Out.WriteLine(st) ;
          }

          public static void WriteLine(string fmt, params object[] args) {
               WriteLine(1, fmt, args);
          }
     }

}