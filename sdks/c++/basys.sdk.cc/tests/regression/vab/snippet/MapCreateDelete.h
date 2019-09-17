#ifndef _MAPCREATEDELETE_H
#define _MAPCREATEDELETE_H

#include <gtest/gtest.h>

#include "basyx/types.h"

#include "vab/core/IModelProvider.h"

#include "macros.h"


namespace basyx {
namespace tests {
namespace regression {
namespace vab {
namespace snippet {

class MapCreateDelete {
public:
	static void test(basyx::vab::core::IModelProvider * modelProvider)
	{
		testCreateElements(modelProvider);
		testDeleteElements(modelProvider);
	}
private:
	static void testCreateElements(basyx::vab::core::IModelProvider * modelProvider)
	{
		modelProvider->createValue("inRoot", 1.2);
		basyx::any toTest = modelProvider->getModelPropertyValue("inRoot");
		ASSERT_ANY_EQ(toTest, 1.2);

		// Create element in Map (with new key contained in the path)
		modelProvider->createValue("/structure/map/inMap", std::string{ "34" });
		toTest = modelProvider->getModelPropertyValue("/structure/map/inMap");
		ASSERT_ANY_EQ(toTest, std::string{ "34" });

		// Create map element
		basyx::objectMap_t newMap;
		newMap.emplace("entryA", 3);
		newMap.emplace("entryB", 4);
		modelProvider->createValue("mapInRoot", newMap);
		toTest = modelProvider->getModelPropertyValue("mapInRoot");

		ASSERT_TRUE(toTest.InstanceOf<basyx::objectMap_t>());
		ASSERT_EQ(toTest.Get<basyx::objectMap_t&>().size(), 2);
		ASSERT_ANY_EQ(toTest.Get<basyx::objectMap_t&>().at("entryA"), 3);

		// Try to overwrite existing element (should be ignored, already exists)
		modelProvider->createValue("inRoot", 0);
		toTest = modelProvider->getModelPropertyValue("inRoot");
		ASSERT_ANY_EQ(toTest, 1.2);

		// Check case-sensitivity
		modelProvider->createValue("inroot", 78);
		toTest = modelProvider->getModelPropertyValue("inRoot");
		ASSERT_ANY_EQ(toTest, 1.2);
		toTest = modelProvider->getModelPropertyValue("inroot");
		ASSERT_ANY_EQ(toTest, 78);

		// Non-existing parent element
		modelProvider->createValue("unkown/x", 5);
		toTest = modelProvider->getModelPropertyValue("unknown/x");
		ASSERT_TRUE(toTest.IsNull());

		// Empty paths - should execute, but has no effect
		modelProvider->createValue("", std::string(""));
		toTest = modelProvider->getModelPropertyValue("");
		ASSERT_FALSE(toTest.IsNull());
		ASSERT_FALSE(toTest.InstanceOf<std::string>());
	}

	static void testDeleteElements(basyx::vab::core::IModelProvider * modelProvider)
	{
		// Delete at Root
		// - by basyx::any - should not work, root is a map
		modelProvider->deleteValue("inRoot", 1.2);
		basyx::any toTest = modelProvider->getModelPropertyValue("inRoot");
		ASSERT_ANY_EQ(toTest, 1.2);
		// - by index
		modelProvider->deleteValue("inRoot");
		toTest = modelProvider->getModelPropertyValue("inRoot");
		ASSERT_TRUE(toTest.IsNull());

		// Check case-sensitivity
		toTest = modelProvider->getModelPropertyValue("inroot");
		ASSERT_ANY_EQ(toTest, 78);
		modelProvider->deleteValue("inroot");
		toTest = modelProvider->getModelPropertyValue("inroot");
		ASSERT_TRUE(toTest.IsNull());

		// Delete at Map
		// - by basyx::any - should not work in maps, because basyx::any refers to a contained basyx::any, not the index
		modelProvider->deleteValue("/structure/map/", "inMap");
		toTest = modelProvider->getModelPropertyValue("/structure/map/inMap");
		ASSERT_ANY_EQ(toTest, std::string("34"));
		// - by index
		modelProvider->deleteValue("/structure/map/inMap");
		toTest = modelProvider->getModelPropertyValue("/structure/map");
		ASSERT_TRUE(toTest.InstanceOf<basyx::objectMap_t>());
		ASSERT_EQ(toTest.Get<basyx::objectMap_t&>().size(), 0);

		// Delete remaining complete Map
		modelProvider->deleteValue("mapInRoot");
		toTest = modelProvider->getModelPropertyValue("mapInRoot");
		ASSERT_TRUE(toTest.IsNull());

		// Empty paths - should not delete anything
		modelProvider->deleteValue("", "");
		toTest = modelProvider->getModelPropertyValue("/primitives/integer");
		ASSERT_ANY_EQ(toTest, 123);
	}

};

}
}
}
}
}

/*
// Create property directly in root element
modelProvider->createValue("inRoot", 1.2);
basyx::any toTest = modelProvider->getModelPropertyValue("inRoot");
assertEquals(1.2, toTest);

// Create element in Map (with new key contained in the path)
modelProvider->createValue("/structure/map/inMap", "34");
toTest = modelProvider->getModelPropertyValue("/structure/map/inMap");
assertEquals("34", toTest);

// Create map element
HashMap<String, basyx::any> newMap = new HashMap<>();
newMap.put("entryA", 3);
newMap.put("entryB", 4);
modelProvider->createValue("mapInRoot", newMap);
toTest = modelProvider->getModelPropertyValue("mapInRoot");
assertTrue(toTest instanceof Map<?, ?>);
assertEquals(2, ((Map<String, basyx::any>)toTest).size());
assertEquals(3, ((Map<String, basyx::any>)toTest).get("entryA"));

// Try to overwrite existing element (should be ignored, already exists)
modelProvider->createValue("inRoot", 0);
toTest = modelProvider->getModelPropertyValue("inRoot");
assertEquals(1.2, toTest);

// Check case-sensitivity
modelProvider->createValue("inroot", 78);
toTest = modelProvider->getModelPropertyValue("inRoot");
assertEquals(1.2, toTest);
toTest = modelProvider->getModelPropertyValue("inroot");
assertEquals(78, toTest);

// Non-existing parent element
modelProvider->createValue("unkown/x", 5);
toTest = modelProvider->getModelPropertyValue("unknown/x");
assertNull(toTest);

// Empty paths - should execute, but has no effect
modelProvider->createValue("", "");
toTest = modelProvider->getModelPropertyValue("");
assertNotEquals("", toTest);

// Null path - should throw exception
try {
modelProvider->createValue(null, "");
fail();
} catch (ServerException e) {
}
}

private
static void testDeleteElements(VABElementProxy connVABElement)
{
// Delete at Root
// - by basyx::any - should not work, root is a map
modelProvider->deleteValue("inRoot", 1.2);
basyx::any toTest = modelProvider->getModelPropertyValue("inRoot");
assertEquals(1.2, toTest);
// - by index
modelProvider->deleteValue("inRoot");
toTest = modelProvider->getModelPropertyValue("inRoot");
assertNull(toTest);

// Check case-sensitivity
toTest = modelProvider->getModelPropertyValue("inroot");
assertEquals(78, toTest);
modelProvider->deleteValue("inroot");
toTest = modelProvider->getModelPropertyValue("inroot");
assertNull(toTest);

// Delete at Map
// - by basyx::any - should not work in maps, because basyx::any refers to a contained basyx::any, not the index
modelProvider->deleteValue("/structure/map/", "inMap");
toTest = modelProvider->getModelPropertyValue("/structure/map/inMap");
assertEquals("34", toTest);
// - by index
modelProvider->deleteValue("/structure/map/inMap");
toTest = modelProvider->getModelPropertyValue("/structure/map");
assertEquals(0, ((Map<?, ?>) toTest).size());

// Delete remaining complete Map
modelProvider->deleteValue("mapInRoot");
toTest = modelProvider->getModelPropertyValue("mapInRoot");
assertNull(toTest);

// Empty paths - should not delete anything
modelProvider->deleteValue("", "");
toTest = modelProvider->getModelPropertyValue("/primitives/integer");
assertEquals(123, toTest);

// Null path - should throw exception
try {
modelProvider->deleteValue(null, "");
fail();
} catch (ServerException e) {
}
try {
modelProvider->deleteValue(null);
fail();
} catch (ServerException e) {
}
}
}
*/
#endif /* _MAPCREATEDELETE_H */
