package xyz.kowalski.vmh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.javers.core.metamodel.annotation.Id;

public abstract class Versionable implements Serializable {

    private static final long serialVersionUID = 6694175476965982736L;

    @Id
    private UUID id;

    private final List<Version> versions;

    public Versionable() {
        versions = new ArrayList<Version>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public List<Version> getVersions() {
        return versions;
    }

}
