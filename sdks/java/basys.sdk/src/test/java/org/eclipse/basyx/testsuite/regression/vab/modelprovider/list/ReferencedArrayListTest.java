package org.eclipse.basyx.testsuite.regression.vab.modelprovider.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.eclipse.basyx.vab.modelprovider.list.InvalidListReferenceException;
import org.eclipse.basyx.vab.modelprovider.list.ReferencedArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class that tests the ReferencedArrayList class
 * 
 * @author espen
 *
 */
public class ReferencedArrayListTest {
	ReferencedArrayList<Object> objList;
	ReferencedArrayList<Object> emptyList;
	LinkedList<Object> addList;
	Object object1;
	Object object2;
	Random random;

	@Before
	public void build() {
		emptyList = new ReferencedArrayList<Object>();

		object1 = new Object();
		object2 = new Object();

		addList = new LinkedList<>();
		addList.add(object1); // 0
		addList.add(5); // 1
		addList.add("Test"); // 2
		addList.add(2.45f); // 3
		addList.add(null); // 4
		addList.add("Test"); // 5
		addList.add(object1); // 6
		addList.add(object2); // 7
		objList = new ReferencedArrayList<Object>(addList);

		random = new Random(3473);
	}

	/**
	 * Tests if the list is instantiated with the correct size and elements
	 */
	@Test
	public void instantiateTest() {
		assertSame(0, emptyList.size());
		assertSame(8, objList.size());
		assertEquals("Test", objList.get(2));
		assertEquals("Test", objList.get(5));
		assertEquals(null, objList.get(4));
		assertNotEquals(null, objList.get(2));
	}

	/**
	 * Tests, if elements can be accessed using a static reference
	 */
	@Test
	public void getByReferenceTest() {
		try {
			assertSame(null, objList.getByReference(-1));
			fail();
		} catch (InvalidListReferenceException e) {
		}
		try {
			assertSame(null, objList.getByReference(100));
			fail();
		} catch (InvalidListReferenceException e) {
		}

		assertSame(null, objList.getByReference(4));
		assertSame(object1, objList.get(0));
		assertSame(object1, objList.getByReference(0));
		assertSame(object1, objList.get(6));
		assertSame(object1, objList.getByReference(6));
		assertSame(object2, objList.get(7));
		assertSame(object2, objList.getByReference(7));
	}

	/**
	 * Tests, if the references point to the correct elements after the list was
	 * sorted
	 */
	@Test
	public void sortTest() {
		objList.sort((o1, o2) -> {
			if (random.nextBoolean()) {
				return -1;
			} else {
				return 1;
			}
		});

		assertNotSame(object1, objList.get(0));
		assertSame(object1, objList.getByReference(0));
		assertNotSame(object1, objList.get(6));
		assertSame(object1, objList.getByReference(6));
		assertNotSame(object2, objList.get(7));
		assertSame(object2, objList.getByReference(7));
	}

	/**
	 * Tests, if the referenced index is updated after the list was modified
	 */
	@Test
	public void getReferencedIndexTest() {
		for (int i = 0; i < 8; i++) {
			assertSame(i, objList.getReferencedIndex(i));
		}

		objList.sort((o1, o2) -> {
			if (random.nextBoolean()) {
				return -1;
			} else {
				return 1;
			}
		});

		for (int i = 0; i < 8; i++) {
			assertNotSame(i, objList.getReferencedIndex(i));
		}
	}

	/**
	 * Tests, if the returned reference list is correct for initialized lists
	 */
	@Test
	public void getReferencesTest() {
		assertSame(0, emptyList.getReferences().size());
		assertSame(8, objList.getReferences().size());
		assertSame(0, objList.getReferences().get(0));
		assertSame(5, objList.getReferences().get(5));
	}

	/**
	 * Tests, if the references stay sorted according to the list entries, when modifying the list
	 */
	@Test
	public void getReferencesSortedTest() {
		objList.add(0, "First");

		assertSame(9, objList.getReferences().size());
		assertSame(8, objList.getReferences().get(0));
		assertSame(0, objList.getReferences().get(1));
	}

	/**
	 * Tests, if a reference is correct after inserting objects at a specific index
	 */
	@Test
	public void addIndexTest() {
		emptyList.add(0, object1);
		emptyList.add(0, object2);
		assertSame(2, emptyList.size());
		assertSame(object2, emptyList.get(0));
		assertSame(object1, emptyList.getByReference(0));
		assertSame(object2, emptyList.getByReference(1));
	}

	/**
	 * Tests, if a reference is correct after inserting objects
	 */
	@Test
	public void addElementTest() {
		emptyList.add(object1);
		emptyList.add(object2);
		assertSame(2, emptyList.size());
		assertSame(object1, emptyList.get(0));
		assertSame(object1, emptyList.getByReference(0));
		assertSame(object2, emptyList.getByReference(1));
	}

	/**
	 * Tests, if the list is valid after adding lists of objects
	 */
	@Test
	public void addAllTest() {
		assertSame(true, objList.addAll(addList));
		assertSame(16, objList.size());
		assertSame(16, objList.getReferences().size());

		assertSame(false, objList.addAll(emptyList));
		assertSame(16, objList.size());
		assertSame(16, objList.getReferences().size());

		assertEquals(5, objList.get(1));

		assertSame(object1, objList.getByReference(6));
		assertSame(object1, objList.getByReference(14));
	}

	/**
	 * Tests, if the list is valid after inserting lists of objects at a specific
	 * index
	 */
	@Test
	public void addAllIndexTest() {
		assertSame(true, objList.addAll(1, addList));
		assertSame(16, objList.size());
		assertSame(16, objList.getReferences().size());

		assertSame(false, objList.addAll(1, emptyList));
		assertSame(16, objList.size());
		assertSame(16, objList.getReferences().size());

		assertSame(object1, objList.get(1));
		assertSame(object2, objList.get(15));
		assertSame(object1, objList.getByReference(6));
		assertSame(object1, objList.getByReference(14));
	}

	/**
	 * Tests, if the list and its references are cleared after the list was cleared
	 */
	@Test
	public void clearTest() {
		objList.clear();
		assertSame(0, objList.size());
		assertSame(0, objList.getReferences().size());
	}

	/**
	 * Tests, if the list can be cloned correctly
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void cloneTest() {
		ReferencedArrayList<Object> cloned = (ReferencedArrayList<Object>) objList.clone();
		cloned.clear();
		assertSame(0, cloned.size());
		assertSame(0, cloned.getReferences().size());
		assertSame(8, objList.size());
		assertSame(8, objList.getReferences().size());
	}

	/**
	 * Tests, if the list references stay valid after removing objects at specific
	 * indices
	 */
	@Test
	public void removeIndexTest() {
		objList.remove(0);
		assertSame(7, objList.size());
		assertSame(7, objList.getReferences().size());
		assertSame(null, objList.getReferencedIndex(0));
		for (int i = 1; i < 8; i++) {
			assertSame(i - 1, objList.getReferencedIndex(i));
		}
		objList.remove(5);
		assertSame(6, objList.size());
		assertSame(6, objList.getReferences().size());
		assertSame(null, objList.getReferencedIndex(0));
		assertSame(null, objList.getReferencedIndex(6));
		for (int i = 1; i < 6; i++) {
			assertSame(i - 1, objList.getReferencedIndex(i));
		}
		assertSame(5, objList.getReferencedIndex(7));

		objList.remove(0);
		assertSame(5, objList.size());
		assertSame(5, objList.getReferences().size());
		assertSame(null, objList.getReferencedIndex(0));
		assertSame(null, objList.getReferencedIndex(1));
		assertSame(null, objList.getReferencedIndex(6));
		for (int i = 2; i < 6; i++) {
			assertSame(i - 2, objList.getReferencedIndex(i));
		}
		assertSame(4, objList.getReferencedIndex(7));
	}

	/**
	 * Tests, if the list is valid after removing objects from the list
	 */
	@Test
	public void removeObjectTest() {
		objList.remove(object1);
		assertSame(7, objList.size());
		assertSame(7, objList.getReferences().size());
		objList.remove(object1);
		assertSame(6, objList.size());
		assertSame(6, objList.getReferences().size());
		objList.remove(object2);
		assertSame(5, objList.size());
		assertSame(5, objList.getReferences().size());
	}

	/**
	 * Tests, if a list of objects can be removed correctly
	 */
	@Test
	public void removeAllTest() {
		List<Object> toRemove = new ArrayList<>();
		toRemove.add(object1);
		toRemove.add(object1);
		toRemove.add("NotContained");
		toRemove.add("Test");
		toRemove.add(object2);
		objList.removeAll(toRemove);
		assertSame(4, objList.size());
		assertSame(4, objList.getReferences().size());
	}

	/**
	 * Tests, if the list is valid after conditional removals
	 */
	@Test
	public void removeIfTest() {
		objList.removeIf((o) -> {
			return o != null && (o.equals("Test") || o == object1);
		});
		assertSame(4, objList.size());
		assertSame(4, objList.getReferences().size());
		assertSame(object2, objList.getByReference(7));
		assertFalse(objList.contains(object1));
	}

	/**
	 * Tests, if the list is valid after replacing elements using replaceAll
	 */
	@Test
	public void replaceAllTest() {
		objList.replaceAll((o) -> {
			if (o == object1) {
				return object2;
			} else {
				return o;
			}
		});
		assertSame(8, objList.size());
		assertSame(6, objList.getReferences().size());
		assertSame(object2, objList.getByReference(7));
		assertFalse(objList.contains(object1));
	}


	/**
	 * Tests, if the list is valid after removing all elements except the ones in
	 * the retain list
	 */
	@Test
	public void retainAllTest() {
		List<Object> toRetain = new ArrayList<>();
		toRetain.add(null);
		toRetain.add(object1);
		objList.retainAll(toRetain);
		assertSame(3, objList.size());
		assertSame(3, objList.getReferences().size());
		assertSame(object1, objList.getByReference(6));
		assertFalse(objList.contains(object2));
	}

	/**
	 * Tests, if the list is valid after replacing an element at a specific index
	 * The reference that refers to the specific index should not change. Thus, it
	 * should still point to that index and to the new element as a consequence.
	 */
	@Test
	public void setIndexTest() {
		objList.set(0, object1);
		assertSame(8, objList.size());
		assertSame(8, objList.getReferences().size());
		assertEquals(5, objList.getByReference(1));
		objList.set(1, object1);
		assertSame(8, objList.size());
		assertSame(8, objList.getReferences().size());
		assertEquals(object1, objList.getByReference(1));
	}
}
