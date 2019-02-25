using System;
using System.Collections.Generic;
using System.Reflection;
using System.Text;

namespace BaSys40.Utils.ModelHandling
{
    public class ModelUtils
    {
        public static IEnumerable<Type> GetTypesWithAttribute(Assembly assembly, Type attributeType)
        {
            foreach (Type type in assembly.GetTypes())
            {
                if (type.GetCustomAttributes(attributeType, true).Length > 0)
                {
                    yield return type;
                }
            }
        }
    }
}
