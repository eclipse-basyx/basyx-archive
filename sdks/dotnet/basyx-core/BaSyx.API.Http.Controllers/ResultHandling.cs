using BaSyx.Utils.ResultHandling;
using Microsoft.AspNetCore.Mvc;
using System;

namespace BaSyx.API.Http.Controllers
{
    public static class ResultHandling
    {
        public static IActionResult NullResult(string elementName)
        {
            ObjectResult objectResult = new ObjectResult(new Result(false, new Message(MessageType.Error, $"Argument {elementName} is null or empty")))
            {
                StatusCode = 400
            };
            return objectResult;
        }

        public static IActionResult CreateActionResult(this IResult result, CrudOperation crud, string route = null)
        {
            if (result == null)
            {
                ObjectResult objectResult = new ObjectResult(new Result(false, new Message(MessageType.Error, "Result object is null")));
                objectResult.StatusCode = 500;
                return objectResult;
            }

            switch (crud)
            {
                case CrudOperation.Create:
                    if (result.Success && result.Entity != null)
                        return new CreatedResult(route, result.Entity);
                    break;
                case CrudOperation.Retrieve:
                    if (result.Success && result.Entity != null)
                        return new OkObjectResult(result.Entity);
                    break;
                case CrudOperation.Update:
                    if (result.Success)
                        return new OkObjectResult(result.Entity);
                    break;
                case CrudOperation.Delete:
                    if (result.Success)
                        return new NoContentResult();
                    break;
                case CrudOperation.Invoke:
                    if (result.Success && result.Entity != null)
                        return new OkObjectResult(result.Entity);
                    break;
                default:
                    return new BadRequestObjectResult(result);
            }

            if (!result.Success)
            {
                ObjectResult objectResult = new ObjectResult(result);
                if (result.IsException.HasValue && result.IsException.Value)
                    objectResult.StatusCode = 500;
                else
                {
                    IMessage message = result.Messages?.Find(m => m.Code != null);
                    if (message != null && Int32.TryParse(message.Code, out int statusCode))
                        objectResult.StatusCode = statusCode;
                }
                return objectResult;
            }

            return new BadRequestObjectResult(result);
        }
    }
    public enum CrudOperation
    {
        Create,
        Retrieve,
        Update,
        Delete,
        Invoke
    }
}
