import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Case2 {
    protected static WebDriver driver;
    private static Logger logger = LogManager.getLogger(Case2.class);

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

        // Нажатие кнопки всё верно для дальнейшего тестирования
        WebElement okay = driver.findElement(By.xpath("//span[text()='Всё верно']"));
        okay.click();

        // Добавление задержки Thread.sleep
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Перемещение курсора на Бытавую технику
        WebElement appliances = driver.findElement(By.xpath("//a[text()='Бытовая техника']"));
        Actions actions = new Actions(driver);
        actions
                .moveToElement(appliances)
                .perform();

        // Добавление задержки Thread.sleep
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверка на отображение ссылок (Техника для кухни, Техника для дома, Красота и здоровье)
        List<WebElement> listAppliances = driver.findElements(By.xpath("//a[@class ='ui-link menu-desktop__first-level']"));
        List<String> listAppliancesText = new ArrayList<>();
        for (WebElement element : listAppliances) {
            listAppliancesText.add(element.getText());
        }
        List<String> listAppliancesTest = new ArrayList<>();
        listAppliancesTest.add("Техника для кухни");
        listAppliancesTest.add("Техника для дома");
        listAppliancesTest.add("Красота и здоровье");
        Assertions.assertTrue(listAppliancesText.equals(listAppliancesTest));
        logger.info("Ссылки (Техника для кухни, Техника для дома, Красота и здоровье) отображаются!");

        // Перемещение курсор на Приготовление пищи
        WebElement cooking = driver.findElement(By.xpath("//a[text() ='Приготовление пищи']"));
        Actions actionsCooking= new Actions(driver);
        actionsCooking
                .moveToElement(cooking)
                .perform();

        // Добавление задержки Thread.sleep
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверка колиечства ссылок в подменю 'Приготовление пищи' больше 5
        List<WebElement> listCooking = driver.findElements(By.xpath("//a[@class ='ui-link menu-desktop__popup-link']"));
        Assertions.assertTrue(listCooking.size()>5);
        logger.info("Количество ссылок в подменю 'Приготовление пищи' больше 5");

        // Перемещаем курсор на плиты и кликаем
        WebElement stove = driver.findElement(By.xpath("//a[text()='Плиты']"));
        Actions actionsStove = new Actions(driver);
        actionsStove
                .moveToElement(stove)
                .click()
                .perform();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Кликаем на плиты электрические
        WebElement electricStove = driver.findElement(By.xpath("//span[text()='Плиты электрические']"));
        electricStove.click();

        // Проверка, что в тексте Плиты электрические [количество] товаров количество товаров больше 100
        String stoveCountText = driver.findElement(By.className("products-count")).getText();
        String[] stoveCountArray =stoveCountText.split("\\s");
        int stoveCount = Integer.parseInt(stoveCountArray[0].trim());
        Assertions.assertTrue(stoveCount > 100,"Количество товара в разделе 'Плиты электрические' меньше или равно 100.");
        logger.info("Количество товара 'Плиты электрические' больше 100!");

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

