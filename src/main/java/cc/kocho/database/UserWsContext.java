package cc.kocho.database;

import io.javalin.websocket.WsContext;

public class UserWsContext extends User{

    private final WsContext wsContext;

    public UserWsContext(String account, String password, String name, String token,WsContext wsContext) {
        super(account, password, name, token);
        this.wsContext = wsContext;
    }

    public WsContext getWsContext() {
        return wsContext;
    }
}
