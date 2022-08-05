package cc.kocho;

import io.javalin.Javalin;

public interface Server {
    void start(Javalin app);
}

