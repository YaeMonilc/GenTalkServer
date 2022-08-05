package cc.kocho.handler;

import cc.kocho.Main;
import cc.kocho.Util;
import cc.kocho.database.User;
import cc.kocho.server.Server;
import io.javalin.Javalin;

public class Http implements Server {
    @Override
    public void start(Javalin app) {
        app.get("/", ctx -> {
            ctx.result("What are you do?");
        });
        app.get("/create", ctx -> {
            Main.datastore.save(new User(Util.getRandomString(5),Util.getRandomString(5),Util.getRandomString(5),Util.getRandomString(5)));
        });
    }
}
