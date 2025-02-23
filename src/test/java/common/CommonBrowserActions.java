package common;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import lab1.HtmlElement;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@RequiredArgsConstructor
public class CommonBrowserActions {
  private final WebDriver driver;

  public void clickLinkInsideElementWithSelector(By selector, String link) {
    var container = driver.findElement(selector);

    clickButtonInsideElementByText(container, link);
  }

  public void fillInInputByClassWithValue(String className, String value) {
    var input = driver.findElement(By.cssSelector("[class='%s']".formatted(className)));
    input.clear();
    input.sendKeys(value);
  }

  public void fillInInputById(String id, String value) {
    var input = driver.findElement(By.cssSelector("[id='%s']".formatted(id)));

    input.sendKeys(value);
  }

  public void clickElementContainingText(HtmlElement element, String text) {
    var elementsContainingText = getElementsContainingText(element, text);
    Assertions.assertEquals(1, elementsContainingText.size());

    elementsContainingText.getFirst().click();
  }

  public List<WebElement> getElementsContainingText(HtmlElement element, String text) {
    return driver.findElements(
        By.xpath("//%s[contains(text(), '%s')]".formatted(element.getValue(), text)));
  }

  public void clickLinkByText(String text) {
    driver.findElement(By.xpath("//a[contains(text(), '%s')]".formatted(text))).click();
  }

  public void clickInputByValue(String text) {
    driver.findElement(By.xpath("//input[@value=\"%s\"]".formatted(text))).click();
  }

  public void clickInputByValue(WebElement element, String text) {
    element.findElement(By.xpath("//input[@value='%s']".formatted(text))).click();
  }

  public void clickElementById(String id) {
    driver.findElement(By.id(id)).click();
  }

  public List<WebElement> getElementsContainingText(String text) {
    return driver.findElements(By.xpath("//*[text()[contains(., '%s')]]".formatted(text)));
  }

  public void waitUntilTextIsPresent(String text) {
    waitUntilElementDisplayed(By.xpath("//*[text()[contains(., '%s')]]".formatted(text)));
  }

  public void performActionUntilElementDisplayed(By selector, Consumer<Void> callback) {
    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    wait.until(
        it -> {
          callback.accept(null);
          return it.findElement(selector).isDisplayed();
        });
  }

  public void waitUntilElementDisplayed(By selector) {
    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    wait.until(it -> it.findElement(selector).isDisplayed());
  }

  public void waitUntilElementNotDisplayed(By selector) {
    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    wait.until(it -> !it.findElement(selector).isDisplayed());
  }

  public void scrollElementIntoView(WebElement element) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
  }

  private void clickButtonInsideElementByText(WebElement element, String text) {
    element.findElement(By.linkText(text)).click();
  }
}
