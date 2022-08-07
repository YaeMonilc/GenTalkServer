package cc.kocho.message;

public class HandleMessage extends Basic{
    public String account;
    public String name;
    public String text;

    public HandleMessage(String account,String name,String text){
        this.event = "HandleMessage";
        this.account = account;
        this.name = name;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }
}
