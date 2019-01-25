package org.eclipse.basyx.testsuite.regression.vab.gateway;

public class Test {
	public static void main(String[] args) {
		String path = "tcp://12.2.2.2:1010//http://test.com/SimpleElement";
		String[] splitted = path.split("//");
		for (int i = 0; i < splitted.length; i++) {
			System.out.println(splitted[i]);
		}
	}
}
