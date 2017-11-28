package tests.gui;

import com.sun.org.glassfish.gmbal.Description;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.gui.forms.LoginForm;
import tests.gui.helpers.DriverHelper;
import tests.gui.helpers.LoginHelper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by a.oreshnikova on 28.11.2017.
 */
public class SomeTest {

    private static DriverHelper driver;
    private static LoginHelper login;

    @BeforeClass
    public static void start() {
        driver = new DriverHelper();
        driver.init();
        login = new LoginHelper();
    }

    @Before
    public void goToStartPage() {
        driver.clearCookies();
        driver.goToPage();
    }

    @Description("Тест 1: залониться под пользователем в SEMrush")
    @Test
    public void loginOnSite_Test() {
        login.login(new LoginForm("piter_ao12@mail.ru", "1560256967Hi"));
        assertThat(login.isAuthSuccess())
                .as("Возникла ошибка при авторизации!")
                .isTrue();
    }

    @Description("Тест 2: создайте новую заметку на /users/notes")
    @Test
    public void test_2() {}

    @Description("Тест 3: создайте новый проект с /dashboard страницы")
    @Test
    public void test_3() {}

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
