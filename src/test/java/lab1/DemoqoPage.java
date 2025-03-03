package lab1;

import common.CommonBrowserActions;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@RequiredArgsConstructor
public class DemoqoPage {
  private final WebDriver driver;
  private CommonBrowserActions commonBrowserActions;

  public void openPage() {
    driver.get("http://web.archive.org/web/20240208220128/https://demoqa.com/");
    commonBrowserActions = new CommonBrowserActions(driver);
  }

  public Consumer<Void> getCreateUserForDemoqoAction() {
    return (temp) -> {
      commonBrowserActions.clickElementContainingText(HtmlElement.BUTTON, "Add");
      commonBrowserActions.fillInInputById("firstName", "a");
      commonBrowserActions.fillInInputById("lastName", "a");
      commonBrowserActions.fillInInputById("userEmail", "a@a.aa");
      commonBrowserActions.fillInInputById("age", "1");
      commonBrowserActions.fillInInputById("salary", "1");
      commonBrowserActions.fillInInputById("department", "a");
      commonBrowserActions.clickElementContainingText(HtmlElement.BUTTON, "Submit");
    };
  }

  public void clickFirstDeleteButtonInUserList() {
    driver.findElements(By.cssSelector("[id*='delete-record']")).getFirst().click();
  }

  public void validatePageCount(int expectedPageCount) {
    driver.findElement(
        By.xpath("//span[@class='-totalPages' and text() = '%d']".formatted(expectedPageCount)));
  }

  public void validateCurrentPageCount(int expectedPageCount) {
    driver.findElement(
        By.xpath(
            "//input[@aria-label='jump to page' and @value='%d']".formatted(expectedPageCount)));
  }
}
