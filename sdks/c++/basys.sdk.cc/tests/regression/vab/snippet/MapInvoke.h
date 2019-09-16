#ifndef _MAPINVOKE_H
#define _MAPINVOKE_H

#include <gtest/gtest.h>

#include "basyx/types.h"

#include "vab/core/IModelProvider.h"

#define ASSERT_ANY_EQ(a,t) 	ASSERT_TRUE(a.template InstanceOf<decltype(t)>());		ASSERT_EQ(t, a.template Get<decltype(t)&>());



namespace basyx {
namespace tests {
namespace regression {
namespace vab {
namespace snippet {


class MapInvoke {
public:

    static void test(basyx::vab::core::IModelProvider * modelProvider) {
		auto complex = modelProvider->invokeOperation("operations/complex", 12, 34);
		ASSERT_ANY_EQ(complex, 46);

		// Invoke unsupported functional interface
		auto supplier = modelProvider->invokeOperation("operations/supplier");
//		assertNull(supplier);

		// Invoke non-existing operation
		auto nonExisting = modelProvider->invokeOperation("operations/unknown");
		ASSERT_TRUE(nonExisting.IsNull());

		// Invoke invalid operation -> not a function, but a primitive data type
		auto invalid = modelProvider->invokeOperation("operations/invalid");
		ASSERT_TRUE(invalid.IsNull());

		/*
		// Invoke operations that throw Exceptions
		try {
			modelProvider->invokeOperation("operations/serverException");
			fail();
		}
		catch (ServerException e) {
			// exception type not implemented, yet
			// assertEquals(e.getType(), "testExceptionType");
		}

		try {
			modelProvider->invokeOperation("operations/nullException");
			fail();
		}
		catch (ServerException e) {
			// exception type not implemented, yet
			// assertEquals(e.getType(), "java.lang.NullPointerException");
		}*/

		// Empty paths - should execute, but has no effect
		auto empty = modelProvider->invokeOperation("", "" );
		ASSERT_TRUE(empty.IsNull());
	}
};

}
}
}
}
}


#endif /* _MAPINVOKE_H */
