package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.DataProvider;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Base {

	public static WebDriver driver;

	public WebDriver intializer() throws IOException {

		// get browser name which we need
		String browserName = properties().getProperty("browser");

		String userdir =System.getProperty("user.dir");
		// path of chromeDriver
		String chromeDriverPath = userdir+"\\src\\Drivers\\ChromeDriver\\chromedriver.exe";

		// path of geckodriver
		String geckoDriverPath = userdir+"\\src\\Drivers\\GeckoDriver\\geckodriver.exe";

		// path of iedriver
		String ieDriverPath = userdir+"\\src\\Drivers\\IEDriver\\IEDriverServer.exe";

		if (browserName.equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			driver = new ChromeDriver();

		}

		if (browserName.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.gecko.driver", geckoDriverPath);
			driver = new FirefoxDriver();

		}

		if (browserName.equalsIgnoreCase("IE")) {

			System.setProperty("webdriver.ie.driver", ieDriverPath);
			driver = new InternetExplorerDriver();

		}

		return driver;

	}

	public static Properties properties() throws IOException {

		String filePath = System.getProperty("user.dir")+"\\src\\test\\java\\resources\\data.properties";
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(filePath);
		properties.load(fis);
		return properties;
	}

	public Object[][] readExcel(String filePath, String sheetName) throws IOException {

		String fileFormat = filePath.substring(filePath.indexOf("."));
		FileInputStream fis = new FileInputStream(filePath);

		Object data[][] = null;
		System.out.println(filePath + "    " + sheetName + "   " + fileFormat);

		if (fileFormat.equalsIgnoreCase(".xlsx")) {

			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(sheetName);

			Row row = sheet.getRow(0);
			int noOfRows = sheet.getLastRowNum();
			int noOfColumns = row.getLastCellNum();

			System.out.println(noOfRows);
			System.out.println(noOfColumns);

			// set size of data
			data = new Object[noOfRows][noOfColumns];

			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i + 1);
				// iterate cells
				Iterator<Cell> cell = ((Row) row).cellIterator();

				for (int j = 0; cell.hasNext(); j++) {
					// get coulmn values row by row
					Cell value = cell.next();
					// store all cell values into data object
					data[i][j] = value.getStringCellValue();

				}
			}

		}

		if (fileFormat.equalsIgnoreCase(".xls")) {
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheet(sheetName);

			Row row = sheet.getRow(0);
			int noOfRows = sheet.getLastRowNum();
			int noOfColumns = row.getLastCellNum();

			// set size of data
			data = new Object[noOfRows - 1][noOfColumns];

			for (int i = 1; i < sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				// iterate the cells
				Iterator<Cell> cell = ((Row) row).cellIterator();

				for (int j = 0; cell.hasNext(); j++) {
					// get all column values row by row
					Cell value = cell.next();

					// store all cell values into data object
					data[i][j] = value.getStringCellValue();
				}
			}

		}
		return data;
	}
	
	public void takeScreenshot(WebDriver driver, String fileName) throws IOException {
		
//		TakesScreenshot screenshot = (TakesScreenshot) driver;
//		File src = screenshot.getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(src, new File(""));
//		
		String folder = System.getProperty("user.dir")+"\\target\\screenshots";
		Screenshot scrnshot = new AShot().takeScreenshot(driver);
		scrnshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		ImageIO.write(scrnshot.getImage(), "jpg", new File(folder+fileName));
	}
}
