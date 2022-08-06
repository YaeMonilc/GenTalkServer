package cc.kocho.database;

public class HttpMessage extends HttpRespond{
    private String message;

    public HttpMessage(String event,String message){
        setEvent(event);
        this.message = message;
    }

}
