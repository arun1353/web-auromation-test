package com.goldclub.frontend.gcpages;

import org.openqa.selenium.Keys;

import com.goldclub.common.*;

public class GCAccountPage extends GCSelenium{
	public static final String LOGIN = "css=li.nav-item:first-child>a";
	public static final String LOGIN_EMAIL = "css=input#edit-name";
	public static final String LOGIN_PASSWORD = "css=input#edit-pass";
    public GCAccountPage(GCSelenium se) {
    	setGCSelenium(se);
	}

	/**
     * Login with default password, and loginSucess defaults to true
     * @param email
     * @throws Exception
     */
    public void login(String email)  throws Exception {
    	login(email, "Second@123", true);	
    }
    
    /**
     * Login with email and password
     * @param email
     * @param password
     * @param loginSuccess (true/false based on whether the login will be successful or not)
     * @throws Exception
     */
    public void login(String email, String password, boolean loginSuccess)  throws Exception {
    	sleep(10);
    	click(LOGIN, 5);
    	sleep(10);	
    	type(LOGIN_EMAIL, email, 5);
    	type(LOGIN_PASSWORD, password);
    	
    	if((browser.browserName) == GCBrowserName.IE){
    		sendSpecialKey(LOGIN_PASSWORD, Keys.ENTER);  
    		sleep(10); 
    	}
//    	} else {
//			executeJavascript("$('signIn').click();"); //invoke JS to click button because the actual element is not "visible" to Selenium, so will fail if just clicked
//		}

    	sleep(5);
	}

}
