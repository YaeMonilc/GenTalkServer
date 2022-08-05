package cc.kocho.server;

import io.javalin.Javalin;

public class ServerStart {
    public static void start(Server serverClass, Javalin app) {
        serverClass.start(app);
    }
}
