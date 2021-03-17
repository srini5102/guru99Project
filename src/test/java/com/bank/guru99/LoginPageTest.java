package com.bank.guru99;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import resources.Base;

@Listeners(itestlisteners.ListenersTest.class)
public class LoginPageTest extends Base  {

	@BeforeTest
	public void setup() throws IOException {
		// intialize the driver
		driver = intializer();

	}

	@Test(dataProvider = "getData")
	public void login(String userID, String password) throws IOException {

		// get url of page
		String url = properties().getProperty("url");

		// launch url
		driver.get(url);

		// maximze window size
		driver.manage().window().maximize();

		LoginPage lip = new LoginPage(driver);

		// enter user id
		lip.getUserID().sendKeys(userID);

		// enter password
		lip.getPassword().sendKeys(password);

		// click submit
		lip.getSubmit().click();

		// if credential is invalid then get text from pop-up
		if (isAlertPresent()) {
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			alert.accept();
		}

		// verfiy wheter its login successfully or not
		else {
			String expectedTitle = "Guru99 Bank Manager HomePage";
			String actualTitle = lip.getTitle();
			if (expectedTitle.equalsIgnoreCase(actualTitle)) {
				System.out.println("Login Successfully");
			}
		}

	}

	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	@DataProvider(name = "getData")
	public Object[][] getData() throws IOException {

		String filePath = System.getProperty("user.dir") + "\\src\\data.xlsx";
		String sheetName = "login credentials";

		// get credentials from excel
		Object data[][] = readExcel(filePath, sheetName);

		return data;
	}

	@AfterTest
	public void tearDown() {

		// close the driver
		driver.quit();
	}

}
