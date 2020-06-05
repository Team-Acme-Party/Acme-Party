package org.spring.framework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CrearValoracionFiestaUITest {	
	  private WebDriver driver;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @BeforeEach
	  public void setUp() throws Exception {
		  String pathToGeckoDriver = "C:\\Users\\Danie\\Downloads";
		  System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
	    driver = new FirefoxDriver();
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testUntitledTestCase() throws Exception {
	    driver.get("http://localhost:8080/");
	    this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("cliente2");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("cliente2");
		driver.findElement(By.xpath("//button[@type='submit']")).click();	
		driver.findElement(By.cssSelector("a[title=\"Mis asistencias\"]")).click();
		 driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();
	    driver.findElement(By.id("comentario")).click();
	    driver.findElement(By.id("comentario")).clear();
	    driver.findElement(By.id("comentario")).sendKeys("asd");
	    driver.findElement(By.id("valor")).click();
	    driver.findElement(By.id("valor")).clear();
	    driver.findElement(By.id("valor")).sendKeys("5");
	    driver.findElement(By.xpath("(//button[@type='button'])[6]")).click();
	    Assert.assertEquals("asd", driver.findElement(By.xpath("//table[@id='valoracionesTable']/tbody/tr/td")).getText());
	  }

	  @AfterEach
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	     Assert.fail(verificationErrorString);
	    }
	  }

	}


