using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;

namespace BaSys40.Models.Core.Constraints
{
    public class ConstraintConverter : JsonConverter<IConstraint>
    {
        public override bool CanWrite => false;
        public override bool CanRead => true;

        public override IConstraint ReadJson(JsonReader reader, Type objectType, IConstraint existingValue, bool hasExistingValue, JsonSerializer serializer)
        {
            JObject jObject;

            try
            {
                jObject = JObject.Load(reader);
            }
            catch (Exception)
            {
                return null;
            }

            var modelTypeToken = jObject.SelectToken("modelType")?.ToObject<ModelType>();

            IConstraint constraint;

            if (modelTypeToken != null)
            {
                if (modelTypeToken == ModelType.Qualifier)
                    constraint = new Qualifier();
                else if (modelTypeToken == ModelType.Formula)
                    constraint = new Formula();
                else
                    return null;
            }
            else
                return null;

            serializer.Populate(jObject.CreateReader(), constraint);

            return constraint;            
        }

        public override void WriteJson(JsonWriter writer, IConstraint value, JsonSerializer serializer)
        {
            throw new NotImplementedException();
        }
    }
}
