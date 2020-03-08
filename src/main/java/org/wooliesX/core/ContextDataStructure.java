package org.wooliesX.core;

import java.util.HashMap;

@SuppressWarnings("serial")
public class ContextDataStructure extends HashMap<String, Object>{

	private static ContextDataStructure dataStructure = null;
	private ContextDataStructure() {
		System.out.println("Creating a new ContextDataStructure object.........");
	}
	
	public static ContextDataStructure getDataStructure() {
		
		return dataStructure = dataStructure == null ? new ContextDataStructure() : dataStructure;
	}
}
