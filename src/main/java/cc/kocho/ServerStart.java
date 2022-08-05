package cc.kocho;

import io.javalin.Javalin;

public class ServerStart {
    public static void start(Server serverClass, Javalin app) {
        serverClass.start(app);
    }
}
