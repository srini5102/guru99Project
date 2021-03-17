package com.bank.guru99;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage{
	

	WebDriver driver;

	@FindBy(name = "uid")
	WebElement userID;

	@FindBy(name = "password")
	WebElement password;

	@FindBy(name="btnLogin")
	WebElement submit;
	
	
	
	public LoginPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getUserID() {
		
		return userID;
	}
	
	public WebElement getPassword() {
		
		return password;
	}
	
	public WebElement getSubmit() {
		
		return submit;
	}
	
	public String getTitle() {
		
		return driver.getTitle();
	}

}
