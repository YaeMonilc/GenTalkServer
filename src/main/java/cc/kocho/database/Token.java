package cc.kocho.database;

import cc.kocho.Config;
import cc.kocho.Util;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.time.Instant;

@Entity(value = "token", useDiscriminator = false)
public class Token {
    @Id
    private ObjectId id;
    private String token;
    private String account;
    private long createTime;
    private long expirationTime;

    public Token(User user){
        this.account = user.getAccount();
        this.token = Util.getRandomString(15);
        this.createTime = (int) Instant.now().getEpochSecond();
        this.expirationTime = this.createTime + Config.expirationTime;
    }

    public String getToken() {
        return token;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public String getAccount() {
        return account;
    }
}
