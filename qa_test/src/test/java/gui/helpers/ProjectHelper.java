package gui.helpers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Created by a.oreshnikova on 29.11.2017.
 */

public class ProjectHelper {

    //Домен
    private SelenideElement fillDomain = $(".s-analytics__content [placeholder='Enter domain...']");
    private SelenideElement addDomainButton = $(".s-analytics__content .s-analytics__submit-domain-btn .s-btn__text");
    //Проект
    private SelenideElement fillProjectName = $(".s-no-projects__input.s-input__control");
    private SelenideElement buttonCreate = $x("//span[.='Create']");
    //Заголовок страницы
    private SelenideElement projectPageTitle = $(".pr-page__title-name");
    //Удаление проекта
    private SelenideElement settings = $("div .sr-infomenu-title");
    private SelenideElement delete = $(".js-remove");
    private SelenideElement fillProjectNameForDelete = $("[placeholder='Project name']");
    private SelenideElement buttonDelete = $x("//span[.='Delete']");
    private SelenideElement pageTitle = $(".pr-page__title-text");

    //Создать домен
    private void addDomain(String domain) {
        fillDomain.setValue(domain);
        addDomainButton.click();
        $("[href='/info/test']").shouldHave(Condition.text(domain));
    }

    //Создать проект
    public void createProject(String domain, String name) {
        addDomain(domain);
        fillProjectName.shouldBe(Condition.visible).setValue(name + "." + domain);
        buttonCreate.click();
        Selenide.sleep(1000);
    }

    public String getTitle() {
        return projectPageTitle.getText();
    }

    //Удалять проект после теста
    public void deleteProject(String name, String domain) {
        settings.click();
        delete.click();
        fillProjectNameForDelete.shouldBe(Condition.visible).setValue(name + "." + domain);
        buttonDelete.click();
        pageTitle.shouldBe(Condition.visible);
    }
}
