package org.wooliesX.core;

public class FactoryProvider {
	
	private FactoryProvider() {
		
	}
	
    public static IAbstractFactory<?> getFactory(String serviceType){
         
        return serviceType.equalsIgnoreCase("ui") ? UIPageFactory.getFactory() : 
        	serviceType.equalsIgnoreCase("api") ? APIServiceFactory.getFactory() : null;
    }
}