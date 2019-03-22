using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace oneM2MClient.Utils
{
    public static class JsonExtensions
    {
        public static bool TryParseJson(this string json, out JToken token)
        {
            try
            {
                token = JToken.Parse(json);
                return true;
            }
            catch
            {
                token = null;
                return false;
            }
        }

        public static T TryParseJson<T>(this string json) where T : new()
        {
            try
            {
                T obj = JsonConvert.DeserializeObject<T>(json);
                return obj;
            }
            catch
            {
                return default(T);
            }
        }
    }
}
