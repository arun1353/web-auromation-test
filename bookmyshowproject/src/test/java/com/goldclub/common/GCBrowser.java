package com.goldclub.common;

/**
 * GoldClub browser class
 * @author hking
 *
 */
public class GCBrowser {
	
	public GCBrowserName browserName;
	public String browserVersion;
	
	/**
	 * Constructor for default browser
	 */
	public GCBrowser() {
		this(GCBrowserName.FF, "30");
	}
	
	/**
	 * Constructor to set browser version based on browser name
	 * 
	 * @param name	Browser name
	 */
	public GCBrowser(GCBrowserName name) {
		browserName = name;
		switch (name) {
		case FF:
			browserVersion = "30";
			break;
		case MFF:
			browserVersion = "30";
			break;
		case TFF:
			browserVersion = "30";
			break;
		case IE:
			browserVersion = "10";
			break;
		case SF:
			browserVersion = "6";
			break;
		case MSF:
			browserVersion = "7.1";
			break;
		case TSF:
			browserVersion = "7.1";
			break;
		case CH:
			browserVersion = "";
			break;
		case MCH:
			browserVersion = "";
			break;
		case TCH:
			browserVersion = "";
			break;
		case IPHONE_APP:
			browserVersion = "7.1";
			break;
		case IPAD_APP:
			browserVersion = "7.1";
			break;
		default:
			browserVersion = "";
			break;
		}
	}
	
	/**
	 * Constructor to set browser name and version
	 * @param name		Browser name
	 * @param version	Browser version
	 */
	public GCBrowser(GCBrowserName name, String version) {
		browserName = name;
		browserVersion = version;
	}
	
}
