package com.goldclub.frontend.gc;


import org.testng.annotations.*;

import com.goldclub.frontend.gcpages.*;

public class GCLoginTest extends GcTestCase{

		@BeforeClass
		public void setUp() throws Exception {
			super.setUp(this.getClass().getName());
		}

		@AfterClass
		public void tearDown() throws Exception {
			super.tearDown();
		}

		@Test (timeOut=300000)
	    
	    public void GCLogin() throws Exception {
			// Open home page
			GCAccountPage gcAccountPage = new GCAccountPage(se);
			
			// Login
			gcAccountPage.login("testtorre@yopmail.com");
	        
	        // Close browser
	        gcAccountPage.close();
	        System.out.println("TEst");
		}

	}
