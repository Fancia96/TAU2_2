
import static org.junit.Assert.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindTest {

    //Przykłady znajdowania elementów na stronie www bez elementów xpath

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        System.setProperty("webdriver.msedge.driver", "resources/msedgedriver");

        EdgeOptions options=new EdgeOptions();
        //options.addArguments("headless");

        driver = new EdgeDriver(options);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() throws Exception {
        driver.get("https://duckduckgo.com/");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testFindingByName() {
        WebElement p=driver.findElement(By.name("viewport"));

        assertNotNull(p);
    }

    @Test
    public void testFindingByClass() {
        WebElement p = driver.findElement(By.className("js-popout-trig"));

        assertNotNull(p);
    }

    @Test
    public void testFindingByTag() {
        WebElement p = driver.findElement(By.tagName("h1"));

        assertNotNull(p);
    }

    @Test
    public void testNotFindingElement() {
        WebElement notFoundElement = null;

        try {
            notFoundElement = driver.findElement(By.id("not-found-element"));
        } catch (NoSuchElementException e) {
        }

        assertNull(notFoundElement);
    }

    @Test
    public void testFindFirstAndThirdResult() {
        WebElement p=driver.findElement(By.name("q"));

        p.sendKeys("Przepis na ciasto");
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
        w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul")));
        p.submit();

        WebElement first = driver.findElement(By.xpath("//article[@id='r1-0']/div[2]/h2/a/span"));
        first.click();


        driver.get("https://duckduckgo.com/?q=przepis+na+ciasto&t=h_&ia=web");
        WebElement second = driver.findElement(By.xpath("//article[@id='r1-2']/div[2]/h2/a/span"));
        second.click();
        assertNotNull(second);
    }


}
