package com.goldclub.common;

import org.openqa.selenium.WebElement;

public class GCElementSizeAndCoordinates {
	public final int topLeftX;
	public final int topLeftY;
	public final int topRightX;
	public final int topRightY;
	public final int bottomLeftX;
	public final int bottomLeftY;
	public final int bottomRightX;
	public final int bottomRightY;
	public final int elementWidth;
	public final int elementHeight;
	public final int elementCenterX;
	public final int elementCenterY;
	
	
	/**
	 * Constructor - pass in element's top left corner x,y coordinates and the element's width & height 
	 * and the remaining coordinates will be calculated from these parameters
	 * @param topLeftX - coordinate for top left corner of element
	 * @param topLeftY - coordinate for top left corner of element
	 * @param elementWidth - element's width (in pixels)
	 * @param elementHeight - element's height (in pixels)
	 */
	public GCElementSizeAndCoordinates(int topLeftX, int topLeftY, int elementWidth, int elementHeight){
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.topRightX = topLeftX + elementWidth;
		this.topRightY = topLeftY;
		this.bottomLeftX = topLeftX;
		this.bottomLeftY = topLeftY - elementHeight;
		this.bottomRightX = this.topRightX;
		this.bottomRightY = this.bottomLeftY;
		this.elementWidth = elementWidth;
		this.elementHeight = elementHeight;
		this.elementCenterX = topLeftX + (elementWidth/2);
		this.elementCenterY = topLeftY - (elementHeight/2);
	}
	
	/**
	 * Constructor - pass in WebElement, and constructor will parse out
	 * the element's coordinates and size.
	 * @param element - the Selenium WebElement
	 */
	public GCElementSizeAndCoordinates(WebElement element){
		this(element.getLocation().x,
				element.getLocation().y,
				element.getSize().width,
				element.getSize().height); 
	}
}
