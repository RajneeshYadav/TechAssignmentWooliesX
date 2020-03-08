package org.wooliesX.steps;

import org.wooliesX.core.FactoryProvider;
import org.wooliesX.core.IAbstractFactory;
import org.wooliesX.pages.Page;

public class Steps {
	
    protected IAbstractFactory<Page> factory;
	
	@SuppressWarnings("unchecked")
	public Steps() {

		this.factory = (IAbstractFactory<Page>)FactoryProvider.getFactory("ui");
	}

}
