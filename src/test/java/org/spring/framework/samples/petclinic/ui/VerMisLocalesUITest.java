package org.spring.framework.samples.petclinic.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class VerMisLocalesUITest {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeEach
  public void setUp() throws Exception {
	String pathToGeckoDriver="C:\\Users\\sergio\\Desktop";
	System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testVerMisLocalesUI() throws Exception {
    driver.get("http://localhost:8080/");
    this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();;
    driver.findElement(By.id("username")).sendKeys("propietario1");
    driver.findElement(By.id("password")).sendKeys("propietario1");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
    driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
    try {
        assertEquals("Propietario1 DB", driver.findElement(By.xpath("//tr[6]/td")).getText());
      } catch (Error e) {
        verificationErrors.append(e.toString());
      }
    }


  @AfterEach
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

}
