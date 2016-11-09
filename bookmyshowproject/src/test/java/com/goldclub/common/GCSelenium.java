package com.goldclub.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.internal.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.safari.*;
import org.openqa.selenium.support.ui.*;



public class GCSelenium {
	public GCMachine machine;
	public GCBrowser browser;
	public FirefoxProfile profile;
	public DesiredCapabilities capabilities;
	public WebDriver driver;
	public int elementTimeout, pageTimeout, testTimeout;

	// browser profile settings
	public static final String BROWSER_PROFILE_PATH = "common/browserprofiles/";
	public static final String CHROME_DRIVER_PATH = "common/chromedriver/";

    private static final int HTTP_CONNECT_TIMEOUT_IN_SECONDS_FAIL_FAST = 5;
    private static final int HTTP_RESPONSE_TIMEOUT_IN_SECONDS_FAIL_FAST = 15;
    
 // Locator attributes
 	public static final String LOC_A = "a";
 	public static final String LOC_CLASS = "class";
 	public static final String LOC_HREF = "href";
 	public static final String LOC_ID = "id";
 	public static final String LOC_NTH_CHILD = ":nth-child";
 	public static final String LOC_CHILD = ">";
 	public static final String LOC_SRC = "src";
 	public static final String LOC_IFRAME = "iframe";
 	public static final String LOC_VALUE = "value";
 	public static final String LOC_TITLE = "title";
 	public static final String LOC_STYLE = "style";
 	public static final String LOC_SPAN = "span";
 	public static final String LOC_BACKGROUND_COLOR = "background-color";
 	public static final String LOC_FONT_COLOR = "color";
 	public static final String LOC_FONT_SIZE = "fontSize"; 
 	public static final String LOC_FONT_FAMILY = "fontFamily"; 
 	public static final String LOC_FONT_CASE = "textTransform"; 
 	public static final String LOC_TEXT_DECORATION = "text-decoration";


	
	static String pageLoadStatus = null;
	/**
	 * Set non-static SflySelenium variables in page object constructors
	 * @param se
	 */
	public void setGCSelenium(GCSelenium se) {
    	machine = se.machine;
    	browser = se.browser;
    	profile = se.profile;
    	capabilities = se.capabilities;
    	driver = se.driver;
    	elementTimeout = se.elementTimeout;
    	pageTimeout = se.pageTimeout;
    	testTimeout = se.testTimeout;
	}
	
	/**
	 * Setup machine, browser and timeouts
	 * @param m			SflyMachine.LOCALHOST = "localhost" or SflyMachine.SAUCELABS = "saucelabs" or IP address of remote machine
	 * @param b			Browser
	 * @param className	Class name
	 * @param time		Timeout in seconds
	 * @throws Exception
	 */
	@SuppressWarnings("incomplete-switch")
	public void setUp(GCMachine m, GCBrowser b, String className,
			int elementTime, int pageTime, int testTime) throws Exception {
		machine = m;
		browser = b;

		// Setup WebDriver based on machine and browser
		DesiredCapabilities capabilities;

		// Browser setup options (profile path, driver executable paths, user agent strings) 
		String ffProfilePath = GCCommonTestCase.GENERIC_FILE_PATH + BROWSER_PROFILE_PATH + "FFProfile"; //or use FFProfileWithFirebug
		//String chromeDriverPath = SflyCommonTestCase.GENERIC_FILE_PATH + CHROME_DRIVER_PATH;
		String blockSecurityMixedContent = "security.mixed_content.block_active_content";
		String iPhoneUserAgentString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
		String iPadUserAgentString = "Mozilla/5.0(iPad; U; CPU OS 4_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8F191 Safari/6533.18.5";
		
		// localhost
		if (machine.ip.equals(GCMachine.LOCALHOST)) {
			switch(browser.browserName) {
			case FF:
				//Unless already set in system path environment variable, else set correct path to driver as needed.
//				System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe"); //PC 64-bit
				System.setProperty("webdriver.firefox.marionette", "C:\\Users\\admin\\Desktop\\geckodriver.exe"); //PC 32-bit
				//System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox-bin"); //Mac
//				profile = new FirefoxProfile(new File(ffProfilePath));
//				profile.setAssumeUntrustedCertificateIssuer(false); 
//				profile.setPreference(blockSecurityMixedContent, false);
//				configureFailFastRequestTimeouts(profile);\
				capabilities=DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", true);
				driver = new FirefoxDriver(capabilities);
				break;
			case MFF:
				//Unless already set in system path environment variable, else set correct path to driver as needed.
				//System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"); //PC 64-bit
				//System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe"); //PC 32-bit
				//System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox-bin"); //Mac
				profile = new FirefoxProfile(new File(ffProfilePath));
				profile.setAssumeUntrustedCertificateIssuer(false); 
				profile.setPreference(blockSecurityMixedContent, false);
				profile.setPreference("general.useragent.override", iPhoneUserAgentString);
				configureFailFastRequestTimeouts(profile);
				driver = new FirefoxDriver(profile);
				break;
			case TFF:
				//Unless already set in system path environment variable, else set correct path to driver as needed.
				//System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"); //PC 64-bit
				//System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe"); //PC 32-bit
				//System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox-bin"); //Mac
				profile = new FirefoxProfile(new File(ffProfilePath));
				profile.setAssumeUntrustedCertificateIssuer(false); 
				profile.setPreference(blockSecurityMixedContent, false);
				profile.setPreference("general.useragent.override", iPadUserAgentString);
				configureFailFastRequestTimeouts(profile);
				driver = new FirefoxDriver(profile);
				break;
			case IE:
				//Unless already set in system path environment variable, else set correct path to driver as needed.
				//System.setProperty("webdriver.ie.driver", "../JARS/IEDriverServer.exe"); //we'll typically be using the 32-bit version (IE defaults to 32-bit even on 64-bit systems)
				System.setProperty("webdriver.ie.driver", "E:\\IEDriverServer.exe"); 
				capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);				
				/* start IE w/ clean session, clearing all cache & cookies before test run & separate process instances per IE session				 * 
				 * NOTE: untested as of yet and for Selenium version 2.33+ only
				 * NOTE: for IE 8 and above, also requires the TabProcGrowth registry value in Windows to be set to 0.
				 * based on Selenium project DesiredCapabilities wiki and https://groups.google.com/forum/?fromgroups#!topic/webdriver/4Qpd47qPPTg
				capabilities.setCapability(InternetExplorerDriver.IE_DRIVER_FORCE_CREATE_PROCESS_PROPERTY, 1);
				capabilities.setCapability(InternetExplorerDriver.IE_DRIVER_IE_SWITCHES_PROPERTY, "-private -nomerge");
				*/				
				driver = new InternetExplorerDriver(capabilities);
				break;
			case SF:
				/* start Safari w/ clean session, clearing all cache & cookies before test run
				 * NOTE: use only for local tests or where not running 2+ Safari tests in parallel on same machine,
				 * otherwise may cause issue with clearing session & cookie data in existing tests when new test starts
				capabilities = DesiredCapabilities.safari();
				capabilities.setCapability(SafariDriver.CLEAN_SESSION_CAPABILITY, true);
				driver = new SafariDriver(capabilities);
				*/
				driver = new SafariDriver();
				break;
			case MSF:
				if (machine.port == 4444) machine.port = 4723; //Appium default port is 4723 instead of 4444
				capabilities = new DesiredCapabilities();
				capabilities.setCapability("deviceName", "iPhone 5");
				capabilities.setCapability("platformName", "iOS");
				capabilities.setCapability("platformVersion", browser.browserVersion);
				capabilities.setCapability("browserName", "Safari");
				driver = new RemoteWebDriver(new URL("http://" + machine.ip + ":" + machine.port + "/wd/hub" ), capabilities);
				break;
			case TSF:
				if (machine.port == 4444) machine.port = 4723; //Appium default port is 4723 instead of 4444
				capabilities = new DesiredCapabilities();
				capabilities.setCapability("deviceName", "iPad Retina");
				capabilities.setCapability("deviceOrientation", "landscape");
				capabilities.setCapability("platformName", "iOS");
				capabilities.setCapability("platformVersion", browser.browserVersion);
				capabilities.setCapability("browserName", "Safari");
				driver = new RemoteWebDriver(new URL("http://" + machine.ip + ":" + machine.port + "/wd/hub" ), capabilities);
				break;
			case MCH:
				//Unless already set in system path environment variable, else set correct path to driver as needed.
				//System.setProperty("webdriver.chrome.driver", chromeDriverPath + "chromedriver.exe"); //PC
				//System.setProperty("webdriver.chrome.driver", chromeDriverPath + "chromedriver-mac"); //MAC 
				ChromeOptions iPhoneOption = new ChromeOptions();
				iPhoneOption.addArguments("chrome:switches", "--user-agent="+iPhoneUserAgentString);
                driver = new ChromeDriver(iPhoneOption);
				break;
			case TCH:
				//Unless already set in system path environment variable, else set correct path to driver as needed.
				//System.setProperty("webdriver.chrome.driver", chromeDriverPath + "chromedriver.exe"); //PC
				//System.setProperty("webdriver.chrome.driver", chromeDriverPath + "chromedriver-mac"); //MAC
				ChromeOptions iPadOption = new ChromeOptions();
				iPadOption.addArguments("chrome:switches", "--user-agent="+iPadUserAgentString);				
				driver = new ChromeDriver(iPadOption);
				break;
			case CH:
				//Unless already set in system path environment variable, else set correct path to driver as needed.
				System.setProperty("webdriver.chrome.driver", "C:\\" + "chromedriver.exe"); //PC
				//System.setProperty("webdriver.chrome.driver", chromeDriverPath + "chromedriver-mac"); //MAC
				driver = new ChromeDriver();
				break;
			}
			// Remote machine
		} else {
			switch(browser.browserName) {
			default:				
			case FF:				
				profile = new FirefoxProfile(new File(ffProfilePath));
				profile.setPreference(blockSecurityMixedContent, false);
				configureFailFastRequestTimeouts(profile);
				capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(FirefoxDriver.PROFILE, profile); 
				break;
			case MFF:				
				profile = new FirefoxProfile(new File(ffProfilePath));
				profile.setPreference(blockSecurityMixedContent, false);
				configureFailFastRequestTimeouts(profile);
				profile.setPreference("general.useragent.override", iPhoneUserAgentString);
				capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(FirefoxDriver.PROFILE, profile); 
				break;
			case TFF:				
				profile = new FirefoxProfile(new File(ffProfilePath));
				configureFailFastRequestTimeouts(profile);
				profile.setPreference(blockSecurityMixedContent, false);
				profile.setPreference("general.useragent.override", iPadUserAgentString);
				capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(FirefoxDriver.PROFILE, profile); 
				break;
			case IE:
				/* Unless already set in system path environment variable, 
				 * else set correct path to driver as needed via Selenium server node JAR execution with extra parameter:
				 * -Dwebdriver.ie.driver="pathTo/IEDriverServer.exe"
				 */
				capabilities = DesiredCapabilities.internetExplorer();				
				/* start IE w/ clean session, clearing all cache & cookies before test run & separate process instances per IE session				 * 
				 * NOTE: untested as of yet and for Selenium version 2.33+ only
				 * NOTE: for IE 8 and above, also requires the TabProcGrowth registry value in Windows to be set to 0.
				 * based on Selenium project DesiredCapabilities wiki and https://groups.google.com/forum/?fromgroups#!topic/webdriver/4Qpd47qPPTg
				capabilities.setCapability(InternetExplorerDriver.IE_DRIVER_FORCE_CREATE_PROCESS_PROPERTY, 1);
				capabilities.setCapability(InternetExplorerDriver.IE_DRIVER_IE_SWITCHES_PROPERTY, "-private -nomerge");
				*/
				break;
			case SF:
				capabilities = DesiredCapabilities.safari();
				/* start Safari w/ clean session, clearing all cache & cookies before test run
				 * NOTE: use only for local tests or where not running 2+ Safari tests in parallel on same machine,
				 * otherwise may cause issue with clearing session data in existing tests when new test starts
				 */				
				//capabilities.setCapability(SafariDriver.CLEAN_SESSION_CAPABILITY, true);
				break;
			case MSF:
				if (machine.port == 4444) machine.port = 4723; //Appium default port is 4723 instead of 4444
				capabilities = new DesiredCapabilities();
				capabilities.setCapability("deviceName", "iPhone 5");
				capabilities.setCapability("platformName", "iOS");
				capabilities.setCapability("platformVersion", browser.browserVersion);
				capabilities.setCapability("browserName", "Safari");
				break;
			case TSF:
				if (machine.port == 4444) machine.port = 4723; //Appium default port is 4723 instead of 4444
				capabilities = new DesiredCapabilities();
				capabilities.setCapability("deviceName", "iPad Retina");
				capabilities.setCapability("deviceOrientation", "landscape");
				capabilities.setCapability("platformName", "iOS");
				capabilities.setCapability("platformVersion", browser.browserVersion);
				capabilities.setCapability("browserName", "Safari");
				break;
			case CH:
				capabilities = DesiredCapabilities.chrome();
				break;
			case MCH:
				capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability("chrome:switches", "--user-agent="+iPhoneUserAgentString);
				break;
			case TCH:
				capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability("chrome:switches", "--user-agent="+iPadUserAgentString);
				break;
			}
			
			driver = new RemoteWebDriver(new URL("http://" + machine.ip + ":" + machine.port + "/wd/hub" ), capabilities);

			//set file uploads to support or "work with" uploading from (Bamboo) test execution agent machine
			//not the remote browser machine running in Grid/SauceLabs. File is sent from agent to browser
			//machine then from browser machine uploaded to target website. See
			//sauceio.com/index.php/2012/03/selenium-tips-uploading-files-in-remote-webdriver/
			((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
		}

		//Set timeouts
		elementTimeout = elementTime;
		pageTimeout = pageTime;
		testTimeout = testTime;
		driver.manage().timeouts().implicitlyWait(elementTimeout, TimeUnit.SECONDS);
		// Selenium issues # 4137, 687
		if (   ! browser.browserName.equals(GCBrowserName.SF) 
			&& ! browser.browserName.equals(GCBrowserName.CH)
			&& ! browser.browserName.equals(GCBrowserName.MCH)
			&& ! browser.browserName.equals(GCBrowserName.TCH))
			driver.manage().timeouts().pageLoadTimeout(pageTimeout, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(testTimeout, TimeUnit.SECONDS);

		// Delete all cookies
		// Appium doesn't support delete all cookies
		if (   ! browser.browserName.equals(GCBrowserName.MSF)
			&& ! browser.browserName.equals(GCBrowserName.TSF))
			driver.manage().deleteAllCookies();
		
		// Maximize window, otherwise some popups are not visible, resulting in an error in IE
		// Appium doesn't support maximize window
		if (   ! browser.browserName.equals(GCBrowserName.MSF)
			&& ! browser.browserName.equals(GCBrowserName.TSF)
			&& ! browser.browserName.equals(GCBrowserName.TFF))
			driver.manage().window().maximize();
	}

    private static void configureFailFastRequestTimeouts(FirefoxProfile profile) {
        profile.setPreference("network.http.connection-timeout", HTTP_CONNECT_TIMEOUT_IN_SECONDS_FAIL_FAST);
        profile.setPreference("network.http.response.timeout", HTTP_RESPONSE_TIMEOUT_IN_SECONDS_FAIL_FAST);
    }

    /**
	 * tearDown
	 * @throws Exception
	 */
	public void tearDown() throws Exception {
		try {
			driver.quit();
		} catch (Exception e) {}  //Ignore IE error
	}


	/**
	 * Determine locator by string pattern of what it starts with
	 * and return appropriate By object for that locator
	 * @param 	loc 	Locator string with pattern
	 * @return
	 * @throws	NoSuchFieldException
	 */
	protected By getLocator(String loc) throws NoSuchFieldException {
		// Match by prefix identifier of locator type
		// then strip out prefix and return as appropriate By object
		if(loc.startsWith("id=")){
			return By.id(loc.substring(3));
		}else if(loc.startsWith("name=")){
			return By.name(loc.substring(5));
		}else if(loc.startsWith("css=")){
			return By.cssSelector(loc.substring(4));
		}else if(loc.startsWith("xpath=")){
			return By.xpath(loc.substring(6));
		}else if(loc.startsWith("//")){
			return By.xpath(loc); //no need strip for this case because of the implied XPath
		}else if(loc.startsWith("link=")){
			return By.linkText(loc.substring(5));
		}else if(loc.startsWith("sublink=")){
			return By.partialLinkText(loc.substring(8));
		}else if(loc.startsWith("tag=")){
			return By.tagName(loc.substring(4));
		}else{ // Locator pattern not found
			throw new NoSuchFieldException("Locator pattern " + loc + " not found. If you are specifying locator by ID or name attribute, please add prefix of 'id=' or 'name=', the lazy implied Selenium RC method not supported anymore, we want to be clear on identifying locator types.");
		}
	}

	/**
	 * Return the number of locators found
	 * @param loc			Locator
	 * @return int
	 * @throws Exception
	 */
	public int getLocatorCount(String loc) throws Exception {
		return driver.findElements(getLocator(loc)).size();
	}
	
	
	/**
	 * Wait for the number of locators becomes equals to the expected count
	 * @param loc			Locator
	 * @param locCount		Expected Locator count
	 * @param wait			Time to wait in seconds
	 * @throws Exception
	 */
	public void waitForLocatorCount(String loc, int locCount, int wait) throws Exception {
		boolean match = false;
		int currLocCount = 0;
		for (int i = 0; i<wait; i++){
			currLocCount = driver.findElements(getLocator(loc)).size();
			if (currLocCount == locCount){
				match = true;
				break;
			}
			sleep(1);
		}
		if(!match) throw new Exception("Timed out waiting for Locator: "+loc+"'s current count "+currLocCount+" become equals to expected coun:  "+ locCount);
	}
	
	
	/**
	 * Wait for the number of locators becomes equals to the expected count
	 * @param loc			Locator
	 * @param locCount		Expected Locator count
	 * @throws Exception
	 */
	public void waitForLocatorCount(String loc, int locCount) throws Exception {
		waitForLocatorCount(loc, locCount, elementTimeout);
	}
	

	/**
	 * Return list of WebElements matched by locator 
	 * @param loc			Locator
	 * @return List <WebElement>
	 * @throws Exception
	 */
	public List<WebElement> findElements(String loc) throws Exception {
		By byloc = getLocator(loc);
		return driver.findElements(byloc);
	}

	/**
	 * Return  WebElement matched by locator 
	 * @param loc			Locator
	 * @return WebElement
	 * @throws Exception
	 */
	
	public WebElement findElement(String loc) throws Exception {
		By byloc = getLocator(loc);
		return driver.findElement(byloc);
	}


	/**
	 * Sleep number of seconds
	 * @param sec
	 * @throws Exception
	 */
	public void sleep(int sec) throws Exception {
		Thread.sleep(sec * 1000L);
	}

	/**
	 * Refresh webpage
	 */
	public void refresh() {
		driver.navigate().refresh();
	}
	
	/**
	 * Refresh page if element not present
	 * @param loc		Locator of the element
	 * @param Nrefresh	Number of times to refresh
	 */
	public void refreshIfElementNotPresent(String loc, int Nrefresh) {
		for (int i=0; i<Nrefresh; i++) {
			if (isElementPresent(loc)) break;
			refresh();
		}
	}

	/**
	 * back webpage
	 */
	public void back() {
		driver.navigate().back();
	}   
	
	/**
	 * For webpage forward navigation
	 * @throws Exception
	 * @author ZenQA
	 */
	public void forward() throws Exception {
		driver.navigate().forward();
	} 

	/**
	 * Open an absolute URL
	 * @param url
	 */
	public void open(String url) {
		driver.get(url);
	}

	/**
	 * Close the browser (all open windows) and ends current Selenium session.
	 */
	public void close() {
		driver.quit();
	}
	
	/**
	 * Close currently selected browser window, when multiple windows open.
	 * Otherwise, closes the single main browser window, and ends current Selenium session.
	 */
	public void closeWindow(){
		driver.close();
	}

	/**
	 * Get current URL
	 */
	public String getLocation() {
		return driver.getCurrentUrl();
	}

	/**
	 * Return title of the webpage
	 * @return	String
	 */
	public String getTitle() {
		return driver.getTitle();
	}

    /**
     * Verifies if the title is as expected while ignoring upper or lower case
     * @param string
     */
	public boolean isTitlePresent(String tString) {
		if (this.getTitle().equalsIgnoreCase(tString)) return true;
		else return false;
		
	}

	/**
	 * Return true if an element is present and visible
	 * @param loc		Locator of the element
	 * @return boolean
	 */
	public boolean isElementPresent(String loc) {
		try {
			By byloc = getLocator(loc);
			if (driver.findElement(byloc).isDisplayed()) return true;
			else return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Return true if an element is present and visible within wait time
	 * @param loc		Locator of the element
	 * @param wait		Wait time in seconds
	 * @return boolean
	 */
	public boolean isElementPresent(String loc, int wait) {
		try {
			for (int i=0; i<wait; i++) {
				if (isElementPresent(loc)) return true;
				sleep(1);
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	  * Verifies the presence of an element by id and returns true if an element is present and visible
	  * @param id
	  * @return boolean
	  * @author ZenQA
	  */
	public boolean isElementPresentById(String elementid) {
	    try{
		if (driver.findElement(By.id(elementid)).isDisplayed())return true;
		else return false;
				
	   }
	   catch (Exception e){
		return false;
	   }
	}

	
	/**
	 * Wait for a text within wait time
	 * @param text			Text to wait for
	 * @param wait			Wait time in seconds
	 * @throws Exception
	 */
	public void waitForText(String text, int wait) throws Exception {
		boolean found = false;
		for (int i=0; i<wait; i++) {
			WebElement bodyElement = driver.findElement(By.tagName("body"));
			String bodySource = bodyElement.getText();
			if(bodySource.contains(text)){
				found = true;
				break;
			}
			sleep(1);
		}
		if (!found) throw new Exception("Timed out waiting for Text:  "+ text);
	}
	
	
	/**
	 * Wait for a Text within default timeout
	 * @param loc			Locator
	 * @throws Exception
	 */
	public void waitForText(String text) throws Exception {
		waitForText(text, elementTimeout);
	}
	
		
	/**
	 * Wait for a locator within default timeout
	 * @param loc			Locator
	 * @throws Exception
	 */
	public void waitForElement(String loc) throws Exception {
		waitForElement(loc, elementTimeout);
	}

	/**
	 * Wait for a locator within wait time
	 * @param loc			Locator
	 * @param wait			Wait time in seconds
	 * @throws Exception
	 */
	public void waitForElement(String loc, int wait) throws Exception {
		if (!isElementPresent(loc, wait)) throw new NoSuchFieldException("Locator " + loc + " not found.");
	}

	/**
	 * Wait for iframe
	 * @param frameNum		iframe number starting from 0
	 * @param wait			Wait time in seconds
	 */
	public void waitForIframe(int frameNum, int wait) throws Exception {
		String iframe = "//" + LOC_IFRAME + "[" + (frameNum+1) + "]";
		waitForElement(iframe, wait);
	}

	/**
	 * Click a locator
	 * @param loc			Locator
	 * @throws Exception
	 */
	public void click(String loc) throws Exception {
		By byloc = getLocator(loc);
		driver.findElement(byloc).click();
	}

	/**
	 * Click a locator within wait time
	 * @param loc			Locator
	 * @param wait			Wait time in seconds
	 * @throws Exception
	 */
	public void click(String loc, int wait) throws Exception {
		if (!isElementPresent(loc, wait)) throw new NoSuchFieldException("Locator " + loc + " not found.");
		click(loc);
	}
	
	/**
	 * Click a locator, at given index, for locators that match multiple elements. 
	 * Best used in tandem with SflyIndexLocator class (for defining locators to 
	 * use with this method).
	 * @param loc - locator string value we normally use, ignoring index
	 * @param index - index of locator from returned match results to use for automation, when you only want 1 out of the n matches
	 * @throws Exception
	 */
	public void clickLocatorAtIndex(String loc, int index) throws Exception {
		By byloc = getLocator(loc);
		driver.findElements(byloc).get(index).click();
	}
	
	/**
	 * Click at the given x,y coordinates.
	 * With this method, we don't define the locator element that we want to click
	 * but rather some coordinates. If you desire to click some specific
	 * locator/element, use one of the other mouse or click methods, or use
	 * getElementSizeAndCoordinates() against desired locator/element, then
	 * use the extracted x,y coordinates (topLeftX, topLeftY, etc.) to pass
	 * into this method here.
	 * @param x
	 * @param y
	 * @throws Exception
	 */
	public void clickCoord(int x, int y) throws Exception{
		executeJavascript("document.elementFromPoint(arguments[0], arguments[1]).click();", x, y);
	}
	
	/**
	 * Click at coordinates of a WebElement
	 * @param loc			Locator
	 * @throws Exception
	 */
	public void clickElementCoord(String loc) throws Exception {
		GCElementSizeAndCoordinates gcElementSizeAndCoordinates = getElementSizeAndCoordinates(loc);
        clickCoord(gcElementSizeAndCoordinates.topLeftX, gcElementSizeAndCoordinates.topLeftY);
	}
	/**
	 * Get the locator element's coordinates on the screen and width, height size 
	 * via SflyElementSizeAndCoordinates object.
	 * @param locator
	 * @return SflyElementSizeAndCoordinates object
	 * @throws Exception
	 */
	public GCElementSizeAndCoordinates getElementSizeAndCoordinates(String locator) throws Exception{
		WebElement elem = driver.findElement(getLocator(locator));
		return new GCElementSizeAndCoordinates(elem.getLocation().x, elem.getLocation().y, elem.getSize().width, elem.getSize().height);
	}

	/**
	 * Type text to locator
	 * @param loc			Locator
	 * @param text			Input text to locator
	 * @throws Exception
	 */
	public void type(String loc, String text) throws Exception {
		By byloc = getLocator(loc);
		driver.findElement(byloc).clear();
		driver.findElement(byloc).sendKeys(text);
	}
	
	public void typeWithoutClear(String loc, String text) throws Exception {
		By byloc = getLocator(loc);
		driver.findElement(byloc).sendKeys(text);
	}

	/**
	 * Type text to locator within wait time
	 * @param loc			Locator
	 * @param text			Input text to locator
	 * @param wait			Wait time in seconds
	 * @throws Exception
	 */
	public void type(String loc, String text, int wait) throws Exception {
		if (!isElementPresent(loc, wait)) throw new NoSuchFieldException("Locator " + loc + " not found.");
		type(loc, text);
	}
	
	/**
	 * Type text to locator, at given index, for locators that match multiple elements. 
	 * Best used in tandem with SflyIndexLocator class (for defining locators to 
	 * use with this method).
	 * @param loc - locator string value we normally use, ignoring index
	 * @param index - index of locator from returned match results to use for automation, when you only want 1 out of the n matches
	 * @param text - Input text to locator
	 * @throws Exception
	 */
	public void typeLocatorAtIndex(String loc, int index, String text) throws Exception {
		By byloc = getLocator(loc);
		WebElement elem = driver.findElements(byloc).get(index);
		elem.clear();
		elem.sendKeys(text);
	}

	/**
	 * Send a special key to given locator. Can use this before or after type() method.
	 * For use in sending keys like ENTER, RETURN, CONTROL, ALT, DELETE, TAB, SHIFT.
	 * Can also be used to send META key (e.g. Windows keys on Windows, command key on Mac)
	 * See org.openqa.selenium.Keys class for list of enum values of special keys to use.
	 * 
	 * @param loc - locator
	 * @param key - enum value of one of the special keys from org.openqa.selenium.Keys
	 * @throws Exception
	 */
	public void sendSpecialKey(String loc, Keys key) throws Exception{
		By byloc = getLocator(loc);
		driver.findElement(byloc).sendKeys(key);
	}
	
	/**
	 * Select an item from option listed, where partial/complete value is provided for an item variable 
	 * @param loc - string
	 * @param item - string
	 * @throws Exception 
	 */
	protected void select(String loc, String item) throws Exception {

		boolean found = false;
		By byloc = getLocator(loc);
		WebElement element = driver.findElement(byloc);
		Select select = new Select(element);  

		List<WebElement> options = select.getOptions();

		for (WebElement option : options) {
			if (option.getText().equals(item)) {
				option.click();
				found = true;
				break;
			}

			if(!found && (option.getText().contains(item))){
				option.click();
				break;
			}

		}
	}	

	/**
     * Select an option by name/text visible of option
     * @param loc			Locator
     * @param optName		Option name
     * @throws Exception
     */
    public void selectByText(String loc, String optName) throws Exception {
    	By byloc = getLocator(loc);
		Select select = new Select(driver.findElement(byloc));
		select.selectByVisibleText(optName);
		sleep(5);
	}
    
    /**
     * Select an option by name/text visible of option with wait time
     * @param loc			Locator
     * @param optName		Option name
     * @param wait			Wait time in seconds
     * @throws Exception
     */
    public void selectByText(String loc, String optName, int wait) throws Exception {
    	if (!isElementPresent(loc, wait)) throw new NoSuchFieldException("Locator " + loc + " not found.");
    	selectByText(loc, optName);
    }
    
    /**
     * Select an option by value
     * @param loc			Locator
     * @param value			Option value
     * @throws Exception
     */
    public void selectByValue(String loc, String value) throws Exception {
    	By byloc = getLocator(loc);
		Select select = new Select(driver.findElement(byloc));
		select.selectByValue(value);
	}
    
    /**
     * Select an option by value with wait time
     * @param loc			Locator
     * @param value			Option value
     * @param wait			Wait time in seconds
     * @throws Exception
     */
    public void selectByValue(String loc, String value, int wait) throws Exception {
    	if (!isElementPresent(loc, wait)) throw new NoSuchFieldException("Locator " + loc + " not found.");
    	selectByValue(loc, value);
    }
	
	/**
	 * Return text of locator
	 * @param loc			Locator
	 * @return String
	 * @throws Exception
	 */
	public String getText(String loc) throws Exception {
		By byloc = getLocator(loc);
		return driver.findElement(byloc).getText();
	}

	/**
	 * Return text of locator within wait time
	 * @param loc			Locator
	 * @param wait			Wait time in seconds
	 * @return String
	 * @throws Exception
	 */
	public String getText(String loc, int wait) throws Exception {
		if (!isElementPresent(loc, wait)) throw new NoSuchFieldException("Locator " + loc + " not found.");
		return getText(loc);
	}

	/**
	 * Return attribute in a locator
	 * @param loc			Locator
	 * @param attr			Attribute
	 * @return String
	 * @throws Exception
	 */
	public String getAttribute(String loc, String attr) throws Exception {
		By byloc = getLocator(loc);
		return driver.findElement(byloc).getAttribute(attr);
	}

	/**
	 * Return attribute in a locator within wait time
	 * @param loc			Locator
	 * @param attr			Attribute
	 * @param wait			Wait time in seconds
	 * @return String
	 * @throws Exception
	 */
	public String getAttribute(String loc, String attr, int wait) throws Exception {
		if (!isElementPresent(loc, wait)) throw new NoSuchFieldException("Locator " + loc + " not found.");
		return getAttribute(loc, attr);
	}

	/**
	 * Return x,y position of the upperLeft corner of a displayed web element
	 * @param loc			Locator
	 * @return Point
	 * @throws Exception
	 */
	public Point getXYPosition(String loc) throws Exception {
		By byloc = getLocator(loc);
		return driver.findElement(byloc).getLocation();
	}

	/**
	 * Confirm alert, prompt, or confirmation dialog that is present on the page.
	 * Does nothing if confirmation not present on page and returns empty string.
	 * @return String 		alert text or empty string
	 */
	public String getConfirmation() {		
		try{
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			alert.accept();
			return alertText;		
			//}catch(Exception ex){
		}catch(NoAlertPresentException ex){
			return ""; //if there is no alert displayed, do nothing, return empty string.
		}
	}

	/**
	 * dismiss alert, prompt, or confirmation dialog that is present on the page.
	 * Does nothing if confirmation not present on page and returns empty string.
	 * @return String 		alert text or empty string
	 */
	public String dismissConfirmation() {		
		try{
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			alert.dismiss();
			return alertText;		
			//}catch(Exception ex){
		}catch(NoAlertPresentException ex){
			return ""; //if there is no alert displayed, do nothing, return empty string.
		}
	}
	
	/**
	 * Mouse over a WebElement
	 * @parm webElement WebElement
	 * @throws Exception
	 * @author Jinzhi Zhang(George)
	 */
	public void mouseOverElement(WebElement webElement) throws Exception{
		Locatable locatable = (Locatable) webElement;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(locatable.getCoordinates());
	} 
	
	/**
	 * Mouse click a WebElement using mouseMove, mouseDown, mouseUp
	 * @param webElement
	 * @throws Exception
	 * @author hking
	 */
	public void mouseClickElement(WebElement webElement) throws Exception{
		Locatable locatable = (Locatable) webElement;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(locatable.getCoordinates());
		mouse.mouseDown(locatable.getCoordinates());
		mouse.mouseUp(locatable.getCoordinates());
	} 
	
	/**
	 * Mouse over a locator(This method sometimes does not work. If it does not work, you can use mouseOverElement(String element) )
	 * @param loc			Locator
	 * @throws Exception
	 */
	public void mouseOver(String loc) throws Exception {
		By byloc = getLocator(loc);

		//Actions action = new Actions(driver);
		//action.moveToElement(driver.findElement(byloc)).build().perform();
		//Mouse over workaround
		//from code.google.com/p/selenium/issues/detail?id=2067, comment #60
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		executeJavascript(mouseOverScript, driver.findElement(byloc));		
	}
	

	/**
	 * Mouse over a locator
	 * @param loc			Locator
	 * @throws Exception
	 */
	public void mouseOverCreative(String loc) throws Exception {
		By byloc = getLocator(loc);

		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(byloc)).build().perform();
		//Mouse over workaround
		//from code.google.com/p/selenium/issues/detail?id=2067, comment #60
		//String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		//executeJavascript(mouseOverScript, driver.findElement(byloc));		
	}

	/**
	 * Mouse over a locator within wait time(This method sometimes does not work. If it does not work, you can use mouseOverElement(String element, int waittime) )
	 * @param loc			Locator
	 * @param wait			Wait time in seconds
	 * @throws Exception
	 */
	public void mouseOver(String loc, int wait) throws Exception {
		if (!isElementPresent(loc, wait)) throw new NoSuchFieldException("Locator " + loc + " not found.");
		mouseOver(loc);
	}
	
	/**
	 * Mouse over a (or mouse move to) given x,y coordinate
	 * NOTE: this method is experimental, may not work or do anything
	 * @param x - coordinate to mouse over/move to
	 * @param y - coordinate to mouse over/move to
	 * @throws Exception
	 */
	public void mouseOverCoordinates(int x, int y) throws Exception{
		//based on code from ynot408.wordpress.com/2011/09/22/drag-and-drop-using-selenium-webdriver
		//we'll use body tag/element as starting element to invoke the javascript event against
		WebElement source = driver.findElement(getLocator("tag=body"));
		String mouseMoveScript = "function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
		 "simulate(arguments[0],\"mousemove\",arguments[0],arguments[1]);";
		executeJavascript(mouseMoveScript,source,String.valueOf(x),String.valueOf(y));
	}

	/**
	 * Drag from source locator and drop to target locator
	 * @param sourceLoc			Source locator
	 * @param targetLoc			Target locator
	 * @throws Exception
	 */
	public void dragAndDrop(String sourceLoc, String targetLoc) throws Exception {
		WebElement source = driver.findElement(getLocator(sourceLoc));
		WebElement target = driver.findElement(getLocator(targetLoc));
		if (browser.browserName == GCBrowserName.SF) { //Safari workaround
			//use javascript to workaround Selenium issue 4136 for SafariDriver
			//based on code from ynot408.wordpress.com/2011/09/22/drag-and-drop-using-selenium-webdriver
			String xto=Integer.toString(target.getLocation().x);
			String yto=Integer.toString(target.getLocation().y);
			String dragAndDropScript = "function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
			"simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]);";			
			sleep(5); // delay required for treat
			executeJavascript(dragAndDropScript,source,xto,yto);			
		}else{ //use native Actiosn/Interactions API for supported browsers
			(new Actions(driver)).dragAndDrop(source, target).perform();
		}
	}

	/**
	 * Drag from source locator and drop to target locator within wait time
	 * @param sourceLoc		Source locator
	 * @param targetLoc		Target locator
	 * @param wait			Wait time in seconds
	 * @throws Exception
	 */
	public void dragAndDrop(String sourceLoc, String targetLoc, int wait) throws Exception {
		if (!isElementPresent(sourceLoc, wait)) throw new NoSuchFieldException("Locator " + sourceLoc + " not found.");
		if (!isElementPresent(targetLoc, wait)) throw new NoSuchFieldException("Locator " + targetLoc + " not found.");
		dragAndDrop(sourceLoc, targetLoc);
	}
	
	/**
	 * Drag from source locator to specified x,y coordinate location.
	 * x,y coordinates can be based off of some (target) locator or
	 * be arbitrary values to test with.
	 * @param sourceLoc - source locator
	 * @param targetLocationX - target x coordinate to drop to
	 * @param targetLocationY - target y coordinate to drop to
	 * @throws Exception
	 */
	public void dragAndDrop(String sourceLoc, int targetLocationX, int targetLocationY) throws Exception{
		//based on code from ynot408.wordpress.com/2011/09/22/drag-and-drop-using-selenium-webdriver
		WebElement source = driver.findElement(getLocator(sourceLoc));
		String dragAndDropScript = "function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
		"simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]);";			
		executeJavascript(dragAndDropScript,source,String.valueOf(targetLocationX),String.valueOf(targetLocationY));
	}
	
	/**
	 * Drag from source locator to specified x,y coordinate location within wait time
	 * x,y coordinates can be based off of some (target) locator or be arbitrary values to test with.
	 * @param sourceLoc - source locator
	 * @param targetLocationX - target x coordinate to drop to
	 * @param targetLocationY - target y coordinate to drop to
	 * @param wait - wait time in seconds
	 * @throws Exception
	 */
	public void dragAndDrop(String sourceLoc, int targetLocationX, int targetLocationY, int wait) throws Exception{
		if (!isElementPresent(sourceLoc, wait)) throw new NoSuchFieldException("Locator " + sourceLoc + " not found.");
		dragAndDrop(sourceLoc, targetLocationX, targetLocationY);
	}

	/**
	 * Get value of named cookie. Can throw a nullPointerException if cookie
	 * doesn't exist, so please call isCookiePresent() before calling this method,
	 * or use a try/catch block around this method to avoid unexpected exception.
	 * @param name		name of cookie
	 * @return			String
	 */
	public String getCookie(String name) throws Exception {
		if(driver.manage().getCookieNamed(name) == null) sleep(1);	//Wait a sec if cookie is not there yet
		if(driver.manage().getCookieNamed(name) == null) return null;
		else return driver.manage().getCookieNamed(name).getValue();
	}

	/**
	 * Add a cookie to the browser session, using default/no expiration, setting as null value.
	 * @param name		Cookie name
	 * @param value		Cookie value
	 * @param domain	Cookie domain
	 * @param path		Cookie path, typically "/"
	 */
	public void addCookie(String name, String value, String domain, String path){
		driver.manage().addCookie(new org.openqa.selenium.Cookie(name, value, domain, path, null));
	}
	
	/**
	 * Add a cookie to the browser session, using specified expiration date
	 * @param name		Cookie name
	 * @param value		Cookie value
	 * @param domain	Cookie domain
	 * @param path		Cookie path, typically "/"
	 * @param expiration cookie expiration date as Java Date object
	 */
	public void addCookie(String name, String value, String domain, String path, Date expiration){
		driver.manage().addCookie(new org.openqa.selenium.Cookie(name, value, domain, path, expiration));
	}

	/**
	 * Delete a cookie by name
	 * @param name		Cookie name
	 */
	public void deleteCookie(String name){
		driver.manage().deleteCookieNamed(name);
	}
	
	/**
	 * Delete all cookies (visible to browser) in current domain
	 */
	public void deleteAllCookies(){
		driver.manage().deleteAllCookies();
	}

	/**
	 * Check if given cookie is present. Useful to call to manually delete cookies
	 * that weren't deleted by driver.manage().deleteAllCookies() or to add some
	 * cookie if it doesn't exist. Also useful to call before calling getCookie()
	 * to avoid getting a nullPointerException for cookie that doesn't exist.
	 * 
	 * @param name		Cookie name
	 * @return			True if cookie found, otherwise false.
	 */
	public boolean isCookiePresent(String name){
		if(driver.manage().getCookieNamed(name) == null)
			return false;
		else
			return true;		
	}

	/**
	 * Execute Javascript against current page. Returns back an object.
	 * Depending on what you expect to do with result, typecast to
	 * String, Boolean, Long, List, or WebElement. If null is returned,
	 * either there is problem, or you weren't expecting to return
	 * anything. 
	 * @param javascript - the javascript code to execute.
	 * @return				Returns null or an object that can be cast to String, Boolean, Long, List, or WebElement
	 */
	public Object executeJavascript(String javascript){
		return ((JavascriptExecutor) driver).executeScript(javascript);
	}

	/**
	 * Execute Javascript against current page with given arguments. 
	 * Returns back an object. Depending on what you expect to do with result, 
	 * typecast to String, Boolean, Long, List, or WebElement. If null is returned,
	 * either there is problem, or you weren't expecting to return anything. 
	 * @param javascript - the javascript code to execute.
	 * @param arguments - variable number of arguments to be used in some specified javascript code
	 * @return				Returns null or an object that can be cast to String, Boolean, Long, List, or WebElement
	 */
	public Object executeJavascript(String javascript, Object... arguments){
		return ((JavascriptExecutor) driver).executeScript(javascript, arguments);
	}

	/**
	 * Remove item stored in HTML5 local storage in browser.
	 * e.g. p13n data on p13n page
	 * @param item		item to remove
	 */
	public void removeItemFromLocalStorage(String item){		
		this.executeJavascript(String.format("window.localStorage.removeItem('%s');", item));
	}

	/**
	 * Checks to see if item exists in HTML5 local storage in browser.
	 * e.g. p13n data on p13n page
	 * @param item		item to check
	 * @return			true if found, otherwise false
	 */
	public boolean isItemPresentInLocalStorage(String item){		
		if(this.executeJavascript(String.format("return window.localStorage.getItem('%s');", item)) == null)
			return false;
		else
			return true;
	}

	/**
	 * Retrieve item's value from HTML5 local storage in browser.
	 * e.g. p13n data on p13n page
	 * @param item		item to retrieve value for
	 * @return			item value
	 */
	public String getItemFromLocalStorage(String item){		
		return (String) this.executeJavascript(String.format("return window.localStorage.getItem('%s');", item));
	}

	/**
	 * Set or create item in HTML5 local storage in browser.
	 * e.g. p13n data on p13n page
	 * @param item		item to set value for or create
	 * @param value		value of item
	 */
	public void setItemInLocalStorage(String item, String value){
		this.executeJavascript(String.format("window.localStorage.setItem('%s','%s');", item, value));
	}

	/**
	 * Clears HTML5 local storage in browser for current domain/page.
	 * Think of this as similar to deleteAllCookies(). Should only be
	 * called on actual page, not empty browser window when WebDriver
	 * is just instantiated.
	 */
	public void clearLocalStorage(){
		this.executeJavascript(String.format("window.localStorage.clear();"));
	}

	/**
	 * Upload file with support for uploads from browser locally as well as over
	 * Selenium Grid2 or RemoteWebDriver. Use this over just sendKeys method to type
	 * in file path. File is sent from test agent to browser machine (via JSONWireProtocol)
	 * then from browser machine uploaded to target website.
	 * 
	 * @param loc			File upload element (input type=file)
	 * @param filePath		The path to file to upload
	 * @throws Exception
	 */
	public void uploadFile(String loc, String filePath) throws Exception {
		//based on code posted by Krishnan and Mark at
		//groups.google.com/forum/#!topic/webdriver/OU2RxvTE7UY/discussion

		//get element via locator string
		By byloc = getLocator(loc);
		WebElement uploader = driver.findElement(byloc);

		//perform appropriate type of upload depending if local or not
		if (machine.getIp() == GCMachine.LOCALHOST) {			
			uploader.sendKeys(filePath); //normal upload
		}else{ //host is IP address OR some hostname = Grid mode OR SauceLabs			
			//set up for file upload across Grid2, RemoteWebDriver, SauceLabs
			((RemoteWebElement) uploader).setFileDetector(new LocalFileDetector());
			uploader.sendKeys(new File(filePath).getAbsolutePath()); //now do upload

			//old code
			//LocalFileDetector lfd = new LocalFileDetector();
			//File f = lfd.getLocalFile(filePath);		
			//((RemoteWebElement) uploader).setFileDetector(lfd);
			//uploader.sendKeys(f.getAbsolutePath()); //now do upload
		}		
	}

	/**
	 * Checks whether vertical or horizontal scrollbar is present in specified
	 * element. Returns TRUE/FALSE, and can then verify with an
	 * assertTrue or assertFalse function call.
	 * @param loc			the locator element to check for scrollbars
	 * @param orientation	the orientation as "vertical" or "horizontal"
	 * @return				true or false
	 */
	public boolean isScrollbarPresentIn(String loc, String orientation) throws Exception {
		By byloc = getLocator(loc);
		WebElement element = driver.findElement(byloc);
		//adapted from selenium-automation.blogspot.com/2010/10/selenium-automation-problems.html
		if(orientation == "vertical"){
			int locatorHeight =  Integer.parseInt(element.getAttribute("clientHeight"));
			int scrollHeight = Integer.parseInt(element.getAttribute("scrollHeight"));
			if(locatorHeight < scrollHeight){
				return true; //scrollbar appears
			}else{
				return false; //scrollbar not appear
			}
		}else{ //$orientation == "horizontal"
			int locatorWidth = Integer.parseInt(element.getAttribute("clientWidth"));
			int scrollWidth = Integer.parseInt(element.getAttribute("scrollWidth"));
			if(locatorWidth < scrollWidth){
				return true; //scrollbar appears
			}else{
				return false; //scrollbar not appear
			}
		}
	}

	/**
	 * Maximize current window.
	 */
	public void windowMaximize(){
		try{
			driver.manage().window().maximize();
		}catch(Exception ex){
			//window maximize not supported for driver or browser (e.g. Chrome)
			//so use javascript workaround
			String winMaxScript = "if(window.screen){window.moveTo(0,0);window.resizeTo(window.screen.availWidth,window.screen.availHeight);}";
			executeJavascript(winMaxScript);			
		}
	}
	
	

	
	/**
	 * Set the window size to that of an ipad screen , to be used with responsive ui
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public void setWindowSizeToIpadLandscape(){
		this.driver.manage().window().setPosition(new Point(0,0));
        this.driver.manage().window().setSize(new Dimension(1024,768));

	}

	/**
	 * Set the window size to that of an ipad screen , to be used with responsive ui
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public void setWindowSizeToIpadPortrait(){
		this.driver.manage().window().setPosition(new Point(0,0));
        this.driver.manage().window().setSize(new Dimension(768,1024));

	}
	
	/**
	 * Check to see if text is present on the page or not.
	 * @param text
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isTextPresent(String text){
		/* alternate solutions, in case current one has any issues...
		 * groups.google.com/group/selenium-users/browse_thread/thread/8d15fb3a0bb95992
		 * or adapt from isTextPresent() method in PHP binding at
		 * code.google.com/p/php-webdriver-bindings/source/browse/trunk/trunk/phpwebdriver/CWebDriverTestCase.php
		 */		
		try {
			WebElement bodyElement = driver.findElement(By.tagName("body"));
			String bodySource = bodyElement.getText();
			return bodySource.contains(text);
		} catch (Exception e) {
			return false;
		}      
	}

	/**
	 * Check to see if text is present on the page or not, within given wait time,
	 * in seconds.
	 * @param text
	 * @param wait time in seconds
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isTextPresent(String text, int wait){
		boolean found;
		int i = 0;
		do {
			try {				
				found = isTextPresent(text);
				if(!found) {
					sleep(1);
					i++;
				}			
			} catch (Exception e) {
				found = false;
			} 
		} while( !found && i <= wait );
		return found;
	}

	/**
	 * Check whether an element is enabled (editable) or disabled (greyed out)
	 * @param loc
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isElementEnabled(String loc) throws Exception {
		By byloc = getLocator(loc);
		return driver.findElement(byloc).isEnabled();
	}

	/**
	 * Check whether an element is selected (or checked) or not.
	 * @param loc
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isElementSelected(String loc) throws Exception {
		By byloc = getLocator(loc);
		return driver.findElement(byloc).isSelected();
	}

	/**
	 * select a checkbox by using isElementSelected method and click method 
	 * @throws Exception 
	 */
	public void check(String loc) throws Exception {
		if (!isElementSelected(loc)) {
			click(loc);
		}
	}

	/**
	 * deselect a checkbox by using isElementSelected method and click method
	 */
	public void unCheck(String loc) throws Exception {
		if (isElementSelected(loc)) {
			click(loc);
		}
	}

	/**
	 * Force a mouse click on given locator using javascript. Useful
	 * when click() method doesn't do anything on locator. This is
	 * same as calling mouseDown, then mouseUp.
	 * 
	 * Currently doesn't work well in Safari when clicked against
	 * buttons that are actually div elements, like Facebook login,
	 * Picasa/Flickr login.
	 * @param loc
	 * @throws Exception
	 */
	public void mouseClick(String loc) throws Exception {
		//could also look into Actions API to do mouse up, down, click
		//but not available in SafariDriver yet and may have issues.
		By byloc = getLocator(loc);
		String clickScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onclick');}";
		this.executeJavascript(clickScript, driver.findElement(byloc));
	}

	/**
	 * Force a mouse click on given locator using javascript within wait time
	 * @param loc			Locator
	 * @param wait			Wait time in seconds
	 * @throws Exception
	 */
	public void mouseClick(String loc, int wait) throws Exception {
		if (!isElementPresent(loc, wait)) throw new NoSuchFieldException("Locator " + loc + " not found.");
		mouseClick(loc);
	}

	/**
	 * Open a new (popup) window with given URL
	 * @param url
	 */
	public void openWindow(String url){
		executeJavascript("window.open('"+url+"');");
	}

	/**
	 * Wait for a (popup) window to appear. Use this 
	 * version of method when there is only 1 window open. 
	 * @throws Exception
	 */
	public void waitForPopUp() throws Exception {
		waitForPopUp(1);
	}

	/**
	 * Wait for specified popup window to appear. 
	 * Specify by window number. Use this version of 
	 * method when there are multiple windows open.
	 * @param windowNumber starting at 1 for first popup
	 * @throws Exception
	 */
	public void waitForPopUp(int windowNumber) throws Exception {
		int winCount = driver.getWindowHandles().size();
		for(int loopCount = 0; loopCount < elementTimeout; loopCount++){
			if(winCount == (windowNumber+1)) break;			
			sleep(1); //else continue waiting
			winCount = driver.getWindowHandles().size();
		}
	}

	/**
	 * Switch window
	 * @param num	Window number starting at 0
	 */
	public void switchToWindow(int num) {
		int numWindow = driver.getWindowHandles().size();
		String[] window = (String[])driver.getWindowHandles().toArray(new String[numWindow]);
		driver.switchTo().window(window[num]);
	}

	/**
	 * Get a count on the number of currently open windows.
	 * @return
	 */
	public int getWindowCount(){
		return driver.getWindowHandles().size();
	}

	/**
	 * Switch to an iframe on page (e.g. widget popups like sign in widget).
	 * Figure out the right iframe that contains your content by searching
	 * for XPath //iframe in Firebug, and find the best contextual match.
	 * @param num	iframe number starting at 0
	 */
	public void switchToIframe(int num) {		
		//driver.switchTo().frame(num); //other method below may be more concise & stable
		driver.switchTo().frame(driver.findElements(By.tagName("iframe")).get(num));
	}

	/**
	 * Switch to iframe by locator
	 * @param loc		Locator
	 * @throws Exception
	 */
	public void switchToIframe(String loc) throws Exception {		
		By byloc = getLocator(loc);
		driver.switchTo().frame(driver.findElement(byloc));
	}

	/**
	 * Switch back to main page content when you've previously switched
	 * to an iframe (e.g. widget popups like sign in widget).
	 */
	public void switchToMainFrame(){
		//go back to main/parent page or 1st frame
		driver.switchTo().defaultContent();
	}

	/**
	 * Get a count on the number of iframes on current page
	 * @return int 
	 * @throws Exception
	 */
	public int getIframeCount() throws Exception{
		return getLocatorCount("//iframe");
	}

	// *** Begin implicit wait management methods *** //
	// Based on this discussion thread online
	// groups.google.com/group/selenium-users/t/e953bfec85f29606
	/** 
	 * Invoke this method when you want to get rid of implicit wait.
	 * 
	 * If you have set ImplicitWait already, then you would have to 
	 * explicitly set it to zero to nullify/disable/cancel it.
	 */
	public void disableImplicitWait() {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	/**
	 * Invoke this method to bring back implicit wait again, if it was 
	 * disabled sometime after Selenium test was started.
	 */
	public void enableImplicitWait() {
		driver.manage().timeouts().implicitlyWait(elementTimeout, TimeUnit.SECONDS);
	}
	// *** End implicit wait management methods *** //

	/**
	 * Checks to see if image element/locator (not the src attribute) is actually rendered/displayed by browser, 
	 * or whether it appears as a broken image (e.g. bad URL/HTTP response). 
	 * Use this over isElementPresent(), etc. when checking against images on a page.
	 * @param loc
	 * @return
	 * @throws Exception
	 */
	public boolean isImageDisplayed(String imageLocator) throws Exception {
		//adapted from watirmelon.com/2012/11/27/checking-an-image-is-actually-visible-using-webdriver/
		By byloc = getLocator(imageLocator);
		Boolean result = null;
		if(browser.browserName == GCBrowserName.IE){
			result = (Boolean) executeJavascript("return arguments[0].complete;", driver.findElement(byloc));			
		}else{ //other browser types use diff method to check
			result = (Boolean) executeJavascript("return (typeof arguments[0].naturalWidth!=\"undefined\" && arguments[0].naturalWidth>0);", driver.findElement(byloc));			
		}
		return result.booleanValue();
	}
		
	/**
	 * Wait for page to redirect to another page/URL, until given wait time.
	 * @param redirectedUrl
	 * @param wait - time in seconds
	 * @throws Exception
	 */
	public void waitForRedirect(String redirectedUrl, int wait) throws Exception{
		int waitCount = 0;
		while(!getLocation().equals(redirectedUrl)){
			if(waitCount == wait) throw new Exception("Timed out waiting for redirect to "+ redirectedUrl);
			sleep(1);
			waitCount++;
		}
	}
	
	/**
	 * Wait for the URL of a page to change
	 * @param origUrl	Original URL to be changed
	 * @param wait		Wait time in seconds
	 * @throws Exception
	 */
	public void waitForPageToChange(String origUrl, int wait) throws Exception{
		int waitCount = 0;
		while(getLocation().equals(origUrl)){
			if(waitCount == wait) throw new Exception("Timed out waiting for page to change from "+ origUrl);
			sleep(1);
			waitCount++;
		}
	}
	 
	
	/**
	 * To get the text of the selected option from a drop-down
	 * @param loc
	 * @return String
	 * @throws Exception
	 * @author ZenQA Automation team
	 */
	public String getSelectedText(String loc) throws Exception{
		By byloc = getLocator(loc);
		WebElement element = driver.findElement(byloc);
		String selectedText="";
		Select select = new Select(element); 
		List<WebElement> options = select.getOptions();

		for (WebElement option : options)
			if (option.isSelected()) {
				selectedText = option.getText(); 
			}
		return selectedText;
	}	
	
	/**
	 * Returns the page source of the lastly loaded page.
	 * @author ZenQA
	 */
	public String getHtmlSource() throws Exception{
		return driver.getPageSource();
	}
	
	/**
	 * Returns the HTML source inside of specified locator
	 * (rather the whole HTML page source). Useful when
	 * you only need a subset of the HTML page source.
	 * @param locator - element locator for which you want the HTML source that is nested under/inside it
	 * @return
	 * @throws Exception
	 */
	public String getHtmlSourceOfLocator(String locator) throws Exception{
		return getAttribute(locator, "innerHTML");
	}
	
	
	
	/**
	 * Check to see if specified locator element's attribute contains
	 * specified value
	 * @param locator - locator element to check
	 * @param attribute - attribute of element to check
	 * @param expectedAttributeValue - the value to be found in the attribute
	 * @return
	 * @throws Exception
	 */
	public boolean doesElementAttributeContain(String locator, String attribute, String expectedAttributeValue) throws Exception{
		return getAttribute(locator, attribute).contains(expectedAttributeValue);
	}
	
	
	/**
	 * Returns the active/displayed/visible locator on screen when the locator value 
	 * used actually matches multiple locators on the page. Useful method since 
	 * Selenium 2 / WebDriver can only manipulate visible/active elements anyhow.
	 * If more than one locator is found active, throws exception as we are only 
	 * expecting one to be active.
	 * @param locator
	 * @return active locator element if found, otherwise null
	 * @throws Exception if more than 1 active locator found or Selenium throws exception. 
	 */
	public WebElement getActiveLocatorInSet(String locator) throws Exception{
		WebElement activeElem = null;
		int activeElemCount = 0;
		ArrayList<WebElement> elems = (ArrayList<WebElement>) findElements(locator);
		for(int i = 0; i < elems.size(); i++){
			if(elems.get(i).isDisplayed()){
				if(++activeElemCount>1)
					throw new Exception("More than 1 active visible locator found on page, expecting only 1");
				else
					activeElem = elems.get(i);
			}
		}
		return activeElem;
	}

	

	/**
	 * Add/enable/allow checking for javascript errors on a page (browser window/frame/iframe)
	 * by first collecting available errors (after page load though). Based on this solution:
	 * mguillem.wordpress.com/2011/10/11/webdriver-capture-js-errors-while-running-tests/
	 * 
	 * Usage: this method to be called after page load but before doing any testing. When 
	 * checking for javascript errors after calling this method, you then call
	 * getJavascriptErrorCountOnPage() and/or getJavascriptErrorsOnPage()
	 */
	public void addJavascriptErrorCollectionToPage(){
		String script = "window.collectedErrors = []; "
			  + "window.onerror = function(errorMessage) { "
			  + "window.collectedErrors[window.collectedErrors.length] = errorMessage; "
			  + "};";
		executeJavascript(script);		
	}
	
	/**
	 * Get the count or number of javascript errors on a page (browser window/frame/iframe),
	 * but after page load only (excludes page load related javascript errors.
	 * 
	 * Prerequisite: call addJavascriptErrorCollectionToPage() after page load but before
	 * doing any testing, and before calling this method.
	 * 
	 * @return count or number of javascript errors
	 */
	public long getJavascriptErrorCountOnPage(){
		return ((Long) executeJavascript("return window.collectedErrors.length;")).longValue();
	}
	
	/**
	 * Get the javascript errors found on a page (browser window/frame/iframe),
	 * but after page load only (excludes page load related javascript errors.
	 * 
	 * Prerequisite: call addJavascriptErrorCollectionToPage() after page load but before
	 * doing any testing, and before calling this method.
	 * 
	 * NOTE: the actual error messages exposed to us may not be useful and are not the
	 * same as the messages seen in Firebug or developer console of the browsers. But
	 * you can at least use getJavascriptErrorCountOnPage() to confirm if there are any
	 * errors found and then manually test/debug to find the actual error message given
	 * by Firebug and other browser developer consoles.
	 * 
	 * @return (String) ArrayList of error messages
	 */
	public ArrayList<?> getJavascriptErrorsOnPage(){
		return (ArrayList<?>) executeJavascript("return window.collectedErrors;");
	}
	
	/**
	 * Enable querying or finding elements by XPath and CSS from the Javascript
	 * Document Object Model (DOM), for workarounds where direct Selenium
	 * API methods to find/click/type element has issues. Calling this method
	 * from any page will then let you make the following type of calls. Not
	 * sure if this method will persist in browser should you navigate to a 
	 * different page.
	 * 
	 * e.g. document.getElementByXPath("//a[contains(text(),'some text')]");
	 * e.g. document.getElementsByXPath("//a[contains(text(),'some text')]");
	 * e.g. document.getElementByCssSelector("a[class*='some text']");
	 * e.g. document.getElementsByCssSelector("a[class*='some text']")
	 * 
	 * where you execute in our framwork as something like
	 * 
	 * SflySeleniumPageObject.executeJavascript("document.getElementByXPath(\"//a[contains(text(),'some text')]\").value = 'workaround for type/sendKeys';");
	 */
	public void enableFindingElementsByXPathOrCSSFromJavascriptDOM(){
		// based on references here:
		// groups.google.com/d/topic/selenium-users/PTPWFU2ho8Y/discussion
		// userscripts.org/scripts/review/24554
		// userscripts.org/scripts/review/23928		
		
		String script = "document.getElementByXPath = function(sValue) { var a = "
		+ "this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); "
		+ "if (a.snapshotLength > 0) { return a.snapshotItem(0); } }; ";
		
		script += "document.getElementsByXPath = function(sValue){ var aResult = new Array(); "
			+ "var a = this.evaluate(sValue, this, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); "
			+ "for ( var i = 0 ; i < a.snapshotLength ; i++ ){aResult.push(a.snapshotItem(i));} return aResult;}; ";
		
		script += "document.getElementByCssSelector = document.querySelector; ";
		script += "document.getElementsByCssSelector = document.querySelectorAll;";
		
		executeJavascript(script);
	}
	
	/**
	 * Checks to see if an element is on the visible screen or viewport of the browser
	 * or not where it would need to be scrolled into view.
	 * 
	 * For more details about this method, see
	 * darrellgrainger.blogspot.com/2013/05/is-element-on-visible-screen.html
	 * 
	 * @param loc
	 * @return
	 * @throws Exception
	 */
	public boolean isElementOnTheVisibleBrowserScreen(String loc) throws Exception{
		//based on code from
		//darrellgrainger.blogspot.com/2013/05/is-element-on-visible-screen.html
		By byloc = getLocator(loc);
		WebElement w = driver.findElement(byloc);
		
	    Dimension weD = w.getSize();
	    Point weP = w.getLocation();
	    Dimension d = driver.manage().window().getSize();

	    int x = d.getWidth();
	    int y = d.getHeight();
	    int x2 = weD.getWidth() + weP.getX();
	    int y2 = weD.getHeight() + weP.getY();

	    return x2 <= x && y2 <= y;
	}
	
	/**
	 * Check if the specified locator/element's class (attribute)
	 * is selected or not. E.g. something like:
	 * <div class="widgets-trir-icon widgets-trir-icon-selected">
	 * when selected VS this when not selected:
	 * <div class="widgets-trir-icon">
	 * @param loc - locator
	 * @return true if selected, otherwise false
	 * @throws Exception
	 */
	public boolean isElementClassSelected(String loc) throws Exception{
		return doesElementAttributeContain(loc, "class", "selected") 
		|| doesElementAttributeContain(loc, "class", "-checked") 
		|| doesElementAttributeContain(loc, "class", " checked");
	}
	
	/**
	 * Check if the specified locator/element's class (attribute)
	 * is enabled or not. E.g. something like:
	 * <div class="something-stepName-enabled">
	 * when element is enabled VS this when disabled:
	 * <div class="soemthing-stepName">
	 * @param loc - locator
	 * @return true if enabled, otherwise false
	 * @throws Exception
	 */
	public boolean isElementClassEnabled(String loc) throws Exception{
		return doesElementAttributeContain(loc, "class", "enabled");
	}
	
	/**
	 * Get the text under/inside/within a locator element. This means
	 * text directly inside the text (can use getText() method) as
	 * well as text within child elements nested under the specified
	 * element. Does not include HTML source of the child elements,
	 * for that use getHtmlSourceOfLocator().
	 * @param loc
	 * @return
	 * @throws Exception
	 */
	public String getTextUnderLocator(String loc) throws Exception{
		if(browser.browserName == GCBrowserName.IE)
			return getAttribute(loc, "innerText");
		else
			return getAttribute(loc, "textContent");
	}
	
	/**
	 * Return the value of the specified CSS style property of the specified locator
	 * (e.g. example CSS style properties: fontSize, color, backgroundColor, height, 
	 * width, left, letterspacing, top, marginRight, margintBottom, maxWidth, paddingTop, zIndex)
	 * 
	 * NOTE: The method doesn't work on styles like "display: none;" or similar, which effect 
	 * visibility of the element (hidden or not). Because the element has to be visible to begin 
	 * with for Selenium to be able to manipulate it. Thus in such cases, rather than reading 
	 * the style attribute value, better to check the presence of the element with isElementPresent. 
	 * Because if the element style is hidden (e.g. display none), it will not be present and vice versa.
	 *  
	 * @param loc			Locator
	 * @param cssStyleProperty
	 * @return String
	 * @throws Exception
	 */
	public String getCssStylePropertyValue(String loc, String cssStyleProperty) throws Exception {
		By byloc = getLocator(loc);
		return driver.findElement(byloc).getCssValue(cssStyleProperty);
	}
	
	/**
	 * Returns a generic object map of all the CSS styles and their values that are applied to the 
	 * specified locator (e.g. example CSS style properties: fontSize, color, backgroundColor, height,
	 * width, left, letterspacing, top, marginRight, margintBottom, maxWidth, paddingTop, zIndex)
	 * 
	 * NOTE: The method doesn't work on styles like "display: none;" or similar, which effect 
	 * visibility of the element (hidden or not). Because the element has to be visible to begin 
	 * with for Selenium to be able to manipulate it. Thus in such cases, rather than reading 
	 * the style attribute value, better to check the presence of the element with isElementPresent. 
	 * Because if the element style is hidden (e.g. display none), it will not be present and vice versa.
	 *  
	 * @param loc			Locator
	 * @return Map<Object,Object> containing all the styles applied to the element
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked") //suppress warning of the cast to Map object
	public Map<Object,Object> getCssStylesAppliedToElement(String loc) throws Exception {
		By byloc = getLocator(loc);
		return (Map<Object,Object>) executeJavascript("return arguments[0].getStyles();", driver.findElement(byloc));
	}
	
	/**
	 * Takes a screenshot of the page using Selenium then crops the screenshot image
	 * down to the location & size of the specified element on the page, returning
	 * the local saved file copy of the cropped screenshot for use in image comparison.
	 * 
	 * <p>NOTE: screenshot & after cropping may still differ visually across browsers and
	 * in some cases may even render as solid black image or throw exception if screenshot
	 * too big to transfer for Selenium Grid or SauceLab test runs.
	 * 
	 * <p>NOTE: as part of the Selenium API, we reuse the screenshot file name from Selenium
	 * after cropping and this temp screenshot file is automatically deleted by
	 * Selenium after the test run completes, unless you pause/breakpoint the test then
	 * abort it abnormally.
	 * 
	 * <p>NOTE: this method currently just supports Selenium, might not work for mobile
	 * Safari or mobile apps via Appium yet (try at your own risk).
	 * 
	 * <p>NOTE: if you get "RasterFormatException: (x + width) is outside Raster", 
	 * or something similar, try disabling multiple monitors which could be the culprit.
	 * 
	 * @param loc - element locator to crop screenshot around/to
	 * @return cropped screenshot saved to local file
	 * @throws Exception
	 */
	public String getScreenshotOfElement(String loc) throws Exception{
		//need to check if code below will work for Appium as is or need make changes...
		//to support mobile Safari and iOS mobile apps...
		GCElementSizeAndCoordinates elemInfo = getElementSizeAndCoordinates(loc);
		File screenshotFile = null;
		if (machine.getIp() == GCMachine.LOCALHOST) {
			//using a native browser-specific WebDriver
			screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		}else{//using RemoteWebDriver, need "augment" functionality
			WebDriver augmentedDriver = new Augmenter().augment(driver);
	        screenshotFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
		}        
        BufferedImage image = ImageIO.read(screenshotFile);
        /* NOTE: if cropping method below tends to often return 
         * "RasterFormatException: (x + width) is outside Raster", or something similar
         * then better to consider 3rd party library for cropping than native Java support
         * 
         * e.g. www.javaxt.com/javaxt-core/javaxt.io.Image/
         * 
         * or disable multiple monitor mode on your machine, which is also a culprit to the problem
         */
        BufferedImage croppedImage = image.getSubimage(elemInfo.topLeftX,elemInfo.topLeftY,elemInfo.elementWidth,elemInfo.elementHeight);
        ImageIO.write(croppedImage, "png", screenshotFile);        
		return screenshotFile.getAbsolutePath();
	}
	
	/**
	 * Takes a screenshot of the page using Selenium, returning the local saved file 
	 * copy of the screenshot for use in image comparison, etc.
	 * 
	 * <p>NOTE: screenshot may differ visually across browsers and in some cases may even  
	 * render as solid black image or throw exception if screenshot too big to transfer 
	 * for Selenium Grid or SauceLab test runs.
	 * 
	 * <p>NOTE: as part of the Selenium API, we reuse the screenshot file name from Selenium
	 * and this temp screenshot file is automatically deleted by Selenium after the test 
	 * run completes, unless you pause/breakpoint the test then abort it abnormally.
	 * 
	 * <p>NOTE: this method currently just supports Selenium, might not work for mobile
	 * Safari or mobile apps via Appium yet (try at your own risk).
	 * 
	 * @return screenshot of page saved to local file
	 * @throws Exception
	 */
	public String getScreenshotOfPage() throws Exception{
		//need to check if code below will work for Appium as is or need make changes...
		//to support mobile Safari and iOS mobile apps...
		File screenshotFile = null;
		if (machine.getIp() == GCMachine.LOCALHOST) {
			//using a native browser-specific WebDriver
			screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		}else{//using RemoteWebDriver, need "augment" functionality
			WebDriver augmentedDriver = new Augmenter().augment(driver);
	        screenshotFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
		}        
		return screenshotFile.getAbsolutePath();
	}
	
	/**
	 * Scroll page/screen in the specified direction for the given units in pixels.
	 * For simplicity you scroll one direction at a time only. To scroll in 2 directions,
	 * call this method twice for the desired combination (UP/DOWN + LEFT/RIGHT).
	 * 
	 * @param direction - enum
	 * @param units - in pixels
	 * @throws Exception
	 */
	public void scrollPage(GCDirection.Movement direction, int units) throws Exception{
		int invertedUnits;
		switch(direction){
		case UP:
			//invert unit value to scroll opposite the default direction
			invertedUnits = units - (2*units);
			executeJavascript("window.scrollBy(0,arguments[0]);", invertedUnits);
			//executeJavascript("scroll(0,arguments[0]);", invertedUnits);			
			break;
		case DOWN:
			executeJavascript("window.scrollBy(0,arguments[0]);", units);
			//executeJavascript("scroll(0,arguments[0]);", units);			
			break;
		case LEFT:
			//invert unit value to scroll opposite the default direction
			invertedUnits = units - (2*units);
			executeJavascript("window.scrollBy(arguments[0],0);", invertedUnits);
			//executeJavascript("scroll(arguments[0],0);", invertedUnits);			
			break;
		case RIGHT:
			executeJavascript("window.scrollBy(arguments[0],0);", units);
			//executeJavascript("scroll(arguments[0],0);", units);			
			break;
		default:
			//do nothing
		}		
	}
	
	/**
	 * Scroll page/screen to the specified locator, in case Selenium
	 * does not auto scroll to that locator when calling any method
	 * (which indirectly calls the findBy methods which should have
	 * scrolled to the element in question?)
	 * 
	 * @param loc
	 * @throws Exception
	 */
	public void scrollToElement(String loc) throws Exception{
		By byloc = getLocator(loc);
		executeJavascript("arguments[0].scrollIntoView();", driver.findElement(byloc));
		//or...
		//SflyElementSizeAndCoordinates elemInfo = getElementSizeAndCoordinates(loc);
		//executeJavascript("window.scrollTo(arguments[0],arguments[1]);", elemInfo.topLeftX, elemInfo.topLeftY);
	}
	
	/**
	 * Reset or scroll page to default top of screen (0,0 coordinates)
	 * @throws Exception
	 */
	public void scrollToTopOfPage() throws Exception{
		executeJavascript("window.scrollTo(0,0);");
	}
	
	/**
     * Get the selcted option from drop down
     * @param loc		drop down Locator
     * @return selected option 
     * @throws Exception
     * @author ZenQ
     */
    public String getSelectedValue(String loc) throws Exception {
    	By byloc = getLocator(loc);
		Select select = new Select(driver.findElement(byloc));
		WebElement option = select.getFirstSelectedOption();
		return option.getText();  
	}
    
    /**
     * Get the WebElement from the given mouse coordinates for
     * further manipulation.
     * @param x - coordinate where element is located
     * @param y - coordinate where element is located
     * @return WebElement matching the DOM element at given coordinates
     * @throws Exception
     */
    public WebElement getElementFromCoordinates(int x, int y) throws Exception{
    	return (WebElement) executeJavascript("return document.elementFromPoint(arguments[0], arguments[1]);", x, y);
    }
    
    /**
     * Click and drag an element by an offset of x, y 
     * clicks at the top left corner of the element  ie (0,0)
     * @throws NoSuchFieldException 
     */
    public void clickAndDrag ( String locator ,int x, int y) throws NoSuchFieldException{
    	WebElement e = driver.findElement(getLocator(locator)); 
    	new Actions(driver).moveToElement(e,0,0).clickAndHold(e).moveByOffset(x, y).release().perform();
    }
    
    /**
     * Wait until the page load status shows complete
     */
    public void waitForPageToLoad() {
    	 do {
             JavascriptExecutor js = (JavascriptExecutor)driver;
             js = (JavascriptExecutor) driver;
             pageLoadStatus = (String)js.executeScript("return document.readyState");
           } 
    	 while ( !pageLoadStatus.equals("complete") );
      }

    /**
     * Tap at pixel (x,y)
     * @param x	Pixels from the left
     * @param y	Pixels from the top
     */
    public void mobileTap(int x, int y) {
    	HashMap<String, Double> hashMap = new HashMap<String, Double>();
    	hashMap.put("x", (double)x);
    	hashMap.put("y", (double)y);
    	((JavascriptExecutor)driver).executeScript("mobile: tap", hashMap);
    }

    /**
     * Swipe from pixel (startX, startY) to pixel (endX, endY) in duration time
     * 
     * ***** NOTE: For iOS only, NOT Android *****
     * 
     * iOS UI Automation reference:
     * https://developer.apple.com/library/ios/documentation/ToolsLanguages/Reference/UIATargetClassReference/UIATargetClass/UIATargetClass.html#//apple_ref/doc/uid/TP40009924
     * @param startX	Start x pixels from the left
     * @param startY	Start y pixels from the top
     * @param endX		End x pixels from the left
     * @param endY		End y pixels from the top
     * @param duration	Time duration of the swipe in seconds
     */
    public void mobileSwipe_iOS(int startX, int startY, int endX, int endY, double duration) {
    	((JavascriptExecutor)driver).executeScript(
			"target.dragFromToForDuration(" + 
					"{x:" + startX + ", y:" + startY +"}, " +
					"{x:" + endX   + ", y:" + endY   +"}, " +
					duration + 
					");"
		);
    }
}
