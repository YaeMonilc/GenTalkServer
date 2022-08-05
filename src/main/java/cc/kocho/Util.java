package cc.kocho;

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

    public static class Encryption{
        public static String encode(String text){
           return Base64.getEncoder().encodeToString(text.getBytes());
        }
        public static String decode(String text){
            return new String(Base64.getDecoder().decode(text.getBytes()));
        }
    }

}
