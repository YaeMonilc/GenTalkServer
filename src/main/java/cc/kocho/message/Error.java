package cc.kocho.message;

public class Error extends Basic{
    public String data;

    public Error(String data){
        this.event = "error";
        this.data = data;
    }

}
