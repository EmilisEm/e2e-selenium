package lab1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

class Lab1Test {
  private WebDriver driver;
  private DemoShopPage demoShopPage;

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.firefoxdriver().setup();
  }

  @BeforeEach
  void beforeEach() {
    driver = new FirefoxDriver();
    demoShopPage = new DemoShopPage(driver);
    demoShopPage.openPage();
  }

  @AfterEach
  void afterAll() {
    driver.quit();
  }

  @Test
  void lab1() {
    demoShopPage.clickLinkInsideElementWithSelector(
        By.cssSelector("[class*=leftside]"), "Gift Cards");
    demoShopPage.openFirstProductWithPriceHigherThan("99");
    demoShopPage.fillInInputByClassWithValue("recipient-name", "recipient");
    demoShopPage.fillInInputByClassWithValue("sender-name", "sender");
    demoShopPage.fillInInputByClassWithValue("qty-input", "5000");
    demoShopPage.clickButtonInItemOverviewByText("Add to cart");
    demoShopPage.waitUntilElementDisplayed(By.cssSelector("[id='bar-notification']"));
    demoShopPage.clickButtonInItemOverviewByText("Add to wishlist");
    demoShopPage.clickLinkInsideElementWithSelector(By.cssSelector("[class*=side]"), "Jewelry");
    demoShopPage.clickButtonByText("Create Your Own Jewelry");

    var materialSelect = new Select(driver.findElement(By.tagName("select")));
    materialSelect.selectByVisibleText("Silver (1 mm)");

    demoShopPage.fillInInputByClassWithValue("textbox", "5000");
    demoShopPage.clickRadioButtonWithTextInProductOverview("Star ");
    demoShopPage.fillInInputByClassWithValue("qty-input", "26");
    demoShopPage.clickButtonInItemOverviewByText("Add to cart");
    demoShopPage.waitUntilElementDisplayed(By.cssSelector("[id='bar-notification']"));
    demoShopPage.clickButtonInItemOverviewByText("Add to wishlist");
    demoShopPage.clickWishlistLink();
    demoShopPage.clickAllAddToCartCheckboxes();
    demoShopPage.clickInputByValue("Add to cart");

    demoShopPage.validateSubTotalInCartOverview("1002600.00");
  }
}
