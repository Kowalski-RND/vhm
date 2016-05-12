package xyz.kowalski.vmh;

import java.io.Serializable;
import java.util.UUID;

import org.javers.core.metamodel.annotation.Id;

public class Hobby implements Serializable {

    private static final long serialVersionUID = 1596978794798401548L;

    @Id
    private final UUID uuid;

    private final String name;

    public Hobby(final String name) {
        uuid = UUID.randomUUID();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

}
