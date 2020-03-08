package org.wooliesX.api.steps;

import org.wooliesX.api.BaseAPI;
import org.wooliesX.core.FactoryProvider;
import org.wooliesX.core.IAbstractFactory;

public class ApiSteps {

	protected IAbstractFactory<BaseAPI> factory;

	@SuppressWarnings("unchecked")
	public ApiSteps() {

		this.factory = (IAbstractFactory<BaseAPI>) FactoryProvider.getFactory("api");
	}
}
