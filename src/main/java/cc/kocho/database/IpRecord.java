package cc.kocho.database;

import dev.morphia.annotations.Entity;

import java.time.Instant;
@Entity
public class IpRecord {
    private String ip;
    private long time;

    public IpRecord(String ip){
        this.ip = ip;
        this.time = (int) Instant.now().getEpochSecond();
    }
}
