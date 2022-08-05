package cc.kocho;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.javalin.websocket.WsContext;

import java.util.Base64;
import java.util.Random;

public class Util {

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static boolean checkMessage(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }

    public static class WebSocket{
        public static void sendMessage(WsContext wsContext, String text){
            wsContext.send(Util.Encryption.encode(text));
        }
    }

    public static class Encryption{
        public static String encode(String text){
           return Base64.getEncoder().encodeToString(text.getBytes());
        }
        public static String decode(String text){
            return new String(Base64.getDecoder().decode(text.getBytes()));
        }
    }

}
