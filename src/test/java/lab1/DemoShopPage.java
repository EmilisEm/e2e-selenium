package lab1;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@RequiredArgsConstructor
public class DemoShopPage {
  private final WebDriver driver;

  public void openPage() {
    driver.get("https://demowebshop.tricentis.com/");
  }

  public void clickLinkInsideElementWithSelector(By selector, String link) {
    var container = driver.findElement(selector);

    clickButtonInsideElementByText(container, link);
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

  public void fillInInputByClassWithValue(String className, String value) {
    var input = driver.findElement(By.cssSelector("[class='%s']".formatted(className)));
    input.clear();
    input.sendKeys(value);
  }

  public void clickButtonInItemOverviewByText(String text) {
    var container = driver.findElement(By.cssSelector("[class='overview']"));

    clickInputByValue(container, text);
  }

  public void clickButtonByText(String text) {
    driver.findElement(By.xpath("//a[contains(text(), '%s')]".formatted(text))).click();
  }

  public void clickRadioButtonWithTextInProductOverview(String text) {
    var overview = driver.findElement(By.cssSelector("[class='overview']"));

    overview.findElement(By.xpath("//li[./label/text()='%s']/input".formatted(text))).click();
  }

  public void clickWishlistLink() {
    driver.findElement(By.cssSelector("[class='wishlist-qty']")).click();
  }

  public void clickShoppingCartLink() {
    driver.findElement(By.cssSelector("[class='cart-qty']")).click();
  }

  public void clickAllAddToCartCheckboxes() {
    driver.findElements(By.xpath("//td[@class='add-to-cart']/input")).forEach(WebElement::click);
  }

  public void clickInputByValue(String text) {
    driver.findElement(By.xpath("//input[@value=\"%s\"]".formatted(text))).click();
  }

  public void waitUntilElementDisplayed(By selector) {
    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    wait.until(it -> it.findElement(selector).isDisplayed());
  }

  private void clickInputByValue(WebElement element, String text) {
    element.findElement(By.xpath("//input[@value='%s']".formatted(text))).click();
  }

  public void validateSubTotalInCartOverview(String expectedTotal) {
    var totalTableBody = driver.findElement(By.xpath("//table[@class='cart-total']/tbody"));
    var subTotalRow = totalTableBody.findElement(By.xpath("//tr[./td[@class='cart-total-left']/span='Sub-Total:']"));
    var subTotal = subTotalRow.findElement(By.xpath("//td[@class='cart-total-right']"));

    Assertions.assertEquals(expectedTotal, subTotal.getText());
  }

  private void clickButtonInsideElementByText(WebElement element, String text) {
    element.findElement(By.linkText(text)).click();
  }
}
