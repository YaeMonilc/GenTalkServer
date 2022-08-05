package cc.kocho.database;

import cc.kocho.Util;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

@Entity(value = "user", useDiscriminator = false)
public class User {

    @Id
    private ObjectId id;
    @Indexed
    private final String account;
    private final String password;
    private final String name;
    private final String token;
    private boolean online = false;

    public User(String account,String password,String name,String token){
        this.account = account;
        this.password = password;
        this.name = name;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
