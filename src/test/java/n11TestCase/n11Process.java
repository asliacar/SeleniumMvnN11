package n11TestCase;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class n11Process {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://www.n11.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testN11TestCase1() throws Exception {
	//Turkish special characters are important for the test case
	//On first import charaters might not be suitable for unicode, we must always check and correct them
    driver.get(baseUrl + "/");
    driver.findElement(By.cssSelector("img[alt=\"Alışverişin Uğurlu Adresi\"]")).click();
    try {
      assertEquals("", driver.findElement(By.cssSelector("img[alt=\"Alışverişin Uğurlu Adresi\"]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.linkText("Giriş Yap")).click();
    try {
      assertEquals("Üye Girişi", driver.findElement(By.cssSelector("h2")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    
    //A sample account currently working with a simple password
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("asliaktugacar@gmail.com");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("sufle123");
    driver.findElement(By.id("loginButton")).click();
    try {
      assertEquals("Hesabım", driver.findElement(By.linkText("Hesabım")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("searchData")).click();
    driver.findElement(By.id("searchData")).clear();
    
    //In n11.com, "Samsung için" text seems to be a logical text choice to search and verify
    driver.findElement(By.id("searchData")).sendKeys("samsung");
    driver.findElement(By.cssSelector("span.icon.iconSearch")).click();
    try {
      assertEquals("Samsung", driver.findElement(By.cssSelector("div.resultText > h1")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    
    //Search results may vary so as the 3rd result of this test case
    //But, we are assuming the result do not change considering the case
    driver.findElement(By.linkText("2")).click();
    try {
      assertEquals("2", driver.findElement(By.cssSelector("div.productArea > div.pagination > div.pageInfo > input[name=\"currentPage\"]")).getAttribute("value"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.xpath("//div[@id='p-28107268']/div[2]/span[2]")).click();
    driver.findElement(By.linkText("Hesabım")).click();
    try {
      assertEquals("Aslı Aktuğ", driver.findElement(By.cssSelector("strong")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.xpath("(//a[contains(text(),'Favorilerim')])[2]")).click();
    try {
      assertEquals("Favorilerim", driver.findElement(By.id("buyerProductWatchLegend")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      //We could have search for the Id of the product that has been added to the link's Url,
      //but, it seems like searching the name of the product works 
      assertEquals("Samsung Galaxy Note 3 Cep Telefonu", driver.findElement(By.linkText("Samsung Galaxy Note 3 Cep Telefonu")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.linkText("Kaldır")).click();
    // Warning: verifyTextNotPresent may require manual changes
    try {
      assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*link=Samsung Galaxy Note 3 Cep Telefonu[\\s\\S]*$"));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}