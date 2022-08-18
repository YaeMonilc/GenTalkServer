package cc.kocho;


import cc.kocho.database.MessageRecord;
import cc.kocho.database.Token;
import cc.kocho.database.User;
import cc.kocho.database.UserWsContext;
import cc.kocho.handler.Http;
import cc.kocho.handler.WebSocket;
import cc.kocho.server.ServerStart;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import org.slf4j.LoggerFactory;

public class Main {


    public static MongoClient mongoClient;
    public static Datastore datastore;
    public static Javalin app;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Main.class);

    private static final Class<?>[] mappedClasses = new Class<?>[] {
            User.class, MessageRecord.class, Token.class
    };

    public static void main(String[] args) {

        if(args.length > 0) {
            if (!args[0].equals(""))
                Config.databaseUri.replaceAll("27017", args[0]);
        }

        mongoClient = MongoClients.create(Config.databaseUri);
        MapperOptions mapperOptions = MapperOptions.builder()
                .storeEmpties(true).storeNulls(false).build();
        datastore = Morphia.createDatastore(mongoClient, Config.databaseName, mapperOptions);
        datastore.getMapper().map(mappedClasses);

        reset();

        Util.SensitiveWordCheck.sensitiveWordList.add(new SensitiveWord("傻逼", "小可爱"));
        Util.SensitiveWordCheck.sensitiveWordList.add(new SensitiveWord("智障", "大聪明"));
        Util.SensitiveWordCheck.sensitiveWordList.add(new SensitiveWord("日你妈", "哎嘿"));
        Util.SensitiveWordCheck.sensitiveWordList.add(new SensitiveWord("你妈死了", "咋了"));

        app = Javalin.create();
        JavalinConfig javalinConfig = new JavalinConfig();
        javalinConfig.asyncRequestTimeout = Long.MAX_VALUE;
        javalinConfig.defaultContentType = "text/html; charset=utf-8";
        javalinConfig.enableCorsForAllOrigins();
        app._conf = javalinConfig;
        app.start(Config.Host, Config.port);
        ServerStart.start(new Http(),app);
        ServerStart.start(new WebSocket(),app);
    }

    private static void reset(){
        Query<User> userQuery = Main.datastore.find(User.class).filter(Filters.eq("online",true));
        userQuery.forEach(user -> {
            logger.info("账号: {} 正在进行复位",user.getAccount());
            user.setOnline(false);
            datastore.save(user);
        });
    }
}