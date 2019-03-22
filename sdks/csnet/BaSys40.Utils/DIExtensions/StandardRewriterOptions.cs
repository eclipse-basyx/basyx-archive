using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Rewrite;
using System;
using System.Collections.Generic;
using System.Linq;

namespace BaSys40.Utils.DIExtensions
{
    public static class StandardRewriterOptions
    {
        private static readonly List<string> RouteExceptions = new List<string>() { "fonts", "ui", "scripts", "images"};

        public static void AddStandardRewriterOptions(this IApplicationBuilder applicationBuilder)
        {
            RewriteOptions options = new RewriteOptions();
            options.Add(RewriteRule);
            applicationBuilder.UseRewriter(options);
        }

        private static void RewriteRule(RewriteContext obj)
        {
            string[] oldPath = obj.HttpContext.Request.Path.ToUriComponent().Split(new char[] { '/' }, StringSplitOptions.RemoveEmptyEntries);

            List<string> newPath = new List<string>();
            for (int i = 0; i < oldPath.Length; i++)
            {
                string pathElement = oldPath[i].ToLower();
                bool lastElement = (i == oldPath.Length - 1);
                if (pathElement == "aas" && lastElement)
                {
                    newPath.Add("/aas");
                    break;
                }
                else if (pathElement == "submodels")
                {
                    if ((i + 1) <= oldPath.Length - 1)
                    {
                        newPath.Add("/submodel");
                        obj.HttpContext.Request.QueryString = new QueryString("?submodelId=" + oldPath[i + 1]);
                    }
                    else
                        newPath.Add("/aas/submodels");
                }
                else if(i > 0 && oldPath[i - 1] != "submodels")
                {
                     newPath.Add(oldPath[i]);
                }
                else
                {
                    if (i == 0 && RouteExceptions.Contains(pathElement))
                    {
                        obj.Result = RuleResult.ContinueRules;
                        return;
                    }
                }
            }
            obj.HttpContext.Request.Path = new PathString(string.Join("/", newPath.ToArray()));
            obj.Result = RuleResult.ContinueRules;
        }
    }
}
