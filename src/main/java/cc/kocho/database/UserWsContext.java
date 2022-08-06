package cc.kocho.database;

import io.javalin.websocket.WsContext;

public class UserWsContext extends User{

    private final WsContext wsContext;
    private final String token;

    public UserWsContext(String account, String password, String name,WsContext wsContext,String token) {
        super(account, password, name);
        this.wsContext = wsContext;
        this.token = token;
    }

    public WsContext getWsContext() {
        return wsContext;
    }

    public String getToken() {
        return token;
    }
}
