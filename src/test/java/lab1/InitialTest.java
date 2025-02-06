package lab1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

class InitialTest {
    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void beforeEach() {
        driver = new FirefoxDriver();
    }

    @AfterEach
    void afterAll() {
        driver.quit();
    }

    @Test
    void checkOutGoogle() {
        driver.get("https://lukesmith.xyz/");
        var element = driver.findElement(By.cssSelector("[id*='tag_luke'"));
        Assertions.assertEquals("Luke Smith's Webpage", element.getText());
    }
}
