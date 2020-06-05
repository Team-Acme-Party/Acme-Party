
package org.spring.framework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CrearAnuncioUITest {

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
	public void testCrearAnuncioFiesta() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("patrocinador1");
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("patrocinador1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("PATROCINADOR1", this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		this.driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
		this.driver.findElement(By.linkText("Ofrecer anuncio")).click();
		this.driver.findElement(By.id("imagen")).click();
		this.driver.findElement(By.id("imagen")).clear();
		this.driver.findElement(By.id("imagen")).sendKeys("https://media.gettyimages.com/photos/man-wearing-sandwich-board-picture-id78486140?s=612x612");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertEquals("Fiesta de rock[ 200 personas ]", this.driver.findElement(By.xpath("//table[@id='anunciosTable']/tbody/tr[4]/td")).getText());
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
