
package org.spring.framework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CrearLocalNegUITest {

	private WebDriver		driver;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = "C:\\Users\\Frank\\Downloads";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testCrearLocalN() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("propietario1");
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("PROPIETARIO1", this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		this.driver.findElement(By.linkText("Nuevo local")).click();
		this.driver.findElement(By.id("direccion")).click();
		this.driver.findElement(By.id("direccion")).clear();
		this.driver.findElement(By.id("direccion")).sendKeys("Prueba1");
		this.driver.findElement(By.id("capacidad")).click();
		this.driver.findElement(By.id("capacidad")).clear();
		this.driver.findElement(By.id("capacidad")).sendKeys("1002");
		this.driver.findElement(By.id("condiciones")).click();
		this.driver.findElement(By.id("condiciones")).clear();
		this.driver.findElement(By.id("condiciones")).sendKeys("Alguna");
		this.driver.findElement(By.id("imagen")).click();
		this.driver.findElement(By.id("imagen")).clear();
		this.driver.findElement(By.id("imagen")).sendKeys("jhhfvvyb");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("tiene que ser una URL válida", this.driver.findElement(By.xpath("//form[@id='add-local-form']/div/div[4]/div/span[2]")).getText());
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
