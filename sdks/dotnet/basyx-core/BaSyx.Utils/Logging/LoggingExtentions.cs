/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
using BaSyx.Utils.ResultHandling;
using NLog;
using System;
using System.Net.Http;
using System.Text;

namespace BaSyx.Utils.Logging
{
    public static class LoggingExtentions
    {
        public static void LogResult(this IResult result, ILogger logger, LogLevel logLevel, string additionalText = null, Exception exp = null)
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
            if (result.Entity != null && result.Entity is HttpResponseMessage response)
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

        public static Microsoft.Extensions.Logging.LogLevel GetLogLevel(ILogger logger)
        {
            if (logger.IsDebugEnabled)
                return Microsoft.Extensions.Logging.LogLevel.Debug;
            else if (logger.IsErrorEnabled)
                return Microsoft.Extensions.Logging.LogLevel.Error;
            else if (logger.IsFatalEnabled)
                return Microsoft.Extensions.Logging.LogLevel.Critical;
            else if (logger.IsInfoEnabled)
                return Microsoft.Extensions.Logging.LogLevel.Information;
            else if (logger.IsTraceEnabled)
                return Microsoft.Extensions.Logging.LogLevel.Trace;
            else if (logger.IsWarnEnabled)
                return Microsoft.Extensions.Logging.LogLevel.Warning;
            else
                return Microsoft.Extensions.Logging.LogLevel.None;
        }
    }
}
