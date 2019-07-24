package org.eclipse.basyx.regression.support.processengine.executor;

/**
 * Expected execution sequences specified in arrays of the task-ids
 * 
 * @author zhangzai
 *
 */
public class ExecutionSequence {
	public static String[] expectedSequence1 = new String[]{
			"t1",
			"t3",
			"t4",
			"t5",
			"t6",
			"t7",
			"end"};
	public static String[] expectedSequence2 = new String[]{
			"t2",
			"t1",
			"t3",
			"t4",
			"t5",
			"t6",
			"t7",
			"end"};

}
