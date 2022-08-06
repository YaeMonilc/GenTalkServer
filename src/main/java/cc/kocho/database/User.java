package cc.kocho.database;

import cc.kocho.Util;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

import java.time.Instant;

@Entity(value = "user", useDiscriminator = false)
public class User {

    @Id
    private ObjectId id;
    @Indexed
    private final String account;
    private final String password;
    private final String name;
    private boolean online = false;
    private final long createTime;

    public User(String account,String password,String name){
        this.account = account;
        this.password = password;
        this.name = name;
        this.createTime = (int) Instant.now().getEpochSecond();
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
