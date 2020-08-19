using BaSyx.Utils.ResultHandling;
using Microsoft.AspNetCore.Mvc;
using System;

namespace BaSyx.API.Http.Controllers
{
    /// <summary>
    /// Helper class for handling Action Results for HTTP-Requests
    /// </summary>
    public static class ResultHandling
    {
        /// <summary>
        /// Returns a Result-Object in an ObjectResult with status code 400 and a message which element is null or empty
        /// </summary>
        /// <param name="elementName">The name of the element which is null or empty</param>
        /// <returns></returns>
        public static IActionResult NullResult(string elementName)
        {
            ObjectResult objectResult = new ObjectResult(new Result(false, new Message(MessageType.Error, $"Argument {elementName} is null or empty")))
            {
                StatusCode = 400
            };
            return objectResult;
        }
        /// <summary>
        /// Returns a Result-Object wrapped in an ObjectResult according to the CRUD-operation
        /// </summary>
        /// <param name="result">The orignary Result object</param>
        /// <param name="crud">The CRUD-operation taken</param>
        /// <param name="route">Optional route for Create-Operations</param>
        /// <returns></returns>
        public static IActionResult CreateActionResult(this IResult result, CrudOperation crud, string route = null)
        {
            if (result == null)
            {
                ObjectResult objectResult = new ObjectResult(new Result(false, new Message(MessageType.Error, "Result object is null")))
                {
                    StatusCode = 500
                };
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
    /// <summary>
    /// Enumeration of the different CRUD-Operations
    /// </summary>
    public enum CrudOperation
    {
        /// <summary>
        /// Create
        /// </summary>
        Create,
        /// <summary>
        /// Retrieve
        /// </summary>
        Retrieve,
        /// <summary>
        /// Update
        /// </summary>
        Update,
        /// <summary>
        /// Delete
        /// </summary>
        Delete,
        /// <summary>
        /// Invoke
        /// </summary>
        Invoke
    }
}
