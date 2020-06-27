package eu.rex2go.hardcore.ban;

import lombok.Data;

import java.util.UUID;

@Data
public class Ban {

    private UUID uuid;
    private String reason;

    public Ban(UUID uuid, String reason) {
        this.uuid = uuid;
        this.reason = reason;
    }
}
