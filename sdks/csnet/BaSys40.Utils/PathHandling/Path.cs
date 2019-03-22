namespace BaSys40.Utils.PathHandling
{
    public static class Path
    {
        public static string GetFormattedEndpoint(string endpoint, string aggregateId, string entityId, string separator = "/")
        {
            if (endpoint[endpoint.Length - 1] == separator[0])
            {
                if (!endpoint.Contains(aggregateId))
                    endpoint += aggregateId + separator + entityId;
                else
                    endpoint += entityId;
            }
            else
            {
                if (!endpoint.Contains(aggregateId))
                    endpoint += separator + aggregateId + separator + entityId;
                else
                    endpoint += separator + entityId;
            }

            return endpoint;
        }
    }
}
