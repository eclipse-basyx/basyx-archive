#include <gtest/gtest.h>

#include <BaSyx/submodel/simple/submodelelement/file/Blob.h>
#include <BaSyx/submodel/map_v2/submodelelement/file/Blob.h>

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	simple::Blob,
	map::Blob
>;

template<class Impl>
class BlobTest :public ::testing::Test {
protected:
	using impl_t = Impl;
protected:
	const std::string idShort = "id_test";
	const std::string mimeType = "bytes";

	std::unique_ptr<api::IBlob> blob;
protected:
	void SetUp() override
	{
		this->blob = util::make_unique<Impl>(idShort, mimeType);
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(BlobTest, ImplTypes);

TYPED_TEST(BlobTest, TestConstructor)
{
	ASSERT_EQ(this->blob->getIdShort(), this->idShort);
	ASSERT_EQ(this->blob->getMimeType(), this->mimeType);
}

TYPED_TEST(BlobTest, TestMimeType)
{
	this->blob->setMimeType("test");
	ASSERT_EQ(this->blob->getMimeType(), "test");
}

TYPED_TEST(BlobTest, TestValueAssignment)
{
	std::vector<char> bytes = { 0, 1, 2, 3, 4 };

	// Test copy assignment
	this->blob->setValue(bytes);
	ASSERT_EQ(this->blob->getValue(), bytes);

	// Test move assignment
	std::vector<char> bytes2 = { 42, 0, (char)255 };
	std::vector<char> bytes2_move = bytes2;

	this->blob->setValue(std::move(bytes2_move));
	ASSERT_EQ(this->blob->getValue(), bytes2);
}

