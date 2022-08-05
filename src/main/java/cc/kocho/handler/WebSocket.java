package cc.kocho.handler;

import cc.kocho.Main;
import cc.kocho.server.Server;
import cc.kocho.server.WebSocketHandle;
import ch.qos.logback.classic.Logger;
import io.javalin.Javalin;
import io.javalin.websocket.WsMessageContext;
import org.slf4j.LoggerFactory;

public class WebSocket implements Server {

    private final Logger logger = (Logger) LoggerFactory.getLogger(WebSocket.class);

    @Override
    public void start(Javalin app) {
        app.ws("/ws/{token}", wsConfig -> {
            wsConfig.onConnect(wsConnectContext -> {
            });
            wsConfig.onMessage(wsMessageContext -> {
                new WebSocketHandle(wsMessageContext) {
                    @Override
                    public void handle(WsMessageContext wsMessageContext) {
                        sendMessage(wsMessageContext.message());
                    }
                };
            });
            wsConfig.onClose(wsMessageContext ->{
            });
        });
    }

}
