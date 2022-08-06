package cc.kocho.message;

public class HandleMessage extends Basic{
    public String account;
    public String text;

    public HandleMessage(String account,String text){
        this.event = "HandleMessage";
        this.account = account;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getAccount() {
        return account;
    }
}
