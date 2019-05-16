using System;
using System.IO;
using System.IO.Packaging;
using System.Xml.Serialization;
using System.Collections;
using System.Collections.Generic;
using Newtonsoft.Json;
using SimpleLog;
using System.Xml;
using AdminShellNS;

#pragma warning disable CS0162 // dead code

/* Copyright (c) 2018-2019 Festo AG & Co. KG <https://www.festo.com/net/de_de/Forms/web/contact_international>, author: Michael Hoffmeister
This software is licensed under the Eclipse Public License - v 2.0 (EPL-2.0) (see https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.txt).
The browser functionality is under the cefSharp license (see https://raw.githubusercontent.com/cefsharp/CefSharp/master/LICENSE).
The JSON serialization is under the MIT license (see https://github.com/JamesNK/Newtonsoft.Json/blob/master/LICENSE.md). */

namespace opctest
{
    class Program
    {        

        public static AdminShell.Submodel CreateSubmodelCad (InputFilePrefs prefs, AdminShellNS.UriIdentifierRepository repo, AdminShell.AdministrationShellEnv aasenv) {

            // CONCEPTS
            var cdGroup = AdminShell.ConceptDescription.CreateNew("URI", repo.CreateOrRetrieveUri("Festo Submodel Cad Item Group"));
            aasenv.ConceptDescriptions.Add(cdGroup);
            cdGroup.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "CAD Dateieinheit", "EN", "CAD file item" },
                shortName: "CadItem",
                unit: "",
                definition: new string[] { "DE", "Gruppe von Merkmalen, die Zugriff gibt auf eine Datei für ein CAD System.", "EN", "Collection of properties, which make a file for a CAD system accessible." }
            );

            var cdFile = AdminShell.ConceptDescription.CreateNew("URI", repo.CreateOrRetrieveUri("Festo Submodel Cad Item File Elem"));
            aasenv.ConceptDescriptions.Add(cdFile);
            cdFile.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Enthaltene CAD Datei", "EN", "Embedded CAD file" },
                shortName: "File",
                unit: "",
                definition: new string[] { "DE", "Verweis auf enthaltene CAD Datei.", "EN", "Reference to embedded CAD file." }
            );

            var cdFormat = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-ZAA120#007");
            aasenv.ConceptDescriptions.Add(cdFormat);
            cdFormat.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Filetype CAD", "EN", "Filetype CAD" },
                shortName: "FileFormat",
                unit: "",
                definition: new string[] { "DE", "Eindeutige Kennung Format der eingebetteten CAD Datei im eCl@ss Standard.", "EN", "Unambigous ID of format of embedded CAD file in eCl@ss standard." }
            );

            // SUB MODEL
            var sub1 = AdminShell.Submodel.CreateNew("URI", repo.CreateOneTimeId());
            sub1.idShort = "CAD";
            aasenv.Submodels.Add(sub1);
            sub1.semanticId.Keys.Add(AdminShell.Key.CreateNew("Submodel", false, "URI", "http://smart.festo.com/id/type/submodel/festocad/1/1"));

            // for each cad file in prefs
            int ndx = 0;
            foreach (var fr in prefs.filerecs) {

                if (fr.submodel != "cad")
                    continue;
                if (fr.args == null || fr.args.Count != 1)
                    continue;

                ndx++;

                // GROUP
                var propGroup = AdminShell.SubmodelElementCollection.CreateNew($"CadItem{ndx:D2}", "PARAMETER", AdminShell.Key.GetFromRef(cdGroup.GetReference()));
                sub1.Add(propGroup);

                // FILE
                var propFile = AdminShell.File.CreateNew("File", "PARAMETER", AdminShell.Key.GetFromRef(cdFile.GetReference()));
                propGroup.Add(propFile);
                propFile.mimeType = AdminShell.PackageEnv.GuessMimeType(fr.fn);
                propFile.value = "" + fr.targetdir.Trim() + System.IO.Path.GetFileName(fr.fn) ;

                // FILEFORMAT
                var propType = AdminShell.ReferenceElement.CreateNew("FileFormat", "PARAMETER", AdminShell.Key.GetFromRef(cdFormat.GetReference()));
                propGroup.Add(propType);
                propType.value = AdminShell.Reference.CreateNew(AdminShell.Key.CreateNew("GlobalReference", false, "IRDI", "" + fr.args[0]));
            }

            return sub1;
        }

        public static AdminShell.Submodel CreateSubmodelDocumentation(InputFilePrefs prefs, AdminShellNS.UriIdentifierRepository repo, AdminShell.AdministrationShellEnv aasenv)
        {

            // CONCEPTS
            var cdGroup = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-AAD001#001");
            aasenv.ConceptDescriptions.Add(cdGroup);
            cdGroup.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Dokumentationsgruppe", "EN", "Documentation item" },
                shortName: "DocumentationItem",
                definition: new string[] { "DE", "Gruppe von Merkmalen, die Zugriff gibt auf eine Dokumentation für ein Asset, beispielhaft struktuiert nach VDI 2770.", 
                "EN", "Collection of properties, which gives access to documentation of an asset, structured exemplary-wise according to VDI 2770." }
            );

            var cdClassName = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-AAD003#002");
            aasenv.ConceptDescriptions.Add(cdClassName);
            cdClassName.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Doku. Klassifiziersname", "EN", "Doc. classification name" },
                shortName: "DocClassName",
                definition: new string[] { "DE", "Name der Klassifizierung nach VDI 2770 für das Dokument, nach eCl@ss Standard.",
                "EN", "Name of classification of the Document according VDI 2770 and eCl@ss." }
            );

            var cdClass = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-AAD003#007");
            aasenv.ConceptDescriptions.Add(cdClass);
            cdClass.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Doku. Klassifizierung", "EN", "Doc. classification"},
                shortName: "DocClass",
                definition: new string[] { "DE", "Eindeutige Klassifizierung nach VDI 2770 für das Dokument, nach eCl@ss Standard.", 
                "EN", "Classification of the Document according VDI 2770 and eCl@ss." }
            );

            var cdTitle = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-AAD004#007");
            aasenv.ConceptDescriptions.Add(cdTitle);
            cdTitle.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Titel des Dokuments", "EN", "Documentation title"},
                shortName: "DocTitle",
                definition: new string[] { "DE", "Titel des Dokuments, wie vom Hersteller/ Erbringer des Assets vorgegeben.",
                "EN", "Title of documentation, as described by producer of the asset." }
            );

            var cdFilename = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-AAD005#005");
            aasenv.ConceptDescriptions.Add(cdFilename);
            cdFilename.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Dateiname des Dokuments", "EN", "Filename of documentation" },
                shortName: "DocFilename",
                definition: new string[] { "DE", "Name der bereitgestellten Datei, wie vom Hersteller des Assets vorgesehen.",
                "EN", "Name of embedded file, as described by producer of the asset." }
            );

            var cdVersion = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-AAD005#006");
            aasenv.ConceptDescriptions.Add(cdVersion);
            cdVersion.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Version des Dokuments", "EN", "Version of documentation" },
                shortName: "DocVersion",
                definition: new string[] { "DE", "Versionsstand der bereitgestellten Datei, wie vom Hersteller des Assets vorgesehen.",
                "EN", "Version of embedded file, as described by producer of the asset." }
            );

            var cdFormat = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-ZAA120#007");
            aasenv.ConceptDescriptions.Add(cdFormat);
            cdFormat.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Dateiformat Doku.", "EN", "File format doc. item" },
                shortName: "DocFormat",
                definition: new string[] { "DE", "Eindeutige Kennung Format der eingebetteten Dokumentations-Datei im eCl@ss Standard.", 
                "EN", "Unambigous ID of format of embedded documentation file in eCl@ss standard." }
            );

            var cdFile = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-AAD005#008");
            aasenv.ConceptDescriptions.Add(cdFile);
            cdFile.SetIEC61360Spec(
                preferredNames: new string[] { "DE", "Enthaltene Doku. Datei", "EN", "Embedded Doc. file" },
                shortName: "File",
                definition: new string[] { "DE", "Verweis/ BLOB auf enthaltene Dokumentations-Datei.", "EN", "Reference/ BLOB to embedded documentation file." }
            );

            // SUB MODEL
            var sub1 = AdminShell.Submodel.CreateNew("URI", repo.CreateOneTimeId());
            sub1.idShort = "DocuVDI2270";
            aasenv.Submodels.Add(sub1);
            sub1.semanticId.Keys.Add(AdminShell.Key.CreateNew("Submodel", false, "URI", "http://www.zvei.de/standards/i40/beispiel_i40_komponente/submodel/vdi2770/1/1"));

            // for each file one group
            int ndx = 0;
            foreach (var fr in prefs.filerecs)
            {
                if (fr.submodel != "docu")
                    continue;
                if (fr.args == null || fr.args.Count != 5)
                    continue;

                ndx++;

                // GROUP
                var cd = cdGroup;
                using (var p0 = AdminShell.SubmodelElementCollection.CreateNew($"DocItem{ndx:D2}", "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference()))) {
                    
                    sub1.Add(p0);

                    // CLASS NAME
                    cd = cdClassName;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.valueType = "string";
                        p.value = "" + fr.args[0];
                    }

                    // CLASS
                    cd = cdClass;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p.Set("GlobalReference", false, "IRDI", "" + fr.args[1]);
                        p0.Add(p);                        
                    }

                    // TITLE
                    cd = cdTitle;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.valueType = "string";
                        p.value = "" + fr.args[2];
                    }

                    // FILENAME
                    cd = cdFilename;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.valueType = "string";
                        p.value = "" + System.IO.Path.GetFileName(fr.fn);
                    }

                    // VERSION
                    cd = cdVersion;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.valueType = "string";
                        p.value = "" + fr.args[3];
                    }

                    // FORMAT
                    cd = cdFormat;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p.Set("GlobalReference", false, "IRDI", "" + fr.args[4]);
                        p0.Add(p);
                    }

                    // FILE
                    cd = cdFile;
                    using (var p = AdminShell.File.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.mimeType = AdminShell.PackageEnv.GuessMimeType(fr.fn);
                        p.value = "" + fr.targetdir.Trim() + System.IO.Path.GetFileName(fr.fn);
                    }

                }

            }

            // for each url one group
            foreach (var web in prefs.webrecs)
            {
                if (web.submodel != "docu")
                    continue;
                if (web.args == null || web.args.Count != 5)
                    continue;

                // GROUP
                var cd = cdGroup;
                using (var p0 = AdminShell.SubmodelElementCollection.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference()))) {
                    
                    sub1.Add(p0);

                    // CLASS NAME
                    cd = cdClassName;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.valueType = "string";
                        p.value = "" + web.args[0];
                    }

                    // CLASS
                    cd = cdClass;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p.Set("GlobalReference", false, "IRDI", "" + web.args[1]);
                        p0.Add(p);
                    }

                    // TITLE
                    cd = cdTitle;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.valueType = "string";
                        p.value = "" + web.args[2];
                    }

                    // FILENAME
                    cd = cdFilename;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.valueType = "string";
                        p.value = "" + System.IO.Path.GetFileName(web.url);
                    }

                    // VERSION
                    cd = cdVersion;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.valueType = "string";
                        p.value = "" + web.args[3];
                    }

                    // FORMAT
                    cd = cdFormat;
                    using (var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p.Set("GlobalReference", false, "IRDI", "" + web.args[4]);
                        p0.Add(p);
                    }

                    // FILE -> URL
                    cd = cdFile;
                    using (var p = AdminShell.File.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference())))
                    {
                        p0.Add(p);
                        p.mimeType = AdminShell.PackageEnv.GuessMimeType(web.url);
                        p.value = web.url;
                    }

                }

            }

            return sub1;
        }

        public static AdminShell.Submodel CreateSubmodelDatasheet(InputFilePrefs prefs, AdminShellNS.UriIdentifierRepository repo, AdminShell.AdministrationShellEnv aasenv)
        {
            // eClass product group: 19-15-07-01 USB stick 

            // SUB MODEL
            var sub1 = AdminShell.Submodel.CreateNew("URI", repo.CreateOneTimeId());
            sub1.idShort = "Datatsheet";
            aasenv.Submodels.Add(sub1);
            sub1.semanticId.Keys.Add(AdminShell.Key.CreateNew("Submodel", false, "URI", "http://smart.festo.com/id/type/submodel/festodatasheet/1/1"));

            // CONCEPT: Manufacturer
            using (var cd = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-AAO677#001")) {
                aasenv.ConceptDescriptions.Add(cd);
                cd.SetIEC61360Spec(
                    preferredNames: new string[] { "DE", "TBD", "EN", "Manufacturer name" },
                    shortName: "Manufacturer",
                    definition: new string[] { "DE", "TBD",
                    "EN", "legally valid designation of the natural or judicial person which is directly responsible for the design, production, packaging and labeling of a product in respect to its being brought into circulation" }
                );
                
                var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference()));
                sub1.Add(p);
                p.valueType = "string";
                p.value = "Festo AG & Co. KG";
            }

            // CONCEPT: Width
            using (var cd = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-BAF016#005"))
            {
                aasenv.ConceptDescriptions.Add(cd);
                cd.SetIEC61360Spec(
                    preferredNames: new string[] { "DE", "Breite", "EN", "Width" },
                    shortName: "Width",
                    unit: "mm",
                    valueFormat: "REAL_MEASURE",
                    definition: new string[] { "DE", "bei eher rechtwinkeligen Körpern die orthogonal zu Höhe/Länge/Tiefe stehende Ausdehnung rechtwinklig zur längsten Symmetrieachse",
                    "EN", "for objects with orientation in preferred position during use the dimension perpendicular to height/ length/depth" }
                );

                var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference()));
                sub1.Add(p);
                p.valueType = "double";
                p.value = "48";
            }

            // CONCEPT: Height
            using (var cd = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-BAA020#008"))
            {
                aasenv.ConceptDescriptions.Add(cd);
                cd.SetIEC61360Spec(
                    preferredNames: new string[] { "DE", "Höhe", "EN", "Height" },
                    shortName: "Height",
                    unit: "mm",
                    valueFormat: "REAL_MEASURE",
                    definition: new string[] { "DE", "bei eher rechtwinkeligen Körpern die orthogonal zu Länge/Breite/Tiefe stehende Ausdehnung - bei Gegenständen mit fester Orientierung oder in bevorzugter Gebrauchslage der parallel zur Schwerkraft gemessenen Abstand zwischen Ober- und Unterkante",
                    "EN", "for objects with orientation in preferred position during use the dimension perpendicular to diameter/length/width/depth" }
                );

                var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference()));
                sub1.Add(p);
                p.valueType = "double";
                p.value = "56";
            }

            // CONCEPT: Depth
            using (var cd = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-BAB577#007"))
            {
                aasenv.ConceptDescriptions.Add(cd);
                cd.SetIEC61360Spec(
                    preferredNames: new string[] { "DE", "Tiefe", "EN", "Depth" },
                    shortName: "Depth",
                    unit: "mm",
                    valueFormat: "REAL_MEASURE",
                    definition: new string[] { "DE", "bei Gegenständen mit fester Orientierung oder in bevorzugter Gebrauchslage wird die nach hinten, im Allgemeinen vom Betrachter weg verlaufende Ausdehnung als Tiefe bezeichnet",
                    "EN", "for objects with fixed orientation or in preferred utilization position, the rear , generally away from the observer expansion is described as depth" }
                );

                var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference()));
                sub1.Add(p);
                p.valueType = "double";
                p.value = "11.9";
            }

            // CONCEPT: Weight
            using (var cd = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-AAS627#001"))
            {
                aasenv.ConceptDescriptions.Add(cd);
                cd.SetIEC61360Spec(
                    preferredNames: new string[] { "DE", "Gewicht der Artikeleinzelverpackung", "EN", "Weight of the individual packaging" },
                    shortName: "Weight",
                    unit: "g",
                    valueFormat: "REAL_MEASURE",
                    definition: new string[] { "DE", "Masse der Einzelverpackung eines Artikels",
                    "EN", "Mass of the individual packaging of an article" }
                );

                var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference()));
                sub1.Add(p);
                p.valueType = "double";
                p.value = "23";
            }

            // CONCEPT: Material
            using (var cd = AdminShell.ConceptDescription.CreateNew("IRDI", "0173-1#02-BAB577#007"))
            {
                aasenv.ConceptDescriptions.Add(cd);
                cd.SetIEC61360Spec(
                    preferredNames: new string[] { "DE", "Werkstoff", "EN", "Material" },
                    shortName: "Material",
                    definition: new string[] { "DE", "TBD",
                    "EN", "Materialzusammensetzung, aus der ein einzelnes Bauteil hergestellt ist, als Ergebnis eines Herstellungsprozesses, in dem der/die Rohstoff(e) durch Extrusion, Verformung, Schweißen usw. in die endgültige Form gebracht werden" }
                );

                var p = AdminShell.ReferenceElement.CreateNew(cd.GetShortName(), "PARAMETER", AdminShell.Key.GetFromRef(cd.GetReference()));
                sub1.Add(p);
                p.value = p.value = AdminShell.Reference.CreateNew(AdminShell.Key.CreateNew("GlobalReference", false, "IRDI", "0173-1#07-AAA878#004")); // Polyamide (PA)
            }

            // Nice
            return sub1;
        }

        public static AdminShell.Submodel CreateSubmodelDatasheetSingleItems(
            AdminShellNS.UriIdentifierRepository repo, AdminShell.AdministrationShellEnv aasenv)
        {
            // SUB MODEL
            var sub1 = AdminShell.Submodel.CreateNew("URI", repo.CreateOneTimeId());
            sub1.idShort = "Datatsheet";
            aasenv.Submodels.Add(sub1);
            sub1.semanticId.Keys.Add(AdminShell.Key.CreateNew(
                type:   "Submodel", 
                local:  false, 
                idType: "URI", 
                value:  "http://smart.festo.com/id/type/submodel/festodatasheet/1/1"));

            // eClass product group: 19-15-07-01 USB stick              
            // siehe: http://www.eclasscontent.com/?id=19150701&version=10_1&language=de&action=det

            // CONCEPT: Weight by Michael Hoffmeister                   // Schreiben Sie hier Ihren Namen
            using (var cd = AdminShell.ConceptDescription.CreateNew(
                idType:"IRDI",                                          // immer IRDI für eCl@ss
                id:"0173-1#02-AAS627#001"))                             // die ID des Merkmales bei eCl@ss
            {
                aasenv.ConceptDescriptions.Add(cd);
                cd.SetIEC61360Spec(
                    preferredNames: new string[] { 
                        "DE", "Gewicht der Artikeleinzelverpackung",    // wechseln Sie die Sprache bei eCl@ss 
                        "EN", "Weight of the individual packaging" },   // um die Sprach-Texte aufzufinden
                    shortName: "Weight",                                // kurzer, sprechender Name
                    unit: "g",                                          // Gewicht als SI Einheit ohne Klammern
                    valueFormat: "REAL_MEASURE",                        // REAL oder INT_MEASURE oder STRING
                    definition: new string[] { "DE", "Masse der Einzelverpackung eines Artikels",
                    "EN", "Mass of the individual packaging of an article" }
                );

                var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", 
                            AdminShell.Key.GetFromRef(cd.GetReference()));
                sub1.Add(p);
                p.valueType = "double";                                 // hier den Datentypen im XSD-Format
                p.value = "23";                                         // hier den Wert; immer als String mit
            }                                                           // doppelten Anfuehrungszeichen

            // eClass product group: 19-15-07-01 USB stick              
            // siehe: http://www.eclasscontent.com/?id=19150701&version=10_1&language=de&action=det

            // CONCEPT: Color by Dominik                   // Schreiben Sie hier Ihren Namen
            using (var cd = AdminShell.ConceptDescription.CreateNew(
                idType:"IRDI",                                          // immer IRDI für eCl@ss
                id:"0173-1#02-AAS624#002"))                             // die ID des Merkmales bei eCl@ss
            {
                aasenv.ConceptDescriptions.Add(cd);
                cd.SetIEC61360Spec(
                    preferredNames: new string[] { 
                        "DE", "Farbcode der Artikeleinzelverpackung",    // wechseln Sie die Sprache bei eCl@ss 
                        "EN", "Color code of the individual packaging" },   // um die Sprach-Texte aufzufinden
                    shortName: "Color",                                // kurzer, sprechender Name
                    unit: "g",                                          // Gewicht als SI Einheit ohne Klammern
                    valueFormat: "STRING",                        // REAL oder INT_MEASURE oder STRING
                    definition: new string[] { "DE", "Farbe der Einzelverpackung eines Artikels",
                    "EN", "Color of the individual packaging of an article" }
                );

                var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", 
                            AdminShell.Key.GetFromRef(cd.GetReference()));
                sub1.Add(p);
                p.valueType = "string";                                 // hier den Datentypen im XSD-Format
                p.value = "Blue";                                       // hier den Wert; immer als String mit
            }                                                           // doppelten Anfuehrungszeichen

            // CONCEPT: Manufacturer by Stefan Pollmeier                // Schreiben Sie hier Ihren Namen
            using (var cd = AdminShell.ConceptDescription.CreateNew(
                idType:"IRDI",                                          // immer IRDI für eCl@ss
                id:"0173-1#02-AAO677#001"))                             // die ID des Merkmales bei eCl@ss
            {
                aasenv.ConceptDescriptions.Add(cd);
                cd.SetIEC61360Spec(
                    preferredNames: new string[] { 
                        "DE", "Herstellername",    // wechseln Sie die Sprache bei eCl@ss 
                        "EN", "Manufacturer name" },   // um die Sprach-Texte aufzufinden
                    shortName: "ManufName",                                // kurzer, sprechender Name
                    unit: "mm",                                          // Gewicht als SI Einheit ohne Klammern
                    valueFormat: "STRING",                        // REAL oder INT_MEASURE oder STRING
                    definition: new string[] { "DE", "TBD",
                    "EN", "legally valid designation of the natural or judicial person..." }
                );

                var p = AdminShell.Property.CreateNew(cd.GetShortName(), "PARAMETER", 
                            AdminShell.Key.GetFromRef(cd.GetReference()));
                sub1.Add(p);
                p.valueType = "string";                                 // hier den Datentypen im XSD-Format
                p.value = "Festo AG & Co. KG";                                         // hier den Wert; immer als String mit
            }                                                           // doppelten Anfuehrungszeichen

            // Nice
            return sub1;
        }

        private static void CreateStochasticViewOnSubmodelsRecurse(AdminShell.View vw, AdminShell.Submodel submodel, AdminShell.SubmodelElement sme)
        {
            if (vw == null || sme == null)
                return;

            var isSmc = (sme is AdminShell.SubmodelElementCollection);

            // spare out some of the leafs of the tree ..
            if (!isSmc)
                if (Math.Abs(sme.idShort.GetHashCode() % 100) > 50)
                    return;

            // ok, create
            var ce = new AdminShell.ContainedElementRef();
            sme.CollectReferencesByParent(ce.Keys);
            vw.AddContainedElement(ce.Keys);
            // recurse
            if (isSmc)
                foreach (var sme2wrap in (sme as AdminShell.SubmodelElementCollection).value)
                    CreateStochasticViewOnSubmodelsRecurse(vw, submodel, sme2wrap.submodelElement);
        }

        public static AdminShell.View CreateStochasticViewOnSubmodels (AdminShell.Submodel[] sms, string idShort)
        {
            // create
            var vw = new AdminShell.View();
            vw.idShort = idShort;

            // over all submodel elements
            if (sms != null)
                foreach (var sm in sms)
                {
                    // parent-ize submodel
                    sm.SetAllParents();

                    // loop in
                    foreach (var sme in sm.submodelElements)
                        CreateStochasticViewOnSubmodelsRecurse(vw, sm, sme.submodelElement);
                }
            // done
            return vw;
        }

        public class InputFilePrefs {

            public class FileRec {
                public string fn = "";
                public string submodel = "";
                public string targetdir = "";
                public List<string> args = new List<string>() ;
            }

            public List<FileRec> filerecs = new List<FileRec>() ;

            public class WebRec {
                public string url = "";
                public string submodel = "";
                public List<string> args = new List<string>() ;
            }

            public List<WebRec> webrecs = new List<WebRec>() ;

            public FileRec FindFileRecFn(string thefn) {
                foreach (var pr in filerecs)
                    if (thefn.ToLower().Trim() == pr.fn.ToLower().Trim())
                        return pr;
                return null;
            }
        }

        public static void Test4() {

            // MAKE or LOAD prefs
            InputFilePrefs prefs = new InputFilePrefs();
            var preffn = "prefs.json";
            try {                
                if (File.Exists(preffn)) {
                    Log.WriteLine(2, "Opening {0} for reading preferences ..", preffn);
                    var init = File.ReadAllText(preffn);
                    Log.WriteLine(2, "Parsing preferences ..") ;
                    prefs = JsonConvert.DeserializeObject<InputFilePrefs>(init);
                } else {
                    Log.WriteLine(2, "Using built-in preferences ..");
                    var init = @"{ 'filerecs' : [
                            { 'fn' : 'data\\thumb-usb.jpeg',                'submodel' : 'thumb',   'targetdir' : '/',                      'args' : [ ] },
                            { 'fn' : 'data\\USB_Hexagon.stp',               'submodel' : 'cad',     'targetdir' : '/aasx/cad/',             'args' : [ '0173-1#02-ZBQ121#003' ] },
                            { 'fn' : 'data\\USB_Hexagon.igs',               'submodel' : 'cad',     'targetdir' : '/aasx/cad/',             'args' : [ '0173-1#02-ZBQ128#008' ] },
                            { 'fn' : 'data\\FES_100500.edz',                'submodel' : 'cad',     'targetdir' : '/aasx/cad/',             'args' : [ '0173-1#02-ZBQ133#002' ] },
                            { 'fn' : 'data\\USB_Hexagon_offen.jpeg',        'submodel' : 'docu',    'targetdir' : '/aasx/documentation/',   'args' : [ 'Drawings', '0173-1#02-ZWY722#001', 'Product rendering open', 'V1.2', '0173-1#02-ZHK61a#002' ] },
                            { 'fn' : 'data\\USB_Hexagon_geschlossen.jpeg',  'submodel' : 'docu',    'targetdir' : '/aasx/documentation/',   'args' : [ 'Presales', '0173-1#02-ZWX723#001', 'Product rendering closed', 'V1.2c', '0173-1#02-ZHK622#001' ] },
                            { 'fn' : 'data\\docu_cecc_presales_DE.PDF',     'submodel' : 'docu',    'targetdir' : '/aasx/documentation/',   'args' : [ 'Presales', '0173-1#02-ZWX723#001', 'Steuerungen CECC', 'V2.1.3', '0173-1#02-ZHK622#001' ] },
                            { 'fn' : 'data\\docu_cecc_presales_EN.PDF',     'submodel' : 'docu',    'targetdir' : '/aasx/documentation/',   'args' : [ 'Presales', '0173-1#02-ZWX723#001', 'Controls CECC', 'V2.1.4', '0173-1#02-ZHK622#001' ] },
                            { 'fn' : 'data\\USB_storage_medium_datasheet_EN.pdf', 'submodel' : 'docu', 'targetdir' : '/aasx/documentation/', 'args' : [ 'Technical specification', '0173-1#02-ZWX724#001', 'Datenblatt Steuerung CECC-LK', 'V1.0', '0173-1#02-ZHK622#001' ] },
                            { 'fn' : 'data\\docu_cecc_install_DE.PDF',      'submodel' : 'docu',    'targetdir' : '/aasx/documentation/',   'args' : [ 'Installation', '0173-1#02-ZWX725#001', 'Kurzbeschreibung Steuerung CECC-LK', 'V3.2a', '0173-1#02-ZHK622#001' ] },
                            { 'fn' : 'data\\docu_cecc_install_EN.PDF',      'submodel' : 'docu',    'targetdir' : '/aasx/documentation/',   'args' : [ 'Installation', '0173-1#02-ZWX725#001', 'Brief description control CECC-LK', 'V3.6b', '0173-1#02-ZHK622#001' ] },
                            { 'fn' : 'data\\docu_cecc_fullmanual_DE.PDF',   'submodel' : 'docu',    'targetdir' : '/aasx/documentation/',   'args' : [ 'Manual', '0173-1#02-ZWX727#001', 'Beschreibung Steuerung CECC-LK', '1403a', '0173-1#02-ZHK622#001' ] },
                            { 'fn' : 'data\\docu_cecc_fullmanual_EN.PDF',   'submodel' : 'docu',    'targetdir' : '/aasx/documentation/',   'args' : [ 'Manual', '0173-1#02-ZWX727#001', 'Description Steuerung CECC-LK', '1403a', '0173-1#02-ZHK622#001' ] },
                        ],  'webrecs' : [
                            { 'url' : 'https://www.festo.com/net/de_de/SupportPortal/Downloads/385954/407353/CECC_2013-05a_8031104e2.pdf',          'submodel' : 'docu',      'args' : [ 'Installation', '0173-1#02-ZWX725#001', 'Controlador CECC', '2013-05a', '0173-1#02-ZHK662#002' ] },
                            { 'url' : 'https://www.festo.com/net/SupportPortal/Files/407352/CECC_2013-05a_8031105x2.pdf',                           'submodel' : 'docu',      'args' : [ 'Installation', '0173-1#02-ZWX725#001', 'Controllore CECC', '2013-05a', '0173-1#02-ZHK699#003' ] },
                        ] }";
                    Log.WriteLine(3, "Dump of built-in preferences: {0}", init);
                    Log.WriteLine(2, "Parsing preferences ..") ;
                    prefs = JsonConvert.DeserializeObject<InputFilePrefs>(init);
                }
            } catch (Exception ex) {
                Console.Error.Write("While parsing preferences: " + ex.Message);
                Environment.Exit(-1);
            }

            // REPOSITORY
            var repo = new AdminShellNS.UriIdentifierRepository();
            try {
                if (!repo.Load("uri-repository.xml"))
                    repo.InitRepository("uri-repository.xml");
            } catch (Exception ex) {
                Console.Error.Write("While accessing URI repository: " + ex.Message);
                Environment.Exit(-1);
            }

            // AAS ENV
            var aasenv1 = new AdminShell.AdministrationShellEnv();

            try
            {

                // ASSET
                var asset1 = new AdminShell.Asset();
                aasenv1.Assets.Add(asset1);
                asset1.SetIdentification("URI", "http://pk.festo.com/3s7plfdrs35", "3s7plfdrs35");
                asset1.AddDescription("EN", "Festo USB Stick");
                asset1.AddDescription("DE", "Festo USB Speichereinheit");

                // CAD
                Log.WriteLine(2, "Creating submodel CAD ..");
                var subCad = CreateSubmodelCad(prefs, repo, aasenv1);

                // DOCU
                Log.WriteLine(2, "Creating submodel DOCU ..");
                var subDocu = CreateSubmodelDocumentation(prefs, repo, aasenv1);

                // DATASHEET
                Log.WriteLine(2, "Creating submodel DATASHEET ..");
                var subDatasheet = CreateSubmodelDatasheet(prefs, repo, aasenv1);
                //var subDatasheet = CreateSubmodelDatasheetSingleItems(repo, aasenv1);

                // VIEW1
                var view1 = CreateStochasticViewOnSubmodels(new AdminShell.Submodel[] { subCad, subDocu, subDatasheet }, "View1");

                // ADMIN SHELL            
                Log.WriteLine(2, "Create AAS ..");
                var aas1 = AdminShell.AdministrationShell.CreateNew("URI", repo.CreateOneTimeId(), "1", "0");
                aas1.derivedFrom = new AdminShell.AssetAdministrationShellRef(new AdminShell.Key("AssetAdministrationShell", false, "URI", "www.admin-shell.io/aas/sample-series-aas/1/1"));
                aasenv1.AdministrationShells.Add(aas1);
                aas1.assetRef = asset1.GetReference();

                // Link things together
                Log.WriteLine(2, "Linking entities to AAS ..");
                aas1.submodelRefs.Add(subCad.GetReference() as AdminShell.SubmodelRef);
                aas1.submodelRefs.Add(subDocu.GetReference() as AdminShell.SubmodelRef);
                aas1.submodelRefs.Add(subDatasheet.GetReference() as AdminShell.SubmodelRef);
                aas1.AddView(view1);

                if (true)
                {
                    asset1.assetIdentificationModelRef = new AdminShell.SubmodelRef(subDatasheet.GetReference() as AdminShell.SubmodelRef);
                }

            }
            catch (Exception ex)
            {
                Console.Error.Write("While building AAS: {0} at {1}", ex.Message, ex.StackTrace);
                Environment.Exit(-1);
            }

            if (true) {
                try {
                    //
                    // Test serialize
                    // this generates a "sample.xml" is addition to the package below .. for direct usag, e.g.
                    //
                    Log.WriteLine(2, "Test serialize sample.xml ..");
                    using (var s = new StreamWriter("sample.xml"))
                    {
                        var serializer = new XmlSerializer(aasenv1.GetType());
                        var nss = new XmlSerializerNamespaces();
                        nss.Add("aas", "http://www.admin-shell.io/aas/1/0");
                        nss.Add("IEC61360", "http://www.admin-shell.io/IEC61360/1/0");
                        serializer.Serialize(s, aasenv1, nss);
                    }
                } catch (Exception ex) {
                    Console.Error.Write("While test serializing XML: {0} at {1}", ex.Message, ex.StackTrace);
                    Environment.Exit(-1);
                }
            }

            //
            // Make PACKAGE
            //

            try {

                // use the library function
                var opcfn = "sample-admin-shell.aasx";
                Log.WriteLine(2, "Creating package {0} ..", opcfn);
                var package = new AdminShell.PackageEnv(aasenv1);

                // supplementary files
                Log.WriteLine(2, "Adding supplementary files ..");
                foreach (var fr in prefs.filerecs) {
                    Log.WriteLine(2, "  + {0}", fr.fn);
                    package.AddSupplementaryFileToStore(fr.fn, fr.targetdir, Path.GetFileName(fr.fn), fr.submodel == "thumb");
                }

                // save
                Log.WriteLine(2, "Saving ..");
                package.SaveAs(opcfn, writeFreshly: true);
                package.Close();

            } catch (Exception ex) {
                Console.Error.Write("While building OPC package: {0} at {1}", ex.Message, ex.StackTrace);
                Environment.Exit(-1);
            }

        }

        static void Main(string[] args)
        {
            Log.WriteLine("AAS and OPC Writer v0.3. (c) 2018 Michael Hoffmeister, Festo AG & Co. KG.");
            Test4();
        }
    }

}
