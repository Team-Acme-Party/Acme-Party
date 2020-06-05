
package org.spring.framework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BuscarFiestaUITest {

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
	public void testBuscarUnaFiesta() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/fiestas/buscar')]")).click();
		this.driver.findElement(By.name("nombre")).click();
		this.driver.findElement(By.name("nombre")).clear();
		this.driver.findElement(By.name("nombre")).sendKeys("disfraces");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Fiesta de disfraces", this.driver.findElement(By.xpath("//table[@id='fiestasTable']/tbody/tr/td")).getText());
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
