package cc.kocho;

import ch.qos.logback.classic.Logger;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.javalin.http.Context;
import io.javalin.websocket.WsContext;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

public class Util {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(Util.class);

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

    public static String md5(String data) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(data.getBytes(StandardCharsets.UTF_8));

            for (byte b : md5) {
                sb.append(Integer.toHexString(b & 0xff));
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.toString());
        }
        return sb.toString();
    }

    public class SensitiveWordCheck {
        public static List<SensitiveWord> sensitiveWordList = new ArrayList<>();

        public static int sensitiveWordNumber = 0;

        public static String check(String word){
            String handleWord = word;
            for (SensitiveWord sensitiveWord : sensitiveWordList) {
                if (handleWord.contains(sensitiveWord.sensitiveWord)){
                    handleWord = handleWord.replaceAll(sensitiveWord.sensitiveWord,sensitiveWord.replaceWord);
                }
            }
            return handleWord;
        }

    }

    public static class WebSocket{
        public static void sendMessage(WsContext wsContext, String text){
            wsContext.send(Encryption.encode(text));
        }
    }

    public static class Http{
        public static void result(Context context, String text){
            context.header("Access-Control-Allow-Origin","*");
            context.result(Util.Encryption.encode(text));
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
