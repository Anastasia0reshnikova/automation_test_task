package gui.helpers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import gui.forms.Note;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Created by a.aoreshnikova on 28.11.17.
 */

public class NotesHelper {

    private SelenideElement notes = $("span a[href='/notes/']");
    private SelenideElement buttonCreateNote = $("[data-cream-action='add-note'] span");

    //Форма заметки
    private SelenideElement nameNote = $("[data-cream-ui='input-title']");
    private SelenideElement textNote = $("[data-cream-ui='input-note']");
    private SelenideElement buttonSave = $("[data-cream-action='save']");

    private SelenideElement firstNote = $("[data-cream-ui='items'] tr");

    public void newNote(Note note) {
        buttonCreateNote.shouldBe(Condition.visible).click();
        nameNote.shouldBe(Condition.visible).setValue(note.getName());
        textNote.shouldBe(Condition.visible).setValue(note.getText());
        buttonSave.click();
    }

    public Note getNoteInfo() {
        firstNote.shouldBe(Condition.visible);
        Note note = new Note();
        note.setName(firstNote.$(".notes-note-title").getText());
        note.setText(firstNote.$(".notes-list__note-text").getText());
        return note;
    }
}
