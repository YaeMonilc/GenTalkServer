package cc.kocho.message;

public class Message extends Basic{
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}