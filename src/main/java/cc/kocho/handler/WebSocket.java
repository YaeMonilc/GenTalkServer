package cc.kocho.handler;

import cc.kocho.Main;
import cc.kocho.Util;
import cc.kocho.database.Token;
import cc.kocho.database.User;
import cc.kocho.database.UserWsContext;
import cc.kocho.message.Basic;
import cc.kocho.message.ServerMessage;
import cc.kocho.server.Server;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import io.javalin.Javalin;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebSocket implements Server {

    private final Logger logger = (Logger) LoggerFactory.getLogger(WebSocket.class);
    public static List<UserWsContext> userList = new ArrayList<>();
    Gson gson = new Gson();

    @Override
    public void start(Javalin app) {
        app.ws("/room/{token}", wsConfig -> {
            wsConfig.onConnect(wsConnectContext -> {
                String token = wsConnectContext.pathParam("token");
                Query<Token> tokenQuery = Main.datastore.find(Token.class).filter(Filters.eq("token", token),
                        Filters.gt("expirationTime",(int) Instant.now().getEpochSecond()));
                if(tokenQuery.count() == 0){
                    wsConnectContext.send("TokenCheckFailed");
                    wsConnectContext.session.close();
                }else{
                    Query<User> userQuery = Main.datastore.find(User.class).filter(Filters.eq("account",tokenQuery.first().getAccount()));
                    User user = userQuery.first();
                    if (user.isOnline()){
                        UserWsContext onlineUser = userList.stream().filter(userWsContext -> Objects.equals(userWsContext.getAccount(), user.getAccount())).toList().get(0);
                        Util.WebSocket.sendMessage(onlineUser.getWsContext(),Util.Encryption.encode(gson.toJson(new ServerMessage("ElsewhereLink",""))));
                        onlineUser.getWsContext().session.close();
                        logger.info("账号 {} 被挤下线",user.getAccount());
                        user.setOnline(false);
                    }
                    Token userToken = tokenQuery.first();
                    user.setOnline(true);
                    Main.datastore.save(user);
                    userList.add(new UserWsContext(user.getAccount(),user.getPassword(),user.getName(), wsConnectContext,userToken.getToken()));
                    logger.info("账号 {} 已连接，Token为: {}",user.getAccount(),userToken.getToken());
                }
            });
            wsConfig.onMessage(wsMessageContext -> {
                List<UserWsContext> user = userList.stream().filter(userWsContext -> userWsContext.getWsContext().session == wsMessageContext.session).toList();
                if (user.size() < 1){
                    wsMessageContext.send("Prohibit");
                    return;
                }
                String data;
                try {
                    data = Util.Encryption.decode(wsMessageContext.message());
                }catch (Exception e){
                    logger.error(e.toString());
                    return;
                }
                if(!Util.checkMessage(data)){
                    return;
                }
                Basic basic = gson.fromJson(data, Basic.class);
                switch (basic.event){
                    case "message" -> WebSocketMessageEventHandle.message(wsMessageContext);
                    default -> {}
                }
            });
            wsConfig.onClose(wsMessageContext ->{
                List<UserWsContext> userWsContextList = userList.stream().filter(userWsContext -> userWsContext.getWsContext().session == wsMessageContext.session).toList();
                if (userWsContextList.size() == 0)
                    return;
                UserWsContext user = userWsContextList.get(0);
                Query<User> userQuery = Main.datastore.find(User.class).filter(Filters.eq("account",user.getAccount()));
                User databaseUser = userQuery.first();
                databaseUser.setOnline(false);
                Main.datastore.save(databaseUser);
                userList.remove(user);
                logger.info("账号 {} 已断开，Token为: {}",user.getAccount(),user.getToken());
            });
        });
    }

}
