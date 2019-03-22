using System;

namespace BaSys40.Utils.StringOperations
{
    public static class StringOperations
    {
        public static string GetValueOrStringEmpty<T>(T? nullable) where T : struct
        {
            if (nullable != null)
            {
                var value = Nullable.GetUnderlyingType(nullable.GetType());
                if (value != null && value.IsEnum)
                    Enum.GetName(Nullable.GetUnderlyingType(nullable.GetType()), nullable.Value);
                else
                    return nullable.Value.ToString();
            }
            return string.Empty;
        }
    }
}
