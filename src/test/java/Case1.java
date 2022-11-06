import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Case1 {
    protected static WebDriver driver;
    private static Logger logger = LogManager.getLogger(Case1.class);

    // Чтение передаваемого параметра browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");

    // Чтение передаваемого параметра pageLoadStrategy (-Dloadstrategy)
    String loadStrategy = System.getProperty("loadstrategy", "normal");


    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        logger.info("loadStrategy = " + loadStrategy);
        driver = WebDriverFactory.getDriver(env.toLowerCase(),loadStrategy.toLowerCase());
        driver.manage().window().maximize();
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void browserWindowsTest3() {
        // Ожидание загрузки страницы в 60 секунд
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
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
        WebElement okay = driver.findElement(By.xpath("//span[text()='Всё верно']"));
        okay.click();

        // Добавление задержки Thread.sleep
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Переход по ссылке Бытовая техника
        WebElement appliances = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        appliances.click();

        // Проверка отображения текста Бытовая техника
        WebElement textAppliances = driver.findElement(By.className("subcategory__page-title"));
        Assertions.assertTrue(textAppliances.getText().equals("Бытовая техника"));
        logger.info("Текст- 'Бытовая техника' отображен!");

        // Переход по ссылке Техника для кухни
        WebElement kitchenAppliances = driver.findElement(By.xpath("//span[text()='Техника для кухни']"));
        kitchenAppliances.click();

        // Проверка отображения текста Техника для кухни
        WebElement textKitchenAppliances = driver.findElement(By.className("subcategory__page-title"));
        Assertions.assertTrue(textKitchenAppliances.getText().equals("Техника для кухни"));
        logger.info("Текст 'Техника для кухни' отображен!");

        // Проверка на отображения ссылки Собрать свою кухню
        WebElement textMakeKitchen = driver.findElement(By.xpath("//a[text()='Собрать свою кухню']"));
        Assertions.assertTrue(textMakeKitchen.getText().equals("Собрать свою кухню"));
        logger.info("Ссылка 'Собрать свою кухню' отображена!");

        // Вывод в логи названия всех категорий страницы 'Техника для кухни'
        List<WebElement> elementsKitchenAppliances = driver.findElements(By.className("subcategory__title"));
        logger.info("Техника для кухни:");
        for (WebElement element : elementsKitchenAppliances){
            logger.info(element.getText());
        }

        // Проверка, что количество категорий больше 5
        Assertions.assertTrue(elementsKitchenAppliances.size()>5);
        logger.info("Количество категорий больше 5");

        // Добавление задержки Thread.sleep, чтобы увидеть результат
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }

}

