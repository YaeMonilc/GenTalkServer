package cc.kocho.database;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Timer;

@Entity(value = "messageRecord", useDiscriminator = false)
public class MessageRecord {
    @Id
    private ObjectId id;
    private String token;
    private long time;
    private String message;

    public MessageRecord(String token,String message){
        this.token = token;
        this.message = message;
        this.time = (int) Instant.now().getEpochSecond();
    }

}
