using BaSys40.Utils.ResultHandling;
using NLog;
using System;
using System.Net.Http;
using System.Text;

namespace BaSys40.Utils.Logging
{
    public static class LoggingExtentions
    {
        public static void LogResult(this IResult result, Logger logger, LogLevel logLevel, string additionalText = null, Exception exp = null)
        {
            StringBuilder logText = new StringBuilder();
            logText.Append("Success: " + result.Success).Append(" || ");

            if (result.Messages != null)
            {
                for (int i = 0; i < result.Messages.Count; i++)
                {
                    logText.Append("Message[" + i + "] = " + result.Messages[i].Text).Append(" || ");
                }
            }
            if (result.Entity != null && result is HttpResponseMessage response)
            {
                logText.Append("StatusCode: " + ((int)response.StatusCode).ToString()).Append(response.ReasonPhrase).Append(" || ");
                logText.Append("Body: " + response.Content.ReadAsStringAsync().Result).Append(" || ");
            }
            if (!string.IsNullOrEmpty(additionalText))
                logText.Append("AdditionalText: " + additionalText).Append(" || ");

            string msg = logText.ToString();
            if (exp != null)
                logger.Log(logLevel, exp, msg);
            else
                logger.Log(logLevel, msg);
        }
    }
}
