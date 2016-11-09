package com.goldclub.frontend.gc;

import com.goldclub.common.GCCommonTestCase;


public class GcTestCase extends GCCommonTestCase{
		public static String siteToTest;
		
		/**
		 *
		 * Set up machine, browser and default timeouts
		 * @param className			Test class name
		 * @throws Exception
		 */
		public void setUp(String className) throws Exception {		
			super.setUp(className);
			siteToTest = Twitter;
			//set special global automation cookie for PERS-2647 & other possible future uses
			se.open(siteToTest);
			//set up for all A/B test flows
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
			super.setUp(className, elementTimeout, pageTimeout, testTimeout);
			siteToTest = Twitter;
			//set special global automation cookie for PERS-2647 & other possible future uses
			se.open(siteToTest);
			//set up for all A/B test flows
		}

		/**
		 * Set up machine, browser and timeouts
		 * @param className			Test class name
		 * @param elementTimeout	Find element timeout
		 * @param pageTimeout		Page load timeout
		 * @param testTimeout		Test run timeout
		 * @throws Exception
		 */
		public void setUpCreative(String className, int elementTimeout, int pageTimeout, int testTimeout) throws Exception {
			super.setUp(className, elementTimeout, pageTimeout, testTimeout);
			siteToTest = Twitter;
			//set special global automation cookie for PERS-2647 & other possible future uses
			se.open(siteToTest);
		}
}
		