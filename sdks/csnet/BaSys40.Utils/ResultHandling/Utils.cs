using Microsoft.AspNetCore.Mvc;
using System;
using System.Diagnostics;
using System.Net;
using System.Threading;

namespace BaSys40.Utils.ResultHandling
{
    public static class Utils
    {
        public static bool RetryUntilSuccessOrTimeout(Func<bool> task, TimeSpan timeout, TimeSpan pause)
        {
            if (pause.TotalMilliseconds < 0)
            {
                throw new ArgumentException("pause must be >= 0 milliseconds");
            }
            var stopwatch = Stopwatch.StartNew();
            do
            {
                if (task())
                    return true; 

                Thread.Sleep((int)pause.TotalMilliseconds);
            }
            while (stopwatch.Elapsed < timeout);
            return false;
        }

        public static bool TryParseStatusCode(IResult result, out int iHttpStatusCode)
        {
            try
            {
                bool success = false;
                var msgs = result.Messages.FindAll(m => !string.IsNullOrEmpty(m.Code));
                if (msgs != null && msgs.Count > 0)
                    foreach (var msg in msgs)
                    {
                        success = Enum.TryParse(msg.Code, out HttpStatusCode httpStatusCode);
                        if (success)
                        {
                            iHttpStatusCode = (int)httpStatusCode;
                            return success;
                        }
                    }
                iHttpStatusCode = (int)HttpStatusCode.BadRequest;
                return success;
            }
            catch
            {
                iHttpStatusCode = (int)HttpStatusCode.BadRequest;
                return false;
            }
        }

        public static IActionResult EvaluateResult(IResult result, CrudOperation crud)
        {
            if (result == null)
                return new StatusCodeResult(502);

            var objResult = new ObjectResult(result);

            switch (crud)
            {
                case CrudOperation.Create:
                    if (result.Success && result.Entity != null)
                        objResult.StatusCode = 201;
                    break;
                case CrudOperation.Retrieve:
                    if (result.Success && result.Entity != null)
                        objResult.StatusCode = 200;
                    else if (result.Success && result.Entity == null)
                        objResult.StatusCode = 404;
                    break;
                case CrudOperation.Update:
                    if (result.Success)
                        objResult.StatusCode = 204;
                    break;
                case CrudOperation.Delete:
                    if (result.Success)
                        objResult.StatusCode = 204;
                    break;
                default:
                    break;
            }

            if (!objResult.StatusCode.HasValue)
            {
                if (TryParseStatusCode(result, out int httpStatusCode))
                    objResult.StatusCode = httpStatusCode;
                else
                    objResult.StatusCode = 502;
            }

            return objResult;
        }

        public enum CrudOperation
        {
            Create,
            Retrieve,
            Update,
            Delete
        }
    }
}
