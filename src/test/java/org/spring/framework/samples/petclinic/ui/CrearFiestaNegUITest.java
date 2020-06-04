
package org.spring.framework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CrearFiestaNegUITest {

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
	public void testCrearFiestaN() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("cliente1");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("cliente1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("CLIENTE1", this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		this.driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();
		this.driver.findElement(By.linkText("Solicitar fiesta")).click();
		this.driver.findElement(By.id("nombre")).click();
		this.driver.findElement(By.id("nombre")).clear();
		this.driver.findElement(By.id("nombre")).sendKeys("test1");
		this.driver.findElement(By.id("descripcion")).click();
		this.driver.findElement(By.id("descripcion")).clear();
		this.driver.findElement(By.id("descripcion")).sendKeys("test");
		this.driver.findElement(By.id("precio")).click();
		this.driver.findElement(By.id("precio")).clear();
		this.driver.findElement(By.id("precio")).sendKeys("23");
		this.driver.findElement(By.id("requisitos")).click();
		this.driver.findElement(By.id("requisitos")).clear();
		this.driver.findElement(By.id("requisitos")).sendKeys("ninguno");
		this.driver.findElement(By.id("fecha")).click();
		this.driver.findElement(By.linkText("1")).click();
		this.driver.findElement(By.id("fecha")).click();
		this.driver.findElement(By.linkText("29")).click();
		this.driver.findElement(By.id("horaInicio")).click();
		this.driver.findElement(By.id("horaInicio")).clear();
		this.driver.findElement(By.id("horaInicio")).sendKeys("tgfr");
		this.driver.findElement(By.id("horaFin")).click();
		this.driver.findElement(By.id("horaFin")).clear();
		this.driver.findElement(By.id("horaFin")).sendKeys("rfc");
		this.driver.findElement(By.id("imagen")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Debe de indicar los asistentes", this.driver.findElement(By.xpath("//form[@id='fiesta']/div/div[8]/div/span[2]")).getText());
		Assert.assertEquals("Este campo no puede estar vacio", this.driver.findElement(By.xpath("//form[@id='fiesta']/div/div[9]/div/span[2]")).getText());
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
