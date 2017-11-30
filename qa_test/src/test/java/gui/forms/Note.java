package gui.forms;

/**
 * Created by a.oreshnikova on 29.11.2017.
 */
public class Note {

    private String name;
    private String text;

    public Note() {}

    public Note(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }
}
