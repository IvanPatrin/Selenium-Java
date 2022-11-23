import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class Selenium4Listener implements WebDriverListener {
    private Logger logger = LogManager.getLogger(Selenium4Listener.class);
    private static int counter = 0;

    @Override
    public void afterClick(WebElement element) {
        logger.info("Нажата кнопка");
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        logger.info("Найден веб элемент");
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            counter++;
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DNS_afterFindElement-" + counter + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        js.executeScript("window.scrollTo(0,0)");
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        logger.info("Найдены веб элементы");
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            counter++;
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\DNS_afterFindElements-" + counter + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        js.executeScript("window.scrollTo(0,0)");
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        logger.info("Получен текст");
    }
}