package gui.helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by a.oreshnikova on 28.11.2017.
 */

public class DriverHelper {

    //Инициализация драйвера
    public void init() {
        System.out.println("Открываем браузер");
        //В папке resources два драйвера: для Win и Mac OS
        String os = System.getProperty("os.name").toLowerCase();
        if (os.equals("windows")){
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        } else if (os.equals("mac")){
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        }
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        Configuration.timeout = 20000; //Большой дефолтный таймаут из-за медленной загрузки страниц на моем компьтере
        baseUrl = "https://ru.semrush.com";
    }

    public void quit() {
        WebDriverRunner.closeWebDriver();
        System.out.println("Браузер закрыт");
    }

    //Страницы
    public void openStartPage() {
        open(baseUrl);
    }

    public void openNotesPage() {
        open("/notes");
    }

    public void openDashboardPage() {
        open("/dashboard");
    }
}
