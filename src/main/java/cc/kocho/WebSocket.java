package cc.kocho;

import cc.kocho.message.Basic;
import cc.kocho.message.Create;
import cc.kocho.message.Error;
import cc.kocho.message.Message;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.websocket.WsMessageContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class WebSocket implements Server{
    public static List<User> userList = new ArrayList<>();
    public Logger logger = Logger.getLogger(this.getClass().getName());
    Gson gson = new Gson();

    Base64.Decoder decoder = Base64.getDecoder();
    Base64.Encoder encoder = Base64.getEncoder();

    @Override
    public void start(Javalin app) {
        app.ws("/ws", wsConfig -> {
            wsConfig.onConnect(wsConnectContext -> {
            });
            wsConfig.onMessage(wsMessageContext -> {
               Basic basic = gson.fromJson(new String(decoder.decode(wsMessageContext.message())), Basic.class);
               switch (basic.event){
                   case "create":
                       create(wsMessageContext);
                       break;
                   case "message":
                       message(wsMessageContext);
                       break;
               }
            });
            wsConfig.onClose(wsMessageContext ->{
                userList.forEach(user -> {
                    user.name = "";
                });
            });
        });
    }

    private void create(WsMessageContext wsMessageContext){
        Create create = gson.fromJson(new String(decoder.decode(wsMessageContext.message())),Create.class);
        if(userList.stream().filter(user -> user.name.equals(create.name)).toList().size() > 0){
            wsMessageContext.send(encoder.encodeToString(gson.toJson(new Error("UserExist")).getBytes()));
            return;
        }
        userList.add(new User(create.name,wsMessageContext));
        wsMessageContext.send(encoder.encodeToString((gson.toJson(new Basic("CreateSuccess")).getBytes())));
        logger.info(create.name + " 已加入聊天室");
    }

    private void message(WsMessageContext wsMessageContext){
        Message message = gson.fromJson(new String(decoder.decode(wsMessageContext.message())),Message.class);
        if(userList.stream().filter(user -> user.name.equals(message.name)).toList().size() < 1){
            userList.add(new User(message.name,wsMessageContext));
        }
        logger.info(message.name + " 发送消息：" + new String(decoder.decode(message.text)));
        logger.info("分发消息：" + wsMessageContext.message());
        userList.forEach(user -> {
            user.wsMessageContext.send(wsMessageContext.message());
        });
    }

}
