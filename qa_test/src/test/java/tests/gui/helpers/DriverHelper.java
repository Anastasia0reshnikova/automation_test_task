package tests.gui.helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by a.oreshnikova on 28.11.2017.
 */
public class DriverHelper {

    public void init() {
        System.out.println("Открываем браузер");
        //В папке resources два драйвера: для Win и Mac OS
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        Configuration.timeout = 10000;
    }

    public void goToPage() {
        open("https://ru.semrush.com");
    }

    public void quit() {
        WebDriverRunner.closeWebDriver();
        System.out.println("Браузер закрыт");
    }

    public void clearCookies() {
        Selenide.clearBrowserCookies();
    }
}
