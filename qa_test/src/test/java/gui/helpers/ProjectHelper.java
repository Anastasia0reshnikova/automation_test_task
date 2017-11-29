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

    private SelenideElement fillDomain = $(".s-analytics__content [placeholder=\"Enter domain...\"]");
    private SelenideElement addDomainButton = $(".s-analytics__content .s-analytics__submit-domain-btn .s-btn__text");

    private SelenideElement fillProjectName = $(".s-no-projects__input.s-input__control");
    private SelenideElement buttonCreate = $x("//span[.='Create']");

    private SelenideElement projectPageTitle = $(".pr-page__title-name");

    private void addDomain(String domain) {
        fillField(fillDomain, domain);
        addDomainButton.click();
        $("[href='/info/test']").shouldHave(Condition.text(domain));
    }

    public void createProject(String domain, String name) {
        addDomain(domain);
        fillField(fillProjectName, name + "." + domain);
        buttonCreate.click();
        Selenide.sleep(1000);
    }

    public String getTitle() {
        return projectPageTitle.getText();
    }

    private void fillField(SelenideElement element, String text) {
        element.click();
        if(text != null) {
            element.clear();
            element.setValue(text);
        }
    }
}
