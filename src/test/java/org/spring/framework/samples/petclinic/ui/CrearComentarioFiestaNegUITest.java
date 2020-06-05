
package org.spring.framework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CrearComentarioFiestaNegUITest {

	private WebDriver		driver;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = "C:\\Users\\Danie\\Downloads";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testCrearComentarioFiestaN() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("cliente1");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("cliente1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("El comentario no puede estar vacio", this.closeAlertAndGetItsText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}
