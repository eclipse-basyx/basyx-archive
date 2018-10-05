using System;
using System.Collections.Generic;
using BaSys40.Models.Core.AssetAdministrationShell.Generics;
using BaSys40.Models.Core.Identification;
using BaSys40.Utils.ResultHandling;

namespace BaSys40.API.ServiceProvider.Implementations
{
    public class InMemoryAssetAdministrationShellServiceProvider : IAssetAdministrationShellServiceProvider
    {
        internal IAssetAdministrationShell AssetAdministrationShell { get; set; }

        public InMemoryAssetAdministrationShellServiceProvider()
        { }

        public void BindTo(IAssetAdministrationShell aas)
        {
            if (aas != null)
                AssetAdministrationShell = aas;
        }

        public IAssetAdministrationShell GetBinding()
        {
            return AssetAdministrationShell;
        }


        public IResult<ISubModel> CreateSubModel(ISubModel subModel)
        {
            try
            {
                AssetAdministrationShell.SubModels.Add(subModel);
                return new Result<ISubModel>(true, subModel);
            }
            catch (Exception e)
            {
                return new Result<ISubModel>(e);
            }
        }

        public IResult<IElementContainer<ISubModel>> RetrieveSubModels()
        {
            try
            {
                return new Result<IElementContainer<ISubModel>>(true, AssetAdministrationShell.SubModels);
            }
            catch (Exception e)
            {
                return new Result<IElementContainer<ISubModel>>(e);
            }
        }

        public IResult<ISubModel> RetrieveSubModel(string subModelId)
        {
            try
            {
                var subModel = AssetAdministrationShell.SubModels[subModelId];
                if (subModel == null)
                    throw new KeyNotFoundException("subModelId not found");
                return new Result<ISubModel>(true, subModel);
            }
            catch (Exception e)
            {
                return new Result<ISubModel>(e);
            }
        }

        public IResult DeleteSubModel(string subModelId)
        {
            try
            {
                var subModel = AssetAdministrationShell.SubModels[subModelId];
                if (subModel == null)
                    throw new KeyNotFoundException("subModelId not found");
                AssetAdministrationShell.SubModels.Remove(subModel);
                return new Result(true);
            }
            catch (Exception e)
            {
                return new Result(e);
            }
        }

        
    }
}
