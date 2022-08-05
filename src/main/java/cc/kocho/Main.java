package cc.kocho;


import io.javalin.Javalin;

import java.util.logging.Logger;

public class Main {
    public static Javalin app = Javalin.create().start("0.0.0.0",1919);
    public Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        ServerStart.start(new Http(),app);
        ServerStart.start(new WebSocket(),app);
    }
}