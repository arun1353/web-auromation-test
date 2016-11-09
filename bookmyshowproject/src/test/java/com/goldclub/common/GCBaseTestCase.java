package com.goldclub.common;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeClass;

public class GCBaseTestCase {
	// Test environment ENV set in defaultTest.properties
		public static String ENV;
		
	    // URL scheme with HTTP set in defaultTest.properties
		public static String HTTP;
		public static String HTTPS = "https";

		// Websites dependent on ENV
		public static String Twitter = "https://twitter.com/";
		
		// BROWSER, BROWSER_VERSION, MACHINE set in defaultTest.properties
		protected static String BROWSER;
		protected static String BROWSER_VERSION;
		protected static String MACHINE;
		
		// Selenium, browser, machine objects, assigned in setUp() based on BROWSER and MACHINE
		protected static GCSelenium se = new GCSelenium();
		protected static GCBrowser browser;
		protected static GCMachine machine;
		
		// App path and name
		public static String APP_PATH;
		public static String APP_NAME;
		
		
		// Multi-thread count
		public static int THREAD_COUNT;
		
		// Internal Servers
		public static String TP_GLOBAL_NET = "tp-global.net";
		public static String TP_STORE_SERVER;
		public static String TP_GLOBAL_SERVER;
		public static String SFLY_UPLOAD_SERVICE;
		
		// TP cloneOrder API credentials set in defaultTest.properties
		public static String TP_SECRET;
		public static String PERFORMANCE_ITERATION;
		
		// Timeout settings
		// override timeouts across test cases globally
		public static int overrideElementTime = 60;
		public static int overridePageTime = 120;
		public static int overrideTestTime = 600;	
		// default timeouts across test cases when not overridden
		public static int defaultElementTime = 10;
		public static int defaultPageTime = 60;
		public static int defaultTestTime = 240;
		
		@BeforeClass
		public void applyDefaultProps() throws IOException {
			applyProps(loadDefaultProps());		
		}
		
		public void setTimeoutsForTestEnvironments(String env){
			if (env == "trunka." || env == "trunkm." || env == "weeklya." || env == "weeklym." ){
				
				overrideElementTime = 60;
				overridePageTime = 240;
				overrideTestTime = 1800;
				
				
				defaultElementTime = 40;
				defaultPageTime = 240;
				defaultTestTime = 1200;
			}	
		}
		
		/**
		 * Set up machine, browser and default timeouts
		 * @param className			Test class name
		 * @throws Exception
		 */
		public void setUp(String className) throws Exception {	
			setTimeoutsForTestEnvironments(ENV);		
			setUp(className, defaultElementTime, defaultPageTime, defaultTestTime);
		}
		
		/**
		 * Set up machine, browser and timeouts
		 * @param className			Test class name
		 * @param elementTimeout	Find element timeout
		 * @param pageTimeout		Page load timeout
		 * @param testTimeout		Test run timeout
		 * @throws Exception
		 */
		public void setUp(String className, int elementTimeout, int pageTimeout, int testTimeout) throws Exception {
			// Browser
			GCBrowserName browserName;
			if (BROWSER == null || BROWSER_VERSION == null) {
				throw new IllegalArgumentException("BROWSER or BROWSER_VERSION null");
			} else if (BROWSER.equals("")) {
				browserName = GCBrowserName.FF;
			} else if (BROWSER.equals("firefoxagentipad")) {
				browserName = GCBrowserName.TFF;
			} else if (BROWSER.equals("firefox")) {
				browserName = GCBrowserName.FF;
			} else if (BROWSER.equals("iexplore")) {
				browserName = GCBrowserName.IE;
			} else if (BROWSER.equals("safari")) {
				browserName = GCBrowserName.SF;
			} else if (BROWSER.equals("mobilesafari")) {
				browserName = GCBrowserName.MSF;
			} else if (BROWSER.equals("tabletsafari")) {
				browserName = GCBrowserName.TSF;
			} else if (BROWSER.equals("chrome")) {
				browserName = GCBrowserName.CH;
			} else if (BROWSER.equals("chromeagentiphone")) {
				browserName = GCBrowserName.MCH;
			} else if (BROWSER.equals("chromeagentipad")) {
				browserName = GCBrowserName.TCH;
			} else if (BROWSER.equals("firefoxagentiphone")) {
				browserName = GCBrowserName.MFF;
			} else if (BROWSER.equals("iphoneapp")) {
				browserName = GCBrowserName.IPHONE_APP;
			} else if (BROWSER.equals("ipadapp")) {
				browserName = GCBrowserName.IPAD_APP;
			} else {
				throw new Exception("Unknown BROWSER " + BROWSER);
			}
			if (BROWSER_VERSION.equals("")) browser = new GCBrowser(browserName);
			else browser = new GCBrowser(browserName, BROWSER_VERSION);
			
			// Machine
			if (MACHINE == null) {
				throw new Exception("MACHINE null");
			} else if (MACHINE.equals("")) {
				machine = new GCMachine();
			} else if (MACHINE.equals(GCMachine.LOCALHOST)) {
				machine = new GCMachine(GCMachine.LOCALHOST);
			} else if (MACHINE.equals(GCMachine.SAUCELABS)) {
				machine = new GCMachine(GCMachine.SAUCELABS);
			} else {
				machine = new GCMachine(MACHINE);
			}
					
			// Call SflySelenium.setUp with machine, browser and timeouts
			se.setUp(machine, browser, className, elementTimeout, pageTimeout, testTimeout);
		}

		
		/**
		 * Load default properties
		 * @return Properties
		 * @throws IOException
		 */
		private Properties loadDefaultProps() throws IOException {
			Properties testProps = new Properties();
			testProps = GCPropertyLoader.loadDefaultPropsFile();
			testProps.putAll(System.getProperties());
			return testProps;
		}
		
		
		/**
		 * Apply properties
		 * @param testProps
		 */
		private void applyProps(Properties testProps) throws IOException {
			// Test environment
			ENV = testProps.getProperty("ENV");
			// Browser and machine
			BROWSER = testProps.getProperty("BROWSER");
			BROWSER_VERSION = testProps.getProperty("BROWSER_VERSION");
			MACHINE = testProps.getProperty("MACHINE");
//			System.out.println("ENV is" +ENV);
//			if (NYQA1.equals(ENV)) {
//				NYQA1 = "https://tc-ny-qa-web-01.theaterclub.com/ny";
//			} else if (NYQA2.equals(ENV)) {
//				NYQA2 = "https://tc-ny-qa-web-02.theaterclub.com/ny";
//			} else if (BostonQA1.equals(ENV)) {
//				BostonQA1 = "https://master-tc-bos-qa-web-01.theaterclub.com/";
//			} else if (LONQA1.equals(ENV)) {
//				LONQA1 = "https://tc-lon-qa-web-01.theatreclub.co.uk/";
//			} else if (LONQA2.equals(ENV)) {
//				LONQA2 = "https://tc-lon-qa-web-02.theatreclub.co.uk/";
//			}
			
		}
		
		/**
		 * Wrapper to call SflySelenium.tearDown()
		 * @throws Exception
		 */
		public void tearDown() throws Exception {
			se.tearDown();
		}
		
}

