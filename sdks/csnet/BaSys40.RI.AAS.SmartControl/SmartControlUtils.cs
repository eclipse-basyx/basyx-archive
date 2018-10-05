using BaSys40.Models.Core;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.AssetAdministrationShell.Implementations;
using BaSys40.Models.Core.Identification;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Reflection;

namespace BaSys40.RI.AAS.SmartControl
{
    public static class SmartControlUtils
    {

        public static PropertyDescription GetPropertyDescription(PropertyInfo property)
        {
            PropertyDescription propertyDescription = new PropertyDescription();
            propertyDescription.Identification = new Identifier(property.Name, Identificator.Internal);
            propertyDescription.DisplayName = property.Name;
            propertyDescription.Writable = property.CanWrite;
            propertyDescription.Readable = property.CanRead;
            propertyDescription.DataType = new DataType(GetDataObjectType(property.PropertyType), IsEnumerableType(property.PropertyType), IsCollectionType(property.PropertyType));

            return propertyDescription;
        }
        public static OperationDescription GetOperationDescription(MethodInfo method)
        {
            OperationDescription operation = new OperationDescription();
            operation.Identification = new Identifier(method.Name, Identificator.Internal);
            operation.DisplayName = method.Name;
            var parameters = method.GetParameters();
            operation.InputParameters = new List<IParameter>(parameters.Length);
            for (int i = 0; i < parameters.Length; i++)
            {
                operation.InputParameters.Add(
                    new Parameter()
                    {
                        DataType = new DataType(GetDataObjectType(parameters[i].ParameterType), IsEnumerableType(parameters[i].ParameterType), IsCollectionType(parameters[i].ParameterType)),
                        ParameterName = parameters[i].Name
                    }
                );
            }

            operation.OutputParameters = new List<IParameter>()
            {
                new Parameter()
                {
                    Index = 0,
                    DataType = new DataType(GetDataObjectType(method.ReturnType), IsEnumerableType(method.ReturnType), IsCollectionType(method.ReturnType))
                }
            };

            return operation;
        }
        static bool IsEnumerableType(Type type)
        {
            return (type.GetInterface(nameof(IList)) != null);
        }

        static bool IsCollectionType(Type type)
        {
            return (type.GetInterface(nameof(IDictionary)) != null);
        }

        static bool IsSimple(Type type)
        {
            var typeInfo = type.GetTypeInfo();
            if (typeInfo.IsGenericType && typeInfo.GetGenericTypeDefinition() == typeof(Nullable<>))
                return IsSimple(typeInfo.GetGenericArguments()[0]);

            return typeInfo.IsPrimitive
              || typeInfo.IsEnum
              || type.Equals(typeof(string))
              || type.Equals(typeof(decimal));
        }

        private static DataObjectType GetDataObjectType(Type type)
        {
            switch (type.FullName)
            {
                case "System.Void": return DataObjectType.Void;
                case "System.String": return DataObjectType.String;
                case "System.SByte": return DataObjectType.Int8;
                case "System.Int16": return DataObjectType.Int16;
                case "System.Int32": return DataObjectType.Int32;
                case "System.Int64": return DataObjectType.Int64;
                case "System.Byte": return DataObjectType.UInt8;
                case "System.UInt16": return DataObjectType.UInt16;
                case "System.UInt32": return DataObjectType.UInt32;
                case "System.UInt64": return DataObjectType.UInt64;
                case "System.Boolean": return DataObjectType.Bool;
                case "System.Single": return DataObjectType.Float;
                case "System.Double": return DataObjectType.Double;
                default:
                    if (!IsSimple(type))
                        return DataObjectType.Object;
                    else
                        return DataObjectType.None;
            }
        }

        public static List<IArgument> ConvertStringArguments(string[] args)
        {
            List<IArgument> inputArguments = new List<IArgument>(args.Length);
            for (int i = 0; i < args.Length; i++)
            {
                inputArguments.Add(
                    new Argument()
                    {
                        Index = i,
                        DataType = new DataType(DataObjectType.String, false, false),
                        Value = new ElementValue<string>(args[i])
                    });
            }
            return inputArguments;
        }

        public static string[] ConvertStringArguments(List<IArgument> arguments)
        {
            string[] args = new string[arguments.Count];
            for (int i = 0; i < args.Length; i++)
            {
                args[i] = arguments[i].Value.Value.ToString();
            }
            return args;
        }
    }
}
