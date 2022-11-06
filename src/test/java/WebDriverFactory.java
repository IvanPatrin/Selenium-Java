import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    // Возврат драйвера для конкретного браузера по его названию
    public static WebDriver getDriver(String browserName, String pageLoadStrategy) {
        switch (browserName) {
            // Создание драйвера для браузера Google Chrome
            case "chrome":
                WebDriverManager.chromedriver().setup();
                logger.info("Драйвер для браузера Google Chrome");
                ChromeOptions options = new ChromeOptions();
                options.setAcceptInsecureCerts(true);
                options.addArguments("--start-maximized");
                options.addArguments("--incognito");

                options.setCapability("pageLoadStrategy", getPageLoadStrategy(pageLoadStrategy));

                return new ChromeDriver(options);
            // Создание драйвера для браузера Mozilla Firefox
            case "firefox" :
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options1 =new FirefoxOptions();
                options1.addArguments("--kiosk");
                options1.addArguments("-private");
                options1.setCapability("pageLoadStrategy", getPageLoadStrategy(pageLoadStrategy));
                return new FirefoxDriver();
            // Ответ по умолчанию, если введено некорректное название браузера
            default:
                throw new RuntimeException("Введено некорректное название браузера");
        }
    }

    // Возврат стратегии загрузки страницы
    private static PageLoadStrategy getPageLoadStrategy(String pageLoadStrategy){
        switch (pageLoadStrategy.toLowerCase()){
            case "normal" :
                return PageLoadStrategy.NORMAL;
            case "none" :
                return PageLoadStrategy.NONE;
            case "eager" :
                return  PageLoadStrategy.EAGER;
            default:
                throw new RuntimeException("Введено некорректное название стратегии загрузки страницы");
        }
    }

}