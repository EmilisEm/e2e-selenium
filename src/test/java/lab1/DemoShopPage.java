package lab1;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        .getFirst()
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
}
