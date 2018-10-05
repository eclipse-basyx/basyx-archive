using System.Collections.Generic;

namespace BaSys40.Utils.Settings
{
    public interface ISettings
    {
        string FilePath { get; set; }
        Dictionary<string, string> Miscellaneous { get; set; }
    }
}