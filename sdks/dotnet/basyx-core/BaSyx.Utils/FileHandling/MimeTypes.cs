using System.Collections.Generic;
using System.IO;

namespace BaSyx.Utils.FileHandling
{
    public static class MimeTypes
    {
        public static Dictionary<string, string> Mappings = new Dictionary<string, string>()
        {
            { ".stp", "application/step" },
            { ".3dxml", "application/3dxml" }
        };

        public static bool TryGetContentType(string filePath, out string contentType)
        {
            string extension = Path.GetExtension(filePath);
            if(Mappings.ContainsKey(extension))
            {
                contentType = Mappings[extension];
                return true;
            }
            contentType = null;
            return false;
        }
    }
}
