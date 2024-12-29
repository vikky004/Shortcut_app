import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTestCase {
    public static void main(String[] args) {
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://seleniumbase.io/demo_page");
        WebElement ele = webDriver.findElement(By.id("myTextInput"));
        ele.sendKeys("Hello");
        WebElement ele2 = webDriver.findElement(By.id("myTextInput2"));
        System.out.println(ele2.getAttribute("title") + " " + ele2.getAttribute("value"));
        webDriver.quit();
    }
}
