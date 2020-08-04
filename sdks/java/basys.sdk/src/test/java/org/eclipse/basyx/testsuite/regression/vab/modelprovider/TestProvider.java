package org.eclipse.basyx.testsuite.regression.vab.modelprovider;

import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.junit.Test;

/**
 * Abstract test suite for testing CRUD-operations for different types of model providers.
 * The concrete test cases implement concrete VABConnectionManagers that are tested
 * 
 * @author espen
 *
 */
public abstract class TestProvider {
	protected abstract VABConnectionManager getConnectionManager();

	@Test
	public void testMapCreateDelete() throws Exception {
		MapCreateDelete.test(getConnectionManager());
	}

	@Test
	public void testMapRead() {
		MapRead.test(getConnectionManager());
	}

	@Test
	public void testMapUpdate() {
		MapUpdate.test(getConnectionManager());
	}

	@Test
	public void testMapInvoke() {
		MapInvoke.test(getConnectionManager());
	}

	@Test
	public void testCollectionCreateDelete() throws Exception {
		TestCollectionProperty.testCreateDelete(getConnectionManager());
	}

	@Test
	public void testCollectionRead() {
		TestCollectionProperty.testRead(getConnectionManager());
	}

	@Test
	public void testCollectionUpdate() {
		TestCollectionProperty.testUpdate(getConnectionManager());
	}

	@Test
	public void testObjectTransfer() {
		MapUpdate.testPushAll(getConnectionManager());
	}

	@Test
	public void testHandlingException() {
		Exceptions.testHandlingException(getConnectionManager());
	}
}
