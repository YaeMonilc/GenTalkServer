package cc.kocho.message;

public class ServerMessage extends Basic{
    private String message;

    public ServerMessage(String event,String message){
        this.event = "Server" + event;
        this.message = message;
    }

}
