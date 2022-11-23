import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class CaseThreeTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(CaseThreeTest.class);
    private static int counter = 0;

    // Чтение передаваемого параметра browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome");

    // Чтение передаваемого параметра pageLoadStrategy (-Dloadstrategy)
    String pageLoadStrategy = System.getProperty("loadstrategy", "normal");

    // Метод для скриншота всей страницы
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
        driver = WebDriverFactory.getDriver(env.toLowerCase(), pageLoadStrategy.toLowerCase());
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void testCaseThree() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Selenium4Listener listener = new Selenium4Listener();
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriver eventFiringWebDriver = new EventFiringDecorator<>(listener).decorate(driver);

        // Открытие сайта DNS
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        // Скриншот всей страницы
        makeScreenshot();

        // Нажатие кнопки всё верно для дальнейшего тестирования
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Всё верно']")));
        WebElement okay = driver.findElement(By.xpath("//span[text()='Всё верно']"));
        wait.until(ExpectedConditions.elementToBeClickable(okay));
        okay.click();

        // Обновление страницы
        driver.navigate().refresh();

        // Перемещение курсора на ПК, ноутбуки, периферия
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='ПК, ноутбуки, периферия']")));
        WebElement computerLink = eventFiringWebDriver.findElement(By.xpath("//a[text()='ПК, ноутбуки, периферия']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='ПК, ноутбуки, периферия']")));
        actions
                .moveToElement(computerLink)
                .perform();

        // Скриншот всей страницы
        makeScreenshot();

        // Переход по ссылке Ноутбуки
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text() = 'Ноутбуки']")));
        WebElement linkLaptop = eventFiringWebDriver.findElement(By.xpath("//a[text() = 'Ноутбуки']"));
        wait.until(ExpectedConditions.elementToBeClickable(linkLaptop));
        linkLaptop.click();

        // Скриншот всей страницы
        makeScreenshot();

        // Скрыть блок страницы
        js.executeScript("document.querySelector('header').style.display='none'");

        // Скриншот всей страницы
        makeScreenshot();

        // Выбор производителя - 'ASUS'
        js.executeScript("window.scrollBy(0,600)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'ASUS  ']")));
        WebElement manufacturerCheckbox = eventFiringWebDriver.findElement(By.xpath("//span[text() = 'ASUS  ']"));
        actions
                .scrollToElement(manufacturerCheckbox)
                .click(manufacturerCheckbox)
                .perform();

        // Выбор объёма оперативной памяти - '32 ГБ'
        By ramChecklistXpath = By.xpath("//span[text() = 'Объем оперативной памяти (ГБ)']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(ramChecklistXpath));
        WebElement ramChecklist = eventFiringWebDriver.findElement(ramChecklistXpath);
        wait.until(ExpectedConditions.elementToBeClickable(ramChecklist));
        actions
                .scrollToElement(ramChecklist)
                .click(ramChecklist)
                .perform();

        js.executeScript("window.scrollBy(0,600)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = '32 ГБ  ']")));
        WebElement ramCheckbox = eventFiringWebDriver.findElement(By.xpath("//span[text() = '32 ГБ  ']"));
        wait.until(ExpectedConditions.elementToBeClickable(ramCheckbox));
        actions
                .scrollToElement(ramCheckbox)
                .click(ramCheckbox)
                .perform();

        // Нажатие кнопки применить
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = 'Применить']")));
        WebElement applyButton = eventFiringWebDriver.findElement(By.xpath("//button[text() = 'Применить']"));
        wait.until(ExpectedConditions.elementToBeClickable(applyButton));
        actions
                .scrollToElement(applyButton)
                .click(applyButton)
                .perform();


        // Скриншот всей страницы
        makeScreenshot();

        // Применение сортировки - 'Сначала дорогие'
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Сортировка:']")));
        WebElement sortSelectButton = eventFiringWebDriver.findElement(By.xpath("//span[text()='Сортировка:']"));
        wait.until(ExpectedConditions.elementToBeClickable(sortSelectButton));
        actions
                .scrollToElement(sortSelectButton)
                .click(sortSelectButton)
                .perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'Сначала дорогие']")));
        WebElement sortRadioButton = eventFiringWebDriver.findElement(By.xpath("//span[text() = 'Сначала дорогие']"));
        wait.until(ExpectedConditions.elementToBeClickable(sortRadioButton));
        actions
                .scrollToElement(sortRadioButton)
                .click(sortRadioButton)
                .perform();

        // Скриншот всей страницы
        makeScreenshot();

        // Получение названия ноутбука для проверки на соответствие
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class = 'catalog-product__name ui-link ui-link_black'][1]")));
        WebElement firstLaptopLink = eventFiringWebDriver.findElement(By.xpath("//a[@class = 'catalog-product__name ui-link ui-link_black'][1]"));
        String firstLaptopLinkText = firstLaptopLink.getText();

        // Переход на новую страницу первого продукта в максимизированном режиме
        String URL = firstLaptopLink.getAttribute("href");
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.manage().window().maximize();
        driver.get(URL);

        // Скриншот всей страницы
        makeScreenshot();

        // Проверка, что заголовок страницы соответствует назавнию в списке предыдущей страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class = 'product-card-top__title']")));
        WebElement pageTitle = eventFiringWebDriver.findElement(By.xpath("//h1[@class = 'product-card-top__title']"));
        String pageTitleText = pageTitle.getText();
        Assertions.assertTrue(firstLaptopLinkText.contains(pageTitleText), "Заголовок не соответствует ожидаемому");

        // Открытие списка характеристик
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() = 'Развернуть все']")));
        WebElement characteristicsButton = eventFiringWebDriver.findElement(By.xpath("//button[text() = 'Развернуть все']"));
        wait.until(ExpectedConditions.elementToBeClickable(characteristicsButton));
        actions
                .scrollToElement(characteristicsButton)
                .click(characteristicsButton)
                .perform();

        // Проверка, что заголовк характеристик содержит ASUS;
        wait.until(ExpectedConditions.visibilityOf(eventFiringWebDriver.findElement(By.xpath("//div[@class='product-card-description__title']"))));
        WebElement characteristicsTitle = eventFiringWebDriver.findElement(By.xpath("//div[@class='product-card-description__title']"));
        Assertions.assertTrue(characteristicsTitle.getText().contains("ASUS"), "Заголовок характеристик не содержит 'ASUS'");


        // Проверка, что объем оперативной памяти равен 32 ГБ
        wait.until(ExpectedConditions.visibilityOf(eventFiringWebDriver.findElement(By.xpath("//div[text() = ' Объем оперативной памяти ']/following-sibling::div"))));
        WebElement laptopRamText = eventFiringWebDriver.findElement(By.xpath("//div[text() = ' Объем оперативной памяти ']/following-sibling::div"));
        actions.scrollToElement(laptopRamText)
                .perform();
        Assertions.assertEquals(laptopRamText.getText(), "32 ГБ", "В характеристиках значение 'Объем оперативной памяти' не равно 32 ГБ");
    }

    @AfterEach
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
}