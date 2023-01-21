
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TauTestFiveChromeAndEdge {
    private static WebDriver driverChrome;
    private static WebDriver driverEdge;

    @BeforeAll
    public static void setUpDrivers(){
        System.setProperty("webdriver.chrome.drive", System.getProperty("user.dir") + "/chromedriver");
        driverChrome = new ChromeDriver();
        driverChrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        System.setProperty("webdriver.msedge.driver", "resources/msedgedriver");

        EdgeOptions options=new EdgeOptions();
        driverEdge = new EdgeDriver(options);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driverEdge.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() throws Exception {
        driverChrome.get("http://automationpractice.pl/index.php");
        driverEdge.get("http://automationpractice.pl/index.php");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driverChrome.quit();
        driverEdge.quit();
    }

    @Test
    public void testSearchingInChrome(){
        driverChrome.findElement(By.id("search_query_top")).click();
        driverChrome.findElement(By.id("search_query_top")).clear();
        driverChrome.findElement(By.id("search_query_top")).sendKeys("dress");
        driverChrome.findElement(By.name("submit_search")).click();

        List<WebElement> expected = driverChrome.findElements(By.xpath("//li[contains(@class, 'ajax_block_product')]"));

        System.out.println(expected.size());
        assertEquals(expected.size(), 7);
    }

    @Test
    public void testSearchingInEdge(){
        driverEdge.findElement(By.id("search_query_top")).click();
        driverEdge.findElement(By.id("search_query_top")).clear();
        driverEdge.findElement(By.id("search_query_top")).sendKeys("dress");
        driverEdge.findElement(By.name("submit_search")).click();

        List<WebElement> expected = driverEdge.findElements(By.xpath("//li[contains(@class, 'ajax_block_product')]"));

        System.out.println(expected.size());
        assertEquals(expected.size(), 7);
    }

    @Test
    public void testSubmitNewsletterAlreadyRegisteredInChrome(){
        driverChrome.findElement(By.xpath("//input[@class='inputNew form-control grey newsletter-input']")).sendKeys("abc@gmail.pl");
        driverChrome.findElement(By.xpath("//button[@name='submitNewsletter']")).click();

        String text = driverChrome.findElement(By.xpath("//p[@class='alert alert-danger']")).getText();
        assertEquals(text,"Newsletter : This email address is already registered.");
    }

    @Test
    public void testSubmitNewsletterAlreadyRegisteredInEdge(){
        driverEdge.findElement(By.xpath("//input[@class='inputNew form-control grey newsletter-input']")).sendKeys("abc@gmail.pl");
        driverEdge.findElement(By.xpath("//button[@name='submitNewsletter']")).click();

        String text = driverEdge.findElement(By.xpath("//p[@class='alert alert-danger']")).getText();
        assertEquals(text,"Newsletter : This email address is already registered.");
    }

    @Test
    public void testClickCheckBoxesChrome(){
        driverChrome.findElement(By.xpath("//a[@title='Women']")).click();

        driverChrome.findElement(By.xpath("//input[@name='layered_id_attribute_group_1']")).click();
        WebElement object = driverChrome.findElement(By.xpath("//input[@id='layered_id_feature_5']"));
        object.click();

        assertNotNull(object);
    }


    @Test
    public void testClickCheckBoxesEdge(){
        driverEdge.findElement(By.xpath("//a[@title='Women']")).click();

        driverEdge.findElement(By.xpath("//input[@name='layered_id_attribute_group_1']")).click();
        WebElement object = driverEdge.findElement(By.xpath("//input[@id='layered_id_feature_5']"));
        object.click();

        assertNotNull(object);
    }

}