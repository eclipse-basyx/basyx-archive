#include <gtest/gtest.h>

#include <BaSyx/submodel/simple/submodelelement/file/File.h>
#include <BaSyx/submodel/map_v2/submodelelement/file/File.h>

using namespace basyx::submodel;

// Implementations to run tests for
using ImplTypes = ::testing::Types<
	simple::File,
	map::File
>;

template<class Impl>
class FileTest :public ::testing::Test {
protected:
	using impl_t = Impl;
protected:
	const std::string idShort = "id_test";
	const std::string mimeType = "bytes";

	std::unique_ptr<api::IFile> file;
protected:
	void SetUp() override
	{
		this->file = util::make_unique<Impl>(idShort, mimeType);
	}

	void TearDown() override
	{
	}
};

TYPED_TEST_CASE(FileTest, ImplTypes);

TYPED_TEST(FileTest, TestConstructor)
{
	ASSERT_EQ(this->file->getIdShort(), this->idShort);
	ASSERT_EQ(this->file->getMimeType(), this->mimeType);
}

TYPED_TEST(FileTest, TestMimeType)
{
	this->file->setMimeType("test");
	ASSERT_EQ(this->file->getMimeType(), "test");
}

TYPED_TEST(FileTest, TestPath)
{
	this->file->setPath("url://test");
	ASSERT_EQ(this->file->getPath(), "url://test");
}

