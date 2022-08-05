package cc.kocho;


import cc.kocho.database.User;
import cc.kocho.handler.Http;
import cc.kocho.handler.WebSocket;
import cc.kocho.server.ServerStart;
import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import io.javalin.Javalin;
import org.slf4j.LoggerFactory;

public class Main {


    public static MongoClient mongoClient;
    public static Datastore datastore;
    public static Javalin app;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Main.class);

    private static final Class<?>[] mappedClasses = new Class<?>[] {
            User.class
    };

    public static void main(String[] args) {

        mongoClient = MongoClients.create(Config.databaseUri);
        MapperOptions mapperOptions = MapperOptions.builder()
                .storeEmpties(true).storeNulls(false).build();
        datastore = Morphia.createDatastore(mongoClient, Config.databaseName, mapperOptions);
        datastore.getMapper().map(mappedClasses);



        app = Javalin.create().start(Config.Host, Config.port);

        ServerStart.start(new Http(),app);
        ServerStart.start(new WebSocket(),app);
    }
}