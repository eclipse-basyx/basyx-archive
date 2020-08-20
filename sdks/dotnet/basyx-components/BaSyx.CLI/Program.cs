using BaSyx.CLI.Options;
using CommandLine;
using NLog;
using System.Collections.Generic;

namespace BaSyx.CLI
{
    class Program
    {
        private static readonly Logger logger = LogManager.GetCurrentClassLogger();


        static int Main(string[] args)
        {
            logger.Info("BaSyx Cli starting job...");

            return Parser.Default.ParseArguments<RefineOptions>(args)
                    .MapResult(
                      (RefineOptions opts) => RefineOptions.RunRefineAndReturnExitCode(opts), 
                      HandleParseError);
        }

        static int HandleParseError(IEnumerable<Error> errs) => -1;
    }
}
