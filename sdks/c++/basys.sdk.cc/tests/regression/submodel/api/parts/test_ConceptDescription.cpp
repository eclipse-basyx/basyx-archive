#include <gtest/gtest.h>
#include <tuple>

#include <BaSyx/submodel/map_v2/parts/ConceptDescription.h>
#include <BaSyx/submodel/simple/parts/ConceptDescription.h>

#include <BaSyx/util/util.h>

using namespace basyx::submodel;

using ImplTypes = ::testing::Types<
    std::tuple<map::ConceptDescription, map::Reference, map::DataSpecification>,
    std::tuple<simple::ConceptDescription, simple::Reference, simple::DataSpecification>>;

template <class Impl>
class ConceptDescriptionTest : public ::testing::Test {
protected:
    using impl_t = typename std::tuple_element<0, Impl>::type;
    using impl_ref_t = typename std::tuple_element<1, Impl>::type;
    using impl_dataSpec_t = typename std::tuple_element<2, Impl>::type;

    std::unique_ptr<impl_t> conceptDescription;
    std::unique_ptr<impl_ref_t> testingReference;
    std::unique_ptr<impl_dataSpec_t> testingDataSpecification;

protected:
    void SetUp() override
    {
        simple::Identifier id;
        conceptDescription = util::make_unique<impl_t>("test id", id);
        simple::Key key(KeyElements::Property, true, KeyType::IdShort, "test");
        testingReference = util::make_unique<impl_ref_t>(key);
        testingDataSpecification = util::make_unique<impl_dataSpec_t>("test data spec", id);
    }

    void TearDown() override
    {
    }
};

TYPED_TEST_CASE(ConceptDescriptionTest, ImplTypes);

TYPED_TEST(ConceptDescriptionTest, TestConstructor)
{
    auto& cases = this->conceptDescription->getIsCaseOf();
    auto& references = this->conceptDescription->getDataSpecificationReference();

    ASSERT_EQ(references.size(), 0);
    ASSERT_EQ(cases.size(), 0);
}

TYPED_TEST(ConceptDescriptionTest, TestAddIsCaseOf)
{
    this->conceptDescription->addIsCaseOf(std::move(this->testingReference));

    auto& references = this->conceptDescription->getIsCaseOf();
    auto keys = references.at(0)->getKeys();
    ASSERT_EQ(1, keys.size());
    ASSERT_EQ("test", keys.at(0).getValue());
}

TYPED_TEST(ConceptDescriptionTest, TestAddEmbeddedDataSpecification)
{
    this->conceptDescription->addEmbeddedDataSpecification(std::move(this->testingDataSpecification));

    auto& references = this->conceptDescription->getEmbeddedDataSpecification();
    ASSERT_NE(references.getElement("test data spec"), nullptr);
}
