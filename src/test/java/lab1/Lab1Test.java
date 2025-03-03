package lab1;

import common.CommonBrowserActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

class Lab1Test {
  private WebDriver driver;
  private DemoShopPage demoShopPage;
  private CommonBrowserActions commonBrowserActions;
  DemoqoPage demoqoPage;

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.firefoxdriver().setup();
  }

  @BeforeEach
  void beforeEach() {
    var options = new FirefoxOptions();
    options.addArguments("--headless");
    driver = new FirefoxDriver(options);
    demoShopPage = new DemoShopPage(driver);
    commonBrowserActions = new CommonBrowserActions(driver);
    demoqoPage = new DemoqoPage(driver);
  }

  @AfterEach
  void afterAll() {
    driver.quit();
  }

  @Disabled
  @Test
  void lab1() {
    demoShopPage.openPage();
    commonBrowserActions.clickLinkInsideElementWithSelector(
        By.cssSelector("[class*=leftside]"), "Gift Cards");
    demoShopPage.openFirstProductWithPriceHigherThan("99");
    commonBrowserActions.fillInInputByClassWithValue("recipient-name", "recipient");
    commonBrowserActions.fillInInputByClassWithValue("sender-name", "sender");
    commonBrowserActions.fillInInputByClassWithValue("qty-input", "5000");
    demoShopPage.clickButtonInItemOverviewByText("Add to cart");
    commonBrowserActions.waitUntilElementDisplayed(By.cssSelector("[id='bar-notification']"));
    demoShopPage.clickButtonInItemOverviewByText("Add to wishlist");
    commonBrowserActions.clickLinkInsideElementWithSelector(
        By.cssSelector("[class*=side]"), "Jewelry");
    commonBrowserActions.clickLinkByText("Create Your Own Jewelry");

    var materialSelect = new Select(driver.findElement(By.tagName("select")));
    materialSelect.selectByVisibleText("Silver (1 mm)");

    commonBrowserActions.fillInInputByClassWithValue("textbox", "5000");
    demoShopPage.clickRadioButtonWithTextInProductOverview("Star ");
    commonBrowserActions.fillInInputByClassWithValue("qty-input", "26");
    demoShopPage.clickButtonInItemOverviewByText("Add to cart");
    commonBrowserActions.waitUntilElementDisplayed(By.cssSelector("[id='bar-notification']"));
    demoShopPage.clickButtonInItemOverviewByText("Add to wishlist");
    demoShopPage.clickWishlistLink();
    demoShopPage.clickAllAddToCartCheckboxes();
    commonBrowserActions.clickInputByValue("Add to cart");

    demoShopPage.validateSubTotalInCartOverview("1002600.00");
  }

  @Disabled
  @Test
  void lab2() {
    demoqoPage.openPage();
    commonBrowserActions.clickElementContainingText(HtmlElement.H5, "Widgets");
    commonBrowserActions.clickElementContainingText(HtmlElement.SPAN, "Progress Bar");
    commonBrowserActions.clickElementContainingText(HtmlElement.BUTTON, "Start");
    commonBrowserActions.waitUntilTextIsPresent("100%");
    commonBrowserActions.clickElementContainingText(HtmlElement.BUTTON, "Reset");
    commonBrowserActions.waitUntilTextIsPresent("0%");
  }

  @Disabled
  @Test
  void lab3() {
    demoqoPage.openPage();
    commonBrowserActions.clickElementContainingText(HtmlElement.H5, "Elements");
    commonBrowserActions.clickElementContainingText(HtmlElement.SPAN, "Web Tables");
    commonBrowserActions.performActionUntilElementDisplayed(
        By.xpath("//span[@class='-totalPages' and text() = '2']"),
        demoqoPage.getCreateUserForDemoqoAction());

    var nextButton =
        commonBrowserActions.getElementsContainingText(HtmlElement.BUTTON, "Next").get(0);
    nextButton.click();

    demoqoPage.clickFirstDeleteButtonInUserList();
    demoqoPage.validatePageCount(1);
    demoqoPage.validateCurrentPageCount(1);
  }
}
