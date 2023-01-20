import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TauTestThree {

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
    public void testCheckLinksInAPage() {
        driver.get("https://mojewypieki.com/");
        //List<WebElement> linkList = driver.findElements(By.xpath("//a[@href='lazy']"));
        List<WebElement> linkList = driver.findElements(By.xpath("//figure[@class='lazy']"));

        System.out.println(linkList.size());

        for (int i = 0; i < linkList.size(); i++) {
            if(!(linkList.get(i).getText().isEmpty()))
            {
                linkList.get(i).click();
                driver.navigate().back();

                linkList=driver.findElements(By.tagName("a"));
            }
        }

        System.out.println(linkList.size());
        assertThat(linkList.size(), greaterThan(10));

    }

    @Test
    public void testCountsLinks() {
        driver.get("https://duckduckgo.com/?q=przepis+na+ciasto&t=h_&ia=web");
        List<WebElement> links = driver.findElements(By.xpath("//a"));
        System.out.println(links.size());
        assertThat(links.size(), greaterThan(50));
    }

    @Test
    public void testCountTextFields() {

        driver.get("https://mojewypieki.com/");
        List<WebElement> listOfFields = driver.findElements(By.xpath("//input[@type='text']"));
        System.out.println(listOfFields.size());

        assertEquals(10, listOfFields.size());

    }

    @Test
    public void testNumberOfImagesOnASite() {
        driver.get("https://duckduckgo.com/?q=przepis+na+ciasto&t=h_&ia=web");

        List<WebElement> links = driver.findElements(By.xpath("//img"));
        System.out.println(links.size());
        assertThat(links.size(), greaterThan(30));
    }

}
