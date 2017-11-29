package gui.helpers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import gui.forms.NoteForm;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Created by a.aoreshnikova on 28.11.17.
 */

public class NotesHelper {

    private SelenideElement notes = $("span a[href=\"/notes/\"]");
    private SelenideElement buttonCreateNote = $x("//span[.='Добавить заметку']");

    //Форма заметки
    private SelenideElement nameNote = $("[data-cream-ui='input-title']");
    private SelenideElement textNote = $("[data-cream-ui='input-note']");
    private SelenideElement buttonSave = $("[data-cream-action='save']");

    private SelenideElement firstNote = $("[data-cream-ui='items'] tr:nth-child(1)");

    public void clickMenuNotes() {
        notes.waitUntil(Condition.visible, 20000).click();
    }

    public void newNote(NoteForm note) {
        buttonCreateNote.shouldBe(Condition.visible).click();
        fillField(nameNote.shouldBe(Condition.visible), note.getName());
        fillField(textNote.shouldBe(Condition.visible), note.getText());
        buttonSave.click();
    }

    public NoteForm getNoteInfo() {
        Selenide.sleep(1000);
        NoteForm note = new NoteForm();
        note.setName(firstNote.$(".notes-note-title").getText());
        note.setText(firstNote.$(".notes-list__note-text").getText());
        return note;
    }

    private void fillField(SelenideElement element, String text) {
        element.click();
        if(text != null) {
            element.clear();
            element.setValue(text);
        }
    }
}
