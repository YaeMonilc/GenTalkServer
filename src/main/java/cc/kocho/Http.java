package cc.kocho;

import io.javalin.Javalin;

public class Http implements Server{
    @Override
    public void start(Javalin app) {
        app.get("/", ctx -> {
            ctx.result("What are you do?");
        });
    }
}
