package cc.kocho;

import io.javalin.websocket.WsMessageContext;

public class User {
    public String name;
    public WsMessageContext wsMessageContext;

    public User(String name, WsMessageContext wsMessageContext) {
        this.name = name;
        this.wsMessageContext = wsMessageContext;
    }
}
