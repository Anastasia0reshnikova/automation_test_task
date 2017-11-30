package gui.helpers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import gui.forms.User;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by a.oreshnikova on 28.11.2017.
 */

public class AuthHelper {

    private SelenideElement buttonEnter = $(".header__navigation-login button"); //На главной странице
    private SelenideElement fillEmail = $(By.name("email"));
    private SelenideElement fillPassword = $(By.name("password"));
    private SelenideElement enterAuth = $(".auth-form .s-btn__text"); //На форме авторизации

    private SelenideElement dashboardElement = $(".s-dashboard__title");

    //Авторизация
    public void auth(User user) {
        if (buttonEnter.is(Condition.visible)){
            buttonEnter.waitUntil(Condition.visible, 15000).click();
        }
        if (fillEmail.is(Condition.visible)) {
            fillEmail.setValue(user.getEmail());
            fillPassword.setValue(user.getPassword());
            enterAuth.click();
        }
    }

    //Элемент Кабинета для проверки авторизации
    public Boolean isAuthSuccess() {
        dashboardElement.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Dashboard"));
        return true;
    }
}
