using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using Swashbuckle.AspNetCore.Swagger;
using Swashbuckle.AspNetCore.SwaggerGen;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;

namespace BaSys40.Component.REST
{
    internal class SchemaFilter : ISchemaFilter
    {
        public void Apply(Schema model, SchemaFilterContext context)
        {
            if (context.SystemType.IsInterface)
            {
                string newName = context.SystemType.Name.Substring(1, context.SystemType.Name.Length - 1);
                model.Title = newName;
            }
        }
    }

    internal class DocumentFilter : IDocumentFilter
    {
        public void Apply(SwaggerDocument swaggerDoc, DocumentFilterContext context)
        {
            var models = typeof(IAssetAdministrationShell).Assembly;
            var types = models.GetTypes().Where(t => Attribute.IsDefined(t, typeof(DataContractAttribute)) && !t.IsGenericType);
            foreach (var type in types)
            {
                context.SchemaRegistry.GetOrRegister(type);
            }

            List<string> modelRemoveList = new List<string>();
            foreach (var model in context.SchemaRegistry.Definitions)
            {
                if (model.Key.StartsWith("I") && models.GetTypes().Any(t => t.Name == model.Key && t.IsInterface))
                    modelRemoveList.Add(model.Key);
            }

            foreach (var model in modelRemoveList)
            {
                //context.SchemaRegistry.Definitions.Remove(model);
                foreach (var schema in context.SchemaRegistry.Definitions)
                {
                    ReplaceRefInSchema(schema.Value, model);
                }
            }

        }

        public void ReplaceRefInSchema(Schema schema, string modelName)
        {
            if (!string.IsNullOrEmpty(schema.Ref))
            {
                if (modelName == "IValue")
                    schema.Ref = schema.Ref.Replace("IValue", "ElementValue");
                else
                    schema.Ref = schema.Ref.Replace(modelName, modelName.Substring(1));
            }
            if (schema.Properties != null)
            {
                foreach (var prop in schema.Properties)
                {
                    ReplaceRefInSchema(prop.Value, modelName);
                }

            }
            if (schema.Items != null)
                ReplaceRefInSchema(schema.Items, modelName);
        }
    }
}
