package lab3;

import common.CommonBrowserActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lab1.DemoShopPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Lab3Test {
  private static final String USER_PASSWORD = "s3cr37";
  private static String USER_EMAIL;
  private static WebDriver driver;
  private static DemoShopPage demoShopPage;
  private static CommonBrowserActions commonBrowserActions;

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.firefoxdriver().setup();
    USER_EMAIL =
        "%s_testavimas@gmail.com"
            .formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("n")));

    initiateTestContext();
    commonBrowserActions.clickLinkByText("Log in");
    commonBrowserActions.clickInputByValue("Register");
    commonBrowserActions.fillInInputById("FirstName", "name");
    commonBrowserActions.fillInInputById("LastName", "surname");
    commonBrowserActions.fillInInputById("Email", USER_EMAIL);
    commonBrowserActions.fillInInputById("Password", USER_PASSWORD);
    commonBrowserActions.fillInInputById("ConfirmPassword", "s3cr37");
    commonBrowserActions.clickInputByValue("Register");

    driver.quit();
  }

  @BeforeEach
  void beforeEach() {
    initiateTestContext();
  }

  @AfterEach
  void afterAll() {
    driver.quit();
  }

  @ParameterizedTest
  @ValueSource(strings = {"data1.txt", "data2.txt"})
  void placeOrder(String fileName) {
    var data = loadDataFromFile(fileName);

    commonBrowserActions.clickLinkByText("Log in");
    commonBrowserActions.fillInInputById("Email", USER_EMAIL);
    commonBrowserActions.fillInInputById("Password", USER_PASSWORD);
    commonBrowserActions.clickInputByValue("Log in");

    commonBrowserActions.clickLinkInsideElementWithSelector(
        By.cssSelector("[class*=leftside]"), "Digital downloads");

    data.forEach(
        productTitle -> {
          demoShopPage.addToCartFirstElementWithTitle(productTitle);
          commonBrowserActions.waitUntilElementNotDisplayed(
              By.cssSelector("[class*='ajax-loading-block-window']"));
        });
    demoShopPage.clickCartLink();
    commonBrowserActions.clickElementById("termsofservice");
    commonBrowserActions.clickElementById("checkout");

    demoShopPage.selectFirstBillingAddressOrFillInNewOne();
    demoShopPage.selectPaymentMethod();
    demoShopPage.selectPaymentInfo();
    demoShopPage.confirmOrder();

    commonBrowserActions.waitUntilTextIsPresent("Your order has been successfully processed!");
  }

  private static void initiateTestContext() {
    driver = new FirefoxDriver();
    demoShopPage = new DemoShopPage(driver);
    commonBrowserActions = new CommonBrowserActions(driver);
    demoShopPage.openPage();
  }

  private List<String> loadDataFromFile(String fileName) {
    try {
      Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());

      return Files.readAllLines(path);
    } catch (URISyntaxException | IOException e) {
      Assertions.fail("Failed to load test data file");
    }
    return null;
  }
}
