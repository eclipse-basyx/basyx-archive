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
using Microsoft.AspNetCore.Mvc;
using System;
using System.Diagnostics;
using System.Net;
using System.Threading;

namespace BaSyx.Utils.ResultHandling
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

        public static IActionResult EvaluateResult(IResult result, CrudOperation crud, string route = null)
        {
            if (result == null)
                return new StatusCodeResult(502);

            switch (crud)
            {
                case CrudOperation.Create:
                    if (result.Success && result.Entity != null)
                        return new CreatedResult(route, result.Entity);
                    break;
                case CrudOperation.Retrieve:
                    if (result.Success && result.Entity != null)
                        return new OkObjectResult(result.Entity);
                    else
                        return new NotFoundObjectResult(result);
                case CrudOperation.Update:
                    if (result.Success)
                        return new OkObjectResult(result.Entity);
                    break;
                case CrudOperation.Delete:
                    if (result.Success)
                        return new NoContentResult();
                    break;
                default:
                    return new BadRequestObjectResult(result);
            }
            return new BadRequestObjectResult(result);
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
