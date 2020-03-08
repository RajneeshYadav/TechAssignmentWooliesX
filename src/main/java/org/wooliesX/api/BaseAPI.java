package org.wooliesX.api;

import java.util.Map;

import org.wooliesX.core.ConfigurationManager;

public class BaseAPI {

	protected Map<String, String> appConfig = ConfigurationManager.getConfig();
}
