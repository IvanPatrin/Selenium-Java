import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CaseTwoTest {
    protected static WebDriver driver;
    private static Logger logger = LogManager.getLogger(CaseTwoTest.class);
    private static int counter = 0;

    // Чтение передаваемого параметра browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");

    // Чтение передаваемого параметра pageLoadStrategy (-Dloadstrategy)
    String loadStrategy = System.getProperty("loadstrategy", "normal");

    public void makeScreenshot() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            counter++;
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\SecondCaseScreenshot-" + counter + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        js.executeScript("window.scrollTo(0,0)");
    }

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        logger.info("loadStrategy = " + loadStrategy);
        driver = WebDriverFactory.getDriver(env.toLowerCase(),loadStrategy.toLowerCase());
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void testCaseTwo() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        // Нажатие кнопки всё верно для дальнейшего тестирования
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Всё верно']")));
        WebElement okay = driver.findElement(By.xpath("//span[text()='Всё верно']"));
        wait.until(ExpectedConditions.elementToBeClickable(okay));
        okay.click();
        makeScreenshot();


        // Перемещение курсора на Бытавую технику
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Бытовая техника']")));
        WebElement linkAppliances = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        Actions actions = new Actions(driver);
        actions
                .moveToElement(linkAppliances)
                .perform();
        makeScreenshot();

        actions
                .moveToElement(linkAppliances)
                .perform();

        // Проверка на отображение ссылок (Техника для кухни, Техника для дома, Красота и здоровье)
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@class ='ui-link menu-desktop__first-level']")));
        List<WebElement> listLinksAppliances = driver.findElements(By.xpath("//a[@class ='ui-link menu-desktop__first-level']"));
        List<String> listAppliancesText = new ArrayList<>();
        for (WebElement element : listLinksAppliances) {
            listAppliancesText.add(element.getText());
        }
        List<String> listLinksAppliancesTest = new ArrayList<>();
        listLinksAppliancesTest.add("Техника для кухни");
        listLinksAppliancesTest.add("Техника для дома");
        listLinksAppliancesTest.add("Красота и здоровье");
        Assertions.assertEquals(listAppliancesText, listLinksAppliancesTest, "Ссылки (Техника для кухни, Техника для дома, Красота и здоровье) не отображаются!");
        logger.info("Ссылки (Техника для кухни, Техника для дома, Красота и здоровье) отображаются!");

        // Перемещение курсор на Приготовление пищи
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text() ='Приготовление пищи']")));
        WebElement linkCooking = driver.findElement(By.xpath("//a[text() ='Приготовление пищи']"));
        Actions actionsCooking= new Actions(driver);
        actionsCooking
                .moveToElement(linkCooking)
                .perform();
        makeScreenshot();
        actionsCooking
                .moveToElement(linkAppliances)
                .moveToElement(linkCooking)
                .perform();

        // Проверка колиечства ссылок в подменю 'Приготовление пищи' больше 5
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[@class ='ui-link menu-desktop__popup-link']")));
        List<WebElement> listCooking = driver.findElements(By.xpath("//a[@class ='ui-link menu-desktop__popup-link']"));
        Assertions.assertTrue(listCooking.size()>5, "Количество ссылок в подменю 'Приготовление пищи' меньше или равно 5'");
        logger.info("Количество ссылок в подменю 'Приготовление пищи' больше 5");

        // Перемещаем курсор на плиты и кликаем
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Плиты']")));
        WebElement linkStove = driver.findElement(By.xpath("//a[text()='Плиты']"));
        wait.until(ExpectedConditions.elementToBeClickable(linkStove));
        Actions actionsStove = new Actions(driver);
        actionsStove
                .moveToElement(linkStove)
                .click()
                .perform();
        makeScreenshot();

        // Кликаем на плиты электрические
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Плиты электрические']")));
        WebElement linkElectricStove = driver.findElement(By.xpath("//span[text()='Плиты электрические']"));
        wait.until(ExpectedConditions.elementToBeClickable(linkElectricStove));
        linkElectricStove.click();
        makeScreenshot();

        // Проверка, что в тексте Плиты электрические [количество] товаров количество товаров больше 100
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("products-count")));
        String stoveCountText = driver.findElement(By.className("products-count")).getText();
        String[] stoveCountArray =stoveCountText.split("\\s");
        int stoveCount = Integer.parseInt(stoveCountArray[0].trim());
        Assertions.assertTrue(stoveCount > 100,"Количество товара в разделе 'Плиты электрические' меньше или равно 100.");
        logger.info("Количество товара 'Плиты электрические' больше 100!");
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

}

