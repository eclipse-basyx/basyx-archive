using BaSys40.Models.Connectivity;

namespace BaSys40.API.ServiceProvider
{
    public interface IServiceProvider<T>
    {
        IServiceDescriptor ServiceDescriptor { get; }
        void BindTo(T element);
        T GetBinding();
    }
}
