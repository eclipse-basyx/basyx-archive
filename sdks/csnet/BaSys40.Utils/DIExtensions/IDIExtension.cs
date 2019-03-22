using System;

namespace BaSys40.Utils.DIExtensions
{
    public interface IDIExtension
    {
        Type GetRegisteredTypeFor(Type t);
        bool IsTypeRegistered(Type t);
    }
}
