package xyz.kowalski.vmh;

import java.io.Serializable;
import java.util.List;

public class Version implements Serializable {

    private static final long serialVersionUID = -3560121388695652040L;

    private final int version;
    private final List<Modification> changes;

    public Version(final int version, final List<Modification> changes) {
        this.version = version;
        this.changes = changes;
    }

    public int getVersion() {
        return version;
    }

    public List<Modification> getChanges() {
        return changes;
    }

}
