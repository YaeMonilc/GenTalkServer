package cc.kocho.handler;

import cc.kocho.Main;
import cc.kocho.Util;
import cc.kocho.database.User;
import cc.kocho.database.UserWsContext;
import cc.kocho.message.HandleMessage;
import cc.kocho.message.Message;
import com.google.gson.Gson;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import io.javalin.websocket.WsMessageContext;

import java.util.List;
import java.util.Objects;

public class WebSocketMessageEventHandle {

    public static void message(WsMessageContext wsMessageContext){
        Gson gson = new Gson();
        Message message = gson.fromJson(Util.Encryption.decode(wsMessageContext.message()), Message.class);
        Query<User> userQuery = Main.datastore.find(User.class).filter(Filters.eq("token",message.token));
        if (userQuery.count() < 1) {
            return;
        }
        User user = userQuery.first();
        List<UserWsContext> wsContexts = WebSocket.userList.stream().filter(userWsContext -> userWsContext.getWsContext().session == wsMessageContext.session).toList();
        if (wsContexts.size() < 1){
            return;
        }
        if (!Objects.equals(wsContexts.get(0).getToken(), user.getToken())){
            return;
        }
        String handleMessage = gson.toJson(new HandleMessage(user.getAccount(), user.getToken()));
        WebSocket.userList.forEach(userWsContext -> {
            Util.WebSocket.sendMessage(userWsContext.getWsContext(),handleMessage);
        });
    }

}
