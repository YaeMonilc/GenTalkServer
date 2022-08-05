package cc.kocho.server;

import cc.kocho.Util;
import io.javalin.websocket.WsMessageContext;

import java.util.logging.Logger;

public abstract class WebSocketHandle {

    private final WsMessageContext wsMessageContext;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public WebSocketHandle(WsMessageContext wsMessageContext){
        this.wsMessageContext = wsMessageContext;
        handle(this.wsMessageContext);
    }

    public abstract void handle(WsMessageContext wsMessageContext);

    public void sendMessage(String message){
        this.wsMessageContext.send(Util.Encryption.encode(message));
    }

}
