package cc.kocho.database;

import cc.kocho.Config;
import cc.kocho.Util;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity(value = "token", useDiscriminator = false)
public class Token {
    @Id
    private ObjectId id;
    private String token;
    private String account;
    private List<IpRecord> ip = new ArrayList<>();
    private long createTime;
    private long expirationTime;

    public Token(User user,String ip){
        this.account = user.getAccount();
        this.token = Util.getRandomString(15);
        this.createTime = (int) Instant.now().getEpochSecond();
        this.expirationTime = this.createTime + Config.expirationTime;
        this.ip.add(new IpRecord(ip));
    }

    public void addIp(String ip) {
        this.ip.add(new IpRecord(ip));
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
