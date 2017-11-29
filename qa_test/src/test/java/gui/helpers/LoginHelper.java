package gui.helpers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import gui.forms.LoginForm;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by a.oreshnikova on 28.11.2017.
 */

public class LoginHelper {

    private SelenideElement buttonEnter = $(".header__navigation-login .s-btn__text"); //На главной странице
    private SelenideElement fillEmail = $(By.name("email"));
    private SelenideElement fillPassword = $(By.name("password"));
    private SelenideElement checkBox = $(".recaptcha-checkbox-border"); //Чек-бокс "Я не робот"
    private SelenideElement enterAuth = $(".auth-form .s-btn__text"); //На форме авторизации

    private SelenideElement dashboardElement = $(".s-dashboard__title");

    public void login(LoginForm user) {
        buttonEnter.waitUntil(Condition.visible, 15000).click();
        fillField(fillEmail, user.getEmail());
        fillField(fillPassword, user.getPassword());
        enterAuth.click();
    }

    private void fillField(SelenideElement element, String text) {
        element.click();
        if(text != null) {
            element.clear();
            element.setValue(text);
        }
    }

    //Элемент Кабинета для проверки авторизации
    public Boolean isAuthSuccess() {
        dashboardElement
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Dashboard"));
        return true;
    }
}
