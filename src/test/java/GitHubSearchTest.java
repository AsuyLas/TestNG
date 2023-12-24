import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class GitHubSearchTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://github.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(groups = {"smoke", "regression"})
    public void testSearch() {
        String searchTerm = System.getProperty("Selenium");
        String expectedText = System.getProperty("Selenium documentation");

        if (searchTerm == null || expectedText == null) {
            throw new IllegalArgumentException("Please provide searchTerm and expectedText");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search_form > .header-search > input")));

        searchInput.sendKeys(searchTerm);

        WebElement searchButton = driver.findElement(By.cssSelector("#search_form > button"));
        searchButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search_results > ul > li > h3")));

        WebElement result = driver.findElement(By.cssSelector("#search_results > ul > li > h3"));
        Assert.assertEquals(expectedText, result.getText(), "Неверный текст в результатах поиска");
    }
}
