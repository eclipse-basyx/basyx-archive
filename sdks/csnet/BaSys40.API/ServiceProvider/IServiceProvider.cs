using BaSys40.Models.Core.Identification;
using System;
using System.Collections.Generic;
using System.Text;

namespace BaSys40.API.ServiceProvider
{
    public interface IServiceProvider<T>
    {
        void BindTo(T element);
        T GetBinding();
    }
}
