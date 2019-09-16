#ifndef _TESTS_SUPPORT_MACROS_H
#define _TESTS_SUPPORT_MACROS_H

#define ASSERT_ANY_EQ(a,t) 	ASSERT_TRUE(a.template InstanceOf<decltype(t)>());		ASSERT_EQ(t, a.template Get<decltype(t)&>());


#endif /* _TESTS_SUPPORT_MACROS_H */

