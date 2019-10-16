package org.eclipse.basyx.examples.snippets.aas.active;

import org.eclipse.basyx.examples.snippets.aas.active.tasks.AverageTask;
import org.eclipse.basyx.examples.snippets.aas.active.tasks.IncrementTask;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.tools.aas.active.ActiveModel;
import org.eclipse.basyx.tools.aas.active.VABModelTaskGroup;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;
import org.junit.Ignore;
import org.junit.Test;

public class RunComposedActiveModelSnippet {
	/**
	 * Test active model computing and printing an average temperature property
	 */
	@Test
	@Ignore
	public void snippet() throws Exception {
		// Create the model provider for the active model
		IModelProvider modelProvider = new SubModelProvider();
		modelProvider.createValue("count", 0);
		modelProvider.createValue("temperature", VABLambdaProviderHelper.createSimple(() -> {
			return 30d + (Math.random() * 10d - 5d);
		}, null));

		// Create an active model based on the previously created IModelProvider
		// => Could be any model provider here, also a connected model provider or one containing a submodel
		ActiveModel activeModel = new ActiveModel(modelProvider);

		// Add a task group with multiple tasks to the active model
		// The groups' update interval is set to 20x per second
		// Then the group is started.
		activeModel.createTaskGroup().addTask(new IncrementTask("/count"))
				.addTask(new AverageTask(0.01f, "/temperature", "/average"))
				.setUpdateInterval(50)
				.start();

		// Runs a task group with a single task (1x per second)
		VABModelTaskGroup printerGroup = activeModel.runTask(1000, model -> {
			System.out.println("Current count: " + model.getModelPropertyValue("/count"));
			System.out.println("Current average: " + model.getModelPropertyValue("/average"));
		});

		// Adds an additional task to the existing task group
		printerGroup.addTask(model -> {
			System.out.println("Printed count + average");
		});

		// Wait for 5 seconds and then stop the printerGroup.
		// The other task group is still scheduled
		Thread.sleep(5000);
		printerGroup.stop();

		// Wait again and then stop all groups. Then start only the printerGroup, but with a faster update interval
		Thread.sleep(5000);
		activeModel.stopAll();
		printerGroup.setUpdateInterval(100).start();

		// Finally stop and clear the model
		Thread.sleep(1000);
		activeModel.clear();

	}
}
