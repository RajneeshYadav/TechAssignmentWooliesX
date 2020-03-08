package org.wooliesX.core;

import org.wooliesX.pages.CartSummaryPage;
import org.wooliesX.pages.HomePage;
import org.wooliesX.pages.LoginPage;
import org.wooliesX.pages.Page;

public class UIPageFactory implements IAbstractFactory<Page>{
	
	private Page page;

	private HomePage homePage;
	private LoginPage loginPage;
	private CartSummaryPage cartSummaryPage;
	private static UIPageFactory uiPageFactory = null;
	
	private UIPageFactory() {
		
	}
	
	@Override
	public Page getServiceUtil(String pageName)	{
		
		if(pageName.equalsIgnoreCase("homepage")) {
			
			return homePage == null ? new HomePage() : homePage;
		}
		else if(pageName.equalsIgnoreCase("loginpage")) {
			
			return loginPage == null ? new LoginPage() : loginPage;
		}
		else if(pageName.equalsIgnoreCase("cartsummarypage")) {
			
			return cartSummaryPage == null ? new CartSummaryPage() : cartSummaryPage;
		}
		else {
			
			page = new Page();
			return page;
		}
	}

	public static UIPageFactory getFactory() {
		
		return uiPageFactory == null ? new UIPageFactory() : uiPageFactory;
	}
}
