package app.projectma.Models;

public class InputModel {
    private String text;

    public InputModel(String str) {
        text = str;
    }

    public InputModel() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
