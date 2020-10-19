using BaSyx.Models.Core.AssetAdministrationShell.Implementations;
using BaSyx.Models.Core.Common;
using BaSyx.Models.Extensions;
using FluentAssertions;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace BaSyx.Core.Tests
{
    [TestClass]
    public class ExtensionTest
    {

        [TestMethod]
        public void TestMethod1_NonGenericProperty_GetSetValue()
        {
            Property property = new Property("TestProperty", typeof(string));

            IValue setValue = new ElementValue<string>("TestValue");
            property.SetValue(setValue);
            IValue getValue = property.GetValue();

            getValue.ValueType.Should().BeEquivalentTo(new DataType(DataObjectType.String));
            getValue.Value.Should().BeEquivalentTo("TestValue");

            property.SetValue("NewTestValue");
            string newValue = property.GetValue<string>();
            newValue.Should().BeEquivalentTo("NewTestValue");

            property.SetValue(new ElementValue<string>("SuperNewTest"));
            IValue getSuperNewValue = property.GetValue();

            getSuperNewValue.ValueType.Should().BeEquivalentTo(new DataType(DataObjectType.String));
            getSuperNewValue.Value.Should().BeEquivalentTo("SuperNewTest");
        }

        [TestMethod]
        public void TestMethod2_GenericProperty_GetSetValue()
        {
            Property<string> property = new Property<string>("TestProperty");

            IValue setValue = new ElementValue<string>("TestValue");
            property.SetValue(setValue);
            IValue getValue = property.GetValue();

            getValue.ValueType.Should().BeEquivalentTo(new DataType(DataObjectType.String));
            getValue.Value.Should().BeEquivalentTo("TestValue");

            property.SetValue("NewTestValue");
            string newValue = property.GetValue<string>();
            newValue.Should().BeEquivalentTo("NewTestValue");
        }
    }
}
