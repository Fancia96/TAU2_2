
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class TauTestFour {
    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        System.setProperty("webdriver.chrome.drive", System.getProperty("user.dir") + "/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp(){
        driver.get("https://www.kurnik.pl/login.phtml");
    }

    @AfterAll
    public static void tearDown(){
        driver.quit();
    }

    @Test
    public void testLogin() throws InterruptedException {
        WebElement username=driver.findElement(By.name("username"));
        WebElement password=driver.findElement(By.name("pw"));
        username.sendKeys("Wanda96");
        password.sendKeys("123qwerty");
        WebElement login=driver.findElement(By.xpath("//button[@class='bxpad ttup']"));
        login.click();
        String actualUrl="https://www.kurnik.pl/";
        String expectedUrl= driver.getCurrentUrl();
        Thread.sleep(1000);
        Assert.assertEquals(expectedUrl,actualUrl);

    }

    @Test
    public void testCheckingFailedLogin() {
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("pw"));
        username.sendKeys("Wanda");
        password.sendKeys("123qwerty");
        WebElement login = driver.findElement(By.xpath("//button[@class='bxpad ttup']"));
        login.click();
        String expected = driver.findElement(By.xpath("//p[@class='fb']")).getText();
        String actual = "nieprawidłowa nazwa użytkownika lub hasło";
        Assert.assertEquals(expected,actual);

    }
}