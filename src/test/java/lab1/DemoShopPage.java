package lab1;

import common.CommonBrowserActions;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DemoShopPage {
  private final WebDriver driver;
  private final CommonBrowserActions commonBrowserActions;

  public DemoShopPage(WebDriver driver) {
    this.driver = driver;
    this.commonBrowserActions = new CommonBrowserActions(driver);
  }

  public void openPage() {
    driver.get("https://demowebshop.tricentis.com/");
  }

  public void openFirstProductWithPriceHigherThan(String price) {
    driver
        .findElements(
            By.xpath(
                "//div[@class='item-box' and .//span > %s]//h2[@class='product-title']"
                    .formatted(price)))
        .get(0)
        .click();
  }

  public void clickButtonInItemOverviewByText(String text) {
    var container = driver.findElement(By.cssSelector("[class='overview']"));

    commonBrowserActions.clickInputByValue(container, text);
  }

  public void clickRadioButtonWithTextInProductOverview(String text) {
    var overview = driver.findElement(By.cssSelector("[class='overview']"));

    overview.findElement(By.xpath("//li[./label/text()='%s']/input".formatted(text))).click();
  }

  public void clickWishlistLink() {
    driver.findElement(By.cssSelector("[class='wishlist-qty']")).click();
  }

  public void clickCartLink() {
    driver.findElement(By.cssSelector("[class='cart-qty']")).click();
  }

  public void clickAllAddToCartCheckboxes() {
    driver.findElements(By.xpath("//td[@class='add-to-cart']/input")).forEach(WebElement::click);
  }

  public void validateSubTotalInCartOverview(String expectedTotal) {
    var totalTableBody = driver.findElement(By.xpath("//table[@class='cart-total']/tbody"));
    var subTotalRow =
        totalTableBody.findElement(
            By.xpath("//tr[./td[@class='cart-total-left']/span='Sub-Total:']"));
    var subTotal = subTotalRow.findElement(By.xpath("//td[@class='cart-total-right']"));

    Assertions.assertEquals(expectedTotal, subTotal.getText());
  }

  public void addToCartFirstElementWithTitle(String title) {
    driver
        .findElements(
            By.xpath(
                "//div[@class='item-box' and .//h2/a/text()='%s']//input[@value='Add to cart']"
                    .formatted(title)))
        .get(0)
        .click();
  }

  public void selectFirstBillingAddressOrFillInNewOne() {
    var billingAddressElement = driver.findElements(By.id("billing-address-select"));
    var continueButton =
        driver.findElement(By.cssSelector("[class*=new-address-next-step-button]"));
    if (billingAddressElement.isEmpty()) {
      var countrySelect = new Select(driver.findElement(By.id("BillingNewAddress_CountryId")));
      countrySelect.selectByVisibleText("United States");

      commonBrowserActions.fillInInputById("BillingNewAddress_City", "a");
      commonBrowserActions.fillInInputById("BillingNewAddress_Address1", "a");
      commonBrowserActions.fillInInputById("BillingNewAddress_ZipPostalCode", "a");
      commonBrowserActions.fillInInputById("BillingNewAddress_PhoneNumber", "a");
    } else if (billingAddressElement.size() > 1) {
      Assertions.fail("More than one billing address select found");
    }

    continueButton.click();
    commonBrowserActions.waitUntilElementDisplayed(By.id("checkout-step-payment-method"));
  }

  public void selectPaymentMethod() {
    var continueButton =
        driver.findElement(By.cssSelector("[class*=payment-method-next-step-button]"));
    continueButton.click();
    commonBrowserActions.waitUntilElementDisplayed(By.id("checkout-step-payment-info"));
  }

  public void selectPaymentInfo() {
    var continueButton =
        driver.findElement(By.cssSelector("[class*=payment-info-next-step-button]"));
    continueButton.click();
    commonBrowserActions.waitUntilElementDisplayed(By.id("checkout-step-confirm-order"));
  }

  public void confirmOrder() {
    var continueButton =
        driver.findElement(By.cssSelector("[class*=confirm-order-next-step-button]"));
    continueButton.click();
  }
}
