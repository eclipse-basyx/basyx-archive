using BaSys40.Models.Core;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Net;
using System.Reflection;
using System.Web;

namespace BaSys40.RI.AAS.SmartControl
{
    public static class SmartControlUtils
    {
        public static Operation GetOperationDescription(MethodInfo method)
        {
            Operation operation = new Operation();
            //operation.Identification = new Identifier(method.Name, Identificator.Internal);
            operation.IdShort = method.Name;
            var parameters = method.GetParameters();
            operation.In = new List<IOperationVariable>(parameters.Length);
            for (int i = 0; i < parameters.Length; i++)
            {
                operation.In.Add(
                    new OperationVariable()
                    {
                        DataType = DataType.GetDataTypeFromSystemTypes(parameters[i].ParameterType),
                        IdShort = parameters[i].Name
                    }
                );
            }

            operation.Out = new List<IOperationVariable>()
            {
                new OperationVariable()
                {
                    Index = 0,
                    DataType = DataType.GetDataTypeFromSystemTypes(method.ReturnType)
                }
            };

            return operation;
        }
      
        public static List<IArgument> ConvertStringArguments(string argumentString, List<IOperationVariable> referenceArguments)
        {
            string urlDecoded = Uri.UnescapeDataString(argumentString);
            object[] args;
            try
            {
                JArray ja = JArray.Parse(urlDecoded);
                args = ja.ToObject<object[]>();
            }
            catch
            {
                JToken val = JValue.Parse(urlDecoded);
                object oVal = val.ToObject<object>();
                args = new object[] {oVal };
            }

            if (args.Length > 0)
            {
                List<IArgument> arguments = new List<IArgument>();
                for (int i = 0; i < args.Length; i++)
                {
                    arguments.Add(
                        new Argument()
                        {
                            Index = i,
                            IdShort = referenceArguments[i].IdShort,
                            ValueType = referenceArguments[i].DataType,
                            Value = args[i]
                        });
                }
                return arguments;
            }
            return null;
        }

        public static string ConvertStringArguments(List<IArgument> arguments)
        {
            string argumentString = string.Empty;
            if (arguments?.Count == 1)
            {
                if (arguments[0].ValueType.DataObjectType == DataObjectType.String)
                    argumentString += '"' + HttpUtility.JavaScriptStringEncode((string)arguments[0].Value) + '"';
                else if (arguments[0].ValueType.DataObjectType == DataObjectType.Float || arguments[0].ValueType.DataObjectType == DataObjectType.Double)
                    argumentString += ((float)arguments[0].Value).ToString(System.Globalization.CultureInfo.GetCultureInfo("en-US").NumberFormat);
                else
                    argumentString += arguments[0].Value.ToString();
            }
            else if (arguments?.Count > 1)
            {
                argumentString += SmartControl.ParameterStrings.PARAM_BRACKET_LEFT;
                for (int i = 0; i < arguments.Count; i++)
                {
                    if (arguments[i].ValueType.DataObjectType == DataObjectType.String)
                        argumentString += '"' + HttpUtility.JavaScriptStringEncode((string)arguments[i].Value) + '"';
                    else if (arguments[i].ValueType.DataObjectType == DataObjectType.Float || arguments[i].ValueType.DataObjectType == DataObjectType.Double)
                        argumentString += ((float)arguments[i].Value).ToString(System.Globalization.CultureInfo.GetCultureInfo("en-US").NumberFormat);
                    else
                        argumentString += arguments[i].Value.ToString();

                    if (i != arguments.Count - 1)
                        argumentString += SmartControl.ELEMENT_SEPERATOR;
                }
                argumentString += SmartControl.ParameterStrings.PARAM_BRACKET_RIGHT;
            }
            else
                argumentString = SmartControl.ParameterStrings.PARAM_BRACKET_LEFT + SmartControl.ParameterStrings.PARAM_BRACKET_RIGHT;

            string urlEncoded = Uri.EscapeDataString(argumentString);
            return urlEncoded;
        }

        public static string ConvertPropertyValue(IValue value)
        {
            string content = string.Empty;
            if (value?.ValueType?.DataObjectType == DataObjectType.String)
                content = '"' + HttpUtility.JavaScriptStringEncode((string)value.Value) + '"';
            else if (value?.ValueType?.DataObjectType == DataObjectType.AnyType)
                content = '"' + HttpUtility.JavaScriptStringEncode(JsonConvert.SerializeObject(value.Value)) + '"';
            else if (value?.ValueType?.DataObjectType == DataObjectType.Float || value?.ValueType?.DataObjectType == DataObjectType.Double)
                content = ((float)value.Value).ToString(System.Globalization.CultureInfo.GetCultureInfo("en-US").NumberFormat);
            else if (value?.ValueType?.DataObjectType == DataObjectType.Bool)
                content = value?.Value?.ToString().ToLower();
            else
                content = value?.Value?.ToString();

            if(!string.IsNullOrEmpty(content))
                return Uri.EscapeDataString(content);
            else
                return string.Empty;
        }

        public static IValue ConvertPropertyValue(string propertyValue, DataType dataType)
        {
            string urlDecoded = Uri.UnescapeDataString(propertyValue);

            var value = urlDecoded.ToObject(dataType);

            var dataElementValue = new DataElementValue(value, dataType);
            return dataElementValue;
        }

        public static object ToObject(this string value, DataType dataType)
        {
            try
            {
                var jVal = JValue.Parse(value);
                var convertedVal = jVal.ToObject(DataType.GetSystemTypeFromDataType(dataType));
                return convertedVal;
            }
            catch (Exception e)
            {
                throw new InvalidCastException("Cannot convert to " + dataType.DataObjectType + " - Exception: " + e.Message);
            }
        }

        public static string ConvertDataTypeNames(DataType dataType)
        {
            string dataTypeName = dataType.DataObjectType.Name.ToLower();
            switch (dataTypeName)
            {
                case "integer":
                case "int": return "int";
                case "bool":
                case "boolean": return "bool";
                case "none": return "void";
                case "decimal":
                case "double":
                case "float": return "float";
                default:
                    return ("string");
            }
        }
    }
}

