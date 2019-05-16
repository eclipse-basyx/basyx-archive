using System;
using System.Linq;
using System.Xml;
using System.Runtime;
using System.ComponentModel;
using System.Collections;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;
using System.Globalization;

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace AdminShellNS
{
     public class UriIdentifierRepository {

          public class Entry 
          {
               public string uri = "";
               public string purpose = "";
          }

          public class Repository 
          {
               public string uriTemplate = "http://smart.festo.com/id/instance/999#######################";
               public int lastIndex = 1;
               public List<Entry> entries = new List<Entry>() ;
          }

          private Repository repository = new Repository() ;
          private string workingFn = "";

          private string lastOTP = "";
          private int otpCounter = 1;
          private static readonly Random getrandom = new Random();

          public bool InitRepository(string fn, string uriTemplate=null) {
               repository = new Repository() ;
               if (uriTemplate != null)
                    repository.uriTemplate = uriTemplate ;
               return this.Save(fn);
          }

        public bool Load(string fn)
        {
            try
            {
                 // try open, see what happens ..
                var s = new StreamReader(fn);
                var serializer = new XmlSerializer(repository.GetType());
                repository = (Repository) serializer.Deserialize(s);
                s.Close();
            }
            catch (Exception ex)
            {
                Console.Error.WriteLine("When loading " + workingFn + " exception:" + ex.Message);
                return false;
            }
            // looks good
            workingFn = fn;     
            return true;
        }


        public bool Save(string fn=null) {
               if (fn != null)
                    workingFn = fn ;
               try {
                    var s = new StreamWriter(workingFn) ;
                    var serializer = new XmlSerializer(repository.GetType());
                    serializer.Serialize(s, repository);
                    s.Close() ;
               } catch (Exception ex)
               {
                    Console.Error.WriteLine("When saving " + workingFn + " exception:" + ex.Message);
                    return false;
               }
               return true;
          }

          public Entry FindUri(string uri)
          {
               foreach (var e in repository.entries)
                    if (e.uri == uri)
                         return e;
               return null;
          }

          public Entry FindPurpose(string purpose)
          {
               foreach (var e in repository.entries)
                    if (e.purpose == purpose)
                         return e;
               return null;
          }

          public string CreateOrRetrieveUri(string purpose) {
               // prepare purpose
               purpose = purpose.ToLower() ;
               var cleanPurpose = Regex.Replace(purpose, @"[^a-zA-Z0-9\-_]", "");
               // check if we have
               Entry found = FindPurpose(cleanPurpose) ;
               if (found != null)
                    return found.uri;
               // count '#' in template
               int digits = repository.uriTemplate.Count(c => c == '#') ;
               if (digits<1)
                    return null;
               // no, we have to build a new uri ..
               var i = repository.lastIndex;
               var uriFree = "";
               var newIndex = false ;
               while (true) {
                    // left padded i
                    var id = (""+i).PadLeft(digits, '0') ;
                    // replace '#'
                    var uri = new StringBuilder(repository.uriTemplate) ;
                    int j = 0 ;
                    for (int ii=0; ii<uri.Length; ii++)
                         if (uri[ii] == '#') {
                              uri[ii] = id[j] ;
                              j++;
                         }
                    uriFree = uri.ToString();
                    // is this free
                    if (FindUri(uriFree) == null)
                         break;
                    // increment
                    i++ ;    
                    repository.lastIndex = i;
                    newIndex = true;           
               }
               // assume: uriFree is OK
               var en = new Entry();
               en.uri = uriFree;
               en.purpose = cleanPurpose;
               repository.entries.Add(en);
               // be paranoid
               if (newIndex)
                    Save() ;
               // nice!
               return en.uri;
          }

          public string CreateOneTimeId() {

               // make up this otp
               var otp = DateTime.Now.ToString("yyyyMMddHHmmss");

               // reset counter
               if (otp != lastOTP) {
                    lastOTP = otp ;
                    otpCounter = 1;
               } else
                    otpCounter++ ;

               // ready to prepare
               // otp + left padded i
               var id = otp + (""+otpCounter).PadLeft(5, '0') ;

               // make uri
               var uri = new StringBuilder(repository.uriTemplate);
               int j = 0;
               for (int ii = 0; ii < uri.Length; ii++) {
                    if (j >= id.Length)
                         uri[ii] = (char) ('0' + getrandom.Next(0,9));
                    else
                         if (uri[ii] == '#')
                         {
                              uri[ii] = id[j];
                              j++;
                         }
               }
               // thats it
               return uri.ToString();
          }
     }

}