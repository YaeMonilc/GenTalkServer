package cc.kocho.server;

import io.javalin.Javalin;

public interface Server {
    void start(Javalin app);
}

