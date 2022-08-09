package cc.kocho.handler;

import cc.kocho.Main;
import cc.kocho.Util;
import cc.kocho.database.MessageRecord;
import cc.kocho.database.Token;
import cc.kocho.database.User;
import cc.kocho.database.UserWsContext;
import cc.kocho.message.HandleMessage;
import cc.kocho.message.Message;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import io.javalin.websocket.WsMessageContext;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WebSocketMessageEventHandle {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(WebSocketMessageEventHandle.class);

    public static void message(WsMessageContext wsMessageContext){
        Gson gson = new Gson();
        Message message = gson.fromJson(Util.Encryption.decode(wsMessageContext.message()), Message.class);
        message.setText(Util.Encryption.decode(message.getText()));
        List<UserWsContext> wsContexts = WebSocket.userList.stream().filter(userWsContext -> userWsContext.getWsContext().session == wsMessageContext.session).toList();
        Query<Token> tokenQuery = Main.datastore.find(Token.class).filter(Filters.eq("token",wsContexts.get(0).getToken()));
        Query<User> userQuery = Main.datastore.find(User.class).filter(Filters.eq("account",tokenQuery.first().getAccount()));
        User user = userQuery.first();
        if (userQuery.count() < 1) {
            return;
        }
        String text = Util.SensitiveWordCheck.check(message.getText());
        String handleMessage = gson.toJson(new HandleMessage(user.getAccount(), user.getName(), text));
        Main.datastore.save(new MessageRecord(tokenQuery.first().getToken(),Util.Encryption.encode(text)));
        logger.info("正在分发账号 {} 的消息 {}",tokenQuery.first().getAccount(), text);
        WebSocket.userList.forEach(userWsContext -> {
            Util.WebSocket.sendMessage(userWsContext.getWsContext(),handleMessage);
        });
    }

}
