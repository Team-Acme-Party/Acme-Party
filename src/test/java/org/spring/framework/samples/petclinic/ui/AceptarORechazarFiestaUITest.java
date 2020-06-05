
package org.spring.framework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AceptarORechazarFiestaUITest {

	private WebDriver		driver;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = "C:\\Users\\Danie\\Downloads";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAceptarFiesta() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).sendKeys("propietario1");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).sendKeys("propietario1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		this.driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
		this.driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
		this.driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
		this.driver.findElement(By.xpath("//span")).click();
		this.driver.findElement(By.xpath("//table[@id='fiestasTable']/tbody/tr[2]/td")).click();
		Assert.assertEquals("Fiesta electronica", this.driver.findElement(By.xpath("//table[@id='fiestasTable']/tbody/tr[2]/td")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

}
