using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Speaker
{
    class Program
    {
        static void Main(string[] args)
        {
            Speaker speaker = new Speaker();
            speaker.SaySomething("Hello World!", "en-EN");
            do
            {
                while (!Console.KeyAvailable)
                {
                    Console.Write("Please enter text [Q=Exit]:");
                    var text = Console.ReadLine();

                    if (text == "Q")
                        return;

                    speaker.SaySomething(text, "en-EN");
                }

            } while (Console.ReadKey(true).Key != ConsoleKey.Escape);

        }
    }
}
