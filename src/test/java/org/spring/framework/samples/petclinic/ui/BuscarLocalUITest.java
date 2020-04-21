
package org.spring.framework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BuscarLocalUITest {

	private WebDriver		driver;
	private String			baseUrl;
	//	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		//		String pathToGeckoDriver = "C:\\Users\\Jesus\\Downloads";
		//		System.setProperty("webdriver.chrome.driver", pathToGeckoDriver + "\\chromedriver.exe");
		this.driver = new ChromeDriver();
		this.baseUrl = "http://localhost:8080/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testBuscarLocal() throws Exception {
		this.driver.get(this.baseUrl);
		this.driver.findElement(By.xpath("//a[contains(@href, '/locales/buscar')]")).click();
		this.driver.findElement(By.name("direccion")).click();
		this.driver.findElement(By.name("direccion")).clear();
		this.driver.findElement(By.name("direccion")).sendKeys("Luis Montoto");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Luis Montoto 12", this.driver.findElement(By.xpath("//table[@id='localesTable']/tbody/tr/td")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	//	private boolean isElementPresent(final By by) {
	//		try {
	//			this.driver.findElement(by);
	//			return true;
	//		} catch (NoSuchElementException e) {
	//			return false;
	//		}
	//	}
	//
	//	private boolean isAlertPresent() {
	//		try {
	//			this.driver.switchTo().alert();
	//			return true;
	//		} catch (NoAlertPresentException e) {
	//			return false;
	//		}
	//	}
	//
	//	private String closeAlertAndGetItsText() {
	//		try {
	//			Alert alert = this.driver.switchTo().alert();
	//			String alertText = alert.getText();
	//			if (this.acceptNextAlert) {
	//				alert.accept();
	//			} else {
	//				alert.dismiss();
	//			}
	//			return alertText;
	//		} finally {
	//			this.acceptNextAlert = true;
	//		}
	//	}
}
