package cc.kocho.handler;

import cc.kocho.Main;
import cc.kocho.Util;
import cc.kocho.database.HttpMessage;
import cc.kocho.database.Token;
import cc.kocho.database.User;
import cc.kocho.server.Server;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.Query;
import io.javalin.Javalin;
import org.slf4j.LoggerFactory;

public class Http implements Server {

    Gson gson = new Gson();
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Http.class);

    @Override
    public void start(Javalin app) {
        app.get("/", ctx -> {
            ctx.result("What are you do?");
        });
        app.get("/create/{account}/{password}/{name}", ctx -> {
            String account = ctx.pathParam("account");
            String password = Util.md5(ctx.pathParam("password"));
            String name = ctx.pathParam("name");
            Query<User> userQuery = Main.datastore.find(User.class).filter(Filters.eq("account",account));
            if (userQuery.count() > 0){
                Util.Http.result(ctx,gson.toJson(new HttpMessage("Error","AccountExist")));
                return;
            }
            Main.datastore.save(new User(account,password,name));
            logger.info("用户: {} 已创建账号: {}",name,account);
            Util.Http.result(ctx,gson.toJson(new HttpMessage("Success","")));
        });
        app.get("/login/{account}/{password}", ctx -> {
            String account = ctx.pathParam("account");
            String password = Util.md5(ctx.pathParam("password"));
            logger.info("账号 {} 正在尝试获取Token",account);
            Query<User> userQuery = Main.datastore.find(User.class).filter(Filters.eq("account",account),Filters.eq("password",password));
            if (userQuery.count() == 0){
                logger.info("账号 {} 获取Token失败,密码(MD5): {}",account,password);
                Util.Http.result(ctx,gson.toJson(new HttpMessage("Error","AccountNotExistOrPasswordError")));
                return;
            }
            if (userQuery.first().isOnline()){
                logger.info("账号 {} 获取Token失败,账号已在线",account);
                Util.Http.result(ctx,gson.toJson(new HttpMessage("Error","AccountIsOnline")));
                return;
            }
            Token token = new Token(userQuery.first(),ctx.ip());
            logger.info("账号 {} 成功获取Token: {}",token.getAccount(),token.getToken());
            Main.datastore.save(token);
            Util.Http.result(ctx,token.getToken());
        });
    }
}
