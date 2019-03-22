using BaSys40.API.ServiceProvider;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BaSys40.Component.REST.Pages
{
    public class IndexModel : PageModel
    {
        public IAssetAdministrationShellServiceProvider ServiceProvider { get; }

        public IndexModel(IAssetAdministrationShellServiceProvider provider)
        {
            ServiceProvider = provider;
        }

        public void OnGet()
        {

        }
    }
}