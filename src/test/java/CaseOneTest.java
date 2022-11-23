import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;

public class CaseOneTest {
    protected static WebDriver driver;
    private static Logger logger = LogManager.getLogger(CaseOneTest.class);
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
            ImageIO.write(screenshot.getImage(), "png", new File("temp\\FirstCaseScreenshot-" + counter + ".png"));
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
    public void testCaseOne() {

        // Ожидание загрузки страницы в 60 секунд
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        // Вывод в логи заголовка страницы
        String title = driver.getTitle();
        logger.info("Заголовок - " + title);

        // Вывод в логи текущего URL
        String currentUrl = driver.getCurrentUrl();
        logger.info("Текущий URL - " + currentUrl);

        // Вывод в логи размера окна браузера
        logger.info(String.format("Ширина окна: %d", driver.manage().window().getSize().getWidth()));
        logger.info(String.format("Высота окна: %d", driver.manage().window().getSize().getHeight()));

        // Нажатие кнопки всё верно для дальнейшего тестирования
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Всё верно']")));
        WebElement okay = driver.findElement(By.xpath("//span[text()='Всё верно']"));
        wait.until(ExpectedConditions.elementToBeClickable(okay));
        okay.click();
        makeScreenshot();

        // Переход по ссылке Бытовая техника;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Бытовая техника']")));
        WebElement linkAppliances = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        wait.until(ExpectedConditions.elementToBeClickable(linkAppliances));
        linkAppliances.click();
        makeScreenshot();

        // Проверка отображения текста Бытовая техника
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("subcategory__page-title")));
        WebElement textAppliances = driver.findElement(By.className("subcategory__page-title"));
        Assertions.assertEquals("Бытовая техника", textAppliances.getText(), "Текст Бытовая техника не отображается");
        logger.info("Текст- 'Бытовая техника' отображен!");

        // Переход по ссылке Техника для кухни
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Техника для кухни']")));
        WebElement linkKitchenAppliances = driver.findElement(By.xpath("//span[text()='Техника для кухни']"));
        wait.until(ExpectedConditions.elementToBeClickable(linkKitchenAppliances));
        linkKitchenAppliances.click();
        makeScreenshot();

        // Проверка отображения текста Техника для кухни
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("subcategory__page-title")));
        WebElement textKitchenAppliances = driver.findElement(By.className("subcategory__page-title"));
        Assertions.assertEquals("Техника для кухни",textKitchenAppliances.getText(), "Текст техника для кухни не отображается");
        logger.info("Текст 'Техника для кухни' отображен!");

        // Проверка на отображения ссылки Собрать свою кухню
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Собрать свою кухню']")));
        WebElement textMakeKitchen = driver.findElement(By.xpath("//a[text()='Собрать свою кухню']"));
        Assertions.assertEquals("Собрать свою кухню", textMakeKitchen.getText(), "Текст Собрать свою кухню не отображается");
        logger.info("Ссылка 'Собрать свою кухню' отображена!");

        // Вывод в логи названия всех категорий страницы 'Техника для кухни'
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("subcategory__title")));
        List<WebElement> elementsKitchenAppliances = driver.findElements(By.className("subcategory__title"));
        logger.info("Техника для кухни:");
        for (WebElement element : elementsKitchenAppliances){
            logger.info(element.getText());
        }

        // Проверка, что количество категорий больше 5
        Assertions.assertTrue(elementsKitchenAppliances.size()>5, "Количество категорий меньше 5");
        logger.info("Количество категорий больше 5");
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

}

